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
    public static void onSendChat(ClientChatEvent event) {
        switch (event.getMessage()) {
            case "/toggle":
                SendClientChatMessage("§4To toggle Voices of Wynn speedrun mode type: §6/speedrun");

                // \n+" +
                //                        "§4To toggle logging of missing lines: §6/togglelogging"
                //
                // \n++" +
                //                        "§4To set api key: §6/apikey <key>
                return;

            case "/speedrun":
                ConfigHandler.SetPlayAllSoundsOnPlayer(!ConfigHandler.playAllSoundsOnPlayer);
                SendClientChatMessage("§bSet speedrun mode to §e" + ConfigHandler.playAllSoundsOnPlayer + "§b. This mode makes all sounds follow the player around");
                return;
            case "/togglelogging":
                ConfigHandler.setLogMissingLines(!ConfigHandler.logMissingLines);
                SendClientChatMessage("§bSet log misslines lines mode to §e" + ConfigHandler.logMissingLines);
                return;
        }
        String[] split = event.getMessage().split(" ");
        if (split[0].equalsIgnoreCase("/apikey") && split.length > 1){
            SendClientChatMessage("§bSet api key to §e" + split[1]);
            ConfigHandler.setApiKey(split[1]);
        }


    }

    private static void SendClientChatMessage(String message) {
        Minecraft.getMinecraft().player.sendMessage(new TextComponentString(message));
    }


}
