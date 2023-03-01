package com.wynnvp.wynncraftvp.sound;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.logging.VowLogger;
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
        if (config.isLogPlayingInformation()){
            VowLogger.logLine("[Attempting to play] " + lineData.getRealLine(), "Info");
        }
        if (!config.isOnlyLogNotPlayingLines() && config.isLogDialogueLines() && lineData.isNPCSentLine())
            VowLogger.logLine(lineData.getRealLine());

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

        if (soundsHandler.get(line).isEmpty()) {
            if (config.isLogDialogueLines() && config.isOnlyLogNotPlayingLines() && lineData.isNPCSentLine())
                VowLogger.logLine(lineData.getRealLine());
            lineReporter.MissingLine(lineData);
            return false;
        }

        if (player == null) {
            if (config.isLogPlayingInformation())
                VowLogger.logLine("Player was null. Sound not played", "Error");
            return false;
        }

        if (world == null) {
            if (config.isLogPlayingInformation())
                VowLogger.logLine("World was null. Sound not played", "Error");
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
            Vector3 posAsVector3 = sound.getPosition();
            Vec3 soundPos = new Vec3(posAsVector3.x, posAsVector3.y, posAsVector3.z);
            playSoundAtCords(soundPos, sound, manager);
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
        int soundObjectFallOff = soundObject.getFallOff();

        int squaredFalloff = soundObjectFallOff == 0 ? config.getBlockCutOff() * config.getBlockCutOff() : soundObjectFallOff * soundObjectFallOff;
        return (player.position().distanceToSqr(npcPosition) >= squaredFalloff);
    }


}
