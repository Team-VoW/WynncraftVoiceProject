/*
 * Copyright © Team-VoW 2024.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.managers.sound.dialogue;

import com.wynnvp.wynncraftvp.utils.type.CappedValue;

/**
 * @param npc      The NPC the dialogue is from, using the name it appears as text in the game.
 *                 This value is optional and can be null, if the NPC is not known.
 * @param line     The current/total number of the dialogue in the context.
 *                 (e.g 2/5, if the current dialogue is "[2/5]" in the game)
 *                 This value is optional and can be null, if the line is not specified.
 * @param dialogue The voiced dialogue line itself
 */
public record DialogueLineInfo(String npc, CappedValue line, String dialogue) {}
