/*
 * Copyright © Team-VoW 2025-2026.
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
import com.wynnvp.wynncraftvp.sound.physics.AppliedAcoustics;
import com.wynnvp.wynncraftvp.sound.physics.ComputedReverb;
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
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
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

    /** Private reverb rigs for ray-traced lines, one per source that can get one. */
    private SourceEffectPool effectPool;

    /** Set once the sound engine has restarted under us; every OpenAL name we hold is dead. */
    private volatile boolean disposed;

    /** How long the render thread will wait for audio work to stop during a sound engine reload. */
    private static final long DISPOSE_TIMEOUT_MS = 250;

    /** Measures the space around each speaker. Client thread only. */
    private final SoundPhysicsManager soundPhysics = new SoundPhysicsManager();

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
                effectPool = new SourceEffectPool(false);
                return;
            }

            // Clear any error left behind by earlier work before probing. alGetError is global and
            // consuming, so a stale error from anywhere else would otherwise read as "no EFX here"
            // and silently disable reverb for the rest of the session.
            AL10.alGetError();

            // Try to create a test effect to verify EFX support
            int testEffect = EXTEfx.alGenEffects();
            if (AL10.alGetError() != AL10.AL_NO_ERROR) {
                ModCore.warn("OpenAL EFX not supported on this system, reverb disabled");
                effectPool = new SourceEffectPool(false);
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

                // Create auxiliary effect slot
                int slot = EXTEfx.alGenAuxiliaryEffectSlots();
                if (AL10.alGetError() != AL10.AL_NO_ERROR) {
                    ModCore.error("Failed to create effect slot for " + reverb.name());
                    EXTEfx.alDeleteEffects(effect);
                    continue;
                }

                // Apply the preset and attach the effect to its slot
                if (!ReverbApplier.apply(effect, slot, ReverbPresets.getParams(reverb))) {
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
            effectPool = new SourceEffectPool(true);
        } catch (Exception e) {
            ModCore.error("Failed to initialize EFX", e);
            efxSupported = false;
            effectPool = new SourceEffectPool(false);
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
        submit(() -> {
            if (gameSettings.getSoundSourceVolume(SoundSource.VOICE) <= 0F) {
                if (!hasShownVolumeMessage) {
                    Utils.sendMessage("Voice/Speech volume is off. Turn it on in your settings to hear the voices.");
                    hasShownVolumeMessage = true;
                }
                return;
            }
            int sourceID = AL10.alGenSources();

            // Allow boosting above 1.0 gain (many implementations clamp otherwise)
            AL10.alSourcef(sourceID, AL10.AL_MAX_GAIN, 10.0f);
            AL10.alSourcef(sourceID, AL10.AL_MIN_GAIN, 0.0f);

            // Ensure correct gain is set before playback starts (prevents first "full volume" frame)
            updateVolume(sourceID);

            CurrentSpeaker speaker = new CurrentSpeaker(audioData.speakerName, audioData.pos, audioData.reverb);
            // The space was measured as playback was being set up, so the line is already in the
            // right room on its very first sample rather than a tick later.
            speaker.publishAcoustics(soundPhysics.takePrimed(audioData.pos));

            int buffer = AL10.alGenBuffers();
            speaker.setBuffer(buffer);

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
            }

            if (monoData == null || monoData.remaining() == 0) {
                // Give the source and buffer straight back. Leaving them behind would strand a source
                // that never reaches AL_STOPPED, so it would never be cleaned up and would keep
                // isPlayingAnything() true for the rest of the session.
                AL10.alDeleteSources(sourceID);
                AL10.alDeleteBuffers(buffer);
                ModCore.warn("Refusing to play a line with no audio data: " + audioData.speakerName);
                return;
            }

            // Always use MONO16 format since that's what we're creating
            AL11.alBufferData(buffer, AL11.AL_FORMAT_MONO16, monoData, (int) audioData.audioFormat.getSampleRate());
            AL11.alSourceQueueBuffers(sourceID, buffer);

            attachEffects(sourceID, speaker);

            updateVolume(sourceID);

            // Published only now that it is fully set up: the tick loop runs on this same pool and
            // must never find a half-configured source.
            currentSpeakers.put(sourceID, speaker);
            sourceIDs.add(sourceID);
            AL11.alSourcePlay(sourceID);
        });
    }

    /**
     * Gives a source its reverb and occlusion rig.
     *
     * <p>A line with ray tracing on borrows a private rig so its parameters can be rewritten as the
     * player moves. When that is unavailable -- physics off, no EFX, or every rig busy -- it falls
     * back to the shared preset slot, which is what the mod has always done.
     */
    private void attachEffects(int sourceID, CurrentSpeaker speaker) {
        if (!efxSupported) {
            return;
        }

        if (SoundPhysicsManager.isEnabled()) {
            Optional<SourceEffectPool.Handles> handles = effectPool.acquire();
            if (handles.isPresent()) {
                speaker.setHandles(handles.get());
                // Take manual control of the filters; OpenAL's own cone and air-absorption automation
                // would otherwise fight the numbers the tracer produced.
                AL11.alSourcei(sourceID, EXTEfx.AL_DIRECT_FILTER_GAINHF_AUTO, AL10.AL_FALSE);
                AL11.alSourcei(sourceID, EXTEfx.AL_AUXILIARY_SEND_FILTER_GAIN_AUTO, AL10.AL_FALSE);
                AL11.alSourcei(sourceID, EXTEfx.AL_AUXILIARY_SEND_FILTER_GAINHF_AUTO, AL10.AL_FALSE);
                applyAcoustics(sourceID, speaker);
                return;
            }
        }

        applyPreset(sourceID, speaker.forcedReverb());
    }

    /**
     * Applies one of the ten shared preset slots.
     *
     * @param reverb the preset named in sounds.json, or {@code null} for the historical default
     */
    private void applyPreset(int sourceID, Reverb reverb) {
        if (!SoundPhysicsManager.reverbEnabled()) {
            return;
        }

        Integer slot = reverbSlots.get(reverb == null ? Reverb.OUTSIDE : reverb);
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

    /** Pushes whatever the client thread last measured for this line at its private rig. */
    private void applyAcoustics(int sourceID, CurrentSpeaker speaker) {
        SourceEffectPool.Handles handles = speaker.handles();
        if (handles == null) {
            return;
        }
        AppliedAcoustics applied = speaker.acoustics();

        if (applied.hasOcclusion()) {
            EXTEfx.alFilterf(handles.filter(), EXTEfx.AL_LOWPASS_GAIN, applied.directGain());
            EXTEfx.alFilterf(handles.filter(), EXTEfx.AL_LOWPASS_GAINHF, applied.directGainHf());
            // Re-attaching is what makes the edit take effect; without it the filter keeps whatever
            // it held the first time it was attached.
            AL11.alSourcei(sourceID, EXTEfx.AL_DIRECT_FILTER, handles.filter());
            speaker.setOcclusionAttached(true);
        } else if (speaker.isOcclusionAttached()) {
            AL11.alSourcei(sourceID, EXTEfx.AL_DIRECT_FILTER, EXTEfx.AL_FILTER_NULL);
            speaker.setOcclusionAttached(false);
        }

        ComputedReverb reverb = applied.reverb();
        if (reverb == null) {
            if (speaker.isSendAttached()) {
                AL11.alSource3i(
                        sourceID, EXTEfx.AL_AUXILIARY_SEND_FILTER, EXTEfx.AL_EFFECTSLOT_NULL, 0, EXTEfx.AL_FILTER_NULL);
                speaker.setSendAttached(false);
            }
            return;
        }

        if (!speaker.isSendAttached()) {
            // One send, never two. Minecraft creates its OpenAL context without asking for extra
            // auxiliary sends, so only the default pair is guaranteed to exist.
            AL11.alSource3i(sourceID, EXTEfx.AL_AUXILIARY_SEND_FILTER, handles.slot(), 0, EXTEfx.AL_FILTER_NULL);
            speaker.setSendAttached(true);
        }

        // Reprogramming the effect makes OpenAL rebuild its reverb state, so skip it once the glide
        // has settled and the numbers stop moving.
        if (!reverb.equals(speaker.lastAppliedReverb())) {
            ReverbApplier.applyThrottled(handles.effect(), handles.slot(), reverb);
            speaker.setLastAppliedReverb(reverb);
        }
    }

    /** True while at least one Voices of Wynn source is still alive (see {@link #onTick()}). */
    public boolean isPlayingAnything() {
        return !sourceIDs.isEmpty();
    }

    public void stopAudio() {
        submit(() -> {
            synchronized (sourceIDs) {
                for (int sourceID : sourceIDs) {
                    AL11.alSourceStop(sourceID);
                    releaseSource(sourceID, currentSpeakers.get(sourceID));
                }
                sourceIDs.clear();
                currentSpeakers.clear();
            }
        });
    }

    /**
     * Called once per world tick, on the client thread.
     *
     * <p>The measuring happens here, inline. It reads a few thousand block states, which is only safe
     * on this thread; the audio work is then handed to the worker pool as it always was.
     */
    public void onTick() {
        if (disposed) {
            return;
        }
        soundPhysics.onClientTick(currentSpeakers.values());
        submit(this::pumpSources);
    }

    private void pumpSources() {
        synchronized (sourceIDs) {
            sourceIDs.removeIf(sourceID -> {
                CurrentSpeaker speaker = currentSpeakers.get(sourceID);
                int state = AL11.alGetSourcei(sourceID, AL11.AL_SOURCE_STATE);
                if (state == AL11.AL_STOPPED) {
                    releaseSource(sourceID, speaker);
                    currentSpeakers.remove(sourceID);
                    return true;
                }
                if (speaker != null) {
                    setPosition(sourceID, speaker.publishedPosition());
                    applyAcoustics(sourceID, speaker);
                }
                return false;
            });
        }
    }

    /** Detaches everything this source borrowed, then deletes the source and its buffer. */
    private void releaseSource(int sourceID, CurrentSpeaker speaker) {
        if (speaker != null && speaker.handles() != null) {
            AL11.alSourcei(sourceID, EXTEfx.AL_DIRECT_FILTER, EXTEfx.AL_FILTER_NULL);
            AL11.alSource3i(
                    sourceID, EXTEfx.AL_AUXILIARY_SEND_FILTER, EXTEfx.AL_EFFECTSLOT_NULL, 0, EXTEfx.AL_FILTER_NULL);
            effectPool.release(speaker.handles());
            speaker.setHandles(null);
        }
        AL10.alDeleteSources(sourceID);
        if (speaker != null && speaker.buffer() != 0) {
            // Only once the source is gone, since it holds a reference to the buffer until then.
            AL10.alDeleteBuffers(speaker.buffer());
            speaker.setBuffer(0);
        }
    }

    /**
     * Retires this player because the sound engine is restarting.
     *
     * <p>Deliberately makes no OpenAL calls. A sound engine reload destroys the whole context and
     * builds a new one, so every source, buffer, effect and filter this player owned died with it.
     * Deleting them would not free anything -- the names belong to a context that no longer exists --
     * and every such call raises AL_INVALID_NAME into the new context's shared error state, where the
     * incoming player's EFX probe reads it and concludes the hardware has no EFX at all.
     */
    public void dispose() {
        disposed = true;
        executorService.shutdownNow();
        // Wait for anything already running to finish. A task caught mid-flight would otherwise keep
        // issuing OpenAL calls while the render thread is building the replacement context, and every
        // one of them lands in the shared error state the incoming player's EFX probe reads.
        try {
            if (!executorService.awaitTermination(DISPOSE_TIMEOUT_MS, TimeUnit.MILLISECONDS)) {
                ModCore.warn("Audio worker did not stop in time while the sound engine was reloading");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        synchronized (sourceIDs) {
            sourceIDs.clear();
        }
        currentSpeakers.clear();
    }

    /**
     * Queues audio work, unless this player has been retired.
     *
     * <p>The disposed check and the submission cannot be atomic, so a reload landing between them
     * would throw. There is nothing to do about it but drop the work, which is exactly right: the
     * context that work was for no longer exists.
     */
    private void submit(Runnable task) {
        if (disposed) {
            return;
        }
        try {
            executorService.execute(task);
        } catch (RejectedExecutionException ignored) {
            // Retired between the check and the submission.
        }
    }

    public SoundPhysicsManager soundPhysics() {
        return soundPhysics;
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
        float base = gameSettings.getSoundSourceVolume(SoundSource.VOICE);

        int percent = (ModCore.config != null) ? ModCore.config.getVoiceVolume() : 100;
        float gain = base * (percent / 100.0f);

        if (gain <= 0.0001f) {
            AL11.alSourceStop(sourceID);
            return;
        }

        AL10.alSourcef(sourceID, AL10.AL_GAIN, gain);
    }
}
