package com.wynnvp.wynncraftvp.sound;

import net.minecraft.util.math.Vec3d;

public class SoundObject {

    private final String npcName;
    private final String id;
    private final CustomSoundClass customSoundClass;
    private final int fallOff;
    private final Vector3 position;

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
