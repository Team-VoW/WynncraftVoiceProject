package com.wynnvp.wynncraftvp.events.mixins;

import com.wynnvp.wynncraftvp.events.ReceiveChatEvent;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayPacketListener.class)
public class MixinChatListener {

    @Inject(at = @At("RETURN"), method = "onGameMessage")
    public void onGameMessage(GameMessageS2CPacket par1, CallbackInfo ci) {
        String message = par1.getMessage().asString();
        if (!message.startsWith("§5[Voices of wynn]§r")) {
            ReceiveChatEvent.receivedChat(message);
        }
    }
}
