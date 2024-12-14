package com.wynnvp.wynncraftvp.sound;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wynnvp.wynncraftvp.sound.line.LineData;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.wynnvp.wynncraftvp.utils.LineFormatter.formatToLineData;

public class JsonConverter {
    private final List<DialogueData> DialogueDataList;


    public static void main(String[] args) {
        JsonConverter yamlConverter = new JsonConverter();
        yamlConverter.saveToJson("sounds.json");
    }

    public JsonConverter() {
        DialogueDataList = new ArrayList<>();
        Sounds.register(this);
    }

    public void addSound(String message, String id, boolean movingSound) {
        addSound(message, id, movingSound, null);
    }

    public void addSound(String message, String id, boolean movingSound, Vector3 position) {
        addSound(message, id, movingSound, position, 0);
    }

    public void addSound(String message, String id, boolean movingSound, int fallOff) {
        addSound(message, id, movingSound, null, fallOff);
    }

    public void addSound(String message, String id, boolean movingSound, Vector3 position, int fallOff) {
        LineData lineData = formatToLineData(message);
        String npcName = lineData.getNPCName();
        DialogueData DialogueData = new DialogueData();
        DialogueData.setDialogueLine(message);
        DialogueData.setFile(id);
        DialogueData.setOnPlayer(movingSound);
        DialogueData.setPosition(position);
        if (fallOff != 0) {
            DialogueData.setFallOff(fallOff);
        }
        DialogueData.setNpc(npcName);
        DialogueData.setReverb(null);

        DialogueDataList.add(DialogueData);
    }


    public void saveToJson(String jsonFilePath) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .create();
        try (FileWriter writer = new FileWriter(jsonFilePath)) {
            gson.toJson(DialogueDataList, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
