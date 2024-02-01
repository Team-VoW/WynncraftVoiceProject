package com.wynnvp.wynncraftvp.sound.at;

import com.wynnvp.wynncraftvp.sound.SoundInstance;
import com.wynnvp.wynncraftvp.sound.SoundObject;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;

import static com.wynnvp.wynncraftvp.ModCore.config;

public class SoundAtCords extends SoundInstance {

    public SoundAtCords(SoundEvent soundEvent, SoundObject soundObject, Vec3 position) {
        super(soundEvent, SoundSource.VOICE, RandomSource.create());
        this.volume = soundObject.getFallOff() == 0 ? config.getBlockCutOff() / 16f : soundObject.getFallOff() / 16f;
        this.x = (float) position.x;
        this.z = (float) position.z;
        this.y = (float) position.y;
    }

    @Override
    public void tick() {
        SoundHighlight.ShowParticles(new Vec3(x, y, z));
    }
}