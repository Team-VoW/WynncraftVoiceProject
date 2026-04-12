/*
 * Copyright © Team-VoW 2025-2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.config;

import java.util.List;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "wynnvp")
public class VOWAutoConfig implements ConfigData {
    // Excluded stuff:
    @ConfigEntry.Gui.Excluded
    public String word = "gzog6zilzq6zxlt";

    @ConfigEntry.Gui.Excluded
    public boolean hasChosenLineReport = false;

    @ConfigEntry.Gui.Excluded
    public String azureBlobLink = "http://voicesofwynn.blob.core.windows.net/audio/sounds/";

    @ConfigEntry.Gui.Excluded
    public String azureBlobRootLink = "https://vow.blob.core.windows.net/mod/";

    @ConfigEntry.Gui.Excluded
    public String lastManifestUpdateHeader = "never";

    @ConfigEntry.Gui.Excluded
    public List<String> urls = List.of("https://cdn.jsdelivr.net/gh/Team-VoW/WynncraftVoiceProject@main/sounds/");

    @ConfigEntry.Gui.Excluded
    public double npcFinderThingMaxDistanceChangeBeforeCacheInvalid = 0.5;

    @ConfigEntry.Gui.Excluded
    public String lastsSoundsUpdateHeader = "never";

    @ConfigEntry.Gui.Tooltip
    public boolean playAllSoundsOnPlayer = false;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 1, max = 1000)
    public int voiceVolume = 100;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 70, max = 200)
    public int playbackSpeed = 100;

    // Line reporting settings
    public boolean reportMissingLines = true;
    public boolean anonymous = true;
    public boolean sendFunFact = false;

    @ConfigEntry.Gui.Tooltip
    public boolean downloadSounds = false;

    @ConfigEntry.Gui.Tooltip
    public int blockCutOff = 32;

    @ConfigEntry.Gui.Tooltip
    public boolean autoProgress = false;

    @ConfigEntry.Gui.Tooltip
    public boolean enableReverb = true;

    @ConfigEntry.Gui.Tooltip
    public boolean blockVillagerSoundsDuringVoiceDialog = true;

    @ConfigEntry.Gui.Tooltip
    public boolean earlyPlayOverlay = true;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
    public int earlyPlayOverlayMinChars = 15;

    @ConfigEntry.Gui.Tooltip
    public String nicknameOverride = "";

    // Debug and Logs section
    @ConfigEntry.Gui.CollapsibleObject
    public DebugAndLogs debugAndLogs = new DebugAndLogs();

    public static class DebugAndLogs {
        @ConfigEntry.Gui.Tooltip
        public boolean highlightSpeaker = false;

        @ConfigEntry.Gui.Tooltip
        public boolean logDialogueLines = true;

        @ConfigEntry.Gui.Tooltip
        public boolean onlyLogNotPlayingLines = true;

        @ConfigEntry.Gui.Tooltip
        public boolean logPlayingInformation = false;

        @ConfigEntry.Gui.Tooltip
        public boolean useCustomAudioPath = false;

        @ConfigEntry.Gui.Tooltip
        public String customAudioPath = "";

        @ConfigEntry.Gui.Tooltip
        public boolean useCustomSoundsJson = false;

        @ConfigEntry.Gui.Tooltip
        public String customSoundsJsonPath = "";

        @ConfigEntry.Gui.Tooltip
        public boolean logOverlayDialogueToChat = false;

        @ConfigEntry.Gui.Tooltip
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
        return debugAndLogs.customAudioPath;
    }

    public boolean isUseCustomSoundsJson() {
        return debugAndLogs.useCustomSoundsJson;
    }

    public String getCustomSoundsJsonPath() {
        return debugAndLogs.customSoundsJsonPath;
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
        AutoConfig.getConfigHolder(VOWAutoConfig.class).save();
    }

    public String getRemoteJsonLink() {
        return azureBlobLink + "sounds.json";
    }
}
