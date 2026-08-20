/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.physics;

/**
 * Everything the tracer needs to know that is not geometry.
 *
 * @param rayCount rays per evaluation, from the quality slider
 * @param maxBounces how many times one ray may reflect
 * @param maxSegment longest single segment, in blocks
 * @param maxTotalPath longest total path for one ray, in blocks
 * @param reverbStrength user scale on wetness, 1.0 being the physically modelled amount
 * @param occlusionStrength user scale on muffling, 0 disabling it entirely
 */
public record AcousticSettings(
        int rayCount,
        int maxBounces,
        double maxSegment,
        double maxTotalPath,
        float reverbStrength,
        float occlusionStrength) {
    public static final int MIN_RAYS = 8;
    public static final int MAX_RAYS = 96;
    public static final int DEFAULT_RAYS = 48;

    private static final double DEFAULT_MAX_SEGMENT = 24.0;
    private static final double DEFAULT_MAX_TOTAL_PATH = 96.0;

    /** Beyond this the space around the speaker tells us nothing useful about what we hear. */
    public static final double MAX_SPEAKER_DISTANCE = 64.0;

    public AcousticSettings {
        rayCount = Math.clamp(rayCount, MIN_RAYS, MAX_RAYS);
        maxBounces = Math.clamp(maxBounces, 2, 5);
        reverbStrength = Math.clamp(reverbStrength, 0f, 2f);
        occlusionStrength = Math.clamp(occlusionStrength, 0f, 1f);
    }

    /**
     * Settings for a given ray budget. Bounce depth scales with the ray count so that turning quality
     * down shortens the trace in both dimensions rather than only thinning it.
     */
    public static AcousticSettings of(int rayCount, float reverbStrength, float occlusionStrength) {
        int rays = Math.clamp(rayCount, MIN_RAYS, MAX_RAYS);
        return new AcousticSettings(
                rays,
                Math.clamp(2 + rays / 24, 2, 5),
                DEFAULT_MAX_SEGMENT,
                DEFAULT_MAX_TOTAL_PATH,
                reverbStrength,
                occlusionStrength);
    }
}
