/*
 * Copyright © Team-VoW 2024-2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.sound.line.LineData;
import com.wynnvp.wynncraftvp.utils.LineFormatter;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.network.chat.Component;

public class DebugCommand {
    public static void register(
            CommandDispatcher<FabricClientCommandSource> dispatcher, CommandBuildContext buildContext) {
        LiteralArgumentBuilder<FabricClientCommandSource> builder = ClientCommandManager.literal("vow-debug")
                .executes(context -> {
                    context.getSource().sendFeedback(Component.literal("VoW Debug command"));
                    return 1;
                })
                .then(ClientCommandManager.literal("play-first-sound")
                        .executes(context -> {
                            if (ModCore.instance == null
                                    || ModCore.instance.soundsHandler == null
                                    || ModCore.instance.soundPlayer == null) {
                                context.getSource().sendError(Component.literal("ModCore not initialized correctly!"));
                                return 0;
                            }

                            var sounds = ModCore.instance.soundsHandler.getSounds();
                            if (sounds.isEmpty()) {
                                context.getSource().sendError(Component.literal("No sounds loaded!"));
                                return 0;
                            }

                            LineData lineData = LineFormatter.formatToLineData(
                                    "[1/15] Theorick: I could've sworn she had gone this way... Oh, what now, more imbeciles? This forest isn't safe to travel!");
                            ModCore.instance.soundPlayer.playSound(lineData);

                            context.getSource()
                                    .sendFeedback(Component.literal("Playing first sound: " + lineData.getRealLine()));
                            return 1;
                        })
                        .then(ClientCommandManager.argument("text", StringArgumentType.greedyString())
                                .executes(context -> {
                                    if (ModCore.instance == null
                                            || ModCore.instance.soundsHandler == null
                                            || ModCore.instance.soundPlayer == null) {
                                        context.getSource()
                                                .sendError(Component.literal("ModCore not initialized correctly!"));
                                        return 0;
                                    }

                                    var sounds = ModCore.instance.soundsHandler.getSounds();
                                    if (sounds.isEmpty()) {
                                        context.getSource().sendError(Component.literal("No sounds loaded!"));
                                        return 0;
                                    }

                                    String customText = StringArgumentType.getString(context, "text");
                                    LineData lineData = LineFormatter.formatToLineData(customText);
                                    ModCore.instance.soundPlayer.playSound(lineData);

                                    context.getSource()
                                            .sendFeedback(Component.literal(
                                                    "Playing sound with custom text: " + lineData.getRealLine()));
                                    return 1;
                                })));

        dispatcher.register(builder);
    }
}
