/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.api;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/** Small JSON transport shared by the mod API calls. */
public final class JsonHttpTransport {
    public static final Duration CONNECT_TIMEOUT = Duration.ofSeconds(5);
    public static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(15);

    private final URI baseUri;
    private final Duration requestTimeout;
    private final HttpClient client;
    private final Gson gson;

    public JsonHttpTransport(URI baseUri) {
        this(baseUri, CONNECT_TIMEOUT, REQUEST_TIMEOUT);
    }

    JsonHttpTransport(URI baseUri, Duration connectTimeout, Duration requestTimeout) {
        String base = baseUri.toString();
        this.baseUri = URI.create(base.endsWith("/") ? base : base + "/");
        this.requestTimeout = requestTimeout;
        client = HttpClient.newBuilder().connectTimeout(connectTimeout).build();
        gson = new Gson();
    }

    public HttpResponse<String> post(String path, Object body) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(baseUri.resolve(path))
                .timeout(requestTimeout)
                .header("Content-Type", "application/json; charset=UTF-8")
                .header("Accept", "application/json")
                .header("User-Agent", "VoicesOfWynnModClient")
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(body), StandardCharsets.UTF_8))
                .build();

        var pending = client.sendAsync(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        try {
            return pending.orTimeout(requestTimeout.toMillis(), TimeUnit.MILLISECONDS)
                    .get();
        } catch (InterruptedException exception) {
            pending.cancel(true);
            throw exception;
        } catch (ExecutionException exception) {
            Throwable cause = exception.getCause();
            if (cause instanceof CompletionException completion && completion.getCause() != null) {
                cause = completion.getCause();
            }
            if (cause instanceof java.util.concurrent.TimeoutException) {
                pending.cancel(true);
                throw new java.net.http.HttpTimeoutException("Request timed out after " + requestTimeout);
            }
            if (cause instanceof IOException ioException) throw ioException;
            if (cause instanceof InterruptedException interruptedException) throw interruptedException;
            if (cause instanceof RuntimeException runtimeException) throw runtimeException;
            throw new IOException("JSON request failed", cause);
        }
    }
}
