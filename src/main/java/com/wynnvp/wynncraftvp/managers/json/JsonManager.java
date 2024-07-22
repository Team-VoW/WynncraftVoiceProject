/*
 * Copyright © Team-VoW 2024.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.managers.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wynnvp.wynncraftvp.core.Manager;
import com.wynnvp.wynncraftvp.managers.sound.dialogue.DialogueHolder;
import com.wynnvp.wynncraftvp.managers.sound.dialogue.json.DialogueHolderDeserializer;
import java.util.List;

public class JsonManager extends Manager {
    public static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(DialogueHolder.class, new DialogueHolderDeserializer())
            .enableComplexMapKeySerialization()
            .setPrettyPrinting()
            .serializeNulls()
            .create();

    public JsonManager() {
        super(List.of());
    }
}
