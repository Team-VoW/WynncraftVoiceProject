package com.wynnvp.wynncraftvp.sound;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.config.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class SoundPlayer {
    private ArrayList<String> latestSoundPlayed;
    private final LineReporter lineReporter;

    public SoundPlayer() {
        latestSoundPlayed = new ArrayList<>();
        lineReporter = new LineReporter();
    }

    //Code that is run to play all the sounds
    public void playSound(String line) {
        ModCore modCore = ModCore.instance;
        SoundsHandler soundsHandler = modCore.soundsHandler;
        if (!soundsHandler.sounds.containsKey(line)) {
            System.out.println("Does not contain line: " + line);
            lineReporter.MissingLine(line);
            return;
        }
        if (isOnCoolDown(line)) {
            System.out.println("Sound: " + line + " is on cooldown.");
            return;
        }

        System.out.println("Playing sound: " + line);
        Minecraft.getMinecraft().getSoundHandler().stopSounds();
        CustomSoundClass customSoundClass = soundsHandler.sounds.get(line);
        SoundEvent soundEvent = customSoundClass.getSoundEvent();

        //If this is a moving sound or it is set to play all sounds on player
        if (customSoundClass.isMovingSound() || ConfigHandler.playAllSoundsOnPlayer) {
            //Play the sound at the player
            Minecraft.getMinecraft().getSoundHandler().playSound(new SoundAtPlayer(soundEvent));
            addSoundToCoolDown(line);
            return;
        }
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        playSoundAtCoords(player.getPosition(), soundEvent);
        addSoundToCoolDown(line);
    }


    private void playSoundAtCoords(BlockPos blockPos, SoundEvent soundEvent) {
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        player.getEntityWorld().playSound(blockPos.getX(), blockPos.getY(), blockPos.getZ(), soundEvent, SoundCategory.VOICE, 1, 1, false);
    }

    private void addSoundToCoolDown(String soundName) {
        if (latestSoundPlayed.size() > 100) {
            latestSoundPlayed.remove(0);
        }
        latestSoundPlayed.add(soundName);
    }

    private boolean isOnCoolDown(String soundName) {
        return latestSoundPlayed.contains(soundName);
    }

    public void clearCoolDown() {
        latestSoundPlayed.clear();
    }

}
