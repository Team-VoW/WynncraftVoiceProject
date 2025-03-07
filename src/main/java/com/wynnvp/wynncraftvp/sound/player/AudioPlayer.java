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
import java.util.List;
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

    public void playAudioFile(Path path, SoundObject soundObject) {
        if (soundObject.shouldStopSounds()) openAlPlayer.stopAudio();
        Optional<AudioData> audioData = OggDecoder.getAudioData(path);

        if (audioData.isEmpty()) {
            Utils.sendMessage("Failed to load audio file: " + path.toString());
            return;
        }
        audioData
                .get()
                .setSpeakerAndPos(
                        soundObject.isSoundAtPlayer() ? "" : soundObject.getTrimmedNpcName(),
                        soundObject.getPosition());

        write(audioData.get());
    }

    public void play(SoundObject soundObject) {
        String audioFileName = soundObject.getId();
        Path audioFilePath = Paths.get(AUDIO_FOLDER, audioFileName + ".ogg");

        if (ModCore.config.downloadSounds && audioFilePath.toFile().exists()) {
            playLocalFile(audioFilePath, soundObject);
            return;
        }

        playRemoteAudio(audioFileName, soundObject);
    }

    private void playLocalFile(Path audioFilePath, SoundObject soundObject) {
        playAudioFile(audioFilePath, soundObject);
    }

    private void playRemoteAudio(String audioFileName, SoundObject soundObject) {
        // Use CompletableFuture for asynchronous remote audio fetching
        CompletableFuture.supplyAsync(() -> {
            List<String> allRemoteUrls = ModCore.config.urls;
            String fastestServer = ModCore.config.azureBlobLink;
            ByteBuffer remoteAudioData = null;
            allRemoteUrls.remove(fastestServer);
            allRemoteUrls.addFirst(fastestServer);

            for (String url : allRemoteUrls) {
                try {
                    remoteAudioData = fetchRemoteAudio(url + audioFileName + ".ogg");

                    break; // Exit loop if fetch is successful
                } catch (IOException e) {
                    Utils.sendMessage("Failed to fetch remote audio file: " + url + audioFileName
                            + ".ogg. If this keeps happening restart your game and report it.");
                }
            }

            if (remoteAudioData != null) {
                playAudioBuffer(remoteAudioData, soundObject);
            } else {
                Utils.sendMessage(
                        "Failed to fetch remote audio file from all URLs. Please report this in our discord.");
            }
            return null;
        });
    }

    private ByteBuffer fetchRemoteAudio(String urlString) throws IOException {
        URL url = new URL(urlString);
        byte[] audioBytes = url.openStream().readAllBytes(); // Read the audio file into a byte array
        return ByteBuffer.wrap(audioBytes); // Convert to ByteBuffer for OpenAL playback
    }

    public void playAudioBuffer(ByteBuffer audioData, SoundObject soundObject) {
        if (soundObject.shouldStopSounds()) openAlPlayer.stopAudio();

        Optional<AudioData> audioOptional = OggDecoder.getAudioData(audioData);
        if (audioOptional.isEmpty()) {
            Utils.sendMessage("Failed to decode remote audio data.");
            return;
        }
        audioOptional
                .get()
                .setSpeakerAndPos(
                        soundObject.isSoundAtPlayer() ? "" : soundObject.getTrimmedNpcName(),
                        soundObject.getPosition());

        write(audioOptional.get());
    }
}
