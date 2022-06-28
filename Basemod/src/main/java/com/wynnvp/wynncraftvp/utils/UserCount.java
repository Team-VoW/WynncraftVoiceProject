package com.wynnvp.wynncraftvp.utils;

import net.minecraft.client.Minecraft;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class UserCount {

    public static void addPlayer() {
        try {
            String letters = "";
            String uuid = Minecraft.getMinecraft().player.getUniqueID().toString();
            URL url = new URL("http://voicesofwynn.com/api/usage-analysis/ping?apiKey=" + letters + "&uuid=" + uuid);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.setRequestProperty("Content-Type", "application/json");
            http.setRequestProperty("Content-Length", "0");

            System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
            http.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
