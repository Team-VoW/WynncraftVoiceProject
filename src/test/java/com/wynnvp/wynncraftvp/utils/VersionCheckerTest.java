/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.wynnvp.wynncraftvp.api.VoWApiClient;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class VersionCheckerTest {
    @Test
    void stripsOnlyTheLeadingVersionPrefix() {
        assertEquals("2.2.0", VersionChecker.withoutLeadingV("v2.2.0"));
        assertEquals("2.2.0", VersionChecker.withoutLeadingV("2.2.0"));
    }

    @Test
    void uuidHashIsLowercaseSha256() {
        assertEquals(
                "986c0dc956dc822b5d8f698661b9eb1ef880786ff9043c16744d2a420e99e9bb",
                Utils.sha256("123e4567-e89b-12d3-a456-426614174000"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"none", "notify", "disable"})
    void serverActionAndBroadcastsRemainIntact(String action) {
        VersionChecker.BootupPlan plan = VersionChecker.plan(response(action, "Fact"), false, true);

        assertEquals(action, plan.action());
        assertEquals("disable".equals(action), plan.disablesMod());
        assertEquals(!"none".equals(action), plan.showsUpdate());
        assertEquals(List.of("First", "Second"), plan.broadcasts());
    }

    @Test
    void funFactIsOptionalAndOnlyShownForNoUpdate() {
        assertNull(VersionChecker.plan(response("none", null), false, true).funFact());
        assertNull(VersionChecker.plan(response("none", "Fact"), false, false).funFact());
        assertNull(VersionChecker.plan(response("notify", "Fact"), false, true).funFact());
        assertEquals(
                "Fact",
                VersionChecker.plan(response("none", "Fact"), false, true).funFact());
    }

    @Test
    void normalBuildAppliesBaseAndOnlyNonemptyMirrors() {
        VersionChecker.BootupPlan plan = VersionChecker.plan(response("none", null), false, false);

        assertEquals("https://audio.test/base/", plan.audioBaseUrl());
        assertEquals(List.of("https://audio.test/mirror/"), plan.audioMirrorUrls());
    }

    @Test
    void betaBuildPreservesItsAudioOverrides() {
        VersionChecker.BootupPlan plan = VersionChecker.plan(response("none", null), true, false);

        assertNull(plan.audioBaseUrl());
        assertEquals(List.of(), plan.audioMirrorUrls());
    }

    private static VoWApiClient.BootupResponse response(String action, String funFact) {
        return new VoWApiClient.BootupResponse(
                new VoWApiClient.UpdateResponse(
                        action, "2.3.0", "https://example.test/mod.jar", "https://example.test/changelog"),
                new VoWApiClient.AudioResponse(
                        "https://audio.test/base/", List.of("", "https://audio.test/mirror/", "  ")),
                funFact,
                List.of("First", "Second"));
    }
}
