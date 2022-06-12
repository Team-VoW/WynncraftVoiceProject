package com.wynnvp.wynncraftvp.sound;

public class SoundObject {

    private final String npcName;
    private final String id;
    private final CustomSoundClass customSoundClass;

    public SoundObject(String npcName, String id, CustomSoundClass customSoundClass) {
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
}
