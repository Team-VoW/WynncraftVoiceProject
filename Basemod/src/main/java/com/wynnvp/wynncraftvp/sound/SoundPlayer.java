package com.wynnvp.wynncraftvp.sound;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.coords.FullCoordinate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class SoundPlayer {

    //Code that is run to play all the sounds
    public static void playSound(String line) {
        ModCore modCore = ModCore.instance;
        SoundsHandler soundsHandler = modCore.soundsHandler;
        if (!soundsHandler.sounds.containsKey(line.hashCode())) {
            return;
        }
        SoundEvent soundEvent = soundsHandler.sounds.get(line.hashCode());

        FullCoordinate coords = getNPCCoords(line);
        //If the npc does not have any coords
        if (coords == null) {
            //Play the sound at the player
            Minecraft.getMinecraft().getSoundHandler().playSound(new SoundAtPlayer(soundEvent));
            return;
        }
        playSoundAtCoords(coords.getX(), coords.getZ(), coords.getY(), soundEvent);
    }


    private static FullCoordinate getNPCCoords(String line) {
        //Split the line ([x/b] <NPCName>: <text>) at :
        String[] splitLine = line.split(":");
        //Splits the [x/b] and <NPCName>
        String[] splitNumberAndName = splitLine[0].split("] ");

        return ModCore.instance.coordsHandler.getLocation(splitNumberAndName[1]);
    }

    private static void playSoundAtCoords(double x, double z, double y, SoundEvent soundEvent) {
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        player.getEntityWorld().playSound(x, y, z, soundEvent, SoundCategory.VOICE, 1, 1, false);
    }


}
