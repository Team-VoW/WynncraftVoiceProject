/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.events;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.sound.downloader.ToastManager;
import net.minecraft.client.Minecraft;

/** Replaces Fabric API's ClientTickEvents. Called from the tick mixins. */
public class ClientTickEvent {
    /** Runs every client tick, so also while no world is loaded. */
    public static void onEndClientTick(Minecraft client) {
        ToastManager toastManager = ToastManager.getInstance();
        if (toastManager != null) {
            toastManager.onClientTick(client);
        }
    }

    /** Runs every world tick, so only while a world is loaded. */
    public static void onEndWorldTick() {
        if (ModCore.overlayHandler != null) {
            ModCore.overlayHandler.onTick();
        }
        // The audio player is created lazily once OpenAL is up, see SoundEngineStartedMixin.
        if (ModCore.instance != null && ModCore.instance.audioPlayer != null) {
            ModCore.instance.audioPlayer.openAlPlayer.onTick();
        }
    }
}
