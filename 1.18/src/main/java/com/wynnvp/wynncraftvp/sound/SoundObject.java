package com.wynnvp.wynncraftvp.sound;

import net.minecraft.world.phys.Vec3;

public class SoundObject {

    private final String npcName;
    private final String id;
    private final CustomSoundClass customSoundClass;
    private final Vec3 position;
    private final int fallOff;


    public SoundObject(String npcName, String id, CustomSoundClass customSoundClass, Vec3 position, int fallOff) {
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

    public Vec3 getPosition() {
        return position;
    }

    public int getFallOff() {
        return fallOff;
    }


}
