package com.wynnvp.wynncraftvp.events;

import com.wynnvp.wynncraftvp.ModCore;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ReceiveChatEvent {

    @SubscribeEvent
    public static void receivedChat(ClientChatReceivedEvent event) {
        String msg = event.getMessage().getUnformattedText();
        if (msg.contains(Minecraft.getMinecraft().player.getName())) {
            msg = msg.replace(Minecraft.getMinecraft().player.getName(), "soldier");
        }
        // if (!Utils.inWynn()) {
        //   return;
        //  }
        // if (!meetsFormat(msg)) {
        //   System.out.println("DID NOT MEET FORMAT: \n " + event.getMessage().getUnformattedText());
        //    return;
        //  }
        ModCore.instance.soundPlayer.playSound(msg);
    }

    private static boolean meetsFormat(String string) {
        //format: [x/b] <NPCName>: <text>
        if (!(string.charAt(0) == '[')) {
            return false;
        }
        return string.charAt(1) == '/' || string.charAt(2) == '/';
    }

}
