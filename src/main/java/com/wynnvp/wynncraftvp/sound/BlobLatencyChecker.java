package com.wynnvp.wynncraftvp.sound;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.concurrent.*;

public class BlobLatencyChecker {
    private final List<String> storageUrls;
    private final ExecutorService executor;
    private static final int SAMPLES_PER_ENDPOINT = 5;
    private static final int WARMUP_REQUESTS = 2;

    public BlobLatencyChecker(List<String> urls) {
        this.storageUrls = urls;
        this.executor = Executors.newFixedThreadPool(urls.size());
    }

    public CompletableFuture<String> findFastestEndpointAsync() {
        return CompletableFuture.supplyAsync(() -> {
            Map<String, List<Long>> allLatencies = new HashMap<>();
            List<CompletableFuture<Map.Entry<String, List<Long>>>> futures = new ArrayList<>();

            for (String url : storageUrls) {
                CompletableFuture<Map.Entry<String, List<Long>>> future = CompletableFuture.supplyAsync(() -> {
                    List<Long> latencies = checkLatencyMultipleTimes(url);
                    return Map.entry(url, latencies);
                }, executor);
                futures.add(future);
            }

            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

            for (CompletableFuture<Map.Entry<String, List<Long>>> future : futures) {
                Map.Entry<String, List<Long>> result = future.join();
                allLatencies.put(result.getKey(), result.getValue());
            }

            String fastestUrl = allLatencies.entrySet().stream()
                    .min(Map.Entry.comparingByValue((list1, list2) -> {
                        double avg1 = list1.stream().mapToDouble(Long::doubleValue).average().orElse(Double.MAX_VALUE);
                        double avg2 = list2.stream().mapToDouble(Long::doubleValue).average().orElse(Double.MAX_VALUE);
                        return Double.compare(avg1, avg2);
                    }))
                    .map(Map.Entry::getKey)
                    .orElse(storageUrls.get(0));

            return fastestUrl;
        }, executor);
    }

    private List<Long> checkLatencyMultipleTimes(String baseUrl) {
        List<Long> latencies = new ArrayList<>();

        for (int i = 0; i < WARMUP_REQUESTS; i++) {
            checkLatency(baseUrl);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        for (int i = 0; i < SAMPLES_PER_ENDPOINT; i++) {
            long latency = checkLatency(baseUrl);
            if (latency != Long.MAX_VALUE) {
                latencies.add(latency);
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        return latencies;
    }

    private long checkLatency(String baseUrl) {
        try {
            URL url = new URL(baseUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            long startTime = System.nanoTime();
            connection.connect();

            int responseCode = connection.getResponseCode();
            long latency = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);

            connection.disconnect();

            if (responseCode >= 200 && responseCode < 500) {
                return latency;
            }
        } catch (Exception e) {
            // Handle the exception if needed
        }
        return Long.MAX_VALUE;
    }

    public void shutdown() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }
}
