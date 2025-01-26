/*
 * Copyright © Team-VoW 2024-2025.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.player;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.sound.SoundObject;
import com.wynnvp.wynncraftvp.utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

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
            // Use CompletableFuture for asynchronous remote audio fetching
            CompletableFuture.supplyAsync(() -> {
                String remoteUrl = ModCore.config.azureBlobLink + audioFileName + ".ogg";
                try {
                    ByteBuffer remoteAudioData = fetchRemoteAudio(remoteUrl);
                    if (remoteAudioData != null) {
                        // Switch to main thread for audio playback
                            stopPlayingCurrentSound();
                            openAlPlayer.updateSpeaker(
                                    soundObject.isSoundAtPlayer() ? "" : soundObject.getTrimmedNpcName(),
                                    soundObject.getPosition()
                            );
                            playAudioBuffer(remoteAudioData);
                    }
                } catch (IOException e) {
                    Utils.sendMessage("Failed to fetch remote audio file: " + remoteUrl);
                }
                return null;
            });
            return;
        }

        stopPlayingCurrentSound();
        openAlPlayer.updateSpeaker(
                soundObject.isSoundAtPlayer() ? "" : soundObject.getTrimmedNpcName(),
                soundObject.getPosition()
        );
        playAudioFile(audioFilePath);
    }

    private ByteBuffer fetchRemoteAudio(String urlString) throws IOException {
        URL url = new URL(urlString);
        byte[] audioBytes = url.openStream().readAllBytes(); // Read the audio file into a byte array
        return ByteBuffer.wrap(audioBytes); // Convert to ByteBuffer for OpenAL playback
    }

    public void playAudioBuffer(ByteBuffer audioData) {
        openAlPlayer.stopAudio();

        Optional<AudioData> audioOptional = OggDecoder.getAudioData(audioData);
        if (audioOptional.isEmpty()) {
            Utils.sendMessage("Failed to decode remote audio data.");
            return;
        }

        write(audioOptional.get());
    }

    public void stopPlayingCurrentSound() {
        openAlPlayer.stopAudio();
    }
}
