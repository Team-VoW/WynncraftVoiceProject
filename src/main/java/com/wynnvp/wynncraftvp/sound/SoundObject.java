/*
 * Copyright © Team-VoW 2024.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound;

import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import javax.swing.text.html.Option;
import java.util.Optional;

public class SoundObject {
    private final String npcName;
    private final String id;
    private final boolean movingSound;
    private final Vector3f position;
    private final int fallOff;

    public SoundObject(String npcName, String id, boolean movingSound, Vector3f position, int fallOff) {
        this.fallOff = fallOff;
        this.position = position;
        this.npcName = npcName;
        this.id = id;
        this.movingSound = movingSound;
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

        if (position == null) return null;

        return Optional.of(new Vec3(position.x, position.y, position.z));
    }

    public int getFallOff() {
        return fallOff;
    }
}
