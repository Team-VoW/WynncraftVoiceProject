package com.wynnvp.wynncraftvp.sound.at;

import com.wynnvp.wynncraftvp.npc.CachedEntity;
import com.wynnvp.wynncraftvp.npc.NPCHandler;
import com.wynnvp.wynncraftvp.sound.SoundObject;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

import static com.wynnvp.wynncraftvp.ModCore.config;

public class SoundAtArmorStand extends MovingSoundInstance {

    private final String rawName;
    private CachedEntity cachedEntity = null;

    public SoundAtArmorStand(SoundEvent soundEvent, String rawName, SoundObject soundObject) {
        super(soundEvent, SoundCategory.VOICE, Random.create());

        this.volume = soundObject.getFallOff() == 0 ? config.getBlockCutOff() / 16f : soundObject.getFallOff() / 16f;
        this.rawName = rawName;
    }


    @Override
    public void tick() {
        if (this.rawName.isEmpty()) {
            return;
        }
        if (!NPCHandler.isCachedValid(cachedEntity))
            cachedEntity = NPCHandler.findEntity(rawName);

        //Was not able to find the entity, so just let the sound keep on playing where it is.
        if (cachedEntity == null)
            return;

        Vec3d entityPos = cachedEntity.child.getEyePos();

        this.x = (float) entityPos.x;
        this.z = (float) entityPos.z;
        this.y = (float) entityPos.y;

        SoundHighlight.ShowParticles(entityPos);
    }
}
