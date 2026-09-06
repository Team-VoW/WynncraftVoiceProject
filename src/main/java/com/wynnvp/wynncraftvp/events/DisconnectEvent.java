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
        ModCore.inLiveWynnServer = false;
        com.wynnvp.wynncraftvp.feedback.QuestFeedback.disconnect();
        if (ModCore.overlayHandler != null) {
            ModCore.overlayHandler.onConnectionChange();
        }
        if (ModCore.instance != null && ModCore.instance.audioPlayer != null) {
            // Leaving mid-line would otherwise strand the sneak key down on the next world.
            ModCore.instance.audioPlayer.autoProgress.reset();
            ModCore.instance.audioPlayer.stopAudio();
        }
        NpcSoundBlocker.reset();
    }
}
