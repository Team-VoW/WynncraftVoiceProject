package com.wynnvp.wynncraftvp.events.mixins;

import com.wynnvp.wynncraftvp.events.ShowGUIEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinShowGuiListener {

    @Inject(at = @At("HEAD"), method = "setScreen", cancellable = true)
    public void onShowScreen(Screen screen, CallbackInfo ci) {
        ShowGUIEvent.onGUIOpen(screen, ci);
    }
}
