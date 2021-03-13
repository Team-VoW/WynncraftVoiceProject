package com.wynnvp.wynncraftvp.events;

import com.wynnvp.wynncraftvp.sound.SoundPlayer;
import com.wynnvp.wynncraftvp.utils.Utils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ReceiveChatEvent {

    @SubscribeEvent
    public static void receivedChat(ClientChatReceivedEvent event) {
        String msg = event.getMessage().getUnformattedText();
        if (msg.contains("test sound")) {
            SoundPlayer.playSound("[1/2] Talking Mushroom: OH MY, LOOK WHO's BACK. HERE I THOUGHT I WAS FREE OF YOUR GRASP AND THEN YOU COME BACK HERE.");
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
        return string.charAt(1) == '/' || string.charAt(2) == '/';
    }
}
