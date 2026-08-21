/*
 * Copyright © Team-VoW 2024-2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.utils;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;

public class Utils {
    private static final Pattern URL_PATTERN =
            Pattern.compile("https?://[\\w\\-._~:/?#\\[\\]@!$&'()*+,;=%]+", Pattern.CASE_INSENSITIVE);

    public static void runOnClientThread(Runnable runnable) {
        Minecraft mc = mc();
        if (mc == null) return;

        if (mc.isSameThread()) {
            runnable.run();
        } else {
            mc.execute(runnable);
        }
    }

    public static void sendMessage(String text) {
        if (!Minecraft.getInstance().isSameThread()) {
            runOnClientThread(() -> sendMessage(text));
            return;
        }

        if (Minecraft.getInstance().player == null) return;

        MutableComponent message = Component.literal("§5[Voices of Wynn]§r ");

        // Check if the text contains URLs
        Matcher matcher = URL_PATTERN.matcher(text);
        if (matcher.find()) {
            // Reset matcher to start from beginning
            matcher.reset();
            int lastEnd = 0;

            while (matcher.find()) {
                // Add text before the URL
                if (matcher.start() > lastEnd) {
                    message.append(Component.literal(text.substring(lastEnd, matcher.start())));
                }

                // Add clickable URL
                String url = matcher.group();
                message.append(Component.literal(url)
                        .setStyle(message.getStyle()
                                .withClickEvent(new ClickEvent.OpenUrl(URI.create(url)))
                                .withColor(ChatFormatting.AQUA)
                                .withUnderlined(true)));

                lastEnd = matcher.end();
            }

            // Add remaining text after the last URL
            if (lastEnd < text.length()) {
                message.append(Component.literal(text.substring(lastEnd)));
            }
        } else {
            // No URLs found, just add the text as is
            message.append(Component.literal(text));
        }

        Minecraft.getInstance().gui.getChat().addMessage(message);
    }

    /**
     * Sends a mod message with a button on the end that runs one of the mod's own commands.
     *
     * <p>The command never reaches the server: {@code MixinClientCommandSender} intercepts it, exactly
     * as it does for one the player typed.
     *
     * @param command the command to run, with its leading slash
     * @param clickText the label for the button, appended after the text
     * @param hoverText what to show when the button is hovered
     */
    public static void sendMessageWithCommand(String text, String command, String clickText, String hoverText) {
        if (!Minecraft.getInstance().isSameThread()) {
            runOnClientThread(() -> sendMessageWithCommand(text, command, clickText, hoverText));
            return;
        }

        if (Minecraft.getInstance().player == null) return;

        MutableComponent message = Component.literal("§5[Voices of Wynn]§r ").append(Component.literal(text));
        message.append(Component.literal(clickText)
                .setStyle(Style.EMPTY
                        .withClickEvent(new ClickEvent.RunCommand(command))
                        .withHoverEvent(new HoverEvent.ShowText(Component.literal(hoverText)))
                        .withColor(ChatFormatting.GREEN)
                        .withUnderlined(true)));

        Minecraft.getInstance().gui.getChat().addMessage(message);
    }

    public static void appendMessageWithLinkAndSend(String text, String url, String clickText) {
        if (!Minecraft.getInstance().isSameThread()) {
            runOnClientThread(() -> appendMessageWithLinkAndSend(text, url, clickText));
            return;
        }

        if (Minecraft.getInstance() != null
                && Minecraft.getInstance().gui != null
                && Minecraft.getInstance().gui.getChat() != null) {
            MutableComponent mutableText = Component.literal("§r " + text).copy();
            mutableText.append(Component.literal(clickText)
                    .setStyle(mutableText.getStyle().withClickEvent(new ClickEvent.OpenUrl(URI.create(url)))));

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
