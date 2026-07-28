/*
 * Copyright © Team-VoW 2025-2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.config;

import com.wynnvp.wynncraftvp.ModCore;
import java.util.List;

/**
 * The mod's settings, persisted by {@link VowConfigHolder}.
 * <p>
 * Field names are the TOML keys, so renaming or reordering a field changes the on-disk format.
 * Fields not exposed in {@link com.wynnvp.wynncraftvp.config.gui.VowConfigScreen} are internal
 * state the mod manages on its own.
 */
public class VOWAutoConfig {
    // Internal state, not shown in the settings screen:
    public String word = "gzog6zilzq6zxlt";

    public boolean hasChosenLineReport = false;

    public String azureBlobLink = "https://cdn.jsdelivr.net/gh/Team-VoW/WynncraftVoiceProject@main/sounds/";

    public String azureBlobRootLink = "https://vow.blob.core.windows.net/mod/";

    public List<String> urls = List.of("https://cdn.jsdelivr.net/gh/Team-VoW/WynncraftVoiceProject@main/sounds/");

    public double npcFinderThingMaxDistanceChangeBeforeCacheInvalid = 0.5;

    public String lastsSoundsUpdateHeader = "never";

    public boolean playAllSoundsOnPlayer = false;

    public int voiceVolume = 100;

    public int playbackSpeed = 100;

    // Line reporting settings
    public boolean reportMissingLines = true;
    public boolean anonymous = true;
    public boolean sendFunFact = false;

    public boolean downloadSounds = false;

    public int blockCutOff = 32;

    public boolean autoProgress = false;

    public boolean enableReverb = true;

    public boolean blockVillagerSoundsDuringVoiceDialog = true;

    public boolean earlyPlayOverlay = true;

    public int earlyPlayOverlayMinChars = 15;

    public String nicknameOverride = "";

    // Debug and Logs section
    public DebugAndLogs debugAndLogs = new DebugAndLogs();

    public static class DebugAndLogs {
        public boolean highlightSpeaker = false;

        public boolean logDialogueLines = true;

        public boolean onlyLogNotPlayingLines = true;

        public boolean logPlayingInformation = false;

        public boolean useCustomAudioPath = false;

        public String customAudioPath = "";

        public boolean useCustomSoundsJson = false;

        public String customSoundsJsonPath = "";

        public boolean logOverlayDialogueToChat = false;

        public boolean logOverlayPackets = false;
    }

    public boolean isLogDialogueLines() {
        return debugAndLogs.logDialogueLines;
    }

    public boolean isOnlyLogNotPlayingLines() {
        return debugAndLogs.onlyLogNotPlayingLines;
    }

    public boolean isLogPlayingInformation() {
        return debugAndLogs.logPlayingInformation;
    }

    public boolean isUseCustomAudioPath() {
        return debugAndLogs.useCustomAudioPath;
    }

    public String getCustomAudioPath() {
        return ConfigPathNormalizer.normalize(debugAndLogs.customAudioPath);
    }

    public boolean isUseCustomSoundsJson() {
        return debugAndLogs.useCustomSoundsJson;
    }

    public String getCustomSoundsJsonPath() {
        return ConfigPathNormalizer.normalize(debugAndLogs.customSoundsJsonPath);
    }

    public boolean isLogOverlayDialogueToChat() {
        return debugAndLogs.logOverlayDialogueToChat;
    }

    public boolean isLogOverlayPackets() {
        return debugAndLogs.logOverlayPackets;
    }

    public boolean isBlockVillagerSoundsDuringVoiceDialog() {
        return blockVillagerSoundsDuringVoiceDialog;
    }

    public boolean isEarlyPlayOverlay() {
        return earlyPlayOverlay;
    }

    public int getEarlyPlayOverlayMinChars() {
        return earlyPlayOverlayMinChars;
    }

    public String getNicknameOverride() {
        return nicknameOverride;
    }

    public boolean isPlayAllSoundsOnPlayer() {
        return playAllSoundsOnPlayer;
    }

    public int getVoiceVolume() {
        return voiceVolume;
    }

    public float getPlaybackSpeed() {
        return playbackSpeed / 100.0f;
    }

    public String getWord() {
        return word;
    }

    public boolean isReportMissingLines() {
        return reportMissingLines;
    }

    public void setReportMissingLines(boolean input) {
        reportMissingLines = input;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean input) {
        anonymous = input;
    }

    public boolean isSendFunFact() {
        return sendFunFact;
    }

    public int getBlockCutOff() {
        return blockCutOff;
    }

    public boolean isHighlightSpeaker() {
        return debugAndLogs.highlightSpeaker;
    }

    public double getNpcFinderThingMaxDistanceChangeBeforeCacheInvalid() {
        return npcFinderThingMaxDistanceChangeBeforeCacheInvalid;
    }

    public boolean getHasShownMissingLineNotification() {
        return hasChosenLineReport;
    }

    public void setHasShownMissingLineNotification(boolean input) {
        hasChosenLineReport = input;
    }

    public void save() {
        if (ModCore.configHolder != null) {
            ModCore.configHolder.save();
        }
    }

    public String getRemoteJsonLink() {
        return azureBlobLink + "sounds.json";
    }
}
