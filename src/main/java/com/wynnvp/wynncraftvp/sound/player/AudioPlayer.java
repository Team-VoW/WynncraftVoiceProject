/*
 * Copyright © Team-VoW 2024-2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.player;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.config.BetaConfig;
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
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AudioPlayer {
    private static final Logger log = LoggerFactory.getLogger(AudioPlayer.class);
    private static final int REMOTE_FETCH_ATTEMPTS = 3;
    private static final Duration REMOTE_FETCH_TIMEOUT = Duration.ofSeconds(3);
    private static final Duration REMOTE_FETCH_RETRY_DELAY = Duration.ofMillis(150);
    public final OpenAlPlayer openAlPlayer;
    public final AutoProgress autoProgress;
    public static final String AUDIO_FOLDER = "VOW_AUDIO";
    private final HttpClient httpClient;
    private final AtomicLong playGeneration = new AtomicLong();

    public AudioPlayer() {
        openAlPlayer = new OpenAlPlayer();
        autoProgress = new AutoProgress();
        httpClient =
                HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();
    }

    private void write(AudioData data) {
        openAlPlayer.playAudio(data);
        if (ModCore.config.autoProgress) {
            float speedMultiplier = ModCore.config.getPlaybackSpeed();
            long adjustedDuration = Math.round(data.getAudioLengthMillis() / speedMultiplier);
            autoProgress.start(adjustedDuration);
        }
    }

    public void playAudioFile(Path path, SoundObject soundObject) {
        if (soundObject.shouldStopSounds()) openAlPlayer.stopAudio();
        Optional<AudioData> audioDataOptional = OggDecoder.getAudioData(path);

        if (audioDataOptional.isEmpty()) {
            Utils.sendMessage("Failed to load audio file: " + path.toString());
            return;
        }

        AudioData audioData = audioDataOptional.get();
        audioData.setSpeakerAndPos(
                soundObject.isSoundAtPlayer() ? "" : soundObject.getTrimmedNpcName(), soundObject.getPosition());
        audioData.setReverb(soundObject.getReverb());

        write(audioData);
    }

    @SuppressWarnings("ConstantValue")
    public void play(SoundObject soundObject) {
        long generation = playGeneration.incrementAndGet();
        String audioFileName = soundObject.getId();
        Path audioFilePath = Paths.get(AUDIO_FOLDER, audioFileName + ".ogg");

        if (ModCore.config.isUseCustomAudioPath()) {
            playFromCustomPath(audioFileName, soundObject, generation);
            return;
        }

        if (ModCore.config.downloadSounds
                && !BetaConfig.isBetaBuild()
                && audioFilePath.toFile().exists()) {
            playLocalFile(audioFilePath, soundObject);
            return;
        }

        playRemoteAudio(audioFileName, soundObject, generation);
    }

    private void playLocalFile(Path audioFilePath, SoundObject soundObject) {
        playAudioFile(audioFilePath, soundObject);
    }

    private void playFromCustomPath(String audioFileName, SoundObject soundObject, long generation) {
        String customPath = ModCore.config.getCustomAudioPath();
        boolean isURL = customPath.startsWith("http");

        if (isURL) {
            CompletableFuture.runAsync(() -> {
                ByteBuffer remoteAudioData = null;
                try {
                    remoteAudioData = fetchRemoteAudioWithRetry(customPath + audioFileName + ".ogg", generation);
                } catch (IOException | InterruptedException e) {
                    if (e instanceof InterruptedException) {
                        Thread.currentThread().interrupt();
                    }
                    if (!isCurrentGeneration(generation)) return;
                    Utils.sendMessage(
                            "Failed to fetch remote audio file: " + customPath + audioFileName
                                    + ". You are using a custom audio path. Please check your settings if this is an accident.");
                }
                if (remoteAudioData != null && isCurrentGeneration(generation)) {
                    playAudioBuffer(remoteAudioData, soundObject);
                }
            });
            return;
        }

        Path customAudioPath = Paths.get(customPath, audioFileName + ".ogg");
        if (customAudioPath.toFile().exists()) {
            playLocalFile(customAudioPath, soundObject);
        } else {
            Utils.sendMessage("Failed to load custom audio file: " + customAudioPath
                    + ". Please check your settings. You are using a custom audio path.");
        }
    }

    private void playRemoteAudio(String audioFileName, SoundObject soundObject, long generation) {
        CompletableFuture.runAsync(() -> {
            List<String> allRemoteUrls = getRemoteUrls();

            ByteBuffer remoteAudioData = null;

            for (String url : allRemoteUrls) {
                if (!isCurrentGeneration(generation)) return;
                try {
                    remoteAudioData = fetchRemoteAudioWithRetry(url + audioFileName + ".ogg", generation);
                    if (remoteAudioData == null) return;
                    break;
                } catch (IOException | InterruptedException e) {
                    if (e instanceof InterruptedException) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                    if (!isCurrentGeneration(generation)) return;
                    log.info("[Voices of Wynn] Failed to fetch remote audio file: {}{}.", url, audioFileName);
                }
            }

            if (remoteAudioData != null && isCurrentGeneration(generation)) {
                playAudioBuffer(remoteAudioData, soundObject);
            } else {
                Utils.sendMessage(
                        "Failed to fetch remote audio file for " + audioFileName
                                + ". If this keeps happening go into your settings, enable the download sounds option and restart your game.");
            }
        });
    }

    private ByteBuffer fetchRemoteAudioWithRetry(String urlString, long generation)
            throws IOException, InterruptedException {
        for (int attempt = 1; attempt < REMOTE_FETCH_ATTEMPTS; attempt++) {
            if (!isCurrentGeneration(generation)) {
                return null;
            }

            try {
                return fetchRemoteAudio(urlString);
            } catch (IOException e) {
                if (!shouldRetryFetch(e)) {
                    throw e;
                }

                log.debug(
                        "[Voices of Wynn] Retrying remote audio fetch after transient failure: {} (attempt {}/{})",
                        urlString,
                        attempt,
                        REMOTE_FETCH_ATTEMPTS);
                Thread.sleep(REMOTE_FETCH_RETRY_DELAY.toMillis() * attempt);
            }
        }

        return isCurrentGeneration(generation) ? fetchRemoteAudio(urlString) : null;
    }

    private ByteBuffer fetchRemoteAudio(String urlString) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlString))
                .timeout(REMOTE_FETCH_TIMEOUT)
                .GET()
                .build();

        HttpResponse<byte[]> response = httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());

        if (response.statusCode() == 200) {
            return ByteBuffer.wrap(response.body());
        }

        throw new RemoteAudioFetchException(response.statusCode());
    }

    private boolean shouldRetryFetch(IOException exception) {
        if (exception instanceof RemoteAudioFetchException remoteException) {
            int statusCode = remoteException.statusCode();
            return statusCode == 408 || statusCode == 429 || statusCode >= 500;
        }

        return true;
    }

    private boolean isCurrentGeneration(long generation) {
        return generation == playGeneration.get();
    }

    private List<String> urls = null;

    @SuppressWarnings("ConstantValue")
    private List<String> getRemoteUrls() {
        if (urls != null) return urls;

        if (!BetaConfig.BETA_SOUNDS_URL.isEmpty()) {
            urls = List.of(BetaConfig.BETA_SOUNDS_URL);
            return urls;
        }

        urls = new ArrayList<>(ModCore.config.urls);
        String fastestServer = ModCore.config.azureBlobLink;
        urls.remove(fastestServer);
        urls.addFirst(fastestServer);
        return urls;
    }

    public void playAudioBuffer(ByteBuffer audioData, SoundObject soundObject) {
        if (soundObject.shouldStopSounds()) openAlPlayer.stopAudio();

        Optional<AudioData> audioDataOptional = OggDecoder.getAudioData(audioData);
        if (audioDataOptional.isEmpty()) {
            Utils.sendMessage("Failed to decode remote audio data.");
            return;
        }

        AudioData audio = audioDataOptional.get();
        audio.setSpeakerAndPos(
                soundObject.isSoundAtPlayer() ? "" : soundObject.getTrimmedNpcName(), soundObject.getPosition());
        audio.setReverb(soundObject.getReverb());

        write(audio);
    }

    public void stopAudio() {
        playGeneration.incrementAndGet();
        openAlPlayer.stopAudio();
    }

    private static class RemoteAudioFetchException extends IOException {
        private final int statusCode;

        private RemoteAudioFetchException(int statusCode) {
            super("Failed to fetch audio: " + statusCode);
            this.statusCode = statusCode;
        }

        private int statusCode() {
            return statusCode;
        }
    }
}
