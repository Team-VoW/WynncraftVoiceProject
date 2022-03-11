package com.wynnvp.wynncraftvp.sound;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.config.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SoundPlayer {
    private int delay = 60;
    private final String apiKey = "testing"; //NOTE: this must be changed before compiling
    private ArrayList<String> latestSoundPlayed;

    public SoundPlayer() {
        latestSoundPlayed = new ArrayList<>();
    }

    //Code that is run to play all the sounds
    public void playSound(String line) {
        ModCore modCore = ModCore.instance;
        SoundsHandler soundsHandler = modCore.soundsHandler;
        if (!soundsHandler.sounds.containsKey(line)) {
            System.out.println("Does not contain line: " + line);
            if (reportUnvoicedLine(line)) {
                System.out.println("Anonymous unvoiced line report has been sent to our servers. Don't worry, this doesn't contain any sensitive information, only the line that didn't have any audio file associated and your coordinates in Wynncraft.");
            }
            else {
                System.out.println("An report of unvoiced line couldn't be sent. Error code should be right above this message.");
            }
            return;
        }
        if (isOnCoolDown(line)) {
            System.out.println("Sound: " + line + " is on cooldown.");
            return;
        }

        System.out.println("Playing sound: " + line);
        Minecraft.getMinecraft().getSoundHandler().stopSounds();
        CustomSoundClass customSoundClass = soundsHandler.sounds.get(line);
        SoundEvent soundEvent = customSoundClass.getSoundEvent();

        //If this is a moving sound or it is set to play all sounds on player
        if (customSoundClass.isMovingSound() || ConfigHandler.playAllSoundsOnPlayer) {
            //Play the sound at the player
            Minecraft.getMinecraft().getSoundHandler().playSound(new SoundAtPlayer(soundEvent));
            addSoundToCoolDown(line);
            return;
        }
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        playSoundAtCoords(player.getPosition(), soundEvent);
        addSoundToCoolDown(line);
    }


    private void playSoundAtCoords(BlockPos blockPos, SoundEvent soundEvent) {
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        player.getEntityWorld().playSound(blockPos.getX(), blockPos.getY(), blockPos.getZ(), soundEvent, SoundCategory.VOICE, 1, 1, false);
    }

    private void addSoundToCoolDown(String soundName) {
        if (latestSoundPlayed.size() > 100) {
            latestSoundPlayed.remove(0);
        }
        latestSoundPlayed.add(soundName);
    }

    private boolean isOnCoolDown(String soundName) {
        return latestSoundPlayed.contains(soundName);
    }

    public void clearCoolDown() {
        latestSoundPlayed.clear();
    }

    private boolean reportUnvoicedLine(String chatMessage) throws IOException {
        //TODO Find a way to fill these variables in an elegant way.
        //I didn't dig through the code that much, but you could probably make a class that would take care of all the formating
        //Something like ChatMessageFormator, which would have a method into which you'd pass the whole chat message, that should trigger a dialogue
        //And after that, you could use methods like extractPlayerName(), extractNpcName(), extractDialogue() etc.

        String npcName = "";
        String dialogue = "";
        String fullLine = chatMessage;
        int CoordX = 0;
        int CoordY = 0;
        int CoordZ = 0;

        URL urlObj = new URL("https://voicesofwynn.com/api/unvoiced-line-report/new);
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "VoicesOfWynnModClient");
        connection.setDoOutput(true);
        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        String urlPostParameters = "npc=" + npcName + "&dialogue=" + dialogue + "&full=" + fullLine + "&x=" + CoordX + "&y=" + CoordY + "&z=" + CoordZ + "&apiKey=" + apiKey; 
        outputStream.writeBytes(urlPostParameters); 
        outputStream.flush();
        outputStream.close();
        Integer responseCode = connection.getResponseCode();
        if (!(responseCode >= 200 && responseCode < 300)) {
            System.out.println("HTTP response Code : " + responseCode);
            return false;
	}
        System.out.println("HTTP response Code : " + responseCode);
        return true;
    }
}
