package com.wynnvp.wynncraftvp.sound;

import com.wynnvp.wynncraftvp.ModCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;

public class SoundPlayer {

    //Code that is run to play all the sounds
    public static void playSound(String line) {
        ModCore modCore = ModCore.instance;
        SoundsHandler soundsHandler = modCore.soundsHandler;
        line = SoundsHandler.formatToSound(line);
        if (!soundsHandler.sounds.containsKey(line.hashCode())) {
            System.out.println("Does not contain line: " + line);
            return;
        }
        System.out.println("Playing sound: " + line);
        CustomSoundClass customSoundClass = soundsHandler.sounds.get(line.hashCode());
        SoundEvent soundEvent = customSoundClass.getSoundEvent();

        //If this is a moving sound
        if (customSoundClass.isMovingSound()) {
            //Play the sound at the player
            Minecraft.getMinecraft().getSoundHandler().playSound(new SoundAtPlayer(soundEvent));
            return;
        }
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        playSoundAtCoords(player.getPosition(), soundEvent);
    }


    private static void playSoundAtCoords(BlockPos blockPos, SoundEvent soundEvent) {
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        player.getEntityWorld().playSound(blockPos.getX(), blockPos.getY(), blockPos.getZ(), soundEvent, SoundCategory.VOICE, 1, 1, false);
    }


}
