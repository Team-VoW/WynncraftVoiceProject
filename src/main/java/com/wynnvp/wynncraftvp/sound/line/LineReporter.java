/*
 * Copyright © Team-VoW 2024.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.line;

import static com.wynnvp.wynncraftvp.ModCore.config;
import static com.wynnvp.wynncraftvp.utils.Utils.HTTPEncode;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.utils.VersionChecker;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class LineReporter {
    private final Queue<String> reportedLines;

    public LineReporter() {
        reportedLines = new LinkedList<>();
    }

    public void MissingLine(LineData lineData) {
        if (!config.isReportMissingLines()
                || !ModCore.inLiveWynnServer
                || !lineData.isNPCSentLine()
                || !VersionChecker.isOnUpToDateVersion) return;

        CompletableFuture.runAsync(() -> {
            if (reportedLines.contains(lineData.getRealLine())) {
                return;
            }
            reportedLines.add(lineData.getRealLine());

            if (reportedLines.size() > 20) {
                reportedLines.remove();
            }
            try {
                reportUnvoicedLine(lineData);
                System.out.println(
                        "Unvoiced line report has been sent to our servers. This contained: " + lineData.getRealLine());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(
                        "A report of unvoiced line couldn't be sent. Error code should be right above this message.");
            }
        });
    }

    private void reportUnvoicedLine(LineData lineData) throws IOException {
        String npcName = lineData.getNPCName();
        String name = config.isAnonymous()
                ? "anonymous"
                : Minecraft.getInstance().player.getName().toString();
        String fullLine = lineData.getRealLine();
        Player p = Minecraft.getInstance().player;
        int CoordX = (int) p.position().x;
        int CoordY = (int) p.position().y;
        int CoordZ = (int) p.position().z;

        URL urlObj = new URL("http://voicesofwynn.com/api/unvoiced-line-report/new");
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("User-Agent", "VoicesOfWynnModClient");
        connection.setDoOutput(true);
        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        fullLine = HTTPEncode(fullLine);
        npcName = HTTPEncode(npcName);
        name = HTTPEncode(name);
        String urlPostParameters = "npc=" + npcName + "&player=" + name + "&full=" + fullLine + "&x=" + CoordX + "&y="
                + CoordY + "&z=" + CoordZ + "&apiKey=" + config.getWord();
        outputStream.writeBytes(urlPostParameters);
        outputStream.flush();
        outputStream.close();
        Integer responseCode = connection.getResponseCode();
        System.out.println("HTTP response Code : " + responseCode);
    }
}
