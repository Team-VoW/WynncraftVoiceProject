/*
 * Copyright © Team-VoW 2024-2025.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.downloader;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wynnvp.wynncraftvp.sound.player.AudioPlayer;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * The `AudioDownloader` class is responsible for downloading audio files from a remote server.
 * It compares the local metadata with the remote metadata to determine which files need to be downloaded.
 * It also handles the download process, retries, and cleanup of unused files.
 */
public class AudioDownloader {
    public static void main(String[] args) {
        AudioDownloader audioDownloader = new AudioDownloader(AudioPlayer.AUDIO_FOLDER);
        audioDownloader.checkToDownload();
    }

    private final String audioDir;
    private static final String BASE_URL =
            "https://cdn.jsdelivr.net/gh/Team-VoW/WynncraftVoiceProject@main/src/main/resources/assets/wynnvp/sounds/";
    private static final String AUDIO_MANIFEST =
            "https://cdn.jsdelivr.net/gh/Team-VoW/WynncraftVoiceProject@main/audio_manifest.json";

    private HashMap<String, AudioMetadata> metadataMap = new HashMap<>();
    private HashMap<String, AudioMetadata> remoteMetadata;
    private File audioFolder;

    private int maxRuns = 5;
    private int currentRun = 0;

    private Gson gson;

    private long downloadSize;

    public AudioDownloader(String audioFolder) {
        audioDir = audioFolder;
        gson = new Gson();
    }

    public void checkToDownload() {
        // Ensure the audio folder exists
        audioFolder = new File(audioDir);
        if (!audioFolder.exists()) {
            audioFolder.mkdirs();
        }

        // Start the async process
        CompletableFuture.runAsync(this::processAudioManifest);
        // processAudioManifest();
    }

    private void processAudioManifest() {
        try {
            // Fetch the manifest
            remoteMetadata = fetchAudioManifest();
            if (remoteMetadata == null) {
                System.err.println("Failed to fetch audio manifest on first attempt. Retrying in 20 seconds...");
                Thread.sleep(10000); // Wait for 10 sec

                // Retry fetching the manifest
                remoteMetadata = fetchAudioManifest();
                if (remoteMetadata == null) {
                    System.err.println("Failed to fetch audio manifest after retry.");
                    return;
                }
            }

            populateLocalMetadata();

            List<String> toDownload = getToDownload();

            if (toDownload.isEmpty()) {
                System.out.println("No files to download");
                return;
            }

            System.out.println("Downloading " + toDownload.size() + " files");

            long downloadSizeInMB = downloadSize / 1024 / 1024;
            System.out.println("Total download size: " + downloadSizeInMB + " MB");

            ToastManager.getInstance()
                    .displayTimedToast(
                            () -> {
                                StartDownloadQueue(toDownload);
                            },
                            10,
                            "Voices of Wynn Download. Size: " + downloadSizeInMB + " MB",
                            "Press N to cancel.");

        } catch (Exception e) {
            System.err.println("Error in audio manifest processing");
            e.printStackTrace();
        }
    }

    /**
     * Starts the download queue with the list of files to be downloaded.
     *
     * @param toDownload The list of file names to be downloaded.
     */
    private void StartDownloadQueue(List<String> toDownload) {
        DownloadQueue downloadQueue = new DownloadQueue(audioDir, BASE_URL, toDownload.size());

        List<DownloadTask> tasks = new ArrayList<>();
        tasks.addAll(toDownload.stream().map(id -> new DownloadTask(id, 1)).toList());

        downloadQueue.setOnQueueEmpty(() -> {
            System.out.println("Download complete");
            cleanUpUnusedFiles();

            populateLocalMetadata();
            List<String> failedToDownload = getToDownload();

            if (failedToDownload.isEmpty()) {
                System.out.println("All files downloaded successfully");
                return;
            }
            System.out.println("Failed to download " + failedToDownload.size() + "("
                    + (failedToDownload.size() * 100 / toDownload.size()) + "%");

            currentRun++;
            if (currentRun >= maxRuns) {
                System.out.println("Max runs reached, stopping download process");
                return;
            }

            // We go through the download process again to make sure we have all the files.
            CompletableFuture.runAsync(this::processAudioManifest);
        });

        downloadQueue.initializeQueue(tasks);

        downloadQueue.start();
    }

    /**
     * Retrieves a list of audio files that need to be downloaded.
     *
     * This method compares the local metadata with the remote metadata to determine which files
     * are missing or have different hashes, indicating that they need to be downloaded. It also
     * calculates the total size of the files to be downloaded, setting the downloadSize variable.
     *
     * @return A list of file names that need to be downloaded.
     */
    private List<String> getToDownload() {
        List<String> toDownload = new ArrayList<>();
        downloadSize = 0;

        // Populate the list of file names to download. If the file is not present or the hash is different, add it to
        // the list
        toDownload.addAll(remoteMetadata.entrySet().stream()
                .filter(entry -> {
                    String id = entry.getKey();
                    long size = entry.getValue().size();
                    AudioMetadata audioMetadata = entry.getValue();
                    AudioMetadata localMetadata = metadataMap.get(id);
                    boolean shouldDownload =
                            localMetadata == null || !localMetadata.hash().equals(audioMetadata.hash());
                    if (shouldDownload) {
                        downloadSize += size;
                    }
                    return shouldDownload;
                })
                .map(Map.Entry::getKey)
                .toList());

        return toDownload;
    }

    /**
     * Populates the local metadata map with the files in the audio folder.
     * It checks if the audio folder is empty and if it is not populates the metadata map with the files in the audio folder.
     */
    private void populateLocalMetadata() {
        audioFolder = new File(audioDir);
        if (!audioFolder.exists()) {
            audioFolder.mkdirs();
        }

        metadataMap.clear();

        // If the local metadata is empty check if the audio folder is empty. If it is not empty, populate the metadata
        // map with the files in the audio folder
        File[] localFiles = audioFolder.listFiles((dir, name) -> name.endsWith(".ogg"));
        if (localFiles != null) {
            for (File localFile : localFiles) {
                String fileName = localFile.getName().replace(".ogg", "");
                try {
                    String hash = computeFileHash(localFile);
                    metadataMap.put(fileName, new AudioMetadata(localFile.length(), hash));
                } catch (Exception e) {
                    System.err.println("Failed to compute hash for file: " + localFile.getName());
                    e.printStackTrace();
                }
            }
            System.out.println("Populated metadata map with " + metadataMap.size() + " files");
        }
    }

    /**
     * Fetches the audio manifest from the remote server.
     *
     * @return A map of audio metadata from the remote server.
     * @throws IOException If an I/O error occurs while fetching the manifest.
     */
    private HashMap<String, AudioMetadata> fetchAudioManifest() throws IOException {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(AUDIO_MANIFEST).openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                try (InputStream inputStream = connection.getInputStream();
                        InputStreamReader reader = new InputStreamReader(inputStream)) {
                    return gson.fromJson(reader, new TypeToken<HashMap<String, AudioMetadata>>() {}.getType());
                }
            }
            throw new IOException("Failed to fetch audio manifest: " + AUDIO_MANIFEST);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    /**
     * Computes the SHA-256 hash of a file. Which is a value that is unique to the file's contents.
     * This is used to compare files and determine if they are the same as the remote file.
     *
     * @param file The file to compute the hash for.
     * @return The SHA-256 hash of the file as a hexadecimal string.
     * @throws Exception If an error occurs while computing the hash.
     */
    private String computeFileHash(File file) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        try (InputStream fis = new FileInputStream(file)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }
        }
        byte[] hashBytes = digest.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    /**
     * Cleans up unused files in the audio folder by deleting files that are not present in the remote metadata.
     * These files are considered unused and can be safely deleted. They might have been present in a previous version
     * but are no longer needed.
     */
    private void cleanUpUnusedFiles() {
        if (remoteMetadata == null) {
            System.err.println("Remote metadata is null, cannot clean up unused files");
            return;
        }

        File audioFolder = new File(audioDir);
        File[] localFiles = audioFolder.listFiles((dir, name) -> name.endsWith(".ogg"));
        if (localFiles != null) {
            for (File localFile : localFiles) {
                String fileName = localFile.getName().replace(".ogg", "");

                // System.out.println(fileName);
                if (!remoteMetadata.containsKey(fileName)) {
                    System.out.println("Deleting unused file: " + localFile.getName());
                    localFile.delete();
                }
            }
        }
    }

    private record AudioMetadata(long size, String hash) {}
}
