package com.wynnvp.wynncraftvp.sound.at;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.random.Random;

public class SoundAtPlayer extends MovingSoundInstance {

    public SoundAtPlayer(SoundEvent soundEvent) {
        super(soundEvent, SoundCategory.VOICE, Random.create());
    }


    @Override
    public void tick() {
        ClientPlayerEntity p = MinecraftClient.getInstance().player;
        x = p.getX();
        y = p.getY();
        z = p.getZ();
    }
}
