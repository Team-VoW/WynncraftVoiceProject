/*
 * Copyright © Team-VoW 2024.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.events.mixins;

import com.wynnvp.wynncraftvp.ModCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundRemoveMobEffectPacket;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ClientPacketListener.class, priority = 900)
public class MixinChatListener {
    @Inject(
            method = "handleSystemChat",
            at =
                    @At(
                            value = "INVOKE",
                            target =
                                    "Lnet/minecraft/client/multiplayer/chat/ChatListener;handleSystemMessage(Lnet/minecraft/network/chat/Component;Z)V"))
    public void onMessage(ClientboundSystemChatPacket packet, CallbackInfo ci) {
        if (packet.content().getString().contains("[Voices of Wynn]") || packet.overlay()) return;

        ModCore.chatHandler.onChatReceived(packet.content());
    }

    @Inject(
            method = "handleUpdateMobEffect(Lnet/minecraft/network/protocol/game/ClientboundUpdateMobEffectPacket;)V",
            at = @At("RETURN"))
    private void handleUpdateMobEffectPost(ClientboundUpdateMobEffectPacket packet, CallbackInfo ci) {
        var minecraftInstance = Minecraft.getInstance();
        if (!minecraftInstance.isSameThread() || packet.getEntityId() != minecraftInstance.player.getId()) return;

        ModCore.chatHandler.onStatusEffectUpdate(packet);
    }

    @Inject(
            method = "handleRemoveMobEffect(Lnet/minecraft/network/protocol/game/ClientboundRemoveMobEffectPacket;)V",
            at = @At("RETURN"))
    private void handleRemoveMobEffectPost(ClientboundRemoveMobEffectPacket packet, CallbackInfo ci) {
        var minecraftInstance = Minecraft.getInstance();
        if (!minecraftInstance.isSameThread() || packet.getEntity(minecraftInstance.level) != minecraftInstance.player)
            return;

        ModCore.chatHandler.onStatusEffectRemove(packet);
    }
}
