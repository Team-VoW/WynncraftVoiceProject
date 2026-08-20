/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.physics;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.chunk.LevelChunk;

/**
 * Reads the loaded client world for the tracer.
 *
 * <p>Only safe on the client thread. Not thread safe, and deliberately so: it reuses one mutable block
 * position and memoises the last chunk, because a single evaluation reads a few thousand blocks and
 * almost all of them are neighbours of the previous one.
 */
public final class LevelMaterialProbe implements MaterialProbe {
    private final AcousticMaterialTable table;
    private final BlockPos.MutableBlockPos scratch = new BlockPos.MutableBlockPos();

    private ClientLevel level;
    private LevelChunk lastChunk;
    private int lastChunkX;
    private int lastChunkZ;

    public LevelMaterialProbe(AcousticMaterialTable table) {
        this.table = table;
    }

    /** Points the probe at a world for one evaluation. */
    public void bind(ClientLevel level) {
        if (this.level != level) {
            this.level = level;
            this.lastChunk = null;
        }
    }

    public void release() {
        this.level = null;
        this.lastChunk = null;
    }

    @Override
    public AcousticMaterial materialAt(int x, int y, int z) {
        if (level == null || level.isOutsideBuildHeight(y)) {
            return null;
        }

        int chunkX = x >> 4;
        int chunkZ = z >> 4;
        if (lastChunk == null || lastChunkX != chunkX || lastChunkZ != chunkZ) {
            if (!level.hasChunk(chunkX, chunkZ)) {
                // Not loaded, so we genuinely do not know. Reporting null makes the ray escape, which
                // is the safe answer: guessing "solid" would seal every speaker at the edge of the
                // render distance into a box and give them cathedral reverb in an open field.
                return null;
            }
            lastChunk = level.getChunk(chunkX, chunkZ);
            lastChunkX = chunkX;
            lastChunkZ = chunkZ;
        }

        scratch.set(x, y, z);
        return table.lookup(lastChunk.getBlockState(scratch), level, scratch);
    }
}
