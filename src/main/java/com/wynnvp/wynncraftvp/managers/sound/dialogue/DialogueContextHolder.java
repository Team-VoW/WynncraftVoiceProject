/*
 * Copyright © Team-VoW 2024.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.managers.sound.dialogue;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Stream;

public record DialogueContextHolder(LinkedHashMap<String, ContextDialogueHolder> contexts) {
    public Stream<DialogueData> dialogues() {
        return contexts.values().stream().map(ContextDialogueHolder::dialogues).flatMap(List::stream);
    }
}
