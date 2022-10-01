package com.wynnvp.wynncraftvp.events.mixins;

import com.wynnvp.wynncraftvp.events.ReceiveChatEvent;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameMessageS2CPacket.class)
public class MixinChatListener {
    @Shadow @Final private Text message;

    @Inject(at = @At("RETURN"), method = "write")
    public void write(PacketByteBuf buf, CallbackInfo ci) {
        if (!message.getString().startsWith("§5[Voices of wynn]§r")) {
            ReceiveChatEvent.receivedChat(message.getString());
        }
    }
}
