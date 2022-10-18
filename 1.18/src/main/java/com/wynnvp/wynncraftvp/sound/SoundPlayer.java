package com.wynnvp.wynncraftvp.sound;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.npc.NPCHandler;
import com.wynnvp.wynncraftvp.sound.at.SoundAtArmorStand;
import com.wynnvp.wynncraftvp.sound.at.SoundAtPlayer;
import com.wynnvp.wynncraftvp.sound.line.LineData;
import com.wynnvp.wynncraftvp.sound.line.LineReporter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Vec3d;

import static com.wynnvp.wynncraftvp.ModCore.config;

public class SoundPlayer {

    private final Thread musicThread = null;
    private final LineReporter lineReporter;
    //public static boolean SPEAKING = false;

    public SoundPlayer() {
        lineReporter = new LineReporter();
    }

    //Code that is run to play all the sounds
    public void playSound(LineData lineData) {
        String line = lineData.getSoundLine();

        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        ClientWorld world = MinecraftClient.getInstance().world;

        SoundsHandler soundsHandler = ModCore.instance.soundsHandler;
        if (soundsHandler.get(line).isEmpty()) {
            // System.out.println("Does not contain line: " + lineData.getRealLine());
            lineReporter.MissingLine(lineData);
            return;
        }

        if (player == null) {
            System.out.println("Player is null! Sound not played.");
            return;
        }

        if (world == null) {
            System.out.println("World is null! Sound not played.");
            return;
        }


        SoundManager manager = MinecraftClient.getInstance().getSoundManager();

        manager.stopAll();
        soundsHandler.get(line).ifPresent(sound -> {

            final CustomSoundClass customSoundClass = sound.getCustomSoundClass();
            final SoundEvent soundEvent = customSoundClass.soundEvent();

            //If this sound contains info about a location to play it at
            if (sound.getPosition() != null){
                playSoundAtCoords(sound.getPosition(), sound, player);
                return;
            }

            NPCHandler.yeetTheCache();

            if (customSoundClass.movingSound() || config.isPlayAllSoundsOnPlayer()) {
                //Play the sound at the player
                manager.play(new SoundAtPlayer(soundEvent));
                return;
            }

            String rawName = sound.getNpcName().toLowerCase().replace(" ", "");
            Vec3d vector = NPCHandler.find(rawName);
            if (vector == null
                    || (player.getPos().squaredDistanceTo(vector) >= config.getBlockCutOff() * config.getBlockCutOff()
                        && player.getPos().squaredDistanceTo(vector) >= sound.getFallOff() * sound.getFallOff())) {
                playSoundAtCoords(player.getPos(), sound, player);
            } else {
                manager.play(new SoundAtArmorStand(soundEvent, rawName, sound));
            }
        });
    }

    private void playSoundAtCoords(Vec3d blockPos, SoundObject soundObject, ClientPlayerEntity pl) {

        SoundEvent soundEvent = soundObject.getCustomSoundClass().soundEvent();
        float volume = soundObject.getFallOff() == 0 ? config.getBlockCutOff() / 16f : soundObject.getFallOff() / 16f;
        pl.clientWorld.playSound(blockPos.x, blockPos.y, blockPos.z, soundEvent, SoundCategory.VOICE, volume, 1, false);
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

}
