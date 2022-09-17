package com.wynnvp.wynncraftvp.events;

import com.wynnvp.wynncraftvp.config.ConfigHandler;
import com.wynnvp.wynncraftvp.gui.ReportLineGui;
import com.wynnvp.wynncraftvp.sound.SoundsHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Timer;
import java.util.TimerTask;

@Mod.EventBusSubscriber
public class SendChatEvent {

    @SubscribeEvent
    public static void onSendChat(ClientChatEvent event) {
        switch (event.getMessage().toLowerCase()) {
            case "/toggle":
                SendClientChatMessage("§4To toggle Voices of Wynn speedrun mode type: §6/toggle speedrun. §4To toggle logging do §6/toggle logging");

                // \n+" +
                //                        "§4To toggle logging of missing lines: §6/togglelogging"
                //
                // \n++" +
                //                        "§4To set api key: §6/apikey <key>
                break;
            case "/toggle speedrun":
                ConfigHandler.SetPlayAllSoundsOnPlayer(!ConfigHandler.playAllSoundsOnPlayer);
                SendClientChatMessage("§bSet speedrun mode to §e" + ConfigHandler.playAllSoundsOnPlayer + "§b. This mode makes all sounds follow the player around");
                event.setCanceled(true);
                break;
            case "/toggle logging":
                Timer timer = new Timer();
                timer.schedule(new openGui(), 100);
                event.setCanceled(true);
                break;
            case "/load kingsrecruit":
                SoundsHandler.registerSound("kingsrecruit-caravandriver-2");
                SoundsHandler.registerSound("kingsrecruit-tasim-2");
                SoundsHandler.registerSound("kingsrecruit-caravandriver-3");
                SoundsHandler.registerSound("kingsrecruit-aledar-1");
                Minecraft.getMinecraft().getSoundHandler().update();
                break;
        }
    }

    private static void SendClientChatMessage(String message) {
        Minecraft.getMinecraft().player.sendMessage(new TextComponentString(message));
    }

    private static class openGui extends TimerTask {
        @Override
        public void run() {
            try {
                Minecraft.getMinecraft().displayGuiScreen(new ReportLineGui(false));

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


}
