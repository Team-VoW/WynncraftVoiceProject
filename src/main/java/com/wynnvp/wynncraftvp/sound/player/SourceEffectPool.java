/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.player;

import com.wynnvp.wynncraftvp.ModCore;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.EXTEfx;

/**
 * A small pool of private reverb rigs, one per line that is currently speaking.
 *
 * <p>The ten preset slots are shared, so a line using one cannot have its parameters edited without
 * changing every other line using the same preset. Ray-traced reverb needs to write a different
 * thirteen numbers for every speaker and to keep re-writing them as the player moves, so each such
 * line borrows its own effect, its own slot and its own low-pass filter for as long as it plays.
 *
 * <p>Dialogue is one or two lines at a time, so a handful of rigs is plenty; if they are all taken the
 * caller falls back to the shared presets rather than failing.
 *
 * <p>Every method here talks to OpenAL and must be called on the audio executor. That executor is a
 * cached pool, so a line starting while another one ends really can acquire and release at the same
 * moment; the methods are synchronized for exactly that.
 */
public final class SourceEffectPool {
    private static final int CAPACITY = 4;

    /** One rig: the effect holding the parameters, the slot it is heard through, and the direct-path filter. */
    public record Handles(int effect, int slot, int filter) {}

    private final List<Handles> created = new ArrayList<>();
    private final List<Handles> free = new ArrayList<>();
    private final boolean supported;

    public SourceEffectPool(boolean efxSupported) {
        this.supported = efxSupported;
    }

    public boolean isSupported() {
        return supported;
    }

    /** A rig for one source, or empty when EFX is unavailable or every rig is in use. */
    public synchronized Optional<Handles> acquire() {
        if (!supported) {
            return Optional.empty();
        }
        if (!free.isEmpty()) {
            return Optional.of(free.remove(free.size() - 1));
        }
        if (created.size() >= CAPACITY) {
            return Optional.empty();
        }
        return create();
    }

    private Optional<Handles> create() {
        AL10.alGetError();

        int effect = EXTEfx.alGenEffects();
        EXTEfx.alEffecti(effect, EXTEfx.AL_EFFECT_TYPE, EXTEfx.AL_EFFECT_REVERB);
        int slot = EXTEfx.alGenAuxiliaryEffectSlots();
        EXTEfx.alAuxiliaryEffectSloti(slot, EXTEfx.AL_EFFECTSLOT_EFFECT, effect);
        int filter = EXTEfx.alGenFilters();
        EXTEfx.alFilteri(filter, EXTEfx.AL_FILTER_TYPE, EXTEfx.AL_FILTER_LOWPASS);

        int error = AL10.alGetError();
        if (error != AL10.AL_NO_ERROR) {
            ModCore.warn(String.format("Could not create a sound physics effect rig (error 0x%X)", error));
            EXTEfx.alDeleteFilters(filter);
            EXTEfx.alDeleteAuxiliaryEffectSlots(slot);
            EXTEfx.alDeleteEffects(effect);
            return Optional.empty();
        }

        Handles handles = new Handles(effect, slot, filter);
        created.add(handles);
        return Optional.of(handles);
    }

    /** Hands a rig back. It keeps its OpenAL objects so the next line can reuse them. */
    public synchronized void release(Handles handles) {
        if (handles == null || free.contains(handles)) {
            return;
        }
        free.add(handles);
    }

    // No shutdown method on purpose. These rigs only ever die with the OpenAL context that made them,
    // and once that context is gone the names are meaningless: deleting them frees nothing and raises
    // AL_INVALID_NAME into the error state the next player's EFX probe reads. See OpenAlPlayer.dispose.
}
