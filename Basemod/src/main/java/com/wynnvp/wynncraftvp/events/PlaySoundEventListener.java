package com.wynnvp.wynncraftvp.events;

import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.Sound;
import net.minecraft.client.audio.SoundEventAccessor;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.client.event.sound.SoundSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class PlaySoundEventListener {

    @SubscribeEvent
    public static void onPlaySound(PlaySoundEvent event) {
        if (!event.getSound().getCategory().getName().equalsIgnoreCase("master")){
            return;
        }
        ISound resultSound = new ISound() {
            @Override
            public ResourceLocation getSoundLocation() {
                return event.getSound().getSoundLocation();
            }

            @Nullable
            @Override
            public SoundEventAccessor createAccessor(SoundHandler handler) {
                return event.getSound().createAccessor(handler);
            }

            @Override
            public Sound getSound() {
                return event.getSound().getSound();
            }

            @Override
            public SoundCategory getCategory() {
                return SoundCategory.BLOCKS;
            }

            @Override
            public boolean canRepeat() {
                return event.getSound().canRepeat();
            }

            @Override
            public int getRepeatDelay() {
                return event.getSound().getRepeatDelay();
            }

            @Override
            public float getVolume() {
                return event.getSound().getVolume();
            }

            @Override
            public float getPitch() {
                return event.getSound().getPitch();
            }

            @Override
            public float getXPosF() {
                return event.getSound().getXPosF();
            }

            @Override
            public float getYPosF() {
                return event.getSound().getYPosF();
            }

            @Override
            public float getZPosF() {
                return event.getSound().getZPosF();
            }

            @Override
            public AttenuationType getAttenuationType() {
                return event.getSound().getAttenuationType();
            }
        };
        event.setResultSound(resultSound);
    }
}
