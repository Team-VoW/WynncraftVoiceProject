/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.config.gui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class SettingSearchFilterTest {
    @Test
    void matchesCaseInsensitivelyAcrossLabelAndTooltip() {
        assertTrue(SettingSearchFilter.matches(
                "REVERB environmental", "Reverb strength", "Controls environmental room effects."));
    }

    @Test
    void requiresEveryWhitespaceDelimitedTerm() {
        assertFalse(SettingSearchFilter.matches("reverb missing", "Reverb strength", "Controls room effects."));
    }

    @Test
    void emptyQueryMatchesEverySetting() {
        assertTrue(SettingSearchFilter.matches("   ", "Voice volume", "Adjusts voice playback."));
    }
}
