package com.wynnvp.wynncraftvp.sound.at;

import com.wynnvp.wynncraftvp.sound.SoundInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;

public class SoundAtPlayer extends SoundInstance {

    public SoundAtPlayer(SoundEvent soundEvent) {
        super(soundEvent, SoundSource.VOICE, RandomSource.create());
    }


    @Override
    public void tick() {
        Player p = Minecraft.getInstance().player;
        x = p.getX();
        y = p.getY();
        z = p.getZ();
    }
}
