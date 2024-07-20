/*
 * Copyright © Team-VoW 2024.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.managers.sound.dialogue.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.wynnvp.wynncraftvp.managers.sound.dialogue.ContextDialogueHolder;
import com.wynnvp.wynncraftvp.managers.sound.dialogue.DialogueContextHolder;
import com.wynnvp.wynncraftvp.managers.sound.dialogue.DialogueData;
import com.wynnvp.wynncraftvp.managers.sound.dialogue.DialogueHolder;
import com.wynnvp.wynncraftvp.managers.sound.dialogue.DialogueInfo;
import com.wynnvp.wynncraftvp.managers.sound.dialogue.DialogueLineInfo;
import com.wynnvp.wynncraftvp.managers.sound.dialogue.DialogueSoundSettings;
import com.wynnvp.wynncraftvp.managers.sound.dialogue.ResolvedDialogueSoundSettings;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import org.joml.Vector3f;

public class DialogueHolderDeserializer implements JsonDeserializer<DialogueHolder> {
    @Override
    public DialogueHolder deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonDialogueHolder jsonHolder = context.deserialize(json, JsonDialogueHolder.class);

        TreeMap<String, DialogueContextHolder> content = new TreeMap<>();
        for (Map.Entry<String, JsonDialogueContextHolder> contentEntry : jsonHolder.content.entrySet()) {
            JsonDialogueContextHolder jsonContextHolder = contentEntry.getValue();

            LinkedHashMap<String, ContextDialogueHolder> contexts = new LinkedHashMap<>();

            for (Map.Entry<String, JsonContextDialogueHolder> contextEntry : jsonContextHolder.contexts.entrySet()) {
                JsonContextDialogueHolder jsonContextDialogueHolder = contextEntry.getValue();

                ArrayList<JsonDialogue> dialogues = jsonContextDialogueHolder.dialogues;

                // Convert JsonDialogue to DialogueData
                ArrayList<DialogueData> dialogueData = new ArrayList<>();
                for (JsonDialogue jsonDialogue : dialogues) {
                    DialogueInfo dialogueInfo =
                            new DialogueInfo(contentEntry.getKey(), contextEntry.getKey(), jsonDialogue.fileOverride());
                    DialogueLineInfo lineInfo = jsonDialogue.lineInfo;
                    ResolvedDialogueSoundSettings soundSettings = resolveSoundSettings(
                            jsonHolder.settings,
                            jsonContextHolder.settings,
                            jsonContextDialogueHolder.settings,
                            jsonDialogue.settings);

                    dialogueData.add(new DialogueData(dialogueInfo, lineInfo, soundSettings));
                }

                contexts.put(contextEntry.getKey(), new ContextDialogueHolder(dialogueData));
            }

            content.put(contentEntry.getKey(), new DialogueContextHolder(contexts));
        }

        return new DialogueHolder(content);
    }

    // Resolve sound settings for a dialogue line, always preferring the most specific settings
    // (dialogue > context > content > global > default)
    // Setting values are inherited as specific fields,
    // not as full objects (so it's possible to override only one field)
    private ResolvedDialogueSoundSettings resolveSoundSettings(
            JsonDialogueSoundSettings globalSettings,
            JsonDialogueSoundSettings contentSettings,
            JsonDialogueSoundSettings contextSettings,
            JsonDialogueSoundSettings dialogueSettings) {
        Boolean followPlayer = Optional.ofNullable(dialogueSettings)
                .flatMap(JsonDialogueSoundSettings::followPlayer)
                .or(() -> Optional.ofNullable(contextSettings).flatMap(JsonDialogueSoundSettings::followPlayer))
                .or(() -> Optional.ofNullable(contentSettings).flatMap(JsonDialogueSoundSettings::followPlayer))
                .or(() -> Optional.ofNullable(globalSettings).flatMap(JsonDialogueSoundSettings::followPlayer))
                .orElse(DialogueHolder.DEFAULT_SETTINGS.followPlayer().get());

        Integer falloff = Optional.ofNullable(dialogueSettings)
                .flatMap(JsonDialogueSoundSettings::falloff)
                .or(() -> Optional.ofNullable(contextSettings).flatMap(JsonDialogueSoundSettings::falloff))
                .or(() -> Optional.ofNullable(contentSettings).flatMap(JsonDialogueSoundSettings::falloff))
                .or(() -> Optional.ofNullable(globalSettings).flatMap(JsonDialogueSoundSettings::falloff))
                .orElse(DialogueHolder.DEFAULT_SETTINGS.falloff().get());

        Vector3f position = Optional.ofNullable(dialogueSettings)
                .flatMap(JsonDialogueSoundSettings::position)
                .or(() -> Optional.ofNullable(contextSettings).flatMap(JsonDialogueSoundSettings::position))
                .or(() -> Optional.ofNullable(contentSettings).flatMap(JsonDialogueSoundSettings::position))
                .or(() -> Optional.ofNullable(globalSettings).flatMap(JsonDialogueSoundSettings::position))
                .orElse(DialogueHolder.DEFAULT_SETTINGS.position().get());

        return new ResolvedDialogueSoundSettings(followPlayer, falloff, position);
    }

    private record JsonDialogueHolder(
            TreeMap<String, JsonDialogueContextHolder> content, JsonDialogueSoundSettings settings) {}

    private record JsonDialogueContextHolder(
            LinkedHashMap<String, JsonContextDialogueHolder> contexts, JsonDialogueSoundSettings settings) {}

    private record JsonContextDialogueHolder(ArrayList<JsonDialogue> dialogues, JsonDialogueSoundSettings settings) {}

    private record JsonDialogue(DialogueLineInfo lineInfo, JsonDialogueSoundSettings settings, String fileOverride) {}

    private static class JsonDialogueSoundSettings implements DialogueSoundSettings {
        private final Boolean followPlayer;
        private final Integer falloff;
        private final Vector3f position;

        private JsonDialogueSoundSettings(Boolean followPlayer, Integer falloff, Vector3f position) {
            this.followPlayer = followPlayer;
            this.falloff = falloff;
            this.position = position;
        }

        @Override
        public Optional<Boolean> followPlayer() {
            return Optional.ofNullable(followPlayer);
        }

        @Override
        public Optional<Integer> falloff() {
            return Optional.ofNullable(falloff);
        }

        @Override
        public Optional<Vector3f> position() {
            return Optional.ofNullable(position);
        }
    }
}
