package com.wynnvp.wynncraftvp.sound;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.config.ConfigHandler;
import com.wynnvp.wynncraftvp.npc.NPCHandler;
import com.wynnvp.wynncraftvp.npc.NPCNames;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;

import java.util.ArrayList;
import java.util.Optional;

public class SoundPlayer {
    private final ArrayList<String> latestSoundPlayed;
    private final LineReporter lineReporter;
    public static boolean SPEAKING = false;

    public SoundPlayer() {
        latestSoundPlayed = new ArrayList<>();
        lineReporter = new LineReporter();
    }

    //Code that is run to play all the sounds
    public void playSound(LineData lineData) {
        String line = lineData.getSoundLine();
        SoundsHandler soundsHandler = ModCore.instance.soundsHandler;
        if (!soundsHandler.find(line).isPresent()) {
         //   System.out.println("Does not contain line: " + lineData.getRealLine());
            lineReporter.MissingLine(lineData);
            return;
        }
        if (isOnCoolDown(line)) {
            //System.out.println("Sound: " + line + " is on cooldown.");
            return;
        }

        //System.out.println("Playing sound: " + line);
        Minecraft.getMinecraft().getSoundHandler().stopSounds();
        SPEAKING = false;
        soundsHandler.find(line).ifPresent(sound -> {
            final CustomSoundClass customSoundClass = sound.getCustomSoundClass();
            final SoundEvent soundEvent = customSoundClass.getSoundEvent();
            SPEAKING = true;

            //If this is a moving sound or it is set to play all sounds on player
            if (customSoundClass.isMovingSound() || ConfigHandler.playAllSoundsOnPlayer) {
                //Play the sound at the player
                Minecraft.getMinecraft().getSoundHandler().playSound(new SoundAtPlayer(soundEvent));
                addSoundToCoolDown(line);
                return;
            }

            NPCHandler.find(NPCNames.get(getId(sound.getNpcName()))).ifPresent(npc -> playSoundAtCoords(npc.getVector(), soundEvent));
            addSoundToCoolDown(line);
        });

    }

    private String getId(String name) {
        String id = "???";
        if (name.contains("-")) {
            String[] args = name.split("-");
            id = args[1];
        }
        return id;
    }

    private void playSoundAtCoords(Vec3d blockPos, SoundEvent soundEvent) {
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        player.getEntityWorld().playSound(blockPos.x, blockPos.y, blockPos.z, soundEvent, SoundCategory.VOICE, 1, 1, false);
    }

    private void addSoundToCoolDown(String soundName) {
        if (latestSoundPlayed.size() > 5) {
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
