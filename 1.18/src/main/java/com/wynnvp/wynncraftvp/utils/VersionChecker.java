package com.wynnvp.wynncraftvp.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.events.ReceiveChatEvent;
import net.minecraft.client.Minecraft;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.wynnvp.wynncraftvp.ModCore.config;

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
        try {
            fact = jsonObject.get("fact").getAsString();
            killSwitchVersion = jsonObject.get("fabric_killSwitchVersion").getAsString();
            newestVersion = jsonObject.get("fabric_newestVersion").getAsString();
            updateVersion = jsonObject.get("fabric_updateNotification").getAsString();
            directUpdateLink = jsonObject.get("fabric_directUpdateLink").getAsString();
            updateInfoPageLink = jsonObject.get("fabric_updateInfopageLink").getAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (newestVersion == null) {
            return;
        }

        String version = ModCore.VERSION;

        float versionInFloat = GetVersionNumberInFloat(version);
        float killSwitchVersionInFloat = GetVersionNumberInFloat(killSwitchVersion);
        assert updateVersion != null;
        float updateVersionInFloat = GetVersionNumberInFloat(updateVersion);

        //If it is on a kill switch version. This is to disable the mod in case some game breaking bug is found
        //such as people being able to trigger sound files through chat messages
        if (killSwitchVersionInFloat >= versionInFloat) {
            ReceiveChatEvent.stopMod = true;
            String message1 = "§8A game breaking bug was found on your version of §5Voices of Wynn§8 so the mod was disabled. Please update it";
            Utils.sendMessage(message1);
        }
        isOnUpToDateVersion = version.equals(newestVersion);

        //Is using the newest version
        if (versionInFloat > updateVersionInFloat) {

            if (fact != null && config.isSendFunFact()) {
                Utils.sendMessage("§9Fun fact: " + fact);
            }
            return;
        }

        Utils.sendMessage("§9A new version of §5Voices of Wynn§9 is available! You are using version: §4" + version + " §9and the newest version is: §2" + newestVersion + ".");
        Utils.sendMessageWithLink("§9To download our updater, click §b§nhere", directUpdateLink);
        Utils.sendMessageWithLink("§9To see the changelog and display other download options, click §b§nhere", updateInfoPageLink);


    }

    private static float GetVersionNumberInFloat(String version){
        float output = 0;
        float multiplier = 1;
        for (String str : version.split("\\.")) {
            output += Float.parseFloat(str) / multiplier;
            multiplier /= 100;
        }
        return output;
    }



    private static JsonObject getVersionCheckFromWebsite() {

        //Hashes the UUID for it to be anonymous
        String hashedUUID = Utils.sha256(Minecraft.getInstance().player.getUUID().toString());
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
