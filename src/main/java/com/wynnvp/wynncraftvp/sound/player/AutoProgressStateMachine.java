/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.player;

/**
 * Decides when the sneak key should be tapped to advance Wynncraft dialogue.
 *
 * <p>The old implementation fired a forged {@code ServerboundPlayerInputPacket} from a
 * {@link java.util.concurrent.ScheduledExecutorService}. That was wrong in three ways: the packet
 * never reached {@code LocalPlayer.lastSentInput}, so vanilla saw no change and never sent the
 * matching release; the packet's own constructor re-entered the mod's sneak mixin, which sent the
 * release <em>before</em> the press; and a fixed timer ignored whether the line was actually still
 * playing. Together that left the player stuck crouching and skipped or missed lines.
 *
 * <p>This class drives the real key mapping from the client tick instead, so vanilla builds and
 * sends the input exactly as if the player had pressed sneak themselves. It is a pure state machine
 * with no {@code net.minecraft.*} dependencies; everything it needs from the game arrives through
 * {@link Environment}, which makes the whole sequence unit testable.
 *
 * <p>Lifecycle of one line:
 * <ol>
 *   <li>{@link #arm} when audio for a line is handed to the sound engine.
 *   <li>{@code WAITING_FOR_START} until the engine reports a live source (it starts on a worker
 *       thread, so this takes a tick or two).
 *   <li>{@code WAITING_FOR_END} until every source has drained — the honest "line is over" signal,
 *       which beats the estimated duration because time stretching and network fetches shift it.
 *   <li>{@code READY_TO_PRESS} until the typewriter has caught up with the audio.
 *   <li>{@code PRESSING} holds sneak for a few ticks, then releases it.
 * </ol>
 *
 * <p>Everything is abandoned the moment the situation stops looking like "an NPC just finished
 * talking to a player who is watching": dialogue gone, the player sneaked themselves, or — the one
 * case that must never be auto-answered — a dialogue choice still waiting on them. An open screen is
 * the exception: that only pauses, because the line is still there behind it.
 */
public final class AutoProgressStateMachine {
    public enum State {
        IDLE,
        WAITING_FOR_START,
        WAITING_FOR_END,
        READY_TO_PRESS,
        PRESSING
    }

    /** How long sneak is held. Vanilla needs a tick to pick the key up and another to send it. */
    static final int PRESS_HOLD_TICKS = 4;

    /** Minimum wait before concluding that audio is never going to start. */
    static final int START_GRACE_TICKS = 20;

    /** Never wait more than this past the estimated end, however stuck the sound engine looks. */
    static final int MAX_OVERRUN_TICKS = 200;

    /** Everything the machine needs from Minecraft. */
    public interface Environment {
        /** Current game tick. */
        long currentTick();

        /** The {@code autoProgress} config toggle. */
        boolean autoProgressEnabled();

        /** World loaded, player alive, connection up. */
        boolean inGame();

        /** A screen is open, so key presses go to it rather than the world. */
        boolean screenOpen();

        /** Wynncraft is still showing overlay dialogue. */
        boolean dialogueActive();

        /**
         * A choice posed at or after {@code sinceTick} is still waiting for an answer, so the player
         * — not the mod — has to give it.
         *
         * <p>The tick matters because Wynncraft leaves the answer list on screen after it has been
         * answered, right through the reply that follows. Asking "is a box visible" would strand
         * every line after a choice; asking "was this question posed before my line even started"
         * separates a live question from its leftovers.
         */
        boolean choiceAwaitingAnswer(long sinceTick);

        /** A choice box is visible, answered or not. Diagnostics only. */
        boolean choiceOnScreen();

        /**
         * The typewriter is still laying out the current line. Sneak during the animation only
         * fast-forwards the text, so pressing then would burn the tap and stall the dialogue.
         */
        boolean dialogueStillTyping();

        /** At least one voice line source is alive. */
        boolean audioPlaying();

        /** The sneak key is down because the player is holding it, not because we pressed it. */
        boolean sneakKeyHeldByPlayer();

        /** True while the sneak key is down for any reason. */
        boolean sneakKeyDown();

        /** Presses or releases the real sneak key mapping. */
        void setSneakKey(boolean down);

        void log(String message);
    }

    private final Environment env;

    private State state = State.IDLE;
    private long armTick;
    private long expectedEndTick;
    private long giveUpTick;
    private long pressStartTick;
    private boolean holdingSneak;
    private boolean previousPlayerSneak;
    private boolean loggedTypewriterWait;
    private boolean loggedScreenWait;
    private String armedLine = "";

    public AutoProgressStateMachine(Environment env) {
        this.env = env;
    }

    public State state() {
        return state;
    }

    /**
     * Arms auto progress for a line that has just been handed to the sound engine.
     *
     * @param expectedDurationMillis playback length after the speed multiplier, used only as a
     *                               fallback deadline — the real trigger is the engine going quiet
     * @param line                   identifier for the log line
     */
    public void arm(long expectedDurationMillis, String line) {
        releaseIfHeld();

        long now = env.currentTick();
        long durationTicks = Math.max(1L, (expectedDurationMillis + 49L) / 50L);

        armTick = now;
        expectedEndTick = now + durationTicks;
        giveUpTick = expectedEndTick + MAX_OVERRUN_TICKS;
        armedLine = line == null ? "" : line;
        loggedTypewriterWait = false;
        loggedScreenWait = false;
        state = State.WAITING_FOR_START;
        // Sampled now so that sneak the player was *already* holding does not read as a fresh press.
        previousPlayerSneak = env.sneakKeyHeldByPlayer();

        env.log("armed for \"" + armedLine + "\" — " + expectedDurationMillis + " ms (~" + durationTicks
                + " ticks), deadline tick " + expectedEndTick);
    }

    /** Drops a pending progress without touching the key state beyond releasing our own press. */
    public void cancel(String reason) {
        if (state == State.IDLE) return;
        releaseIfHeld();
        state = State.IDLE;
        env.log("cancelled: " + reason);
    }

    /** Full reset for disconnects and sound engine restarts. */
    public void reset() {
        releaseIfHeld();
        state = State.IDLE;
        previousPlayerSneak = false;
        armedLine = "";
    }

    public void onTick() {
        if (state == State.PRESSING) {
            tickPress();
            return;
        }

        if (state == State.IDLE) return;

        long now = env.currentTick();

        if (!env.autoProgressEnabled()) {
            cancel("auto progress was turned off");
            return;
        }

        if (!env.inGame()) {
            cancel("there is no player in the world any more");
            return;
        }

        boolean playerSneak = env.sneakKeyHeldByPlayer();
        boolean freshPress = playerSneak && !previousPlayerSneak;
        previousPlayerSneak = playerSneak;
        if (freshPress) {
            cancel("the player pressed sneak themselves");
            return;
        }

        if (!env.dialogueActive()) {
            cancel("dialogue is no longer on screen");
            return;
        }

        // Chat, the escape menu, the inventory: vanilla releases every key mapping while one of these
        // is up and routes input to it, so there is nothing useful to do until it closes. Hold rather
        // than give up — the line is still on screen behind it, and a player who tabs into chat for a
        // second and back out expects the dialogue to carry on. The give-up clock is pushed along in
        // step with the wait so that time spent in a menu does not count against it.
        if (env.screenOpen()) {
            giveUpTick++;
            if (!loggedScreenWait) {
                loggedScreenWait = true;
                env.log("a screen is open, holding \"" + armedLine + "\" until it closes");
            }
            return;
        }

        if (loggedScreenWait) {
            loggedScreenWait = false;
            env.log("screen closed, carrying on with \"" + armedLine + "\"");
        }

        switch (state) {
            case WAITING_FOR_START -> tickWaitingForStart(now);
            case WAITING_FOR_END -> tickWaitingForEnd(now);
            case READY_TO_PRESS -> tickReadyToPress(now);
            default -> {}
        }
    }

    private void tickWaitingForStart(long now) {
        if (env.audioPlaying()) {
            state = State.WAITING_FOR_END;
            env.log("playback started for \"" + armedLine + "\" after " + (now - armTick) + " ticks");
            return;
        }

        // Either the fetch failed, or the line was so short that it began and ended between two
        // ticks. Both mean there is nothing left to wait for once the estimate has passed.
        if (now >= expectedEndTick && now >= armTick + START_GRACE_TICKS) {
            becomeReady(
                    now,
                    "no live audio source appeared for \"" + armedLine + "\" within " + (now - armTick)
                            + " ticks; going on the estimated duration instead");
        }
    }

    private void tickWaitingForEnd(long now) {
        if (!env.audioPlaying()) {
            becomeReady(
                    now,
                    "playback of \"" + armedLine + "\" finished at tick " + now + " (estimate was " + expectedEndTick
                            + ", " + (now - expectedEndTick) + " ticks out)");
            return;
        }

        if (now >= giveUpTick) {
            becomeReady(
                    now,
                    "playback of \"" + armedLine + "\" is still running " + MAX_OVERRUN_TICKS
                            + " ticks past its estimate; going on anyway");
        }
    }

    private void becomeReady(long now, String why) {
        state = State.READY_TO_PRESS;
        env.log(why);
        tickReadyToPress(now);
    }

    private void tickReadyToPress(long now) {
        if (env.choiceAwaitingAnswer(armTick)) {
            cancel("a dialogue choice is waiting for an answer — the player has to give it");
            return;
        }

        if (env.dialogueStillTyping()) {
            // The line is voiced but the text has not caught up, which early play makes routine on
            // short lines. Sneaking now would only skip the animation, so hold until it settles.
            if (now >= giveUpTick) {
                cancel("the text never finished animating");
                return;
            }
            if (!loggedTypewriterWait) {
                loggedTypewriterWait = true;
                env.log("waiting for the text to finish before pressing sneak");
            }
            return;
        }

        if (env.sneakKeyDown()) {
            // Someone is already holding it down; pressing on top would only mean we later force a
            // key up under their finger.
            state = State.IDLE;
            env.log("sneak is already held, leaving it alone");
            return;
        }

        env.setSneakKey(true);
        holdingSneak = true;
        pressStartTick = now;
        state = State.PRESSING;
        env.log("pressing sneak to advance past \"" + armedLine + "\""
                + (env.choiceOnScreen() ? " (an already answered choice box is still on screen)" : ""));
    }

    private void tickPress() {
        if (env.currentTick() < pressStartTick + PRESS_HOLD_TICKS) return;
        releaseIfHeld();
        state = State.IDLE;
        env.log("released sneak");
    }

    private void releaseIfHeld() {
        if (!holdingSneak) return;
        holdingSneak = false;
        env.setSneakKey(false);
    }
}
