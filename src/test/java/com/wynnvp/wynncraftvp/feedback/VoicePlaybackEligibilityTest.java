/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.feedback;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.ByteBuffer;
import org.junit.jupiter.api.Test;

class VoicePlaybackEligibilityTest {
    @Test
    void actualNonMutedNonSilentPlaybackOnly() {
        ByteBuffer audio = ByteBuffer.wrap(new byte[] {0, 0, 1, 2});
        assertTrue(VoicePlaybackEligibility.qualifies(true, 1, audio));
        assertEquals(0, audio.position());
        assertFalse(VoicePlaybackEligibility.qualifies(false, 1, audio));
        assertFalse(VoicePlaybackEligibility.qualifies(true, 0, audio));
        assertFalse(VoicePlaybackEligibility.qualifies(true, 0.00001f, audio));
        assertFalse(VoicePlaybackEligibility.qualifies(true, 1, ByteBuffer.allocate(200)));
        assertFalse(VoicePlaybackEligibility.qualifies(true, 1, ByteBuffer.allocate(0)));
        assertFalse(VoicePlaybackEligibility.qualifies(false, 1, null));
    }
}
