/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.physics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class FibonacciSphereTest {
    @ParameterizedTest
    @ValueSource(ints = {8, 16, 48, 96})
    void everyDirectionIsAUnitVector(int count) {
        double[] dirs = FibonacciSphere.directions(count);

        assertEquals(count * 3, dirs.length);
        for (int i = 0; i < count; i++) {
            double x = dirs[i * 3];
            double y = dirs[i * 3 + 1];
            double z = dirs[i * 3 + 2];
            assertEquals(1.0, Math.sqrt(x * x + y * y + z * z), 1e-9, "direction " + i + " is not unit length");
        }
    }

    /**
     * An unbiased set is what makes an empty world measure as fully open: if the directions leaned to
     * one side, the escape fraction would depend on which way the geometry happened to lie.
     */
    @ParameterizedTest
    @ValueSource(ints = {16, 48, 96})
    void theDirectionsAreEvenlySpread(int count) {
        double[] dirs = FibonacciSphere.directions(count);

        double sx = 0;
        double sy = 0;
        double sz = 0;
        for (int i = 0; i < count; i++) {
            sx += dirs[i * 3];
            sy += dirs[i * 3 + 1];
            sz += dirs[i * 3 + 2];
        }
        double bias = Math.sqrt(sx * sx + sy * sy + sz * sz) / count;

        assertTrue(bias < 0.05, "direction set is biased: " + bias);
    }

    @Test
    void repeatedCallsReturnTheSameCachedArray() {
        assertSame(FibonacciSphere.directions(48), FibonacciSphere.directions(48));
    }

    @Test
    void degenerateCountsDoNotThrow() {
        assertEquals(0, FibonacciSphere.directions(0).length);
        assertEquals(0, FibonacciSphere.directions(-5).length);
        assertEquals(3, FibonacciSphere.directions(1).length);
    }
}
