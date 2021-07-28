package com.wynnvp.wynncraftvp.events;


import com.wynnvp.wynncraftvp.ModCore;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class SendChatMessageEvent {

    @SubscribeEvent
    public static void sendChat(ClientChatEvent event){
        String commandText = event.getMessage();
        if (!commandText.equalsIgnoreCase("/class")) {
            return;
        }
        Minecraft.getMinecraft().getSoundHandler().stopSounds();
        ModCore.instance.soundPlayer.clearCoolDown();
    }
}
