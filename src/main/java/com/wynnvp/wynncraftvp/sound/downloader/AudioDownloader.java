package com.wynnvp.wynncraftvp.sound.downloader;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wynnvp.wynncraftvp.sound.SoundObject;
import com.wynnvp.wynncraftvp.sound.SoundsHandler;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AudioDownloader {

    public static void main(String[] args) {
        SoundsHandler soundsHandler = new SoundsHandler();

        var audio = soundsHandler.getSounds();
        System.out.println(audio.size());
        new AudioDownloader(soundsHandler.getSounds());
    }

    private static final String AUDIO_FOLDER = "VOW_AUDIO";
    private static final String METADATA_FILE = "audio_metadata.json";
    private static final String BASE_URL = "https://cdn.jsdelivr.net/gh/Team-VoW/WynncraftVoiceProject@main/src/main/resources/assets/wynnvp/sounds/";
    private static final String AUDIO_MANIFEST = "https://cdn.jsdelivr.net/gh/Team-VoW/WynncraftVoiceProject@main/audio_manifest.json";

    private ConcurrentHashMap<String, AudioMetadata> metadataMap;
    private final HashMap<String, SoundObject> soundObjects;

    public AudioDownloader(HashMap<String, SoundObject> soundObjects) {
        this.soundObjects = soundObjects;

        // Start the async process
        //CompletableFuture.runAsync(this::processAudioManifest);
        processAudioManifest();
    }

    private void processAudioManifest() {
        try {
            // Ensure the audio folder exists
            File audioFolder = new File(AUDIO_FOLDER);
            if (!audioFolder.exists()) {
                audioFolder.mkdirs();
            }

            // Load local metadata in the background thread
            this.metadataMap = new ConcurrentHashMap<>(loadLocalMetadata());

            // Fetch the manifest
            HashMap<String, AudioMetadata> remoteMetadata = fetchAudioManifest();
            if (remoteMetadata == null) {
                System.err.println("Failed to fetch audio manifest");
                return;
            }

            // Process each sound object
            soundObjects.forEach((line, soundObject) -> {
                try {
                    processAudioFile(soundObject.getId(), soundObject, remoteMetadata);
                } catch (Exception e) {
                    System.err.println("Error processing audio file: " + soundObject.getId());
                    e.printStackTrace();
                }
            });

            // Clean up unused files
            cleanUpUnusedFiles();

            // Save final metadata
            saveLocalMetadata();

        } catch (Exception e) {
            System.err.println("Error in audio manifest processing");
            e.printStackTrace();
        }
    }

    private void processAudioFile(String id, SoundObject soundObject, HashMap<String, AudioMetadata> remoteMetadata) throws Exception {
        String fileName = soundObject.getId() + ".ogg";
        File localFile = new File(AUDIO_FOLDER, fileName);
        AudioMetadata localMetadata = metadataMap.get(id);
        AudioMetadata remoteMetadataItem = remoteMetadata.get(id);

        if (needsDownload(localFile, localMetadata, remoteMetadataItem)) {
            System.out.println("Downloading: " + fileName);
            downloadFile(BASE_URL + fileName, localFile);
            AudioMetadata newMetadata = new AudioMetadata(localFile.length(), computeFileHash(localFile));
            metadataMap.put(id, newMetadata);
            saveLocalMetadata();
        } else {
            System.out.println("File already exists and matches hash: " + fileName);
        }
    }

    private boolean needsDownload(File localFile, AudioMetadata localMetadata, AudioMetadata remoteMetadata) {
        return !localFile.exists() ||
                localMetadata == null ||
                remoteMetadata == null ||
                !localMetadata.hash().equals(remoteMetadata.hash());
    }

    private HashMap<String, AudioMetadata> fetchAudioManifest() throws IOException {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(AUDIO_MANIFEST).openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                try (InputStream inputStream = connection.getInputStream();
                     InputStreamReader reader = new InputStreamReader(inputStream)) {
                    return new Gson().fromJson(reader,
                            new TypeToken<HashMap<String, AudioMetadata>>() {
                            }.getType());
                }
            }
            throw new IOException("Failed to fetch audio manifest: " + AUDIO_MANIFEST);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private void downloadFile(String fileURL, File destination) throws IOException {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(fileURL).openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                try (InputStream inputStream = connection.getInputStream();
                     FileOutputStream outputStream = new FileOutputStream(destination)) {
                    byte[] buffer = new byte[8192];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }
            } else {
                throw new IOException("Failed to download file: " + fileURL);
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

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

    private void cleanUpUnusedFiles() {
        File audioFolder = new File(AUDIO_FOLDER);
        File[] localFiles = audioFolder.listFiles((dir, name) -> name.endsWith(".ogg"));
        if (localFiles != null) {
            for (File localFile : localFiles) {
                String fileName = localFile.getName().replace(".ogg", "");
                if (!soundObjects.containsKey(fileName)) {
                    System.out.println("Deleting unused file: " + localFile.getName());
                    localFile.delete();
                    metadataMap.remove(fileName);
                }
            }
        }
    }

    private Map<String, AudioMetadata> loadLocalMetadata() {
        File metadataFile = new File(AUDIO_FOLDER, METADATA_FILE);
        if (metadataFile.exists()) {
            try (Reader reader = new FileReader(metadataFile)) {
                return new Gson().fromJson(reader,
                        new TypeToken<Map<String, AudioMetadata>>() {
                        }.getType());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new HashMap<>();
    }

    private synchronized void saveLocalMetadata() {
        File metadataFile = new File(AUDIO_FOLDER, METADATA_FILE);
        try (Writer writer = new FileWriter(metadataFile)) {
            new Gson().toJson(metadataMap, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private record AudioMetadata(long size, String hash) {
    }
}