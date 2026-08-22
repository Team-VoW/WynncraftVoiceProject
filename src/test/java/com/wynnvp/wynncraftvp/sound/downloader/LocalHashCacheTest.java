/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.downloader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class LocalHashCacheTest {
    @TempDir
    Path tempDir;

    private Path cacheFile() {
        return tempDir.resolve(".vow_hash_cache.json");
    }

    @Test
    void missesEverythingWhenNoCacheExists() {
        LocalHashCache cache = LocalHashCache.load(cacheFile());

        assertNull(cache.lookup("kingsrecruit-tasim-1", 1234, 5678));
    }

    @Test
    void reusesTheHashWhenSizeAndTimestampBothMatch() {
        LocalHashCache first = LocalHashCache.load(cacheFile());
        first.record("kingsrecruit-tasim-1", 1234, 5678, "abc123");
        assertTrue(first.save());

        LocalHashCache reloaded = LocalHashCache.load(cacheFile());

        assertEquals("abc123", reloaded.lookup("kingsrecruit-tasim-1", 1234, 5678));
    }

    @Test
    void rehashesWhenTheFileChanged() {
        LocalHashCache first = LocalHashCache.load(cacheFile());
        first.record("kingsrecruit-tasim-1", 1234, 5678, "abc123");
        first.save();

        LocalHashCache reloaded = LocalHashCache.load(cacheFile());

        // A replaced file changes size, a rewritten one changes its timestamp; either alone is enough.
        assertNull(reloaded.lookup("kingsrecruit-tasim-1", 9999, 5678));
        assertNull(reloaded.lookup("kingsrecruit-tasim-1", 1234, 9999));
        assertNull(reloaded.lookup("some-file-that-was-never-cached", 1234, 5678));
    }

    @Test
    void dropsEntriesForFilesThatAreGone() {
        LocalHashCache first = LocalHashCache.load(cacheFile());
        first.record("still-here", 1, 2, "hash-a");
        first.record("deleted-later", 3, 4, "hash-b");
        first.save();

        // A second pass only sees one of the two files, so the other falls out of the cache.
        LocalHashCache second = LocalHashCache.load(cacheFile());
        second.record("still-here", 1, 2, "hash-a");
        assertTrue(second.save());

        LocalHashCache third = LocalHashCache.load(cacheFile());
        assertEquals("hash-a", third.lookup("still-here", 1, 2));
        assertNull(third.lookup("deleted-later", 3, 4));
    }

    @Test
    void doesNotRewriteAnUnchangedCache() throws IOException {
        LocalHashCache first = LocalHashCache.load(cacheFile());
        first.record("kingsrecruit-tasim-1", 1234, 5678, "abc123");
        assertTrue(first.save());

        long writtenAt = Files.getLastModifiedTime(cacheFile()).toMillis();

        LocalHashCache second = LocalHashCache.load(cacheFile());
        second.record("kingsrecruit-tasim-1", 1234, 5678, "abc123");

        assertFalse(second.save(), "an audio folder nobody touched should not rewrite the cache");
        assertEquals(writtenAt, Files.getLastModifiedTime(cacheFile()).toMillis());
    }

    @Test
    void treatsADamagedCacheAsEmptyRatherThanFailing() throws IOException {
        Files.writeString(cacheFile(), "{ this is not json", StandardCharsets.UTF_8);

        LocalHashCache cache = LocalHashCache.load(cacheFile());

        assertNull(cache.lookup("kingsrecruit-tasim-1", 1234, 5678));

        // ...and it recovers, rather than staying broken forever.
        cache.record("kingsrecruit-tasim-1", 1234, 5678, "abc123");
        assertTrue(cache.save());
        assertEquals("abc123", LocalHashCache.load(cacheFile()).lookup("kingsrecruit-tasim-1", 1234, 5678));
    }
}
