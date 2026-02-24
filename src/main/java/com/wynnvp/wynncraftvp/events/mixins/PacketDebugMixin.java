/*
 * Copyright © Team-VoW 2024-2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.events.mixins;

import com.wynnvp.wynncraftvp.utils.Utils;
import net.minecraft.network.Connection;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Connection.class)
public class PacketDebugMixin {
    private static final String TARGET_TEXT = "Never speak of this";

    @Inject(method = "genericsFtw", at = @At("HEAD"))
    private static <T extends PacketListener> void onPacketReceived(
            Packet<T> packet, PacketListener listener, CallbackInfo ci) {
        String packetStr = packet.toString();
        if (packetStr.contains(TARGET_TEXT)) {
            Utils.sendMessage("§c[PacketDebug MATCH]§r " + packet.getClass().getSimpleName() + ": " + packetStr);
        }
    }
}
