package com.wynnvp.wynncraftvp.sound;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.config.ConfigHandler;
import com.wynnvp.wynncraftvp.npc.NPCHandler;
import com.wynnvp.wynncraftvp.npc.QuestMarkHandler;
import com.wynnvp.wynncraftvp.sound.at.SoundAtArmorStand;
import com.wynnvp.wynncraftvp.sound.at.SoundAtPlayer;
import com.wynnvp.wynncraftvp.sound.line.LineData;
import com.wynnvp.wynncraftvp.sound.line.LineReporter;
import com.wynnvp.wynncraftvp.utils.Utils;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import paulscode.sound.FilenameURL;
import paulscode.sound.SoundSystemException;
import paulscode.sound.libraries.LibraryLWJGLOpenAL;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class SoundPlayer {

    private Thread musicThread = null;
    private AdvancedPlayer player = null;

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
        if (!soundsHandler.get(line).isPresent()) {
         //   System.out.println("Does not contain line: " + lineData.getRealLine());
            lineReporter.MissingLine(lineData);
            return;
        }
        if (isOnCoolDown(line)) {
            System.out.println("Sound: " + line + " is on cooldown.");
            return;
        }

        if (Minecraft.getMinecraft().player == null) {
            System.out.println("Player is null! Sound not played.");
            return;
        }

        if (Minecraft.getMinecraft().world == null) {
            System.out.println("World is null! Sound not played.");
            return;
        }

        //System.out.println("Playing sound: " + line);
        Minecraft.getMinecraft().getSoundHandler().stopSounds();
        soundsHandler.get(line).ifPresent(sound -> {
            final CustomSoundClass customSoundClass = sound.getCustomSoundClass();
            final SoundEvent soundEvent = customSoundClass.getSoundEvent();

            //Solves ArmorStand problem with ??? as name
            //WARNING: not yet tested
            QuestMarkHandler.put(getQuest(sound.getId()));

            //If this is a moving sound or it is set to play all sounds on player
            //ModCore.instance.controller.playAtPlayer(new File("C:/Users/ender/AppData/Roaming/.minecraft/wynnvp/kingsrecruit/kingsrecruit-caravandriver-2.ogg"));
            //ModCore.instance.controller.playAtPlayer(new File(Utils.FILE_ROOT, getQuest(sound.getId())+"/"+sound.getId()+".ogg"));
            if (customSoundClass.isMovingSound() || ConfigHandler.playAllSoundsOnPlayer) {
                //Play the sound at the player
                Minecraft.getMinecraft().getSoundHandler().playSound(new SoundAtPlayer(soundEvent));
                addSoundToCoolDown(line);
                return;
            }

            String rawName = getRawName(sound.getId());
            if (NPCHandler.getNamesHandlers().containsKey(rawName)) {
               NPCHandler.find(rawName).ifPresent(vector -> {
                   if (Minecraft.getMinecraft().player.getDistance(vector.x, vector.y, vector.z) >= 20) {
                       playSoundAtCoords(Minecraft.getMinecraft().player.getPositionVector(), soundEvent);
                   } else {
                       Minecraft.getMinecraft().getSoundHandler().playSound(new SoundAtArmorStand(soundEvent, rawName));
                   }
               });
            } else {
                playSoundAtCoords(Minecraft.getMinecraft().player.getPositionVector(), soundEvent);
            }
            addSoundToCoolDown(line);
        });
    }

    private void playSoundAtCoords(Vec3d blockPos, SoundEvent soundEvent) {
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        player.getEntityWorld().playSound(blockPos.x, blockPos.y, blockPos.z, soundEvent, SoundCategory.VOICE, 1, 1, false);
    }

    private String getQuest(String id) {
        String result = "none";
        if (id.contains("-")) {
            String[] args = id.split("-");
            result = args[0];
        }
        return result;
    }

    private String getRawName(String name) {
        return ModCore.instance.soundsHandler.findNPCName(name);
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

    public void play(File file) throws SoundSystemException {
        musicThread = new Thread(() -> {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                float baseVolume = -32 + (32 * Minecraft.getMinecraft().gameSettings.getSoundLevel(SoundCategory.RECORDS));
                //player = new AdvancedPlayer(bufferedInputStream, baseVolume);
                fileInputStream.close();
                bufferedInputStream.close();



            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        musicThread.setName("VoW - Sound Player");
        musicThread.start();
    }

}
