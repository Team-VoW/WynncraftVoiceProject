/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.physics;

/**
 * An Amanatides-Woo voxel walk over a {@link MaterialProbe}.
 *
 * <p>Minecraft's own {@code Level#clip} cannot be used here: barriers have a full collision shape and
 * {@code ClipContext.Block} has no acoustic variant, so a clip would bounce sound off every one of
 * the invisible walls Wynncraft is built out of. {@code BlockGetter#traverseBlocks} could skip them
 * but yields no face normal, which would mean a {@code VoxelShape#clip} call per hit.
 *
 * <p>Walking ourselves gets the normal free from the step axis, keeps the whole tracer in the pure
 * layer where it can be tested against a fake world, and makes "skip", "escape" and "pass through
 * with loss" first-class outcomes.
 */
public final class VoxelRayTracer {
    /** Stops a pathological ray from walking forever if a probe returns nonsense. */
    private static final int MAX_STEPS = 4096;

    public enum Outcome {
        /** The ray bounced off a reflector. */
        HIT,
        /** The ray left the sampled world - unloaded chunk or past the build limit. */
        ESCAPED_OPEN,
        /** The ray used up its length without hitting anything. */
        ESCAPED_MAX_DISTANCE
    }

    public record RayResult(
            Outcome outcome,
            double distance,
            double hitX,
            double hitY,
            double hitZ,
            double normalX,
            double normalY,
            double normalZ,
            AcousticMaterial material,
            float occlusionUnits) {
        public boolean isHit() {
            return outcome == Outcome.HIT;
        }
    }

    private VoxelRayTracer() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Walks until the ray meets a reflector, leaves the world, or runs out of length. Occlusion
     * picked up from pass-through blocks along the way is reported either way.
     *
     * @param skipOriginBlock ignore whatever the ray starts inside. NPCs frequently clip a hair into
     *     the geometry they stand against, and without this every reverb ray would hit immediately.
     */
    public static RayResult cast(
            MaterialProbe world,
            double ox,
            double oy,
            double oz,
            double dx,
            double dy,
            double dz,
            double maxDistance,
            boolean skipOriginBlock,
            TraceSink sink) {
        return walk(world, ox, oy, oz, dx, dy, dz, maxDistance, skipOriginBlock, true, sink);
    }

    /**
     * Walks the whole length, charging occlusion for everything it passes through including
     * reflectors, and never stopping. This is what the direct-path and shadow rays use: they care how
     * much material is in the way, not what the first surface was.
     */
    public static RayResult castAccumulating(
            MaterialProbe world,
            double ox,
            double oy,
            double oz,
            double dx,
            double dy,
            double dz,
            double maxDistance,
            TraceSink sink) {
        return walk(world, ox, oy, oz, dx, dy, dz, maxDistance, false, false, sink);
    }

    private static RayResult walk(
            MaterialProbe world,
            double ox,
            double oy,
            double oz,
            double dx,
            double dy,
            double dz,
            double maxDistance,
            boolean skipOriginBlock,
            boolean stopOnReflector,
            TraceSink sink) {
        int x = (int) Math.floor(ox);
        int y = (int) Math.floor(oy);
        int z = (int) Math.floor(oz);

        int stepX = stepOf(dx);
        int stepY = stepOf(dy);
        int stepZ = stepOf(dz);

        // A zero component gives an infinite tDelta, which is what keeps the walk free of NaN.
        double tDeltaX = dx == 0 ? Double.POSITIVE_INFINITY : Math.abs(1.0 / dx);
        double tDeltaY = dy == 0 ? Double.POSITIVE_INFINITY : Math.abs(1.0 / dy);
        double tDeltaZ = dz == 0 ? Double.POSITIVE_INFINITY : Math.abs(1.0 / dz);

        double tMaxX = firstBoundary(ox, dx, x);
        double tMaxY = firstBoundary(oy, dy, y);
        double tMaxZ = firstBoundary(oz, dz, z);

        double t = 0;
        // Which face the ray entered the current voxel through. -1 while still in the origin voxel,
        // where the only sensible normal is straight back down the ray.
        int enteredAxis = -1;
        int enteredSign = 0;
        float occlusion = 0f;
        boolean firstVoxel = true;

        for (int steps = 0; steps < MAX_STEPS; steps++) {
            double tNext;
            int nextAxis;
            if (tMaxX <= tMaxY && tMaxX <= tMaxZ) {
                tNext = tMaxX;
                nextAxis = 0;
            } else if (tMaxY <= tMaxZ) {
                tNext = tMaxY;
                nextAxis = 1;
            } else {
                tNext = tMaxZ;
                nextAxis = 2;
            }

            AcousticMaterial material = world.materialAt(x, y, z);
            if (material == null) {
                return escaped(Outcome.ESCAPED_OPEN, ox, oy, oz, dx, dy, dz, t, occlusion, sink);
            }

            boolean ignored = firstVoxel && skipOriginBlock;
            if (!ignored && !material.isTransparent()) {
                if (material.isReflector() && stopOnReflector) {
                    return hit(ox, oy, oz, dx, dy, dz, t, enteredAxis, enteredSign, material, occlusion);
                }
                double segment = Math.min(tNext, maxDistance) - t;
                if (segment > 0) {
                    occlusion += (float) (material.occlusion() * segment);
                }
                if (sink != null) {
                    sink.onOcclusion(x + 0.5, y + 0.5, z + 0.5);
                }
            }
            firstVoxel = false;

            if (tNext >= maxDistance) {
                return escaped(Outcome.ESCAPED_MAX_DISTANCE, ox, oy, oz, dx, dy, dz, maxDistance, occlusion, sink);
            }

            t = tNext;
            switch (nextAxis) {
                case 0 -> {
                    tMaxX += tDeltaX;
                    x += stepX;
                    enteredSign = -stepX;
                }
                case 1 -> {
                    tMaxY += tDeltaY;
                    y += stepY;
                    enteredSign = -stepY;
                }
                default -> {
                    tMaxZ += tDeltaZ;
                    z += stepZ;
                    enteredSign = -stepZ;
                }
            }
            enteredAxis = nextAxis;
        }

        return escaped(Outcome.ESCAPED_MAX_DISTANCE, ox, oy, oz, dx, dy, dz, t, occlusion, sink);
    }

    private static RayResult hit(
            double ox,
            double oy,
            double oz,
            double dx,
            double dy,
            double dz,
            double t,
            int axis,
            int sign,
            AcousticMaterial material,
            float occlusion) {
        double nx = 0;
        double ny = 0;
        double nz = 0;
        switch (axis) {
            case 0 -> nx = sign;
            case 1 -> ny = sign;
            case 2 -> nz = sign;
            // Started inside the block, so the only normal that makes sense points back at the origin.
            default -> {
                nx = -dx;
                ny = -dy;
                nz = -dz;
            }
        }
        return new RayResult(Outcome.HIT, t, ox + dx * t, oy + dy * t, oz + dz * t, nx, ny, nz, material, occlusion);
    }

    private static RayResult escaped(
            Outcome outcome,
            double ox,
            double oy,
            double oz,
            double dx,
            double dy,
            double dz,
            double t,
            float occlusion,
            TraceSink sink) {
        double ex = ox + dx * t;
        double ey = oy + dy * t;
        double ez = oz + dz * t;
        if (sink != null) {
            sink.onEscape(ex, ey, ez);
        }
        return new RayResult(outcome, t, ex, ey, ez, 0, 0, 0, null, occlusion);
    }

    private static int stepOf(double d) {
        if (d > 0) {
            return 1;
        }
        return d < 0 ? -1 : 0;
    }

    private static double firstBoundary(double origin, double delta, int voxel) {
        if (delta > 0) {
            return (voxel + 1 - origin) / delta;
        }
        if (delta < 0) {
            return (voxel - origin) / delta;
        }
        return Double.POSITIVE_INFINITY;
    }
}
