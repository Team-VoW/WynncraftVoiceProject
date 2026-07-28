/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.config;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import com.wynnvp.wynncraftvp.ModCore;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * Loads and stores {@link VOWAutoConfig} as TOML.
 * <p>
 * The file layout is the one Cloth Config used to produce, so configs written by older versions of
 * the mod are read back unchanged. Keys missing from the file keep the field initializers declared
 * in {@link VOWAutoConfig}, which is what makes adding new options backwards compatible.
 */
public class VowConfigHolder {
    private final Path file;
    private VOWAutoConfig config;

    public VowConfigHolder(Path file) {
        this.file = file;
    }

    public VOWAutoConfig load() {
        boolean existed = Files.isRegularFile(file);
        config = readOrDefault();
        if (!existed) {
            save();
        }
        return config;
    }

    public VOWAutoConfig get() {
        return config;
    }

    public void save() {
        if (config == null) {
            return;
        }

        Path tempFile = file.resolveSibling(file.getFileName() + ".tmp");
        try {
            Path parent = file.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
            new TomlWriter().write(config, tempFile.toFile());
            moveIntoPlace(tempFile);
        } catch (IOException e) {
            ModCore.error("Failed to save the Voices of Wynn config to " + file, e);
            deleteQuietly(tempFile);
        }
    }

    private VOWAutoConfig readOrDefault() {
        if (!Files.isRegularFile(file)) {
            return new VOWAutoConfig();
        }

        try {
            return new Toml().read(file.toFile()).to(VOWAutoConfig.class);
        } catch (RuntimeException e) {
            ModCore.error("Could not read the Voices of Wynn config at " + file + ", falling back to defaults.", e);
            quarantine();
            return new VOWAutoConfig();
        }
    }

    private void quarantine() {
        try {
            ConfigFileRecovery.quarantine(file);
        } catch (IOException e) {
            ModCore.warn("Failed to move the unreadable Voices of Wynn config aside.", e);
        }
    }

    private void moveIntoPlace(Path tempFile) throws IOException {
        try {
            Files.move(tempFile, file, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (IOException e) {
            // Not every file system supports atomic moves, so fall back to a plain replace.
            Files.move(tempFile, file, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private void deleteQuietly(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException ignored) {
            // Nothing useful to do; the next save overwrites it anyway.
        }
    }
}
