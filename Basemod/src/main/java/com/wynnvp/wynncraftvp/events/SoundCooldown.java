package com.wynnvp.wynncraftvp.events;


import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.HashMap;
/*
@Mod.EventBusSubscriber
public class SoundCooldown {

    public static int timeInSeconds = 0;
    public static int ticks = 1;
    public static HashMap<String, Integer> soundsOnCoolDowN = new HashMap<>();

    @SubscribeEvent
    public static void onTick(TickEvent.ClientTickEvent event) {
        if (ticks % 40 == 0) {
            timeInSeconds++;
            ticks = 0;
            checkForRemove();
        }
        ticks++;
    }

    private static void checkForRemove(){
        //Checks each value in the HashMap
        if (soundsOnCoolDowN.isEmpty()) {
            return;
        }
        for (String key : soundsOnCoolDowN.keySet()) {
            int number = soundsOnCoolDowN.get(key);

            //If it has been in the HashMap for 30sec remove it
            number = (number + 40);
            if (number <= timeInSeconds) {
                removeFromCoolDown(key);
            }
        }
    }


    public static void addSoundToCoolDown(String soundName){
        soundsOnCoolDowN.clear();
        soundsOnCoolDowN.put(soundName, timeInSeconds);
    }

    public static boolean isOnCoolDown(String soundName){
        return soundsOnCoolDowN.containsKey(soundName);
    }

    private static void removeFromCoolDown(String soundName){
        soundsOnCoolDowN.remove(soundName);
    }

}

 */
