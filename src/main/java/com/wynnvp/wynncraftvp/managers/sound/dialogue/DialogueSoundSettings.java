/*
 * Copyright © Team-VoW 2024.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.managers.sound.dialogue;

import java.util.Optional;
import org.joml.Vector3f;

/**
 * An interface for providing sound settings for a dialogue line.
 * For more information, see {@link ResolvedDialogueSoundSettings}.
 */
public interface DialogueSoundSettings {
    Optional<Boolean> followPlayer();

    Optional<Integer> falloff();

    Optional<Vector3f> position();
}
