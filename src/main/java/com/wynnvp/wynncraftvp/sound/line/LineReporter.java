/*
 * Copyright © Team-VoW 2024-2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.line;

import static com.wynnvp.wynncraftvp.ModCore.config;
import static com.wynnvp.wynncraftvp.utils.Utils.HTTPEncode;

import com.wynnvp.wynncraftvp.ModCore;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class LineReporter {
    /**
     * Ticks a report is held before it is sent. The overlay state machine can fire a line while the
     * typewriter animation is still running (a stalled packet stream looks like a finished line), so
     * a report is buffered long enough for the rest of the line to arrive and supersede it.
     */
    private static final int REPORT_DELAY_TICKS = 60;

    private final Queue<String> reportedLines;

    private LineData pendingReport;
    private int pendingReportTicks;

    public LineReporter() {
        reportedLines = new ConcurrentLinkedQueue<>();
    }

    public void MissingLine(LineData lineData) {
        if (!config.isReportMissingLines() || !ModCore.inLiveWynnServer) return;

        LineData supersededFinalLine = null;
        synchronized (this) {
            if (pendingReport != null) {
                String pending = stripWhitespace(pendingReport.getRealLine());
                String incoming = stripWhitespace(lineData.getRealLine());

                if (incoming.startsWith(pending)) {
                    // The typewriter kept going — what we held was only a partial of this line.
                    pendingReport = lineData;
                    pendingReportTicks = 0;
                    return;
                }
                if (pending.startsWith(incoming)) {
                    // A shorter version of the line we already hold; keep the longer one.
                    return;
                }
                // An unrelated line started, so the buffered one is final. Send it now.
                supersededFinalLine = pendingReport;
            }
            pendingReport = lineData;
            pendingReportTicks = 0;
        }

        if (supersededFinalLine != null) send(supersededFinalLine);
    }

    /** Flushes the buffered report once it has been held for {@link #REPORT_DELAY_TICKS}. */
    public void onTick() {
        LineData toSend;
        synchronized (this) {
            if (pendingReport == null) return;
            if (++pendingReportTicks < REPORT_DELAY_TICKS) return;
            toSend = pendingReport;
            pendingReport = null;
            pendingReportTicks = 0;
        }
        send(toSend);
    }

    private static String stripWhitespace(String text) {
        return text == null ? "" : text.replaceAll("\\s+", "");
    }

    private void send(LineData lineData) {
        CompletableFuture.runAsync(() -> {
            synchronized (reportedLines) {
                if (reportedLines.contains(lineData.getRealLine())) {
                    return;
                }
                reportedLines.add(lineData.getRealLine());

                if (reportedLines.size() > 20) {
                    reportedLines.remove();
                }
            }
            try {
                reportUnvoicedLine(lineData);
                ModCore.LOGGER.info(
                        "Unvoiced line report has been sent to our servers. This contained: " + lineData.getRealLine());
            } catch (IOException e) {
                ModCore.LOGGER.error("A report of unvoiced line couldn't be sent.", e);
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

        URL urlObj = new URL("https://voicesofwynn.com/api/unvoiced-line-report/new");
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
        ModCore.LOGGER.debug("HTTP response Code : " + responseCode);
    }
}
