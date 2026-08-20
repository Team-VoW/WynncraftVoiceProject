/*
 * Copyright © Team-VoW 2024-2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.events.mixins;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.sound.MasterSoundReroute;
import com.wynnvp.wynncraftvp.sound.NpcSoundBlocker;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mutes the vanilla mob "blips" Wynncraft plays for every dialogue line while one of our
 * voice lines is playing.
 *
 * <p>Also re-tags Wynncraft's MASTER-category sounds as BLOCKS so they get a volume slider of
 * their own — see {@link MasterSoundReroute}.
 *
 * <p>Injects into {@link SoundEngine} rather than {@code SoundManager} because delayed sounds
 * are re-submitted straight to the engine from {@code tickInGameSound}, bypassing the manager.
 */
@Mixin(SoundEngine.class)
public class MixinPlaySoundListener {
    /**
     * Swaps the instance before the engine reads its category. Delayed sounds come back through
     * {@code play} a second time, but by then they already carry the new category and pass straight
     * through.
     */
    @ModifyVariable(method = "play", at = @At("HEAD"), argsOnly = true)
    private SoundInstance vow$rerouteMasterSounds(SoundInstance sound) {
        return MasterSoundReroute.apply(sound);
    }

    @Inject(method = "play", at = @At("HEAD"), cancellable = true)
    private void onPlay(SoundInstance sound, CallbackInfoReturnable<SoundEngine.PlayResult> cir) {
        if (ModCore.config == null || !ModCore.config.isBlockVillagerSoundsDuringVoiceDialog()) return;
        if (!vow$isVoicePlaybackActive()) return;

        Identifier id = sound.getIdentifier();
        if (id == null) return;

        if (!NpcSoundBlocker.isNpcVoiceSound(id.getNamespace(), id.getPath())) {
            if (ModCore.config.isLogBlockedSounds()) {
                ModCore.info("[VOW] Allowed sound during dialogue: " + id);
            }
            return;
        }

        if (ModCore.config.isLogBlockedSounds()) {
            ModCore.info("[VOW] Blocked sound during dialogue: " + id);
        }

        // Returning NOT_STARTED instead of cancelling with a null return value — callers read
        // this result and a null would blow up.
        cir.setReturnValue(SoundEngine.PlayResult.NOT_STARTED);
    }

    /**
     * Overlay dialogue is muted for its whole duration (that system knows when a dialogue box is
     * open). Chat dialogue has no such flag, so it is gated on us actually playing a line: the
     * grace window covers the fetch/decode delay, the source check covers the rest of the line.
     */
    @Unique
    private static boolean vow$isVoicePlaybackActive() {
        if (ModCore.overlayHandler != null && ModCore.overlayHandler.isVoiceDialogActive()) return true;
        if (NpcSoundBlocker.hasRecentVoiceLine()) return true;

        return ModCore.instance != null
                && ModCore.instance.audioPlayer != null
                && ModCore.instance.audioPlayer.openAlPlayer.isPlayingAnything();
    }
}
