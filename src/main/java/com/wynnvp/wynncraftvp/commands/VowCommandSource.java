/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.commands;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

/**
 * Command source for the mod's client side commands. Replaces Fabric API's FabricClientCommandSource
 * and reproduces its chat output so command feedback looks unchanged.
 */
public record VowCommandSource(Minecraft client) {
    public void sendFeedback(Component message) {
        client.gui.getChat().addMessage(message);
        client.getNarrator().saySystemChatQueued(message);
    }

    public void sendError(Component message) {
        sendFeedback(Component.empty().append(message).withStyle(ChatFormatting.RED));
    }

    public Minecraft getClient() {
        return client;
    }
}
