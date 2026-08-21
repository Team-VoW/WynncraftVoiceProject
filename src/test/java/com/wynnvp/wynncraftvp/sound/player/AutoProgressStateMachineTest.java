/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AutoProgressStateMachineTest {
    /** Stands in for Minecraft; every field is a knob a test can turn. */
    private static final class FakeEnv implements AutoProgressStateMachine.Environment {
        long tick = 100;
        boolean enabled = true;
        boolean inGame = true;
        boolean screenOpen = false;
        boolean dialogueActive = true;
        boolean choiceOnScreen = false;
        /** Tick the visible choice was posed. Only a choice at or after the arm tick blocks. */
        long choiceStartTick = Long.MIN_VALUE;

        boolean audioPlaying = false;
        boolean stillTyping = false;
        boolean playerSneaking = false;
        boolean keyDown = false;

        final List<Boolean> keyEvents = new ArrayList<>();
        final List<String> log = new ArrayList<>();

        @Override
        public long currentTick() {
            return tick;
        }

        @Override
        public boolean autoProgressEnabled() {
            return enabled;
        }

        @Override
        public boolean inGame() {
            return inGame;
        }

        @Override
        public boolean screenOpen() {
            return screenOpen;
        }

        @Override
        public boolean dialogueActive() {
            return dialogueActive;
        }

        @Override
        public boolean choiceAwaitingAnswer(long sinceTick) {
            return choiceOnScreen && choiceStartTick >= sinceTick;
        }

        @Override
        public boolean choiceOnScreen() {
            return choiceOnScreen;
        }

        /** Puts a question on screen right now, as Wynncraft does mid-line. */
        void poseChoice() {
            choiceOnScreen = true;
            choiceStartTick = tick;
        }

        @Override
        public boolean dialogueStillTyping() {
            return stillTyping;
        }

        @Override
        public boolean audioPlaying() {
            return audioPlaying;
        }

        @Override
        public boolean sneakKeyHeldByPlayer() {
            return playerSneaking;
        }

        @Override
        public boolean sneakKeyDown() {
            return keyDown || playerSneaking;
        }

        @Override
        public void setSneakKey(boolean down) {
            keyEvents.add(down);
            keyDown = down;
        }

        @Override
        public void log(String message) {
            log.add(message);
        }
    }

    private FakeEnv env;
    private AutoProgressStateMachine machine;

    @BeforeEach
    void setUp() {
        env = new FakeEnv();
        machine = new AutoProgressStateMachine(env);
    }

    /** Runs the machine for {@code ticks} ticks, one game tick apart. */
    private void run(int ticks) {
        for (int i = 0; i < ticks; i++) {
            env.tick++;
            machine.onTick();
        }
    }

    /** Arms a two second line, lets playback start, then lets it finish. */
    private void playFullLine() {
        machine.arm(2000, "some-line-1");
        env.audioPlaying = true;
        run(2);
        assertEquals(AutoProgressStateMachine.State.WAITING_FOR_END, machine.state());
        env.audioPlaying = false;
    }

    // -------------------------------------------------------------------------
    // The happy path
    // -------------------------------------------------------------------------

    @Test
    void pressesSneakOnceTheLineHasFinished() {
        playFullLine();
        run(1);

        assertEquals(List.of(true), env.keyEvents, "Sneak must go down exactly once");
        assertEquals(AutoProgressStateMachine.State.PRESSING, machine.state());
    }

    @Test
    void releasesSneakAfterTheHoldWindow() {
        playFullLine();
        run(1);
        run(AutoProgressStateMachine.PRESS_HOLD_TICKS);

        assertEquals(List.of(true, false), env.keyEvents);
        assertEquals(AutoProgressStateMachine.State.IDLE, machine.state());
        assertFalse(env.keyDown, "The key must not be left down");
    }

    @Test
    void doesNotPressWhileTheLineIsStillPlaying() {
        machine.arm(2000, "some-line-1");
        env.audioPlaying = true;
        run(80); // well past the two second estimate

        assertTrue(env.keyEvents.isEmpty(), "Sneak must wait for the audio, not the estimate");
        assertEquals(AutoProgressStateMachine.State.WAITING_FOR_END, machine.state());
    }

    // -------------------------------------------------------------------------
    // Dialogue choices
    // -------------------------------------------------------------------------

    @Test
    void neverPressesWhenAChoiceIsWaitingForAnAnswer() {
        machine.arm(2000, "the-question");
        env.audioPlaying = true;
        run(2);
        env.poseChoice(); // the answer list appears while the question is being spoken
        env.audioPlaying = false;
        run(1);

        assertTrue(env.keyEvents.isEmpty(), "A dialogue choice must be left for the player to answer");
        assertEquals(AutoProgressStateMachine.State.IDLE, machine.state());
    }

    @Test
    void aChoiceIsNotRetriedOnLaterTicks() {
        machine.arm(2000, "the-question");
        env.audioPlaying = true;
        run(2);
        env.poseChoice();
        env.audioPlaying = false;
        run(1);

        // The prompt goes away, but the line it belonged to is long gone — no delayed press.
        env.choiceOnScreen = false;
        run(100);

        assertTrue(env.keyEvents.isEmpty());
    }

    /**
     * Regression for "After you!": Wynncraft leaves the answer list on screen after it is answered,
     * so the reply that follows was being cancelled by a box nobody was waiting on.
     */
    @Test
    void aChoiceAlreadyAnsweredDoesNotBlockTheReplyThatFollows() {
        machine.arm(2000, "the-question");
        env.audioPlaying = true;
        run(2);
        env.poseChoice();
        env.audioPlaying = false;
        run(1);
        assertTrue(env.keyEvents.isEmpty(), "The question itself must not be auto-answered");

        // The player answers; the reply starts while the box is still up and unchanged.
        run(60);
        machine.arm(685, "the-reply");
        env.audioPlaying = true;
        run(2);
        env.audioPlaying = false;
        run(1);

        assertEquals(List.of(true), env.keyEvents, "The reply must still auto progress");
    }

    @Test
    void aSecondQuestionPosedDuringTheReplyBlocksAgain() {
        machine.arm(2000, "the-question");
        env.audioPlaying = true;
        run(2);
        env.poseChoice();
        env.audioPlaying = false;
        run(1);

        run(60);
        machine.arm(2000, "the-reply");
        env.audioPlaying = true;
        run(2);
        env.poseChoice(); // a fresh set of answers replaces the old ones
        env.audioPlaying = false;
        run(1);

        assertTrue(env.keyEvents.isEmpty());
    }

    // -------------------------------------------------------------------------
    // Waiting for the typewriter
    // -------------------------------------------------------------------------

    @Test
    void waitsForTheTextBeforePressing() {
        // Early play starts the audio partway through the animation, so on a short line the audio can
        // run out first. Sneak then would only fast-forward the text and leave the dialogue sitting.
        env.stillTyping = true;
        playFullLine();
        run(20);

        assertTrue(env.keyEvents.isEmpty(), "Sneak must not fast-forward the animation");
        assertEquals(AutoProgressStateMachine.State.READY_TO_PRESS, machine.state());
    }

    @Test
    void pressesAsSoonAsTheTextSettles() {
        env.stillTyping = true;
        playFullLine();
        run(20);

        env.stillTyping = false;
        run(1);

        assertEquals(List.of(true), env.keyEvents);
    }

    @Test
    void givesUpIfTheTextNeverSettles() {
        env.stillTyping = true;
        machine.arm(1000, "some-line-1"); // 20 ticks
        env.audioPlaying = true;
        run(2);
        env.audioPlaying = false;

        run(AutoProgressStateMachine.MAX_OVERRUN_TICKS + 20);

        assertTrue(env.keyEvents.isEmpty());
        assertEquals(AutoProgressStateMachine.State.IDLE, machine.state());
    }

    @Test
    void aChoiceAppearingWhileWaitingForTheTextStillBlocksThePress() {
        env.stillTyping = true;
        playFullLine();
        run(5);

        env.poseChoice();
        env.stillTyping = false;
        run(5);

        assertTrue(env.keyEvents.isEmpty());
        assertEquals(AutoProgressStateMachine.State.IDLE, machine.state());
    }

    // -------------------------------------------------------------------------
    // Open screens pause, they do not cancel
    // -------------------------------------------------------------------------

    @Test
    void doesNotPressWhileAScreenIsOpen() {
        playFullLine();
        env.screenOpen = true;
        run(10);

        assertTrue(env.keyEvents.isEmpty(), "The key press would go to the screen, not the world");
    }

    /** Chat opened and shut mid-line must not lose the line. Same for the escape menu. */
    @Test
    void pressesAfterAScreenOpenedAndClosedMidLine() {
        machine.arm(2000, "some-line-1");
        env.audioPlaying = true;
        run(2);

        env.screenOpen = true; // player taps T, then escape
        run(6);
        env.screenOpen = false;
        run(1);

        env.audioPlaying = false;
        run(1);

        assertEquals(List.of(true), env.keyEvents, "The line must still auto progress");
    }

    @Test
    void pressesAsSoonAsAScreenOpenDuringTheLineEndCloses() {
        // The line finishes while chat is up: the press has to wait, then fire on close.
        playFullLine();
        env.screenOpen = true;
        run(10);
        assertTrue(env.keyEvents.isEmpty());

        env.screenOpen = false;
        run(1);

        assertEquals(List.of(true), env.keyEvents);
    }

    @Test
    void timeSpentInAScreenDoesNotCountAgainstTheGiveUpDeadline() {
        // Typing out a long chat message takes longer than the give-up window. Coming back should
        // still advance the dialogue rather than find auto progress had quietly expired.
        playFullLine();
        env.screenOpen = true;
        run(AutoProgressStateMachine.MAX_OVERRUN_TICKS * 3);

        env.screenOpen = false;
        run(1);

        assertEquals(List.of(true), env.keyEvents);
    }

    // -------------------------------------------------------------------------
    // Bailing out
    // -------------------------------------------------------------------------

    @Test
    void cancelsWhenThePlayerPressesSneakThemselves() {
        machine.arm(2000, "some-line-1");
        env.audioPlaying = true;
        run(2);

        env.playerSneaking = true;
        run(1);
        assertEquals(AutoProgressStateMachine.State.IDLE, machine.state());

        env.playerSneaking = false;
        env.audioPlaying = false;
        run(10);
        assertTrue(env.keyEvents.isEmpty(), "The player already advanced the dialogue");
    }

    @Test
    void sneakAlreadyHeldWhenArmingIsNotAFreshPress() {
        env.playerSneaking = true;
        machine.arm(2000, "some-line-1");
        env.audioPlaying = true;
        run(2);

        assertEquals(AutoProgressStateMachine.State.WAITING_FOR_END, machine.state());
    }

    @Test
    void doesNotPressOnTopOfAHeldSneakKey() {
        machine.arm(2000, "some-line-1");
        env.audioPlaying = true;
        run(2);
        env.audioPlaying = false;
        env.playerSneaking = true; // held since before arming, so not a fresh press
        run(1);

        assertTrue(env.keyEvents.isEmpty(), "Forcing the key up later would fight the player's finger");
        assertEquals(AutoProgressStateMachine.State.IDLE, machine.state());
    }

    @Test
    void cancelsWhenThePlayerLeavesTheWorld() {
        machine.arm(2000, "some-line-1");
        env.audioPlaying = true;
        run(2);

        env.inGame = false;
        run(1);
        assertEquals(AutoProgressStateMachine.State.IDLE, machine.state());

        env.inGame = true;
        env.audioPlaying = false;
        run(10);
        assertTrue(env.keyEvents.isEmpty());
    }

    @Test
    void cancelsWhenTheDialogueDisappears() {
        machine.arm(2000, "some-line-1");
        env.audioPlaying = true;
        run(2);

        env.dialogueActive = false;
        env.audioPlaying = false;
        run(10);

        assertTrue(env.keyEvents.isEmpty());
    }

    @Test
    void cancelsWhenTheSettingIsTurnedOffMidLine() {
        machine.arm(2000, "some-line-1");
        env.audioPlaying = true;
        run(2);

        env.enabled = false;
        env.audioPlaying = false;
        run(10);

        assertTrue(env.keyEvents.isEmpty());
    }

    @Test
    void explicitCancelReleasesAKeyWeAreHolding() {
        playFullLine();
        run(1);
        assertEquals(List.of(true), env.keyEvents);

        machine.cancel("test");

        assertEquals(List.of(true, false), env.keyEvents);
        assertFalse(env.keyDown);
    }

    @Test
    void resetReleasesAKeyWeAreHolding() {
        playFullLine();
        run(1);

        machine.reset();

        assertEquals(List.of(true, false), env.keyEvents);
        assertEquals(AutoProgressStateMachine.State.IDLE, machine.state());
    }

    @Test
    void reArmingWhileHoldingTheKeyReleasesItFirst() {
        playFullLine();
        run(1);

        machine.arm(1000, "some-line-2");

        assertEquals(List.of(true, false), env.keyEvents);
        assertEquals(AutoProgressStateMachine.State.WAITING_FOR_START, machine.state());
    }

    // -------------------------------------------------------------------------
    // Fallbacks when the sound engine never speaks up
    // -------------------------------------------------------------------------

    @Test
    void progressesOnTheEstimateWhenPlaybackNeverStarts() {
        machine.arm(2000, "some-line-1"); // 40 ticks
        run(39);
        assertTrue(env.keyEvents.isEmpty());

        run(1);
        assertEquals(List.of(true), env.keyEvents);
    }

    @Test
    void shortLinesStillWaitOutTheStartGrace() {
        // A 100 ms line is two ticks long, far shorter than it takes a source to appear, so the
        // estimate alone would fire before the audio had a chance to exist.
        machine.arm(100, "some-line-1");
        run(AutoProgressStateMachine.START_GRACE_TICKS - 1);
        assertTrue(env.keyEvents.isEmpty());

        run(1);
        assertEquals(List.of(true), env.keyEvents);
    }

    @Test
    void givesUpOnAudioThatOverrunsItsEstimate() {
        machine.arm(1000, "some-line-1"); // 20 ticks
        env.audioPlaying = true;
        run(2);

        run(AutoProgressStateMachine.MAX_OVERRUN_TICKS + 20);

        assertEquals(List.of(true), env.keyEvents, "A source that never stops must not wedge auto progress");
    }
}
