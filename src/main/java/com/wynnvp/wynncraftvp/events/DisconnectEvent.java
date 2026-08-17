/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.events;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.sound.NpcSoundBlocker;

/** Replaces Fabric API's ClientPlayConnectionEvents.DISCONNECT. */
public class DisconnectEvent {
    public static void run() {
        if (ModCore.overlayHandler != null) {
            ModCore.overlayHandler.onConnectionChange();
        }
        NpcSoundBlocker.reset();
    }
}
