package com.wynnvp.wynncraftvp.events.mixins;

import com.wynnvp.wynncraftvp.events.ShowGUIEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MixinShowGuiListener {

    @Inject(at = @At("HEAD"), method = "setScreen", cancellable = true)
    public void onShowScreen(Screen screen, CallbackInfo ci) {
        ShowGUIEvent.onGUIOpen(screen, ci);
    }
}
