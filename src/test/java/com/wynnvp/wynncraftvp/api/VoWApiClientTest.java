/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpTimeoutException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class VoWApiClientTest {
    @ParameterizedTest
    @ValueSource(strings = {"none", "notify", "disable"})
    void bootupPostsContractAndReadsEveryAction(String action) throws Exception {
        List<String> methods = new ArrayList<>();
        List<String> paths = new ArrayList<>();
        List<String> bodies = new ArrayList<>();
        HttpServer server = server("/vow-api/mod/bootup", exchange -> {
            methods.add(exchange.getRequestMethod());
            paths.add(exchange.getRequestURI().getPath());
            bodies.add(readBody(exchange));
            respond(exchange, 200, bootupJson(action, "Glück für alle ☃"));
        });
        try {
            VoWApiClient client = client(server, Duration.ofSeconds(2));
            String clientId = "0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef";

            VoWApiClient.BootupResponse response = client.bootup(clientId, "2.2.0");

            assertEquals(action, response.update().action());
            assertEquals("Glück für alle ☃", response.funFact());
            assertEquals(List.of("One", "Zwei ✓"), response.broadcasts());
            assertEquals(List.of("POST"), methods);
            assertEquals(List.of("/vow-api/mod/bootup"), paths);
            JsonObject payload = JsonParser.parseString(bodies.getFirst()).getAsJsonObject();
            assertEquals(2, payload.size());
            assertEquals(clientId, payload.get("clientId").getAsString());
            assertEquals("2.2.0", payload.get("modVersion").getAsString());
        } finally {
            server.stop(0);
        }
    }

    @Test
    void nullableFunFactIsAccepted() throws Exception {
        HttpServer server = server("/vow-api/mod/bootup", exchange -> respond(exchange, 200, bootupJson("none", null)));
        try {
            assertEquals(
                    null,
                    client(server, Duration.ofSeconds(2))
                            .bootup("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "2.2.0")
                            .funFact());
        } finally {
            server.stop(0);
        }
    }

    @ParameterizedTest
    @ValueSource(
            strings = {
                "{}",
                "{\"update\":{}}",
                "{\"update\":{\"action\":\"surprise\"},\"audio\":{},\"broadcasts\":[]}",
                "not-json"
            })
    void malformedBootupResponsesFail(String body) throws Exception {
        HttpServer server = server("/vow-api/mod/bootup", exchange -> respond(exchange, 200, body));
        try {
            assertThrows(
                    IOException.class,
                    () -> client(server, Duration.ofSeconds(2))
                            .bootup("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "2.2.0"));
        } finally {
            server.stop(0);
        }
    }

    @Test
    void bootupHttpErrorIncludesStatus() throws Exception {
        HttpServer server = server("/vow-api/mod/bootup", exchange -> respond(exchange, 503, "unavailable"));
        try {
            ApiHttpException error = assertThrows(
                    ApiHttpException.class,
                    () -> client(server, Duration.ofSeconds(2))
                            .bootup("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "2.2.0"));
            assertEquals(503, error.statusCode());
            assertEquals("unavailable", error.responseBody());
        } finally {
            server.stop(0);
        }
    }

    @Test
    void reportUsesJsonUtf8AndOmitsAnonymousPlayerName() throws Exception {
        List<String> bodies = new ArrayList<>();
        List<String> methods = new ArrayList<>();
        HttpServer server = server("/vow-api/reports", exchange -> {
            methods.add(exchange.getRequestMethod());
            bodies.add(readBody(exchange));
            respond(exchange, 201, "{\"reportedTimes\":1}");
        });
        try {
            client(server, Duration.ofSeconds(2))
                    .submitReport(new VoWApiClient.LineReportRequest(
                            "[1/1] Élise: Schnee ☃ & more", "élise", null, new VoWApiClient.Position(12, 64, -9)));

            assertEquals(List.of("POST"), methods);
            JsonObject payload = JsonParser.parseString(bodies.getFirst()).getAsJsonObject();
            assertEquals(3, payload.size());
            assertEquals(
                    "[1/1] Élise: Schnee ☃ & more", payload.get("chatMessage").getAsString());
            assertEquals("élise", payload.get("npcName").getAsString());
            assertFalse(payload.has("playerName"));
            assertEquals(12, payload.getAsJsonObject("position").get("x").getAsInt());
            assertEquals(64, payload.getAsJsonObject("position").get("y").getAsInt());
            assertEquals(-9, payload.getAsJsonObject("position").get("z").getAsInt());
            assertFalse(bodies.getFirst().contains("apiKey"));
            assertFalse(bodies.getFirst().contains("%"));
        } finally {
            server.stop(0);
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {200, 201})
    void bothReportSuccessStatusesAreAccepted(int status) throws Exception {
        HttpServer server = server("/vow-api/reports", exchange -> respond(exchange, status, "{}"));
        try {
            client(server, Duration.ofSeconds(2))
                    .submitReport(new VoWApiClient.LineReportRequest(
                            "Line", "npc", "PlainUsername", new VoWApiClient.Position(1, 2, 3)));
        } finally {
            server.stop(0);
        }
    }

    @Test
    void namedReportContainsPlainUsername() throws Exception {
        List<String> bodies = new ArrayList<>();
        HttpServer server = server("/vow-api/reports", exchange -> {
            bodies.add(readBody(exchange));
            respond(exchange, 200, "{}");
        });
        try {
            client(server, Duration.ofSeconds(2))
                    .submitReport(new VoWApiClient.LineReportRequest(
                            "Line", "npc", "PlainUsername", new VoWApiClient.Position(1, 2, 3)));
            assertEquals(
                    "PlainUsername",
                    JsonParser.parseString(bodies.getFirst())
                            .getAsJsonObject()
                            .get("playerName")
                            .getAsString());
        } finally {
            server.stop(0);
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {400, 429, 500})
    void reportValidationThrottleAndServerErrorsFailWithoutRetry(int status) throws Exception {
        int[] requests = {0};
        List<String> bodies = new ArrayList<>();
        HttpServer server = server("/vow-api/reports", exchange -> {
            requests[0]++;
            bodies.add(readBody(exchange));
            respond(exchange, status, "failure");
        });
        try {
            String completeDialogue = "Complete dialogue is retained: " + "雪".repeat(400);
            ApiHttpException error = assertThrows(
                    ApiHttpException.class,
                    () -> client(server, Duration.ofSeconds(2))
                            .submitReport(new VoWApiClient.LineReportRequest(
                                    completeDialogue, "npc", null, new VoWApiClient.Position(1, 2, 3))));
            assertEquals(status, error.statusCode());
            assertEquals(1, requests[0]);
            assertEquals(
                    completeDialogue,
                    JsonParser.parseString(bodies.getFirst())
                            .getAsJsonObject()
                            .get("chatMessage")
                            .getAsString());
        } finally {
            server.stop(0);
        }
    }

    @Test
    void requestHasOverallTimeout() throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            server.setExecutor(executor);
            server.createContext("/vow-api/reports", exchange -> {
                try {
                    Thread.sleep(500);
                    respond(exchange, 200, "{}");
                } catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                }
            });
            server.start();
            try {
                assertThrows(
                        HttpTimeoutException.class,
                        () -> client(server, Duration.ofMillis(50))
                                .submitReport(new VoWApiClient.LineReportRequest(
                                        "Line", "npc", null, new VoWApiClient.Position(1, 2, 3))));
            } finally {
                server.stop(0);
            }
        }
    }

    private static VoWApiClient client(HttpServer server, Duration timeout) {
        URI base = URI.create("http://127.0.0.1:" + server.getAddress().getPort() + "/vow-api/");
        return new VoWApiClient(new JsonHttpTransport(base, Duration.ofSeconds(1), timeout));
    }

    private static HttpServer server(String path, Handler handler) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext(path, exchange -> {
            try {
                handler.handle(exchange);
            } finally {
                exchange.close();
            }
        });
        server.start();
        return server;
    }

    private static String readBody(HttpExchange exchange) throws IOException {
        assertTrue(exchange.getRequestHeaders().getFirst("Content-Type").contains("application/json"));
        return new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
    }

    private static void respond(HttpExchange exchange, int status, String body) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(status, bytes.length);
        exchange.getResponseBody().write(bytes);
    }

    private static String bootupJson(String action, String fact) {
        String factJson = fact == null ? "null" : "\"Glück für alle ☃\"";
        return """
                {
                  "update": {
                    "action": "%s",
                    "latestVersion": "2.3.0",
                    "downloadUrl": "https://example.test/mod.jar",
                    "changelogUrl": "https://example.test/changelog"
                  },
                  "audio": {
                    "baseUrl": "https://audio.test/base/",
                    "mirrorUrls": ["https://audio.test/mirror/"]
                  },
                  "funFact": %s,
                  "broadcasts": ["One", "Zwei ✓"]
                }
                """.formatted(action, factJson);
    }

    @FunctionalInterface
    private interface Handler {
        void handle(HttpExchange exchange) throws IOException;
    }
}
