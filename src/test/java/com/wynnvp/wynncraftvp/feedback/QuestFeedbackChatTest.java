/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.feedback;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.TextColor;
import org.junit.jupiter.api.Test;

class QuestFeedbackChatTest {
    @Test
    void brandedPromptKeepsFiveIndependentLocalChoices() {
        var submission = new FeedbackSubmission("Enzan's Brother");
        var prompt = QuestFeedbackChat.prompt(submission);
        assertEquals(
                TextColor.fromLegacyFormat(ChatFormatting.DARK_PURPLE),
                prompt.getSiblings().getFirst().getStyle().getColor());
        var content = prompt.getSiblings().get(1);
        assertEquals(
                TextColor.fromLegacyFormat(ChatFormatting.GRAY),
                content.getStyle().getColor());
        assertEquals(
                TextColor.fromLegacyFormat(ChatFormatting.LIGHT_PURPLE),
                content.getSiblings().getFirst().getStyle().getColor());
        var choices = content.getSiblings().stream()
                .filter(c -> c.getStyle().getClickEvent() != null)
                .toList();
        assertEquals(5, choices.size());
        for (int i = 0; i < choices.size(); i++) {
            assertEquals(
                    TextColor.fromLegacyFormat(ChatFormatting.LIGHT_PURPLE),
                    choices.get(i).getStyle().getColor());
            assertEquals(
                    "/vow-feedback " + submission.id + " " + (i + 1),
                    ((ClickEvent.RunCommand) choices.get(i).getStyle().getClickEvent()).command());
            assertNotNull(choices.get(i).getStyle().getHoverEvent());
        }
        assertEquals(1, prompt.getString().split("\\[Voices of Wynn\\]", -1).length - 1);
        assertEquals(
                TextColor.fromLegacyFormat(ChatFormatting.GREEN),
                QuestFeedbackChat.action("[Retry]", submission, "1").getStyle().getColor());
    }
}
