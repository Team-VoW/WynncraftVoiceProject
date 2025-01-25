/*
 * Copyright © Team-VoW 2025.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.events;

import com.wynnvp.wynncraftvp.ModCore;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class ShowGUIEvent {
    public static void onGUIOpen(Screen screen, CallbackInfo ci) {
        if (screen instanceof TitleScreen) {
            ModCore.instance.audioDownloader.checkIfHasNot();
        }
    }
}
