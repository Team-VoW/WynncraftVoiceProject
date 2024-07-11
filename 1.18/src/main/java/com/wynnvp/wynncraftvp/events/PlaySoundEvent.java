/*
 * Copyright © Team-VoW 2024.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class PlaySoundEvent {
    public static void SoundPlayed(SoundInstance sound, CallbackInfo ci) {
        if (!(sound.getSource() == SoundSource.MASTER)) return;
        SoundManager manager = Minecraft.getInstance().getSoundManager();

        ci.cancel();

        manager.play(changeCategory(sound, SoundSource.BLOCKS));
    }

    private static SoundInstance changeCategory(SoundInstance sound, SoundSource soundCategory) {
        return new SoundInstance() {
            @Override
            public ResourceLocation getLocation() {
                return sound.getLocation();
            }

            @Override
            public WeighedSoundEvents resolve(SoundManager manager) {
                return sound.resolve(manager);
            }

            @Override
            public Sound getSound() {
                return sound.getSound();
            }

            @Override
            public SoundSource getSource() {
                return soundCategory;
            }

            @Override
            public boolean isLooping() {
                return sound.isLooping();
            }

            @Override
            public boolean isRelative() {
                return sound.isRelative();
            }

            @Override
            public int getDelay() {
                return sound.getDelay();
            }

            @Override
            public float getVolume() {
                return sound.getVolume();
            }

            @Override
            public float getPitch() {
                return sound.getPitch();
            }

            @Override
            public double getX() {
                return sound.getX();
            }

            @Override
            public double getY() {
                return sound.getY();
            }

            @Override
            public double getZ() {
                return sound.getZ();
            }

            @Override
            public Attenuation getAttenuation() {
                return sound.getAttenuation();
            }
        };
    }
}
