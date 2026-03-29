/*
 * Copyright © Team-VoW 2024-2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.events.mixins;

import com.wynnvp.wynncraftvp.ModCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ClientPacketListener.class, priority = 900)
public class MixinChatListener {
    @Inject(method = "handleSystemChat", at = @At("HEAD"))
    public void onOverlayMessage(ClientboundSystemChatPacket packet, CallbackInfo ci) {
        if (!packet.overlay()) return;
        if (!Minecraft.getInstance().isSameThread()) return;
        ModCore.overlayHandler.onOverlayReceived(packet.content());
    }
}
