/*
 * Copyright © Team-VoW 2024-2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.player;

import com.wynnvp.wynncraftvp.ModCore;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;

/**
 * Taps the sneak key once a voice line has finished, so dialogue moves on by itself.
 *
 * <p>Thin Minecraft adapter around {@link AutoProgressStateMachine}. Two things matter here:
 *
 * <ul>
 *   <li><b>The real key mapping is used</b>
 *       Driving {@code options.keyShift} makes vanilla do the whole thing, exactly as if the player
 *       had crouched.
 *   <li><b>Arming is queued, not applied inline.</b> Playback is kicked off from the audio worker
 *       pool after a remote fetch, so {@link #onLinePlaybackStarted} can be called from any thread.
 *       Requests are drained in order on the client tick, which keeps a stop-then-replay pair (the
 *       early-play correction path) in the order it happened.
 * </ul>
 */
public class AutoProgress {
    private record Request(boolean cancel, long durationMillis, String line) {}

    private final ConcurrentLinkedQueue<Request> requests = new ConcurrentLinkedQueue<>();

    /** True while the sneak key is down because this class pressed it. */
    private boolean pressedByUs;

    private final AutoProgressStateMachine machine = new AutoProgressStateMachine(new Env());

    /** Arms auto progress for a line that was just handed to the sound engine. Any thread. */
    public void onLinePlaybackStarted(long durationMillis, String line) {
        requests.add(new Request(false, durationMillis, line));
    }

    /** Drops a pending progress, e.g. because the audio it was waiting on got stopped. Any thread. */
    public void cancel(String reason) {
        requests.add(new Request(true, 0L, reason));
    }

    /** Called once per world tick, on the client thread. */
    public void onTick() {
        Request request;
        while ((request = requests.poll()) != null) {
            if (request.cancel()) {
                machine.cancel(request.line());
            } else if (ModCore.config != null && ModCore.config.autoProgress) {
                machine.arm(request.durationMillis(), request.line());
            }
        }
        machine.onTick();
    }

    /** Releases the key and forgets everything; for disconnects and sound engine restarts. */
    public void reset() {
        requests.clear();
        machine.reset();
    }

    private final class Env implements AutoProgressStateMachine.Environment {
        @Override
        public long currentTick() {
            Minecraft mc = Minecraft.getInstance();
            return mc.level == null ? 0L : mc.level.getGameTime();
        }

        @Override
        public boolean autoProgressEnabled() {
            return ModCore.config != null && ModCore.config.autoProgress;
        }

        @Override
        public boolean inGame() {
            Minecraft mc = Minecraft.getInstance();
            return mc.player != null && mc.level != null && mc.getConnection() != null;
        }

        @Override
        public boolean screenOpen() {
            return Minecraft.getInstance().screen != null;
        }

        @Override
        public boolean dialogueActive() {
            return ModCore.overlayHandler != null && ModCore.overlayHandler.isVoiceDialogActive();
        }

        @Override
        public boolean choiceAwaitingAnswer(long sinceTick) {
            return ModCore.overlayHandler != null && ModCore.overlayHandler.isChoiceAwaitingAnswerSince(sinceTick);
        }

        @Override
        public boolean choiceOnScreen() {
            return ModCore.overlayHandler != null && ModCore.overlayHandler.isDialogueChoiceOnScreen();
        }

        @Override
        public boolean dialogueStillTyping() {
            return ModCore.overlayHandler != null && ModCore.overlayHandler.isTypewriterRunning();
        }

        @Override
        public boolean audioPlaying() {
            return ModCore.instance != null
                    && ModCore.instance.audioPlayer != null
                    && ModCore.instance.audioPlayer.openAlPlayer.isPlayingAnything();
        }

        @Override
        public boolean sneakKeyHeldByPlayer() {
            return !pressedByUs && sneakKeyDown();
        }

        @Override
        public boolean sneakKeyDown() {
            KeyMapping key = sneakKey();
            return key != null && key.isDown();
        }

        /**
         * Presses or releases sneak.
         *
         * <p>The mapping is a {@code ToggleKeyMapping}: with "Toggle Sneak" on, {@code setDown(true)}
         * flips the toggle and {@code setDown(false)} does nothing at all. Releasing therefore has to
         * check whether the key actually came up, and flip it back if it did not — otherwise a
         * toggle-sneak player would be left permanently crouched.
         */
        @Override
        public void setSneakKey(boolean down) {
            KeyMapping key = sneakKey();
            if (key == null) return;

            if (down) {
                if (!key.isDown()) key.setDown(true);
                pressedByUs = key.isDown();
                return;
            }

            key.setDown(false);
            if (key.isDown()) key.setDown(true);
            pressedByUs = false;
        }

        @Override
        public void log(String message) {
            if (ModCore.config != null && ModCore.config.isLogAutoProgress()) {
                ModCore.info("[AutoProgress] " + message);
            }
        }

        private KeyMapping sneakKey() {
            Minecraft mc = Minecraft.getInstance();
            return mc.options == null ? null : mc.options.keyShift;
        }
    }
}
