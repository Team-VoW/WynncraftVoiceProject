/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.physics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.wynnvp.wynncraftvp.sound.physics.OcclusionModel.Occlusion;
import org.junit.jupiter.api.Test;

class AcousticSmootherTest {
    private static ComputedReverb reverb(float decayTime, float gain) {
        return new ComputedReverb(0.6f, 0.7f, gain, 0.8f, decayTime, 1.0f, 0.3f, 0.02f, 0.5f, 0.01f, 0.994f, 0f, 1);
    }

    @Test
    void theFirstEvaluationSnapsSoALineDoesNotSwellAtItsStart() {
        AcousticSmoother smoother = new AcousticSmoother();
        ComputedReverb target = reverb(4.0f, 0.6f);

        smoother.setTarget(target, new Occlusion(0.7f, 0.3f));
        smoother.tick(true);

        AppliedAcoustics applied = smoother.publish();
        assertSame(target, applied.reverb());
        assertEquals(0.7f, applied.directGain(), 1e-6);
        assertEquals(0.3f, applied.directGainHf(), 1e-6);
    }

    @Test
    void aChangeIsMostlyAppliedByTheTimeTheNextEvaluationLands() {
        AcousticSmoother smoother = new AcousticSmoother();
        smoother.setTarget(reverb(0.5f, 0.1f), Occlusion.NONE);
        smoother.tick(true);

        smoother.setTarget(reverb(6.0f, 0.6f), new Occlusion(0.6f, 0.2f));
        // The world is re-measured every ten ticks, so that is the window the glide has to close in.
        for (int i = 0; i < 10; i++) {
            smoother.tick(false);
        }

        AppliedAcoustics applied = smoother.publish();
        assertAtLeastFractionOfTheWay("directGain", 1f, 0.6f, applied.directGain(), 0.80f);
        assertAtLeastFractionOfTheWay("directGainHf", 1f, 0.2f, applied.directGainHf(), 0.80f);
        // Decay glides in log space, so it covers most of the perceptual distance rather than most of
        // the arithmetic one: a jump this large is still clearly on its way rather than snapping.
        assertAtLeastFractionOfTheWay("decayTime", 0.5f, 6.0f, applied.reverb().decayTime(), 0.65f);
    }

    @Test
    void aGlideFullyArrivesGivenALittleLonger() {
        AcousticSmoother smoother = new AcousticSmoother();
        smoother.setTarget(reverb(0.5f, 0.1f), Occlusion.NONE);
        smoother.tick(true);

        smoother.setTarget(reverb(6.0f, 0.6f), new Occlusion(0.6f, 0.2f));
        for (int i = 0; i < 30; i++) {
            smoother.tick(false);
        }

        AppliedAcoustics applied = smoother.publish();
        assertEquals(6.0f, applied.reverb().decayTime(), 0.1f);
        assertEquals(0.6f, applied.directGain(), 0.005f);
        assertEquals(0.2f, applied.directGainHf(), 0.005f);
    }

    private static void assertAtLeastFractionOfTheWay(String name, float from, float to, float actual, float fraction) {
        float progress = (actual - from) / (to - from);
        assertTrue(
                progress >= fraction && progress <= 1.0001f,
                name + " was " + actual + ", only " + progress + " of the way from " + from + " to " + to);
    }

    @Test
    void theGlideNeverOvershootsEitherEnd() {
        AcousticSmoother smoother = new AcousticSmoother();
        smoother.setTarget(reverb(0.5f, 0.1f), Occlusion.NONE);
        smoother.tick(true);
        smoother.setTarget(reverb(6.0f, 0.6f), new Occlusion(0.6f, 0.2f));

        float previous = 0.5f;
        for (int i = 0; i < 40; i++) {
            smoother.tick(false);
            float decay = smoother.publish().reverb().decayTime();
            assertTrue(decay >= previous - 1e-5f, "decay went backwards at tick " + i);
            assertTrue(decay <= 6.0f + 1e-4f, "decay overshot the target at tick " + i);
            previous = decay;
        }
    }

    @Test
    void retargetingMidGlideJustChangesWhereItIsHeaded() {
        AcousticSmoother smoother = new AcousticSmoother();
        smoother.setTarget(reverb(0.5f, 0.1f), Occlusion.NONE);
        smoother.tick(true);

        smoother.setTarget(reverb(6.0f, 0.6f), Occlusion.NONE);
        for (int i = 0; i < 3; i++) {
            smoother.tick(false);
        }
        float midway = smoother.publish().reverb().decayTime();
        assertTrue(midway > 0.5f && midway < 6.0f);

        smoother.setTarget(reverb(0.5f, 0.1f), Occlusion.NONE);
        for (int i = 0; i < 40; i++) {
            smoother.tick(false);
        }
        assertEquals(0.5f, smoother.publish().reverb().decayTime(), 0.02f);
    }

    /** Log-space interpolation is the one place a zero could turn into a negative or an infinity. */
    @Test
    void gainsGlidingDownToSilenceStayPositive() {
        AcousticSmoother smoother = new AcousticSmoother();
        smoother.setTarget(reverb(2.0f, 0.6f), Occlusion.NONE);
        smoother.tick(true);
        smoother.setTarget(reverb(2.0f, 0f), Occlusion.NONE);

        for (int i = 0; i < 60; i++) {
            smoother.tick(false);
            float gain = smoother.publish().reverb().gain();
            assertTrue(gain >= 0f && Float.isFinite(gain), "gain became " + gain);
        }
        assertTrue(smoother.publish().reverb().gain() < 0.01f);
    }

    /** Reverb being off is not the same as nothing having been measured yet. */
    @Test
    void mufflingStillGlidesWhenReverbIsTurnedOff() {
        AcousticSmoother smoother = new AcousticSmoother();
        smoother.setTarget(null, Occlusion.NONE);
        smoother.tick(true);

        smoother.setTarget(null, new Occlusion(0.6f, 0.2f));
        smoother.tick(false);

        AppliedAcoustics afterOneTick = smoother.publish();
        assertNull(afterOneTick.reverb());
        assertTrue(
                afterOneTick.directGain() > 0.6f && afterOneTick.directGain() < 1f,
                "muffling jumped instead of gliding: " + afterOneTick.directGain());

        for (int i = 0; i < 30; i++) {
            smoother.tick(false);
        }
        assertEquals(0.6f, smoother.publish().directGain(), 0.005f);
    }

    @Test
    void reverbSwitchedOnMidLineIsTakenStraightAway() {
        AcousticSmoother smoother = new AcousticSmoother();
        smoother.setTarget(null, Occlusion.NONE);
        smoother.tick(true);

        ComputedReverb target = reverb(3.0f, 0.5f);
        smoother.setTarget(target, Occlusion.NONE);
        smoother.tick(false);

        // Nothing to glide from, so it arrives rather than creeping up from a floor value.
        assertSame(target, smoother.publish().reverb());
    }

    @Test
    void publishesDryUntilSomethingHasBeenMeasured() {
        AcousticSmoother smoother = new AcousticSmoother();

        AppliedAcoustics applied = smoother.publish();

        assertNull(applied.reverb());
        assertEquals(1f, applied.directGain(), 1e-6);
        assertTrue(!applied.hasOcclusion());
    }
}
