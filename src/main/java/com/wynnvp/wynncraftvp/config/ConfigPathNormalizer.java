/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.config;

/**
 * Cleans up paths typed or pasted into the config GUI.
 * <p>
 * Paths with spaces are common outside of Windows (macOS keeps instances under
 * {@code ~/Library/Application Support/...}), and users reach for shell habits when entering them:
 * dragging a folder into a terminal escapes every space with a backslash, and wrapping the path in
 * quotes is muscle memory. Neither is a valid file path, so both are stripped here.
 */
public final class ConfigPathNormalizer {
    public static String normalize(String path) {
        if (path == null) {
            return "";
        }

        return unescapeSpaces(stripSurroundingQuotes(path.trim()));
    }

    private static String stripSurroundingQuotes(String path) {
        String current = path;
        while (current.length() >= 2) {
            char first = current.charAt(0);
            char last = current.charAt(current.length() - 1);
            if ((first != '"' && first != '\'') || first != last) {
                break;
            }
            current = current.substring(1, current.length() - 1).trim();
        }
        return current;
    }

    /**
     * A backslash directly before a space is a shell escape. No platform we support has a directory
     * whose name starts with a space, so this is safe to undo even for Windows paths.
     */
    private static String unescapeSpaces(String path) {
        return path.replace("\\ ", " ");
    }

    private ConfigPathNormalizer() {}
}
