package com.wynnvp.wynncraftvp;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class Utils {

    //Checks if the players is even on wynn
    public static boolean inWynn() {
        try {
            return Minecraft.getMinecraft().getCurrentServerData().serverIP.toLowerCase().contains("wynncraft");
        } catch (NullPointerException e) {
            return false;
        }
    }

    //Code that is run to play all the sounds
    public static void playSound(String line) {
        Minecraft.getMinecraft().getSoundHandler().playSound(new WynnVPMovingSound(getSoundEventFromLine(line)));
    }

    //Gets the sound from the resources
    public static SoundEvent getSoundEventFromLine(String line) {
        //Split the line ([x/b] <NPCName>: <text>) at :
        String[] splitLine = line.split(":");
        //Splits the [x/b] and <NPCName>
        String[] splitNumberAndName = splitLine[0].split("] ");
        //Splits the [x/b numbers so that they are not together
        String[] splitNumbers = splitNumberAndName[0].split("/");
        //Removes the [ so that the splitNumbers only contains x and b
        splitNumbers[0].replace("[", "");

        //Gets the sound file with the location
        SoundEvent soundEvent = new SoundEvent(new ResourceLocation("wynnvp",
                splitNumbers[0] + "." +
                        splitNumbers[1] + "." +
                        splitNumberAndName[1]
                        + line));
        return soundEvent;
    }


}
