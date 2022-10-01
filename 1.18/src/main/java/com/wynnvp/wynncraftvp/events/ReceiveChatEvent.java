package com.wynnvp.wynncraftvp.events;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.sound.line.LineData;
import com.wynnvp.wynncraftvp.utils.LineFormatter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

public class ReceiveChatEvent {

    private static final Vec3d mixedFeelingsNPC1 = new Vec3d(-5881, 17, -2464);
    private static final Vec3d mixedFeelingsNPC2 = new Vec3d(-5835, 16, -2463);
    private static final Vec3d mixedFeelingsNPC3 = new Vec3d(-5807, 16, -2421);

    public static boolean stopMod = false;

    private static String last = null;
    public static void receivedChat(String msg) {
        if (stopMod) return;

        //Replace player Name with "soldier"
        String name = GetPlayerName(msg);

        if (msg.contains(name)) {
            msg = msg.replace(name, "soldier");
            //System.out.println("Replaced player name!");
        }


        LineData lineData = LineFormatter.formatToLineData(msg);

        if (lineData == null) { // invalid line data returned
            last = null;
            return;
        }

        if (lineData.getSoundLine().equals(last)) {
            return;
        }
        last = lineData.getSoundLine();

        if (isInMixedFeelingsQuest()) {
            String result = getMixedFeelingsLine(lineData.getSoundLine());
            if (result != null) {
                lineData.setSoundLine(result);
            }
        }

        ModCore.instance.soundPlayer.playSound(lineData);
    }


    private static boolean isInMixedFeelingsQuest() {
        PlayerEntity player = MinecraftClient.getInstance().player;
        return player.getPos().distanceTo(mixedFeelingsNPC1) < 250;

    }

    private static String getMixedFeelingsLine(String msg) {
        PlayerEntity player = MinecraftClient.getInstance().player;

        if (player.getPos().distanceTo(mixedFeelingsNPC1) < 15) {
            msg = GetRightMixedFeelingsLine("mixedfeelingscorkuscitycitizen1", msg);
        } else if (player.getPos().distanceTo(mixedFeelingsNPC2) < 15) {
            msg = GetRightMixedFeelingsLine("mixedfeelingscorkuscitycitizen2", msg);
        } else if (player.getPos().distanceTo(mixedFeelingsNPC3) < 15) {
            msg = GetRightMixedFeelingsLine("mixedfeelingscorkuscitycitizen3", msg);
        }

        return msg;
    }

    private static String GetRightMixedFeelingsLine(String fileName, String msg) {
        boolean foundMsg = false;

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


    private static String GetPlayerName(String eventMessageToString) {
        return MinecraftClient.getInstance().player.getEntityName();
    }
}


