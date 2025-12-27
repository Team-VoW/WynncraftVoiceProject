/*
 * Copyright © Team-VoW 2024-2025.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound;

import java.util.Optional;
import net.minecraft.world.phys.Vec3;

public class SoundObject {
    private final String npcName;
    private final String id;
    private final boolean movingSound;
    private final Vec3 position;
    private final int fallOff;
    private final boolean stopSounds;
    private final Reverb reverb;

    public SoundObject(
            String npcName,
            String id,
            boolean movingSound,
            Vec3 position,
            int fallOff,
            boolean stopSounds,
            Reverb reverb) {
        this.fallOff = fallOff;
        this.position = position;
        this.npcName = npcName;
        this.id = id;
        this.movingSound = movingSound;
        this.stopSounds = stopSounds;
        this.reverb = reverb;
    }

    public String getId() {
        return id;
    }

    public String getNpcName() {
        return npcName;
    }

    public String getTrimmedNpcName() {
        return getNpcName().toLowerCase().replace(" ", "");
    }

    public boolean isSoundAtPlayer() {
        return movingSound;
    }

    public Optional<Vec3> getPosition() {
        if (position == null) return Optional.empty();

        return Optional.of(position);
    }

    public int getFallOff() {
        return fallOff;
    }

    public boolean shouldStopSounds() {
        return stopSounds;
    }

    public Reverb getReverb() {
        return reverb;
    }
}
