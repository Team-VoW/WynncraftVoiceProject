/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.events.mixins;

import com.wynnvp.wynncraftvp.commands.VowCommands;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Runs the mod's client side commands instead of sending them to the server. Commands we do not know
 * are left untouched so they still reach Wynncraft.
 */
@Mixin(ClientPacketListener.class)
public class MixinClientCommandSender {
    @Inject(method = "sendCommand", at = @At("HEAD"), cancellable = true)
    private void onSendCommand(String command, CallbackInfo ci) {
        if (VowCommands.execute(command)) {
            ci.cancel();
        }
    }

    @Inject(method = "sendUnattendedCommand", at = @At("HEAD"), cancellable = true)
    private void onSendUnattendedCommand(String command, Screen screen, CallbackInfo ci) {
        if (VowCommands.execute(command)) {
            ci.cancel();
        }
    }
}
