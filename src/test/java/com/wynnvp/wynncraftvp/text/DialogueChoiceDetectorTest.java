/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.text;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.wynnvp.wynncraftvp.text.DialogueChoiceDetector.Segment;
import java.util.List;
import org.junit.jupiter.api.Test;

class DialogueChoiceDetectorTest {
    // The real font names, from an in-game capture during Ensembles of Hope.
    private static final String BODY = "minecraft:hud/dialogue/text/wynncraft/body_0";
    private static final String NAMEPLATE = "minecraft:hud/dialogue/text/nameplate";
    private static final String CHOICE_BOX = "minecraft:hud/dialogue/style/default/choice";
    private static final String CHOICE_TEXT = "minecraft:hud/dialogue/text/wynncraft/choice_0";

    private static Segment body(String text) {
        return new Segment(BODY, text);
    }

    // -------------------------------------------------------------------------
    // Ordinary dialogue must stay untouched
    // -------------------------------------------------------------------------

    @Test
    void plainDialogueIsNotAChoice() {
        assertFalse(DialogueChoiceDetector.isChoice(
                List.of(new Segment(NAMEPLATE, "Tasim"), body("It's been a while, hasn't it? We should catch up!"))));
    }

    @Test
    void emptyOverlayIsNotAChoice() {
        assertFalse(DialogueChoiceDetector.isChoice(List.of()));
        assertFalse(DialogueChoiceDetector.isChoice(null));
    }

    @Test
    void continuePromptIsNotAChoice() {
        assertFalse(
                DialogueChoiceDetector.isChoice(List.of(body("Follow me, soldier."), body("Press SHIFT to continue"))));
    }

    @Test
    void aLoneNumberInProseIsNotAChoice() {
        assertFalse(DialogueChoiceDetector.isChoice(List.of(body("I have 3 apples and I need 2 more."))));
    }

    @Test
    void theHudSelectorFontIsNotAChoice() {
        // "minecraft:hud/selector/..." contains "select" and is on screen permanently. Matching it
        // would turn auto progress off for the whole session.
        assertFalse(DialogueChoiceDetector.isChoice(List.of(
                new Segment("minecraft:hud/selector/default/top_left", "󰀈"),
                new Segment("minecraft:hud/selector/default/bottom_middle", "󰀲"),
                body("Follow me, soldier."))));
    }

    @Test
    void anOrdinaryDialogueOverlayIsNotAChoice() {
        // The full font set of a normal line, from the same capture.
        assertFalse(DialogueChoiceDetector.isChoice(List.of(
                new Segment("minecraft:hud/dialogue/style/default/box", "󰀀"),
                new Segment("minecraft:hud/dialogue/style/default/frame", "󰀠"),
                new Segment("minecraft:hud/dialogue/style/default/nameplate", "󰀡"),
                new Segment("minecraft:hud/dialogue/portrait", "󰀨"),
                new Segment("minecraft:hud/dialogue/effect/progress", "󰀐"),
                new Segment("minecraft:hud/dialogue/text/control", "󰀗"),
                new Segment(NAMEPLATE, "Rex"),
                body("How do we feel about light terrorism?"))));
    }

    @Test
    void aDialogueCounterIsNotAChoice() {
        assertFalse(DialogueChoiceDetector.isChoice(List.of(body("[2/5] Aledar: We are almost there."))));
    }

    // -------------------------------------------------------------------------
    // Choices
    // -------------------------------------------------------------------------

    @Test
    void theRealChoiceOverlayIsAChoice() {
        // Trimmed from the capture: the box style, then one text font per answer slot.
        assertTrue(DialogueChoiceDetector.isChoice(List.of(
                new Segment(CHOICE_BOX, "󰀣"),
                new Segment(CHOICE_TEXT, "Light terrorism is fine"),
                new Segment("minecraft:hud/dialogue/text/wynncraft/choice_1", "Absolutely not"))));
    }

    // -------------------------------------------------------------------------
    // Answer fingerprint — tells one question from the next when the box never leaves
    // -------------------------------------------------------------------------

    @Test
    void theSignatureIsTheAnswerTextOnly() {
        String signature = DialogueChoiceDetector.signature(List.of(
                new Segment(CHOICE_BOX, "󰀣"),
                new Segment(CHOICE_TEXT, "Yes"),
                new Segment("minecraft:hud/dialogue/text/wynncraft/choice_1", "No"),
                body("Well?")));

        assertTrue(signature.contains("Yes"));
        assertTrue(signature.contains("No"));
        assertFalse(signature.contains("Well?"), "Body text would change every tick as it types");
        assertFalse(signature.contains("󰀣"), "Box glyphs animate and would fake a new question");
    }

    @Test
    void differentAnswersProduceDifferentSignatures() {
        String first = DialogueChoiceDetector.signature(List.of(new Segment(CHOICE_TEXT, "Yes")));
        String second = DialogueChoiceDetector.signature(List.of(new Segment(CHOICE_TEXT, "Maybe")));
        assertFalse(first.equals(second));
    }

    @Test
    void anOverlayWithNoAnswersHasAnEmptySignature() {
        assertTrue(
                DialogueChoiceDetector.signature(List.of(body("Just talking."))).isEmpty());
        assertTrue(DialogueChoiceDetector.signature(null).isEmpty());
    }

    @Test
    void aRenamedDialogueFontNamingOptionsIsStillAChoice() {
        assertTrue(DialogueChoiceDetector.isChoice(
                List.of(body("What will it be?"), new Segment("minecraft:hud/dialogue/text/option_0", "Yes"))));
    }

    @Test
    void theSelectAnOptionPromptIsAChoice() {
        assertTrue(DialogueChoiceDetector.isChoice(List.of(body("Select an option to continue"))));
    }

    @Test
    void promptDetectionIsCaseInsensitive() {
        assertTrue(DialogueChoiceDetector.isChoice(List.of(body("SELECT AN OPTION"))));
    }

    @Test
    void aShiftToSelectPromptIsAChoice() {
        assertTrue(DialogueChoiceDetector.isChoice(List.of(body("Press SHIFT to select"))));
    }

    @Test
    void aBracketedNumberedListIsAChoice() {
        assertTrue(DialogueChoiceDetector.isChoice(
                List.of(body("[1] Sure, I'll help you."), body("[2] Not interested."))));
    }

    @Test
    void aDottedNumberedListIsAChoice() {
        assertTrue(DialogueChoiceDetector.isChoice(
                List.of(body("1. Sure, I'll help you."), body("2. Not interested."), body("3. Tell me more."))));
    }

    @Test
    void numbersMustRunInOrderToCountAsAList() {
        // "2." then "1." is prose that happens to contain numbers, not a list of answers.
        assertFalse(DialogueChoiceDetector.isChoice(List.of(body("Chapter 2. Then chapter 1. Read on."))));
    }

    @Test
    void customFontGlyphsBetweenOptionsDoNotHideTheList() {
        // Wynncraft draws its dialogue box out of private-use glyphs; they must not weld the marker
        // onto the word before it and stop the pattern from matching.
        assertTrue(DialogueChoiceDetector.isChoice(List.of(body("[1] Yes[2] No"))));
    }
}
