/*
 * Copyright © Team-VoW 2025.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.player;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.WaveformSimilarityBasedOverlapAdd;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.sound.Reverb;
import com.wynnvp.wynncraftvp.utils.Utils;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.sound.sampled.AudioFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.AL11;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.EXTEfx;

public class OpenAlPlayer {
    private final ExecutorService executorService;
    private final List<Integer> sourceIDs = Collections.synchronizedList(new ArrayList<>());
    private final Map<Integer, CurrentSpeaker> currentSpeakers = new ConcurrentHashMap<>();
    private static final float maxDistance = 20000;
    private final Options gameSettings;
    private boolean hasShownVolumeMessage = false;

    // EFX (Environmental Audio Extension) support
    private boolean efxSupported = false;
    private final Map<Reverb, Integer> reverbEffects = new EnumMap<>(Reverb.class);
    private final Map<Reverb, Integer> reverbSlots = new EnumMap<>(Reverb.class);

    public OpenAlPlayer() {
        executorService = Executors.newCachedThreadPool();
        gameSettings = Minecraft.getInstance().options;
        initializeEFX();
    }

    /**
     * Initializes OpenAL EFX (Effects Extension) for reverb support.
     * Creates effect objects and auxiliary effect slots for each reverb preset.
     */
    private void initializeEFX() {
        try {
            // Check if EFX is supported
            long device = ALC10.alcGetCurrentContext();
            if (device == 0) {
                ModCore.warn("OpenAL context not available, reverb disabled");
                return;
            }

            // Try to create a test effect to verify EFX support
            int testEffect = EXTEfx.alGenEffects();
            if (AL10.alGetError() != AL10.AL_NO_ERROR) {
                ModCore.warn("OpenAL EFX not supported on this system, reverb disabled");
                return;
            }

            // EFX is supported, delete the test effect
            EXTEfx.alDeleteEffects(testEffect);
            efxSupported = true;
            ModCore.info("OpenAL EFX initialized successfully");

            // Create effect objects and auxiliary effect slots for each reverb type
            for (Reverb reverb : Reverb.values()) {
                // Create effect
                int effect = EXTEfx.alGenEffects();
                if (AL10.alGetError() != AL10.AL_NO_ERROR) {
                    ModCore.error("Failed to create effect for " + reverb.name());
                    continue;
                }

                // Set effect type to reverb
                EXTEfx.alEffecti(effect, EXTEfx.AL_EFFECT_TYPE, EXTEfx.AL_EFFECT_REVERB);
                if (AL10.alGetError() != AL10.AL_NO_ERROR) {
                    ModCore.error("Failed to set effect type for " + reverb.name());
                    EXTEfx.alDeleteEffects(effect);
                    continue;
                }

                // Apply reverb parameters
                ReverbPresets.ReverbParams params = ReverbPresets.getParams(reverb);
                params.applyToEffect(effect);

                // Create auxiliary effect slot
                int slot = EXTEfx.alGenAuxiliaryEffectSlots();
                if (AL10.alGetError() != AL10.AL_NO_ERROR) {
                    ModCore.error("Failed to create effect slot for " + reverb.name());
                    EXTEfx.alDeleteEffects(effect);
                    continue;
                }

                // Attach effect to slot
                EXTEfx.alAuxiliaryEffectSloti(slot, EXTEfx.AL_EFFECTSLOT_EFFECT, effect);
                if (AL10.alGetError() != AL10.AL_NO_ERROR) {
                    ModCore.error("Failed to attach effect to slot for " + reverb.name());
                    EXTEfx.alDeleteEffects(effect);
                    EXTEfx.alDeleteAuxiliaryEffectSlots(slot);
                    continue;
                }

                // Store effect and slot
                reverbEffects.put(reverb, effect);
                reverbSlots.put(reverb, slot);
            }

            ModCore.info("Created " + reverbEffects.size() + " reverb presets");
        } catch (Exception e) {
            ModCore.error("Failed to initialize EFX", e);
            efxSupported = false;
        }
    }

    private ByteBuffer timeStretch(AudioData audioData, float speed) {
        try {
            ByteBuffer originalBuffer = audioData.byteBuffer;
            byte[] audioBytes = new byte[originalBuffer.remaining()];
            originalBuffer.get(audioBytes);
            originalBuffer.rewind();

            float sampleRate = audioData.audioFormat.getSampleRate();

            AudioFormat javaSoundFormat = new AudioFormat(
                    sampleRate,
                    16,
                    1,
                    true, // signed
                    false // little-endian
                    );

            ByteArrayOutputStream out = new ByteArrayOutputStream();

            // Create WSOLA processor first to get proper buffer sizes
            WaveformSimilarityBasedOverlapAdd wsola = new WaveformSimilarityBasedOverlapAdd(
                    WaveformSimilarityBasedOverlapAdd.Parameters.speechDefaults(speed, sampleRate));

            // Use WSOLA's recommended buffer sizes
            int bufferSize = wsola.getInputBufferSize();
            int overlap = wsola.getOverlap();

            AudioDispatcher dispatcher =
                    AudioDispatcherFactory.fromByteArray(audioBytes, javaSoundFormat, bufferSize, overlap);

            // Set dispatcher on WSOLA
            wsola.setDispatcher(dispatcher);

            // Add processors in correct order
            dispatcher.addAudioProcessor(wsola);
            dispatcher.addAudioProcessor(new AudioProcessor() {
                @Override
                public boolean process(AudioEvent audioEvent) {
                    try {
                        out.write(audioEvent.getByteBuffer());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                }

                @Override
                public void processingFinished() {
                    // No-op
                }
            });

            dispatcher.run();

            byte[] processedAudioBytes = out.toByteArray();
            // Create a direct ByteBuffer which is preferred for native OpenAL operations
            ByteBuffer processedBuffer = ByteBuffer.allocateDirect(processedAudioBytes.length);
            processedBuffer.put(processedAudioBytes);
            processedBuffer.flip(); // Important! Prepare the buffer for reading

            return processedBuffer;
        } catch (Exception e) {
            Utils.sendMessage("Failed to apply speed up, using original audio.");
            e.printStackTrace();
            return audioData.byteBuffer;
        }
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

            // Only set up 3D audio if not playing all sounds on player
            if (!ModCore.config.isPlayAllSoundsOnPlayer()) {
                AL11.alDistanceModel(AL11.AL_LINEAR_DISTANCE);
                AL11.alSourcef(sourceID, AL11.AL_MAX_DISTANCE, maxDistance);
                AL11.alSourcef(sourceID, AL11.AL_REFERENCE_DISTANCE, 0F);
            } else {
                // For non-3D audio, make the source relative to listener
                AL11.alSourcei(sourceID, AL11.AL_SOURCE_RELATIVE, AL11.AL_TRUE);
                AL11.alSource3f(sourceID, AL11.AL_POSITION, 0F, 0F, 0F);
            }

            ByteBuffer monoData = audioData.byteBuffer;
            float speed = ModCore.config.getPlaybackSpeed();

            if (speed != 1.0f) {
                monoData = timeStretch(audioData, speed);

                // Always use MONO16 format since that's what we're creating
                AL11.alBufferData(
                        buffers[0], AL11.AL_FORMAT_MONO16, monoData, (int) audioData.audioFormat.getSampleRate());
                AL11.alSourceQueueBuffers(sourceID, buffers[0]);

                // Apply reverb effect if supported and enabled
                applyReverb(sourceID, audioData.reverb);

                AL11.alSourcePlay(sourceID);
                return;
            }

            if (monoData == null || monoData.remaining() == 0) {
                throw new IllegalArgumentException("Invalid audio data.");
            }

            AL11.alBufferData(buffers[0], AL11.AL_FORMAT_MONO16, monoData, (int) audioData.audioFormat.getSampleRate());
            AL11.alSourceQueueBuffers(sourceID, buffers[0]);

            // Apply reverb effect if supported and enabled
            applyReverb(sourceID, audioData.reverb);

            AL11.alSourcePlay(sourceID);
        });
    }

    /**
     * Applies reverb effect to an audio source.
     * @param sourceID The OpenAL source ID
     * @param reverb The reverb type to apply (can be null)
     */
    private void applyReverb(int sourceID, Reverb reverb) {
        // Skip if EFX not supported or reverb not specified
        if (!efxSupported || reverb == null) {
            return;
        }

        // Skip if reverb disabled in config
        if (ModCore.config != null && !ModCore.config.enableReverb) {
            return;
        }

        // Get the effect slot for this reverb type
        Integer slot = reverbSlots.get(reverb);
        if (slot == null) {
            return;
        }

        // Attach the effect slot to the source
        // Send 0 = main output, Send 1-4 = auxiliary sends
        AL11.alSource3i(sourceID, EXTEfx.AL_AUXILIARY_SEND_FILTER, slot, 0, EXTEfx.AL_FILTER_NULL);

        if (AL10.alGetError() != AL10.AL_NO_ERROR) {
            ModCore.warn("Failed to apply reverb effect to audio source");
        }
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
        // If playing all sounds on player, always use non-3D positioning
        if (ModCore.config.isPlayAllSoundsOnPlayer()) {
            AL11.alSourcei(sourceID, AL11.AL_SOURCE_RELATIVE, AL11.AL_TRUE);
            AL11.alSource3f(sourceID, AL11.AL_POSITION, 0F, 0F, 0F);
        } else {
            soundPos.ifPresentOrElse(
                    pos -> {
                        AL11.alSourcei(sourceID, AL11.AL_SOURCE_RELATIVE, AL11.AL_FALSE);
                        AL11.alSource3f(sourceID, AL11.AL_POSITION, (float) pos.x, (float) pos.y, (float) pos.z);
                    },
                    () -> {
                        AL11.alSourcei(sourceID, AL11.AL_SOURCE_RELATIVE, AL11.AL_TRUE);
                        AL11.alSource3f(sourceID, AL11.AL_POSITION, 0F, 0F, 0F);
                    });
        }
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
