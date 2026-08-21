/*
 * Copyright © Team-VoW 2024-2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.npc.NPCHandler;
import com.wynnvp.wynncraftvp.sound.line.LineData;
import com.wynnvp.wynncraftvp.utils.LineFormatter;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class DebugCommand {
    public static void register(CommandDispatcher<VowCommandSource> dispatcher) {
        LiteralArgumentBuilder<VowCommandSource> builder = VowCommands.literal("vow-debug")
                .executes(context -> {
                    context.getSource().sendFeedback(Component.literal("VoW Debug command"));
                    return 1;
                })
                .then(VowCommands.literal("sound-physics").executes(context -> {
                    if (ModCore.instance == null || ModCore.instance.audioPlayer == null) {
                        context.getSource().sendError(Component.literal("Sound engine is not up yet!"));
                        return 0;
                    }

                    // Measure whatever the player is looking at, so an NPC across the room can be
                    // checked without waiting for them to say something.
                    Vec3 target = null;
                    HitResult hit = Minecraft.getInstance().hitResult;
                    if (hit != null && hit.getType() != HitResult.Type.MISS) {
                        target = hit.getLocation();
                    }

                    String summary = ModCore.instance
                            .audioPlayer
                            .openAlPlayer
                            .soundPhysics()
                            .probeForDebug(target, 20);
                    if (summary == null) {
                        context.getSource().sendError(Component.literal("No world loaded."));
                        return 0;
                    }

                    context.getSource().sendFeedback(Component.literal(summary));
                    return 1;
                }))
                .then(VowCommands.literal("tags").executes(context -> {
                    String summary = NPCHandler.describeNametags();
                    ModCore.info(summary);
                    context.getSource().sendFeedback(Component.literal(summary));
                    return 1;
                }))
                .then(VowCommands.literal("npc")
                        .then(VowCommands.argument("name", StringArgumentType.greedyString())
                                .executes(context -> {
                                    String name = StringArgumentType.getString(context, "name");
                                    String summary = NPCHandler.describeSearch(name);
                                    ModCore.info(summary);
                                    context.getSource().sendFeedback(Component.literal(summary));
                                    return 1;
                                })))
                .then(VowCommands.literal("play-first-sound")
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
                        .then(VowCommands.argument("text", StringArgumentType.greedyString())
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
