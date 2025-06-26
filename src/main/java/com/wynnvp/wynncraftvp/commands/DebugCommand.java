/*
 * Copyright © Team-VoW 2024-2025.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.sound.SoundObject;
import com.wynnvp.wynncraftvp.sound.line.LineData;
import com.wynnvp.wynncraftvp.utils.LineFormatter;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class DebugCommand {



    public static void register(CommandDispatcher<CommandSourceStack> commandSourceStackCommandDispatcher, CommandBuildContext commandBuildContext, Commands.CommandSelection commandSelection) {
        LiteralArgumentBuilder<CommandSourceStack> builder = Commands.literal("vow-debug")
                .executes(context -> {
                    context.getSource().sendSuccess(() -> Component.literal("VoW Debug command"), false);
                    return 1;
                })
                .then(Commands.literal("play-first-sound")
                        .executes(context -> {
                            if (ModCore.instance == null || ModCore.instance.soundsHandler == null || ModCore.instance.soundPlayer == null) {
                                context.getSource().sendFailure(Component.literal("ModCore not initialized correctly!"));
                                return 0;
                            }

                            var sounds = ModCore.instance.soundsHandler.getSounds();
                            if (sounds.isEmpty()) {
                                context.getSource().sendFailure(Component.literal("No sounds loaded!"));
                                return 0;
                            }

                            SoundObject firstSound = sounds.values().iterator().next();
                            LineData lineData = LineFormatter.formatToLineData("[1/15] Theorick: I could've sworn she had gone this way... Oh, what now, more imbeciles? This forest isn't safe to travel!");
                            ModCore.instance.soundPlayer.playSound(lineData);

                            context.getSource().sendSuccess(() -> Component.literal("Playing first sound: " + lineData.getRealLine()), false);
                            return 1;
                        }));

        commandSourceStackCommandDispatcher.register(builder);
    }
}
