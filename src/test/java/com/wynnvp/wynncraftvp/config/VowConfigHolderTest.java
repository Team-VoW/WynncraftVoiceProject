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
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class VowConfigHolderTest {
    /** A config as written by the Cloth Config versions of the mod. */
    private static final String CLOTH_WRITTEN_CONFIG = """
            word = "gzog6zilzq6zxlt"
            hasChosenLineReport = true
            azureBlobLink = "https://cdn.jsdelivr.net/gh/Team-VoW/WynncraftVoiceProject@main/sounds/"
            azureBlobRootLink = "https://vow.blob.core.windows.net/mod/"
            urls = ["https://cdn.jsdelivr.net/gh/Team-VoW/WynncraftVoiceProject@main/sounds/"]
            npcFinderThingMaxDistanceChangeBeforeCacheInvalid = 0.5
            lastsSoundsUpdateHeader = "Sat, 04 Apr 2026 19:24:51 GMT"
            playAllSoundsOnPlayer = false
            voiceVolume = 100
            playbackSpeed = 100
            reportMissingLines = true
            anonymous = true
            sendFunFact = true
            downloadSounds = false
            blockCutOff = 32
            autoProgress = false
            enableReverb = true
            blockVillagerSoundsDuringVoiceDialog = true
            earlyPlayOverlay = true
            earlyPlayOverlayMinChars = 15
            nicknameOverride = ""

            [debugAndLogs]
            highlightSpeaker = false
            logDialogueLines = true
            onlyLogNotPlayingLines = true
            logPlayingInformation = false
            useCustomAudioPath = false
            customAudioPath = ""
            useCustomSoundsJson = true
            customSoundsJsonPath = "/Users/maxi/sounds/sounds.json"
            logOverlayDialogueToChat = false
            logOverlayPackets = false
            """;

    @TempDir
    Path tempDir;

    @Test
    void readsConfigWrittenByClothConfig() throws Exception {
        Path configPath = writeConfig(CLOTH_WRITTEN_CONFIG);

        VOWAutoConfig config = new VowConfigHolder(configPath).load();

        assertEquals("gzog6zilzq6zxlt", config.word);
        assertTrue(config.hasChosenLineReport);
        assertEquals("Sat, 04 Apr 2026 19:24:51 GMT", config.lastsSoundsUpdateHeader);
        assertEquals(List.of("https://cdn.jsdelivr.net/gh/Team-VoW/WynncraftVoiceProject@main/sounds/"), config.urls);
        assertEquals(0.5, config.npcFinderThingMaxDistanceChangeBeforeCacheInvalid);
        assertEquals(32, config.blockCutOff);
        assertEquals(15, config.earlyPlayOverlayMinChars);
        assertTrue(config.sendFunFact);
        assertTrue(config.debugAndLogs.useCustomSoundsJson);
        assertEquals("/Users/maxi/sounds/sounds.json", config.debugAndLogs.customSoundsJsonPath);
        assertFalse(config.debugAndLogs.logOverlayPackets);
    }

    @Test
    void savingKeepsTheClothConfigFileFormat() throws Exception {
        Path configPath = writeConfig(CLOTH_WRITTEN_CONFIG);

        VowConfigHolder holder = new VowConfigHolder(configPath);
        holder.load();
        holder.save();

        assertEquals(CLOTH_WRITTEN_CONFIG, Files.readString(configPath, StandardCharsets.UTF_8));
    }

    @Test
    void roundTripsChangedValues() throws Exception {
        Path configPath = writeConfig(CLOTH_WRITTEN_CONFIG);

        VowConfigHolder holder = new VowConfigHolder(configPath);
        VOWAutoConfig config = holder.load();
        config.voiceVolume = 250;
        config.nicknameOverride = "Maxi";
        config.debugAndLogs.logOverlayPackets = true;
        holder.save();

        VOWAutoConfig reloaded = new VowConfigHolder(configPath).load();
        assertEquals(250, reloaded.voiceVolume);
        assertEquals("Maxi", reloaded.nicknameOverride);
        assertTrue(reloaded.debugAndLogs.logOverlayPackets);
    }

    @Test
    void keepsDefaultsForKeysMissingFromTheFile() throws Exception {
        Path configPath = writeConfig("voiceVolume = 42\n\n[debugAndLogs]\nlogOverlayPackets = true\n");

        VOWAutoConfig config = new VowConfigHolder(configPath).load();

        assertEquals(42, config.voiceVolume);
        assertTrue(config.debugAndLogs.logOverlayPackets);
        // Everything the file did not mention falls back to the field initializers.
        assertEquals(100, config.playbackSpeed);
        assertEquals(32, config.blockCutOff);
        assertEquals("never", config.lastsSoundsUpdateHeader);
        assertTrue(config.debugAndLogs.logDialogueLines);
    }

    @Test
    void writesDefaultsWhenNoConfigExists() throws Exception {
        Path configPath = tempDir.resolve("wynnvp.toml");

        VOWAutoConfig config = new VowConfigHolder(configPath).load();

        assertEquals(100, config.voiceVolume);
        assertTrue(Files.exists(configPath));
        assertEquals(config.voiceVolume, new VowConfigHolder(configPath).load().voiceVolume);
    }

    @Test
    void quarantinesUnreadableConfigAndFallsBackToDefaults() throws Exception {
        Path configPath = writeConfig("this is not = = valid toml [[[\n");

        VOWAutoConfig config = new VowConfigHolder(configPath).load();

        assertEquals(100, config.voiceVolume);
        assertEquals(1, countCorruptConfigs());
    }

    private Path writeConfig(String contents) throws Exception {
        Path configPath = tempDir.resolve("wynnvp.toml");
        Files.writeString(configPath, contents, StandardCharsets.UTF_8);
        return configPath;
    }

    private long countCorruptConfigs() throws Exception {
        try (var paths = Files.list(tempDir)) {
            return paths.filter(path -> path.getFileName().toString().startsWith("wynnvp.toml.corrupt-"))
                    .count();
        }
    }
}
