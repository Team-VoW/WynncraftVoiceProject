/*
 * Copyright © Team-VoW 2024.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.utils;

import static com.wynnvp.wynncraftvp.ModCore.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.events.ReceiveChatEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;

public class VersionChecker {
    public static boolean isOnUpToDateVersion = false;

    public static void checkVersion() {
        JsonObject jsonObject = getVersionCheckFromWebsite();
        String fact = null;
        String killSwitchVersion = null;
        String newestVersion = null;
        String updateVersion = null;
        String directUpdateLink = null;
        String updateInfoPageLink = null;
        String azureBlobLink = null;
        //In the jsonObject there is an array called "audio_urls" that contains these audio urls.
        List<String> audioUrls = new ArrayList<>();
        try {
            fact = jsonObject.get("fact").getAsString();
            killSwitchVersion = jsonObject.get("fabric_killSwitchVersion").getAsString();
            newestVersion = jsonObject.get("fabric_newestVersion").getAsString();
            updateVersion = jsonObject.get("fabric_updateNotification").getAsString();
            directUpdateLink = jsonObject.get("fabric_directUpdateLink").getAsString();
            updateInfoPageLink = jsonObject.get("fabric_updateInfopageLink").getAsString();
            azureBlobLink = jsonObject.get("azure_blob_link").getAsString();
            jsonObject.getAsJsonArray("audio_urls").forEach(jsonElement -> audioUrls.add(jsonElement.getAsString()));
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (newestVersion == null) {
            return;
        }

        if (azureBlobLink != null){
            config.azureBlobLink = azureBlobLink;
            config.save();
        }

        if (!audioUrls.isEmpty()){
            config.urls = audioUrls;
            config.save();
        }

        // Strip the "v" from the version
        String version = ModCore.instance.getVersion().substring(1);

        float versionInFloat = GetVersionNumberInFloat(version);
        float killSwitchVersionInFloat = GetVersionNumberInFloat(killSwitchVersion);
        assert updateVersion != null;
        float updateVersionInFloat = GetVersionNumberInFloat(updateVersion);

        // If it is on a kill switch version. This is to disable the mod in case some game breaking bug is found
        // such as people being able to trigger sound files through chat messages
        if (killSwitchVersionInFloat >= versionInFloat) {
            ReceiveChatEvent.stopMod = true;
            String message1 =
                    "§8A game breaking bug was found on your version of §5Voices of Wynn§8 so the mod was disabled. Please update it";
            Utils.sendMessage(message1);
        }
        isOnUpToDateVersion = version.equals(newestVersion);

        // Is using the newest version
        if (versionInFloat > updateVersionInFloat) {
            if (fact != null && config.isSendFunFact()) {
                Utils.sendMessage("§9Fun fact: " + fact);
            }
            return;
        }

        Utils.sendMessage("§9A new version of §5Voices of Wynn§9 is available! You are using version: §4" + version
                + " §9and the newest version is: §2" + newestVersion + ".");
        Utils.appendMessageWithLinkAndSend("§9To download our updater, click ", directUpdateLink, "§b§nhere");
        Utils.appendMessageWithLinkAndSend(
                "§9To see the changelog and display other download options, click ", updateInfoPageLink, "§b§nhere");
    }

    private static float GetVersionNumberInFloat(String version) {

        version = version.replaceAll("[^\\d.]", "");
        float output = 0;
        float multiplier = 1;
        for (String str : version.split("\\.")) {
            output += Float.parseFloat(str) / multiplier;
            multiplier *= 10;
        }
        return output;
    }

    private static JsonObject getVersionCheckFromWebsite() {
        // Hashes the UUID for it to be anonymous
        String hashedUUID =
                Utils.sha256(Minecraft.getInstance().player.getUUID().toString());
        String url = "http://voicesofwynn.com/api/version/check?id=" + hashedUUID;

        JsonObject jsonObject = null;
        try {
            jsonObject = getJsonData(url);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    private static JsonObject getJsonData(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line);
            }
        }
        JsonParser jsonParser = new JsonParser();
        return jsonParser.parse(result.toString()).getAsJsonObject();
    }
}
