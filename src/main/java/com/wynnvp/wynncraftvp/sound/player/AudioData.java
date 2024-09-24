/*
 * Copyright © Team-VoW 2024.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.player;

import java.nio.ByteBuffer;
import javax.sound.sampled.AudioFormat;

public class AudioData {
    public ByteBuffer byteBuffer;
    public AudioFormat audioFormat;

    public AudioData(ByteBuffer byteBuffer, AudioFormat audioFormat) {
        this.byteBuffer = byteBuffer;
        this.audioFormat = audioFormat;
    }
}
