/*
 * Copyright © Team-VoW 2024-2025.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.player;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.sound.SoundObject;
import com.wynnvp.wynncraftvp.utils.Utils;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class AudioPlayer {
    public final OpenAlPlayer openAlPlayer;
    public final AutoProgress autoProgress;
    public static final String AUDIO_FOLDER = "VOW_AUDIO";
    private final HttpClient httpClient;

    public AudioPlayer() {
        openAlPlayer = new OpenAlPlayer();
        autoProgress = new AutoProgress();
        httpClient =
                HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();
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
        CompletableFuture.runAsync(() -> {
            List<String> allRemoteUrls = getRemoteUrls();

            ByteBuffer remoteAudioData = null;

            for (String url : allRemoteUrls) {
                try {
                    remoteAudioData = fetchRemoteAudio(url + audioFileName + ".ogg");
                    break;
                } catch (IOException | InterruptedException e) {
                    Utils.sendMessage(
                            "Failed to fetch remote audio file: " + url + audioFileName
                                    + ". If this keeps happening go into your settings, enable the download sounds option and restart your game.");
                }
            }

            if (remoteAudioData != null) {
                playAudioBuffer(remoteAudioData, soundObject);
            } else {
                Utils.sendMessage(
                        "Failed to fetch remote audio file from all URLs. Please report this in our discord.");
            }
        });
    }

    private ByteBuffer fetchRemoteAudio(String urlString) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlString))
                .timeout(Duration.ofSeconds(3))
                .GET()
                .build();

        HttpResponse<byte[]> response = httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());

        if (response.statusCode() == 200) {
            return ByteBuffer.wrap(response.body());
        }

        throw new IOException("Failed to fetch audio: " + response.statusCode());
    }

    private List<String> urls = null;

    private List<String> getRemoteUrls() {
        if (urls != null) return urls;

        urls = new ArrayList<>(ModCore.config.urls);
        String fastestServer = ModCore.config.azureBlobLink;
        urls.remove(fastestServer);
        urls.addFirst(fastestServer);
        return urls;
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
