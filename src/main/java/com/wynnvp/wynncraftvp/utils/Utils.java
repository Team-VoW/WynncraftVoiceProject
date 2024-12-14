/*
 * Copyright © Team-VoW 2024.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.utils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class Utils {
    public static void sendMessage(String text) {
        // && Minecraft.getInstance().inGameHud != null && Minecraft.getInstance().inGameHud.getChatHud() != null
        if (Minecraft.getInstance().player != null)
            Minecraft.getInstance().gui.getChat().addMessage(Component.literal("§5[Voices of Wynn]§r " + text));
    }

    public static void appendMessageWithLinkAndSend(String text, String url, String clickText) {
        if (Minecraft.getInstance() != null
                && Minecraft.getInstance().gui != null
                && Minecraft.getInstance().gui.getChat() != null) {
            MutableComponent mutableText = Component.literal("§r " + text).copy();
            mutableText.append(Component.literal(clickText)
                    .setStyle(mutableText.getStyle().withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url))));

            Minecraft.getInstance().gui.getChat().addMessage(mutableText);
        }
    }

    public static String HTTPEncode(String input) {
        return URLEncoder.encode(input, StandardCharsets.UTF_8);
    }

    public static String sha256(final String base) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            final byte[] hash = digest.digest(base.getBytes(StandardCharsets.UTF_8));
            final StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                final String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static Minecraft mc() {
        return Minecraft.getInstance();
    }

    public static LocalPlayer player() {
        return mc().player;
    }
}
