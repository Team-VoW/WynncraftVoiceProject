/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.physics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.wynnvp.wynncraftvp.sound.physics.OcclusionModel.Occlusion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class OcclusionModelTest {
    @Test
    void nothingInTheWayLeavesTheSoundAlone() {
        assertSame(Occlusion.NONE, OcclusionModel.compute(0f, 1f));
        assertSame(Occlusion.NONE, OcclusionModel.compute(-1f, 1f));
    }

    @Test
    void oneBlockOfStoneDullsMoreThanItQuietens() {
        Occlusion o = OcclusionModel.compute(1f, 1f);

        assertEquals(0.80f, o.gain(), 1e-5);
        assertEquals(0.45f, o.gainHf(), 1e-5);
    }

    @Test
    void transmissionFallsOffExponentiallyWithThickness() {
        Occlusion o = OcclusionModel.compute(2f, 1f);

        assertEquals(0.64f, o.gain(), 1e-5);
        assertEquals(0.2025f, o.gainHf(), 1e-5);
    }

    /**
     * The mod deliberately runs without distance attenuation so a player never misses a quest line.
     * Uncapped occlusion would undo that, so however much rock is in the way the voice has to stay
     * audible.
     */
    @ParameterizedTest
    @ValueSource(floats = {5f, 10f, 50f})
    void aThickWallNeverSwallowsTheLine(float units) {
        Occlusion o = OcclusionModel.compute(units, 1f);

        assertEquals(OcclusionModel.MIN_GAIN, o.gain(), 1e-5);
        assertEquals(OcclusionModel.MIN_GAINHF, o.gainHf(), 1e-5);
    }

    @Test
    void zeroStrengthDisablesMufflingEntirely() {
        assertSame(Occlusion.NONE, OcclusionModel.compute(10f, 0f));
    }

    @Test
    void strengthBlendsBackTowardNoWall() {
        Occlusion o = OcclusionModel.compute(10f, 0.5f);

        assertEquals(0.775f, o.gain(), 1e-5);
        assertEquals(0.575f, o.gainHf(), 1e-5);
    }

    @Test
    void anyOcclusionIsReportedAsAudible() {
        assertTrue(OcclusionModel.compute(1f, 1f).isAudible());
        assertTrue(!Occlusion.NONE.isAudible());
    }
}
