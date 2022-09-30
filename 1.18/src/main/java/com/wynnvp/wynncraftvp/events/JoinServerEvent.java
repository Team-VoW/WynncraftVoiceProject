package com.wynnvp.wynncraftvp.events;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.utils.VersionChecker;
import net.fabricmc.fabric.api.networking.v1.ServerLoginConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerLoginNetworkHandler;
import net.minecraft.util.Identifier;


import java.text.DecimalFormat;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import static org.lwjgl.glfw.GLFW.Functions.Init;


public class JoinServerEvent {


    private static final DecimalFormat df = new DecimalFormat("0.00");


    public static void run(String ip) {

        String serverIP = ip.toLowerCase();
        if (serverIP.startsWith("play.wynncraft")
                || serverIP.startsWith("media.wynncraft")
                || serverIP.startsWith("beta.wynncraft")
                || serverIP.startsWith("lobby.wynncraft")) {
            System.out.println("Joined Live Wynncraft server");
            ModCore.inLiveWynnServer = true;
        } else {
            ModCore.inLiveWynnServer = false;
        }

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
