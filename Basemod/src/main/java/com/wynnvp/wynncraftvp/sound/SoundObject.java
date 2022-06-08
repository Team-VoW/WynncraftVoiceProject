package com.wynnvp.wynncraftvp.sound;

public class SoundObject {

    private final String messageKey;
    private final String npcName;
    private final CustomSoundClass customSoundClass;

    public SoundObject(String messageKey, String npcName, CustomSoundClass customSoundClass) {
        this.messageKey = messageKey;
        this.npcName = npcName;
        this.customSoundClass = customSoundClass;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public String getNpcName() {
        return npcName;
    }

    public CustomSoundClass getCustomSoundClass() {
        return customSoundClass;
    }
}
