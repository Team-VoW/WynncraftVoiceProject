/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.feedback;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FontDescription;
import net.minecraft.resources.Identifier;
import org.junit.jupiter.api.Test;

class QuestCompletionTextTest {
    private Component spaced(String text) {
        return Component.empty()
                .withStyle(style -> style.withFont(new FontDescription.Resource(Identifier.parse("minecraft:space"))))
                .append(Component.literal("\uDB00\uDC00" + text));
    }

    @Test
    void customFontSplitCompletionPromptsWithCleanNameAfterFifteenMinutes() {
        AtomicLong now = new AtomicLong();
        var tracker = new QuestCompletionTracker(now::get);
        tracker.playbackStarted(0);
        now.set(Duration.ofMinutes(15).toNanos());
        tracker.systemChat(QuestCompletionText.plainText(spaced("§6[Quest Completed]")));
        tracker.systemChat(QuestCompletionText.plainText(spaced("§e§lDearly Departed")));
        tracker.systemChat(QuestCompletionText.plainText(spaced("")));
        tracker.systemChat(QuestCompletionText.plainText(spaced("Rewards:")));
        now.addAndGet(QuestCompletionTracker.DELAY);
        assertEquals(
                List.of("Dearly Departed"),
                tracker.tick().stream()
                        .map(QuestCompletionTracker.Completion::quest)
                        .toList());
        assertTrue(tracker.tick().isEmpty());
    }

    @Test
    void ordinaryFormattingAndPlayerPrefixesArePreserved() {
        assertEquals(
                "§6   [Quest Completed]", QuestCompletionText.plainText(Component.literal("§6   [Quest Completed]")));
        assertEquals(
                "Player:  [Quest Completed]",
                QuestCompletionText.plainText(Component.literal("Player: ").append(spaced("[Quest Completed]"))));
        assertEquals("\uE001", QuestCompletionText.plainText(Component.literal("\uE001")));
    }
}
