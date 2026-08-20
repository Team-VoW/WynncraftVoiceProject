/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.physics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;
import org.junit.jupiter.api.Test;

class ReverbParameterMapperTest {
    /** A fully enclosed room of a given size and hardness, with the other metrics left neutral. */
    private static AcousticSample room(float meanFreePath, float reflectivity) {
        return new AcousticSample(0f, meanFreePath, 0.3f, 0.5f, 0.35f, reflectivity, 0.3f, 0.2f, 0.02f, 0.03f, 0f, 48);
    }

    @Test
    void aSmallFurnishedWoodenRoomLandsNearTheHandTunedPreset() {
        // 5x4x5 room, mean free path 4V/S = 3.08. FURNISHED_ROOM is hand-tuned to 0.4s.
        float decay = ReverbParameterMapper.map(room(3.08f, 0.75f), 1f).decayTime();

        assertEquals(0.50f, decay, 0.05f);
    }

    @Test
    void aTenBlockWoodenHallLandsNearTheHandTunedPreset() {
        // 10x10x10, mean free path 6.67. EMPTY_ROOM is hand-tuned to 1.1s.
        float decay = ReverbParameterMapper.map(room(6.67f, 0.75f), 1f).decayTime();

        assertEquals(1.07f, decay, 0.08f);
    }

    @Test
    void aSmallStoneCaveRingsFarLongerThanTheSameRoomInWood() {
        float stone = ReverbParameterMapper.map(room(3.43f, 0.92f), 1f).decayTime();
        float wood = ReverbParameterMapper.map(room(3.43f, 0.75f), 1f).decayTime();

        assertEquals(1.72f, stone, 0.10f);
        assertTrue(stone > wood * 2.5f, "stone " + stone + " should ring far longer than wood " + wood);
    }

    @Test
    void aCathedralSizedSpaceIsCappedRatherThanRunningAway() {
        // 30x30x40, mean free path 21.8. Sabine gives about 11s, which stops sounding like a room.
        float decay = ReverbParameterMapper.map(room(21.8f, 0.92f), 1f).decayTime();

        assertEquals(8.0f, decay, 1e-4);
    }

    @Test
    void anOpenFieldBarelyReverberates() {
        ComputedReverb reverb = ReverbParameterMapper.map(AcousticSample.OPEN_FIELD, 1f);

        assertEquals(0.05f, reverb.gain(), 1e-4);
        assertEquals(1.10f, reverb.decayTime(), 1e-4);
    }

    @Test
    void strengthScalesWetnessButNeverTime() {
        AcousticSample sample = room(6.67f, 0.92f);
        ComputedReverb full = ReverbParameterMapper.map(sample, 1f);
        ComputedReverb silent = ReverbParameterMapper.map(sample, 0f);

        assertEquals(0f, silent.gain(), 1e-6);
        assertEquals(0f, silent.reflectionsGain(), 1e-6);
        assertEquals(0f, silent.lateReverbGain(), 1e-6);
        assertEquals(full.decayTime(), silent.decayTime(), 1e-6);
    }

    @Test
    void brightSurfacesLiftTheDecayPastTheReferenceBandAndSaySo() {
        // Nearly all the reflected energy survived in the high band: a hard, bright cave.
        AcousticSample bright = new AcousticSample(0f, 8f, 0.3f, 0.5f, 0.49f, 0.92f, 0.25f, 0.2f, 0.02f, 0.03f, 0f, 48);
        ComputedReverb reverb = ReverbParameterMapper.map(bright, 1f);

        assertTrue(reverb.decayHfRatio() > 1f);
        assertEquals(0, reverb.decayHfLimit());
    }

    @Test
    void dullSurfacesKeepTheDecayLimited() {
        AcousticSample dull = new AcousticSample(0f, 8f, 0.3f, 0.5f, 0.05f, 0.30f, 0.65f, 0.2f, 0.02f, 0.03f, 0f, 48);
        ComputedReverb reverb = ReverbParameterMapper.map(dull, 1f);

        assertTrue(reverb.decayHfRatio() <= 1f);
        assertEquals(1, reverb.decayHfLimit());
    }

    /**
     * A mapping bug should show up as a wrong-but-legal sound, never as a silently rejected OpenAL
     * parameter, so every output has to stay inside its EFX range for any input at all.
     */
    @Test
    void everyParameterStaysLegalForAnyInputIncludingNonsense() {
        Random random = new Random(20260820L);
        for (int i = 0; i < 5000; i++) {
            AcousticSample sample = new AcousticSample(
                    wild(random),
                    wild(random),
                    wild(random),
                    wild(random),
                    wild(random),
                    wild(random),
                    wild(random),
                    wild(random),
                    wild(random),
                    wild(random),
                    wild(random),
                    random.nextInt(-4, 128));

            assertLegal(ReverbParameterMapper.map(sample, wild(random)));
        }
    }

    private static float wild(Random random) {
        return switch (random.nextInt(8)) {
            case 0 -> Float.NaN;
            case 1 -> Float.POSITIVE_INFINITY;
            case 2 -> Float.NEGATIVE_INFINITY;
            case 3 -> -random.nextFloat() * 100f;
            case 4 -> 0f;
            case 5 -> 1f;
            case 6 -> random.nextFloat() * 1000f;
            default -> random.nextFloat();
        };
    }

    private static void assertLegal(ComputedReverb r) {
        inRange("density", r.density(), 0f, 1f);
        inRange("diffusion", r.diffusion(), 0f, 1f);
        inRange("gain", r.gain(), 0f, 1f);
        inRange("gainHf", r.gainHf(), 0f, 1f);
        inRange("decayTime", r.decayTime(), ComputedReverb.MIN_DECAY_TIME, ComputedReverb.MAX_DECAY_TIME);
        inRange("decayHfRatio", r.decayHfRatio(), ComputedReverb.MIN_DECAY_HF_RATIO, ComputedReverb.MAX_DECAY_HF_RATIO);
        inRange("reflectionsGain", r.reflectionsGain(), 0f, ComputedReverb.MAX_REFLECTIONS_GAIN);
        inRange("reflectionsDelay", r.reflectionsDelay(), 0f, ComputedReverb.MAX_REFLECTIONS_DELAY);
        inRange("lateReverbGain", r.lateReverbGain(), 0f, ComputedReverb.MAX_LATE_REVERB_GAIN);
        inRange("lateReverbDelay", r.lateReverbDelay(), 0f, ComputedReverb.MAX_LATE_REVERB_DELAY);
        inRange("airAbsorptionGainHf", r.airAbsorptionGainHf(), ComputedReverb.MIN_AIR_ABSORPTION_GAIN_HF, 1f);
        inRange("roomRolloffFactor", r.roomRolloffFactor(), 0f, ComputedReverb.MAX_ROOM_ROLLOFF_FACTOR);
        assertTrue(r.decayHfLimit() == 0 || r.decayHfLimit() == 1);
    }

    private static void inRange(String name, float value, float min, float max) {
        assertTrue(
                !Float.isNaN(value) && value >= min && value <= max,
                name + " was " + value + ", outside [" + min + ", " + max + "]");
    }
}
