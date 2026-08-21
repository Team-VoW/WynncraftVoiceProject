/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import org.junit.jupiter.api.Test;

/** The cadence of the nudge about Minecraft's Directional Audio setting. */
class DirectionalAudioHintTest {
    private static final long NOW = Duration.ofDays(20_000).toMillis();

    private final VOWAutoConfig config = new VOWAutoConfig();

    @Test
    void aPlayerWhoHasNeverSeenItSeesItStraightAway() {
        assertTrue(config.isDirectionalAudioHintDue(NOW));
    }

    @Test
    void itStaysQuietForTheRestOfTheInterval() {
        config.markDirectionalAudioHintShown(NOW);

        assertFalse(config.isDirectionalAudioHintDue(NOW));
        assertFalse(config.isDirectionalAudioHintDue(NOW + Duration.ofDays(2).toMillis()));
        assertFalse(config.isDirectionalAudioHintDue(NOW + VOWAutoConfig.DIRECTIONAL_AUDIO_HINT_INTERVAL_MS - 1));
    }

    @Test
    void itComesBackOnceTheIntervalHasPassed() {
        config.markDirectionalAudioHintShown(NOW);

        assertTrue(config.isDirectionalAudioHintDue(NOW + VOWAutoConfig.DIRECTIONAL_AUDIO_HINT_INTERVAL_MS));
        assertTrue(config.isDirectionalAudioHintDue(NOW + Duration.ofDays(30).toMillis()));
    }

    @Test
    void theIntervalIsThreeDays() {
        assertEquals(Duration.ofDays(3).toMillis(), VOWAutoConfig.DIRECTIONAL_AUDIO_HINT_INTERVAL_MS);
    }

    /** A timezone move or an NTP correction must not silence the hint until real time catches up. */
    @Test
    void aClockThatJumpedBackwardsDoesNotLockItOut() {
        config.markDirectionalAudioHintShown(NOW);

        assertTrue(config.isDirectionalAudioHintDue(NOW - Duration.ofDays(365).toMillis()));
    }
}
