/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class VersionCheckerTest {
    @Test
    void equalVersionsReturnZero() {
        assertEquals(0, VersionChecker.compareVersions("2.0.0", "2.0.0"));
    }

    @Test
    void higherMajorIsNewer() {
        assertTrue(VersionChecker.compareVersions("2.0.0", "1.14.2") > 0);
    }

    @Test
    void lowerMajorIsOlder() {
        assertTrue(VersionChecker.compareVersions("1.14.2", "2.0.0") < 0);
    }

    @Test
    void multiDigitMinorComparedCorrectly() {
        // The old float trick produced 1.14.2 → 2.42 > 2.0.0 → 2.0, which was wrong
        assertTrue(VersionChecker.compareVersions("1.14.2", "1.9.0") > 0);
    }

    @Test
    void higherMinorIsNewer() {
        assertTrue(VersionChecker.compareVersions("1.5.0", "1.4.9") > 0);
    }

    @Test
    void higherPatchIsNewer() {
        assertTrue(VersionChecker.compareVersions("1.0.2", "1.0.1") > 0);
    }

    @Test
    void vPrefixStrippedCorrectly() {
        // Production code strips the leading 'v' before calling compareVersions,
        // but the method itself should also tolerate it via the replaceAll strip
        assertEquals(0, VersionChecker.compareVersions("v2.0.0", "2.0.0"));
    }

    @Test
    void missingPatchSegmentTreatedAsZero() {
        assertEquals(0, VersionChecker.compareVersions("2.0", "2.0.0"));
    }

    @Test
    void killSwitchVersionEqualTriggers() {
        // kill switch fires when version <= killSwitchVersion
        assertTrue(VersionChecker.compareVersions("1.5.0", "1.5.0") <= 0);
    }

    @Test
    void killSwitchVersionBelowDoesNotTrigger() {
        assertTrue(VersionChecker.compareVersions("2.0.0", "1.5.0") > 0);
    }
}
