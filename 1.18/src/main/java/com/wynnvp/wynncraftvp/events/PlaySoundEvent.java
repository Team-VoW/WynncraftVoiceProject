package com.wynnvp.wynncraftvp.events;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.Sound;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.sound.WeightedSoundSet;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class PlaySoundEvent {

    public static void SoundPlayed(SoundInstance sound, CallbackInfo ci){
        if (!(sound.getCategory() == SoundCategory.MASTER))
            return;
        SoundManager manager = MinecraftClient.getInstance().getSoundManager();

        ci.cancel();
        manager.play(changeCategory(sound, SoundCategory.BLOCKS));
    }

    private static SoundInstance changeCategory(SoundInstance sound, SoundCategory soundCategory){

        return new SoundInstance() {
            @Override
            public Identifier getId() {
                return sound.getId();
            }
            @Nullable
            @Override
            public WeightedSoundSet getSoundSet(SoundManager soundManager) {
                return sound.getSoundSet(soundManager);
            }

            @Override
            public Sound getSound() {
                return sound.getSound();
            }

            @Override
            public SoundCategory getCategory() {
                return soundCategory;
            }

            @Override
            public boolean isRepeatable() {
                return sound.isRepeatable();
            }

            @Override
            public boolean isRelative() {
                return sound.isRelative();
            }

            @Override
            public int getRepeatDelay() {
                return sound.getRepeatDelay();
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
            public AttenuationType getAttenuationType() {
                return sound.getAttenuationType();
            }
        };
    }
}
