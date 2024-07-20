/*
 * Copyright © Team-VoW 2024.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.managers.sound.dialogue;

import org.joml.Vector3f;

/**
 * A record to hold the sound settings for a dialogue line.
 * This class' fields should match {@link DialogueSoundSettings}.
 *
 * @param followPlayer Whether the sound should follow the player or not
 * @param falloff      The falloff of the sound
 * @param position     The position of the sound
 */
public record ResolvedDialogueSoundSettings(boolean followPlayer, int falloff, Vector3f position) {}
