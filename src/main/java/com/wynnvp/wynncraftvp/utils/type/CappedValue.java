/*
 * Copyright © Team-VoW 2024.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.utils.type;

public record CappedValue(int current, int max) {
    public static final CappedValue EMPTY = new CappedValue(0, 0);

    public boolean atMax() {
        return current >= max;
    }
}
