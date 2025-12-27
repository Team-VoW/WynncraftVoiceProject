/*
 * Copyright © Team-VoW 2024-2025.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.player;

import com.wynnvp.wynncraftvp.sound.Reverb;
import org.lwjgl.openal.EXTEfx;

/**
 * Provides OpenAL EFX reverb parameter presets for different environment types.
 * Each preset is tuned to simulate realistic acoustic properties.
 */
public class ReverbPresets {
    public static class ReverbParams {
        public final float density;
        public final float diffusion;
        public final float gain;
        public final float gainHF;
        public final float decayTime;
        public final float decayHFRatio;
        public final float reflectionsGain;
        public final float reflectionsDelay;
        public final float lateReverbGain;
        public final float lateReverbDelay;
        public final float airAbsorptionGainHF;
        public final float roomRolloffFactor;
        public final int decayHFLimit;

        public ReverbParams(
                float density,
                float diffusion,
                float gain,
                float gainHF,
                float decayTime,
                float decayHFRatio,
                float reflectionsGain,
                float reflectionsDelay,
                float lateReverbGain,
                float lateReverbDelay,
                float airAbsorptionGainHF,
                float roomRolloffFactor,
                int decayHFLimit) {
            this.density = density;
            this.diffusion = diffusion;
            this.gain = gain;
            this.gainHF = gainHF;
            this.decayTime = decayTime;
            this.decayHFRatio = decayHFRatio;
            this.reflectionsGain = reflectionsGain;
            this.reflectionsDelay = reflectionsDelay;
            this.lateReverbGain = lateReverbGain;
            this.lateReverbDelay = lateReverbDelay;
            this.airAbsorptionGainHF = airAbsorptionGainHF;
            this.roomRolloffFactor = roomRolloffFactor;
            this.decayHFLimit = decayHFLimit;
        }

        /**
         * Applies these reverb parameters to an OpenAL effect object.
         */
        public void applyToEffect(int effect) {
            EXTEfx.alEffectf(effect, EXTEfx.AL_REVERB_DENSITY, density);
            EXTEfx.alEffectf(effect, EXTEfx.AL_REVERB_DIFFUSION, diffusion);
            EXTEfx.alEffectf(effect, EXTEfx.AL_REVERB_GAIN, gain);
            EXTEfx.alEffectf(effect, EXTEfx.AL_REVERB_GAINHF, gainHF);
            EXTEfx.alEffectf(effect, EXTEfx.AL_REVERB_DECAY_TIME, decayTime);
            EXTEfx.alEffectf(effect, EXTEfx.AL_REVERB_DECAY_HFRATIO, decayHFRatio);
            EXTEfx.alEffectf(effect, EXTEfx.AL_REVERB_REFLECTIONS_GAIN, reflectionsGain);
            EXTEfx.alEffectf(effect, EXTEfx.AL_REVERB_REFLECTIONS_DELAY, reflectionsDelay);
            EXTEfx.alEffectf(effect, EXTEfx.AL_REVERB_LATE_REVERB_GAIN, lateReverbGain);
            EXTEfx.alEffectf(effect, EXTEfx.AL_REVERB_LATE_REVERB_DELAY, lateReverbDelay);
            EXTEfx.alEffectf(effect, EXTEfx.AL_REVERB_AIR_ABSORPTION_GAINHF, airAbsorptionGainHF);
            EXTEfx.alEffectf(effect, EXTEfx.AL_REVERB_ROOM_ROLLOFF_FACTOR, roomRolloffFactor);
            EXTEfx.alEffecti(effect, EXTEfx.AL_REVERB_DECAY_HFLIMIT, decayHFLimit);
        }
    }

    // Preset definitions based on realistic acoustic environments

    /**
     * OUTSIDE - Minimal reverb, open space with high absorption
     */
    private static final ReverbParams OUTSIDE = new ReverbParams(
            1.0f, // density
            1.0f, // diffusion
            0.316f, // gain
            0.447f, // gainHF (significant high frequency rolloff for outdoor)
            1.5f, // decayTime (very short)
            0.5f, // decayHFRatio
            0.05f, // reflectionsGain (minimal reflections)
            0.007f, // reflectionsDelay
            0.251f, // lateReverbGain
            0.011f, // lateReverbDelay
            0.994f, // airAbsorptionGainHF
            0.0f, // roomRolloffFactor
            1 // decayHFLimit
            );

    /**
     * FOREST - Outdoor with trees dampening sound
     */
    private static final ReverbParams FOREST = new ReverbParams(
            1.0f, // density
            0.85f, // diffusion (trees scatter sound irregularly)
            0.316f, // gain
            0.562f, // gainHF (moderate high frequency absorption from foliage)
            1.2f, // decayTime (slightly dampened outdoor)
            0.6f, // decayHFRatio
            0.1f, // reflectionsGain (some tree reflections)
            0.01f, // reflectionsDelay
            0.2f, // lateReverbGain
            0.015f, // lateReverbDelay
            0.97f, // airAbsorptionGainHF (foliage absorbs)
            0.0f, // roomRolloffFactor
            1 // decayHFLimit
            );

    /**
     * FURNISHED_ROOM - Small indoor space with furniture absorption
     */
    private static final ReverbParams FURNISHED_ROOM = new ReverbParams(
            1.0f, // density
            1.0f, // diffusion
            0.316f, // gain
            0.562f, // gainHF
            0.4f, // decayTime (short, furniture absorbs sound)
            0.83f, // decayHFRatio
            0.15f, // reflectionsGain
            0.002f, // reflectionsDelay
            0.1f, // lateReverbGain
            0.003f, // lateReverbDelay
            0.994f, // airAbsorptionGainHF
            0.0f, // roomRolloffFactor
            1 // decayHFLimit
            );

    /**
     * EMPTY_ROOM - Small indoor space with hard, reflective surfaces
     */
    private static final ReverbParams EMPTY_ROOM = new ReverbParams(
            1.0f, // density
            1.0f, // diffusion
            0.316f, // gain
            0.794f, // gainHF (less high frequency absorption)
            1.1f, // decayTime (moderate)
            0.89f, // decayHFRatio
            0.4f, // reflectionsGain (strong reflections)
            0.002f, // reflectionsDelay
            0.5f, // lateReverbGain
            0.003f, // lateReverbDelay
            0.994f, // airAbsorptionGainHF
            0.0f, // roomRolloffFactor
            1 // decayHFLimit
            );

    /**
     * HALLWAY - Long narrow corridor with directional echo
     */
    private static final ReverbParams HALLWAY = new ReverbParams(
            1.0f, // density
            0.9f, // diffusion (one-dimensional reflections)
            0.316f, // gain
            0.707f, // gainHF
            0.8f, // decayTime (quick but present echo)
            0.75f, // decayHFRatio
            0.3f, // reflectionsGain (strong early reflections)
            0.005f, // reflectionsDelay
            0.35f, // lateReverbGain
            0.008f, // lateReverbDelay
            0.994f, // airAbsorptionGainHF
            0.0f, // roomRolloffFactor
            1 // decayHFLimit
            );

    /**
     * LARGE_HALL - Throne room, banquet hall, or large meeting chamber
     */
    private static final ReverbParams LARGE_HALL = new ReverbParams(
            1.0f, // density
            1.0f, // diffusion
            0.316f, // gain
            0.871f, // gainHF
            3.5f, // decayTime (substantial space)
            0.92f, // decayHFRatio
            0.45f, // reflectionsGain
            0.025f, // reflectionsDelay (large room)
            0.85f, // lateReverbGain
            0.035f, // lateReverbDelay
            0.994f, // airAbsorptionGainHF
            0.0f, // roomRolloffFactor
            1 // decayHFLimit
            );

    /**
     * SMALL_CAVE - Confined underground space with reflective stone walls
     */
    private static final ReverbParams SMALL_CAVE = new ReverbParams(
            1.0f, // density
            1.0f, // diffusion
            0.316f, // gain
            0.891f, // gainHF
            2.13f, // decayTime (longer than room)
            0.79f, // decayHFRatio
            0.5f, // reflectionsGain
            0.015f, // reflectionsDelay
            0.7f, // lateReverbGain
            0.022f, // lateReverbDelay
            0.994f, // airAbsorptionGainHF
            0.0f, // roomRolloffFactor
            0 // decayHFLimit
            );

    /**
     * CAVE - Medium underground cavern with highly reflective surfaces
     */
    private static final ReverbParams CAVE = new ReverbParams(
            1.0f, // density
            1.0f, // diffusion
            0.316f, // gain
            1.0f, // gainHF (full high frequency preservation)
            2.91f, // decayTime (long)
            1.3f, // decayHFRatio
            0.5f, // reflectionsGain
            0.015f, // reflectionsDelay
            0.706f, // lateReverbGain
            0.022f, // lateReverbDelay
            0.994f, // airAbsorptionGainHF
            0.0f, // roomRolloffFactor
            0 // decayHFLimit
            );

    /**
     * BIG_CAVE - Large underground cavern with extensive reverb
     */
    private static final ReverbParams BIG_CAVE = new ReverbParams(
            1.0f, // density
            1.0f, // diffusion
            0.316f, // gain
            1.0f, // gainHF
            4.32f, // decayTime (very long)
            1.5f, // decayHFRatio
            0.5f, // reflectionsGain
            0.03f, // reflectionsDelay (longer for larger space)
            0.8f, // lateReverbGain
            0.039f, // lateReverbDelay
            0.994f, // airAbsorptionGainHF
            0.0f, // roomRolloffFactor
            0 // decayHFLimit
            );

    /**
     * CATHEDRAL - Massive enclosed space with extremely long reverb tail
     */
    private static final ReverbParams CATHEDRAL = new ReverbParams(
            1.0f, // density
            1.0f, // diffusion
            0.316f, // gain
            0.891f, // gainHF
            7.24f, // decayTime (extremely long)
            0.87f, // decayHFRatio
            0.5f, // reflectionsGain
            0.056f, // reflectionsDelay (large space delay)
            1.26f, // lateReverbGain (prominent reverb tail)
            0.024f, // lateReverbDelay
            0.994f, // airAbsorptionGainHF
            0.0f, // roomRolloffFactor
            1 // decayHFLimit
            );

    /**
     * Gets the reverb parameters for a given environment type.
     */
    public static ReverbParams getParams(Reverb reverb) {
        return switch (reverb) {
            case OUTSIDE -> OUTSIDE;
            case FOREST -> FOREST;
            case FURNISHED_ROOM -> FURNISHED_ROOM;
            case EMPTY_ROOM -> EMPTY_ROOM;
            case HALLWAY -> HALLWAY;
            case LARGE_HALL -> LARGE_HALL;
            case SMALL_CAVE -> SMALL_CAVE;
            case CAVE -> CAVE;
            case BIG_CAVE -> BIG_CAVE;
            case CATHEDRAL -> CATHEDRAL;
        };
    }
}
