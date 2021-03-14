package com.wynnvp.wynncraftvp.sound;

import net.minecraft.util.SoundEvent;

public class CustomSoundClass {
    private final SoundEvent soundEvent;
    private final boolean movingSound;

    public CustomSoundClass(SoundEvent soundEvent, boolean movingSound) {
        this.movingSound = movingSound;
        this.soundEvent = soundEvent;
    }

    public SoundEvent getSoundEvent() {
        return soundEvent;
    }

    public boolean isMovingSound() {
        return movingSound;
    }
}
