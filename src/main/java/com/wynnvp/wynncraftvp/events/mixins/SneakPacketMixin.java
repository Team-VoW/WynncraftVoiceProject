/*
 * Copyright © Team-VoW 2024.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.events.mixins;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.events.ReceiveChatEvent;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerboundPlayerCommandPacket.class) // Target the class responsible for player input packets
public class SneakPacketMixin {
    @Inject(
            at = @At(value = "RETURN"),
            method =
                    "<init>(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/network/protocol/game/ServerboundPlayerCommandPacket$Action;I)V")
    private void injectOnSneak(Entity entity, ServerboundPlayerCommandPacket.Action action, int i, CallbackInfo ci) {
        if (action != ServerboundPlayerCommandPacket.Action.PRESS_SHIFT_KEY) {
            return;
        }
        ReceiveChatEvent.resetCooldowns();
        if (ModCore.instance.audioPlayer != null)
            ModCore.instance.audioPlayer.autoProgress.cancelShift();
    }
}
