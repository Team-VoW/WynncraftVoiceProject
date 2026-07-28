/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ConfigPathNormalizerTest {
    private static final String MAC_PATH =
            "/Users/nyx/Library/Application Support/PrismLauncher/instances/Wynncraft/minecraft/VOW_AUDIO";

    @Test
    void keepsPlainPathWithSpacesUntouched() {
        assertEquals(MAC_PATH, ConfigPathNormalizer.normalize(MAC_PATH));
    }

    @Test
    void unescapesShellEscapedSpaces() {
        assertEquals(MAC_PATH, ConfigPathNormalizer.normalize(MAC_PATH.replace(" ", "\\ ")));
    }

    @Test
    void stripsSurroundingDoubleQuotes() {
        assertEquals(MAC_PATH, ConfigPathNormalizer.normalize("\"" + MAC_PATH + "\""));
    }

    @Test
    void stripsSurroundingSingleQuotes() {
        assertEquals(MAC_PATH, ConfigPathNormalizer.normalize("'" + MAC_PATH + "'"));
    }

    @Test
    void stripsQuotesAndUnescapesTogether() {
        assertEquals(MAC_PATH, ConfigPathNormalizer.normalize("'" + MAC_PATH.replace(" ", "\\ ") + "'"));
    }

    @Test
    void trimsSurroundingWhitespace() {
        assertEquals(MAC_PATH, ConfigPathNormalizer.normalize("  " + MAC_PATH + "  \n"));
    }

    @Test
    void keepsWindowsSeparatorsIntact() {
        assertEquals(
                "C:\\Users\\nyx\\AppData\\Roaming\\VOW_AUDIO",
                ConfigPathNormalizer.normalize("C:\\Users\\nyx\\AppData\\Roaming\\VOW_AUDIO"));
    }

    @Test
    void keepsUrlsIntact() {
        assertEquals(
                "https://example.com/sounds/", ConfigPathNormalizer.normalize(" \"https://example.com/sounds/\" "));
    }

    @Test
    void mapsBlankAndNullToEmpty() {
        assertEquals("", ConfigPathNormalizer.normalize(null));
        assertEquals("", ConfigPathNormalizer.normalize("   "));
        assertEquals("", ConfigPathNormalizer.normalize("\"\""));
    }
}
