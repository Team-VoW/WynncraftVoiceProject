/*
 * Copyright © Team-VoW 2024.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.player;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.sound.SoundObject;
import com.wynnvp.wynncraftvp.utils.Utils;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;

public class AudioPlayer {
    public final OpenAlPlayer openAlPlayer;

    public final AutoProgress autoProgress;

    public AudioPlayer() {
        openAlPlayer = new OpenAlPlayer();
        autoProgress = new AutoProgress();
    }

    private void write(AudioData data) {
        openAlPlayer.playAudio(data);
        if (ModCore.config.autoProgress) {
            autoProgress.start(data.getAudioLengthMillis());
        }
    }

    public void playAudioFile(ResourceLocation resouce) {
        openAlPlayer.stopAudio();
        Optional<AudioData> audioData = OggDecoder.getAudioData(resouce);

        if (audioData.isEmpty()) {
            Utils.sendMessage("Failed to load audio file: " + resouce.getPath());
            return;
        }

        write(audioData.get());
    }

    public void play(SoundObject soundObject) {
        stopPlayingCurrentSound();

        openAlPlayer.updateSpeaker(
                soundObject.isSoundAtPlayer() ? "" : soundObject.getTrimmedNpcName(), soundObject.getPosition());

        String audioFileName = soundObject.getId();
        ResourceLocation resourceLocation =
                ResourceLocation.fromNamespaceAndPath("wynnvp", "sounds/" + audioFileName + ".ogg");

        playAudioFile(resourceLocation);
    }

    public void stopPlayingCurrentSound() {
        openAlPlayer.stopAudio();
    }
}
