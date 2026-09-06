/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.feedback;

import java.nio.ByteBuffer;

public final class VoicePlaybackEligibility {
    private VoicePlaybackEligibility() {}

    public static boolean qualifies(boolean started, float gain, ByteBuffer samples) {
        if (!started || gain <= 0.0001f || samples == null) return false;
        for (int i = samples.position(); i < samples.limit(); i++) {
            if (samples.get(i) != 0) return true;
        }
        return false;
    }
}
