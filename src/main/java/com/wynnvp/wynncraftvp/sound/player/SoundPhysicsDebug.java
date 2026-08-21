/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.player;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.sound.physics.AcousticSample;
import com.wynnvp.wynncraftvp.sound.physics.ComputedReverb;
import com.wynnvp.wynncraftvp.sound.physics.OcclusionModel;
import com.wynnvp.wynncraftvp.sound.physics.TraceSink;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.phys.Vec3;

/**
 * Shows what the tracer saw.
 *
 * <p>The occlusion particles are the useful ones: standing behind a Wynncraft invisible wall and
 * seeing no smoke at all is the quickest proof that barriers really are being skipped.
 */
public final class SoundPhysicsDebug {
    /** Enough to read the shape of a room without whiting out the screen. */
    private static final int MAX_PARTICLES = 200;

    private final List<double[]> bounces = new ArrayList<>();
    private final List<double[]> occluders = new ArrayList<>();
    private final List<double[]> escapes = new ArrayList<>();

    /**
     * How many more measurements to visualise, set by the debug command.
     *
     * <p>Counted in measurements rather than ticks because that is what actually produces particles:
     * a line is only re-measured every ten ticks, so a tick count would outlive its own name by an
     * order of magnitude.
     */
    private int forcedTraces;

    private final TraceSink sink = new TraceSink() {
        @Override
        public void onBounce(double x, double y, double z) {
            add(bounces, x, y, z);
        }

        @Override
        public void onOcclusion(double x, double y, double z) {
            add(occluders, x, y, z);
        }

        @Override
        public void onEscape(double x, double y, double z) {
            add(escapes, x, y, z);
        }
    };

    private static void add(List<double[]> into, double x, double y, double z) {
        if (into.size() < MAX_PARTICLES) {
            into.add(new double[] {x, y, z});
        }
    }

    /** Visualises the next {@code traces} measurements regardless of the config flag. */
    public void showFor(int traces) {
        forcedTraces = Math.max(forcedTraces, traces);
    }

    public boolean isActive() {
        return forcedTraces > 0 || (ModCore.config != null && ModCore.config.isDebugSoundPhysics());
    }

    /** @return the sink to trace with, or {@code null} when nobody is watching */
    public TraceSink beginTrace(ClientLevel level) {
        if (level == null || !isActive()) {
            return null;
        }
        bounces.clear();
        occluders.clear();
        escapes.clear();
        return sink;
    }

    public void endTrace(
            ClientLevel level,
            CurrentSpeaker speaker,
            Vec3 source,
            Vec3 listener,
            AcousticSample sample,
            ComputedReverb reverb,
            OcclusionModel.Occlusion occlusion) {
        if (level == null || !isActive()) {
            return;
        }
        if (forcedTraces > 0) {
            forcedTraces--;
        }

        spawn(level, ParticleTypes.END_ROD, bounces);
        spawn(level, ParticleTypes.SMOKE, occluders);
        spawn(level, ParticleTypes.CRIT, escapes);

        ModCore.info(describe(speaker, source, listener, sample, reverb, occlusion));
    }

    /** The one-line summary, also used by {@code /vow-debug sound-physics}. */
    public static String describe(
            CurrentSpeaker speaker,
            Vec3 source,
            Vec3 listener,
            AcousticSample sample,
            ComputedReverb reverb,
            OcclusionModel.Occlusion occlusion) {
        String who = speaker == null || speaker.rawName.isEmpty() ? "<narration>" : speaker.rawName;
        // Where the voice was placed is the first thing to check when a line sounds wrong, so it goes
        // in front of everything the tracer measured about that spot.
        String where = source == null
                ? ""
                : String.format(
                        " @(%.1f, %.1f, %.1f) d=%.1f",
                        source.x, source.y, source.z, listener == null ? 0 : source.distanceTo(listener));
        String room = String.format(
                "[SoundPhysics] %s%s rays=%d | open=%.2f mfp=%.1f var=%.2f refl=%.3f hf=%.2f surf=%.2f scat=%.2f",
                who,
                where,
                sample.raysTraced(),
                sample.openness(),
                sample.meanFreePath(),
                sample.pathVariation(),
                sample.reflectedEnergy(),
                sample.hfRatio(),
                sample.meanReflectivity(),
                sample.meanScatter());
        String tail = reverb == null
                ? " -> reverb off"
                : String.format(
                        " -> decay=%.2f gain=%.2f gainHF=%.2f hfRatio=%.2f dens=%.2f diff=%.2f"
                                + " earlyG=%.2f earlyD=%.3f lateG=%.2f lateD=%.3f hfLimit=%d",
                        reverb.decayTime(),
                        reverb.gain(),
                        reverb.gainHf(),
                        reverb.decayHfRatio(),
                        reverb.density(),
                        reverb.diffusion(),
                        reverb.reflectionsGain(),
                        reverb.reflectionsDelay(),
                        reverb.lateReverbGain(),
                        reverb.lateReverbDelay(),
                        reverb.decayHfLimit());
        String occ = String.format(
                " | occl=%.2fu -> gain=%.2f gainHF=%.2f",
                sample.occlusionUnits(), occlusion.gain(), occlusion.gainHf());
        return room + tail + occ;
    }

    private static void spawn(ClientLevel level, ParticleOptions particle, List<double[]> points) {
        for (double[] point : points) {
            level.addParticle(particle, point[0], point[1], point[2], 0, 0, 0);
        }
    }
}
