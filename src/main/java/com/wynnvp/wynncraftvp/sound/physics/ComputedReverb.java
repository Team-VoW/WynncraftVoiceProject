/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.physics;

/**
 * A full set of OpenAL {@code AL_REVERB_*} values, as plain numbers.
 *
 * <p>Field order matches {@code ReverbPresets.ReverbParams} so the hand-authored presets and the
 * traced result converge on one applier. This record deliberately imports nothing: the whole physics
 * model stays testable without OpenAL on the classpath, let alone its natives.
 *
 * <p>Every value is clamped to its EFX-legal range on construction, so nothing downstream has to
 * re-check and a mapping bug shows up as a wrong-but-legal sound rather than a silent AL error.
 */
public record ComputedReverb(
        float density,
        float diffusion,
        float gain,
        float gainHf,
        float decayTime,
        float decayHfRatio,
        float reflectionsGain,
        float reflectionsDelay,
        float lateReverbGain,
        float lateReverbDelay,
        float airAbsorptionGainHf,
        float roomRolloffFactor,
        int decayHfLimit) {
    public static final float MIN_DECAY_TIME = 0.1f;
    public static final float MAX_DECAY_TIME = 20.0f;
    public static final float MIN_DECAY_HF_RATIO = 0.1f;
    public static final float MAX_DECAY_HF_RATIO = 2.0f;
    public static final float MAX_REFLECTIONS_GAIN = 3.16f;
    public static final float MAX_REFLECTIONS_DELAY = 0.3f;
    public static final float MAX_LATE_REVERB_GAIN = 10.0f;
    public static final float MAX_LATE_REVERB_DELAY = 0.1f;
    public static final float MIN_AIR_ABSORPTION_GAIN_HF = 0.892f;
    public static final float MAX_ROOM_ROLLOFF_FACTOR = 10.0f;

    public ComputedReverb {
        density = unit(density);
        diffusion = unit(diffusion);
        gain = unit(gain);
        gainHf = unit(gainHf);
        decayTime = bounded(decayTime, MIN_DECAY_TIME, MAX_DECAY_TIME, MIN_DECAY_TIME);
        decayHfRatio = bounded(decayHfRatio, MIN_DECAY_HF_RATIO, MAX_DECAY_HF_RATIO, 1f);
        reflectionsGain = bounded(reflectionsGain, 0f, MAX_REFLECTIONS_GAIN, 0f);
        reflectionsDelay = bounded(reflectionsDelay, 0f, MAX_REFLECTIONS_DELAY, 0f);
        lateReverbGain = bounded(lateReverbGain, 0f, MAX_LATE_REVERB_GAIN, 0f);
        lateReverbDelay = bounded(lateReverbDelay, 0f, MAX_LATE_REVERB_DELAY, 0f);
        airAbsorptionGainHf = bounded(airAbsorptionGainHf, MIN_AIR_ABSORPTION_GAIN_HF, 1f, 0.994f);
        roomRolloffFactor = bounded(roomRolloffFactor, 0f, MAX_ROOM_ROLLOFF_FACTOR, 0f);
        decayHfLimit = decayHfLimit == 0 ? 0 : 1;
    }

    private static float unit(float value) {
        return bounded(value, 0f, 1f, 0f);
    }

    private static float bounded(float value, float min, float max, float fallback) {
        if (Float.isNaN(value) || Float.isInfinite(value)) {
            return fallback;
        }
        return Math.clamp(value, min, max);
    }
}
