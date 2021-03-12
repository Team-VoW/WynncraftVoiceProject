package com.wynnvp.wynncraftvp.events;

import com.wynnvp.wynncraftvp.sound.SoundPlayer;
import com.wynnvp.wynncraftvp.utils.Utils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ReceiveChatEvent {

    @SubscribeEvent
    public static void eventHandler(ClientChatReceivedEvent event) {
        String msg = event.getMessage().getUnformattedText();
        if (msg.contains("test sound")) {
            SoundPlayer.playSound("test");
            return;
        }
        if (!Utils.inWynn()) {
            return;
        }
        if (!meetsFormat(msg)) {
            return;
        }
        SoundPlayer.playSound(msg);
    }

    private static boolean meetsFormat(String string) {
        //format: [x/b] <NPCName>: <text>
        if (!(string.charAt(0) == '[')) {
            return false;
        }
        if (!(string.charAt(1) == '/' || string.charAt(2) == '/')) {
            return false;
        }
        return true;
    }
}
