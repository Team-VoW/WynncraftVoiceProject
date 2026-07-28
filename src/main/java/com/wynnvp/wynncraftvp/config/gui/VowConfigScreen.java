/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.config.gui;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.config.VOWAutoConfig;
import java.util.List;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

/** The mod's main settings screen, reachable through Mod Menu. */
public class VowConfigScreen extends VowOptionsScreen {
    public VowConfigScreen(Screen parent) {
        super(parent, Component.translatable("wynnvp.config.title"));
    }

    @Override
    protected void addOptions() {
        VOWAutoConfig config = ModCore.config;

        addPaired(List.<OptionInstance<?>>of(
                bool("playAllSoundsOnPlayer", config.playAllSoundsOnPlayer, v -> config.playAllSoundsOnPlayer = v),
                slider("voiceVolume", 1, 1000, config.voiceVolume, v -> config.voiceVolume = v),
                slider("playbackSpeed", 70, 200, config.playbackSpeed, v -> config.playbackSpeed = v),
                slider("blockCutOff", 1, 128, config.blockCutOff, v -> config.blockCutOff = v),
                bool("enableReverb", config.enableReverb, v -> config.enableReverb = v),
                bool(
                        "blockVillagerSoundsDuringVoiceDialog",
                        config.blockVillagerSoundsDuringVoiceDialog,
                        v -> config.blockVillagerSoundsDuringVoiceDialog = v),
                bool("autoProgress", config.autoProgress, v -> config.autoProgress = v),
                bool("downloadSounds", config.downloadSounds, v -> config.downloadSounds = v),
                bool("earlyPlayOverlay", config.earlyPlayOverlay, v -> config.earlyPlayOverlay = v),
                slider(
                        "earlyPlayOverlayMinChars",
                        1,
                        100,
                        config.earlyPlayOverlayMinChars,
                        v -> config.earlyPlayOverlayMinChars = v),
                bool("reportMissingLines", config.reportMissingLines, v -> config.reportMissingLines = v),
                bool("anonymous", config.anonymous, v -> config.anonymous = v),
                bool("sendFunFact", config.sendFunFact, v -> config.sendFunFact = v)));

        addTextRow("nicknameOverride", config.nicknameOverride, v -> config.nicknameOverride = v);

        list.addFull(Button.builder(
                        Component.translatable("wynnvp.config.debugAndLogs.open"),
                        button -> minecraft.setScreen(new VowDebugOptionsScreen(this)))
                .build());
    }
}
