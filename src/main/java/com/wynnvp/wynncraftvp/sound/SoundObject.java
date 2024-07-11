/*
 * Copyright © Team-VoW 2024.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound;

import org.joml.Vector3f;

public class SoundObject {
    private final String npcName;
    private final String id;
    private final CustomSoundClass customSoundClass;
    private final Vector3f position;
    private final int fallOff;

    public SoundObject(String npcName, String id, CustomSoundClass customSoundClass, Vector3f position, int fallOff) {
        this.fallOff = fallOff;
        this.position = position;
        this.npcName = npcName;
        this.id = id;
        this.customSoundClass = customSoundClass;
    }

    public String getId() {
        return id;
    }

    public String getNpcName() {
        return npcName;
    }

    public CustomSoundClass getCustomSoundClass() {
        return customSoundClass;
    }

    public Vector3f getPosition() {
        return position;
    }

    public int getFallOff() {
        return fallOff;
    }
}
