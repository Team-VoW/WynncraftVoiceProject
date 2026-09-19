/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.api;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;
import java.util.List;

/** Typed client for the public endpoints used by the mod. */
public final class VoWApiClient {
    public static final URI PRODUCTION_BASE_URI = URI.create("https://voicesofwynn.com/vow-api/");

    private final JsonHttpTransport transport;
    private final Gson gson = new Gson();

    public VoWApiClient() {
        this(new JsonHttpTransport(PRODUCTION_BASE_URI));
    }

    public VoWApiClient(JsonHttpTransport transport) {
        this.transport = transport;
    }

    public BootupResponse bootup(String clientId, String modVersion) throws IOException, InterruptedException {
        HttpResponse<String> response = transport.post("mod/bootup", new BootupRequest(clientId, modVersion));
        requireStatus(response, 200);
        try {
            BootupResponse result = gson.fromJson(response.body(), BootupResponse.class);
            validate(result);
            return result;
        } catch (JsonParseException | IllegalStateException exception) {
            throw new IOException("Invalid bootup response", exception);
        }
    }

    public void submitReport(LineReportRequest report) throws IOException, InterruptedException {
        HttpResponse<String> response = transport.post("reports", report);
        if (response.statusCode() != 200 && response.statusCode() != 201) {
            throw new ApiHttpException(response.statusCode(), response.body());
        }
    }

    private static void requireStatus(HttpResponse<String> response, int expected) throws ApiHttpException {
        if (response.statusCode() != expected) {
            throw new ApiHttpException(response.statusCode(), response.body());
        }
    }

    private static void validate(BootupResponse response) {
        if (response == null || response.update == null || response.audio == null || response.broadcasts == null) {
            throw new IllegalStateException("Missing bootup response fields");
        }
        if (!List.of("none", "notify", "disable").contains(response.update.action)
                || isBlank(response.update.latestVersion)
                || isBlank(response.update.downloadUrl)
                || isBlank(response.update.changelogUrl)
                || isBlank(response.audio.baseUrl)
                || response.audio.mirrorUrls == null) {
            throw new IllegalStateException("Invalid bootup response fields");
        }
    }

    private static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    public record BootupRequest(String clientId, String modVersion) {}

    public record BootupResponse(UpdateResponse update, AudioResponse audio, String funFact, List<String> broadcasts) {}

    public record UpdateResponse(String action, String latestVersion, String downloadUrl, String changelogUrl) {}

    public record AudioResponse(String baseUrl, List<String> mirrorUrls) {}

    public record LineReportRequest(String chatMessage, String npcName, String playerName, Position position) {}

    public record Position(int x, int y, int z) {}
}
