/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.line;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class LineDataTest {
    private static LineData of(String realLine) {
        LineData data = new LineData();
        data.setRealLine(realLine);
        return data;
    }

    @Test
    void derivesNameFromSpeakerPrefix() {
        assertEquals(
                "burick", of("Burick: Someone must have lost it or something.").getNPCName());
    }

    @Test
    void derivesMultiWordName() {
        assertEquals(
                "uggwordpollywaggonthemurderous",
                of("Uggword Pollywaggon the Murderous: Hello there.").getNPCName());
    }

    @Test
    void keepsQuestionMarksForUnknownSpeaker() {
        assertEquals("???", of("???: Welcome...").getNPCName());
    }

    @Test
    void stripsLevelTag() {
        assertEquals("tasim", of("[Lv. 42] Tasim: It's been a while.").getNPCName());
    }

    @Test
    void narrationWithoutColonHasNoName() {
        // Regression: this used to report the whole sentence as the NPC name.
        assertEquals("", of("You'll say what you've been avoiding.").getNPCName());
    }

    @Test
    void longSentenceBeforeColonIsNotASpeaker() {
        String narration = "The bandits came sprinting out of my wealthy neighbor's house with some"
                + " of his most prized possessions: gold, jewels and a painting.";
        assertEquals("", of(narration).getNPCName());
    }

    @Test
    void explicitNameWins() {
        LineData data = of("Sovereign Tasim: soldier, it's been a while.");
        data.setNpcName("Sovereign Tasim");
        assertEquals("sovereigntasim", data.getNPCName());
    }

    @Test
    void explicitEmptyNameMarksNarration() {
        // The overlay handler passes "" when there is no nameplate, even if the narration text
        // happens to contain something that looks like a speaker prefix.
        LineData data = of("He turned and said: run.");
        data.setNpcName("");
        assertEquals("", data.getNPCName());
    }
}
