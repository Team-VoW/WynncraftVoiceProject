/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.BuiltInExceptionProvider;
import com.mojang.brigadier.exceptions.CommandExceptionType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.wynnvp.wynncraftvp.ModCore;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;

/**
 * Owns the mod's client side command dispatcher. Replaces Fabric API's client command API, which was
 * the mod's only reason to depend on it. Commands are executed from
 * {@link com.wynnvp.wynncraftvp.events.mixins.MixinClientCommandSender}, which intercepts outgoing
 * commands before they reach the server.
 */
public final class VowCommands {
    private static final CommandDispatcher<VowCommandSource> DISPATCHER = new CommandDispatcher<>();

    private VowCommands() {}

    public static void register() {
        DebugCommand.register(DISPATCHER);
        VowLogCommand.register(DISPATCHER);
        ConfigCommand.register(DISPATCHER);
    }

    public static LiteralArgumentBuilder<VowCommandSource> literal(String name) {
        return LiteralArgumentBuilder.literal(name);
    }

    public static <T> RequiredArgumentBuilder<VowCommandSource, T> argument(String name, ArgumentType<T> type) {
        return RequiredArgumentBuilder.argument(name, type);
    }

    /**
     * Executes a command typed by the player, with the leading slash already removed.
     *
     * @return true when the command was one of ours and must not be forwarded to the server
     */
    public static boolean execute(String command) {
        VowCommandSource source = new VowCommandSource(Minecraft.getInstance());

        try {
            DISPATCHER.execute(command, source);
            return true;
        } catch (CommandSyntaxException e) {
            if (isUnknownCommand(e.getType())) {
                // Not one of our commands, let the server handle it.
                return false;
            }

            ModCore.warn("Syntax exception for Voices of Wynn command '" + command + "'", e);
            source.sendError(getErrorMessage(e));
            return true;
        } catch (Exception e) {
            ModCore.warn("Error while executing Voices of Wynn command '" + command + "'", e);
            source.sendError(Component.nullToEmpty(e.getMessage()));
            return true;
        }
    }

    /**
     * Only unknown commands and node parse failures mean "this is not our command". The remaining
     * dispatcher exceptions only happen once a command of ours has already been matched, so those
     * must not fall through to the server.
     */
    private static boolean isUnknownCommand(CommandExceptionType type) {
        BuiltInExceptionProvider builtins = CommandSyntaxException.BUILT_IN_EXCEPTIONS;
        return type == builtins.dispatcherUnknownCommand() || type == builtins.dispatcherParseException();
    }

    private static Component getErrorMessage(CommandSyntaxException e) {
        Component message = ComponentUtils.fromMessage(e.getRawMessage());
        String context = e.getContext();

        if (context == null) {
            return message;
        }
        return Component.translatable("command.context.parse_error", message, e.getCursor(), context);
    }
}
