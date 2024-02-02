package com.wynnvp.wynncraftvp.utils;

import com.wynnvp.wynncraftvp.sound.line.LineData;

import static com.wynnvp.wynncraftvp.utils.Utils.HTTPEncode;

public class LineFormatter {


    public static LineData formatToLineData(String message) {
        LineData lineData = new LineData();

        message = ChatHandler.extractMessage(message);
        message = message.trim();
        lineData.setRealLine(message);

        message = message.replace("’", "'");

        message = HTTPEncode(message);

        message = message.toLowerCase();
        message = message.replaceAll("[^abcdefghijklmnopqrstuvwxyz?.!0123456789/]", "");

        lineData.setSoundLine(message);
        return lineData;
    }

}
