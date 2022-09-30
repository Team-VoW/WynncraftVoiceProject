package com.wynnvp.wynncraftvp.sound.at;

import com.wynnvp.wynncraftvp.config.VOWConfig;
import com.wynnvp.wynncraftvp.npc.NPCHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Vec3d;

public class SoundAtArmorStand extends MovingSoundInstance {

    private final String rawName;

    public SoundAtArmorStand(SoundEvent soundEvent, String rawName){
        super(soundEvent, SoundCategory.VOICE);
        this.volume = VOWConfig.blockCutOff / 16f;
        this.rawName = rawName;
    }


    @Override
    public void tick() {
        if (this.rawName.isEmpty()) {
            return;
        }
        Vec3d vector = NPCHandler.find(rawName);
        ClientPlayerEntity p = MinecraftClient.getInstance().player;
        if (vector == null) {
            vector = p.getPos();
        } else {
            this.x = (float) vector.x;
            this.z = (float) vector.z;
            this.y = (float) vector.y;
        }

        if (VOWConfig.highlightSpeaker) {
            for (double x = vector.x - 0.2 ; x <= vector.x + 0.2; x+=0.1) {
                for (double y = vector.y - 0.2 ; y <= vector.y + 0.2; y+=0.1) {
                    for (double z = vector.z - 0.2 ; z <= vector.z + 0.2; z+=0.1) {
                        p.clientWorld.addParticle(ParticleTypes.END_ROD, x, y, z, 0, 0, 0);
                    }
                }
            }
        }
    }
}
