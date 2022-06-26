package com.wynnvp.wynncraftvp.sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

import static javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED;
import static javax.sound.sampled.AudioSystem.getAudioInputStream;

public class SoundController {

    /*public static void main(String[] args) {
        new SoundController().playAtPlayer(new File("C:/Users/ender/AppData/Roaming/.minecraft/wynnvp/kingsrecruit/kingsrecruit-caravandriver-2.ogg"));
    }

    public void playAtPlayer(File file) {
        final VorbisAudioFileReader vb = new VorbisAudioFileReader();
        try (final AudioInputStream in = vb.getAudioInputStream(file)) {
            final AudioFormat outFormat = getOutFormat(in.getFormat());
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, outFormat);
            try (final SourceDataLine line =
                         (SourceDataLine) AudioSystem.getLine(info)) {
                if (line != null) {
                    line.open(outFormat);
                    line.start();
                    stream(getAudioInputStream(outFormat, in), line);
                    line.drain();
                    line.stop();
                }
            }
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    private AudioFormat getOutFormat(AudioFormat inFormat) {
        final int ch = inFormat.getChannels();
        final float rate = inFormat.getSampleRate();
        return new AudioFormat(PCM_SIGNED, rate, 16, ch, ch * 2, rate, false);
    }

    private void stream(AudioInputStream in, SourceDataLine line)
            throws IOException {
        final byte[] buffer = new byte[65536];
        for (int n = 0; n != -1; n = in.read(buffer, 0, buffer.length)) {
            line.write(buffer, 0, n);
        }
    }*/

}
