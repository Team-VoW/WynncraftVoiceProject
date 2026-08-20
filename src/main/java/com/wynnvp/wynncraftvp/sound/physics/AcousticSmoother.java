/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.physics;

/**
 * Glides the acoustics of one playing line toward whatever the last evaluation measured.
 *
 * <p>The world is only re-measured every ten ticks, so without this a player walking out of a cave
 * would hear the tail collapse in one step. At {@value #ALPHA} per tick a change is about 86% applied
 * by the time the next evaluation lands, which is smooth without lagging behind the player.
 *
 * <p>Decay time and the gains glide in log space. Doubling a decay time from 0.5s to 6s is a far
 * bigger perceptual jump at the start than at the end, and a linear glide spends most of its time
 * already near the target while the audible part of the change happens in the first two ticks.
 */
public final class AcousticSmoother {
    /** Per-tick approach rate. */
    public static final float ALPHA = 0.18f;

    /** Keeps the log-space interpolation away from log(0). */
    private static final float LOG_FLOOR = 1e-4f;

    private ComputedReverb targetReverb;
    private OcclusionModel.Occlusion targetOcclusion = OcclusionModel.Occlusion.NONE;

    private ComputedReverb currentReverb;
    private OcclusionModel.Occlusion currentOcclusion = OcclusionModel.Occlusion.NONE;

    /**
     * Whether anything has been measured yet.
     *
     * <p>Tracked separately from {@code currentReverb} being null, because a null reverb is also what
     * a line looks like with reverb switched off - and its muffling should still glide.
     */
    private boolean started;

    public void setTarget(ComputedReverb reverb, OcclusionModel.Occlusion occlusion) {
        this.targetReverb = reverb;
        this.targetOcclusion = occlusion == null ? OcclusionModel.Occlusion.NONE : occlusion;
    }

    /**
     * Advances one tick.
     *
     * @param snap jump straight to the target instead of gliding. Used for the very first evaluation
     *     of a line, where there is no previous state to glide from and any glide would just be an
     *     audible swell at the start of every line.
     */
    public void tick(boolean snap) {
        if (snap || !started) {
            started = true;
            currentReverb = targetReverb;
            currentOcclusion = targetOcclusion;
            return;
        }
        // A reverb appearing or disappearing (the setting being toggled mid-line) has nothing to
        // glide from, so take it as it is and let the next tick blend normally.
        if (currentReverb == null || targetReverb == null) {
            currentReverb = targetReverb;
        } else {
            currentReverb = blend(currentReverb, targetReverb, ALPHA);
        }
        currentOcclusion = new OcclusionModel.Occlusion(
                lerp(currentOcclusion.gain(), targetOcclusion.gain(), ALPHA),
                lerp(currentOcclusion.gainHf(), targetOcclusion.gainHf(), ALPHA));
    }

    /** The current state, as a snapshot safe to hand to another thread. */
    public AppliedAcoustics publish() {
        return new AppliedAcoustics(currentReverb, currentOcclusion.gain(), currentOcclusion.gainHf());
    }

    private static ComputedReverb blend(ComputedReverb from, ComputedReverb to, float t) {
        return new ComputedReverb(
                lerp(from.density(), to.density(), t),
                lerp(from.diffusion(), to.diffusion(), t),
                logLerp(from.gain(), to.gain(), t),
                lerp(from.gainHf(), to.gainHf(), t),
                logLerp(from.decayTime(), to.decayTime(), t),
                lerp(from.decayHfRatio(), to.decayHfRatio(), t),
                logLerp(from.reflectionsGain(), to.reflectionsGain(), t),
                lerp(from.reflectionsDelay(), to.reflectionsDelay(), t),
                logLerp(from.lateReverbGain(), to.lateReverbGain(), t),
                lerp(from.lateReverbDelay(), to.lateReverbDelay(), t),
                lerp(from.airAbsorptionGainHf(), to.airAbsorptionGainHf(), t),
                lerp(from.roomRolloffFactor(), to.roomRolloffFactor(), t),
                // A binary flag has nothing sensible between its two states, and it only decides
                // whether the HF decay may outrun the reference decay, so take the target at once.
                to.decayHfLimit());
    }

    private static float lerp(float from, float to, float t) {
        return from + (to - from) * t;
    }

    private static float logLerp(float from, float to, float t) {
        float a = Math.max(from, LOG_FLOOR);
        float b = Math.max(to, LOG_FLOOR);
        return (float) Math.exp(Math.log(a) + (Math.log(b) - Math.log(a)) * t);
    }
}
