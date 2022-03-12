package com.wynnvp.wynncraftvp.events;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.sound.SoundsHandler;
import com.wynnvp.wynncraftvp.utils.LineFormatter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.vecmath.Vector3f;

@Mod.EventBusSubscriber
public class ReceiveChatEvent {

    private static final Vector3f mixedFeelingsNPC1 = new Vector3f(-5881, 17, -2464);
    private static final Vector3f mixedFeelingsNPC2 = new Vector3f(-5835, 16, -2463);
    private static final Vector3f mixedFeelingsNPC3 = new Vector3f(-5807, 16, -2421);


    @SubscribeEvent
    public static void receivedChat(ClientChatReceivedEvent event) {
        String msg = event.getMessage().getUnformattedText();

        msg = msg.replace("\n", "iso95bf");

        //Replace player Name with "soldier"
        String name = GetPlayerName(event.getMessage().toString());
        if (msg.contains(name)) {
            msg = msg.replace(name, "soldier");
            System.out.println("Replaced player name!");
        }


        msg = LineFormatter.formatToSound(msg);
        if (isInMixedFeelingsQuest()) {
            String result = getMixedFeelingsLine(msg);
            if (result != null) {
                msg = result;
            }
        }
        ModCore.instance.soundPlayer.playSound(msg);
    }


    private static boolean isInMixedFeelingsQuest() {
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        return player.getDistance(mixedFeelingsNPC1.x, mixedFeelingsNPC1.y, mixedFeelingsNPC1.z) < 250;

    }

    private static String getMixedFeelingsLine(String msg) {
        EntityPlayerSP player = Minecraft.getMinecraft().player;

        if (player.getDistance(mixedFeelingsNPC1.x, mixedFeelingsNPC1.y, mixedFeelingsNPC1.z) < 15) {
            msg = GetRightMixedFeelingsLine("mixedfeelingscorkuscitycitizen1", msg);
        } else if (player.getDistance(mixedFeelingsNPC2.x, mixedFeelingsNPC2.y, mixedFeelingsNPC2.z) < 15) {
            msg = GetRightMixedFeelingsLine("mixedfeelingscorkuscitycitizen2", msg);
        } else if (player.getDistance(mixedFeelingsNPC3.x, mixedFeelingsNPC3.y, mixedFeelingsNPC3.z) < 15) {
            msg = GetRightMixedFeelingsLine("mixedfeelingscorkuscitycitizen3", msg);
        }

        return msg;
    }

    private static String GetRightMixedFeelingsLine(String fileName, String msg) {
        Boolean foundMsg = false;

        if (msg.equalsIgnoreCase("2/5corkuscitycitizencorkushasbeenurgingtouristsandenvoysfromtheotherprovincestogainrecognition")) {
            fileName = fileName + "1";
            foundMsg = true;
        } else if (msg.equalsIgnoreCase("2/5corkuscitycitizenyouknowaboutthepatriotsofcorkus?")) {
            fileName = fileName + "2";
            foundMsg = true;
        } else if (msg.equalsIgnoreCase("2/6corkuscitycitizenhmmasithappensihaveseensomestrangethingsaroundhere")) {
            fileName = fileName + "3";
            foundMsg = true;
        }
        if (!foundMsg) return null;


        return fileName;
    }


    private static String GetPlayerName(String message) {
        String segments[] = message.split("hoverEvent=HoverEvent\\{action=SHOW_TEXT, value='TextComponent\\{text='");
        if (segments.length <= 1) return Minecraft.getMinecraft().player.getDisplayNameString();
        String name = segments[segments.length - 1].split(" ")[0];
        if (name.contains("Previous")) return Minecraft.getMinecraft().player.getDisplayNameString();
        return name.split("'")[0];
    }
}


