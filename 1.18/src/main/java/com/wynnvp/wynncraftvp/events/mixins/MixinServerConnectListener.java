package com.wynnvp.wynncraftvp.events.mixins;

import com.wynnvp.wynncraftvp.events.JoinServerEvent;
import net.minecraft.client.network.ClientLoginNetworkHandler;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.s2c.login.LoginSuccessS2CPacket;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientLoginNetworkHandler.class)
public class MixinServerConnectListener {

    @Shadow
    @Final
    private ClientConnection connection;

    @Inject(at = @At("RETURN"), method = "onSuccess")
    private void onSuccess(LoginSuccessS2CPacket packet, CallbackInfo ci) {
        JoinServerEvent.run(connection.getAddress().toString());
    }

}
