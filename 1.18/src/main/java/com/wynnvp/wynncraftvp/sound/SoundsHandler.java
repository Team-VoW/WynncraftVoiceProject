package com.wynnvp.wynncraftvp.sound;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.sound.line.LineData;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Optional;

import static com.wynnvp.wynncraftvp.utils.LineFormatter.formatToLineData;

public class SoundsHandler {
    private final HashMap<String, SoundObject> sounds;

    public SoundsHandler() {
        sounds = new HashMap<>();

        loadSoundsFromJson("sounds.json");
    }

    private void loadSoundsFromJson(String jsonFilePath) {
        Gson gson = new Gson();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(jsonFilePath);
        InputStreamReader reader = new InputStreamReader(inputStream);

        Type dialogueListType = new TypeToken<DialogueList>() {
        }.getType();
        DialogueList dialogues = gson.fromJson(reader, dialogueListType);

        for (DialogueData dialogue : dialogues.getSounds()) {
            String message = dialogue.getDialogueLine();
            String fileName = dialogue.getFile();
            boolean movingSound = dialogue.isOnPlayer();
            Vector3 position = dialogue.getPosition();
            int fallOff = dialogue.getFallOff();
            String npcName = dialogue.getNpc();
            Reverb environment = dialogue.getReverb();

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

