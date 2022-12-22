package com.wynnvp.wynncraftvp.events.mixins;

import com.wynnvp.wynncraftvp.events.ReceiveChatEvent;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
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
                            "Lnet/minecraft/client/multiplayer/chat/ChatListener;handleSystemMessage(Lnet/minecraft/network/chat/Component;Z)V"),
            cancellable = true)
    public void onMessage(ClientboundSystemChatPacket packet, CallbackInfo ci) {

        if (packet.content().getString().contains("[Voices of Wynn]") || packet.overlay())
            return;


        ReceiveChatEvent.receivedChat(packet.content().getString());

    }
}
