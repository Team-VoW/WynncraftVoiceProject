/*
 * Copyright © Team-VoW 2024.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound;

import static com.wynnvp.wynncraftvp.utils.LineFormatter.formatToLineData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wynnvp.wynncraftvp.sound.line.LineData;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Optional;
import net.minecraft.world.phys.Vec3;

public class SoundsHandler2 {
    private final HashMap<String, SoundObject> sounds;

    public SoundsHandler2() {
        sounds = new HashMap<>();

        loadSoundsFromJson("sounds.json");
    }

    private void loadSoundsFromJson(String jsonFilePath) {
        Gson gson = new Gson();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(jsonFilePath);
        InputStreamReader reader = new InputStreamReader(inputStream);

        Type dialogueListType = new TypeToken<DialogueList>() {}.getType();
        DialogueList dialogues = gson.fromJson(reader, dialogueListType);

        for (DialogueData dialogue : dialogues.getSounds()) {
            String message = dialogue.getDialogueLine();
            String fileName = dialogue.getFile();
            boolean movingSound = dialogue.isOnPlayer();
            Vec3 position = dialogue.getPos();
            int fallOff = dialogue.getFallOff();
            String npcName = dialogue.getNpc();
            Reverb environment = dialogue.getReverb();

            LineData lineData = formatToLineData(message);
            message = lineData.getSoundLine();
            sounds.put(message, new SoundObject(lineData.getNPCName(), fileName, movingSound, position, fallOff));
        }
    }

    public Optional<SoundObject> get(String message) {
        return Optional.ofNullable(sounds.get(message));
    }
}
