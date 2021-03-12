package com.wynnvp.wynncraftvp.sound;

import com.wynnvp.wynncraftvp.events.RegistryHandler;
import com.wynnvp.wynncraftvp.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class SoundPlayer {

    //Code that is run to play all the sounds
    public static void playSound(String line) {
        //IF chat message is test to test playing a sound.
        // Delete this once it is tested.
        if (line.equals("test")) {
            SoundEvent soundEvent = SoundsHandler.TALKING_MUSHROOM_RETURNINGARDFD;
            //Minecraft.getMinecraft().player.playSound(soundEvent, 100, 1);
            //playSoundAtCoords(0, 100, 0, soundEvent);
            Minecraft.getMinecraft().player.sendChatMessage("Playing sound");
            Minecraft.getMinecraft().getSoundHandler().playSound(new SoundAtPlayer(soundEvent));
            return;
        }

        SoundEvent soundEvent = getSoundEventFromLine(line);
        //If the line the npc said is not included
        if (soundEvent == null) {
            return;
        }

        String coords = getNPCCoords(line);
        //If the npc does not have any coords
        if (coords == null) {
            //Play the sound at the player
            Minecraft.getMinecraft().getSoundHandler().playSound(new SoundAtPlayer(soundEvent));
            return;
        }
        String[] splitCoords = coords.split(",");
        double x = Double.parseDouble(splitCoords[0]);
        double z = Double.parseDouble(splitCoords[1]);
        double y = Double.parseDouble(splitCoords[2]);
        playSoundAtCoords(x, z, y, soundEvent);
    }


    //Gets the sound from the resources
    private static SoundEvent getSoundEventFromLine(String line) {
        //Split the line ([x/b] <NPCName>: <text>) at :
        String[] splitLine = line.split(":");
        //Splits the [x/b] and <NPCName>
        String[] splitNumberAndName = splitLine[0].split("] ");
        //Splits the [x/b numbers so that they are not together
        String[] splitNumbers = splitNumberAndName[0].split("/");
        //Removes the [ so that the splitNumbers only contains x and b
        splitNumbers[0] = splitNumbers[0].replace("[", "");

        //Gets the sound file
        try {
            ResourceLocation sound = new ResourceLocation("sounds", Utils.pathToFile(splitNumbers[0], splitNumbers[1], splitNumberAndName[1], splitLine[1]));
            return new SoundEvent(sound);
        } catch (Exception e) {
            return null;
        }

    }

    private static String getNPCCoords(String line) {
        //Split the line ([x/b] <NPCName>: <text>) at :
        String[] splitLine = line.split(":");
        //Splits the [x/b] and <NPCName>
        String[] splitNumberAndName = splitLine[0].split("] ");
        //Splits the [x/b numbers so that they are not together
        String[] splitNumbers = splitNumberAndName[0].split("/");
        //Removes the [ so that the splitNumbers only contains x and b
        splitNumbers[0] = splitNumbers[0].replace("[", "");

        //Gets the sound file
        String location = Utils.pathToFile(splitNumbers[0], splitNumbers[1], splitNumberAndName[1], splitLine[1] + "coords");
        try {
            ResourceLocation x = new ResourceLocation("coords", location + "x");
            ResourceLocation z = new ResourceLocation("coords", location + "z");
            ResourceLocation y = new ResourceLocation("coords", location + "y");
            return x + "," + z + "," + y;

            //If npc has no coords
        } catch (Exception e) {
            return null;
        }
    }

    private static void playSoundAtCoords(double x, double z, double y, SoundEvent soundEvent) {
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        player.getEntityWorld().playSound(x, y, z, soundEvent, SoundCategory.VOICE, 2, 1, false);
    }


}
