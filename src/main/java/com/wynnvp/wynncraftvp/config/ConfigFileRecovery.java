/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ConfigFileRecovery {
    private static final DateTimeFormatter BACKUP_TIMESTAMP = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");

    public static boolean quarantineIfNullByteCorrupted(Path configPath) throws IOException {
        if (!Files.isRegularFile(configPath)) {
            return false;
        }

        byte[] bytes = Files.readAllBytes(configPath);
        if (!containsNullByte(bytes)) {
            return false;
        }

        Files.move(configPath, corruptedConfigPath(configPath), StandardCopyOption.REPLACE_EXISTING);
        return true;
    }

    private static boolean containsNullByte(byte[] bytes) {
        for (byte b : bytes) {
            if (b == 0) {
                return true;
            }
        }
        return false;
    }

    private static Path corruptedConfigPath(Path configPath) {
        String fileName = configPath.getFileName().toString();
        String timestamp = LocalDateTime.now().format(BACKUP_TIMESTAMP);
        return configPath.resolveSibling(fileName + ".corrupt-" + timestamp);
    }

    private ConfigFileRecovery() {}
}
