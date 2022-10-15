package com.wynnvp.wynncraftvp.sound.line;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.utils.LineFormatter;
import com.wynnvp.wynncraftvp.utils.VersionChecker;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;

import static com.wynnvp.wynncraftvp.ModCore.config;

public class LineReporter {

    private final Queue<String> reportedLines;

    public LineReporter() {
        reportedLines = new LinkedList<>();
    }

    public void MissingLine(LineData lineData) {
        if (!config.isLogMissingLines()
                || !ModCore.inLiveWynnServer
                || !LineFormatter.isNPCSentLine(lineData.getRealLine())
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
                System.out.println("Unvoiced line report has been sent to our servers. This contained: " + lineData.getRealLine());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("A report of unvoiced line couldn't be sent. Error code should be right above this message.");
            }
        });
    }

    private void reportUnvoicedLine(LineData lineData) throws IOException {

        String npcName = lineData.getNPCName();
        String name = config.isAnonymous() ? "anonymous" : MinecraftClient.getInstance().player.getName().toString();
        String fullLine = lineData.getRealLine();
        PlayerEntity p = MinecraftClient.getInstance().player;
        int CoordX = (int) p.getPos().x;
        int CoordY = (int) p.getPos().y;
        int CoordZ = (int) p.getPos().z;

        URL urlObj = new URL("http://voicesofwynn.com/api/unvoiced-line-report/new");
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("User-Agent", "VoicesOfWynnModClient");
        connection.setDoOutput(true);
        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        String urlPostParameters = "npc=" + npcName + "&player=" + name + "&full=" + fullLine + "&x=" + CoordX + "&y=" + CoordY + "&z=" + CoordZ + "&apiKey=" + config.getWord();
        outputStream.writeBytes(urlPostParameters);
        outputStream.flush();
        outputStream.close();
        Integer responseCode = connection.getResponseCode();
        System.out.println("HTTP response Code : " + responseCode);



        /*
         if (!(responseCode >= 200 && responseCode < 300)) {
            //Failed
            System.out.println("HTTP response Code : " + responseCode);
            return false;
        }
        System.out.println("HTTP response Code : " + responseCode);
        return true;
         */
    }

}
