package com.wynnvp.wynncraftvp.utils;

import com.wynnvp.wynncraftvp.ModCore;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.ClickEvent;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.security.MessageDigest;

public class Utils {


    public static void sendMessage(String text) {
        Minecraft.getMinecraft().player.sendMessage(new TextComponentString("§5[Voices of Wynn]§r " + text));
    }

    public static void sendMessageWithLink(String text, String url) {
        Minecraft.getMinecraft().player.sendMessage(
                new TextComponentString(text).setStyle(new Style().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url))));
    }

    public static String sha256(final String base) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            final byte[] hash = digest.digest(base.getBytes("UTF-8"));
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
