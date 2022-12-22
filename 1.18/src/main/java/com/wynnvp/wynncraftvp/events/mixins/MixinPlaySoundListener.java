package com.wynnvp.wynncraftvp.events.mixins;

import com.wynnvp.wynncraftvp.events.PlaySoundEvent;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SoundManager.class)
public class MixinPlaySoundListener {

    @Inject(at = @At("HEAD"), method = "play", cancellable = true)
    public void onShowScreen(SoundInstance sound, CallbackInfo ci) {
        PlaySoundEvent.SoundPlayed(sound, ci);
    }

}
