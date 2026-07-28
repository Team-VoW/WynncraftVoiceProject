/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.wynnvp.wynncraftvp.config.gui.VowConfigScreen;
import net.minecraft.client.Minecraft;

/** Opens the settings screen, so the mod is configurable without Mod Menu installed. */
public class ConfigCommand {
    public static void register(CommandDispatcher<VowCommandSource> dispatcher) {
        LiteralArgumentBuilder<VowCommandSource> builder = VowCommands.literal("vow-config")
                .executes(context -> {
                    Minecraft client = context.getSource().getClient();
                    // Deferred, because the chat screen closes right after the command runs and
                    // would replace a screen opened here directly.
                    client.execute(() -> client.setScreen(new VowConfigScreen(null)));
                    return 1;
                });

        dispatcher.register(builder);
    }
}
