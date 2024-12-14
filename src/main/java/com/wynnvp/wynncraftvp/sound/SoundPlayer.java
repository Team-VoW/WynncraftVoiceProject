/*
 * Copyright © Team-VoW 2024.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound;

import static com.wynnvp.wynncraftvp.ModCore.config;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.logging.VowLogger;
import com.wynnvp.wynncraftvp.sound.line.LineData;
import com.wynnvp.wynncraftvp.sound.line.LineReporter;
import com.wynnvp.wynncraftvp.utils.Utils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class SoundPlayer {
    private final LineReporter lineReporter;

    public SoundPlayer() {
        lineReporter = new LineReporter();
    }

    // Code that is run to play all the sounds
    public void playSound(LineData lineData) {
        if (config.isLogPlayingInformation()) {
            VowLogger.logLine(
                    "[Attempting to play] " + lineData.getRealLine().trim() + " HTTP encoded:"
                            + Utils.HTTPEncode(lineData.getRealLine().trim()),
                    "Info");
        }

        String line = lineData.getSoundLine();

        SoundsHandler soundsHandler = ModCore.instance.soundsHandler;

        if (!canPlaySound(soundsHandler, lineData)) return;

        if (!config.isOnlyLogNotPlayingLines() && config.isLogDialogueLines())
            VowLogger.logLine(lineData.getRealLine() + " [PLAYED]");

        soundsHandler.get(line).ifPresentOrElse(this::PlaySoundObject, () -> {
            if (config.isLogPlayingInformation()) {
                VowLogger.logLine("Could not play sound: " + line, "Info");
            }
        });
    }

    private boolean canPlaySound(SoundsHandler soundsHandler, LineData lineData) {
        String line = lineData.getSoundLine();

        if (soundsHandler.get(line).isEmpty()) {
            if (config.isLogDialogueLines() && lineData.isNPCSentLine()) VowLogger.logLine(lineData.getRealLine());
            lineReporter.MissingLine(lineData);
            return false;
        }
        return true;
    }

    private void PlaySoundObject(SoundObject sound) {
        ModCore.instance.audioPlayer.play(sound);
        // sound.getCustomSoundClass()

        /*        final CustomSoundClass customSoundClass = sound.getCustomSoundClass();
        final SoundEvent soundEvent = customSoundClass.soundEvent();

        // If this sound contains info about a location to play it at
        if (sound.getPosition() != null) {
            Vector3f posAsVector3 = sound.getPosition();
            Vec3 soundPos = new Vec3(posAsVector3.x, posAsVector3.y, posAsVector3.z);
            lastPlayedSound = playSoundAtCords(soundPos, sound, manager);
            return;
        }

        // If this sound was set to play at the player pos or the setting to play all sounds on player is turned on
        if (customSoundClass.movingSound() || config.isPlayAllSoundsOnPlayer()) {
            // Play the sound at the player
            var soundAtPlayer = new SoundAtPlayer(soundEvent);
            manager.play(soundAtPlayer);
            lastPlayedSound = soundAtPlayer;
            return;
        }

        String rawName = sound.getNpcName().toLowerCase().replace(" ", "");
        Vec3 npcPosition = NPCHandler.findPosition(rawName);

        if (npcPosition == null || isOutsideReach(sound, player, npcPosition)) {
            lastPlayedSound = playSoundAtCords(player.position(), sound, manager);
            return;
        }

        var soundAtArmorStand = new SoundAtArmorStand(soundEvent, rawName, sound);
        manager.play(soundAtArmorStand);
        lastPlayedSound = soundAtArmorStand;*/
    }

    /*    private SoundInstance playSoundAtCords(Vec3 position, SoundObject soundObject, SoundManager manager) {
        SoundEvent soundEvent = soundObject.getCustomSoundClass().soundEvent();

        var soundAtCords = new SoundAtCords(soundEvent, soundObject, position);

        manager.play(soundAtCords);
        return soundAtCords;
    }*/

    private boolean isOutsideReach(SoundObject soundObject, Player player, Vec3 npcPosition) {
        int soundObjectFallOff = soundObject.getFallOff();

        int squaredFalloff = soundObjectFallOff == 0
                ? config.getBlockCutOff() * config.getBlockCutOff()
                : soundObjectFallOff * soundObjectFallOff;
        return (player.position().distanceToSqr(npcPosition) >= squaredFalloff);
    }
}
