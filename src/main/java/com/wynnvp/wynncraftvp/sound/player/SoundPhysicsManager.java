/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.player;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.sound.Reverb;
import com.wynnvp.wynncraftvp.sound.physics.AcousticMaterialTable;
import com.wynnvp.wynncraftvp.sound.physics.AcousticProbe;
import com.wynnvp.wynncraftvp.sound.physics.AcousticSample;
import com.wynnvp.wynncraftvp.sound.physics.AcousticSettings;
import com.wynnvp.wynncraftvp.sound.physics.AppliedAcoustics;
import com.wynnvp.wynncraftvp.sound.physics.ComputedReverb;
import com.wynnvp.wynncraftvp.sound.physics.LevelMaterialProbe;
import com.wynnvp.wynncraftvp.sound.physics.OcclusionModel;
import com.wynnvp.wynncraftvp.sound.physics.ReverbParameterMapper;
import com.wynnvp.wynncraftvp.sound.physics.TraceSink;
import java.util.Collection;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * Measures the space around each speaking line and hands the result to the audio thread.
 *
 * <p>Runs entirely on the client thread. That is not a preference: an evaluation reads a few thousand
 * block states, and the audio thread this mod plays on is a worker pool, where walking chunk sections
 * concurrently with chunk loading would read torn or missing data.
 *
 * <p>At most one line is measured per tick. Dialogue is rarely more than one line at a time, but
 * capping it means a burst of overlapping lines can never turn into a frame spike.
 */
public final class SoundPhysicsManager {
    /** Ticks between re-measurements while a line plays. */
    private static final int EVALUATION_INTERVAL = 10;

    /** How long a measurement taken just before playback began is still worth applying. */
    private static final int PRIMED_LIFETIME_TICKS = 20;

    private final AcousticMaterialTable table;
    private final LevelMaterialProbe probe;
    private final SoundPhysicsDebug debug = new SoundPhysicsDebug();

    private ResourceKey<Level> lastDimension;

    /** Written on the client thread, read on the audio thread as a source is being created. */
    private volatile Primed primed;

    /** The client thread's idea of the time, so the audio thread never has to ask the world. */
    private volatile long clientTick;

    /** A measurement taken at play time, before the source exists, so a line never starts dry. */
    private record Primed(Vec3 position, long tick, AppliedAcoustics acoustics) {}

    public SoundPhysicsManager() {
        this.table = AcousticMaterialTable.load();
        this.probe = new LevelMaterialProbe(table);
    }

    /** Whether ray tracing should run at all right now. */
    public static boolean isEnabled() {
        return ModCore.config != null && ModCore.config.isSoundPhysics();
    }

    /**
     * Measures the space at a position straight away, so the very first moment of a line is already
     * in the right room.
     *
     * <p>Called as playback is being set up. Chat and overlay handling are already on the client
     * thread, so this normally runs inline; if it does not, the line simply starts dry and the next
     * tick snaps it into place.
     */
    public void prime(String speakerName, Optional<Vec3> fixedPosition, Reverb forcedReverb) {
        if (!isEnabled()) {
            return;
        }
        Minecraft minecraft = Minecraft.getInstance();
        if (!minecraft.isSameThread()) {
            return;
        }
        ClientLevel level = minecraft.level;
        LocalPlayer player = minecraft.player;
        if (level == null || player == null) {
            return;
        }

        // Resolving the speaker means searching the entity list, which is exactly why this only runs
        // on the client thread.
        Optional<Vec3> resolved = new CurrentSpeaker(speakerName, fixedPosition).getUpdatedPosition();

        Vec3 listener = player.getEyePosition();
        boolean headLocked = resolved.isEmpty() || playsOnPlayer();
        Vec3 source = resolved.orElse(listener);
        AppliedAcoustics acoustics = evaluate(level, source, listener, headLocked, forcedReverb, null);
        clientTick = level.getGameTime();
        primed = new Primed(source, clientTick, acoustics);
    }

    /**
     * The primed measurement for a line about to start, if one was taken recently and nearby.
     *
     * <p>Read from the audio thread while a source is being created.
     */
    public AppliedAcoustics takePrimed(Optional<Vec3> speakerPos) {
        Primed snapshot = primed;
        if (snapshot == null) {
            return AppliedAcoustics.DRY;
        }
        // Deliberately compares against a tick the client thread published rather than reading the
        // world. This runs on the audio executor, which must never touch the level.
        if (clientTick - snapshot.tick() > PRIMED_LIFETIME_TICKS) {
            return AppliedAcoustics.DRY;
        }
        if (speakerPos.isPresent() && speakerPos.get().distanceToSqr(snapshot.position()) > 4.0) {
            return AppliedAcoustics.DRY;
        }
        return snapshot.acoustics();
    }

    /** Client thread, once per world tick, before the audio thread reads any of this. */
    public void onClientTick(Collection<CurrentSpeaker> speakers) {
        Minecraft minecraft = Minecraft.getInstance();
        ClientLevel level = minecraft.level;
        LocalPlayer player = minecraft.player;
        if (level == null || player == null) {
            return;
        }

        // Published even with nothing playing, so a primed measurement can always be aged correctly
        // by the audio thread without it having to ask the world what time it is.
        clientTick = level.getGameTime();
        if (speakers.isEmpty()) {
            return;
        }

        // Block states are shared, but the shapes and the world behind them are not, so a change of
        // world invalidates both the cache and every measurement taken in the old one.
        if (lastDimension != level.dimension()) {
            lastDimension = level.dimension();
            table.clearCache();
            speakers.forEach(CurrentSpeaker::requestEvaluation);
        }

        long tick = clientTick;
        Vec3 listener = player.getEyePosition();
        boolean physics = isEnabled();
        boolean measuredOne = false;

        for (CurrentSpeaker speaker : speakers) {
            speaker.refreshPosition();

            if (!physics) {
                speaker.publishAcoustics(presetOnly(speaker));
                continue;
            }

            boolean first = speaker.needsEvaluation();
            boolean due = tick - speaker.lastEvaluationTick() >= EVALUATION_INTERVAL;
            if (first && measuredOne) {
                // Another line already used this tick's budget. Leave what was measured as playback
                // started in place rather than publishing an empty smoother over the top of it.
                continue;
            }
            if ((first || due) && !measuredOne) {
                Optional<Vec3> position = speaker.publishedPosition();
                // A narrating line has no speaker to stand next to, so it is measured where the
                // listener is: the room they are in is still the room the voice is heard in.
                boolean headLocked = position.isEmpty() || playsOnPlayer();
                Vec3 source = position.orElse(listener);

                AppliedAcoustics measured =
                        evaluate(level, source, listener, headLocked, speaker.forcedReverb(), speaker);
                speaker.smoother()
                        .setTarget(
                                measured.reverb(),
                                new OcclusionModel.Occlusion(measured.directGain(), measured.directGainHf()));
                speaker.markEvaluated(tick);
                measuredOne = true;
            }

            // Glide every tick, not only when a fresh measurement arrives, so walking out of a cave
            // shortens the tail smoothly instead of in ten-tick steps.
            speaker.smoother().tick(first);
            speaker.publishAcoustics(speaker.smoother().publish());
        }
    }

    private AppliedAcoustics presetOnly(CurrentSpeaker speaker) {
        return new AppliedAcoustics(presetFor(speaker.forcedReverb()), 1f, 1f);
    }

    /**
     * The preset a line falls back on, honouring the reverb toggle.
     *
     * <p>The single definition of two rules that used to be restated wherever they were needed: an
     * absent tag means the historical {@link Reverb#OUTSIDE}, and reverb being switched off means no
     * reverb at all. Both the traced and the preset path go through here so they cannot drift apart.
     *
     * @return the preset, or {@code null} when reverb is switched off
     */
    public static ComputedReverb presetFor(Reverb forced) {
        if (!reverbEnabled()) {
            return null;
        }
        return ReverbPresets.getParams(forced == null ? Reverb.OUTSIDE : forced);
    }

    public static boolean reverbEnabled() {
        return ModCore.config == null || ModCore.config.enableReverb;
    }

    private AppliedAcoustics evaluate(
            ClientLevel level,
            Vec3 source,
            Vec3 listener,
            boolean headLocked,
            Reverb forcedReverb,
            CurrentSpeaker speaker) {
        TraceSink sink = debug.beginTrace(level);
        AcousticSample sample;
        try {
            probe.bind(level);
            sample = AcousticProbe.sample(
                    probe,
                    source.x,
                    source.y,
                    source.z,
                    listener.x,
                    listener.y,
                    listener.z,
                    settings(),
                    sink,
                    !headLocked);
        } finally {
            probe.release();
        }

        // An explicit reverb in sounds.json is a deliberate authoring decision and always wins.
        ComputedReverb reverb = forcedReverb != null
                ? presetFor(forcedReverb)
                : reverbEnabled() ? ReverbParameterMapper.map(sample, reverbStrength()) : null;

        // A head-locked line is spoken from inside the listener's own head, so there is by definition
        // nothing between the two of them to muffle it.
        OcclusionModel.Occlusion occlusion = headLocked
                ? OcclusionModel.Occlusion.NONE
                : OcclusionModel.compute(sample.occlusionUnits(), occlusionStrength());

        debug.endTrace(level, speaker, sample, reverb, occlusion);
        return new AppliedAcoustics(reverb, occlusion.gain(), occlusion.gainHf());
    }

    /**
     * Measures one spot on demand and describes what it found, for {@code /vow-debug sound-physics}.
     *
     * @param source where to listen from, or {@code null} for the player themselves
     * @param showTicks how long to leave the ray visualisation on afterwards
     * @return the summary line, or {@code null} if there is no world to measure
     */
    public String probeForDebug(Vec3 source, int showTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        ClientLevel level = minecraft.level;
        LocalPlayer player = minecraft.player;
        if (level == null || player == null) {
            return null;
        }

        debug.showFor(showTicks);
        Vec3 listener = player.getEyePosition();
        Vec3 from = source == null ? listener : source;

        TraceSink sink = debug.beginTrace(level);
        AcousticSample sample;
        try {
            probe.bind(level);
            sample = AcousticProbe.sample(
                    probe, from.x, from.y, from.z, listener.x, listener.y, listener.z, settings(), sink);
        } finally {
            probe.release();
        }

        ComputedReverb reverb = ReverbParameterMapper.map(sample, reverbStrength());
        OcclusionModel.Occlusion occlusion = OcclusionModel.compute(sample.occlusionUnits(), occlusionStrength());
        debug.endTrace(level, null, sample, reverb, occlusion);
        return SoundPhysicsDebug.describe(null, sample, reverb, occlusion);
    }

    private static boolean playsOnPlayer() {
        return ModCore.config != null && ModCore.config.isPlayAllSoundsOnPlayer();
    }

    private static AcousticSettings settings() {
        int rays = ModCore.config != null ? ModCore.config.getSoundPhysicsRays() : AcousticSettings.DEFAULT_RAYS;
        return AcousticSettings.of(rays, reverbStrength(), occlusionStrength());
    }

    private static float reverbStrength() {
        return ModCore.config != null ? ModCore.config.getSoundPhysicsReverbStrength() : 1f;
    }

    private static float occlusionStrength() {
        return ModCore.config != null ? ModCore.config.getSoundPhysicsOcclusionStrength() : 1f;
    }
}
