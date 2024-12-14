/*
 * Copyright © Team-VoW 2023-2024.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.text;

/*
 * This file originates from © Wynntils 2023 https://github.com/Wynntils/Artemis/ but was modified to fit this project
 */
public enum NpcDialogueType {
    NONE, // This is a "clear screen" with no real message in it
    NORMAL, // User needs to press sneak to continue
    SELECTION, // User needs to select an option to continue
    CONFIRMATIONLESS // Message needs no confirmation
}
