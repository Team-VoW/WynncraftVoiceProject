/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.physics;

/**
 * What a full trace measured about the space around a speaker.
 *
 * <p>These are raw physical observations, deliberately kept separate from the OpenAL parameters
 * {@link ReverbParameterMapper} derives from them: the trace is about the world, the mapping is about
 * how a reverb unit wants to be told about it.
 *
 * @param openness fraction of emitted energy that left the space, 0 sealed to 1 open field
 * @param meanFreePath average distance between surfaces, in blocks - the room-size proxy
 * @param pathVariation standard deviation over mean of the segment lengths, so how uneven the space
 *     is. Measured rather than assumed: a long corridor scores lower than a cube, because most of its
 *     chords are the same short cross-section.
 * @param reflectedEnergy fraction of emitted energy that came back to the listener
 * @param reflectedEnergyHf the same, in the high band, so HF character comes out of the trace rather
 *     than being guessed from average reflectivity
 * @param meanReflectivity average reflectivity of the surfaces actually hit
 * @param meanScatter average roughness of those surfaces
 * @param earlyEnergy the part of {@code reflectedEnergy} that arrived on the first bounce
 * @param earlyDelaySeconds energy-weighted arrival time of the first-bounce reflections
 * @param lateDelaySeconds energy-weighted arrival time of everything after the first bounce
 * @param occlusionUnits material between speaker and listener on the direct path
 * @param raysTraced how many rays produced this sample
 */
public record AcousticSample(
        float openness,
        float meanFreePath,
        float pathVariation,
        float reflectedEnergy,
        float reflectedEnergyHf,
        float meanReflectivity,
        float meanScatter,
        float earlyEnergy,
        float earlyDelaySeconds,
        float lateDelaySeconds,
        float occlusionUnits,
        int raysTraced) {
    /** What an empty sky looks like: everything escaped, nothing came back. */
    public static final AcousticSample OPEN_FIELD = new AcousticSample(1f, 24f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0);

    /**
     * Several of these metrics are ratios that divide by an energy sum, so a trace that hit nothing
     * produces 0/0. Normalising here means every consumer can treat the record as clean numbers.
     */
    public AcousticSample {
        openness = clean(openness, 0f, 1f, 1f);
        meanFreePath = clean(meanFreePath, 0f, 256f, 0f);
        pathVariation = clean(pathVariation, 0f, 8f, 0f);
        reflectedEnergy = clean(reflectedEnergy, 0f, 1f, 0f);
        reflectedEnergyHf = clean(reflectedEnergyHf, 0f, 1f, 0f);
        meanReflectivity = clean(meanReflectivity, 0f, 1f, 0f);
        meanScatter = clean(meanScatter, 0f, 1f, 0f);
        earlyEnergy = clean(earlyEnergy, 0f, 1f, 0f);
        earlyDelaySeconds = clean(earlyDelaySeconds, 0f, 2f, 0f);
        lateDelaySeconds = clean(lateDelaySeconds, 0f, 2f, 0f);
        occlusionUnits = clean(occlusionUnits, 0f, 64f, 0f);
        raysTraced = Math.max(0, raysTraced);
    }

    /** The high-band share of what came back, which drives every HF parameter downstream. */
    public float hfRatio() {
        if (reflectedEnergy <= 1e-4f) {
            return 0f;
        }
        return Math.clamp(reflectedEnergyHf / reflectedEnergy, 0f, 1f);
    }

    /** How enclosed the space is. The complement of {@link #openness}. */
    public float enclosure() {
        return 1f - openness;
    }

    private static float clean(float value, float min, float max, float fallback) {
        if (Float.isNaN(value) || Float.isInfinite(value)) {
            return fallback;
        }
        return Math.clamp(value, min, max);
    }
}
