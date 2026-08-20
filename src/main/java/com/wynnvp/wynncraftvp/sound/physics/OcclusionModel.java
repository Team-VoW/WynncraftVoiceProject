/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.physics;

/**
 * Turns "how much material is in the way" into a low-pass filter on the direct path.
 *
 * <p>Transmission through a wall is exponential in its thickness (Beer-Lambert), so the gains are
 * powers of a per-unit base rather than a hand-drawn curve. High frequencies die far faster than low
 * ones, which is what makes a muffled voice read as "through a wall" rather than "quieter".
 *
 * <p>The floors matter as much as the curve. Voices of Wynn deliberately runs with distance
 * attenuation disabled so a player never misses a quest line, and uncapped occlusion would undo that
 * -- three blocks of stone would swallow the dialogue entirely. Clamping at
 * {@value #MIN_GAIN}/{@value #MIN_GAINHF} keeps a heavily occluded voice clearly muffled but still
 * intelligible, no matter how much rock is between the two of you.
 */
public final class OcclusionModel {
    /** Broadband transmission per occlusion unit. */
    public static final float GAIN_BASE = 0.80f;
    /** High-band transmission per occlusion unit. Walls eat treble first. */
    public static final float GAINHF_BASE = 0.45f;

    /** Never attenuate the direct path below this, however thick the wall. */
    public static final float MIN_GAIN = 0.55f;
    /** Never dull the direct path below this. */
    public static final float MIN_GAINHF = 0.15f;

    /** Beyond this the clamps have long since taken over, so the tracer can stop walking. */
    public static final float SATURATION_UNITS = 6.0f;

    /** A low-pass filter setting for the direct path. */
    public record Occlusion(float gain, float gainHf) {
        /** Nothing in the way. */
        public static final Occlusion NONE = new Occlusion(1f, 1f);

        public boolean isAudible() {
            return gain < 0.999f || gainHf < 0.999f;
        }
    }

    private OcclusionModel() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * @param units occlusion accumulated along the direct path
     * @param strength user scale in [0, 1]; 0 disables muffling entirely
     */
    public static Occlusion compute(float units, float strength) {
        if (!(units > 0f) || !(strength > 0f)) {
            return Occlusion.NONE;
        }
        float clamped = Math.clamp(strength, 0f, 1f);
        float gain = Math.max(MIN_GAIN, (float) Math.pow(GAIN_BASE, units));
        float gainHf = Math.max(MIN_GAINHF, (float) Math.pow(GAINHF_BASE, units));
        // Blend back toward "no wall" so the slider scales the effect rather than the wall.
        return new Occlusion(1f - (1f - gain) * clamped, 1f - (1f - gainHf) * clamped);
    }
}
