/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.config.gui;

import com.wynnvp.wynncraftvp.config.BetaConfig;
import com.wynnvp.wynncraftvp.config.VOWAutoConfig;
import java.util.List;

/** The single, ordered definition of the settings displayed by the configuration screen. */
public final class VowSettings {
    private static final String DEBUG_PREFIX = "debugAndLogs.";

    private VowSettings() {}

    public static List<Category> all(VOWAutoConfig config) {
        VOWAutoConfig.DebugAndLogs debug = config.debugAndLogs;
        return List.of(
                category(
                        "audio",
                        section(
                                "volumeAndSpeed",
                                VowSetting.slider(
                                                "voiceVolume",
                                                1,
                                                1000,
                                                () -> config.voiceVolume,
                                                value -> config.voiceVolume = value)
                                        .withValueFormat("options.percent_value"),
                                VowSetting.slider(
                                                "playbackSpeed",
                                                70,
                                                200,
                                                () -> config.playbackSpeed,
                                                value -> config.playbackSpeed = value)
                                        .withValueFormat("options.percent_value")),
                        section(
                                "positioning",
                                VowSetting.bool(
                                        "playAllSoundsOnPlayer",
                                        () -> config.playAllSoundsOnPlayer,
                                        value -> config.playAllSoundsOnPlayer = value),
                                VowSetting.slider(
                                                "blockCutOff",
                                                1,
                                                128,
                                                () -> config.blockCutOff,
                                                value -> config.blockCutOff = value)
                                        .withValueFormat("wynnvp.config.value.blocks")),
                        section(
                                "otherGameSounds",
                                VowSetting.bool(
                                        "blockVillagerSoundsDuringVoiceDialog",
                                        () -> config.blockVillagerSoundsDuringVoiceDialog,
                                        value -> config.blockVillagerSoundsDuringVoiceDialog = value),
                                VowSetting.bool(
                                        "routeMasterSoundsToBlocks",
                                        () -> config.routeMasterSoundsToBlocks,
                                        value -> config.routeMasterSoundsToBlocks = value)),
                        section(
                                "downloading",
                                VowSetting.bool(
                                                "downloadSounds",
                                                () -> config.downloadSounds,
                                                value -> config.downloadSounds = value)
                                        .enabledWhen(() -> !BetaConfig.isBetaBuild()))),
                category(
                        "acoustics",
                        section(
                                "reverb",
                                VowSetting.bool(
                                        "enableReverb",
                                        () -> config.enableReverb,
                                        value -> config.enableReverb = value)),
                        section(
                                "soundPhysics",
                                VowSetting.bool(
                                        "soundPhysics",
                                        () -> config.soundPhysics,
                                        value -> config.soundPhysics = value),
                                VowSetting.slider(
                                                "soundPhysicsReverbStrength",
                                                0,
                                                200,
                                                () -> config.soundPhysicsReverbStrength,
                                                value -> config.soundPhysicsReverbStrength = value)
                                        .withValueFormat("options.percent_value")
                                        .enabledWhen(() -> config.soundPhysics && config.enableReverb),
                                VowSetting.slider(
                                                "soundPhysicsOcclusionStrength",
                                                0,
                                                100,
                                                () -> config.soundPhysicsOcclusionStrength,
                                                value -> config.soundPhysicsOcclusionStrength = value)
                                        .withValueFormat("options.percent_value")
                                        .enabledWhen(() -> config.soundPhysics),
                                VowSetting.slider(
                                                "soundPhysicsRays",
                                                8,
                                                96,
                                                () -> config.soundPhysicsRays,
                                                value -> config.soundPhysicsRays = value)
                                        .withValueFormat("wynnvp.config.value.rays")
                                        .enabledWhen(() -> config.soundPhysics))),
                category(
                        "dialogue",
                        section(
                                "autoProgress",
                                VowSetting.bool(
                                        "autoProgress",
                                        () -> config.autoProgress,
                                        value -> config.autoProgress = value)),
                        section(
                                "earlyPlayback",
                                VowSetting.bool(
                                        "earlyPlayOverlay",
                                        () -> config.earlyPlayOverlay,
                                        value -> config.earlyPlayOverlay = value),
                                VowSetting.slider(
                                                "earlyPlayOverlayMinChars",
                                                1,
                                                100,
                                                () -> config.earlyPlayOverlayMinChars,
                                                value -> config.earlyPlayOverlayMinChars = value)
                                        .withValueFormat("wynnvp.config.value.characters")
                                        .enabledWhen(() -> config.earlyPlayOverlay)),
                        section(
                                "yourCharacter",
                                VowSetting.text(
                                        "nicknameOverride",
                                        () -> config.nicknameOverride,
                                        value -> config.nicknameOverride = value))),
                category(
                        "reporting",
                        section(
                                "missingLines",
                                VowSetting.bool(
                                        "reportMissingLines",
                                        () -> config.reportMissingLines,
                                        value -> config.reportMissingLines = value),
                                VowSetting.bool("anonymous", () -> config.anonymous, value -> config.anonymous = value)
                                        .enabledWhen(() -> config.reportMissingLines),
                                VowSetting.bool(
                                                "sendFunFact",
                                                () -> config.sendFunFact,
                                                value -> config.sendFunFact = value)
                                        .enabledWhen(() -> config.reportMissingLines))),
                category(
                        "debugAndLogs",
                        section(
                                "logging",
                                VowSetting.bool(
                                        DEBUG_PREFIX + "logDialogueLines",
                                        () -> debug.logDialogueLines,
                                        value -> debug.logDialogueLines = value),
                                VowSetting.bool(
                                                DEBUG_PREFIX + "onlyLogNotPlayingLines",
                                                () -> debug.onlyLogNotPlayingLines,
                                                value -> debug.onlyLogNotPlayingLines = value)
                                        .enabledWhen(() -> debug.logDialogueLines),
                                VowSetting.bool(
                                        DEBUG_PREFIX + "logPlayingInformation",
                                        () -> debug.logPlayingInformation,
                                        value -> debug.logPlayingInformation = value),
                                VowSetting.bool(
                                        DEBUG_PREFIX + "logOverlayDialogueToChat",
                                        () -> debug.logOverlayDialogueToChat,
                                        value -> debug.logOverlayDialogueToChat = value),
                                VowSetting.bool(
                                        DEBUG_PREFIX + "logOverlayPackets",
                                        () -> debug.logOverlayPackets,
                                        value -> debug.logOverlayPackets = value),
                                VowSetting.bool(
                                        DEBUG_PREFIX + "logBlockedSounds",
                                        () -> debug.logBlockedSounds,
                                        value -> debug.logBlockedSounds = value),
                                VowSetting.bool(
                                        DEBUG_PREFIX + "logAutoProgress",
                                        () -> debug.logAutoProgress,
                                        value -> debug.logAutoProgress = value)),
                        section(
                                "visualisation",
                                VowSetting.bool(
                                        DEBUG_PREFIX + "highlightSpeaker",
                                        () -> debug.highlightSpeaker,
                                        value -> debug.highlightSpeaker = value),
                                VowSetting.bool(
                                        DEBUG_PREFIX + "debugSoundPhysics",
                                        () -> debug.debugSoundPhysics,
                                        value -> debug.debugSoundPhysics = value)),
                        section(
                                "customSources",
                                VowSetting.bool(
                                        DEBUG_PREFIX + "useCustomAudioPath",
                                        () -> debug.useCustomAudioPath,
                                        value -> debug.useCustomAudioPath = value),
                                VowSetting.text(
                                                DEBUG_PREFIX + "customAudioPath",
                                                () -> debug.customAudioPath,
                                                value -> debug.customAudioPath = value)
                                        .enabledWhen(() -> debug.useCustomAudioPath),
                                VowSetting.bool(
                                        DEBUG_PREFIX + "useCustomSoundsJson",
                                        () -> debug.useCustomSoundsJson,
                                        value -> debug.useCustomSoundsJson = value),
                                VowSetting.text(
                                                DEBUG_PREFIX + "customSoundsJsonPath",
                                                () -> debug.customSoundsJsonPath,
                                                value -> debug.customSoundsJsonPath = value)
                                        .enabledWhen(() -> debug.useCustomSoundsJson))));
    }

    private static Category category(String key, Section... sections) {
        return new Category(key, List.of(sections));
    }

    private static Section section(String key, VowSetting... settings) {
        return new Section(key, List.of(settings));
    }

    public record Category(String key, List<Section> sections) {}

    public record Section(String key, List<VowSetting> settings) {}
}
