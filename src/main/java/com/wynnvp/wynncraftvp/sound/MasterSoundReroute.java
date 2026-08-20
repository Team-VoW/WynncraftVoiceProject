/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound;

import com.wynnvp.wynncraftvp.ModCore;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
import org.jspecify.annotations.NonNull;

/**
 * Moves sounds Wynncraft plays under {@link SoundSource#MASTER} into {@link SoundSource#BLOCKS}.
 *
 * <p>Minecraft gives the MASTER category no slider of its own: {@code Options.getFinalSoundSourceVolume}
 * returns the Master slider unchanged for it, while every other category is multiplied by Master on top
 * of its own slider. Wynncraft plays many of its sound effects under MASTER, so without this they can
 * only be turned down by turning the whole game down. Re-tagging them as BLOCKS gives players a slider
 * that affects those sounds and nothing else.
 */
public final class MasterSoundReroute {
    private MasterSoundReroute() {}

    /**
     * @return the sound to actually play — either {@code sound} untouched, or a BLOCKS-category view of it
     */
    public static SoundInstance apply(SoundInstance sound) {
        if (sound == null) return null;
        if (ModCore.config == null || !ModCore.config.isRouteMasterSoundsToBlocks()) return sound;
        if (sound.getSource() != SoundSource.MASTER) return sound;

        // The engine keys its live-channel and ticking maps on the instance itself, so anything that
        // gets stopped or updated later must reach the engine as the very object the caller holds on
        // to. One-shots are never looked up again, looping sounds are.
        if (sound.isLooping()) return sound;

        return new CategoryOverride(sound, SoundSource.BLOCKS);
    }

    /** A {@link SoundInstance} that is {@code delegate} in every respect except its category. */
    private record CategoryOverride(SoundInstance delegate, SoundSource source) implements SoundInstance {
        @Override
        public @NonNull Identifier getIdentifier() {
            return delegate.getIdentifier();
        }

        @Override
        public WeighedSoundEvents resolve(@NonNull SoundManager manager) {
            return delegate.resolve(manager);
        }

        @Override
        public Sound getSound() {
            return delegate.getSound();
        }

        @Override
        public @NonNull SoundSource getSource() {
            return source;
        }

        @Override
        public boolean isLooping() {
            return delegate.isLooping();
        }

        @Override
        public boolean isRelative() {
            return delegate.isRelative();
        }

        @Override
        public int getDelay() {
            return delegate.getDelay();
        }

        @Override
        public float getVolume() {
            return delegate.getVolume();
        }

        @Override
        public float getPitch() {
            return delegate.getPitch();
        }

        @Override
        public double getX() {
            return delegate.getX();
        }

        @Override
        public double getY() {
            return delegate.getY();
        }

        @Override
        public double getZ() {
            return delegate.getZ();
        }

        @Override
        public @NonNull Attenuation getAttenuation() {
            return delegate.getAttenuation();
        }

        @Override
        public boolean canStartSilent() {
            return delegate.canStartSilent();
        }

        @Override
        public boolean canPlaySound() {
            return delegate.canPlaySound();
        }
    }
}
