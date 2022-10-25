package com.wynnvp.wynncraftvp.sound.line;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.config.ConfigHandler;
import com.wynnvp.wynncraftvp.utils.LineFormatter;
import com.wynnvp.wynncraftvp.utils.NeatLogger;
import com.wynnvp.wynncraftvp.utils.VersionChecker;
import net.minecraft.client.Minecraft;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;

public class LineReporter {

    private final Queue<String> reportedLines;

    private NeatLogger neatLogger;
    private boolean createLog = false;

    public LineReporter() {
        reportedLines = new LinkedList<>();
        if (ConfigHandler.createLog){
            neatLogger = new NeatLogger();
            createLog = true;
        }
    }

    public void MissingLine(LineData lineData) {
        if (!ConfigHandler.logMissingLines
                || !ModCore.inLiveWynnServer
                || !LineFormatter.isNPCSentLine(lineData.getRealLine())
                || !VersionChecker.isOnUpToDateVersion) return;

        if (createLog){
            neatLogger.ReceivedChat(lineData.getRealLine());
        }

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
                System.out.println("Unvoiced line report has been sent to our servers. This contained: " + lineData.getRealLine());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("A report of unvoiced line couldn't be sent. Error code should be right above this message.");
            }
        });
    }

    private void reportUnvoicedLine(LineData lineData) throws IOException {

        String npcName = lineData.getNPCName();
        String name = ConfigHandler.anonymous ? "anonymous" : Minecraft.getMinecraft().player.getName();
        String fullLine = lineData.getRealLine();
        int CoordX = (int) Minecraft.getMinecraft().player.posX;
        int CoordY = (int) Minecraft.getMinecraft().player.posY;
        int CoordZ = (int) Minecraft.getMinecraft().player.posZ;


        URL urlObj = new URL("http://voicesofwynn.com/api/unvoiced-line-report/new");
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("User-Agent", "VoicesOfWynnModClient");
        connection.setDoOutput(true);
        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        fullLine = HTTPEncode(fullLine);
        npcName = HTTPEncode(npcName);
        name = HTTPEncode(name);
        String urlPostParameters = "npc=" + npcName + "&player=" + name + "&full=" + fullLine + "&x=" + CoordX + "&y=" + CoordY + "&z=" + CoordZ + "&apiKey=" + ConfigHandler.word;
        outputStream.writeBytes(urlPostParameters);
        outputStream.flush();
        outputStream.close();
        Integer responseCode = connection.getResponseCode();
        System.out.println("HTTP response Code : " + responseCode);

    }

    private String HTTPEncode(String input) {
        try {
            return URLEncoder.encode(input, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

}
