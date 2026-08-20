/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.physics;

import java.util.HashMap;
import java.util.Map;

/**
 * So the tracer can be tested without Minecraft.
 *
 * <p>Everything not explicitly filled is air. Set {@link #voidOutside} to make positions outside a
 * box read as {@code null} instead, which is how the real probe reports an unloaded chunk.
 */
final class FakeWorld implements MaterialProbe {
    static final AcousticMaterial STONE =
            new AcousticMaterial(0.92f, 0.95f, 0.25f, 1.0f, AcousticMaterial.Mode.REFLECTOR);
    static final AcousticMaterial WOOL =
            new AcousticMaterial(0.28f, 0.35f, 0.65f, 0.90f, AcousticMaterial.Mode.REFLECTOR);
    static final AcousticMaterial WATER =
            new AcousticMaterial(0f, 0.90f, 0f, 0.20f, AcousticMaterial.Mode.PASS_THROUGH);
    /** What a barrier block resolves to: present in the world, invisible to sound. */
    static final AcousticMaterial BARRIER = AcousticMaterial.TRANSPARENT;

    private final Map<Long, AcousticMaterial> blocks = new HashMap<>();
    private boolean voidOutside;
    private int voidMin;
    private int voidMax;

    /** Positions outside the inclusive cube read as {@code null}, mimicking unloaded chunks. */
    FakeWorld voidOutside(int min, int max) {
        this.voidOutside = true;
        this.voidMin = min;
        this.voidMax = max;
        return this;
    }

    FakeWorld fill(int x0, int y0, int z0, int x1, int y1, int z1, AcousticMaterial material) {
        for (int x = x0; x <= x1; x++) {
            for (int y = y0; y <= y1; y++) {
                for (int z = z0; z <= z1; z++) {
                    blocks.put(key(x, y, z), material);
                }
            }
        }
        return this;
    }

    /**
     * A hollow cube whose interior runs from 1 to {@code interior} on every axis, walled on all six
     * sides. The interior is therefore {@code interior} blocks across.
     */
    static FakeWorld sealedBox(int interior, AcousticMaterial wall) {
        FakeWorld world = new FakeWorld();
        int outer = interior + 1;
        world.fill(0, 0, 0, outer, outer, 0, wall);
        world.fill(0, 0, outer, outer, outer, outer, wall);
        world.fill(0, 0, 0, 0, outer, outer, wall);
        world.fill(outer, 0, 0, outer, outer, outer, wall);
        world.fill(0, 0, 0, outer, 0, outer, wall);
        world.fill(0, outer, 0, outer, outer, outer, wall);
        return world;
    }

    /** The centre of a {@link #sealedBox(int, AcousticMaterial)} interior. */
    static double boxCentre(int interior) {
        return 1 + interior / 2.0;
    }

    FakeWorld clear(int x0, int y0, int z0, int x1, int y1, int z1) {
        for (int x = x0; x <= x1; x++) {
            for (int y = y0; y <= y1; y++) {
                for (int z = z0; z <= z1; z++) {
                    blocks.remove(key(x, y, z));
                }
            }
        }
        return this;
    }

    @Override
    public AcousticMaterial materialAt(int x, int y, int z) {
        if (voidOutside && (outside(x) || outside(y) || outside(z))) {
            return null;
        }
        return blocks.getOrDefault(key(x, y, z), AcousticMaterial.TRANSPARENT);
    }

    private boolean outside(int v) {
        return v < voidMin || v > voidMax;
    }

    private static long key(int x, int y, int z) {
        return ((long) (x & 0xFFFFF) << 40) | ((long) (y & 0xFFFFF) << 20) | (z & 0xFFFFF);
    }
}
