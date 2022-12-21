package com.wynnvp.wynncraftvp.utils;

import com.wynnvp.wynncraftvp.ModCore;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.text.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Utils {

    public static void sendMessage(String text) {
        if (MinecraftClient.getInstance() != null && MinecraftClient.getInstance().inGameHud != null && MinecraftClient.getInstance().inGameHud.getChatHud() != null)
            MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of("§5[Voices of Wynn]§r " + text));
    }

    public static void sendMessageWithLink(String text, String url){
        if (MinecraftClient.getInstance() != null && MinecraftClient.getInstance().inGameHud != null && MinecraftClient.getInstance().inGameHud.getChatHud() != null){

            MutableText mutableText = Text.of("§r " + text).copy();
            mutableText.styled(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url)));

            MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(mutableText);

        }
    }

    public static String sha256(final String base) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            final byte[] hash = digest.digest(base.getBytes(StandardCharsets.UTF_8));
            final StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                final String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
