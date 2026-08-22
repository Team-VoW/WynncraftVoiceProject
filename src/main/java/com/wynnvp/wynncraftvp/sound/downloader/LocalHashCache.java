/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.downloader;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

/**
 * Remembers the SHA-256 of every downloaded audio file, so startup does not have to read the whole
 * audio folder back off disk to work out what is already up to date.
 * <p>
 * A cached hash is trusted only while the file's size and modification time both still match what
 * they were when it was hashed. Anything else — edited, replaced, half-written, or absent from the
 * cache — is hashed again, so a stale or hand-edited cache costs time rather than correctness.
 */
final class LocalHashCache {
    /** What a file looked like when it was last hashed. */
    record Entry(long size, long modified, String hash) {}

    private static final Gson GSON = new Gson();

    private final Path file;
    private final Map<String, Entry> loaded;
    private final Map<String, Entry> current = new HashMap<>();

    private LocalHashCache(Path file, Map<String, Entry> loaded) {
        this.file = file;
        this.loaded = loaded;
    }

    /** Reads the cache, falling back to an empty one if it is missing or unreadable. */
    static LocalHashCache load(Path file) {
        if (!Files.isRegularFile(file)) {
            return new LocalHashCache(file, Map.of());
        }
        try (Reader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            Map<String, Entry> entries = GSON.fromJson(reader, new TypeToken<HashMap<String, Entry>>() {}.getType());
            return new LocalHashCache(file, entries != null ? entries : Map.of());
        } catch (IOException | RuntimeException e) {
            // A damaged cache is not worth reporting; every file simply gets hashed again.
            return new LocalHashCache(file, Map.of());
        }
    }

    /**
     * Returns the stored hash for {@code name}, or null if the file has changed since it was hashed
     * and needs re-reading.
     */
    String lookup(String name, long size, long modified) {
        Entry entry = loaded.get(name);
        if (entry == null || entry.size() != size || entry.modified() != modified) {
            return null;
        }
        return entry.hash();
    }

    /** Records the hash of a file as it exists right now. */
    void record(String name, long size, long modified, String hash) {
        current.put(name, new Entry(size, modified, hash));
    }

    /**
     * Writes back everything recorded since loading, dropping entries for files that are no longer
     * present. Does nothing when the contents are unchanged, so an untouched audio folder does not
     * rewrite the file on every launch.
     *
     * @return true if the cache was written to disk
     */
    boolean save() {
        if (current.equals(loaded)) {
            return false;
        }
        Path temp = file.resolveSibling(file.getFileName() + ".tmp");
        try {
            Path parent = file.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
            try (Writer writer = Files.newBufferedWriter(temp, StandardCharsets.UTF_8)) {
                GSON.toJson(current, writer);
            }
            Files.move(temp, file, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            // Losing the cache only means the next launch re-hashes; don't fail the download for it.
            try {
                Files.deleteIfExists(temp);
            } catch (IOException ignored) {
                // Nothing useful to do; the next save overwrites it anyway.
            }
            return false;
        }
    }
}
