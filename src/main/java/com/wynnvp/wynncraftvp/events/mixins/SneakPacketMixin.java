/*
 * Copyright © Team-VoW 2024-2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.events.mixins;

import com.wynnvp.wynncraftvp.ModCore;
import net.minecraft.network.protocol.game.ServerboundPlayerInputPacket;
import net.minecraft.world.entity.player.Input;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerboundPlayerInputPacket.class)
public class SneakPacketMixin {
    @Unique
    private static boolean wasShiftDown = false;

    @Inject(method = "<init>(Lnet/minecraft/world/entity/player/Input;)V", at = @At("RETURN"))
    private void injectOnSneak(Input input, CallbackInfo ci) {
        boolean shiftDown = input.shift();

        if (shiftDown && !wasShiftDown) {
            if (ModCore.instance.audioPlayer != null) {
                ModCore.instance.audioPlayer.autoProgress.cancelShift();
            }
        }

        wasShiftDown = shiftDown;
    }
}
