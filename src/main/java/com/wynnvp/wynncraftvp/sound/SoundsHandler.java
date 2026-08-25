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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.GZIPInputStream;
import net.minecraft.world.phys.Vec3;

public class SoundsHandler {
    private static final String JSON_FILE = "sounds.json";

    /** Stored in place of an ETag when the CDN gave us none, forcing an unconditional re-download. */
    private static final String NO_VALIDATOR = "never";

    /** Stored by beta builds so the next normal build re-downloads the production sounds.json. */
    private static final String BETA_VALIDATOR = "beta";

    private volatile TreeMap<String, SoundObject> soundsTree = null;
    private final Gson gson;

    /**
     * Loads run here rather than on the calling thread. Kept for the whole session so a reload
     * triggered from the settings screen queues up behind the initial load instead of racing it.
     */
    private final ExecutorService executor = Executors.newSingleThreadExecutor(runnable -> {
        Thread thread = new Thread(runnable, "VoW Sounds Loader");
        thread.setDaemon(true);
        return thread;
    });

    public SoundsHandler() {
        gson = new GsonBuilder()
                .registerTypeAdapter(Reverb.class, new ReverbDeserializer())
                .create();
        reload();
    }

    /**
     * Re-reads sounds.json from whichever source the config currently points at.
     * <p>
     * Called when the custom sounds.json setting changes, so pointing the mod at a local file (or
     * back at the CDN copy) takes effect without a restart.
     */
    public void reload() {
        executor.execute(this::loadSounds);
    }

    private void loadSounds() {
        try {
            updateJsonIfNeeded();
            loadSoundsFromJson(getJsonStream());
        } catch (RuntimeException e) {
            if (e.getCause() instanceof com.google.gson.stream.MalformedJsonException) {
                ModCore.LOGGER.error("[Voices of Wynn] Detected corrupted JSON file, attempting to fix", e);
                handleCorruptedJsonFile();
            } else {
                ModCore.LOGGER.error("[Voices of Wynn] Failed to initialize sounds", e);
            }
        }
    }

    private void handleCorruptedJsonFile() {
        try {
            // Delete the corrupted file
            Files.deleteIfExists(Paths.get(JSON_FILE));
            ModCore.LOGGER.info("[Voices of Wynn] Deleted corrupted sounds.json file");

            // Re-download and try again
            downloadJson(null);
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

    @SuppressWarnings("ConstantValue")
    private String getEffectiveJsonUrl() {
        if (!BetaConfig.BETA_SOUNDS_JSON_URL.isEmpty()) {
            ModCore.LOGGER.info("[Voices of Wynn] Using beta sounds.json URL");
            return BetaConfig.BETA_SOUNDS_JSON_URL;
        }
        String remoteJsonLink = ModCore.config.getRemoteJsonLink();
        ModCore.LOGGER.info("[Voices of Wynn] Using configured sounds.json URL: {}", remoteJsonLink);
        return remoteJsonLink;
    }

    /**
     * Re-downloads sounds.json when the CDN has a copy newer than the cached one.
     * <p>
     * Freshness is decided by the {@code ETag} stored from the last successful download: jsDelivr
     * does not send {@code Last-Modified} at all, so the previous check saw a null validator and
     * treated the cache as stale on every launch.
     */
    @SuppressWarnings("ConstantValue")
    private void updateJsonIfNeeded() {
        // Don't touch the cache when the user points us at their own sounds.json.
        if (ModCore.config.isUseCustomSoundsJson()) {
            return;
        }

        if (!BetaConfig.BETA_SOUNDS_JSON_URL.isEmpty()) {
            // Beta builds always re-download. Invalidate the cached validator so switching back to
            // a normal build also forces a re-download of the production sounds.json.
            downloadJson(null);
            ModCore.config.soundsJsonETag = BETA_VALIDATOR;
            ModCore.config.save();
            return;
        }

        // Without a local copy there is nothing to revalidate against.
        String cachedETag = Files.exists(Paths.get(JSON_FILE)) ? ModCore.config.soundsJsonETag : null;
        downloadJson(cachedETag);
    }

    /**
     * Downloads sounds.json unless the CDN reports that {@code cachedETag} is still current.
     *
     * @param cachedETag validator from the last successful download, or null to download unconditionally
     * @return true if a new copy was written to disk
     */
    private boolean downloadJson(String cachedETag) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(getEffectiveJsonUrl()).openConnection();
            connection.setRequestMethod("GET");
            // HttpURLConnection neither asks for compression nor decodes it, and the CDN only
            // compresses on request, so both halves are done by hand here.
            connection.setRequestProperty("Accept-Encoding", "gzip");
            if (isUsableValidator(cachedETag)) {
                connection.setRequestProperty("If-None-Match", cachedETag);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_NOT_MODIFIED) {
                ModCore.LOGGER.info("[Voices of Wynn] Cached sounds.json is still up to date.");
                return false;
            }
            if (responseCode != HttpURLConnection.HTTP_OK) {
                ModCore.LOGGER.warn("[Voices of Wynn] Failed to download sounds.json. Response code: {}", responseCode);
                return false;
            }

            ModCore.LOGGER.info("[Voices of Wynn] Downloading updated sounds.json");
            writeBodyToJsonFile(connection);

            // Only recorded once the file is safely in place, so an interrupted download is retried
            // on the next launch rather than being remembered as current.
            String etag = connection.getHeaderField("ETag");
            ModCore.config.soundsJsonETag = etag != null ? etag : NO_VALIDATOR;
            ModCore.config.save();
            return true;
        } catch (IOException e) {
            ModCore.LOGGER.error("[Voices of Wynn] Failed to download sounds.json file", e);
            return false;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /**
     * Streams the response into sounds.json via a temporary file, so a download that dies partway
     * through leaves the previous copy intact instead of a truncated one.
     */
    private void writeBodyToJsonFile(HttpURLConnection connection) throws IOException {
        Path target = Paths.get(JSON_FILE);
        Path temp = target.resolveSibling(JSON_FILE + ".tmp");
        try {
            try (InputStream body = openBody(connection)) {
                Files.copy(body, temp, StandardCopyOption.REPLACE_EXISTING);
            }
            Files.move(temp, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            Files.deleteIfExists(temp);
            throw e;
        }
    }

    /** Unwraps the response body, honouring the compression we asked for. */
    private InputStream openBody(HttpURLConnection connection) throws IOException {
        InputStream body = connection.getInputStream();
        return "gzip".equalsIgnoreCase(connection.getContentEncoding()) ? new GZIPInputStream(body) : body;
    }

    private static boolean isUsableValidator(String etag) {
        return etag != null && !etag.isEmpty() && !NO_VALIDATOR.equals(etag) && !BETA_VALIDATOR.equals(etag);
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
