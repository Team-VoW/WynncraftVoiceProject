package com.wynnvp.wynncraftvp.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.config.VOWConfig;
import com.wynnvp.wynncraftvp.events.ReceiveChatEvent;
import net.minecraft.client.MinecraftClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class VersionChecker {

    public static boolean isOnUpToDateVersion = false;

    public static void checkVersion() {

        JsonObject jsonObject = getVersionCheckFromWebsite();
        String fact = null;
        String killSwitchVersion = null;
        String newestVersion = null;
        String updateVersion = null;
        try {
            fact = jsonObject.get("fact").getAsString();
            killSwitchVersion = jsonObject.get("fabric_killSwitchVersion").getAsString();
            newestVersion = jsonObject.get("fabric_newestVersion").getAsString();
            updateVersion = jsonObject.get("fabric_updateNotification").getAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (fact != null && VOWConfig.sendFunFact) {
            Utils.sendMessage("§9Fun fact: " + fact);
        }

        if (newestVersion == null) {
            return;
        }

        String version = ModCore.VERSION;

        float versionInFloat = 0;
        float mult = 1;
        for (String str : version.split("\\.")) {
            versionInFloat += Float.parseFloat(str) / mult;
            mult /= 100;
        }

        float killSwitchVersionInFloat = 0;
        mult = 1;
        for (String str : killSwitchVersion.split("\\.")) {
            killSwitchVersionInFloat += Float.parseFloat(str) / mult;
            mult /= 100;
        }

        float updateVersionInFloat = 0;
        mult = 1;
        for (String str : killSwitchVersion.split("\\.")) {
            updateVersionInFloat += Float.parseFloat(str) / mult;
            mult /= 100;
        }

        //If it is on a killswitch version. This is to disable the mod in case some game breaking bug is found
        //such as people being able to trigger sound files through chat messages
        if (killSwitchVersionInFloat >= versionInFloat) {
            ReceiveChatEvent.stopMod = true;
            String message1 = "§8A game breaking bug was found on your version of §5Voices of Wynn§8 so the mod was disabled. Please update it";
            Utils.sendMessage(message1);
        }
        isOnUpToDateVersion = version.equals(newestVersion);

        //Is using newest version
        if (versionInFloat > updateVersionInFloat) {
            return;
        }

        //Send message to player about being on an old version
        String message = "§8A new version of §5Voices of Wynn§8 is available! You are using version: §4"
                + version + " §8and the newest version is: §2" + newestVersion + ".\n Please update using the installer.";

        Utils.sendMessage(message);

    }


    private static JsonObject getVersionCheckFromWebsite() {

        //Hashes the UUID for it to be anonymous
        String hashedUUID = Utils.sha256(MinecraftClient.getInstance().player.getUuid().toString());
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

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line);
            }
        }
        JsonParser jsonParser = new JsonParser();
        return jsonParser.parse(result.toString()).getAsJsonObject();
    }

}
