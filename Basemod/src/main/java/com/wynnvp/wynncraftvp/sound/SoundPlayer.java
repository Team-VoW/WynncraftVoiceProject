package com.wynnvp.wynncraftvp.sound;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.config.ConfigHandler;
import com.wynnvp.wynncraftvp.npc.QuestMarkHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.util.SoundEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.wynnvp.wynncraftvp.sound.SoundsHandler.getNameForId;
import static com.wynnvp.wynncraftvp.sound.SoundsHandler.getNameForMessage;

public class SoundPlayer {
    private final List<String> latestSoundPlayed;
    private final LineReporter lineReporter;
    //public static boolean SPEAKING = false;

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
        soundsHandler.find(line).ifPresent(sound -> {
            final CustomSoundClass customSoundClass = sound.getCustomSoundClass();
            final SoundEvent soundEvent = customSoundClass.getSoundEvent();

            //Solves ArmorStand problem with ??? as name
            //WARNING: not yet tested
            QuestMarkHandler.put(getQuest(sound.getNpcName()));

            //If this is a moving sound or it is set to play all sounds on player
            if (customSoundClass.isMovingSound() || ConfigHandler.playAllSoundsOnPlayer) {
                //Play the sound at the player
                Minecraft.getMinecraft().getSoundHandler().playSound(new SoundAtPlayer(soundEvent));
                addSoundToCoolDown(line);
                return;
            }

            //The sound will come out of the armorstand and follow it
            String rawName = getRawName(line, sound.getNpcName());
            Minecraft.getMinecraft().getSoundHandler().playSound(new SoundAtArmorStand(soundEvent, rawName));
            addSoundToCoolDown(line);
        });
    }

    private String getQuest(String name) {
        String id = "none";
        if (name.contains("-")) {
            String[] args = name.split("-");
            id = args[0];
        }
        return id;
    }

    private String getRawName(String message, String name) {
        String npcName = getNameForMessage(message);
        if (npcName.isEmpty()) {
            npcName = getNameForId(name);
        }
        return npcName;
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
