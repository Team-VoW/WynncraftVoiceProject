package com.wynnvp.wynncraftvp.sound;

import net.minecraft.util.SoundEvent;

public class CustomSoundClass {

    private final SoundEvent soundEvent;
    private final boolean movingSound;

    public CustomSoundClass(SoundEvent soundEvent, boolean movingSound) {
        this.soundEvent = soundEvent;
        this.movingSound = movingSound;
    }

    public SoundEvent getSoundEvent() {
        return soundEvent;
    }

    public boolean isMovingSound() {
        return movingSound;
    }
}
