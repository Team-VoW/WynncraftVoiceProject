/*
 * Copyright © Team-VoW 2024.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.events;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.utils.Utils;
import com.wynnvp.wynncraftvp.utils.VersionChecker;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

public class JoinServerEvent {
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public static void run(String ip) {
        ModCore.inLiveWynnServer = true;

        Timer timer = new Timer();
        // In 8 seconds
        timer.schedule(new SchedulerTask(), 8000L);
    }

    public static class SchedulerTask extends TimerTask {
        @Override
        public void run() {
            VersionChecker.checkVersion();
            if (!ModCore.config.getHasShownMissingLineNotification()) {
                Utils.sendMessage(
                        "VOW sends non voiced dialogue lines anonymously to our server so the mod can be improved. This can be disabled in the Mod menu settings.");
                ModCore.config.setHasShownMissingLineNotification(true);
            }
        }
    }
}
