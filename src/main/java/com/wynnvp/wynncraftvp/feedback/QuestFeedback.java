/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.feedback;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wynnvp.wynncraftvp.ModCore;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public final class QuestFeedback {
    private static final String API = "https://voicesofwynn.com/vow-api/feedback/quests";
    private static final FeedbackTransport TRANSPORT = new FeedbackTransport(API, Duration.ofSeconds(15));
    private static final QuestCompletionTracker TRACKER = new QuestCompletionTracker(System::nanoTime);
    private static final Map<String, FeedbackSubmission> SUBMISSIONS = new HashMap<>();
    private static volatile long connectionGeneration;
    private static final java.util.concurrent.ConcurrentLinkedQueue<Playback> PLAYBACKS =
            new java.util.concurrent.ConcurrentLinkedQueue<>();

    private record Playback(long generation, long time) {}

    private QuestFeedback() {}

    public static void initialize() {
        try {
            UUID id = UUID.fromString(ModCore.config.feedbackInstallationId);
            if (id.equals(new UUID(0, 0))) throw new IllegalArgumentException();
        } catch (IllegalArgumentException | NullPointerException ex) {
            ModCore.config.feedbackInstallationId = UUID.randomUUID().toString();
            ModCore.config.save();
        }
    }

    public static long generation() {
        return connectionGeneration;
    }

    public static void playbackStarted(long generation) {
        PLAYBACKS.add(new Playback(generation, System.nanoTime()));
    }

    private static void sync() {
        TRACKER.setEnabled(ModCore.config != null && ModCore.config.questFeedbackPrompts);
        Playback playback;
        while ((playback = PLAYBACKS.poll()) != null) {
            if (playback.generation == connectionGeneration) TRACKER.playbackStarted(playback.time);
        }
    }

    public static void systemChat(String message) {
        if (!ModCore.inLiveWynnServer) return;
        sync();
        TRACKER.systemChat(message);
    }

    public static void tick() {
        sync();
        SUBMISSIONS
                .values()
                .removeIf(
                        s -> System.nanoTime() - s.created > Duration.ofHours(2).toNanos()
                                && !s.busy
                                && !(Minecraft.getInstance().screen instanceof QuestFeedbackScreen screen
                                        && screen.submission() == s));
        for (QuestCompletionTracker.Completion completion : TRACKER.tick()) {
            String quest = completion.quest();
            FeedbackSubmission s = new FeedbackSubmission(completion.id(), quest, completion.completed());
            SUBMISSIONS.put(s.id, s);
            Minecraft.getInstance().gui.getChat().addMessage(QuestFeedbackChat.prompt(s));
        }
    }

    public static void disconnect() {
        connectionGeneration++;
        PLAYBACKS.clear();
        TRACKER.reset();
        SUBMISSIONS.clear();
        if (Minecraft.getInstance().screen instanceof QuestFeedbackScreen)
            Minecraft.getInstance().setScreen(null);
    }

    public static void disablePrompts() {
        TRACKER.setEnabled(false);
    }

    public static boolean active(FeedbackSubmission s) {
        return SUBMISSIONS.get(s.id) == s;
    }

    /** Intercept the entire private command namespace, including malformed and stale links. */
    public static boolean command(String command) {
        if (!command.equals("vow-feedback") && !command.startsWith("vow-feedback ")) return false;
        String[] parts = command.split(" ");
        FeedbackSubmission s = parts.length == 3 ? SUBMISSIONS.get(parts[1]) : null;
        if (s == null) {
            say(Component.literal("This feedback link has expired."));
            return true;
        }
        if (parts[2].equals("comment") && s.token != null && s.score <= 3) {
            Minecraft.getInstance().execute(() -> {
                if (active(s)) Minecraft.getInstance().setScreen(new QuestFeedbackScreen(s));
            });
        } else {
            try {
                rate(s, Integer.parseInt(parts[2]));
            } catch (NumberFormatException ignored) {
                say(Component.literal("This feedback link is invalid."));
            }
        }
        return true;
    }

    private static void rate(FeedbackSubmission s, int score) {
        if (s.retryAt != null && System.nanoTime() - s.retryAt < 0) {
            return;
        }
        if (!s.beginRating(score)) return;
        request(s, TRANSPORT.rate(s, ModCore.config.feedbackInstallationId, ModCore.instance.getVersion()), result -> {
            if (result != null && result.statusCode() == 200) {
                try {
                    JsonObject data = JsonParser.parseString(result.body()).getAsJsonObject();
                    String token = data.get("editToken").getAsString();
                    if (!s.id.equals(data.get("id").getAsString()) || token.length() < 32)
                        throw new IllegalArgumentException();
                    s.token = token;
                    say(Component.literal("Thank you for rating the voice acting in " + s.quest + "!"));
                    if (s.score <= 3)
                        say(Component.literal("What could we improve about the voice acting? ")
                                .append(link("[Leave feedback]", s, "comment")));
                    return;
                } catch (RuntimeException ignored) {
                    /* Invalid response is not confirmed success. */
                }
            }
            say(Component.literal("Could not submit your rating. " + retryMessage(result))
                    .append(link("[Retry]", s, Integer.toString(s.score))));
        });
    }

    public static void comment(FeedbackSubmission s, Consumer<String> finished) {
        if (!active(s)) {
            finished.accept("This feedback link has expired.");
            return;
        }
        if (s.busy || s.token == null) return;
        if (s.retryAt != null && System.nanoTime() - s.retryAt < 0) {
            finished.accept("Please wait before retrying.");
            return;
        }
        if (s.draft.isBlank() || s.draft.length() > 2000) {
            finished.accept("Enter 1–2,000 characters.");
            return;
        }
        s.busy = true;
        request(s, TRANSPORT.comment(s), result -> {
            if (result != null && result.statusCode() == 204) {
                say(Component.literal("Thank you for your feedback about " + s.quest + "!"));
                finished.accept(null);
            } else finished.accept("Could not submit feedback. " + retryMessage(result) + "Use Retry to try again.");
        });
    }

    private static String retryMessage(HttpResponse<String> result) {
        if (result != null && result.statusCode() >= 500)
            return "The feedback service is temporarily unavailable; please try again later. ";
        return result != null && result.statusCode() == 429
                ? "Too many requests; wait "
                        + result.headers().firstValue("Retry-After").orElse("3600") + " seconds. "
                : "";
    }

    private static void request(
            FeedbackSubmission s,
            java.util.concurrent.CompletableFuture<HttpResponse<String>> request,
            Consumer<HttpResponse<String>> done) {
        request.whenComplete((response, error) -> Minecraft.getInstance().execute(() -> {
            if (!active(s)) return;
            s.busy = false;
            if (error != null || response == null || response.statusCode() >= 400) {
                ModCore.warn("Quest feedback request failed: "
                        + (response == null ? "network error or timeout" : "HTTP " + response.statusCode()));
                long seconds = response != null && response.statusCode() == 429 ? 3600 : 2;
                try {
                    if (response != null)
                        seconds = Long.parseLong(
                                response.headers().firstValue("Retry-After").orElse(Long.toString(seconds)));
                } catch (NumberFormatException ignored) {
                }
                s.retryAt = System.nanoTime()
                        + Duration.ofSeconds(Math.clamp(seconds, 1, 3600)).toNanos();
            }
            done.accept(error == null ? response : null);
        }));
    }

    private static Component link(String label, FeedbackSubmission s, String action) {
        return QuestFeedbackChat.action(label, s, action);
    }

    private static void say(Component text) {
        Minecraft.getInstance().gui.getChat().addMessage(QuestFeedbackChat.message(text));
    }
}
