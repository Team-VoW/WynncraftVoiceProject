package com.wynnvp.wynncraftvp.sound.at;

import com.wynnvp.wynncraftvp.npc.NPCHandler;
import com.wynnvp.wynncraftvp.sound.SoundObject;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Vec3d;

import static com.wynnvp.wynncraftvp.ModCore.config;

public class SoundAtArmorStand extends MovingSoundInstance {

    private final String rawName;
    private NPCHandler.CachedEntity cachedEntity = null;

    public SoundAtArmorStand(SoundEvent soundEvent, String rawName, SoundObject soundObject) {
        super(soundEvent, SoundCategory.VOICE);
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

        if (config.isHighlightSpeaker()) {
            ClientPlayerEntity p = MinecraftClient.getInstance().player;
            for (double x = entityPos.x - 0.2; x <= entityPos.x + 0.2; x += 0.1) {
                for (double y = entityPos.y - 0.2; y <= entityPos.y + 0.2; y += 0.1) {
                    for (double z = entityPos.z - 0.2; z <= entityPos.z + 0.2; z += 0.1) {
                        p.clientWorld.addParticle(ParticleTypes.END_ROD, x, y, z, 0, 0, 0);
                    }
                }
            }
        }
    }
}
