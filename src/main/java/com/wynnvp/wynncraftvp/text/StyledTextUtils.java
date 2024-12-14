/*
 * Copyright © Team-VoW 2024.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.text;

/*
 * Copyright © Wynntils 2023-2024.
 * This file is released under LGPLv3. See LICENSE for full license details.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public final class StyledTextUtils {
    private static final Pattern COORDINATE_PATTERN =
            Pattern.compile(".*\\[(-?\\d+)(?:.\\d+)?, ?(-?\\d+)(?:.\\d+)?, ?(-?\\d+)(?:.\\d+)?\\].*");

    private static final String NEWLINE_PREPARATION = "\n";
    private static final Pattern NEWLINE_WRAP_PATTERN = Pattern.compile("\uDAFF\uDFFC\uE001\uDB00\uDC06");

    /**
     * This method is used by ChatHandler to split multi-line messages.
     * Multi-line messages use new lines not just to split the multi-line message, but also to format the message.
     * If a part is only a new line, we know it's a new message, but if the new line is in the middle of a part,
     * we know it's a new line in the same message, which we don't want to split.
     *
     * @param styledText The styled text to split
     * @return A list of styled texts, each representing a line
     */
    public static List<StyledText> splitInLines(StyledText styledText) {
        List<StyledText> newLines = new ArrayList<>();

        List<StyledTextPart> parts = new ArrayList<>();
        for (StyledTextPart part : styledText) {
            String partString = part.getString(null, PartStyle.StyleType.NONE);

            if (partString.equals("\n")) {
                newLines.add(StyledText.fromParts(parts));
                parts.clear();
            } else {
                parts.add(part);
            }
        }

        if (!parts.isEmpty()) {
            newLines.add(StyledText.fromParts(parts));
        }

        return newLines;
    }
}
