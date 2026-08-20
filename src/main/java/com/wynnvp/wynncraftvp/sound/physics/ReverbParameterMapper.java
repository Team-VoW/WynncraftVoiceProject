/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.physics;

/**
 * Turns what the trace measured into what a reverb unit wants to be told.
 *
 * <p>The decay time is Sabine's equation, not a tuned curve. Sabine gives
 * {@code RT60 = 0.161 * V / (S * a)}, and the mean free path of a room is {@code MFP = 4V/S}, so
 * substituting leaves {@code RT60 = 0.04025 * MFP / a} - both terms of which the trace measures
 * directly. That it lands on top of the hand-authored presets (a small wooden room at 0.5s against
 * FURNISHED_ROOM's 0.4s, a 10x10x10 wooden hall at 1.07s against EMPTY_ROOM's 1.1s) is the main
 * evidence the constants here are right rather than merely plausible.
 */
public final class ReverbParameterMapper {
    /** Metres per second. Also blocks per second, since a block is a metre. */
    public static final float SPEED_OF_SOUND = 343.0f;

    /**
     * Sabine's constant folded through {@code MFP = 4V/S}. {@code 0.161 / 4}.
     */
    private static final float SABINE_MFP = 0.04025f;

    /** What an unenclosed space decays like. Open air has no reverberant field to speak of. */
    private static final float OPEN_DECAY_TIME = 1.10f;

    /**
     * Real stone absorbs a few percent, so reflectivity runs high and absorption runs low. Flooring
     * absorption here stops a large hard room mapping to an absurd decay time.
     */
    private static final float MIN_ABSORPTION = 0.04f;

    /** EFX allows 20s; anything past this stops sounding like a room and starts sounding broken. */
    private static final float MAX_MODELLED_DECAY = 8.0f;

    private ReverbParameterMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * @param strength user scale on wetness. It touches the three gains only - a room does not become
     *     a smaller room because someone turned the reverb down.
     */
    public static ComputedReverb map(AcousticSample s, float strength) {
        float wet = Math.clamp(strength, 0f, 2f);

        float absorption = Math.clamp(1f - s.meanReflectivity(), MIN_ABSORPTION, 1f);
        float rt60Enclosed = SABINE_MFP * s.meanFreePath() / absorption;
        float enclosure = s.enclosure();
        float hfRatio = s.hfRatio();

        // Blend toward open air by the square root of enclosure: a room with one wall missing still
        // rings a good deal, so the falloff should not be linear in how much of it is left.
        float decayTime = lerp(OPEN_DECAY_TIME, rt60Enclosed, (float) Math.sqrt(enclosure));
        decayTime = Math.clamp(decayTime, 0.15f, MAX_MODELLED_DECAY);

        // Square root because loudness tracks amplitude, not energy.
        float gain = Math.clamp(0.05f + 0.55f * (float) Math.sqrt(s.reflectedEnergy()), 0f, 0.70f);
        float gainHf = Math.clamp(0.30f + 0.70f * hfRatio, 0.10f, 1.0f);
        float decayHfRatio = Math.clamp(0.40f + 1.10f * hfRatio, 0.10f, 2.0f);

        // Modal density rises with room size. Diffusion is driven mainly by how rough the surfaces
        // are, since that is what actually scatters a reflection, with a smaller contribution from how
        // uneven the space is: a cave whose chords vary wildly smears reflections out in time, while a
        // bare box returns them coherently.
        float density = Math.clamp(s.meanFreePath() / 20f, 0.35f, 1.0f);
        float diffusion = Math.clamp(
                0.40f + 0.50f * s.meanScatter() + 0.20f * Math.clamp(s.pathVariation(), 0f, 1f), 0.10f, 1.0f);

        float reflectionsGain = Math.clamp(0.05f + 1.20f * s.earlyEnergy(), 0f, 1.0f);
        float reflectionsDelay = Math.clamp(s.earlyDelaySeconds(), 0f, 0.10f);

        float lateEnergy = Math.max(0f, s.reflectedEnergy() - s.earlyEnergy());
        float lateReverbGain = Math.clamp(0.10f + 1.40f * lateEnergy, 0f, 1.6f);
        float lateReverbDelay = Math.clamp(s.lateDelaySeconds() - reflectionsDelay, 0f, 0.05f);

        return new ComputedReverb(
                density,
                diffusion,
                Math.clamp(gain * wet, 0f, 1f),
                gainHf,
                decayTime,
                decayHfRatio,
                Math.clamp(reflectionsGain * wet, 0f, ComputedReverb.MAX_REFLECTIONS_GAIN),
                reflectionsDelay,
                Math.clamp(lateReverbGain * wet, 0f, ComputedReverb.MAX_LATE_REVERB_GAIN),
                lateReverbDelay,
                0.994f,
                // Distance attenuation is disabled mod-wide, so a room rolloff would fight it.
                0f,
                // Letting the decay run brighter than the reference band only makes sense when the
                // surfaces really are that reflective, which is exactly what a hard cave measures.
                decayHfRatio > 1.0f ? 0 : 1);
    }

    private static float lerp(float from, float to, float t) {
        return from + (to - from) * t;
    }
}
