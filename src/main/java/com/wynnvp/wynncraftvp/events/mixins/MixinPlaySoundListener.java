/*
 * Copyright © Team-VoW 2024-2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.events.mixins;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.events.PlaySoundEvent;
import java.util.Set;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SoundManager.class)
public abstract class MixinPlaySoundListener {
    @Unique
    private static final Set<Identifier> VILLAGER_SOUNDS = Set.of(
            SoundEvents.VILLAGER_TRADE.location(),
            SoundEvents.VILLAGER_YES.location(),
            SoundEvents.VILLAGER_NO.location(),
            SoundEvents.VILLAGER_AMBIENT.location(),
            SoundEvents.WANDERING_TRADER_NO.location(),
            SoundEvents.ZOMBIE_AMBIENT.location(),
            SoundEvents.PARROT_IMITATE_EVOKER.location());

    @Inject(method = "play", at = @At("HEAD"), cancellable = true)
    private void onPlay(SoundInstance sound, CallbackInfoReturnable<SoundEngine.PlayResult> cir) {
        Identifier id = sound.getIdentifier();

        if (ModCore.config.isRemoveVillagerSounds() && VILLAGER_SOUNDS.contains(id)) {
            cir.cancel();
            return;
        }

        PlaySoundEvent.SoundPlayed(sound, cir);
    }
}
