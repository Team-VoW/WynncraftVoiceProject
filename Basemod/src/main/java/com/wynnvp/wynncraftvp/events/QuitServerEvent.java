package com.wynnvp.wynncraftvp.events;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.config.ConfigHandler;
import com.wynnvp.wynncraftvp.npc.NPCHandler;
import com.wynnvp.wynncraftvp.npc.QuestMarkHandler;
import com.wynnvp.wynncraftvp.packet.PacketIncomingFilter;
import com.wynnvp.wynncraftvp.sound.SoundPlayer;
import com.wynnvp.wynncraftvp.utils.VersionChecker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

public class QuitServerEvent {

    @SubscribeEvent
    public static void onServerQuit(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        ModCore.inServer = false;
        //SoundPlayer.SPEAKING = false;
        NPCHandler.getNamesHandlers().clear();
        QuestMarkHandler.getWichQuest().clear();
    }

    @SubscribeEvent
    public void onServerQuit(GuiOpenEvent event) {
        if (ModCore.inServer && event.getGui() instanceof GuiDisconnected) {
            ModCore.inServer = false;
            //SoundPlayer.SPEAKING = false;
            QuestMarkHandler.getWichQuest().clear();
            NPCHandler.getNamesHandlers().clear();
        }
    }

}
