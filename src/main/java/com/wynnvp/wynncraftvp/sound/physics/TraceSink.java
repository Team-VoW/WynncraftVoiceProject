/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.physics;

/**
 * Optional observer for a trace, used only by the debug visualisation. Production traces pass
 * {@code null} so the pure layer never allocates for something nobody is watching.
 */
public interface TraceSink {
    /** A ray bounced off a surface here. */
    void onBounce(double x, double y, double z);

    /** A ray was charged occlusion for the block whose centre is here. */
    void onOcclusion(double x, double y, double z);

    /** A ray left the world or ran out of length here. */
    void onEscape(double x, double y, double z);
}
