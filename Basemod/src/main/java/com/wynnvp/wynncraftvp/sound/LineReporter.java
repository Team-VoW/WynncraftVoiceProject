package com.wynnvp.wynncraftvp.sound;

import com.wynnvp.wynncraftvp.config.ConfigHandler;
import com.wynnvp.wynncraftvp.utils.LineFormatter;
import net.minecraft.client.Minecraft;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class LineReporter {


    public void MissingLine(LineData lineData){
        if (!ConfigHandler.logMissingLines) return;

        if (!LineFormatter.isNPCSentLine( lineData.getRealLine())){
            return;
        }

        try {
            reportUnvoicedLine(lineData);
            System.out.println("Anonymous unvoiced line report has been sent to our servers. Don't worry, this doesn't contain any sensitive information, only the line that didn't have any audio file associated and your coordinates in Wynncraft.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("An report of unvoiced line couldn't be sent. Error code should be right above this message.");
        }

    }


    private void reportUnvoicedLine(LineData lineData) throws IOException {
        String npcName = lineData.getNPCName();
        String dialogue = "";
        String fullLine = lineData.getRealLine();
        int CoordX = (int) Minecraft.getMinecraft().player.posX;
        int CoordY = (int) Minecraft.getMinecraft().player.posY;
        int CoordZ = (int) Minecraft.getMinecraft().player.posZ;


        URL urlObj = new URL("https://voicesofwynn.com/api/unvoiced-line-report/new");
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "VoicesOfWynnModClient");
        connection.setDoOutput(true);
        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        String urlPostParameters = "npc=" + npcName + "&dialogue=" + dialogue + "&full=" + fullLine + "&x=" + CoordX + "&y=" + CoordY + "&z=" + CoordZ + "&apiKey=" + ConfigHandler.apiKey;
        outputStream.writeBytes(urlPostParameters);
        outputStream.flush();
        outputStream.close();
        Integer responseCode = connection.getResponseCode();
        //Failed
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
