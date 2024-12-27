/*
 * Copyright © Team-VoW 2024.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.player;

import com.wynnvp.wynncraftvp.utils.Utils;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.CompletionException;
import net.minecraft.client.sounds.JOrbisAudioStream;

public class OggDecoder {
    private static final int BUFFER_SIZE = 4096;

    public static short[] byteBufferToShortArray(ByteBuffer byteBuffer) {
        // Ensure the ByteBuffer uses little-endian order (or whatever matches your audio data)
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);

        // Create a short array to hold the converted data
        short[] shortArray = new short[byteBuffer.remaining() / 2]; // 2 bytes per short

        // Convert the byte buffer to a short array
        byteBuffer.asShortBuffer().get(shortArray);

        return shortArray;
    }

    public static Optional<AudioData> getAudioData(Path filePath) {
        if (filePath == null || !filePath.toFile().exists()) {
            System.err.println("File not found: " + filePath);
            return Optional.empty();
        }

        try (InputStream inputStream = new FileInputStream(filePath.toFile());
                JOrbisAudioStream finiteAudioStream = new JOrbisAudioStream(inputStream)) {
            // Process the audio data
            ByteBuffer byteBuffer = finiteAudioStream.readAll();
            AudioData audioData = new AudioData(byteBuffer, finiteAudioStream.getFormat());

            return Optional.of(audioData);

        } catch (IOException e) {
            System.err.println("Error processing audio file: " + filePath);
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public static AudioData getAudioData(String filePath) {
        try (InputStream inputStream = new FileInputStream(filePath)) {
            AudioData audioData;
            ByteBuffer byteBuffer;
            try (JOrbisAudioStream finiteAudioStream = new JOrbisAudioStream(inputStream); ) {
                byteBuffer = finiteAudioStream.readAll();
                Utils.sendMessage("Format: Channels"
                        + finiteAudioStream.getFormat().getChannels()
                        + " Sample Rate: " + finiteAudioStream.getFormat().getSampleRate()
                        + " Sample Size: " + finiteAudioStream.getFormat().getSampleSizeInBits());
                audioData = new AudioData(byteBuffer, finiteAudioStream.getFormat());
            }
            return audioData;
        } catch (IOException iOException) {
            throw new CompletionException(iOException);
        }
    }
}
