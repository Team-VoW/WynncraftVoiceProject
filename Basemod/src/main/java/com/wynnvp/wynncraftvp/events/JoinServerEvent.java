package com.wynnvp.wynncraftvp.events;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.config.ConfigHandler;
import com.wynnvp.wynncraftvp.packet.PacketIncomingFilter;
import com.wynnvp.wynncraftvp.utils.UserCount;
import com.wynnvp.wynncraftvp.utils.Utils;
import com.wynnvp.wynncraftvp.utils.VersionChecker;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import java.text.DecimalFormat;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

@Mod.EventBusSubscriber
public class JoinServerEvent {

    public static boolean isOnNewestVersion = false;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    @SubscribeEvent
    public static void onServerJoin(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        //Inject filter of packets
        event.getManager().channel().pipeline().addBefore("fml:packet_handler", ModCore.MODID + ":packet_filter", new PacketIncomingFilter());
        String serverIP = Objects.requireNonNull(Minecraft.getMinecraft().getCurrentServerData()).serverIP.toLowerCase();
        if (serverIP.startsWith("play.wynncraft")
                || serverIP.startsWith("media.wynncraft")
                || serverIP.startsWith("beta.wynncraft")
                || serverIP.startsWith("lobby.wynncraft")){
            System.out.println("Joined Live Wynncraft server");
            ModCore.inLiveWynnServer = true;
        }
        ModCore.inServer = true;

        Timer timer = new Timer();
        //In 10 seconds
        timer.schedule(new SchedulerTask(), 10000L);
    }

    public static class SchedulerTask extends TimerTask {
        @Override
        public void run() {
            try {
                UserCount.addPlayer();
                String version = ModCore.instance.getClass().getPackage().getImplementationVersion();

                float[] versions = VersionChecker.getVersionCheck();

                float versionInFloat = Float.parseFloat(version);
                //Is using newest version
                if (versions[0] == versionInFloat) {
                    isOnNewestVersion = true;
                    return;
                }

                if (versions[1] < versionInFloat) {
                    isOnNewestVersion = true;
                    return;
                }

                ConfigHandler.logMissingLines = false;
                //The version is smaller or the same as the version to recommend update
                String message = "§8A new version of §5Voices of Wynn§8 is available! You are using version: §4" + df.format(versionInFloat) + " §8and the newest version is: §2" + df.format(versions[0]);
                Minecraft.getMinecraft().player.sendMessage(new TextComponentString(message));


            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
