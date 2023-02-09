package com.wynnvp.wynncraftvp.utils;

import com.wynnvp.wynncraftvp.sound.line.LineData;

import java.util.regex.Pattern;

public class LineFormatter {

    private static final Pattern NPC_DIALOGUE_PATTERN = Pattern.compile("^\\[\\d+/\\d+].+: .");

    public static LineData formatToLineData(String message) {
        LineData lineData = new LineData();

        message = ChatHandler.extractMessage(message);
        message = message.trim();
        lineData.setRealLine(message);
        message = message.toLowerCase();
        message = message.replaceAll("[^abcdefghijklmnopqrstuvwxyz?.!0123456789/]", "");

        lineData.setSoundLine(message);
        return lineData;
    }

    public static boolean isNPCSentLine(String line) {
        return NPC_DIALOGUE_PATTERN.matcher(line).find();
    }
}
