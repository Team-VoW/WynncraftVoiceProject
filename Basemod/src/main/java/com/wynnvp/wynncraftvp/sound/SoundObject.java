package com.wynnvp.wynncraftvp.sound;

public class SoundObject {

    private final String npcName;
    private final CustomSoundClass customSoundClass;

    public SoundObject(String npcName, CustomSoundClass customSoundClass) {
        this.npcName = npcName;
        this.customSoundClass = customSoundClass;
    }

    public String getNpcName() {
        return npcName;
    }

    public CustomSoundClass getCustomSoundClass() {
        return customSoundClass;
    }
}
