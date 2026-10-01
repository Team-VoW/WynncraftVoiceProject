/*
 * Copyright © Team-VoW 2024-2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.player;

import com.wynnvp.wynncraftvp.sound.Reverb;
import com.wynnvp.wynncraftvp.sound.physics.ComputedReverb;

/**
 * Hand-authored reverb settings for the ten named environments.
 *
 * <p>These are the override path. A line carrying an explicit {@code "reverb"} in {@code sounds.json}
 * uses the preset named there verbatim; everything else is measured from the world by
 * {@link com.wynnvp.wynncraftvp.sound.physics.ReverbParameterMapper}. They are also the reference the
 * traced model was checked against - a small wooden room measures at roughly
 * {@link #FURNISHED_ROOM}, a ten block hall at roughly {@link #EMPTY_ROOM}.
 */
public final class ReverbPresets {
    private ReverbPresets() {
        throw new UnsupportedOperationException("Utility class");
    }

    /** OUTSIDE - minimal reverb, open space with high absorption. */
    private static final ComputedReverb OUTSIDE =
            new ComputedReverb(1.0f, 1.0f, 0.316f, 0.447f, 1.5f, 0.5f, 0.05f, 0.007f, 0.251f, 0.011f, 0.994f, 0.0f, 1);

    /** FOREST - outdoors, with trees scattering and foliage absorbing. */
    private static final ComputedReverb FOREST =
            new ComputedReverb(1.0f, 0.85f, 0.316f, 0.562f, 1.2f, 0.6f, 0.1f, 0.01f, 0.2f, 0.015f, 0.97f, 0.0f, 1);

    /** FURNISHED_ROOM - small indoor space, furniture soaking up the tail. */
    private static final ComputedReverb FURNISHED_ROOM =
            new ComputedReverb(1.0f, 1.0f, 0.316f, 0.562f, 0.4f, 0.83f, 0.15f, 0.002f, 0.1f, 0.003f, 0.994f, 0.0f, 1);

    /** EMPTY_ROOM - small indoor space with hard, reflective surfaces. */
    private static final ComputedReverb EMPTY_ROOM =
            new ComputedReverb(1.0f, 1.0f, 0.316f, 0.794f, 1.1f, 0.89f, 0.4f, 0.002f, 0.5f, 0.003f, 0.994f, 0.0f, 1);

    /** HALLWAY - long narrow corridor with directional echo. */
    private static final ComputedReverb HALLWAY =
            new ComputedReverb(1.0f, 0.9f, 0.316f, 0.707f, 0.8f, 0.75f, 0.3f, 0.005f, 0.35f, 0.008f, 0.994f, 0.0f, 1);

    /** LARGE_HALL - throne room, banquet hall, or large meeting chamber. */
    private static final ComputedReverb LARGE_HALL =
            new ComputedReverb(1.0f, 1.0f, 0.316f, 0.871f, 3.5f, 0.92f, 0.45f, 0.025f, 0.85f, 0.035f, 0.994f, 0.0f, 1);

    /** SMALL_CAVE - confined underground space with reflective stone walls. */
    private static final ComputedReverb SMALL_CAVE =
            new ComputedReverb(1.0f, 1.0f, 0.316f, 0.891f, 2.13f, 0.79f, 0.5f, 0.015f, 0.7f, 0.022f, 0.994f, 0.0f, 0);

    /** CAVE - medium underground cavern with highly reflective surfaces. */
    private static final ComputedReverb CAVE =
            new ComputedReverb(1.0f, 1.0f, 0.316f, 1.0f, 2.91f, 1.3f, 0.5f, 0.015f, 0.706f, 0.022f, 0.994f, 0.0f, 0);

    /** BIG_CAVE - large underground cavern with extensive reverb. */
    private static final ComputedReverb BIG_CAVE =
            new ComputedReverb(1.0f, 1.0f, 0.316f, 1.0f, 4.32f, 1.5f, 0.5f, 0.03f, 0.8f, 0.039f, 0.994f, 0.0f, 0);

    /** CATHEDRAL - massive enclosed space with an extremely long tail. */
    private static final ComputedReverb CATHEDRAL =
            new ComputedReverb(1.0f, 1.0f, 0.316f, 0.891f, 7.24f, 0.87f, 0.5f, 0.056f, 1.26f, 0.024f, 0.994f, 0.0f, 1);

    /**
     * VOID_CATHEDRAL - an intense supernatural aura with a short, smooth, dark tail.
     *
     * <p>Unlike {@link #CATHEDRAL}, this is not meant to imply a physical room. High diffusion and a
     * strong late return create scale around a voice, while the shorter decay and subdued high band
     * avoid the metallic, boxy quality of closely spaced reflections.
     */
    private static final ComputedReverb VOID_CATHEDRAL =
            new ComputedReverb(1.0f, 1.0f, 0.40f, 0.48f, 1.6f, 0.62f, 0.22f, 0.018f, 1.55f, 0.034f, 0.96f, 0.0f, 1);

    /** The reverb parameters for a named environment. */
    public static ComputedReverb getParams(Reverb reverb) {
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
            case VOID_CATHEDRAL -> VOID_CATHEDRAL;
        };
    }
}
