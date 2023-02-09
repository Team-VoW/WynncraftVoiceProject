package com.wynnvp.wynncraftvp.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.config.ConfigHandler;
import com.wynnvp.wynncraftvp.events.ReceiveChatEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class VersionChecker {

    public static boolean isOnUpToDateVersion = false;

    public static void checkVersion() {

        JsonObject jsonObject = getVersionCheckFromWebsite();
        String directUpdateLink = jsonObject.get("directUpdateLink").getAsString();
        String fact = jsonObject.get("fact").getAsString();
        String killSwitchVersion = jsonObject.get("killSwitchVersion").getAsString();
        String newestVersion = jsonObject.get("newestVersion").getAsString();
        String updateInfoPageLink = jsonObject.get("updateInfopageLink").getAsString();
        String updateNotification = jsonObject.get("updateNotification").getAsString();


        String version = ModCore.instance.getClass().getPackage().getImplementationVersion();

        float versionInFloat = GetVersionNumberInFloat(version);
        float killSwitchVersionInFloat = GetVersionNumberInFloat(killSwitchVersion);
        float updateVersionInFloat = GetVersionNumberInFloat(updateNotification);
        float newestVersionInFloat = GetVersionNumberInFloat(newestVersion);

        //If it is on a kill switch version. This is to disable the mod in case some game breaking mod is found
        //such as people being able to trigger sound files through chat messages
        if (killSwitchVersionInFloat >= versionInFloat) {
            ReceiveChatEvent.stopMod = true;
            String message1 = "§8A game breaking mod was found on your version of §5Voices of Wynn§8 so the mod was disabled. Please update it";
            Minecraft.getMinecraft().player.sendMessage(new TextComponentString(message1));
        }
        //Is using the newest version
        if (newestVersionInFloat == versionInFloat || updateVersionInFloat < versionInFloat) {
            if (ConfigHandler.sendFunFact) {
                Utils.sendMessage("§9Fun fact: " + fact);
            }
            isOnUpToDateVersion = true;
            return;
        }


        //The version is smaller or the same as the version to recommend update
        //Disable logging if on old version
        ConfigHandler.logMissingLines = false;

        //Send message to player about being on an old version
        Utils.sendMessage("§9A new version of §5Voices of Wynn§9 is available! You are using version: §4" + version + " §9and the newest version is: §2" + newestVersion + ".");
        Utils.sendMessageWithLink("§9To download our updater, click §b§nhere", directUpdateLink);
        Utils.sendMessageWithLink("§9To see the changelog and display other download options, click §b§nhere", updateInfoPageLink);


    }


    private static float GetVersionNumberInFloat(String version) {
        float output = 0;
        float multiplier = 1;
        for (String str : version.split("\\.")) {
            output += Float.parseFloat(str) / multiplier;
            multiplier *= 10;
        }
        return output;
    }

    private static JsonObject getVersionCheckFromWebsite() {

        //Hashes the UUID for it to be anonymous
        String hashedUUID = Utils.sha256(Minecraft.getMinecraft().player.getUniqueID().toString());
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
