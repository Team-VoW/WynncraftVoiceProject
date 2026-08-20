/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.physics;

/**
 * Measures the space around a speaker by tracing sound through it.
 *
 * <p>Two phases. Phase A walks the direct path to the listener and adds up how much material is in
 * the way. Phase B fires an even spread of rays from the speaker, bounces each one off the geometry,
 * and after every bounce asks whether that reflection can reach the listener, the classic image-source
 * idea, sampled stochastically instead of enumerated.
 *
 * <p>Energy is carried in two bands. Tracking the high band separately means the brightness of the
 * result falls out of which surfaces the sound actually touched, rather than being guessed from an
 * average. A voice bouncing around a wool-lined room comes back dull because the wool ate the treble
 * on every bounce, which is what happens in a real room.
 */
public final class AcousticProbe {
    /** How far to either side of the direct path the two extra occlusion rays are offset. */
    private static final double OCCLUSION_SPREAD = 0.35;

    /** Below this a ray has nothing left to contribute and is cheaper to drop than to follow. */
    private static final float ENERGY_FLOOR = 0.02f;

    /** Keeps a reflected ray from immediately re-hitting the surface it just left. */
    private static final double SURFACE_NUDGE = 1e-3;

    /** Distance at which a reflection has lost half its strength on the way to the listener. */
    private static final double REFERENCE_DISTANCE = 4.0;

    private AcousticProbe() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * @param sink optional observer for the debug visualisation; {@code null} in production
     */
    public static AcousticSample sample(
            MaterialProbe world,
            double sx,
            double sy,
            double sz,
            double lx,
            double ly,
            double lz,
            AcousticSettings settings,
            TraceSink sink) {
        return sample(world, sx, sy, sz, lx, ly, lz, settings, sink, true);
    }

    /**
     * @param measureOcclusion trace the direct path. A head-locked line is spoken from inside the
     *     listener's own head, so there is nothing between the two of them and the answer is known
     *     in advance; skipping saves the three rays it would take to prove it.
     */
    public static AcousticSample sample(
            MaterialProbe world,
            double sx,
            double sy,
            double sz,
            double lx,
            double ly,
            double lz,
            AcousticSettings settings,
            TraceSink sink,
            boolean measureOcclusion) {
        double toListenerX = lx - sx;
        double toListenerY = ly - sy;
        double toListenerZ = lz - sz;
        double listenerDistance = length(toListenerX, toListenerY, toListenerZ);

        if (listenerDistance > AcousticSettings.MAX_SPEAKER_DISTANCE) {
            return AcousticSample.OPEN_FIELD;
        }

        float occlusionUnits = measureOcclusion
                ? traceOcclusion(
                        world, sx, sy, sz, lx, ly, lz, toListenerX, toListenerY, toListenerZ, listenerDistance, sink)
                : 0f;

        return traceReverb(world, sx, sy, sz, lx, ly, lz, occlusionUnits, settings, sink);
    }

    /**
     * Phase A. Three parallel rays rather than one, so a doorway or window beside the wall pulls the
     * average down instead of the speaker being judged by whichever single block the centre line
     * happened to cross.
     */
    private static float traceOcclusion(
            MaterialProbe world,
            double sx,
            double sy,
            double sz,
            double lx,
            double ly,
            double lz,
            double dirX,
            double dirY,
            double dirZ,
            double distance,
            TraceSink sink) {
        if (distance < 1e-4) {
            return 0f;
        }

        double nx = dirX / distance;
        double ny = dirY / distance;
        double nz = dirZ / distance;

        // Any vector perpendicular to the path will do; cross with world up, unless the path is
        // already vertical, in which case cross with an axis it cannot be parallel to.
        double px;
        double py;
        double pz;
        if (Math.abs(ny) > 0.99) {
            px = 1;
            py = 0;
            pz = 0;
        } else {
            px = -nz;
            py = 0;
            pz = nx;
            double plen = length(px, py, pz);
            px /= plen;
            py /= plen;
            pz /= plen;
        }

        float total = 0f;
        for (int i = -1; i <= 1; i++) {
            double offset = i * OCCLUSION_SPREAD;
            double tx = lx + px * offset;
            double ty = ly + py * offset;
            double tz = lz + pz * offset;
            double rx = tx - sx;
            double ry = ty - sy;
            double rz = tz - sz;
            double len = length(rx, ry, rz);
            if (len < 1e-4) {
                continue;
            }
            VoxelRayTracer.RayResult r =
                    VoxelRayTracer.castAccumulating(world, sx, sy, sz, rx / len, ry / len, rz / len, len, sink);
            total += Math.min(r.occlusionUnits(), OcclusionModel.SATURATION_UNITS);
        }
        return total / 3f;
    }

    /** Phase B. */
    private static AcousticSample traceReverb(
            MaterialProbe world,
            double sx,
            double sy,
            double sz,
            double lx,
            double ly,
            double lz,
            float occlusionUnits,
            AcousticSettings settings,
            TraceSink sink) {
        int rays = settings.rayCount();
        double[] directions = FibonacciSphere.directions(rays);

        double escapedEnergy = 0;
        double reflectedEnergy = 0;
        double reflectedEnergyHf = 0;
        double earlyEnergy = 0;
        double earlyArrival = 0;
        double lateEnergy = 0;
        double lateArrival = 0;
        double reflectivitySum = 0;
        double scatterSum = 0;
        int surfaceCount = 0;
        double segmentSum = 0;
        double segmentSquareSum = 0;
        int segmentCount = 0;

        for (int i = 0; i < rays; i++) {
            double dx = directions[i * 3];
            double dy = directions[i * 3 + 1];
            double dz = directions[i * 3 + 2];
            double ox = sx;
            double oy = sy;
            double oz = sz;

            double energy = 1.0;
            double energyHf = 1.0;
            double path = 0;

            for (int bounce = 0; bounce < settings.maxBounces(); bounce++) {
                VoxelRayTracer.RayResult hit =
                        VoxelRayTracer.cast(world, ox, oy, oz, dx, dy, dz, settings.maxSegment(), true, null);

                if (!hit.isHit()) {
                    escapedEnergy += energy;
                    if (sink != null) {
                        sink.onEscape(hit.hitX(), hit.hitY(), hit.hitZ());
                    }
                    break;
                }

                path += hit.distance();
                if (path > settings.maxTotalPath()) {
                    break;
                }
                segmentSum += hit.distance();
                segmentSquareSum += hit.distance() * hit.distance();
                segmentCount++;

                AcousticMaterial material = hit.material();
                reflectivitySum += material.reflectivity();
                scatterSum += material.scatter();
                surfaceCount++;

                energy *= material.reflectivity();
                energyHf *= material.reflectivity() * material.hfDamping();
                if (energy < ENERGY_FLOOR) {
                    break;
                }

                double nx = hit.normalX();
                double ny = hit.normalY();
                double nz = hit.normalZ();
                double nlen = length(nx, ny, nz);
                if (nlen < 1e-9) {
                    break;
                }
                nx /= nlen;
                ny /= nlen;
                nz /= nlen;

                double bx = hit.hitX() + nx * SURFACE_NUDGE;
                double by = hit.hitY() + ny * SURFACE_NUDGE;
                double bz = hit.hitZ() + nz * SURFACE_NUDGE;

                if (sink != null) {
                    sink.onBounce(bx, by, bz);
                }

                // Can this reflection reach the listener, and how much of it survives the trip?
                double tx = lx - bx;
                double ty = ly - by;
                double tz = lz - bz;
                double toListener = length(tx, ty, tz);
                if (toListener > 1e-4) {
                    VoxelRayTracer.RayResult shadow = VoxelRayTracer.castAccumulating(
                            world, bx, by, bz, tx / toListener, ty / toListener, tz / toListener, toListener, null);
                    float blocked = Math.min(shadow.occlusionUnits(), OcclusionModel.SATURATION_UNITS);
                    double transmission = Math.pow(OcclusionModel.GAIN_BASE, blocked);
                    double transmissionHf = Math.pow(OcclusionModel.GAINHF_BASE, blocked);
                    double falloff = 1.0 / (1.0 + toListener / REFERENCE_DISTANCE);

                    double arriving = energy * transmission * falloff;
                    double arrivingHf = energyHf * transmissionHf * falloff;
                    double arrivalTime = (path + toListener) / ReverbParameterMapper.SPEED_OF_SOUND;

                    reflectedEnergy += arriving;
                    reflectedEnergyHf += arrivingHf;
                    if (bounce == 0) {
                        earlyEnergy += arriving;
                        earlyArrival += arriving * arrivalTime;
                    } else {
                        lateEnergy += arriving;
                        lateArrival += arriving * arrivalTime;
                    }
                }

                // Specular direction pulled toward the normal by how rough the surface is. Deterministic,
                // and it reproduces the mean of a Lambertian bounce - which also stops two parallel walls
                // trapping a ray in a perfect echo, the failure mode a purely specular tracer has in the
                // boxy rooms Wynncraft is built out of.
                double dot = dx * nx + dy * ny + dz * nz;
                double rx = dx - 2 * dot * nx;
                double ry = dy - 2 * dot * ny;
                double rz = dz - 2 * dot * nz;
                double scatter = material.scatter();
                double ndx = rx * (1 - scatter) + nx * scatter;
                double ndy = ry * (1 - scatter) + ny * scatter;
                double ndz = rz * (1 - scatter) + nz * scatter;
                double dlen = length(ndx, ndy, ndz);
                if (dlen < 1e-9) {
                    ndx = nx;
                    ndy = ny;
                    ndz = nz;
                    dlen = 1;
                }
                dx = ndx / dlen;
                dy = ndy / dlen;
                dz = ndz / dlen;
                ox = bx;
                oy = by;
                oz = bz;
            }
        }

        float meanFreePath = segmentCount > 0 ? (float) (segmentSum / segmentCount) : 0f;
        float pathVariation = 0f;
        if (segmentCount > 1 && meanFreePath > 1e-4f) {
            double mean = segmentSum / segmentCount;
            double variance = Math.max(0, segmentSquareSum / segmentCount - mean * mean);
            pathVariation = (float) (Math.sqrt(variance) / mean);
        }

        return new AcousticSample(
                (float) (escapedEnergy / rays),
                meanFreePath,
                pathVariation,
                (float) (reflectedEnergy / rays),
                (float) (reflectedEnergyHf / rays),
                surfaceCount > 0 ? (float) (reflectivitySum / surfaceCount) : 0f,
                surfaceCount > 0 ? (float) (scatterSum / surfaceCount) : 0f,
                (float) (earlyEnergy / rays),
                earlyEnergy > 0 ? (float) (earlyArrival / earlyEnergy) : 0f,
                lateEnergy > 0 ? (float) (lateArrival / lateEnergy) : 0f,
                occlusionUnits,
                rays);
    }

    private static double length(double x, double y, double z) {
        return Math.sqrt(x * x + y * y + z * z);
    }
}
