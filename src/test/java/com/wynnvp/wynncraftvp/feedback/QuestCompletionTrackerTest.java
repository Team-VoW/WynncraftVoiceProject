/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.feedback;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.Test;

class QuestCompletionTrackerTest {
    @Test
    void unicodeWhitespaceAndCompletionIds() {
        voice();
        tracker.systemChat("[Quest Completed]\nRecover\u00a0the Past");
        tracker.systemChat("[Quest Completed] ???");
        now.set(QuestCompletionTracker.DELAY);
        var completions = tracker.tick();
        assertEquals(
                List.of("Recover the Past", "???"),
                completions.stream()
                        .map(QuestCompletionTracker.Completion::quest)
                        .toList());
        assertEquals(
                2,
                completions.stream()
                        .map(QuestCompletionTracker.Completion::id)
                        .distinct()
                        .count());
        for (var completion : completions) {
            java.util.UUID.fromString(completion.id());
            assertEquals(0, completion.completed());
        }
    }

    private final AtomicLong now = new AtomicLong();
    private final QuestCompletionTracker tracker = new QuestCompletionTracker(now::get);

    private List<String> ready() {
        return tracker.tick().stream()
                .map(QuestCompletionTracker.Completion::quest)
                .toList();
    }

    private void voice() {
        tracker.playbackStarted(now.get());
    }

    private List<String> afterDelay() {
        now.addAndGet(QuestCompletionTracker.DELAY);
        return ready();
    }

    @Test
    void multilineFormattingRewardsAndExactDelay() {
        voice();
        tracker.systemChat("§a[Quest Completed]\n§6Recover the Past\n+1000 Combat XP\n+64 Emeralds");
        now.set(QuestCompletionTracker.DELAY - 1);
        assertTrue(ready().isEmpty());
        now.incrementAndGet();
        assertEquals(List.of("Recover the Past"), ready());
        assertTrue(ready().isEmpty());
    }

    @Test
    void consecutivePacketsAndDuplicateSuppression() {
        voice();
        tracker.systemChat("[Quest Completed]");
        tracker.systemChat("Recover the Past");
        tracker.systemChat("[Quest Completed]\nRecover the Past");
        assertEquals(List.of("Recover the Past"), afterDelay());
    }

    @Test
    void independentQuestsAndLaterRepeatCompletion() {
        voice();
        tracker.systemChat("[Quest Completed] Recover the Past");
        tracker.systemChat("[Quest Completed]\nKing's Recruit");
        assertEquals(List.of("Recover the Past", "King's Recruit"), afterDelay());
        now.addAndGet(11_000_000_000L);
        tracker.systemChat("[Quest Completed] Recover the Past");
        assertEquals(List.of("Recover the Past"), afterDelay());
    }

    @Test
    void boundaryInclusiveButOneNanosecondLaterIneligible() {
        voice();
        now.set(QuestCompletionTracker.RECENT);
        tracker.systemChat("[Quest Completed] Recover the Past");
        now.incrementAndGet();
        tracker.systemChat("[Quest Completed] King's Recruit");
        assertEquals(List.of("Recover the Past"), afterDelay());
    }

    @Test
    void noPlaybackNoPromptAndPlaybackAfterCompletionDoesNotQualify() {
        tracker.systemChat("[Quest Completed] Recover the Past");
        voice();
        assertTrue(afterDelay().isEmpty());
    }

    @Test
    void disablingCancelsPendingAndIgnoresPackets() {
        voice();
        tracker.systemChat("[Quest Completed] Recover the Past");
        tracker.setEnabled(false);
        tracker.systemChat("[Quest Completed] King's Recruit");
        tracker.setEnabled(true);
        assertTrue(afterDelay().isEmpty());
    }

    @Test
    void disconnectClearsEligibilityPendingAndPartialMessages() {
        voice();
        tracker.systemChat("[Quest Completed] Recover the Past");
        tracker.systemChat("[Quest Completed]");
        tracker.reset();
        tracker.systemChat("King's Recruit");
        tracker.systemChat("[Quest Completed] Another Quest");
        assertTrue(afterDelay().isEmpty());
    }

    @Test
    void rejectsChatRewardsAndExpiredSplitPacket() {
        voice();
        tracker.systemChat("Player: [Quest Completed] Fake Quest");
        tracker.systemChat("[Quest Completed]\n+100 XP");
        tracker.systemChat("[Quest Completed]\nRewards");
        tracker.systemChat("[Quest Completed]");
        now.addAndGet(QuestCompletionTracker.DELAY + 1);
        tracker.systemChat("Recover the Past");
        assertTrue(afterDelay().isEmpty());
    }
}
