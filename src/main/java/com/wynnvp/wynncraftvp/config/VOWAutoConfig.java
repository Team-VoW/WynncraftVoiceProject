/*
 * Copyright © Team-VoW 2025-2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.config;

import com.wynnvp.wynncraftvp.ModCore;
import java.time.Duration;
import java.util.List;

/**
 * The mod's settings, persisted by {@link VowConfigHolder}.
 * <p>
 * Field names are the TOML keys, so renaming or reordering a field changes the on-disk format.
 * Fields not exposed in {@link com.wynnvp.wynncraftvp.config.gui.VowConfigScreen} are internal
 * state the mod manages on its own.
 */
public class VOWAutoConfig {
    /** How long the Directional Audio nudge stays quiet after being shown. */
    public static final long DIRECTIONAL_AUDIO_HINT_INTERVAL_MS =
            Duration.ofDays(3).toMillis();

    // Internal state, not shown in the settings screen:
    public String word = "gzog6zilzq6zxlt";

    public boolean hasChosenLineReport = false;

    /** When the nudge about Minecraft's Directional Audio setting was last shown, in epoch millis. */
    public long directionalAudioHintLastShown = 0;

    /** Base URL the sounds and sounds.json are served from; picked from {@link #urls} by latency. */
    public String soundsBaseUrl = "https://cdn.jsdelivr.net/gh/Team-VoW/WynncraftVoiceProject@main/sounds/";

    public String azureBlobRootLink = "https://vow.blob.core.windows.net/mod/";

    public List<String> urls = List.of("https://cdn.jsdelivr.net/gh/Team-VoW/WynncraftVoiceProject@main/sounds/");

    public double npcFinderThingMaxDistanceChangeBeforeCacheInvalid = 0.5;

    /** ETag of the cached sounds.json, used to revalidate it against the CDN. */
    public String soundsJsonETag = "never";

    /** ETag of the cached audio manifest, used to revalidate it against blob storage. */
    public String audioManifestETag = "";

    public boolean playAllSoundsOnPlayer = false;

    public int voiceVolume = 200;

    public int playbackSpeed = 100;

    // Line reporting settings
    public boolean reportMissingLines = true;
    public boolean anonymous = true;
    public boolean questFeedbackPrompts = true;
    /** Random installation identifier; never derived from a Minecraft account. */
    public String feedbackInstallationId = "";

    public boolean sendFunFact = false;

    public boolean downloadSounds = false;

    public int blockCutOff = 32;

    public boolean autoProgress = false;

    public boolean enableReverb = true;

    public boolean soundPhysics = true;

    public int soundPhysicsReverbStrength = 100;

    public int soundPhysicsOcclusionStrength = 100;

    public int soundPhysicsRays = 48;

    public boolean blockVillagerSoundsDuringVoiceDialog = true;

    public boolean routeMasterSoundsToBlocks = true;

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

        public boolean logBlockedSounds = false;

        public boolean logAutoProgress = true;

        public boolean debugSoundPhysics = false;
    }

    /**
     * Whether the Directional Audio nudge is allowed to appear again.
     *
     * <p>A fresh install has never seen it, so it is due at once. After that it stays quiet for
     * {@link #DIRECTIONAL_AUDIO_HINT_INTERVAL_MS}: the setting it points at is worth mentioning more
     * than once, since a player who ignores it the first time has no other way of finding out, but not
     * worth mentioning every session.
     *
     * @param now wall-clock time in epoch millis
     */
    public boolean isDirectionalAudioHintDue(long now) {
        // A clock that has jumped backwards -- a timezone move, an NTP correction, a machine whose
        // battery died -- would otherwise silence the hint until real time caught up with the stamp.
        if (now < directionalAudioHintLastShown) {
            return true;
        }
        return now - directionalAudioHintLastShown >= DIRECTIONAL_AUDIO_HINT_INTERVAL_MS;
    }

    public void markDirectionalAudioHintShown(long now) {
        directionalAudioHintLastShown = now;
    }

    public boolean isSoundPhysics() {
        return soundPhysics;
    }

    /** Wetness scale for computed reverb. 1.0 is the physically modelled amount. */
    public float getSoundPhysicsReverbStrength() {
        return soundPhysicsReverbStrength / 100.0f;
    }

    /** Muffling scale. 0 turns occlusion off without disabling reverb. */
    public float getSoundPhysicsOcclusionStrength() {
        return soundPhysicsOcclusionStrength / 100.0f;
    }

    public int getSoundPhysicsRays() {
        return soundPhysicsRays;
    }

    public boolean isDebugSoundPhysics() {
        return debugAndLogs.debugSoundPhysics;
    }

    public boolean isLogBlockedSounds() {
        return debugAndLogs.logBlockedSounds;
    }

    public boolean isLogAutoProgress() {
        return debugAndLogs.logAutoProgress;
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

    public boolean isRouteMasterSoundsToBlocks() {
        return routeMasterSoundsToBlocks;
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

    public void setPlayAllSoundsOnPlayer(boolean input) {
        playAllSoundsOnPlayer = input;
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
        return soundsBaseUrl + "sounds.json";
    }
}
