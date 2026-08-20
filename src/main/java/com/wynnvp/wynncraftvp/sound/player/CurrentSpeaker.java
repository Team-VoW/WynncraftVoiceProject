/*
 * Copyright © Team-VoW 2024-2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.player;

import com.wynnvp.wynncraftvp.npc.CachedEntity;
import com.wynnvp.wynncraftvp.npc.NPCHandler;
import com.wynnvp.wynncraftvp.sound.Reverb;
import com.wynnvp.wynncraftvp.sound.physics.AcousticSmoother;
import com.wynnvp.wynncraftvp.sound.physics.AppliedAcoustics;
import com.wynnvp.wynncraftvp.sound.physics.ComputedReverb;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.world.phys.Vec3;

/**
 * Everything the mod knows about one line that is currently speaking.
 *
 * <p>This is also the handover point between the two threads involved in playback. The client thread
 * resolves where the speaker is and measures the space around them; the audio thread reads those
 * results and pushes them at OpenAL. Only the fields marked as such cross that line, and each is
 * either volatile or an atomic reference to an immutable value, so no locking is needed.
 */
public class CurrentSpeaker {
    private CachedEntity cachedEntity;
    public String rawName = "";
    private Optional<Vec3> lastEntityPos;

    private Optional<Vec3> setPosition;

    /** An explicit reverb from sounds.json, which always beats what the ray tracer measures. */
    private final Reverb forcedReverb;

    // --- client thread writes, audio thread reads ---

    /** The finished acoustics for this line. Replaced wholesale, never edited in place. */
    private final AtomicReference<AppliedAcoustics> acoustics = new AtomicReference<>(AppliedAcoustics.DRY);

    /**
     * Where the speaker was as of the last client tick.
     *
     * <p>Resolving this needs the entity list, which is only safe to read on the client thread, so the
     * audio thread reads this snapshot rather than searching for the entity itself.
     */
    private volatile Optional<Vec3> publishedPosition = Optional.empty();

    // --- client thread only ---

    private final AcousticSmoother smoother = new AcousticSmoother();
    private boolean needsEvaluation = true;
    private long lastEvaluationTick = Long.MIN_VALUE;

    // --- audio thread only ---

    private SourceEffectPool.Handles handles;
    private boolean occlusionAttached;
    private boolean sendAttached;
    private ComputedReverb lastAppliedReverb;
    private int buffer;

    public CurrentSpeaker() {
        this(null, Optional.empty(), null);
    }

    public CurrentSpeaker(String name, Optional<Vec3> pos) {
        this(name, pos, null);
    }

    public CurrentSpeaker(String name, Optional<Vec3> pos, Reverb forcedReverb) {
        lastEntityPos = Optional.empty();
        setPosition = Optional.empty();
        this.forcedReverb = forcedReverb;
        if (name != null) {
            setNpc(name, pos);
        }
    }

    public void setNpc(String name, Optional<Vec3> pos) {
        // The origin is the manifest's way of saying "no fixed position", so it has to fall through to
        // finding the entity by name rather than parking the voice at world zero.
        setPosition = pos.filter(p -> p.x != 0 || p.y != 0 || p.z != 0);
        rawName = name.toLowerCase().replace(" ", "");
        cachedEntity = null;
    }

    /**
     * Where the speaker is now.
     *
     * <p>Client thread only: it searches the entity list when the cache has gone stale.
     */
    public Optional<Vec3> getUpdatedPosition() {
        if (setPosition.isPresent()) {
            return setPosition;
        }

        if (this.rawName.isEmpty()) {
            return Optional.empty();
        }
        if (!NPCHandler.isCachedValid(cachedEntity)) cachedEntity = NPCHandler.findEntity(rawName);

        // Was not able to find the entity, so just let the sound keep on playing where it is.
        if (cachedEntity == null) {
            if (lastEntityPos.isEmpty()) {
                return Optional.empty();
            }

        } else {
            lastEntityPos = Optional.of(cachedEntity.child.getEyePosition());
        }

        return lastEntityPos;
    }

    /** Client thread. Re-resolves the speaker position and publishes it for the audio thread. */
    public void refreshPosition() {
        publishedPosition = getUpdatedPosition();
    }

    /** The position as of the last client tick. Safe to read from any thread. */
    public Optional<Vec3> publishedPosition() {
        return publishedPosition;
    }

    public AppliedAcoustics acoustics() {
        return acoustics.get();
    }

    public void publishAcoustics(AppliedAcoustics applied) {
        acoustics.set(applied);
    }

    public Reverb forcedReverb() {
        return forcedReverb;
    }

    public AcousticSmoother smoother() {
        return smoother;
    }

    public boolean needsEvaluation() {
        return needsEvaluation;
    }

    public void markEvaluated(long tick) {
        this.needsEvaluation = false;
        this.lastEvaluationTick = tick;
    }

    public long lastEvaluationTick() {
        return lastEvaluationTick;
    }

    /** Forces a fresh measurement on the next client tick, snapping rather than gliding to it. */
    public void requestEvaluation() {
        this.needsEvaluation = true;
    }

    public SourceEffectPool.Handles handles() {
        return handles;
    }

    public void setHandles(SourceEffectPool.Handles handles) {
        this.handles = handles;
    }

    public boolean isOcclusionAttached() {
        return occlusionAttached;
    }

    public void setOcclusionAttached(boolean attached) {
        this.occlusionAttached = attached;
    }

    public boolean isSendAttached() {
        return sendAttached;
    }

    public void setSendAttached(boolean attached) {
        this.sendAttached = attached;
    }

    /** What was last written to this line's effect, so an unchanged update can be skipped. */
    public ComputedReverb lastAppliedReverb() {
        return lastAppliedReverb;
    }

    public void setLastAppliedReverb(ComputedReverb reverb) {
        this.lastAppliedReverb = reverb;
    }

    public int buffer() {
        return buffer;
    }

    public void setBuffer(int buffer) {
        this.buffer = buffer;
    }
}
