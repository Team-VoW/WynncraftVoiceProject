/*
 * Copyright © Team-VoW 2023-2024.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.text;

import java.util.regex.Pattern;

/*
 * This file originates from © Wynntils 2023 https://github.com/Wynntils/Artemis/ but was modified to fit this project
 */
public enum RecipientType {
    INFO(null, "Info"),
    // https://regexr.com/7b14s
    NPC("^§7\\[\\d+\\/\\d+\\](?:§.)? ?§[25] ?.+: ?§..*$", "NPC");

    private final Pattern foregroundPattern;

    RecipientType(String foregroundPattern, String name) {
        this.foregroundPattern = (foregroundPattern == null ? null : Pattern.compile(foregroundPattern));
    }

    public boolean matchPattern(StyledText msg) {
        Pattern pattern = foregroundPattern;
        if (pattern == null) return false;
        return msg.getMatcher(pattern).find();
    }
}
