package com.wynnvp.wynncraftvp.sound;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;


public abstract class SoundInstance extends AbstractTickableSoundInstance {


    protected SoundInstance(SoundEvent soundEvent, SoundSource soundSource, RandomSource randomSource) {
        super(soundEvent, soundSource, randomSource);
    }

    public void StopSound() {
        this.stop();
    }


}
