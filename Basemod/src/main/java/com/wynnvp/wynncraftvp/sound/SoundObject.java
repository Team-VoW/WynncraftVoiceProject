package com.wynnvp.wynncraftvp.sound;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SoundObject {

    private final String messageKey;
    private final String npcName;
    private final CustomSoundClass customSoundClass;

}
