/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import org.junit.jupiter.api.Test;

class OverlayTextCleanerTest {
    @Test
    void plainAsciiTextIsUnchanged() {
        assertEquals("Hello world", OverlayHandler.cleanBodyRawText(List.of("Hello world ")));
    }

    @Test
    void highCodepointReplacedWithSpace() {
        // U+0100 is above the 0x00FF threshold used by Wynncraft custom fonts
        String withSeparator = "Hello\u0100World";
        String result = OverlayHandler.cleanBodyRawText(List.of(withSeparator));
        assertEquals("Hello World", result);
    }

    @Test
    void multipleConsecutiveHighCodepointsCollapseToOneSpace() {
        String withMultipleSeparators = "A\u0100\u0101\u0102B";
        String result = OverlayHandler.cleanBodyRawText(List.of(withMultipleSeparators));
        assertEquals("A B", result);
    }

    @Test
    void trailingSpaceIsTrimmed() {
        String result = OverlayHandler.cleanBodyRawText(List.of("Hello "));
        assertEquals("Hello", result);
    }

    @Test
    void multiplePartsJoinedWithSpaces() {
        // Each part gets a trailing space appended, then result is trimmed
        String result = OverlayHandler.cleanBodyRawText(List.of("Hello", "World"));
        assertEquals("Hello World", result);
    }

    @Test
    void emptyListReturnsNull() {
        assertNull(OverlayHandler.cleanBodyRawText(List.of()));
    }

    @Test
    void allHighCodepointsReturnsNull() {
        // Everything is stripped → blank result → null
        String result = OverlayHandler.cleanBodyRawText(List.of("\u0100\u0101\u0102"));
        assertNull(result);
    }

    @Test
    void leadingHighCodepointProducesNoLeadingSpace() {
        // High codepoint at start: no leading space should appear
        String result = OverlayHandler.cleanBodyRawText(List.of("\u0100Hello"));
        assertEquals("Hello", result);
    }
}
