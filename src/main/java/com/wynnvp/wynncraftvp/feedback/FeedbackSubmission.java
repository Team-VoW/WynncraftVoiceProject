/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.feedback;

import java.util.UUID;

/** One completion owns one immutable rating once a request is attempted. */
public final class FeedbackSubmission {
    public final String id;
    public final String quest;
    public final long created;
    public int score;
    public String token;
    public String draft = "";
    public boolean busy;
    public Long retryAt;

    public FeedbackSubmission(String quest) {
        this(UUID.randomUUID().toString(), quest, System.nanoTime());
    }

    public FeedbackSubmission(String id, String quest, long created) {
        this.id = id;
        this.quest = quest;
        this.created = created;
    }

    public boolean beginRating(int selected) {
        if (selected < 1 || selected > 5 || busy || token != null || (score != 0 && score != selected)) return false;
        score = selected;
        busy = true;
        return true;
    }
}
