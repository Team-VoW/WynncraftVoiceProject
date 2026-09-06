/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.feedback;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.function.LongSupplier;

/** Client-thread completion parser and scheduler. Times are elapsed monotonic nanoseconds. */
public final class QuestCompletionTracker {
    public static final long RECENT = Duration.ofMinutes(15).toNanos();
    public static final long DELAY = Duration.ofSeconds(2).toNanos();
    private static final long DUPLICATE_WINDOW = Duration.ofSeconds(10).toNanos();
    private final LongSupplier clock;
    private Long lastPlayback;
    private Long waitingForName;
    private boolean enabled = true;
    private final Map<String, Long> completions = new HashMap<>();
    private final List<Completion> pending = new ArrayList<>();

    public record Completion(String id, String quest, long completed) {}

    public QuestCompletionTracker(LongSupplier clock) {
        this.clock = clock;
    }

    public void playbackStarted(long timestamp) {
        lastPlayback = timestamp;
    }

    public void setEnabled(boolean value) {
        enabled = value;
        if (!value) {
            pending.clear();
            waitingForName = null;
        }
    }

    public void reset() {
        lastPlayback = null;
        waitingForName = null;
        pending.clear();
        completions.clear();
    }

    public void systemChat(String message) {
        if (!enabled) return;
        long now = clock.getAsLong();
        completions.entrySet().removeIf(e -> now - e.getValue() > DUPLICATE_WINDOW);
        if (waitingForName != null && now - waitingForName > DELAY) waitingForName = null;
        for (String raw : message.replaceAll("(?i)§[0-9a-fk-orx]", "").split("\\R")) {
            String line = raw.replaceAll("(?U)\\s+", " ").trim();
            if (line.isEmpty()) continue;
            if (line.startsWith("[Quest Completed]")) {
                waitingForName = now;
                String suffix = line.substring("[Quest Completed]".length()).trim();
                if (!suffix.isEmpty()) acceptName(suffix, now);
            } else if (waitingForName != null) {
                acceptName(line, now);
            }
        }
    }

    private void acceptName(String name, long now) {
        waitingForName = null;
        // Rewards and chat syntax are not quest names. Matching is deliberately conservative.
        if (name.length() > 200
                || (!Character.isLetter(name.codePointAt(0)) && !name.equals("???"))
                || name.matches(".*[\\[\\]<>:§].*")
                || name.matches("(?i)^(rewards?|experience|combat xp|emeralds?|quest points?)( .*)?$")) return;
        String key = name.toLowerCase(Locale.ROOT);
        if (completions.putIfAbsent(key, now) != null) return;
        if (lastPlayback != null && now - lastPlayback >= 0 && now - lastPlayback <= RECENT)
            pending.add(new Completion(UUID.randomUUID().toString(), name, now));
    }

    public List<Completion> tick() {
        long now = clock.getAsLong();
        List<Completion> ready = new ArrayList<>();
        pending.removeIf(p -> {
            if (now - p.completed >= DELAY) {
                ready.add(p);
                return true;
            }
            return false;
        });
        return ready;
    }
}
