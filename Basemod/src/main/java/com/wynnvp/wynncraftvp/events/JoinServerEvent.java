package com.wynnvp.wynncraftvp.events;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.packet.PacketIncomingFilter;
import com.wynnvp.wynncraftvp.utils.VersionChecker;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import java.text.DecimalFormat;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

@Mod.EventBusSubscriber
public class JoinServerEvent {


    private static final DecimalFormat df = new DecimalFormat("0.00");

    @SubscribeEvent
    public static void onServerJoin(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        //Inject filter of packets
        event.getManager().channel().pipeline().addBefore("fml:packet_handler", ModCore.MODID + ":packet_filter", new PacketIncomingFilter());
        String serverIP = Objects.requireNonNull(Minecraft.getMinecraft().getCurrentServerData()).serverIP.toLowerCase();
        if (serverIP.startsWith("play.wynncraft")
                || serverIP.startsWith("media.wynncraft")
                //     || serverIP.startsWith("beta.wynncraft")
                || serverIP.startsWith("lobby.wynncraft")) {
            System.out.println("Joined Live Wynncraft server");
            ModCore.inLiveWynnServer = true;
        }
        ModCore.inServer = true;

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
