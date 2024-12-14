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
import java.util.Optional;
import java.util.concurrent.CompletionException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.sounds.JOrbisAudioStream;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;

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

    public static Optional<AudioData> getAudioData(ResourceLocation resourceLocation) {
        // Retrieve the resource manager instance
        ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();

        try {
            // Get the input stream for the resource
            Optional<Resource> resource = resourceManager.getResource(resourceLocation);

            if (resource.isEmpty()) {
                return Optional.empty();
            }

            InputStream inputStream = resource.get().open();

            // Process the audio data
            AudioData audioData;
            ByteBuffer byteBuffer;

            // Assuming you're working with a JOrbis audio stream for Ogg files
            try (JOrbisAudioStream finiteAudioStream = new JOrbisAudioStream(inputStream); ) {
                byteBuffer = finiteAudioStream.readAll();
                audioData = new AudioData(byteBuffer, finiteAudioStream.getFormat());
            }

            // Handle your audio data here or return it if necessary
            return Optional.of(audioData);
        } catch (IOException e) {
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
