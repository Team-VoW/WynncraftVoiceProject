package com.wynnvp.wynncraftvp.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.config.ConfigHandler;
import com.wynnvp.wynncraftvp.events.ReceiveChatEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.ClickEvent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

public class VersionChecker {

    public static boolean isOnUpToDateVersion = false;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    public static void checkVersion() {

        JsonObject jsonObject = getVersionCheckFromWebsite();
        String directUpdateLink = jsonObject.get("directUpdateLink").getAsString();
        String fact = jsonObject.get("fact").getAsString();
        float killSwitchVersion = jsonObject.get("killSwitchVersion").getAsFloat();
        float newestVersion = jsonObject.get("newestVersion").getAsFloat();
        String updateInfoPageLink = jsonObject.get("updateInfopageLink").getAsString();
        float updateNotification = jsonObject.get("updateNotification").getAsFloat();
        //System.out.println("Direct update link: " + directUpdateLink + "\nfact: " + fact + "\nkillSwitchVersion: " + killSwitchVersion+ "\nnewestVersion: " + newestVersion + "\nupdateInfoPageLink: " + updateInfoPageLink + "\nupdateNotification: " + updateNotification);

        if (ConfigHandler.sendFunFact) {
            Minecraft.getMinecraft().player.sendMessage(new TextComponentString("§9Fun fact: " + fact));
        }

        String version = ModCore.instance.getClass().getPackage().getImplementationVersion();

        float versionInFloat = Float.parseFloat(version);

        //If it is on a killswitch version. This is to disable the mod in case some game breaking mod is found
        //such as people being able to trigger sound files through chat messages
        if (killSwitchVersion >= versionInFloat) {
            ReceiveChatEvent.stopMod = true;
            String message1 = "§8A game breaking mod was found on your version of §5Voices of Wynn§8 so the mod was disabled. Please update it";
            Minecraft.getMinecraft().player.sendMessage(new TextComponentString(message1));
        }
        //Is using newest version
        if (newestVersion == versionInFloat) {
            isOnUpToDateVersion = true;
            return;
        }

        if (updateNotification < versionInFloat) {
            isOnUpToDateVersion = true;
            return;
        }
        //The version is smaller or the same as the version to recommend update

        //Disable logging if on old version
        ConfigHandler.logMissingLines = false;

        //Send message to player about being on an old version
        String message = "§8A new version of §5Voices of Wynn§8 is available! You are using version: §4"
                + df.format(versionInFloat) + " §8and the newest version is: §2" + df.format(newestVersion);
        Minecraft.getMinecraft().player.sendMessage(new TextComponentString(message));
        message = "§8To immediately download the new version, click here: §5" + directUpdateLink
                + ". §8To read more, click here: §5" + updateInfoPageLink;
        Minecraft.getMinecraft().player.sendMessage(
                new TextComponentString(message).setStyle(new Style().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "url"))));

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



/*
    public static float[] getVersionCheck() {
        String file = "";
        try {
            URL url = new URL("https://raw.githubusercontent.com/Team-WoV/ModVersion/main/version");
            String username = "user";
            String password = "gitpwd";

            URLConnection uc = url.openConnection();
            uc.setRequestProperty("X-Requested-With", "Curl");
            ArrayList<String> list = new ArrayList<String>();
            String userpass = username + ":" + password;
            String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userpass.getBytes()));//needs Base64 encoder, apache.commons.codec
            uc.setRequestProperty("Authorization", basicAuth);

            BufferedReader reader = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null)
                file = file + line + "\n";


        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] split = file.split("\n");
        if (split.length < 2) return new float[0];

        split[0] = split[0].replace("newerstVersion: ", "");
        split[1] = split[1].replace("updateNotification: ", "");
        float[] ret = new float[2];
        ret[0] = Float.parseFloat(split[0]);
        ret[1] = Float.parseFloat(split[1]);

        return ret;
    }
    */

}
