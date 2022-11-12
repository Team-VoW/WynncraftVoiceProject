package com.wynnvp.wynncraftvp.sound;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.npc.NPCHandler;
import com.wynnvp.wynncraftvp.sound.at.SoundAtArmorStand;
import com.wynnvp.wynncraftvp.sound.at.SoundAtCords;
import com.wynnvp.wynncraftvp.sound.at.SoundAtPlayer;
import com.wynnvp.wynncraftvp.sound.line.LineData;
import com.wynnvp.wynncraftvp.sound.line.LineReporter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.world.ClientWorld;
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

        if (!canPlaySound(soundsHandler, lineData, player, world))
            return;

        soundsHandler.get(line).ifPresent(this::PlaySoundObject);
    }

    private boolean canPlaySound(SoundsHandler soundsHandler, LineData lineData, ClientPlayerEntity player, ClientWorld world) {
        String line = lineData.getSoundLine();

        if (soundsHandler.get(line).isEmpty()) {
             System.out.println("Does not contain line: " + lineData.getRealLine());
             System.out.println("Checked line: " + line);
            lineReporter.MissingLine(lineData);
            return false;
        }

        if (player == null) {
            System.out.println("Player is null! Sound not played.");
            return false;
        }

        if (world == null) {
            System.out.println("World is null! Sound not played.");
            return false;
        }

        return true;
    }

    private void PlaySoundObject(SoundObject sound) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        assert player != null;
        SoundManager manager = MinecraftClient.getInstance().getSoundManager();

        //Stops all sounds so that not multiple voice lines are played over each other
        manager.stopAll();

        final CustomSoundClass customSoundClass = sound.getCustomSoundClass();
        final SoundEvent soundEvent = customSoundClass.soundEvent();

        //If this sound contains info about a location to play it at
        if (sound.getPosition() != null) {
            playSoundAtCords(sound.getPosition(), sound, manager);
            return;
        }

        //If this sound was set to play at the player pos or the setting to play all sounds on player is turned on
        if (customSoundClass.movingSound() || config.isPlayAllSoundsOnPlayer()) {
            //Play the sound at the player
            manager.play(new SoundAtPlayer(soundEvent));
            return;
        }

        String rawName = sound.getNpcName().toLowerCase().replace(" ", "");
        Vec3d npcPosition = NPCHandler.findPosition(rawName);

        if (npcPosition == null || isOutsideReach(sound, player, npcPosition)) {
            playSoundAtCords(player.getPos(), sound, manager);
            return;
        }

        manager.play(new SoundAtArmorStand(soundEvent, rawName, sound));

    }

    private void playSoundAtCords(Vec3d position, SoundObject soundObject, SoundManager manager) {

        SoundEvent soundEvent = soundObject.getCustomSoundClass().soundEvent();
        /*
        float volume = soundObject.getFallOff() == 0 ? config.getBlockCutOff() / 16f : soundObject.getFallOff() / 16f;
        pl.clientWorld.playSound(blockPos.x, blockPos.y, blockPos.z, soundEvent, SoundCategory.VOICE, volume, 1, false);*/
        manager.play(new SoundAtCords(soundEvent, soundObject, position));
    }

    private boolean isOutsideReach(SoundObject soundObject, ClientPlayerEntity player, Vec3d npcPosition) {
        return (player.getPos().squaredDistanceTo(npcPosition) >= config.getBlockCutOff() * config.getBlockCutOff()
                && player.getPos().squaredDistanceTo(npcPosition) >= soundObject.getFallOff() * soundObject.getFallOff());
    }


}
