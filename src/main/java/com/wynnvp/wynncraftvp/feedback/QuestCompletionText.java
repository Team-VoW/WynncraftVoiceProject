/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.feedback;

import java.util.Optional;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FontDescription;
import net.minecraft.network.chat.Style;

/** Removes layout glyphs before matching completion text, retaining the actual quest name. */
public final class QuestCompletionText {
    private QuestCompletionText() {}

    public static String plainText(Component message) {
        StringBuilder result = new StringBuilder();
        message.visit(
                (style, text) -> {
                    if (style.getFont() instanceof FontDescription.Resource font
                            && font.id().toString().equals("minecraft:space")) {
                        // Space-font advances use non-text glyphs, including supplementary code points.
                        // Preserve normal text: the spacing font may be inherited by its children.
                        text.codePoints().forEach(codePoint -> {
                            int type = Character.getType(codePoint);
                            result.appendCodePoint(
                                    type == Character.PRIVATE_USE
                                                    || type == Character.FORMAT
                                                    || type == Character.UNASSIGNED
                                            ? ' '
                                            : codePoint);
                        });
                    } else {
                        result.append(text);
                    }
                    return Optional.empty();
                },
                Style.EMPTY);
        return result.toString();
    }
}
