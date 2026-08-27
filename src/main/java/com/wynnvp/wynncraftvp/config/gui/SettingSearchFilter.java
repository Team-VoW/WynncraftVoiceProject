/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.config.gui;

import java.util.Locale;

/** Pure text matching for the settings search field. */
public final class SettingSearchFilter {
    private SettingSearchFilter() {}

    /**
     * Matches every whitespace-delimited query term against a setting's label or its help text.
     */
    public static boolean matches(String query, String label, String tooltip) {
        String searchable = (label + " " + tooltip).toLowerCase(Locale.ROOT);
        for (String term : query.toLowerCase(Locale.ROOT).trim().split("\\s+")) {
            if (!term.isEmpty() && !searchable.contains(term)) {
                return false;
            }
        }
        return true;
    }
}
