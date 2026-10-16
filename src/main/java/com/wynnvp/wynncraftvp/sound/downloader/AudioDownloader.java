/*
 * Copyright © Team-VoW 2024-2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.downloader;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.config.BetaConfig;
import com.wynnvp.wynncraftvp.config.VOWAutoConfig;
import com.wynnvp.wynncraftvp.sound.player.AudioPlayer;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.zip.GZIPInputStream;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The AudioDownloader class is responsible for downloading audio files from a remote server.
 * It compares the local metadata with the remote metadata to determine which files need to be downloaded.
 * It also handles the download process, retries, and cleanup of unused files.
 */
public class AudioDownloader {
    public static void main(String[] args) {
        AudioDownloader audioDownloader = new AudioDownloader(AudioPlayer.AUDIO_FOLDER);
        audioDownloader.checkToDownload();
    }

    /** Sits inside the audio folder; skipped by the .ogg filters that scan and clean that folder. */
    private static final String HASH_CACHE_FILE = ".vow_hash_cache.json";

    /** Pre-compressed manifest. Blob storage will not compress on the fly, so this is its own object. */
    private static final String MANIFEST_FILE = "audio_manifest.json.gz";

    /**
     * The uncompressed manifest every released version before this one reads. Still uploaded, and
     * still the fallback here, so a client and a manifest from different releases keep working.
     */
    private static final String LEGACY_MANIFEST_FILE = "audio_manifest.json";

    /** Local copy of the compressed manifest, kept so an unchanged one need not be downloaded again. */
    private static final String MANIFEST_CACHE_FILE = ".vow_manifest_cache.json.gz";

    private final String audioDir;

    @SuppressWarnings("ConstantValue")
    private static String getEffectiveBaseUrl() {
        if (!BetaConfig.BETA_SOUNDS_URL.isEmpty()) {
            String betaBaseUrl = ensureTrailingSlash(BetaConfig.BETA_SOUNDS_URL);
            LOGGER.info("Using beta sounds base URL");
            return betaBaseUrl;
        }
        String baseUrl = ensureTrailingSlash(ModCore.config.audioBaseUrl);
        LOGGER.info("Using sounds base URL: {}", baseUrl);
        return baseUrl;
    }

    private static String ensureTrailingSlash(String baseUrl) {
        return baseUrl.endsWith("/") ? baseUrl : baseUrl + "/";
    }

    // The manifest always comes from R2 rather than following audioBaseUrl: the jsDelivr mirror
    // does not carry it.
    private String getManifestUrl() {
        return VOWAutoConfig.R2_SOUNDS_URL + MANIFEST_FILE;
    }

    private String getLegacyManifestUrl() {
        return VOWAutoConfig.R2_SOUNDS_URL + LEGACY_MANIFEST_FILE;
    }

    private final HashMap<String, AudioMetadata> metadataMap = new HashMap<>();
    private HashMap<String, AudioMetadata> remoteMetadata;
    private File audioFolder;

    private final int maxRuns = 2;
    private int currentRun = 0;

    /**
     * The minimum size of the update the confirmation dialog to appear.
     * Any update smaller than this will automatically install
     */
    private final int minSizeForConfirmation = 50;

    private final Gson gson;

    private long downloadSize;

    private static final Logger LOGGER = LoggerFactory.getLogger("VoW Audio Downloader");

    public AudioDownloader(String audioFolder) {
        audioDir = audioFolder;
        gson = new Gson();
    }

    private boolean hasRan = false;

    /** Guards against a second check starting while one is still working through the manifest. */
    private final AtomicBoolean running = new AtomicBoolean();

    public void checkIfHasNot() {
        if (hasRan || !downloadsAllowed()) {
            return;
        }

        hasRan = true;
        checkToDownload();
    }

    /**
     * Beta builds stream their audio and never download it. The beta CDN holds whatever is being tested
     * at the moment, so a cache filled from it would outlive the beta and be served to a normal build as
     * if it were production audio.
     */
    private boolean downloadsAllowed() {
        if (!ModCore.config.downloadSounds) {
            return false;
        }
        if (BetaConfig.isBetaBuild()) {
            LOGGER.info("Beta build: sounds are streamed, skipping the audio download");
            return false;
        }
        return true;
    }

    /**
     * Runs a check straight away, even if this session has already run one.
     * <p>
     * This is what the settings screen calls when the download option is switched on, so the download
     * starts (and its confirmation toast appears) there and then rather than on the next launch.
     */
    public void checkNow() {
        if (!downloadsAllowed()) {
            return;
        }

        hasRan = true;
        currentRun = 0;
        checkToDownload();
    }

    public void checkToDownload() {
        if (!downloadsAllowed()) {
            return;
        }

        if (!running.compareAndSet(false, true)) {
            LOGGER.info("An audio download check is already in progress, ignoring this one");
            return;
        }

        // Ensure the audio folder exists
        audioFolder = new File(audioDir);
        if (!audioFolder.exists()) {
            if (!audioFolder.mkdirs()) {
                LOGGER.error("Failed to create audio folder: " + audioFolder.getAbsolutePath());
                finishRun();
                return;
            }
        }

        // Start the async process
        CompletableFuture.runAsync(this::processAudioManifest);
        // processAudioManifest();
    }

    /** Marks the run as over, so a later {@link #checkNow()} is allowed to start a fresh one. */
    private void finishRun() {
        running.set(false);
    }

    private void processAudioManifest() {
        try {
            // Fetch the manifest
            remoteMetadata = fetchAudioManifest();
            if (remoteMetadata == null) {
                LOGGER.error("Failed to fetch audio manifest on first attempt. Retrying in 20 seconds...");
                Thread.sleep(10000); // Wait for 10 sec

                // Retry fetching the manifest
                remoteMetadata = fetchAudioManifest();
                if (remoteMetadata == null) {
                    LOGGER.error("Failed to fetch audio manifest after retry.");
                    finishRun();
                    return;
                }
            }

            populateLocalMetadata();

            List<String> toDownload = getToDownload();

            if (toDownload.isEmpty()) {
                LOGGER.info("No files to download. Up to date");
                finishRun();
                return;
            }

            LOGGER.info("Downloading " + toDownload.size() + " files");

            long downloadSizeInMB = downloadSize / 1024 / 1024;
            LOGGER.info("Total download size: " + downloadSizeInMB + " MB");

            if (downloadSizeInMB < minSizeForConfirmation) {
                StartDownloadQueue(toDownload);
                return;
            }

            ToastManager.getInstance()
                    .displayTimedToast(
                            () -> StartDownloadQueue(toDownload),
                            // Declining ends the run, so switching the setting off and on again asks anew.
                            this::finishRun,
                            10,
                            "Voices of Wynn Download. Size: " + downloadSizeInMB + " MB",
                            "Press N to cancel.");

        } catch (Exception e) {
            LOGGER.error("Error in audio manifest processing");
            e.printStackTrace();
            finishRun();
        }
    }

    /**
     * Starts the download queue with the list of files to be downloaded.
     *
     * @param toDownload The list of file names to be downloaded.
     */
    private void StartDownloadQueue(List<String> toDownload) {
        DownloadQueue downloadQueue = new DownloadQueue(audioDir, getEffectiveBaseUrl(), toDownload.size());

        List<DownloadTask> tasks = new ArrayList<>(
                toDownload.stream().map(id -> new DownloadTask(id, 1)).toList());

        downloadQueue.setOnQueueEmpty(() -> {
            LOGGER.info("Download complete");
            cleanUpUnusedFiles();

            populateLocalMetadata();
            List<String> failedToDownload = getToDownload();

            if (failedToDownload.isEmpty()) {
                LOGGER.info("All files downloaded successfully");
                ToastManager.getInstance()
                        .displayToast(
                                Component.literal("Voices of Wynn audio downloaded successfully"),
                                Component.literal("All files downloaded successfully"));
                finishRun();
                return;
            }
            LOGGER.info("Failed to download " + failedToDownload.size() + "("
                    + (failedToDownload.size() * 100 / toDownload.size()) + "%");
            failedToDownload.forEach(fail -> {
                LOGGER.info("Failed: " + fail);
            });

            currentRun++;
            if (currentRun >= maxRuns) {
                LOGGER.info("Max runs reached, stopping download process");
                ToastManager.getInstance()
                        .displayToast(
                                Component.literal("Failed to download " + failedToDownload.size() + " files"),
                                Component.literal("Restart the game to try downloading again"));
                finishRun();
                return;
            }

            ToastManager.getInstance()
                    .displayToast(
                            Component.literal("Failed to download " + failedToDownload.size() + " files"),
                            Component.literal("Will try downloading these files again"));

            // We go through the download process again to make sure we have all the files.
            CompletableFuture.runAsync(this::processAudioManifest);
        });

        downloadQueue.initializeQueue(tasks);

        downloadQueue.start();
    }

    /**
     * Retrieves a list of audio files that need to be downloaded.
     * <p>
     * This method compares the local metadata with the remote metadata to determine which files
     * are missing or have different hashes, indicating that they need to be downloaded. It also
     * calculates the total size of the files to be downloaded, setting the downloadSize variable.
     *
     * @return A list of file names that need to be downloaded.
     */
    private List<String> getToDownload() {
        downloadSize = 0;
        // Populate the list of file names to download. If the file is not present or the hash is different, add it to
        // the list
        return new ArrayList<>(remoteMetadata.entrySet().stream()
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
    }

    /**
     * Populates the local metadata map with the files in the audio folder.
     * It checks if the audio folder is empty and if it is not populates the metadata map with the files in the audio folder.
     */
    private void populateLocalMetadata() {
        audioFolder = new File(audioDir);
        if (!audioFolder.exists()) {
            if (!audioFolder.mkdirs()) {
                LOGGER.error("Failed to create audio folder: " + audioFolder.getAbsolutePath());
                return;
            }
        }

        metadataMap.clear();

        // Hashing the whole folder means reading every byte of it back off disk, which is well over a
        // gigabyte by now. Files that have not changed since they were last hashed reuse that result.
        LocalHashCache hashCache = LocalHashCache.load(audioFolder.toPath().resolve(HASH_CACHE_FILE));
        int[] hashed = {0};

        try {
            // Walking the folder hands us the size and timestamp the directory listing already carries.
            // Asking each File for them instead costs two extra syscalls apiece, which across ~19k
            // files is seconds rather than milliseconds.
            Files.walkFileTree(audioFolder.toPath(), Set.of(), 1, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes attributes) {
                    String name = path.getFileName().toString();
                    if (!name.endsWith(".ogg")) {
                        return FileVisitResult.CONTINUE;
                    }

                    String fileName = name.replace(".ogg", "");
                    long size = attributes.size();
                    long modified = attributes.lastModifiedTime().toMillis();
                    try {
                        String hash = hashCache.lookup(fileName, size, modified);
                        if (hash == null) {
                            hash = computeFileHash(path.toFile());
                            hashed[0]++;
                        }
                        hashCache.record(fileName, size, modified, hash);
                        metadataMap.put(fileName, new AudioMetadata(size, hash));
                    } catch (Exception e) {
                        // Left out of the metadata map, so it is treated as missing and downloaded again.
                        LOGGER.error("Failed to compute hash for file: " + name);
                        e.printStackTrace();
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            LOGGER.error("Failed to scan the audio folder: " + audioDir);
            e.printStackTrace();
            return;
        }

        hashCache.save();
        LOGGER.info("Populated metadata map with " + metadataMap.size() + " files (" + hashed[0] + " hashed, "
                + (metadataMap.size() - hashed[0]) + " reused from cache)");
    }

    /**
     * Fetches the audio manifest from the remote server.
     * <p>
     * Prefers the compressed manifest, which is revalidated against a local copy so an unchanged one
     * costs no download at all. Anything unexpected there falls through to the uncompressed manifest
     * the mod has always read, so a client newer than the upload pipeline still works.
     *
     * @return A map of audio metadata from the remote server.
     * @throws IOException If an I/O error occurs while fetching the manifest.
     */
    private HashMap<String, AudioMetadata> fetchAudioManifest() throws IOException {
        HashMap<String, AudioMetadata> manifest = fetchCompressedManifest();
        if (manifest != null) {
            return manifest;
        }
        LOGGER.warn("Falling back to the uncompressed audio manifest");
        return fetchLegacyManifest();
    }

    /**
     * Downloads the compressed manifest, or reads the local copy back when blob storage reports it
     * has not changed.
     *
     * @return the manifest, or null if it could not be fetched and the caller should fall back
     */
    private HashMap<String, AudioMetadata> fetchCompressedManifest() {
        Path cacheFile = Paths.get(audioDir, MANIFEST_CACHE_FILE);
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(getManifestUrl()).openConnection();
            connection.setRequestMethod("GET");

            // Only worth revalidating if there is actually something on disk to revalidate against.
            String cachedETag = ModCore.config.audioManifestETag;
            if (Files.isRegularFile(cacheFile) && cachedETag != null && !cachedETag.isEmpty()) {
                connection.setRequestProperty("If-None-Match", cachedETag);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_NOT_MODIFIED) {
                LOGGER.info("Audio manifest is unchanged; reading the cached copy");
                return readManifest(cacheFile);
            }
            if (responseCode != HttpURLConnection.HTTP_OK) {
                LOGGER.warn("Failed to fetch the compressed audio manifest. Response code: " + responseCode);
                return null;
            }

            writeManifestCache(connection, cacheFile);
            HashMap<String, AudioMetadata> manifest = readManifest(cacheFile);
            if (manifest == null) {
                return null;
            }

            // Recorded only once the file is readable, so a broken download is not remembered as current.
            String etag = connection.getHeaderField("ETag");
            ModCore.config.audioManifestETag = etag != null ? etag : "";
            ModCore.config.save();
            LOGGER.info("Fetched audio manifest with " + manifest.size() + " entries");
            return manifest;
        } catch (IOException | RuntimeException e) {
            LOGGER.error("Failed to fetch the compressed audio manifest");
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /** Streams the response to disk via a temporary file, so a failed download keeps the old copy. */
    private void writeManifestCache(HttpURLConnection connection, Path cacheFile) throws IOException {
        Path parent = cacheFile.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        Path temp = cacheFile.resolveSibling(MANIFEST_CACHE_FILE + ".tmp");
        try {
            try (InputStream inputStream = connection.getInputStream()) {
                Files.copy(inputStream, temp, StandardCopyOption.REPLACE_EXISTING);
            }
            Files.move(temp, cacheFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            Files.deleteIfExists(temp);
            throw e;
        }
    }

    /**
     * Reads a cached manifest off disk, discarding it if it turns out to be unreadable so the next
     * run fetches a fresh one rather than trusting the same broken file forever.
     */
    private HashMap<String, AudioMetadata> readManifest(Path cacheFile) {
        try (InputStream gzipped = new GZIPInputStream(Files.newInputStream(cacheFile));
                InputStreamReader reader = new InputStreamReader(gzipped, StandardCharsets.UTF_8)) {
            return gson.fromJson(reader, new TypeToken<HashMap<String, AudioMetadata>>() {}.getType());
        } catch (IOException | RuntimeException e) {
            LOGGER.error("Cached audio manifest could not be read; discarding it");
            e.printStackTrace();
            try {
                Files.deleteIfExists(cacheFile);
            } catch (IOException ignored) {
                // Nothing useful to do; the stale ETag is cleared below either way.
            }
            ModCore.config.audioManifestETag = "";
            ModCore.config.save();
            return null;
        }
    }

    /** The uncompressed manifest fetch, unchanged, kept as the fallback. */
    private HashMap<String, AudioMetadata> fetchLegacyManifest() throws IOException {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(getLegacyManifestUrl()).openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                try (InputStream inputStream = connection.getInputStream();
                        InputStreamReader reader = new InputStreamReader(inputStream)) {
                    return gson.fromJson(reader, new TypeToken<HashMap<String, AudioMetadata>>() {}.getType());
                }
            }
            throw new IOException("Failed to fetch audio manifest: " + getLegacyManifestUrl());
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
            LOGGER.error("Remote metadata is null, cannot clean up unused files");
            return;
        }

        File audioFolder = new File(audioDir);
        File[] localFiles = audioFolder.listFiles((dir, name) -> name.endsWith(".ogg"));
        if (localFiles != null) {
            for (File localFile : localFiles) {
                String fileName = localFile.getName().replace(".ogg", "");

                if (!remoteMetadata.containsKey(fileName)) {
                    LOGGER.info("Deleting unused file: " + localFile.getName());
                    if (!localFile.delete()) {
                        LOGGER.error("Failed to delete file: " + localFile.getName());
                    }
                }
            }
        }
    }
}
