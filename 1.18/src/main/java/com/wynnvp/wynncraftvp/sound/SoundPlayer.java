package com.wynnvp.wynncraftvp.sound;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.npc.NPCHandler;
import com.wynnvp.wynncraftvp.sound.at.SoundAtArmorStand;
import com.wynnvp.wynncraftvp.sound.at.SoundAtCords;
import com.wynnvp.wynncraftvp.sound.at.SoundAtPlayer;
import com.wynnvp.wynncraftvp.sound.line.LineData;
import com.wynnvp.wynncraftvp.sound.line.LineReporter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

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

        Player player = Minecraft.getInstance().player;
        ClientLevel world = Minecraft.getInstance().level;

        SoundsHandler soundsHandler = ModCore.instance.soundsHandler;

        if (!canPlaySound(soundsHandler, lineData, player, world))
            return;

        soundsHandler.get(line).ifPresent(this::PlaySoundObject);
    }

    private boolean canPlaySound(SoundsHandler soundsHandler, LineData lineData, Player player, ClientLevel world) {
        String line = lineData.getSoundLine();

        //System.out.println("Trying to play " + lineData.getRealLine());
        //System.out.println("Checked line: " + line);
        if (soundsHandler.get(line).isEmpty()) {

        //System.out.println("DID NOT CONTAIN LINE");
            lineReporter.MissingLine(lineData);
            return false;
        }

        if (player == null) {
       //System.out.println("Player is null! Sound not played.");
            return false;
        }

        if (world == null) {
            //System.out.println("World is null! Sound not played.");
            return false;
        }

        return true;
    }

    private void PlaySoundObject(SoundObject sound) {
        Player player = Minecraft.getInstance().player;
        assert player != null;
        SoundManager manager = Minecraft.getInstance().getSoundManager();

        //Stops all sounds so that not multiple voice lines are played over each other
        manager.stop();

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
        Vec3 npcPosition = NPCHandler.findPosition(rawName);

        if (npcPosition == null || isOutsideReach(sound, player, npcPosition)) {
            playSoundAtCords(player.position(), sound, manager);
            return;
        }

        manager.play(new SoundAtArmorStand(soundEvent, rawName, sound));

    }

    private void playSoundAtCords(Vec3 position, SoundObject soundObject, SoundManager manager) {

        SoundEvent soundEvent = soundObject.getCustomSoundClass().soundEvent();
        manager.play(new SoundAtCords(soundEvent, soundObject, position));
    }

    private boolean isOutsideReach(SoundObject soundObject, Player player, Vec3 npcPosition) {
        return (player.position().distanceToSqr(npcPosition) >= config.getBlockCutOff() * config.getBlockCutOff()
                && player.position().distanceToSqr(npcPosition) >= soundObject.getFallOff() * soundObject.getFallOff());
    }


}
