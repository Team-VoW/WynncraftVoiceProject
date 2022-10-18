package com.wynnvp.wynncraftvp.sound.at;

import com.wynnvp.wynncraftvp.sound.SoundObject;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Vec3d;

import static com.wynnvp.wynncraftvp.ModCore.config;

public class SoundAtCords extends MovingSoundInstance {

    public SoundAtCords(SoundEvent soundEvent, SoundObject soundObject, Vec3d position) {
        super(soundEvent, SoundCategory.VOICE);
        this.volume = soundObject.getFallOff() == 0 ? config.getBlockCutOff() / 16f : soundObject.getFallOff() / 16f;
        this.x = (float) position.x;
        this.z = (float) position.z;
        this.y = (float) position.y;
    }


    @Override
    public void tick() {
        SoundHighlight.ShowParticles(new Vec3d(x, y, z));
    }
}