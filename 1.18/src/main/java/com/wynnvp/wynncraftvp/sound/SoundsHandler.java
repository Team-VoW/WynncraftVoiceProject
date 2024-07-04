package com.wynnvp.wynncraftvp.sound;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.sound.line.LineData;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Optional;

import static com.wynnvp.wynncraftvp.utils.LineFormatter.formatToLineData;

public class SoundsHandler {
    private final HashMap<String, SoundObject> sounds;

    public SoundsHandler() {
        sounds = new HashMap<>();

        loadSoundsFromYaml("sounds.yml");
    }

    private void loadSoundsFromYaml(String yamlFilePath) {
        Yaml yaml = new Yaml(new Constructor(DialogueData.class, new LoaderOptions()));
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream(yamlFilePath);

        DialogueList dialogues = yaml.load(inputStream);

        for (DialogueData dialogue : dialogues.getSounds()) {
            String message = dialogue.getDialogueLine();
            String fileName = dialogue.getFileName();
            boolean movingSound = dialogue.isShouldPlayOnPlayer();
            Vector3 position = dialogue.getCustomPosition();
            int fallOff = dialogue.getCustomSoundFallOff();
            String npcName = dialogue.getNpcName();
            Environment environment = dialogue.getEnvironment();

            LineData lineData = formatToLineData(message);
            message = lineData.getSoundLine();
            sounds.put(message, new SoundObject(npcName, fileName, new CustomSoundClass(registerSound(fileName), movingSound), position, fallOff));
        }
    }

    public static SoundEvent registerSound(String name) {
        ResourceLocation id = new ResourceLocation(ModCore.MODID, name.toLowerCase());
        SoundEvent se = SoundEvent.createVariableRangeEvent(id);
        Registry.register(BuiltInRegistries.SOUND_EVENT, id, se);
        return se;
    }


    public Optional<SoundObject> get(String message) {
        return Optional.ofNullable(sounds.get(message));
    }

}

