/*
 * Copyright © Team-VoW 2024-2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound;

import static com.wynnvp.wynncraftvp.utils.LineFormatter.formatToLineData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.config.BetaConfig;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import net.minecraft.world.phys.Vec3;

public class SoundsHandler {
    private static final String JSON_FILE = "sounds.json";

    private volatile TreeMap<String, SoundObject> soundsTree = null;
    private final Gson gson;

    public SoundsHandler() {
        gson = new GsonBuilder()
                .registerTypeAdapter(Reverb.class, new ReverbDeserializer())
                .create();
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            try {
                if (shouldUpdateJson()) {
                    ModCore.LOGGER.info("[Voices of Wynn] Updating JSON audio file.");
                    downloadJson();
                }
                loadSoundsFromJson(getJsonStream());
            } catch (RuntimeException e) {
                if (e.getCause() instanceof com.google.gson.stream.MalformedJsonException) {
                    ModCore.LOGGER.error("[Voices of Wynn] Detected corrupted JSON file, attempting to fix", e);
                    handleCorruptedJsonFile();
                } else {
                    ModCore.LOGGER.error("[Voices of Wynn] Failed to initialize sounds", e);
                }
            }
        });
    }

    private void handleCorruptedJsonFile() {
        try {
            // Delete the corrupted file
            Files.deleteIfExists(Paths.get(JSON_FILE));
            ModCore.LOGGER.info("[Voices of Wynn] Deleted corrupted sounds.json file");

            // Re-download and try again
            downloadJson();
            loadSoundsFromJson(getJsonStream());
            ModCore.LOGGER.info("[Voices of Wynn] Successfully recovered from corrupted JSON file");
        } catch (Exception e) {
            ModCore.LOGGER.error("[Voices of Wynn] Failed to recover from corrupted JSON file", e);
            // Try to load from bundled resource as last resort
            try {
                InputStream bundledStream = this.getClass().getClassLoader().getResourceAsStream(JSON_FILE);
                if (bundledStream != null) {
                    loadSoundsFromJson(bundledStream);
                    ModCore.LOGGER.info("[Voices of Wynn] Successfully loaded bundled sounds.json as fallback");
                }
            } catch (Exception ex) {
                ModCore.LOGGER.error("[Voices of Wynn] All recovery attempts failed", ex);
            }
        }
    }

    public Map<String, SoundObject> getSounds() {
        TreeMap<String, SoundObject> tree = soundsTree;
        return tree != null ? Collections.unmodifiableMap(tree) : Map.of();
    }

    private void loadSoundsFromJson(InputStream inputStream) {
        try (InputStreamReader reader = new InputStreamReader(inputStream)) {
            Type dialogueListType = new TypeToken<List<DialogueData>>() {}.getType();
            List<DialogueData> dialogues = gson.fromJson(reader, dialogueListType);

            HashMap<String, SoundObject> loaded = new HashMap<>();
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
                loaded.put(
                        message,
                        new SoundObject(
                                lineData.getNPCName(),
                                fileName,
                                movingSound,
                                position,
                                fallOff,
                                stopSounds,
                                environment));
            }
            soundsTree = new TreeMap<>(loaded);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load JSON file", e);
        }
    }

    private String getEffectiveJsonUrl() {
        if (BetaConfig.isBetaBuild() && !BetaConfig.BETA_SOUNDS_JSON_URL.isEmpty()) {
            return BetaConfig.BETA_SOUNDS_JSON_URL;
        }
        return getEffectiveJsonUrl();
    }

    private boolean shouldUpdateJson() {
        // Beta builds always re-download. Invalidate the cached header so switching
        // back to a normal build will also force a re-download of the production sounds.json.
        if (BetaConfig.isBetaBuild() && !BetaConfig.BETA_SOUNDS_JSON_URL.isEmpty()) {
            ModCore.config.lastsSoundsUpdateHeader = "beta";
            ModCore.config.save();
            return true;
        }

        // Don't update if using custom sounds.json
        if (ModCore.config.isUseCustomSoundsJson()) {
            return false;
        }

        if (!Files.exists(Paths.get(JSON_FILE))) {
            return true;
        }
        try {
            String lastModifiedStored = ModCore.config.lastsSoundsUpdateHeader;
            String lastModifiedNew = fetchLastModifiedHeader();

            if (lastModifiedNew == null || !lastModifiedNew.equals(lastModifiedStored)) {
                ModCore.config.lastsSoundsUpdateHeader = lastModifiedNew;
                ModCore.config.save();
                return true;
            }
        } catch (Exception e) {
            ModCore.LOGGER.error("[Voices of Wynn] Failed to download determine if it should update the Json", e);
        }
        return false;
    }

    private String fetchLastModifiedHeader() {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(getEffectiveJsonUrl()).openConnection();
            connection.setRequestMethod("HEAD");

            if (connection.getResponseCode() == 200) {
                return connection.getHeaderField("Last-Modified");
            }
        } catch (Exception e) {
            ModCore.LOGGER.error("[Voices of Wynn] Failed to fetch lastModifiedHeader", e);
        }
        return null;
    }

    private void downloadJson() {
        try {
            ModCore.LOGGER.info("Downloading updated sounds.json");
            HttpURLConnection connection = (HttpURLConnection) new URL(getEffectiveJsonUrl()).openConnection();
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
                ModCore.LOGGER.info(
                        "[Voices of Wynn] Failed to download JSON file. Response code: {}",
                        connection.getResponseCode());
            }
        } catch (IOException e) {
            ModCore.LOGGER.error("[Voices of Wynn] Failed to download sounds.json file", e);
        }
    }

    private InputStream getJsonStream() {
        // Check for custom sounds.json path first
        if (ModCore.config.isUseCustomSoundsJson()) {
            String customPath = ModCore.config.getCustomSoundsJsonPath();
            if (customPath != null && !customPath.isEmpty()) {
                if (customPath.startsWith("http://") || customPath.startsWith("https://")) {
                    try {
                        ModCore.LOGGER.info("[Voices of Wynn] Using custom sounds.json URL: {}", customPath);
                        return new URL(customPath).openStream();
                    } catch (IOException e) {
                        ModCore.LOGGER.error(
                                "[Voices of Wynn] Failed to load custom sounds.json from URL: {}", customPath, e);
                        ModCore.LOGGER.info("[Voices of Wynn] Falling back to default sounds.json");
                    }
                } else if (Files.exists(Paths.get(customPath))) {
                    try {
                        ModCore.LOGGER.info("[Voices of Wynn] Using custom sounds.json file from: {}", customPath);
                        return new FileInputStream(customPath);
                    } catch (IOException e) {
                        ModCore.LOGGER.error(
                                "[Voices of Wynn] Failed to load custom sounds.json from: {}", customPath, e);
                        ModCore.LOGGER.info("[Voices of Wynn] Falling back to default sounds.json");
                    }
                } else {
                    ModCore.LOGGER.warn(
                            "[Voices of Wynn] Custom sounds.json enabled but path is invalid or file doesn't exist: {}",
                            customPath);
                    ModCore.LOGGER.info("[Voices of Wynn] Falling back to default sounds.json");
                }
            }
        }

        // Default behavior
        if (Files.exists(Paths.get(JSON_FILE))) {
            try {
                ModCore.LOGGER.info("[Voices of Wynn] Using cached sounds.json file");
                return new FileInputStream(JSON_FILE);
            } catch (IOException e) {
                throw new RuntimeException("Failed to open local JSON file", e);
            }
        } else {
            ModCore.LOGGER.info("[Voices of Wynn] Using bundled sounds.json file");
            InputStream bundledStream = this.getClass().getClassLoader().getResourceAsStream(JSON_FILE);
            if (bundledStream != null) {
                return bundledStream;
            } else {
                ModCore.LOGGER.error("[Voices of Wynn] COULD NOT FIND BUNDLED SOUNDS.JSON FILE");
            }
        }
        throw new IllegalStateException("No valid JSON file found!");
    }

    /**
     * Returns the single manifest entry whose key starts with {@code prefix}, or empty if
     * there are zero or more than one such entries (ambiguous).
     */
    public Optional<Map.Entry<String, SoundObject>> findEarlyMatch(String prefix) {
        TreeMap<String, SoundObject> tree = soundsTree;
        if (tree == null) return Optional.empty();

        Map.Entry<String, SoundObject> ceiling = tree.ceilingEntry(prefix);
        if (ceiling == null || !ceiling.getKey().startsWith(prefix)) return Optional.empty();

        // Ambiguous if a second key also starts with the prefix
        String nextKey = tree.higherKey(ceiling.getKey());
        if (nextKey != null && nextKey.startsWith(prefix)) return Optional.empty();

        return Optional.of(ceiling);
    }

    public Optional<SoundObject> get(String message) {
        TreeMap<String, SoundObject> tree = soundsTree;
        if (tree == null) return Optional.empty();
        return Optional.ofNullable(tree.get(message));
    }
}
