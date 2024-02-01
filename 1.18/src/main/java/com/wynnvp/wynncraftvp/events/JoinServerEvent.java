package com.wynnvp.wynncraftvp.events;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.utils.VersionChecker;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;


public class JoinServerEvent {


    private static final DecimalFormat df = new DecimalFormat("0.00");


    public static void run(String ip) {

        ModCore.inLiveWynnServer = true;


        Timer timer = new Timer();
        //In 8 seconds
        timer.schedule(new SchedulerTask(), 8000L);
    }

    public static class SchedulerTask extends TimerTask {
        @Override
        public void run() {
            VersionChecker.checkVersion();
        }
    }
}
