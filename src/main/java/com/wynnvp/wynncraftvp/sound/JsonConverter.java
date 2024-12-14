/*
 * Copyright © Team-VoW 2024.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound;

import static com.wynnvp.wynncraftvp.utils.LineFormatter.formatToLineData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wynnvp.wynncraftvp.sound.line.LineData;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.phys.Vec3;

public class JsonConverter {
    private final List<DialogueData> DialogueDataList;

    public static void main(String[] args) {
        JsonConverter yamlConverter = new JsonConverter();
        yamlConverter.saveToJson("sounds.json");
    }

    public JsonConverter() {
        DialogueDataList = new ArrayList<>();
        // Sounds.register(this);
    }

    public void addSound(String message, String id, boolean movingSound) {
        addSound(message, id, movingSound, null);
    }

    public void addSound(String message, String id, boolean movingSound, Vec3 position) {
        addSound(message, id, movingSound, position, 0);
    }

    public void addSound(String message, String id, boolean movingSound, int fallOff) {
        addSound(message, id, movingSound, null, fallOff);
    }

    public void addSound(String message, String id, boolean movingSound, Vec3 position, int fallOff) {
        LineData lineData = formatToLineData(message);
        String npcName = lineData.getNPCName();
        DialogueData DialogueData = new DialogueData();
        DialogueData.setDialogueLine(message);
        DialogueData.setFile(id);
        DialogueData.setOnPlayer(movingSound);
        DialogueData.setPos(position);
        if (fallOff != 0) {
            DialogueData.setFallOff(fallOff);
        }
        DialogueData.setNpc(npcName);
        DialogueData.setReverb(null);

        DialogueDataList.add(DialogueData);
    }

    public void saveToJson(String jsonFilePath) {
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        try (FileWriter writer = new FileWriter(jsonFilePath)) {
            gson.toJson(DialogueDataList, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
