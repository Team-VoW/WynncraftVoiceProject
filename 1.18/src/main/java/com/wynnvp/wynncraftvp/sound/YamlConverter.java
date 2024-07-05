package com.wynnvp.wynncraftvp.sound;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wynnvp.wynncraftvp.sound.line.LineData;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.representer.Representer;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



import static com.wynnvp.wynncraftvp.utils.LineFormatter.formatToLineData;

public class YamlConverter {
    private final List<DialogueData> DialogueDataList;


    public static void main(String[] args) {
        YamlConverter yamlConverter = new YamlConverter();
        yamlConverter.saveToYaml("sounds.yml");
        yamlConverter.saveToJson("sounds.json");
    }
    public YamlConverter() {
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
        DialogueData.setFileName(id);
        DialogueData.setShouldPlayOnPlayer(movingSound);
        DialogueData.setCustomPosition(position);
        if (fallOff != 0) {
            DialogueData.setCustomSoundFallOff(fallOff );
        }
        DialogueData.setNpcName(npcName);
        DialogueData.setEnvironment(null);

        DialogueDataList.add(DialogueData);
    }


    public void saveToYaml(String yamlFilePath) {
        DialogueList soundConfig = new DialogueList();
        soundConfig.setSounds(DialogueDataList);

        DumperOptions options = new DumperOptions();
        options.setIndent(2);
        options.setPrettyFlow(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Representer representer = new Representer(options);
        representer.getPropertyUtils().setSkipMissingProperties(true);
        Yaml yaml = new Yaml(new Constructor(DialogueList.class, new LoaderOptions()), representer, options);

        try (FileWriter writer = new FileWriter(yamlFilePath)) {
            yaml.dump(soundConfig, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
