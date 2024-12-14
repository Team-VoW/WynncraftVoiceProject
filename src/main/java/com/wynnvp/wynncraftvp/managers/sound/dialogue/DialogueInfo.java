/*
 * Copyright © Team-VoW 2024.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.managers.sound.dialogue;

/**
 * A record to hold generic information about a dialogue line, which is used to identify the dialogue, making it a key
 * for identifying the sound to play.
 *
 * @param content      The content the dialogue is from
 * @param context      The dialogue context, which identifies the current dialogue (e.g. "caravanCrashed" in tutorial).
 *                     This can also be numbered from 1 to n, in the order the NPC says them
 *                     (mainly used for porting the old names to the new system).
 * @param fileOverride The file to use instead of using the default file for the dialogue
 */
public record DialogueInfo(String content, String context, String fileOverride) {}
