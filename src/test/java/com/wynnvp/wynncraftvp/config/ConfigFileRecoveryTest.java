/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ConfigFileRecoveryTest {
    @TempDir
    Path tempDir;

    @Test
    void leavesValidConfigInPlace() throws Exception {
        Path configPath = tempDir.resolve("wynnvp.toml");
        Files.writeString(configPath, "voiceVolume = 100\n", StandardCharsets.UTF_8);

        assertFalse(ConfigFileRecovery.quarantineIfNullByteCorrupted(configPath));

        assertTrue(Files.exists(configPath));
        assertEquals("voiceVolume = 100\n", Files.readString(configPath, StandardCharsets.UTF_8));
    }

    @Test
    void quarantinesConfigContainingNullBytes() throws Exception {
        Path configPath = tempDir.resolve("wynnvp.toml");
        Files.write(configPath, new byte[] {0, 0, 0, 0});

        assertTrue(ConfigFileRecovery.quarantineIfNullByteCorrupted(configPath));

        assertFalse(Files.exists(configPath));
        assertEquals(1, countCorruptConfigs());
    }

    private long countCorruptConfigs() throws Exception {
        try (var paths = Files.list(tempDir)) {
            return paths.filter(path -> path.getFileName().toString().startsWith("wynnvp.toml.corrupt-"))
                    .count();
        }
    }
}
