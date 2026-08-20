/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.physics;

/**
 * How one block behaves acoustically.
 *
 * <p>{@code reflectivity}, {@code hfDamping} and {@code scatter} are fractions in [0, 1].
 * {@code occlusion} is measured in "occlusion units per block of thickness", so it can exceed 1 for
 * unusually dense blocks; {@link OcclusionModel} turns accumulated units into filter gains.
 */
public record AcousticMaterial(float reflectivity, float hfDamping, float scatter, float occlusion, Mode mode) {
    public enum Mode {
        /** Sound passes through untouched. Air, and barrier blocks. */
        TRANSPARENT,
        /** Sound passes through but loses energy doing so. Panes, carpets, water. */
        PASS_THROUGH,
        /** Sound bounces off. Everything properly solid. */
        REFLECTOR
    }

    /** Acoustically invisible. A ray walks straight through and is charged nothing. */
    public static final AcousticMaterial TRANSPARENT = new AcousticMaterial(0f, 1f, 0f, 0f, Mode.TRANSPARENT);

    public boolean isTransparent() {
        return mode == Mode.TRANSPARENT;
    }

    public boolean isReflector() {
        return mode == Mode.REFLECTOR;
    }

    /**
     * The same material as a reflector, with its occlusion scaled by how much of its cube the block
     * actually fills. A slab occludes half as much as the full block it is cut from.
     */
    public AcousticMaterial asReflector(float occupancy) {
        return new AcousticMaterial(reflectivity, hfDamping, scatter, occlusion * occupancy, Mode.REFLECTOR);
    }

    /**
     * The same material as a pass-through. Reflectivity and scatter go to zero because a ray never
     * bounces off one of these; only the occlusion it charges on the way through matters.
     */
    public AcousticMaterial asPassThrough(float occupancy) {
        return new AcousticMaterial(0f, hfDamping, 0f, occlusion * occupancy, Mode.PASS_THROUGH);
    }
}
