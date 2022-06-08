package com.wynnvp.wynncraftvp.sound;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.util.SoundEvent;

@Getter
@RequiredArgsConstructor
public class CustomSoundClass {

    private final SoundEvent soundEvent;
    private final boolean movingSound;

}
