/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.wynnvp.wynncraftvp.sound.physics.ComputedReverb;
import com.wynnvp.wynncraftvp.sound.player.ReverbPresets;
import org.junit.jupiter.api.Test;

class ReverbTest {
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Reverb.class, new ReverbDeserializer())
            .create();

    @Test
    void existingIdsRemainStableAndVoidCathedralIsAppended() {
        assertEquals(Reverb.OUTSIDE, Reverb.fromId(0));
        assertEquals(Reverb.CATHEDRAL, Reverb.fromId(9));
        assertEquals(Reverb.VOID_CATHEDRAL, Reverb.fromId(10));
        assertNull(Reverb.fromId(11));
    }

    @Test
    void voidCathedralDeserializesByIdAndName() {
        assertEquals(Reverb.VOID_CATHEDRAL, GSON.fromJson("10", Reverb.class));
        assertEquals(Reverb.VOID_CATHEDRAL, GSON.fromJson("\"VOID_CATHEDRAL\"", Reverb.class));
    }

    @Test
    void voidCathedralIsShortDarkAndMoreIntenseThanTheRoomPresets() {
        ComputedReverb preset = ReverbPresets.getParams(Reverb.VOID_CATHEDRAL);

        assertEquals(1.6f, preset.decayTime(), 1e-4f);
        assertEquals(1.0f, preset.diffusion(), 1e-4f);
        assertEquals(0.48f, preset.gainHf(), 1e-4f);
        assertEquals(1.55f, preset.lateReverbGain(), 1e-4f);
        assertEquals(0.034f, preset.lateReverbDelay(), 1e-4f);
    }

    @Test
    void idAboveTheSupportedRangeIsRejected() {
        org.junit.jupiter.api.Assertions.assertThrows(
                JsonParseException.class, () -> GSON.fromJson("11", Reverb.class));
    }
}
