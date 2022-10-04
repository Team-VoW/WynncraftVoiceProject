package com.wynnvp.wynncraftvp.sound.at;

import com.wynnvp.wynncraftvp.npc.NPCHandler;
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

    public SoundAtArmorStand(SoundEvent soundEvent, String rawName){
        super(soundEvent, SoundCategory.VOICE);
        this.volume = config.blockCutOff / 16f;
        this.rawName = rawName;
    }


    @Override
    public void tick() {
        if (this.rawName.isEmpty()) {
            return;
        }
        Vec3d vector = NPCHandler.find(rawName);
        if (vector != null) {
            this.x = (float) vector.x;
            this.z = (float) vector.z;
            this.y = (float) vector.y;

            if (config.highlightSpeaker) {
                ClientPlayerEntity p = MinecraftClient.getInstance().player;
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
}
