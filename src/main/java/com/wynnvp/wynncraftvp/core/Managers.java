/*
 * Copyright © Team-VoW 2024.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.core;

import com.wynnvp.wynncraftvp.managers.json.JsonManager;
import com.wynnvp.wynncraftvp.managers.sound.SoundManager;

public final class Managers {
    // Managers with no dependencies, kept in alphabetical order
    public static final JsonManager Json = new JsonManager();
    public static final SoundManager Sound = new SoundManager();

    // Managers with dependencies, kept in dependency order (then alphabetical)

    // Initialization method for all managers
    public static void initialize() {
        // no-op, class loading this class will initialize all managers
    }
}
