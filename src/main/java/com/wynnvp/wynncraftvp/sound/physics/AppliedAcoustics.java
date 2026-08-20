/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.physics;

/**
 * The finished acoustics for one playing line, ready to be pushed at OpenAL.
 *
 * <p>Immutable on purpose. It is written on the client thread and read on the audio thread, so a
 * single reference swap is the entire memory barrier and no locking is needed anywhere.
 *
 * @param reverb what to program into the source's own effect, or {@code null} for no reverb at all
 * @param directGain broadband gain on the direct path, 1 meaning nothing in the way
 * @param directGainHf high-band gain on the direct path
 */
public record AppliedAcoustics(ComputedReverb reverb, float directGain, float directGainHf) {
    /** No reverb, no muffling. What a source starts with and falls back to. */
    public static final AppliedAcoustics DRY = new AppliedAcoustics(null, 1f, 1f);

    public boolean hasOcclusion() {
        return directGain < 0.999f || directGainHf < 0.999f;
    }

    public AppliedAcoustics withoutOcclusion() {
        return new AppliedAcoustics(reverb, 1f, 1f);
    }
}
