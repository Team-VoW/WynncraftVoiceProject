package com.wynnvp.wynncraftvp.events;

import com.wynnvp.wynncraftvp.config.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class SendChatEvent {

    @SubscribeEvent
    public static void onRecieveChat(ClientChatEvent event) {
        switch (event.getMessage()) {
            case "/toggle":
                SendClientChatMessage("§4To toggle Voices of Wynn speedrun mode type: §6/speedrun");
                break;

            case "/speedrun":
                ConfigHandler.SetPlayAllSoundsOnPlayer(!ConfigHandler.playAllSoundsOnPlayer);
                SendClientChatMessage("§bSet speedrun mode to §e" + ConfigHandler.playAllSoundsOnPlayer + "§b. This mode makes all sounds follow the player around");
                break;
        }

    }

    private static void SendClientChatMessage(String message) {
        Minecraft.getMinecraft().player.sendMessage(new TextComponentString(message));
    }


}
