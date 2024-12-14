/*
 * Copyright © Team-VoW 2024.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.managers.sound.dialogue;

import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Stream;
import org.joml.Vector3f;

public record DialogueHolder(TreeMap<String, DialogueContextHolder> content) {
    public static final DialogueHolder EMPTY = new DialogueHolder(new TreeMap<>());

    public static final DialogueSoundSettings DEFAULT_SETTINGS = new DialogueSoundSettings() {
        @Override
        public Optional<Boolean> followPlayer() {
            return Optional.of(false);
        }

        @Override
        public Optional<Integer> falloff() {
            return Optional.of(0);
        }

        @Override
        public Optional<Vector3f> position() {
            return Optional.of(new Vector3f());
        }
    };

    public Stream<DialogueData> dialogues() {
        return content.values().stream().flatMap(DialogueContextHolder::dialogues);
    }
}
