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
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SoundsHandler {
    private static final String JSON_FILE = "sounds.json";

    private final HashMap<String, SoundObject> sounds;
    private final Gson gson;

    private static final Logger LOGGER = LogManager.getLogger(SoundsHandler.class);

    public SoundsHandler() {
        sounds = new HashMap<>();
        gson = new Gson();
        ExecutorService executor = Executors.newSingleThreadExecutor();

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
            String lastModifiedStored = ModCore.config.lastsSoundsUpdateHeader;
            String lastModifiedNew = fetchLastModifiedHeader();

            if (lastModifiedNew != null && !lastModifiedNew.equals(lastModifiedStored)) {
                ModCore.config.lastsSoundsUpdateHeader = lastModifiedNew;
                ModCore.config.save();
                return true;
            }
        } catch (Exception e) {
            LOGGER.error("[Voices of Wynn] Failed to download determine if it should update the Json", e);
        }
        return false;
    }

    private String fetchLastModifiedHeader() {
        try {
            HttpURLConnection connection =
                    (HttpURLConnection) new URL(ModCore.config.getRemoteJsonLink()).openConnection();
            connection.setRequestMethod("HEAD");

            if (connection.getResponseCode() == 200) {
                return connection.getHeaderField("Last-Modified");
            }
        } catch (Exception e) {
            LOGGER.error("[Voices of Wynn] Failed to fetch lastModifiedHeader", e);
        }
        return null;
    }

    private void downloadJson() {
        try {
            LOGGER.info("Downloading updated sounds.json");
            HttpURLConnection connection =
                    (HttpURLConnection) new URL(ModCore.config.getRemoteJsonLink()).openConnection();
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() == 200) {
                try (InputStream inputStream = connection.getInputStream();
                        OutputStream outputStream = new FileOutputStream(JSON_FILE)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }
            } else {
                LOGGER.info(
                        "[Voices of Wynn] Failed to download JSON file. Response code: {}",
                        connection.getResponseCode());
            }
        } catch (IOException e) {
            LOGGER.error("[Voices of Wynn] Failed to download sounds.json file", e);
        }
    }

    private InputStream getJsonStream() {
        if (Files.exists(Paths.get(JSON_FILE))) {
            try {
                LOGGER.info("[Voices of Wynn] Using cached sounds.json file");
                return new FileInputStream(JSON_FILE);
            } catch (IOException e) {
                throw new RuntimeException("Failed to open local JSON file", e);
            }
        } else {
            LOGGER.info("[Voices of Wynn] Using bundled sounds.json file");
            InputStream bundledStream = this.getClass().getClassLoader().getResourceAsStream(JSON_FILE);
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
