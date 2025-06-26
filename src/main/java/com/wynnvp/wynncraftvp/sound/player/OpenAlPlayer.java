/*
 * Copyright © Team-VoW 2025.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.player;

import com.wynnvp.wynncraftvp.utils.Utils;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.AL11;

public class OpenAlPlayer {
    private final ExecutorService executorService;
    private final List<Integer> sourceIDs = Collections.synchronizedList(new ArrayList<>());
    private final Map<Integer, CurrentSpeaker> currentSpeakers = new ConcurrentHashMap<>();
    private static final float maxDistance = 20000;
    private final Options gameSettings;
    private boolean hasShownVolumeMessage = false;

    public OpenAlPlayer() {
        executorService = Executors.newCachedThreadPool();
        gameSettings = Minecraft.getInstance().options;
    }

    public void playAudio(AudioData audioData) {
        executorService.execute(() -> {
            if (gameSettings.getSoundSourceVolume(SoundSource.VOICE) <= 0F) {
                if (!hasShownVolumeMessage) {
                    Utils.sendMessage("Voice/Speech volume is off. Turn it on in your settings to hear the voices.");
                    hasShownVolumeMessage = true;
                }
                return;
            }
            int sourceID = AL10.alGenSources();
            sourceIDs.add(sourceID);
            CurrentSpeaker speaker = new CurrentSpeaker(audioData.speakerName, audioData.pos);
            currentSpeakers.put(sourceID, speaker);

            int[] buffers = new int[3000];
            AL10.alGenBuffers(buffers);
            AL11.alDistanceModel(AL11.AL_LINEAR_DISTANCE);
            AL11.alSourcef(sourceID, AL11.AL_MAX_DISTANCE, maxDistance);
            AL11.alSourcef(sourceID, AL11.AL_REFERENCE_DISTANCE, 0F);

            ByteBuffer monoData = audioData.byteBuffer;
            AL11.alBufferData(buffers[0], AL11.AL_FORMAT_MONO16, monoData, (int) audioData.audioFormat.getSampleRate());
            AL11.alSourceQueueBuffers(sourceID, buffers[0]);
            AL11.alSourcePlay(sourceID);
        });
    }

    public void stopAudio() {
        executorService.execute(() -> {
            synchronized (sourceIDs) {
                for (int sourceID : sourceIDs) {
                    AL11.alSourceStop(sourceID);
                    AL10.alDeleteSources(sourceID);
                }
                sourceIDs.clear();
                currentSpeakers.clear();
            }
        });
    }

    public void onTick() {
        executorService.execute(() -> {
            synchronized (sourceIDs) {
                sourceIDs.removeIf(sourceID -> {
                    int state = AL11.alGetSourcei(sourceID, AL11.AL_SOURCE_STATE);
                    if (state == AL11.AL_STOPPED) {
                        AL10.alDeleteSources(sourceID);
                        currentSpeakers.remove(sourceID);
                        return true;
                    }
                    setPosition(sourceID, currentSpeakers.get(sourceID).getUpdatedPosition());
                    return false;
                });
            }
        });
    }

    private void setPosition(int sourceID, Optional<Vec3> soundPos) {
        soundPos.ifPresentOrElse(
                pos -> {
                    AL11.alSourcei(sourceID, AL11.AL_SOURCE_RELATIVE, AL11.AL_FALSE);
                    AL11.alSource3f(sourceID, AL11.AL_POSITION, (float) pos.x, (float) pos.y, (float) pos.z);
                },
                () -> {
                    AL11.alSourcei(sourceID, AL11.AL_SOURCE_RELATIVE, AL11.AL_TRUE);
                    AL11.alSource3f(sourceID, AL11.AL_POSITION, 0F, 0F, 0F);
                });
        updateVolume(sourceID);
    }

    private void updateVolume(int sourceID) {
        float volume = gameSettings.getSoundSourceVolume(SoundSource.VOICE);
        if (volume <= 0F) {
            AL11.alSourceStop(sourceID);
        }
        AL10.alSourcef(sourceID, AL10.AL_GAIN, volume);
    }
}
