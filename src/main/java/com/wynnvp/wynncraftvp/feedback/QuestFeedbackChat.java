/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.feedback;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;

/** Matches the mod's purple prefix and green action links without leaking styles between siblings. */
public final class QuestFeedbackChat {
    private QuestFeedbackChat() {}

    public static MutableComponent message(Component content) {
        return Component.empty()
                .append(Component.literal("[Voices of Wynn] ").withStyle(ChatFormatting.DARK_PURPLE))
                .append(content.copy().withStyle(ChatFormatting.GRAY));
    }

    public static Component prompt(FeedbackSubmission submission) {
        MutableComponent content = Component.literal("How was the voice acting in ")
                .append(Component.literal(submission.quest).withStyle(ChatFormatting.LIGHT_PURPLE))
                .append("?\n  ");
        for (int score = 1; score <= 5; score++) {
            int selected = score;
            content.append(Component.literal("[" + score + "]")
                    .withStyle(style -> style.withColor(ChatFormatting.LIGHT_PURPLE)
                            .withBold(true)
                            .withClickEvent(
                                    new ClickEvent.RunCommand("/vow-feedback " + submission.id + " " + selected))
                            .withHoverEvent(new HoverEvent.ShowText(
                                    Component.literal("Rate voice acting: " + selected + " / 5")))));
            content.append(" ");
        }
        return message(
                content.append(Component.literal("— 1 = Poor, 5 = Excellent").withStyle(ChatFormatting.GRAY)));
    }

    public static Component action(String label, FeedbackSubmission submission, String action) {
        return Component.literal(label)
                .withStyle(style -> style.withColor(ChatFormatting.GREEN)
                        .withUnderlined(true)
                        .withClickEvent(new ClickEvent.RunCommand("/vow-feedback " + submission.id + " " + action))
                        .withHoverEvent(new HoverEvent.ShowText(Component.literal(
                                action.equals("comment")
                                        ? "Leave feedback about " + submission.quest
                                        : "Retry your rating"))));
    }
}
