/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.player;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.sound.physics.ComputedReverb;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.EXTEfx;

/**
 * The one place a {@link ComputedReverb} is pushed at an OpenAL effect.
 *
 * <p>Both the hand-authored presets and the ray-traced result come through here, so there is a single
 * definition of how the numbers map onto EFX.
 */
public final class ReverbApplier {
    /** How many further failures to swallow after complaining once. */
    private static final int ERROR_LOG_INTERVAL = 200;

    private static int suppressedErrors;

    private ReverbApplier() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Writes every reverb parameter, then re-attaches the effect to its slot.
     *
     * <p>That re-attach is not optional and is the classic way to lose an evening: OpenAL Soft only
     * picks up edits to an effect when it is committed to its slot again, so without it the very first
     * parameter set is the only one ever heard.
     *
     * <p>The error state is checked once for the whole batch rather than per parameter. The mod calls
     * into OpenAL from an unbounded thread pool where that state is shared, so a per-parameter check
     * would as often read another thread's error as its own.
     *
     * @return whether OpenAL accepted the update
     */
    public static boolean apply(int effect, int slot, ComputedReverb reverb) {
        EXTEfx.alEffectf(effect, EXTEfx.AL_REVERB_DENSITY, reverb.density());
        EXTEfx.alEffectf(effect, EXTEfx.AL_REVERB_DIFFUSION, reverb.diffusion());
        EXTEfx.alEffectf(effect, EXTEfx.AL_REVERB_GAIN, reverb.gain());
        EXTEfx.alEffectf(effect, EXTEfx.AL_REVERB_GAINHF, reverb.gainHf());
        EXTEfx.alEffectf(effect, EXTEfx.AL_REVERB_DECAY_TIME, reverb.decayTime());
        EXTEfx.alEffectf(effect, EXTEfx.AL_REVERB_DECAY_HFRATIO, reverb.decayHfRatio());
        EXTEfx.alEffectf(effect, EXTEfx.AL_REVERB_REFLECTIONS_GAIN, reverb.reflectionsGain());
        EXTEfx.alEffectf(effect, EXTEfx.AL_REVERB_REFLECTIONS_DELAY, reverb.reflectionsDelay());
        EXTEfx.alEffectf(effect, EXTEfx.AL_REVERB_LATE_REVERB_GAIN, reverb.lateReverbGain());
        EXTEfx.alEffectf(effect, EXTEfx.AL_REVERB_LATE_REVERB_DELAY, reverb.lateReverbDelay());
        EXTEfx.alEffectf(effect, EXTEfx.AL_REVERB_AIR_ABSORPTION_GAINHF, reverb.airAbsorptionGainHf());
        EXTEfx.alEffectf(effect, EXTEfx.AL_REVERB_ROOM_ROLLOFF_FACTOR, reverb.roomRolloffFactor());
        EXTEfx.alEffecti(effect, EXTEfx.AL_REVERB_DECAY_HFLIMIT, reverb.decayHfLimit());

        EXTEfx.alAuxiliaryEffectSloti(slot, EXTEfx.AL_EFFECTSLOT_EFFECT, effect);

        return AL10.alGetError() == AL10.AL_NO_ERROR;
    }

    /**
     * Applies an update that happens many times a second, complaining at most occasionally.
     *
     * <p>A source whose rig has gone bad would otherwise fill the log twenty times a second.
     */
    public static void applyThrottled(int effect, int slot, ComputedReverb reverb) {
        if (apply(effect, slot, reverb)) {
            return;
        }
        if (suppressedErrors > 0) {
            suppressedErrors--;
            return;
        }
        ModCore.warn("OpenAL rejected a reverb update");
        suppressedErrors = ERROR_LOG_INTERVAL;
    }
}
