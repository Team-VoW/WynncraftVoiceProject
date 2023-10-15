package com.wynnvp.wynncraftvp.sound.at;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import static com.wynnvp.wynncraftvp.ModCore.config;

public class SoundHighlight {

    public static void ShowParticles(Vec3 position) {
        if (config.isHighlightSpeaker()) {
            ShowDefaultParticles(position);
        }
    }

    private static void ShowDefaultParticles(Vec3 position) {
        Player p = Minecraft.getInstance().player;
        assert p != null;

        var level = Minecraft.getInstance().level;

        for (double x = position.x - 0.2; x <= position.x + 0.2; x += 0.1) {
            for (double y = position.y - 0.2; y <= position.y + 0.2; y += 0.1) {
                for (double z = position.z - 0.2; z <= position.z + 0.2; z += 0.1) {
                    level.addParticle(ParticleTypes.END_ROD, x, y, z, 0, 0, 0);
                }
            }
        }
    }
}
