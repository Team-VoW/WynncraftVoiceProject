/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.feedback;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class FeedbackSubmissionTest {
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    void everyScoreLocksWhileSendingAndRetriesSameSubmission(int score) {
        FeedbackSubmission s = new FeedbackSubmission("Recover the Past");
        String id = s.id;
        assertTrue(s.beginRating(score));
        assertFalse(s.beginRating(score));
        s.busy = false; // failed request
        assertFalse(s.beginRating(score == 5 ? 1 : score + 1));
        assertTrue(s.beginRating(score));
        assertEquals(id, s.id);
        s.busy = false;
        s.token = "accepted";
        assertFalse(s.beginRating(score));
    }

    @Test
    void independentIdsAndInvalidScores() {
        FeedbackSubmission a = new FeedbackSubmission("Recover the Past");
        FeedbackSubmission b = new FeedbackSubmission("Recover the Past");
        assertNotEquals(a.id, b.id);
        assertFalse(a.beginRating(0));
        assertFalse(a.beginRating(6));
    }
}
