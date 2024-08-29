/*
 * Copyright © Team-VoW 2022-2024.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.text;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.ChatFormatting;

public class CustomColor {
    public static final CustomColor NONE = new CustomColor(-1, -1, -1, -1);

    private static final Pattern HEX_PATTERN = Pattern.compile("#?([0-9a-fA-F]{6})([0-9a-fA-F]{2})?");
    private static final Pattern STRING_PATTERN = Pattern.compile("rgba\\((\\d+),(\\d+),(\\d+),(\\d+)\\)");
    private static final Map<String, CustomColor> REGISTERED_HASHED_COLORS = new HashMap<>();

    public final int r;
    public final int g;
    public final int b;
    public final int a;

    public CustomColor(int r, int g, int b) {
        this(r, g, b, 255);
    }

    public CustomColor(int r, int g, int b, int a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public CustomColor(float r, float g, float b) {
        this(r, g, b, 1f);
    }

    public CustomColor(float r, float g, float b, float a) {
        this.r = (int) (r * 255);
        this.g = (int) (g * 255);
        this.b = (int) (b * 255);
        this.a = (int) (a * 255);
    }

    public CustomColor(CustomColor color) {
        this(color.r, color.g, color.b, color.a);
    }

    public CustomColor(CustomColor color, int alpha) {
        this(color.r, color.g, color.b, alpha);
    }

    public CustomColor(String toParse) {
        String noSpace = toParse.replace(" ", "");

        CustomColor parseTry = CustomColor.fromString(noSpace);

        if (parseTry == CustomColor.NONE) {
            parseTry = CustomColor.fromHexString(noSpace);

            if (parseTry == CustomColor.NONE) {
                throw new RuntimeException("Failed to parse CustomColor");
            }
        }

        this.r = parseTry.r;
        this.g = parseTry.g;
        this.b = parseTry.b;
        this.a = parseTry.a;
    }

    public static CustomColor fromChatFormatting(ChatFormatting cf) {
        return fromInt(cf.getColor() | 0xFF000000);
    }

    /**
     * This method takes a color in the format 0x(AA)RRGGBB.
     * If the alpha is not set, it will be set to 255.
     * If the alpha is set to 0, it will be set to 255. If 0 is desired, use {@link CustomColor#withAlpha(int)}
     * @param num the color
     * @return the color
     */
    public static CustomColor fromInt(int num) {
        // if alpha is not set, set it to 255
        if ((num & 0xFF000000) == 0) num |= 0xFF000000;
        return new CustomColor(num >> 16 & 255, num >> 8 & 255, num & 255, num >> 24 & 255);
    }

    /** "#rrggbb(aa)" or "rrggbb(aa)" */
    public static CustomColor fromHexString(String hex) {
        Matcher hexMatcher = HEX_PATTERN.matcher(hex.trim());

        // invalid format
        if (!hexMatcher.matches()) return CustomColor.NONE;

        // parse hex
        if (hexMatcher.group(2) == null) {
            return fromInt(Integer.parseInt(hexMatcher.group(1), 16)).withAlpha(255);
        } else {
            return fromInt(Integer.parseInt(hexMatcher.group(1), 16))
                    .withAlpha(Integer.parseInt(hexMatcher.group(2), 16));
        }
    }

    /** "rgba(r,g,b,a)" format as defined in toString() */
    public static CustomColor fromString(String string) {
        Matcher stringMatcher = STRING_PATTERN.matcher(string.trim());

        // invalid format
        if (!stringMatcher.matches()) return CustomColor.NONE;

        return new CustomColor(
                Integer.parseInt(stringMatcher.group(1)),
                Integer.parseInt(stringMatcher.group(2)),
                Integer.parseInt(stringMatcher.group(3)),
                Integer.parseInt(stringMatcher.group(4)));
    }

    public CustomColor withAlpha(int a) {
        return new CustomColor(this, a);
    }

    public CustomColor withAlpha(float a) {
        return new CustomColor(this, (int) (a * 255));
    }

    /** 0xAARRGGBB format */
    public int asInt() {
        int a = Math.min(this.a, 255);
        int r = Math.min(this.r, 255);
        int g = Math.min(this.g, 255);
        int b = Math.min(this.b, 255);
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    public float[] asFloatArray() {
        return new float[] {r / 255f, g / 255f, b / 255f};
    }

    /**
     * #rrggbbaa format
     * The alpha is always included, so it can be parsed more easily
     */
    public String toHexString() {
        return "#" + String.format("%08x", ((r << 24) | (g << 16) | (b << 8) | a));
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CustomColor color)) return false;

        // colors are equal as long as rgba values match
        return (this.r == color.r && this.g == color.g && this.b == color.b && this.a == color.a);
    }

    @Override
    public String toString() {
        return toHexString();
    }

    public static class CustomColorSerializer implements JsonSerializer<CustomColor>, JsonDeserializer<CustomColor> {
        @Override
        public CustomColor deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            CustomColor customColor = CustomColor.fromHexString(json.getAsString());
            return customColor == NONE ? CustomColor.fromString(json.getAsString()) : customColor;
        }

        @Override
        public JsonElement serialize(CustomColor src, Type typeOfSrc, JsonSerializationContext context) {
            return context.serialize(src.toString());
        }
    }
}
