/*
 * Copyright © Team-VoW 2024-2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.line;

import java.util.regex.Pattern;

public class LineData {
    private String soundLine;
    private String realLine;

    public String getSoundLine() {
        return soundLine;
    }

    public void setSoundLine(String soundLine) {
        this.soundLine = soundLine;
    }

    public String getRealLine() {
        return realLine;
    }

    public void setRealLine(String realLine) {
        this.realLine = realLine;
    }

    public static final Pattern NPC_DIALOGUE_PATTERN = Pattern.compile("^\\[\\d+/\\d+].+: .");
    // Overlay dialogue arrives as "NpcName: text" without a [N/M] prefix
    private static final Pattern OVERLAY_NPC_PATTERN = Pattern.compile("^[A-Z][a-zA-Z ]+: .");

    public String getNPCName() {
        String[] split = realLine.split(": ");
        String name = split[0];
        name = name.substring(name.indexOf("]") + 1);

        return name.trim().toLowerCase().replaceAll("[^a-z\\d?]", "");
    }

    public boolean isNPCSentLine() {
        return NPC_DIALOGUE_PATTERN.matcher(realLine).find()
                || OVERLAY_NPC_PATTERN.matcher(realLine).find();
    }
}
