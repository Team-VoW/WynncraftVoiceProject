/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound;

import java.util.Set;

public final class NpcSoundBlocker {
    /**
     * How long after starting a voice line we keep muting blips, to cover the gap between the
     * decision to play and the audio actually being decoded/streamed (remote fetch included).
     */
    private static final long VOICE_LINE_GRACE_MILLIS = 2500;

    /** The exact vanilla sound events Wynncraft uses as dialogue blips. */
    private static final Set<String> NPC_VOICE_SOUNDS = Set.of(
            "entity.villager.ambient",
            "entity.villager.trade",
            "entity.villager.yes",
            "entity.villager.no",
            "entity.villager.celebrate",
            "entity.wandering_trader.ambient",
            "entity.wandering_trader.trade",
            "entity.wandering_trader.yes",
            "entity.wandering_trader.no",
            "entity.zombie_villager.ambient",
            "entity.evoker.ambient",
            "entity.evoker.celebrate",
            "entity.illusioner.ambient",
            "entity.vindicator.ambient",
            "entity.vindicator.celebrate",
            "entity.pillager.ambient",
            "entity.pillager.celebrate",
            "entity.witch.ambient",
            "entity.witch.celebrate",
            "entity.vex.ambient",
            "entity.ravager.ambient",
            "entity.ravager.celebrate",
            "entity.parrot.imitate.evoker",
            "entity.zombie.ambient",
            "entity.husk.ambient",
            "entity.drowned.ambient",
            "entity.silverfish.ambient",
            "entity.silverfish.death",
            "entity.endermite.ambient");

    private static volatile long lastVoiceLineStartedAt = 0;

    private NpcSoundBlocker() {}

    /** Called whenever the mod hands a line to the audio player. */
    public static void markVoiceLineStarted() {
        lastVoiceLineStartedAt = System.currentTimeMillis();
    }

    /** True while we are still within the grace window after starting a line. */
    public static boolean hasRecentVoiceLine() {
        long startedAt = lastVoiceLineStartedAt;
        return startedAt != 0 && System.currentTimeMillis() - startedAt < VOICE_LINE_GRACE_MILLIS;
    }

    public static void reset() {
        lastVoiceLineStartedAt = 0;
    }

    /**
     * @param namespace sound event namespace (only vanilla sounds are ever blocked)
     * @param path      sound event path, e.g. {@code entity.evoker.ambient}
     */
    public static boolean isNpcVoiceSound(String namespace, String path) {
        if (namespace == null || path == null) return false;
        if (!namespace.equals("minecraft")) return false;

        return NPC_VOICE_SOUNDS.contains(path);
    }
}
