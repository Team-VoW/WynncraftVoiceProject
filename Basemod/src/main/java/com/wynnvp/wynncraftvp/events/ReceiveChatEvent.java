package com.wynnvp.wynncraftvp.events;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.sound.SoundsHandler;
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
        if (msg.contains(Minecraft.getMinecraft().player.getName())) {
            msg = msg.replace(Minecraft.getMinecraft().player.getName(), "soldier");
        }
        msg = SoundsHandler.formatToSound(msg);
        if (isInMixedFeelingsQuest()) {
            System.out.println("Is in mixed feelings quest");
            String result = getMixedFeelingsLine(msg);
            if (result != null) {
                System.out.println("Overriding input message");
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
            System.out.println("Talking to npc 1");
        } else if (player.getDistance(mixedFeelingsNPC2.x, mixedFeelingsNPC2.y, mixedFeelingsNPC2.z) < 15) {
            msg = GetRightMixedFeelingsLine("mixedfeelingscorkuscitycitizen2", msg);
            System.out.println("Talking to npc 2");
        } else if (player.getDistance(mixedFeelingsNPC3.x, mixedFeelingsNPC3.y, mixedFeelingsNPC3.z) < 15) {
            msg = GetRightMixedFeelingsLine("mixedfeelingscorkuscitycitizen3", msg);
            System.out.println("Talking to npc 3");
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


        System.out.println("Found Mixed feelings message. Returning: " + fileName);
        return fileName;


    }

}
