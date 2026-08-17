/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class NpcSoundBlockerTest {
    @ParameterizedTest
    @ValueSource(
            strings = {
                "entity.villager.ambient",
                "entity.villager.celebrate",
                "entity.villager.trade",
                "entity.villager.yes",
                "entity.villager.no",
                "entity.wandering_trader.no",
                "entity.zombie_villager.ambient",
                "entity.evoker.ambient",
                "entity.witch.ambient",
                "entity.vex.ambient",
                "entity.pillager.ambient",
                "entity.vindicator.ambient",
                "entity.illusioner.ambient",
                "entity.ravager.ambient",
                "entity.silverfish.ambient",
                "entity.silverfish.death",
                "entity.parrot.imitate.evoker",
                "entity.zombie.ambient",
                "entity.husk.ambient",
                "entity.drowned.ambient",
                "entity.endermite.ambient"
            })
    void blocksNpcBlipSounds(String path) {
        assertTrue(NpcSoundBlocker.isNpcVoiceSound("minecraft", path), path + " should be blocked");
    }

    @ParameterizedTest
    @ValueSource(
            strings = {
                // Scripted cutscene effects must survive, including on mobs whose ambient
                // sound IS a blocked blip — this is why matching is per-event, not per-family.
                "entity.player.attack.strong",
                "entity.player.attack.knockback",
                "entity.ravager.attack",
                "entity.vindicator.hurt",
                "entity.villager.hurt",
                "entity.villager.death",
                "entity.evoker.cast_spell",
                "entity.evoker.prepare_summon",
                "entity.witch.throw",
                "entity.zombie.hurt",
                "entity.generic.explode",
                "entity.arrow.hit_player",
                "entity.enderman.teleport",
                // Ambients of mobs that are not used as NPC voices
                "entity.enderman.ambient",
                "entity.cat.ambient",
                "entity.parrot.ambient",
                // Non-entity sounds must survive
                "block.stone.break",
                "ui.button.click",
                "music.game",
                "ambient.cave"
            })
    void keepsUnrelatedSounds(String path) {
        assertFalse(NpcSoundBlocker.isNpcVoiceSound("minecraft", path), path + " should not be blocked");
    }

    @Test
    void ignoresNonVanillaNamespaces() {
        assertFalse(NpcSoundBlocker.isNpcVoiceSound("wynnvp", "entity.villager.ambient"));
    }

    @Test
    void handlesMissingValues() {
        assertFalse(NpcSoundBlocker.isNpcVoiceSound(null, "entity.villager.ambient"));
        assertFalse(NpcSoundBlocker.isNpcVoiceSound("minecraft", null));
        assertFalse(NpcSoundBlocker.isNpcVoiceSound("minecraft", ""));
        assertFalse(NpcSoundBlocker.isNpcVoiceSound("minecraft", "entity.zombie."));
    }

    @Test
    void voiceLineWindowTracksLastLine() {
        NpcSoundBlocker.reset();
        assertFalse(NpcSoundBlocker.hasRecentVoiceLine());

        NpcSoundBlocker.markVoiceLineStarted();
        assertTrue(NpcSoundBlocker.hasRecentVoiceLine());

        NpcSoundBlocker.reset();
        assertFalse(NpcSoundBlocker.hasRecentVoiceLine());
    }
}
