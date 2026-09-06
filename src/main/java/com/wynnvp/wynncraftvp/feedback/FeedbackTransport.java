/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.feedback;

import com.google.gson.Gson;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/** Contains the entire public wire payload; it has no access to a Minecraft account. */
public final class FeedbackTransport {
    private final String endpoint;
    private final Duration timeout;
    private final HttpClient client;
    private final Gson json = new Gson();

    public FeedbackTransport(String endpoint, Duration timeout) {
        this.endpoint = endpoint;
        this.timeout = timeout;
        client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build();
    }

    public CompletableFuture<HttpResponse<String>> rate(FeedbackSubmission s, String installation, String version) {
        return send(
                endpoint,
                "POST",
                Map.of(
                        "submissionId",
                        s.id,
                        "installationId",
                        installation,
                        "questName",
                        s.quest,
                        "score",
                        s.score,
                        "modVersion",
                        version));
    }

    public CompletableFuture<HttpResponse<String>> comment(FeedbackSubmission s) {
        return send(endpoint + "/" + s.id + "/comment", "PUT", Map.of("editToken", s.token, "comment", s.draft));
    }

    private CompletableFuture<HttpResponse<String>> send(String uri, String method, Map<String, ?> body) {
        HttpRequest request = HttpRequest.newBuilder(URI.create(uri))
                .timeout(timeout)
                .header("Content-Type", "application/json")
                .method(method, HttpRequest.BodyPublishers.ofString(json.toJson(body)))
                .build();
        var pending = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        // Bound the entire response, including a body that stalls after headers have arrived.
        var bounded = pending.copy().orTimeout(timeout.toMillis(), TimeUnit.MILLISECONDS);
        bounded.whenComplete((response, error) -> {
            if (error != null) pending.cancel(true);
        });
        return bounded;
    }
}
