/*
 * Copyright © Team-VoW 2024.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound;

public class SoundObject {
    private final String npcName;
    private final String id;
    private final CustomSoundClass customSoundClass;
    private final Vector3 position;
    private final int fallOff;

    public SoundObject(String npcName, String id, CustomSoundClass customSoundClass, Vector3 position, int fallOff) {
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

    public Vector3 getPosition() {
        return position;
    }

    public int getFallOff() {
        return fallOff;
    }
}
