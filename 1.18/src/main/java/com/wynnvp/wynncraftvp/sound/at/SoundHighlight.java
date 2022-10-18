package com.wynnvp.wynncraftvp.sound.at;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;

import static com.wynnvp.wynncraftvp.ModCore.config;

public class SoundHighlight {

    public static void ShowParticles(Vec3d position){
        if (config.isHighlightSpeaker()) {
            ShowDefaultParticles(position);
        }
    }

    private static void ShowDefaultParticles(Vec3d position){
        ClientPlayerEntity p = MinecraftClient.getInstance().player;
        assert p != null;

        for (double x = position.x - 0.2; x <= position.x + 0.2; x += 0.1) {
            for (double y = position.y - 0.2; y <= position.y + 0.2; y += 0.1) {
                for (double z = position.z - 0.2; z <= position.z + 0.2; z += 0.1) {
                    p.clientWorld.addParticle(ParticleTypes.END_ROD, x, y, z, 0, 0, 0);
                }
            }
        }
    }
}
