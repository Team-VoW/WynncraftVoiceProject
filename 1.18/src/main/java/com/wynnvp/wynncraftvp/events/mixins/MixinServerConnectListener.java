package com.wynnvp.wynncraftvp.events.mixins;

import com.wynnvp.wynncraftvp.events.JoinServerEvent;
import net.minecraft.client.multiplayer.ClientHandshakePacketListenerImpl;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientHandshakePacketListenerImpl.class)
public class MixinServerConnectListener {


    @Inject(at = @At("RETURN"), method = "authenticateServer")
    private void onSuccess(String serverHash, CallbackInfoReturnable<Component> cir) {
        JoinServerEvent.run(serverHash);
    }

}
