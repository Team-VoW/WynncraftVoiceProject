package com.wynnvp.wynncraftvp.sound;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class LineReporter {

    private final String apiKey = "testing"; //NOTE: this must be changed before compiling


    public void MissingLine(String line){
        try {
            reportUnvoicedLine(line);
            System.out.println("Anonymous unvoiced line report has been sent to our servers. Don't worry, this doesn't contain any sensitive information, only the line that didn't have any audio file associated and your coordinates in Wynncraft.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("An report of unvoiced line couldn't be sent. Error code should be right above this message.");
        }
    }


    private boolean reportUnvoicedLine(String chatMessage) throws IOException {
        //TODO Find a way to fill these variables in an elegant way.
        //I didn't dig through the code that much, but you could probably make a class that would take care of all the formating
        //Something like ChatMessageFormator, which would have a method into which you'd pass the whole chat message, that should trigger a dialogue
        //And after that, you could use methods like extractPlayerName(), extractNpcName(), extractDialogue() etc.

        String npcName = "";
        String dialogue = "";
        String fullLine = chatMessage;
        int CoordX = 0;
        int CoordY = 0;
        int CoordZ = 0;


        URL urlObj = new URL("https://voicesofwynn.com/api/unvoiced-line-report/new");
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "VoicesOfWynnModClient");
        connection.setDoOutput(true);
        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        String urlPostParameters = "npc=" + npcName + "&dialogue=" + dialogue + "&full=" + fullLine + "&x=" + CoordX + "&y=" + CoordY + "&z=" + CoordZ + "&apiKey=" + apiKey;
        outputStream.writeBytes(urlPostParameters);
        outputStream.flush();
        outputStream.close();
        Integer responseCode = connection.getResponseCode();
        if (!(responseCode >= 200 && responseCode < 300)) {
            System.out.println("HTTP response Code : " + responseCode);
            return false;
        }
        System.out.println("HTTP response Code : " + responseCode);
        return true;
    }

}
