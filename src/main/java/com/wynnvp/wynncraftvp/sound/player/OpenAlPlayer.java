/*
 * Copyright © Team-VoW 2024.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.player;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.sound.sampled.AudioFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.AL11;

public class OpenAlPlayer {
    private int sourceID;
    private final ExecutorService executorService;
    protected volatile int bufferIndex;
    protected final int[] buffers;
    private static final float maxDistance = 20000;
    private final CurrentSpeaker currentSpeaker;

    private final Options gameSettings; // For retrieving the volume setting

    public void updateSpeaker(String speakerName, Optional<Vec3> pos) {
        currentSpeaker.setNpc(speakerName, pos);

        /*        executorService.execute(() -> {
            //soundEffects.setEcho();
        });*/
    }

    public OpenAlPlayer() {
        currentSpeaker = new CurrentSpeaker();
        this.buffers = new int[3000];
        executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {});

        createOpenAL();

        gameSettings = Minecraft.getInstance().options;
    }

    private void createOpenAL() {
        executorService.execute(() -> {
            // Create an OpenAL source
            sourceID = AL10.alGenSources();

            // Create OpenAL buffers
            AL10.alGenBuffers(buffers);
            AL11.alDistanceModel(AL11.AL_LINEAR_DISTANCE);
            AL11.alSourcef(sourceID, AL11.AL_MAX_DISTANCE, maxDistance);
            AL11.alSourcef(sourceID, AL11.AL_REFERENCE_DISTANCE, 0F);
        });
    }

    public void playAudio(AudioData audioData) {
        executorService.execute(() -> {
            removeProcessedBuffersSync();
            writeSync(audioData);
            startPlayingIfStoppedSync();
        });
    }

    private void writeSync(AudioData audioData) {
        int queuedBuffers = AL11.alGetSourcei(sourceID, AL11.AL_BUFFERS_QUEUED);
        if (queuedBuffers >= buffers.length) {
            int sampleOffset = AL11.alGetSourcei(sourceID, AL11.AL_SAMPLE_OFFSET);
            int buffersToSkip = queuedBuffers - 100;
            AL11.alSourcei(
                    sourceID, AL11.AL_SAMPLE_OFFSET, sampleOffset + buffersToSkip * audioData.byteBuffer.remaining());
            removeProcessedBuffersSync();
        }

        // Convert the audio data to mono
        ByteBuffer monoData = convertToMono(audioData);

        // Always use a mono format (16-bit PCM)
        AL11.alBufferData(
                buffers[bufferIndex], AL11.AL_FORMAT_MONO16, monoData, (int) audioData.audioFormat.getSampleRate());

        AL11.alSourceQueueBuffers(sourceID, buffers[bufferIndex]);
        bufferIndex = (bufferIndex + 1) % buffers.length;
    }

    // Method to convert stereo ByteBuffer to mono
    private ByteBuffer convertToMono(AudioData audioData) {
        AudioFormat format = audioData.audioFormat;
        ByteBuffer originalData = audioData.byteBuffer;

        int channels = format.getChannels();
        int sampleSizeInBits = format.getSampleSizeInBits();
        boolean bigEndian = format.isBigEndian();

        if (channels == 1) {
            // Already mono, no conversion needed
            return originalData;
        }

        // We are only handling 16-bit PCM audio here
        if (sampleSizeInBits != 16) {
            throw new IllegalArgumentException("Unsupported sample size: " + sampleSizeInBits + " bits.");
        }

        // Create a new ByteBuffer for mono audio (half the size of stereo buffer)
        ByteBuffer monoData = ByteBuffer.allocate(originalData.remaining() / 2)
                .order(bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);

        while (originalData.remaining() >= 4) {
            // For 16-bit stereo PCM, read left and right samples (2 bytes each)
            short leftSample = originalData.getShort();
            short rightSample = originalData.getShort();

            // Average the left and right channels to get mono
            short monoSample = (short) ((leftSample + rightSample) / 2);

            // Write the mono sample
            monoData.putShort(monoSample);
        }

        // Flip the buffer to prepare for reading
        monoData.flip();
        return monoData;
    }

    private void startPlayingIfStoppedSync() {
        if (isStopped()) {
            AL11.alSourcePlay(sourceID);
        }
    }

    private boolean isStopped() {
        int state = AL11.alGetSourcei(sourceID, AL11.AL_SOURCE_STATE);
        return state == AL11.AL_INITIAL || state == AL11.AL_STOPPED || state <= 0;
    }

    public void stopAudio() {
        executorService.execute(this::stopPlayingSync);
    }

    private void stopPlayingSync() {
        AL11.alSourceStop(sourceID);
    }

    private void removeProcessedBuffersSync() {
        int processed = AL11.alGetSourcei(sourceID, AL11.AL_BUFFERS_PROCESSED);
        for (int i = 0; i < processed; i++) {
            AL11.alSourceUnqueueBuffers(sourceID);
        }
    }

    public void cleanup() {
        AL10.alDeleteSources(sourceID);
        AL10.alDeleteBuffers(buffers);
        executorService.shutdown(); // Shutdown the executor
    }

    public void onTick() {
        // IF currently not playing an audio do nothing
        if (isStopped()) return;

        setPosition(currentSpeaker.getUpdatedPosition());
    }

    private void updateVolumeSync() {
        // Retrieve the volume of the "Voice/Speech" category
        float volume = gameSettings.getSoundSourceVolume(SoundSource.VOICE); // For newer versions
        // For older versions, use SoundCategory.VOICE or SOUND_CATEGORY_VOICE

        // Apply the volume to the OpenAL source
        AL10.alSourcef(sourceID, AL10.AL_GAIN, volume);
    }

    public void setPosition(Optional<Vec3> soundPos) {
        executorService.execute(() -> {
            setPositionSync(soundPos);
        });
    }

    private void setPositionSync(Optional<Vec3> soundPos) {
        soundPos.ifPresentOrElse(
                pos -> {
                    AL11.alSourcei(sourceID, AL11.AL_SOURCE_RELATIVE, AL11.AL_FALSE);
                    AL11.alSource3f(sourceID, AL11.AL_POSITION, (float) pos.x, (float) pos.y, (float) pos.z);
                },
                () -> {
                    AL11.alSourcei(sourceID, AL11.AL_SOURCE_RELATIVE, AL11.AL_TRUE);
                    AL11.alSource3f(sourceID, AL11.AL_POSITION, 0F, 0F, 0F);
                });

        updateVolumeSync();
    }
}
