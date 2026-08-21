/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.npc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class SpeakerNamesTest {
    @Nested
    @DisplayName("clean")
    class Clean {
        @Test
        void lowercasesAndDropsSpacing() {
            assertEquals("captainfuhue", SpeakerNames.clean("Captain Fuhue"));
        }

        @Test
        void dropsColourCodesWithTheirLetter() {
            // Without taking the code's letter too, the "a" of "§a" would be left behind in the name.
            assertEquals("tasim", SpeakerNames.clean("§aTasim§r"));
        }

        @Test
        void keepsQuestionMarksForUnnamedSpeakers() {
            assertEquals("???", SpeakerNames.clean("???"));
        }

        @Test
        void keepsDigits() {
            assertEquals("guard2", SpeakerNames.clean("Guard #2"));
        }

        @Test
        void dropsPunctuationAndNonAscii() {
            assertEquals("sirlorathebrv", SpeakerNames.clean("Sir Lora, the Brávé"));
        }

        @Test
        void survivesATrailingSectionSign() {
            assertEquals("a", SpeakerNames.clean("a§"));
        }
    }

    @Nested
    @DisplayName("names")
    class Names {
        @Test
        void tagMayCarryATitleTheSpeakerDoesNot() {
            assertTrue(SpeakerNames.names("captainfuhue", "fuhue"));
        }

        @Test
        void exactMatchCounts() {
            assertTrue(SpeakerNames.names("tasim", "tasim"));
        }

        @Test
        void unrelatedNamesDoNotMatch() {
            assertFalse(SpeakerNames.names("captainrurie", "fuhue"));
        }

        @Test
        void aTagShorterThanTheSpeakerIsNotANamedMatch() {
            assertFalse(SpeakerNames.names("tasim", "sovereigntasim"));
        }

        @Test
        void anEmptyTagNamesNobody() {
            assertFalse(SpeakerNames.names("", "fuhue"));
            assertFalse(SpeakerNames.names("", ""));
        }
    }

    @Nested
    @DisplayName("isShorteningOf")
    class IsShorteningOf {
        @Test
        void tagLeavesOffTheSpeakersTitle() {
            assertTrue(SpeakerNames.isShorteningOf("tasim", "sovereigntasim"));
        }

        @Test
        void tooShortToBeAnythingButACoincidence() {
            assertFalse(SpeakerNames.isShorteningOf("so", "sovereigntasim"));
            assertFalse(SpeakerNames.isShorteningOf("", "sovereigntasim"));
        }

        @Test
        void threeCharactersIsTheFloor() {
            assertTrue(SpeakerNames.isShorteningOf("sov", "sovereigntasim"));
        }

        @Test
        void aTagThatIsNotPartOfTheSpeakerIsNotAShortening() {
            assertFalse(SpeakerNames.isShorteningOf("rurie", "sovereigntasim"));
        }
    }

    @Nested
    @DisplayName("nametagName")
    class NametagName {
        @Test
        void keepsAPlainNametag() {
            assertEquals("captainfuhue", SpeakerNames.nametagName("§aCaptain Fuhue"));
        }

        @Test
        void takesTheNameOffARealWynncraftNpcTag() {
            // Exactly what /vow-debug tags reported in game: the name, then the [NPC] badge drawn out
            // of private-use font glyphs on a second line. Getting this wrong finds no NPCs at all.
            assertEquals("captainrurie", SpeakerNames.nametagName("Captain Rurie\n"));
        }

        @Test
        void ignoresEverythingBelowTheFirstLine() {
            assertEquals("captain", SpeakerNames.nametagName("Captain\nFuhue"));
        }

        @Test
        void stripsTheBadgeGlyphsWynncraftDrawsIntoTags() {
            // Badges are private-use codepoints the resource pack renders as sprites. None of them
            // are letters, so cleaning takes them out and leaves the name behind.
            assertEquals("auburnfox", SpeakerNames.nametagName("Auburn Fox \ue000\ue001\uf8ff\n\ue002"));
        }

        @Test
        void dropsProse() {
            assertEquals(
                    "",
                    SpeakerNames.nametagName(
                            "It's shut up now, but it was speaking just a moment ago, so do not worry."));
        }

        @Test
        void dropsTextWithNoNameLeftInIt() {
            assertEquals("", SpeakerNames.nametagName("---"));
            assertEquals("", SpeakerNames.nametagName(""));
        }

        @Test
        void dropsAShortLineThatCleansDownToNothingButDecoration() {
            assertEquals("", SpeakerNames.nametagName("§7[§b§7]"));
        }

        @Test
        void keepsAMobTagWithItsLevel() {
            assertEquals("soldierlv117", SpeakerNames.nametagName("§cSoldier §7LV 117"));
        }
    }
}
