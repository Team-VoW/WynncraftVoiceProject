/*
 * Copyright © Team-VoW 2024-2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.events.mixins;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.sound.player.AudioPlayer;
import net.minecraft.client.sounds.SoundEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SoundEngine.class)
public class SoundEngineStartedMixin {
    @Inject(method = "loadLibrary", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/audio/Listener;reset()V"))
    private void onSoundEngineStarted(CallbackInfo ci) {
        // A reload runs this again, against a brand new OpenAL context. Retire the old player so its
        // worker threads stop pushing at names that died with the old context.
        if (ModCore.instance.audioPlayer != null) {
            ModCore.instance.audioPlayer.openAlPlayer.dispose();
        }
        ModCore.instance.audioPlayer = new AudioPlayer();
    }
}
