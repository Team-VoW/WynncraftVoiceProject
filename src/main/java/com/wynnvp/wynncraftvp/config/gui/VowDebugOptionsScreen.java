/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.config.gui;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.config.VOWAutoConfig;
import java.util.List;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

/**
 * Logging and troubleshooting options.
 * <p>
 * These live on their own screen so the main settings screen stays about playback.
 */
public class VowDebugOptionsScreen extends VowOptionsScreen {
    private static final String PREFIX = "debugAndLogs.";

    public VowDebugOptionsScreen(Screen parent) {
        super(parent, Component.translatable("wynnvp.config.category.debugAndLogs"));
    }

    @Override
    protected void addOptions() {
        VOWAutoConfig.DebugAndLogs debug = ModCore.config.debugAndLogs;

        addPaired(List.<OptionInstance<?>>of(
                bool(PREFIX + "highlightSpeaker", debug.highlightSpeaker, v -> debug.highlightSpeaker = v),
                bool(PREFIX + "logDialogueLines", debug.logDialogueLines, v -> debug.logDialogueLines = v),
                bool(
                        PREFIX + "onlyLogNotPlayingLines",
                        debug.onlyLogNotPlayingLines,
                        v -> debug.onlyLogNotPlayingLines = v),
                bool(
                        PREFIX + "logPlayingInformation",
                        debug.logPlayingInformation,
                        v -> debug.logPlayingInformation = v),
                bool(
                        PREFIX + "logOverlayDialogueToChat",
                        debug.logOverlayDialogueToChat,
                        v -> debug.logOverlayDialogueToChat = v),
                bool(PREFIX + "logOverlayPackets", debug.logOverlayPackets, v -> debug.logOverlayPackets = v),
                bool(PREFIX + "useCustomAudioPath", debug.useCustomAudioPath, v -> debug.useCustomAudioPath = v),
                bool(PREFIX + "useCustomSoundsJson", debug.useCustomSoundsJson, v -> debug.useCustomSoundsJson = v)));

        addTextRow(PREFIX + "customAudioPath", debug.customAudioPath, v -> debug.customAudioPath = v);
        addTextRow(PREFIX + "customSoundsJsonPath", debug.customSoundsJsonPath, v -> debug.customSoundsJsonPath = v);
    }
}
