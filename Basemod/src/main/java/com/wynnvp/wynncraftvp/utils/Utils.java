package com.wynnvp.wynncraftvp.utils;

import net.minecraft.client.Minecraft;

public class Utils {

    //Checks if the players is even on wynn
    public static boolean inWynn() {
        try {
            return Minecraft.getMinecraft().getCurrentServerData().serverIP.toLowerCase().contains("wynncraft");
        } catch (NullPointerException e) {
            return false;
        }
    }

    //Returns the path to the sound file in the format that is used in the JSON
    public static String pathToFile(String firstNumber, String secondNumber, String npcName, String line) {
        if (line.contains(Minecraft.getMinecraft().player.getName())) {
            line = line.replaceAll(Minecraft.getMinecraft().player.getName(), "player");
        }
        npcName = npcName.replaceAll(" ", "");

        line.replaceAll(" ", "");

        return npcName + "." + secondNumber + "." + firstNumber + line;
    }



}
