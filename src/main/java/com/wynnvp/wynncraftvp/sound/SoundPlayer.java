/*
 * Copyright © Team-VoW 2024-2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound;

import static com.wynnvp.wynncraftvp.ModCore.config;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.logging.VowLogger;
import com.wynnvp.wynncraftvp.sound.line.LineData;
import com.wynnvp.wynncraftvp.sound.line.LineReporter;
import com.wynnvp.wynncraftvp.sound.player.AudioPlayer;
import com.wynnvp.wynncraftvp.utils.Utils;
import java.util.ArrayDeque;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class SoundPlayer {
    public static volatile boolean stopMod = false;

    /**
     * How long a line that has just been handed to the sound engine counts as playing before the
     * queue gives up waiting for it.
     *
     * <p>A line is not audible the moment it is started: a remote fetch and the Ogg decode both
     * happen off-thread first. Without this window the queue would empty itself into that gap and
     * every waiting line would start at once. It also caps the damage when a line never arrives at
     * all, e.g. because the download failed.
     */
    private static final int START_GRACE_TICKS = 60;

    private final LineReporter lineReporter;

    /** Lines waiting for the one before them to finish, in the order they were spoken. */
    private final ArrayDeque<SoundObject> queuedSounds = new ArrayDeque<>();

    /** Ticks left of {@link #START_GRACE_TICKS} for the line that was started last. */
    private int startGrace = 0;

    public SoundPlayer() {
        lineReporter = new LineReporter();
    }

    /** Drives the buffered unvoiced-line reports and the queued lines; called once per world tick. */
    public void onTick() {
        lineReporter.onTick();
        pumpQueuedSounds();
    }

    // Code that is run to play all the sounds
    public void playSound(LineData lineData) {
        if (stopMod) return;
        if (config.isLogPlayingInformation()) {
            VowLogger.logLine(
                    "[Attempting to play] " + lineData.getRealLine().trim() + " HTTP encoded:"
                            + Utils.HTTPEncode(lineData.getRealLine().trim()),
                    "Info");
        }

        String line = lineData.getSoundLine();

        SoundsHandler soundsHandler = ModCore.instance.soundsHandler;

        if (!canPlaySound(soundsHandler, lineData)) return;

        if (!config.isOnlyLogNotPlayingLines() && config.isLogDialogueLines())
            VowLogger.logLine(lineData.getRealLine() + " [PLAYED]");

        soundsHandler.get(line).ifPresentOrElse(this::PlaySoundObject, () -> {
            if (config.isLogPlayingInformation()) {
                VowLogger.logLine("Could not play sound: " + line, "Info");
            }
        });
    }

    private boolean canPlaySound(SoundsHandler soundsHandler, LineData lineData) {
        String line = lineData.getSoundLine();

        if (soundsHandler.get(line).isEmpty()) {
            if (config.isLogDialogueLines()) VowLogger.logLine(lineData.getRealLine());
            lineReporter.MissingLine(lineData);
            return false;
        }
        return true;
    }

    public void playFromObject(SoundObject sound) {
        if (stopMod) return;
        PlaySoundObject(sound);
    }

    public void stopCurrentAudio() {
        // Whatever was lined up behind the stopped audio goes with it: the usual caller is the
        // early-play correction path, where the line the queue was waiting on turned out to be the
        // wrong guess entirely.
        clearQueue();
        if (ModCore.instance.audioPlayer == null) return;
        ModCore.instance.audioPlayer.stopAudio();
    }

    private void PlaySoundObject(SoundObject sound) {
        if (ModCore.instance.audioPlayer == null) {
            Utils.sendMessage("ERROR! SOUND ENGINE WAS NOT INITIALIZED! PLEASE MAKE A REPORT");
            return;
        }

        // This sound is registered so it does not get reported to the line reporter
        // But it is not supposed to play anything.
        if (sound.getId() == null) {
            return;
        }

        submit(sound);
    }

    /**
     * Starts the line, or parks it behind whatever is already speaking.
     *
     * <p>{@code "queue": true} in sounds.json is for lines that read as a follow-on rather than an
     * interruption: the speaker keeps talking and this one comes in when they are done. Everything
     * else behaves as it always has and cuts the current line off.
     */
    private synchronized void submit(SoundObject sound) {
        if (sound.shouldQueue() && isBusy()) {
            queuedSounds.add(sound);
            return;
        }

        // A line that cuts the current one off supersedes what was waiting behind it too, otherwise
        // the interrupted line's follow-ups would surface after the new one and read as non sequiturs.
        if (sound.shouldStopSounds()) {
            queuedSounds.clear();
        }
        startPlaying(sound);
    }

    /**
     * Releases the next waiting line once the one before it has fallen silent.
     *
     * <p>Client thread only, which is also where {@link #primeSpeaker} wants to run.
     */
    private synchronized void pumpQueuedSounds() {
        AudioPlayer audioPlayer = ModCore.instance.audioPlayer;
        if (stopMod || audioPlayer == null) {
            clearQueue();
            return;
        }

        if (audioPlayer.openAlPlayer.isPlayingAnything()) {
            // The line that was started has reached the sound engine, so the grace window is spent.
            startGrace = 0;
            return;
        }

        if (startGrace > 0) {
            startGrace--;
            return;
        }

        SoundObject next = queuedSounds.poll();
        if (next != null) {
            startPlaying(next);
        }
    }

    private void startPlaying(SoundObject sound) {
        startGrace = START_GRACE_TICKS;
        NpcSoundBlocker.markVoiceLineStarted();
        primeSpeaker(sound);
        ModCore.instance.audioPlayer.play(sound);
    }

    /** True while a line is audible, on its way to being audible, or still waiting its turn. */
    private boolean isBusy() {
        AudioPlayer audioPlayer = ModCore.instance.audioPlayer;
        return audioPlayer != null
                && (audioPlayer.openAlPlayer.isPlayingAnything() || startGrace > 0 || !queuedSounds.isEmpty());
    }

    private synchronized void clearQueue() {
        queuedSounds.clear();
        startGrace = 0;
    }

    /**
     * Finds the speaker, and measures the room around them, before playback starts.
     *
     * <p>Decoding and uploading the audio happens on a worker thread, which must not touch the world,
     * so both are done here instead. Chat and overlay handling are already on the client thread, so
     * this normally runs inline and the line is in the right place, in the right room, from its first
     * sample.
     *
     * <p>Not gated on the sound physics toggle: the position is needed either way, or the source spends
     * its first tick at the world origin. The measuring inside is what the toggle skips.
     */
    private void primeSpeaker(SoundObject sound) {
        Utils.runOnClientThread(() -> {
            if (ModCore.instance.audioPlayer == null) return;
            ModCore.instance
                    .audioPlayer
                    .openAlPlayer
                    .soundPhysics()
                    .prime(
                            sound.isSoundAtPlayer() ? "" : sound.getTrimmedNpcName(),
                            sound.getPosition(),
                            sound.getReverb());
        });
    }

    /*    private SoundInstance playSoundAtCords(Vec3 position, SoundObject soundObject, SoundManager manager) {
        SoundEvent soundEvent = soundObject.getCustomSoundClass().soundEvent();

        var soundAtCords = new SoundAtCords(soundEvent, soundObject, position);

        manager.play(soundAtCords);
        return soundAtCords;
    }*/

    private boolean isOutsideReach(SoundObject soundObject, Player player, Vec3 npcPosition) {
        int soundObjectFallOff = soundObject.getFallOff();

        int squaredFalloff = soundObjectFallOff == 0
                ? config.getBlockCutOff() * config.getBlockCutOff()
                : soundObjectFallOff * soundObjectFallOff;
        return (player.position().distanceToSqr(npcPosition) >= squaredFalloff);
    }
}
