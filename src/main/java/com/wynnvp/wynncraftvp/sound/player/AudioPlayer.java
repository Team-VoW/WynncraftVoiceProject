package com.wynnvp.wynncraftvp.sound.player;

import com.mojang.blaze3d.audio.SoundBuffer;
import com.wynnvp.wynncraftvp.sound.line.LineData;


import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AudioPlayer {

    //private final OpusDecoder decoder;

    private int lastPlayedSoundLength;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    public final OpenAlPlayer openAlPlayer;

    private LineData lastSentLineData;

    public final AutoProgress autoProgress;


    public void onNpcDialogue(LineData lineData) {
        lastSentLineData = lineData;
    }


    public AudioPlayer() {
        openAlPlayer = new OpenAlPlayer();

/*        // Create a new Opus decoder instance with the same parameters as the encoder
        try {
            decoder = new OpusDecoder(48000, 1);
        } catch (IOException | UnknownPlatformException e) {
            throw new RuntimeException(e);
        }
        decoder.setFrameSize(960);*/
        autoProgress = new AutoProgress();
    }

    private void write(AudioData data) {
        openAlPlayer.playAudio(data);
    }




    public void play(String fileName) {
        openAlPlayer.stopAudio();
        executorService.execute(() -> {

            /*    SoundBuffer soundBuffer = ;
                soundBuffer.*/

            //short[] decoded = OggDecoder.decodeOggFile(fileName);
            write(OggDecoder.getCompleteBuffer(fileName));
        });

    }

/*    public void play(AudioPacket audioPacket) {

        int toPlayLength = audioPacket.getTotalAudioLength();


        if (toPlayLength != lastPlayedSoundLength) {
            handleNewAudioStarted(audioPacket);
        }

        lastPlayedSoundLength = toPlayLength;

        //We run the playing on a different thread as the playing of sound slows down the thread it is running on
        executorService.execute(() -> {

            short[] decoded = decoder.decode(audioPacket.getAudioData());

            write(decoded);
        });
    }

    private void handleNewAudioStarted(AudioPacket audioPacket) {

        stopPlayingCurrentSound();
        openAlPlayer.updateSpeaker(audioPacket.isMovingSound() ? "" : lastSentLineData.getNPCName(), audioPacket.getPosition());

        //This totalAudioLength is the length in short[] which means we do not have to divide it by the bit depth (16 / 8 = 2),
        //As the audio is half as long as raw PCM audio.
        long seconds = (long) (audioPacket.getTotalAudioLength() / (48000f));
        if (VowCloud.CONFIG.autoProgress.get())
            autoProgress.autoProgress((long) (seconds * 1000 + VowCloud.CONFIG.autoProgressDelay.get() * 1000));
    }*/

    public void stopPlayingCurrentSound() {
        openAlPlayer.stopAudio();
    }


}
