/*
 * Copyright © Team-VoW 2024-2025.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound;

import static com.wynnvp.wynncraftvp.utils.LineFormatter.formatToLineData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.sound.line.LineData;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import net.minecraft.world.phys.Vec3;

public class SoundsHandler {
    private static final String JSON_FILE = "sounds.json";
    private static final String GITHUB_JSON_URL =
            "https://raw.githubusercontent.com/Team-VoW/WynncraftVoiceProject/main/src/main/resources/sounds.json";
    private static final String BUNDLED_JSON = "sounds.json";

    private final HashMap<String, SoundObject> sounds;
    private final ExecutorService executor;

    private Gson gson;

    public SoundsHandler() {
        sounds = new HashMap<>();
        gson = new Gson();
        executor = Executors.newSingleThreadExecutor();

        // Run the update check and JSON loading in a separate thread
        executor.execute(() -> {
            if (shouldUpdateJson()) {
                downloadJson();
            }
            loadSoundsFromJson(getJsonStream());
        });
    }

    public HashMap<String, SoundObject> getSounds() {
        return sounds;
    }

    private void loadSoundsFromJson(InputStream inputStream) {
        try (InputStreamReader reader = new InputStreamReader(inputStream)) {
            Type dialogueListType = new TypeToken<List<DialogueData>>() {}.getType();
            List<DialogueData> dialogues = gson.fromJson(reader, dialogueListType);

            for (DialogueData dialogue : dialogues) {
                String message = dialogue.getDialogueLine();
                String fileName = dialogue.getFile();
                boolean movingSound = dialogue.isOnPlayer();
                Vec3 position = dialogue.getPos();
                int fallOff = dialogue.getFallOff();
                String npcName = dialogue.getNpc();
                Reverb environment = dialogue.getReverb();
                boolean stopSounds = dialogue.shouldStopSounds();
                System.out.println("Loaded sound: " + message);

                LineData lineData = formatToLineData(message);
                message = lineData.getSoundLine();
                sounds.put(
                        message,
                        new SoundObject(lineData.getNPCName(), fileName, movingSound, position, fallOff, stopSounds));
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load JSON file", e);
        }
    }


    private boolean shouldUpdateJson() {
        try {
            long lastUpdate = ModCore.config.lastSoundsUpdate;
            System.out.println("lastUpdate: " + lastUpdate);
            long latestCommitTime = fetchLatestCommitTime();
            System.out.println("latestCommitTime: " + latestCommitTime);
            System.out.println("shouldUpdateJson: " + (latestCommitTime > lastUpdate));

            return latestCommitTime > lastUpdate;
        } catch (Exception e) {
            return true; // If any error occurs, assume the JSON needs updating
        }
    }

    public static void main(String[] args) {
        SoundsHandler soundsHandler = new SoundsHandler();
    }

    private long fetchLatestCommitTime() {
        try {
            URL url = new URL(
                    "https://api.github.com/repos/Team-VoW/WynncraftVoiceProject/commits?path=src/main/resources/sounds.json");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/vnd.github.v3+json");

            if (connection.getResponseCode() == 200) {
                try (InputStream inputStream = connection.getInputStream();
                        InputStreamReader reader = new InputStreamReader(inputStream)) {
                    Gson gson = new Gson();
                    List<Map<String, Object>> commits =
                            gson.fromJson(reader, new TypeToken<List<Map<String, Object>>>() {}.getType());
                    Map<String, Object> commitMap =
                            (Map<String, Object>) commits.get(0).get("commit");
                    Map<String, Object> committerMap = (Map<String, Object>) commitMap.get("committer");
                    String date = (String) committerMap.get("date");
                    Instant instant = Instant.parse(date);

                    return instant.getEpochSecond();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void downloadJson() {
        try {
            System.out.println("downlading sounds");
            URL url = new URL(GITHUB_JSON_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() == 200) {
                try (InputStream inputStream = connection.getInputStream();
                        OutputStream outputStream = new FileOutputStream(JSON_FILE)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }

                    long latestCommitTime = fetchLatestCommitTime();
                    ModCore.config.lastSoundsUpdate = latestCommitTime;
                }
            } else {
                System.out.println("Failed to download JSON file: " + GITHUB_JSON_URL);
                System.out.println("Response code: " + connection.getResponseCode());
                System.out.println("Response message: " + connection.getResponseMessage());
            }
        } catch (IOException e) {
            System.out.println("Failed to download JSON file: " + GITHUB_JSON_URL);
            e.printStackTrace();
        }
    }

    private InputStream getJsonStream() {
        if (Files.exists(Paths.get(JSON_FILE))) {
            try {
                System.out.println("Using remote JSON file");
                return new FileInputStream(JSON_FILE);
            } catch (IOException e) {
                throw new RuntimeException("Failed to open local JSON file: " + JSON_FILE, e);
            }
        } else {
            System.out.println("Using bundled JSON file");
            InputStream bundledStream = this.getClass().getClassLoader().getResourceAsStream(BUNDLED_JSON);
            if (bundledStream != null) {
                return bundledStream;
            }
        }
        throw new IllegalStateException("No valid JSON file found!");
    }

    public Optional<SoundObject> get(String message) {
        return Optional.ofNullable(sounds.get(message));
    }
}
