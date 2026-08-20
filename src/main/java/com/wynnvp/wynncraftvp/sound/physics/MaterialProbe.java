/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.physics;

/** The only thing {@link VoxelRayTracer} knows about the world. */
@FunctionalInterface
public interface MaterialProbe {
    /**
     * The material at a block position, or {@code null} when that position is outside the sampled
     * world — an unloaded chunk, or past the build limit.
     *
     * <p>{@code null} means the ray left the world, which acoustically is open air. It must never be
     * read as a solid surface: doing so would seal every speaker near a render-distance edge into an
     * imaginary box and give them cathedral reverb outdoors.
     */
    AcousticMaterial materialAt(int x, int y, int z);
}
