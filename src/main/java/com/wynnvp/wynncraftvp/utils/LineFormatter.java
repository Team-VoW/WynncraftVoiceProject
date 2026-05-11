/*
 * Copyright © Team-VoW 2024-2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.utils;

import static com.wynnvp.wynncraftvp.utils.Utils.HTTPEncode;

import com.wynnvp.wynncraftvp.sound.line.LineData;

public class LineFormatter {
    public static LineData formatToLineData(String message) {
        LineData lineData = new LineData();

        message = message.trim();
        lineData.setRealLine(message);

        // Wynncraft is inconsistent about quote glyphs in overlay text vs sounds.json.
        // Ignore quote characters before URL encoding so ASCII, smart quotes, and doubled
        // single-quote placeholders all resolve to the same sound key.
        message = message.replaceAll("[\"'‘’“”]", "");

        message = HTTPEncode(message);

        message = message.toLowerCase();

        // Temporary fix for to get rid of formatting
        message = message.replaceAll("§[0-9a-r]", "");

        message = message.replaceAll("[^abcdefghijklmnopqrstuvwxyz?.!0123456789/]", "");

        lineData.setSoundLine(message);
        return lineData;
    }
}
