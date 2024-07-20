/*
 * Copyright © Team-VoW 2024.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.managers.sound.dialogue;

import java.util.Locale;

/**
 * A record to hold all data for a dialogue line.
 *
 * @param dialogueInfo  The unique information about the dialogue
 * @param lineInfo      The information about the line itself, including the NPC and the line numbers
 * @param soundSettings The sound settings for the dialogue
 */
public record DialogueData(
        DialogueInfo dialogueInfo, DialogueLineInfo lineInfo, ResolvedDialogueSoundSettings soundSettings) {
    /**
     * Get the dialogue ID for the dialogue line. This ID is also the file name for the sound file, and the key for the
     * sound event.
     * @return The dialogue ID
     */
    public String dialogueId() {
        if (dialogueInfo.fileOverride() != null) {
            return dialogueInfo.fileOverride();
        }

        return ("%s-%s-%s-%d"
                        .formatted(
                                dialogueInfo.content(),
                                dialogueInfo.context(),
                                lineInfo.npc(),
                                lineInfo.line().current()))
                .toLowerCase(Locale.ROOT);
    }
}
