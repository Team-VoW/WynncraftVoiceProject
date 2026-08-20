/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.physics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.wynnvp.wynncraftvp.sound.physics.VoxelRayTracer.Outcome;
import com.wynnvp.wynncraftvp.sound.physics.VoxelRayTracer.RayResult;
import org.junit.jupiter.api.Test;

class VoxelRayTracerTest {
    private static RayResult cast(FakeWorld world, double ox, double oy, double oz, double dx, double dy, double dz) {
        return VoxelRayTracer.cast(world, ox, oy, oz, dx, dy, dz, 32, false, null);
    }

    @Test
    void findsAnAxisAlignedWallAndItsNormal() {
        FakeWorld world = new FakeWorld().fill(5, -4, -4, 5, 12, 12, FakeWorld.STONE);

        RayResult hit = cast(world, 0.5, 0.5, 0.5, 1, 0, 0);

        assertTrue(hit.isHit());
        assertEquals(4.5, hit.distance(), 1e-9);
        assertEquals(5.0, hit.hitX(), 1e-9);
        assertEquals(-1, hit.normalX(), 1e-9);
        assertEquals(0, hit.normalY(), 1e-9);
        assertEquals(0, hit.normalZ(), 1e-9);
        assertSame(FakeWorld.STONE, hit.material());
    }

    @Test
    void findsAWallApproachedDiagonally() {
        FakeWorld world = new FakeWorld().fill(6, -8, -8, 6, 16, 16, FakeWorld.STONE);

        double d = Math.sqrt(2) / 2;
        RayResult hit = cast(world, 0.5, 0.5, 0.5, d, d, 0);

        assertTrue(hit.isHit());
        // The plane at x = 6 is 5.5 away along x, and the ray covers x at cos(45 degrees).
        assertEquals(5.5 / d, hit.distance(), 1e-6);
        assertEquals(-1, hit.normalX(), 1e-9);
    }

    /**
     * The reason this tracer exists. Wynncraft builds its invisible walls out of barriers, and sound
     * has to behave as though they are not there at all.
     */
    @Test
    void barriersAreWalkedThroughAndChargeNothing() {
        FakeWorld world = new FakeWorld().fill(5, -4, -4, 6, 12, 12, FakeWorld.BARRIER);

        RayResult result = cast(world, 0.5, 0.5, 0.5, 1, 0, 0);

        assertFalse(result.isHit());
        assertEquals(Outcome.ESCAPED_MAX_DISTANCE, result.outcome());
        assertEquals(0f, result.occlusionUnits(), 1e-6);
    }

    @Test
    void aBarrierInFrontOfAWallDoesNotHideTheWall() {
        FakeWorld world = new FakeWorld()
                .fill(5, -4, -4, 5, 12, 12, FakeWorld.BARRIER)
                .fill(8, -4, -4, 8, 12, 12, FakeWorld.STONE);

        RayResult hit = cast(world, 0.5, 0.5, 0.5, 1, 0, 0);

        assertTrue(hit.isHit());
        assertEquals(7.5, hit.distance(), 1e-9);
    }

    @Test
    void leavingTheLoadedWorldCountsAsOpenAirRatherThanAWall() {
        FakeWorld world = new FakeWorld().voidOutside(0, 10);

        RayResult result = cast(world, 5.5, 5.5, 5.5, 1, 0, 0);

        assertEquals(Outcome.ESCAPED_OPEN, result.outcome());
        assertFalse(result.isHit());
    }

    @Test
    void aZeroComponentDoesNotProduceNaN() {
        FakeWorld world = new FakeWorld().fill(-8, 9, -8, 8, 9, 8, FakeWorld.STONE);

        RayResult hit = cast(world, 0.5, 0.5, 0.5, 0, 1, 0);

        assertTrue(hit.isHit());
        assertEquals(8.5, hit.distance(), 1e-9);
        assertFalse(Double.isNaN(hit.hitX()));
        assertFalse(Double.isNaN(hit.hitZ()));
    }

    @Test
    void skippingTheOriginBlockAvoidsHittingWhateverTheSpeakerStandsIn() {
        FakeWorld world =
                new FakeWorld().fill(0, 0, 0, 0, 0, 0, FakeWorld.STONE).fill(5, 0, 0, 5, 0, 0, FakeWorld.STONE);

        RayResult without = VoxelRayTracer.cast(world, 0.5, 0.5, 0.5, 1, 0, 0, 32, false, null);
        RayResult with = VoxelRayTracer.cast(world, 0.5, 0.5, 0.5, 1, 0, 0, 32, true, null);

        assertEquals(0.0, without.distance(), 1e-9);
        assertEquals(4.5, with.distance(), 1e-9);
    }

    @Test
    void accumulatingChargesEveryBlockItCrossesAndNeverStops() {
        FakeWorld world = new FakeWorld().fill(5, -4, -4, 6, 12, 12, FakeWorld.STONE);

        RayResult result = VoxelRayTracer.castAccumulating(world, 0.5, 0.5, 0.5, 1, 0, 0, 32, null);

        assertFalse(result.isHit());
        // Two blocks of stone at 1.0 occlusion units per block of thickness.
        assertEquals(2.0f, result.occlusionUnits(), 1e-5);
    }

    @Test
    void accumulatingIgnoresBarriersEntirely() {
        FakeWorld world = new FakeWorld().fill(5, -4, -4, 8, 12, 12, FakeWorld.BARRIER);

        RayResult result = VoxelRayTracer.castAccumulating(world, 0.5, 0.5, 0.5, 1, 0, 0, 32, null);

        assertEquals(0f, result.occlusionUnits(), 1e-6);
    }

    @Test
    void passThroughBlocksChargeLessThanSolidOnes() {
        FakeWorld world = new FakeWorld().fill(5, -4, -4, 6, 12, 12, FakeWorld.WATER);

        RayResult result = VoxelRayTracer.castAccumulating(world, 0.5, 0.5, 0.5, 1, 0, 0, 32, null);

        assertEquals(0.4f, result.occlusionUnits(), 1e-5);
    }
}
