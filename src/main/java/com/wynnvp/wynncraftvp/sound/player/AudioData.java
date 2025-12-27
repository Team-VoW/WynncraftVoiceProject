/*
 * Copyright © Team-VoW 2024-2025.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.player;

import com.wynnvp.wynncraftvp.sound.Reverb;
import java.nio.ByteBuffer;
import java.util.Optional;
import javax.sound.sampled.AudioFormat;
import net.minecraft.world.phys.Vec3;

public class AudioData {
    public ByteBuffer byteBuffer;
    public AudioFormat audioFormat;
    public String speakerName;
    public Optional<Vec3> pos;
    public Reverb reverb;

    public AudioData(ByteBuffer byteBuffer, AudioFormat audioFormat) {
        this.byteBuffer = byteBuffer;
        this.audioFormat = audioFormat;
    }

    public void setSpeakerAndPos(String speakerName, Optional<Vec3> pos) {
        this.speakerName = speakerName;
        this.pos = pos;
    }

    /**
     * Gets the audio length in seconds
     * @return audio length
     */
    public double getAudioLength() {
        int totalFrames = byteBuffer.remaining() / audioFormat.getFrameSize();
        return totalFrames / audioFormat.getFrameRate();
    }

    public long getAudioLengthMillis() {
        return (long) (getAudioLength() * 1000);
    }
}
