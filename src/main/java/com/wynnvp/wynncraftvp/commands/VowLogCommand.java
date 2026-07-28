/*
 * Copyright © Team-VoW 2024-2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.wynnvp.wynncraftvp.logging.VowLogger;
import net.minecraft.network.chat.Component;

public class VowLogCommand {
    public static void register(CommandDispatcher<VowCommandSource> dispatcher) {
        LiteralArgumentBuilder<VowCommandSource> builder = VowCommands.literal("vowlog")
                .then(VowCommands.argument("message", StringArgumentType.greedyString())
                        .executes(context -> {
                            String message = StringArgumentType.getString(context, "message");
                            VowLogger.logComment(message);
                            context.getSource()
                                    .sendFeedback(Component.literal("Added comment to vowLog: // " + message));
                            return 1;
                        }));

        dispatcher.register(builder);
    }
}
