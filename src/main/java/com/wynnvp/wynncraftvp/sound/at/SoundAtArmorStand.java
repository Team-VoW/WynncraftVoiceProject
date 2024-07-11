/*
 * Copyright © Team-VoW 2024.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.at;

import static com.wynnvp.wynncraftvp.ModCore.config;

import com.wynnvp.wynncraftvp.npc.CachedEntity;
import com.wynnvp.wynncraftvp.npc.NPCHandler;
import com.wynnvp.wynncraftvp.sound.SoundInstance;
import com.wynnvp.wynncraftvp.sound.SoundObject;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;

public class SoundAtArmorStand extends SoundInstance {
    private final String rawName;
    private CachedEntity cachedEntity = null;

    public SoundAtArmorStand(SoundEvent soundEvent, String rawName, SoundObject soundObject) {
        super(soundEvent, SoundSource.VOICE, RandomSource.create());

        this.volume = soundObject.getFallOff() == 0 ? config.getBlockCutOff() / 16f : soundObject.getFallOff() / 16f;
        this.rawName = rawName;
    }

    @Override
    public void tick() {
        if (this.rawName.isEmpty()) {
            return;
        }
        if (!NPCHandler.isCachedValid(cachedEntity)) cachedEntity = NPCHandler.findEntity(rawName);

        // Was not able to find the entity, so just let the sound keep on playing where it is.
        if (cachedEntity == null) return;

        Vec3 entityPos = cachedEntity.child.getEyePosition();

        this.x = (float) entityPos.x;
        this.z = (float) entityPos.z;
        this.y = (float) entityPos.y;

        SoundHighlight.ShowParticles(entityPos);
    }
}
