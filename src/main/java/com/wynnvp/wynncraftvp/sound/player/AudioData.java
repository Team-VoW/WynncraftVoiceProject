package com.wynnvp.wynncraftvp.sound.player;

import javax.sound.sampled.AudioFormat;
import java.nio.ByteBuffer;

public class AudioData {

    public ByteBuffer byteBuffer;
    public AudioFormat audioFormat;

    public AudioData(ByteBuffer byteBuffer, AudioFormat audioFormat) {
        this.byteBuffer = byteBuffer;
        this.audioFormat = audioFormat;
    }
}
