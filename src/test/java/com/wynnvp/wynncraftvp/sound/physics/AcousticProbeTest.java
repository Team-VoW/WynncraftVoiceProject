/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.physics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class AcousticProbeTest {
    private static final AcousticSettings SETTINGS = AcousticSettings.of(48, 1f, 1f);

    /** Samples a sealed cube from its centre, with the listener standing next to the speaker. */
    private static AcousticSample sampleBox(int interior, AcousticMaterial wall) {
        FakeWorld world = FakeWorld.sealedBox(interior, wall);
        double c = FakeWorld.boxCentre(interior);
        return AcousticProbe.sample(world, c, c, c, c + 1, c, c, SETTINGS, null);
    }

    @Test
    void anEmptyWorldMeasuresAsCompletelyOpen() {
        AcousticSample sample = AcousticProbe.sample(new FakeWorld(), 0.5, 0.5, 0.5, 2.5, 0.5, 0.5, SETTINGS, null);

        assertEquals(1f, sample.openness(), 1e-6);
        assertTrue(sample.reflectedEnergy() < 0.02f);
        assertEquals(0f, sample.occlusionUnits(), 1e-6);
    }

    @Test
    void aSealedBoxMeasuresAsCompletelyEnclosed() {
        AcousticSample sample = sampleBox(10, FakeWorld.STONE);

        assertEquals(0f, sample.openness(), 1e-6);
        assertTrue(sample.reflectedEnergy() > 0.05f, "a sealed stone box should return energy");
        assertEquals(FakeWorld.STONE.reflectivity(), sample.meanReflectivity(), 1e-5);
    }

    /**
     * The absolute mean free path depends on the scattering model, but its scaling does not: a room
     * twice as big has surfaces twice as far apart, and that is what carries the decay time.
     */
    @Test
    void theMeanFreePathScalesWithTheRoom() {
        float small = sampleBox(10, FakeWorld.STONE).meanFreePath();
        float large = sampleBox(20, FakeWorld.STONE).meanFreePath();

        assertTrue(small > 3f && small < 14f, "implausible mean free path for a 10 block room: " + small);
        assertEquals(2.0, large / small, 0.35, "mean free path did not scale with the room");
    }

    @Test
    void aWoolRoomDecaysFarFasterThanTheSameRoomInStone() {
        float stone =
                ReverbParameterMapper.map(sampleBox(10, FakeWorld.STONE), 1f).decayTime();
        float wool =
                ReverbParameterMapper.map(sampleBox(10, FakeWorld.WOOL), 1f).decayTime();

        assertTrue(wool < stone / 3f, "wool " + wool + "s should be far shorter than stone " + stone + "s");
    }

    @Test
    void openingOneWallLetsEnergyOut() {
        int interior = 10;
        FakeWorld world = FakeWorld.sealedBox(interior, FakeWorld.STONE).clear(0, 0, 0, 0, interior + 1, interior + 1);
        double c = FakeWorld.boxCentre(interior);

        AcousticSample sample = AcousticProbe.sample(world, c, c, c, c + 1, c, c, SETTINGS, null);

        assertTrue(sample.openness() > 0.05f, "an open wall should let energy escape, got " + sample.openness());
        assertTrue(sample.openness() < 0.75f, "one open wall should not make it a field, got " + sample.openness());
    }

    /** Roughness is what actually scatters a reflection, so it has to be what drives diffusion. */
    @Test
    void roughSurfacesDiffuseMoreThanSmoothOnes() {
        float rough =
                ReverbParameterMapper.map(sampleBox(10, FakeWorld.WOOL), 1f).diffusion();
        float smooth =
                ReverbParameterMapper.map(sampleBox(10, FakeWorld.STONE), 1f).diffusion();

        assertTrue(rough > smooth, "wool " + rough + " should diffuse more than stone " + smooth);
    }

    /**
     * A corridor is elongated, and that shows up as longer average chords rather than as more varied
     * ones: most of its paths are the same short cross-section, so its path variation is actually
     * lower than a cube's.
     */
    @Test
    void aCorridorMeasuresLongerPathsThanACubeOfTheSameWidth() {
        AcousticSample cube = sampleBox(3, FakeWorld.STONE);

        // The same 3 x 3 cross-section, but running 40 blocks along z.
        FakeWorld corridor =
                new FakeWorld().fill(-1, -1, -1, 5, 5, 42, FakeWorld.STONE).clear(1, 1, 0, 3, 3, 41);
        AcousticSample sample = AcousticProbe.sample(corridor, 2.5, 2.5, 20.5, 2.5, 2.5, 22.5, SETTINGS, null);

        assertTrue(
                sample.meanFreePath() > cube.meanFreePath() * 1.2f,
                "corridor " + sample.meanFreePath() + " should out-reach cube " + cube.meanFreePath());
        assertEquals(0f, sample.openness(), 1e-6);
    }

    @Test
    void aStoneWallBetweenSpeakerAndListenerIsMeasured() {
        FakeWorld world = new FakeWorld().fill(5, -8, -8, 6, 16, 16, FakeWorld.STONE);

        AcousticSample sample = AcousticProbe.sample(world, 0.5, 5.5, 5.5, 10.5, 5.5, 5.5, SETTINGS, null);

        assertEquals(2.0f, sample.occlusionUnits(), 0.05f);
    }

    /** The whole point of the barrier rule: Wynncraft invisible walls must not muffle anything. */
    @Test
    void aBarrierWallBetweenSpeakerAndListenerIsNotMeasured() {
        FakeWorld world = new FakeWorld().fill(5, -8, -8, 6, 16, 16, FakeWorld.BARRIER);

        AcousticSample sample = AcousticProbe.sample(world, 0.5, 5.5, 5.5, 10.5, 5.5, 5.5, SETTINGS, null);

        assertEquals(0f, sample.occlusionUnits(), 1e-6);
        assertEquals(OcclusionModel.Occlusion.NONE, OcclusionModel.compute(sample.occlusionUnits(), 1f));
    }

    /**
     * A head-locked line is spoken from inside the listener's own head, so the direct path is known to
     * be clear and tracing it would be three rays spent proving something we already know.
     */
    @Test
    void aHeadLockedLineSkipsTheDirectPathTrace() {
        FakeWorld world = new FakeWorld().fill(5, -8, -8, 6, 16, 16, FakeWorld.STONE);

        AcousticSample traced = AcousticProbe.sample(world, 0.5, 5.5, 5.5, 10.5, 5.5, 5.5, SETTINGS, null, true);
        AcousticSample skipped = AcousticProbe.sample(world, 0.5, 5.5, 5.5, 10.5, 5.5, 5.5, SETTINGS, null, false);

        assertTrue(traced.occlusionUnits() > 1.5f, "the wall should be measured when asked for");
        assertEquals(0f, skipped.occlusionUnits(), 1e-6);
        // Everything else about the room is unchanged; only the direct path was skipped.
        assertEquals(traced.meanFreePath(), skipped.meanFreePath(), 1e-4);
        assertEquals(traced.openness(), skipped.openness(), 1e-4);
    }

    @Test
    void aSpeakerBeyondTheProcessingRangeIsNotTraced() {
        FakeWorld world = FakeWorld.sealedBox(10, FakeWorld.STONE);

        AcousticSample sample = AcousticProbe.sample(world, 5.5, 5.5, 5.5, 5.5, 5.5, 500.5, SETTINGS, null);

        assertEquals(AcousticSample.OPEN_FIELD, sample);
    }

    /** Fewer rays should be a cheaper estimate of the same room, not a different room. */
    @Test
    void theEstimateConvergesAcrossRayBudgets() {
        FakeWorld world = FakeWorld.sealedBox(10, FakeWorld.STONE);
        double c = FakeWorld.boxCentre(10);

        AcousticSample coarse = AcousticProbe.sample(world, c, c, c, c + 1, c, c, AcousticSettings.of(8, 1f, 1f), null);
        AcousticSample fine = AcousticProbe.sample(world, c, c, c, c + 1, c, c, AcousticSettings.of(96, 1f, 1f), null);

        assertEquals(
                fine.meanFreePath(),
                coarse.meanFreePath(),
                fine.meanFreePath() * 0.25f,
                "8 rays and 96 rays disagree about the room size");
    }

    @Test
    void unloadedChunksReadAsOpenSkyRatherThanWalls() {
        // Nothing but air, but everything past 10 blocks is "not loaded".
        FakeWorld world = new FakeWorld().voidOutside(-10, 10);

        AcousticSample sample = AcousticProbe.sample(world, 0.5, 0.5, 0.5, 2.5, 0.5, 0.5, SETTINGS, null);

        assertEquals(1f, sample.openness(), 1e-6);
        assertTrue(sample.reflectedEnergy() < 0.02f);
    }
}
