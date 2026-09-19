/*
 * Copyright © Team-VoW 2024-2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.line;

import static com.wynnvp.wynncraftvp.ModCore.config;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.api.ApiHttpException;
import com.wynnvp.wynncraftvp.api.VoWApiClient;
import java.io.IOException;
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
    private final VoWApiClient api;

    private LineData pendingReport;
    private int pendingReportTicks;

    public LineReporter() {
        this(new VoWApiClient());
    }

    LineReporter(VoWApiClient api) {
        this.api = api;
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
        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        String playerName =
                config.isAnonymous() ? null : player.getGameProfile().name();
        VoWApiClient.LineReportRequest report = new VoWApiClient.LineReportRequest(
                lineData.getRealLine(),
                lineData.getNPCName(),
                playerName,
                new VoWApiClient.Position(
                        (int) player.position().x, (int) player.position().y, (int) player.position().z));

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
                api.submitReport(report);
                ModCore.LOGGER.info(
                        "Unvoiced line report has been sent to our servers. This contained: " + lineData.getRealLine());
            } catch (ApiHttpException e) {
                ModCore.LOGGER.error(
                        "A report of unvoiced line couldn't be sent: server returned HTTP {}.", e.statusCode());
            } catch (IOException e) {
                ModCore.LOGGER.error("A report of unvoiced line couldn't be sent.", e);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                ModCore.LOGGER.warn("A report of unvoiced line was interrupted.", e);
            }
        });
    }
}
