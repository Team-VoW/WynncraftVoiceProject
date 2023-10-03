/*
 * This file originates from © Wynntils 2023 https://github.com/Wynntils/Artemis/
 * but was modified to fit this project
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.text;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ComponentUtils {

    private static final Pattern NEWLINE_PATTERN = Pattern.compile("\n");

    public static List<Component> splitComponentInLines(Component message) {
        ComponentListBuilder builder = new ComponentListBuilder();

        message.visit(
                (style, str) -> {
                    Matcher m = NEWLINE_PATTERN.matcher(str);
                    int lastSegmentStart = 0;
                    while (m.find()) {
                        String segment = str.substring(lastSegmentStart, m.start());
                        builder.appendSegment(segment, style);
                        builder.endLine();
                        lastSegmentStart = m.end();
                    }
                    if (lastSegmentStart != str.length()) {
                        String segment = str.substring(lastSegmentStart);
                        builder.appendSegment(segment, style);
                    }
                    return Optional.empty();
                },
                Style.EMPTY);
        return builder.extractLines();
    }

    private static class ComponentListBuilder {
        private final List<Component> lines = new ArrayList<>();
        private MutableComponent currentLine = Component.literal("");

        protected void appendSegment(String segment, Style style) {
            currentLine.append(Component.literal(segment).withStyle(style));
        }

        protected void endLine() {
            lines.add(currentLine);
            currentLine = Component.literal("");
        }

        protected List<Component> extractLines() {
            if (!currentLine.getString().isEmpty()) {
                endLine();
            }
            return lines;
        }
    }
}
