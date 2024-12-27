/*
 * Copyright © Team-VoW 2024.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.player;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.sound.SoundObject;
import com.wynnvp.wynncraftvp.utils.Utils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class AudioPlayer {
    public final OpenAlPlayer openAlPlayer;

    public final AutoProgress autoProgress;

    public static final String AUDIO_FOLDER = "VOW_AUDIO";


    public AudioPlayer() {
        openAlPlayer = new OpenAlPlayer();
        autoProgress = new AutoProgress();
    }

    private void write(AudioData data) {
        openAlPlayer.playAudio(data);
        if (ModCore.config.autoProgress) {
            autoProgress.start(data.getAudioLengthMillis());
        }
    }

    public void playAudioFile(Path path) {
        openAlPlayer.stopAudio();
        Optional<AudioData> audioData = OggDecoder.getAudioData(path);

        if (audioData.isEmpty()) {
            Utils.sendMessage("Failed to load audio file: " + path.toString());
            return;
        }

        write(audioData.get());
    }

    public void play(SoundObject soundObject) {
        String audioFileName = soundObject.getId();

        Path audioFilePath = Paths.get(AUDIO_FOLDER, audioFileName + ".ogg");
        if (!audioFilePath.toFile().exists()) {
            Utils.sendMessage("Audio file not found: " + audioFilePath);
            return;
        }

        stopPlayingCurrentSound();

        openAlPlayer.updateSpeaker(
                soundObject.isSoundAtPlayer() ? "" : soundObject.getTrimmedNpcName(), soundObject.getPosition());

        playAudioFile(audioFilePath);
    }

    public void stopPlayingCurrentSound() {
        openAlPlayer.stopAudio();
    }
}
