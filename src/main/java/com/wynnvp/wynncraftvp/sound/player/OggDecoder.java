package com.wynnvp.wynncraftvp.sound.player;

import com.jcraft.jogg.Packet;
import com.jcraft.jogg.Page;
import com.jcraft.jogg.StreamState;
import com.jcraft.jogg.SyncState;
import com.jcraft.jorbis.Block;
import com.jcraft.jorbis.Comment;
import com.jcraft.jorbis.DspState;
import com.jcraft.jorbis.Info;
import com.mojang.blaze3d.audio.SoundBuffer;
import com.wynnvp.wynncraftvp.utils.Utils;
import net.minecraft.client.sounds.JOrbisAudioStream;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionException;

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
    public static AudioData getCompleteBuffer(String filePath) {
            try (InputStream inputStream = new FileInputStream(filePath)) {
                AudioData audioData;
                SoundBuffer soundBuffer;
                ByteBuffer byteBuffer;
                try (JOrbisAudioStream finiteAudioStream = new JOrbisAudioStream(inputStream);){
                     byteBuffer = finiteAudioStream.readAll();
                    Utils.sendMessage("Format: Channels" + finiteAudioStream.getFormat().getChannels()
                            + " Sample Rate: " + finiteAudioStream.getFormat().getSampleRate()
                            + " Sample Size: " + finiteAudioStream.getFormat().getSampleSizeInBits());
                    soundBuffer = new SoundBuffer(byteBuffer, finiteAudioStream.getFormat());
                    audioData = new AudioData(byteBuffer, finiteAudioStream.getFormat());
                }
                return audioData;
            } catch (IOException iOException) {
                throw new CompletionException(iOException);
            }
    }

    public static short[] decodeOggFile(String filePath) throws IOException {
        try (InputStream inputStream = new FileInputStream(filePath)) {
            SyncState syncState = new SyncState();
            StreamState streamState = new StreamState();
            Page oggPage = new Page();
            Packet oggPacket = new Packet();
            Info vorbisInfo = new Info();
            Comment vorbisComment = new Comment();
            DspState dspState = new DspState();
            Block vorbisBlock = new Block(dspState);

            syncState.init();

            byte[] buffer;
            int bytes = 0;
            boolean endOfStream = false;

            List<Short> pcmList = new ArrayList<>();

            while (!endOfStream) {
                int index = syncState.buffer(BUFFER_SIZE);
                buffer = syncState.data;

                bytes = inputStream.read(buffer, index, BUFFER_SIZE);
                syncState.wrote(bytes);

                if (syncState.pageout(oggPage) != 1) {
                    if (bytes < BUFFER_SIZE) {
                        endOfStream = true;
                    }
                    continue;
                }

                streamState.init(oggPage.serialno());
                vorbisInfo.init();
                vorbisComment.init();

                if (streamState.pagein(oggPage) < 0) {
                    System.err.println("Error reading first page of Ogg bitstream.");
                    return null;
                }

                if (streamState.packetout(oggPacket) != 1) {
                    System.err.println("Error reading initial packet.");
                    return null;
                }

                if (vorbisInfo.synthesis_headerin(vorbisComment, oggPacket) < 0) {
                    System.err.println("This Ogg bitstream does not contain Vorbis audio data.");
                    return null;
                }

                for (int i = 0; i < 2; i++) {
                    while (streamState.packetout(oggPacket) != 1) {
                        if (syncState.pageout(oggPage) == 0) {
                            if (bytes < BUFFER_SIZE) {
                                endOfStream = true;
                                break;
                            }
                            continue;
                        }
                        streamState.pagein(oggPage);
                    }

                    vorbisInfo.synthesis_headerin(vorbisComment, oggPacket);
                }

                dspState.synthesis_init(vorbisInfo);

                while (!endOfStream) {
                    while (!endOfStream) {
                        int result = syncState.pageout(oggPage);
                        if (result == 0) {
                            break;
                        }

                        if (result == -1) {
                            System.err.println("Corrupt or missing data in bitstream.");
                            return null;
                        }

                        streamState.pagein(oggPage);

                        while (true) {
                            result = streamState.packetout(oggPacket);
                            if (result == 0) {
                                break;
                            }

                            if (result == -1) {
                                continue;
                            }

                            if (vorbisBlock.synthesis(oggPacket) == 0) {
                                dspState.synthesis_blockin(vorbisBlock);
                            }

                            float[][][] pcm = new float[1][][];
                            int[] pcmIndex = new int[vorbisInfo.channels];

                            while ((bytes = dspState.synthesis_pcmout(pcm, pcmIndex)) > 0) {
                                for (int i = 0; i < vorbisInfo.channels; i++) {
                                    for (int j = 0; j < bytes; j++) {
                                        short value = (short) Math.max(Math.min(pcm[0][i][j] * 32767.0f, 32767.0f), -32768.0f);
                                        pcmList.add(value);
                                    }
                                }

                                dspState.synthesis_read(bytes);
                            }
                        }

                        if (oggPage.eos() != 0) {
                            endOfStream = true;
                        }
                    }

                    if (!endOfStream) {
                        int index2 = syncState.buffer(BUFFER_SIZE);
                        buffer = syncState.data;

                        bytes = inputStream.read(buffer, index2, BUFFER_SIZE);
                        syncState.wrote(bytes);

                        if (bytes == 0) {
                            endOfStream = true;
                        }
                    }
                }

                streamState.clear();
                vorbisBlock.clear();
                dspState.clear();
                vorbisInfo.clear();
            }

            syncState.clear();

            // Convert List<Short> to short[]
            short[] pcmArray = new short[pcmList.size()];
            for (int i = 0; i < pcmList.size(); i++) {
                pcmArray[i] = pcmList.get(i);
            }

            return pcmArray;
        }
    }

    public static void main(String[] args) {
        try {
            String oggFilePath = "assets/wynnvp/sounds/acquiringcredentials-barman-1.ogg";
            short[] pcmData = decodeOggFile(oggFilePath);

            // Example: print the first 10 decoded samples
            for (int i = 0; i < 10 && i < pcmData.length; i++) {
                System.out.println(pcmData[i]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
