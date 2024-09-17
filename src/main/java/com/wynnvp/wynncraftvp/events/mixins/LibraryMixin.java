/*
 * Copyright © Team-VoW 2024.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.events.mixins;

import com.mojang.blaze3d.audio.Library;
import java.nio.IntBuffer;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.EXTEfx;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = Library.class, priority = 900)
public class LibraryMixin {
    /**
     * Redirects the call to alcCreateContext to request 4 auxiliary sends
     * @param deviceHandle
     * @param attrList
     * @return
     */
    @Redirect(
            method = "init",
            at = @At(value = "INVOKE", target = "Lorg/lwjgl/openal/ALC10;alcCreateContext(JLjava/nio/IntBuffer;)J"))
    private long requestAuxSends(long deviceHandle, IntBuffer attrList) {
        return ALC10.alcCreateContext(deviceHandle, new int[] {EXTEfx.ALC_MAX_AUXILIARY_SENDS, 4, 0, 0});
    }
}
