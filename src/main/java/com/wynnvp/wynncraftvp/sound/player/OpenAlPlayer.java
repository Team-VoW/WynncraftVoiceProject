package com.wynnvp.wynncraftvp.sound.player;


import com.jcraft.jorbis.VorbisFile;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.AL11;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static it.unimi.dsi.fastutil.io.TextIO.BUFFER_SIZE;


public class OpenAlPlayer {
    private int sourceID;
    private final ExecutorService executorService;

    protected volatile int bufferIndex;

    protected final int[] buffers;

    private static final int sampleRate = 48000;
    private static final int bufferSampleSize = 960;

    private static final float maxDistance = 20000;

    private final CurrentSpeaker currentSpeaker;

    private Vec3 customPlayPos;


    public OpenAlPlayer() {

        currentSpeaker = new CurrentSpeaker();
        this.buffers = new int[3000];

        executorService = Executors.newSingleThreadExecutor();
        createOpelAL();
    }

    //private static SoundEffects soundEffects;


    private void createOpelAL() {
        executorService.execute(() -> {
            // Create an OpenAL source
            sourceID = AL10.alGenSources();

            // Create an OpenAL buffer
            AL10.alGenBuffers(buffers);
            AL11.alDistanceModel(AL11.AL_LINEAR_DISTANCE);
            AL11.alSourcef(sourceID, AL11.AL_MAX_DISTANCE, maxDistance);
            AL11.alSourcef(sourceID, AL11.AL_REFERENCE_DISTANCE, 0F);

            //soundEffects = new SoundEffects(sourceID);


        });
    }


    public void playAudio(short[] pcmData) {
        executorService.execute(() -> {

            removeProcessedBuffersSync();

            writeSync(pcmData);

            startPlayingIfStoppedSync();

        });

    }


    public void onTick() {
        executorService.execute(() -> {
            if (isStopped()) {
                return;
            }

            if (customPlayPos != null) {
                setPosition(Optional.of(customPlayPos));
                return;
            }

            Optional<Vec3> position = currentSpeaker.getUpdatedPosition();
            setPosition(position);
        });
    }

    public void updateSpeaker(String speakerName, Vec3 pos) {
        executorService.execute(() -> {
            if (pos.x == 0 && pos.y == 0 && pos.z == 0) {
                customPlayPos = null;
            } else {
                customPlayPos = pos;
            }
            currentSpeaker.setNpc(speakerName);
            //soundEffects.setEcho();
        });
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

    private void setVolumeSync(float volume) {
        AL11.alSourcef(sourceID, AL11.AL_MAX_GAIN, 6F);
        AL11.alSourcef(sourceID, AL11.AL_GAIN, volume);
        AL11.alListenerf(AL11.AL_GAIN, 1F);
    }

    private void writeSync(short[] data) {
        int queuedBuffers = AL11.alGetSourcei(sourceID, AL11.AL_BUFFERS_QUEUED);
        if (queuedBuffers >= buffers.length) {
            int sampleOffset = AL11.alGetSourcei(sourceID, AL11.AL_SAMPLE_OFFSET);
            int buffersToSkip = queuedBuffers - 100;
            AL11.alSourcei(sourceID, AL11.AL_SAMPLE_OFFSET, sampleOffset + buffersToSkip * bufferSampleSize);
            removeProcessedBuffersSync();
        }

        AL11.alBufferData(buffers[bufferIndex], AL11.AL_FORMAT_MONO16, data, sampleRate);
        AL11.alSourceQueueBuffers(sourceID, buffers[bufferIndex]);
        bufferIndex = (bufferIndex + 1) % buffers.length;
    }

    public void setPosition(Optional<Vec3> soundPos) {
        executorService.execute(() -> {
            setPositionSync(soundPos);
        });
    }


    private void setPositionSync(Optional<Vec3> soundPos) {
        soundPos.ifPresentOrElse((pos) -> {
            AL11.alSourcei(sourceID, AL11.AL_SOURCE_RELATIVE, AL11.AL_FALSE);

            AL11.alSource3f(sourceID, AL11.AL_POSITION, (float) pos.x, (float) pos.y, (float) pos.z);


            //soundEffects.evaluateEnvironment(pos.x, pos.y, pos.z);
        }, () -> {

            AL11.alSourcei(sourceID, AL11.AL_SOURCE_RELATIVE, AL11.AL_TRUE);

            AL11.alSource3f(sourceID, AL11.AL_POSITION, (float) 0, (float) 0, (float) 0);

        });
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
}