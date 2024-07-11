/*
 * Copyright © Team-VoW 2023-2024.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.text;

/*
 * This file originates from © Wynntils 2023 https://github.com/Wynntils/Artemis/ but was modified to fit this project
 */
public class CustomColor {
    public static final CustomColor NONE = new CustomColor(-1, -1, -1, -1);

    public final int r;
    public final int g;
    public final int b;
    public final int a;

    public CustomColor(int r, int g, int b, int a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    /**
     * 0xAARRGGBB format
     */
    public static CustomColor fromInt(int num) {
        return new CustomColor(num >> 16 & 255, num >> 8 & 255, num & 255, num >> 24 & 255);
    }

    /**
     * 0xAARRGGBB format
     */
    public int asInt() {
        int a = Math.min(this.a, 255);
        int r = Math.min(this.r, 255);
        int g = Math.min(this.g, 255);
        int b = Math.min(this.b, 255);
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    /**
     * #rrggbb(aa) format
     */
    public String toHexString() {
        String colorHex = String.format("%06x", (0xFFFFFF & (r << 16) | (g << 8) | b));

        // Only append alpha if it's not 255
        if (a != 255) {
            String alphaHex = String.format("%02x", (0xFF & a));
            colorHex += alphaHex;
        }

        colorHex = "#" + colorHex;

        return colorHex;
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
}
