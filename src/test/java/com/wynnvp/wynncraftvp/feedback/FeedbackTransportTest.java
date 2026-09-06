/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.feedback;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.net.http.HttpTimeoutException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class FeedbackTransportTest {
    @Test
    void failedRatingAndCommentRetriesReusePayloadAndNeverSendAccountIdentity() throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        List<String> bodies = new CopyOnWriteArrayList<>();
        List<String> methods = new CopyOnWriteArrayList<>();
        AtomicInteger attempts = new AtomicInteger();
        server.createContext("/feedback/quests", exchange -> {
            bodies.add(new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8));
            methods.add(exchange.getRequestMethod());
            int attempt = attempts.getAndIncrement();
            if (attempt == 0) exchange.getResponseHeaders().add("Retry-After", "5");
            exchange.sendResponseHeaders(attempt == 0 ? 429 : attempt == 2 ? 500 : 204, -1);
            exchange.close();
        });
        server.start();
        try {
            FeedbackTransport transport = new FeedbackTransport(
                    "http://127.0.0.1:" + server.getAddress().getPort() + "/feedback/quests", Duration.ofSeconds(2));
            FeedbackSubmission s = new FeedbackSubmission("Recover the Past");
            s.beginRating(2);
            String installation = UUID.randomUUID().toString();
            var failed = transport.rate(s, installation, "v2.2.0").get(5, TimeUnit.SECONDS);
            assertEquals(429, failed.statusCode());
            assertEquals("5", failed.headers().firstValue("Retry-After").orElseThrow());
            transport.rate(s, installation, "v2.2.0").get(5, TimeUnit.SECONDS);
            assertEquals(bodies.get(0), bodies.get(1));
            var payload = JsonParser.parseString(bodies.get(0)).getAsJsonObject();
            assertEquals(5, payload.size());
            assertEquals(s.id, payload.get("submissionId").getAsString());
            assertEquals(installation, payload.get("installationId").getAsString());
            assertFalse(payload.has("uuid"));
            assertFalse(payload.has("playerName"));
            s.token = "test-edit-token";
            s.draft = "Less shouting\nMore warmth";
            assertEquals(500, transport.comment(s).get(5, TimeUnit.SECONDS).statusCode());
            assertEquals("Less shouting\nMore warmth", s.draft);
            assertEquals(204, transport.comment(s).get(5, TimeUnit.SECONDS).statusCode());
            assertEquals(bodies.get(2), bodies.get(3));
            assertEquals(List.of("POST", "POST", "PUT", "PUT"), methods);
        } finally {
            server.stop(0);
        }
    }

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void asyncRequestsHaveFiniteTimeouts(boolean stallBody) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            server.setExecutor(executor);
            server.createContext("/feedback", exchange -> {
                try {
                    if (stallBody) {
                        exchange.sendResponseHeaders(200, 2);
                        exchange.getResponseBody().write(1);
                        exchange.getResponseBody().flush();
                    }
                    Thread.sleep(500);
                    if (!stallBody) exchange.sendResponseHeaders(204, -1);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                } finally {
                    exchange.close();
                }
            });
            server.start();
            try {
                FeedbackTransport transport = new FeedbackTransport(
                        "http://127.0.0.1:" + server.getAddress().getPort() + "/feedback", Duration.ofMillis(50));
                var future = transport.rate(
                        new FeedbackSubmission("Recover the Past"),
                        UUID.randomUUID().toString(),
                        "test");
                ExecutionException error =
                        assertThrows(ExecutionException.class, () -> future.get(5, TimeUnit.SECONDS));
                assertTrue(error.getCause() instanceof HttpTimeoutException
                        || error.getCause() instanceof java.util.concurrent.TimeoutException);
            } finally {
                server.stop(0);
            }
        }
    }
}
