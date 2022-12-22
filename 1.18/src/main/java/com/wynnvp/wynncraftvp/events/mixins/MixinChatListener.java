package com.wynnvp.wynncraftvp.events.mixins;

import com.wynnvp.wynncraftvp.events.ReceiveChatEvent;
import com.wynnvp.wynncraftvp.utils.Utils;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundPlayerChatPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class MixinChatListener {

    @Inject(
            method = "handlePlayerChat",
            at =
            @At(
                    value = "INVOKE",
                    target =
                            "Lnet/minecraft/client/multiplayer/chat/ChatListener;handlePlayerChatMessage(Lnet/minecraft/network/chat/PlayerChatMessage;Lcom/mojang/authlib/GameProfile;Lnet/minecraft/network/chat/ChatType$Bound;)V")
    )

    public void onMessage(ClientboundPlayerChatPacket packet, CallbackInfo ci) {

        //  par1.content().getString()
        //  if (par1.getType() == MessageType.CHAT || par1.getType() == MessageType.SYSTEM) {
        //    String message = par1.getMessage().getString();

                //String message = par1.content().getString();
        Utils.sendMessage("Got message");

        if (packet.unsignedContent() == null)
            return;


        Utils.sendMessage("unsigned content.getcontents.tostring: " + packet.unsignedContent().getContents().toString());
        Utils.sendMessage("unsignedContent.tostring: " + packet.unsignedContent().toString());
        Utils.sendMessage("Type: " + packet.chatType().chatType());

        ReceiveChatEvent.receivedChat(packet.unsignedContent().getString());

    }
}
