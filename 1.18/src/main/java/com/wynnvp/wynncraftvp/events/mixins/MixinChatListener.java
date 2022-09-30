package com.wynnvp.wynncraftvp.events.mixins;

import com.wynnvp.wynncraftvp.events.ReceiveChatEvent;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatHud.class)
public class MixinChatListener {
    @Inject(at = @At("RETURN"), method = "addMessage(Lnet/minecraft/text/Text;IIZ)V")
    private void addMessage(Text message, int messageId, int timestamp, boolean refresh, CallbackInfo ci) {
        if (!message.getString().startsWith("§[Voices of wynn]§r")) {
            ReceiveChatEvent.receivedChat(message.getString());
        }
    }
}
