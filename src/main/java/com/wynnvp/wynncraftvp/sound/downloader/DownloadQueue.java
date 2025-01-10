/*
 * Copyright © Team-VoW 2024-2025.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.downloader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import net.minecraft.client.Minecraft;

public class DownloadQueue {
    private PriorityBlockingQueue<DownloadTask> queue;
    private final String audioFolder;
    private final String baseUrl;
    private final ExecutorService executor;
    private final int threadCount = 10;
    private volatile boolean running = false; // To control worker threads
    private Runnable onQueueEmpty; // Callback for when the queue is empty
    private volatile boolean queueEmptyNotified = false; // To prevent duplicate notifications

    private final DownloadProgressToast progressToast;

    public DownloadQueue(String audioFolder, String baseUrl, int downloadCount) {
        this.audioFolder = audioFolder;
        this.baseUrl = baseUrl;
        this.queue = new PriorityBlockingQueue<>();
        this.executor = Executors.newFixedThreadPool(threadCount);
        this.progressToast =
                new DownloadProgressToast(Minecraft.getInstance(), "Downloading Voices of Wynn audio", downloadCount);
    }

    public void start() {
        synchronized (this) {
            if (!running) {
                running = true;
                queueEmptyNotified = false; // Reset notification flag when starting
                for (int i = 0; i < threadCount; i++) {
                    executor.submit(this::worker);
                }
            }
        }
    }

    public void stop() {
        synchronized (this) {
            running = false;
            executor.shutdown();
            try {
                executor.awaitTermination(10, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void setOnQueueEmpty(Runnable onQueueEmpty) {
        this.onQueueEmpty = onQueueEmpty;
    }

    public void initializeQueue(List<DownloadTask> tasks) {
        queue = new PriorityBlockingQueue<>(tasks);
    }

    private void worker() {
        while (running) {
            try {
                DownloadTask task = queue.poll(1, TimeUnit.SECONDS); // Timeout to allow shutdown check
                if (task == null) {
                    checkQueueEmpty();
                    continue;
                }

                System.out.println("Downloading: " + task.getAudioName());
                String fileName = sanitizeFileName(task.getAudioName()) + ".ogg";
                File localFile = new File(audioFolder, fileName);

                try {
                    downloadFile(baseUrl + fileName, localFile);
                    // Update the progress toast for a successful download
                    progressToast.increaseCount();
                } catch (IOException e) {
                    System.err.println("Failed to download " + task.getAudioName() + ": " + e.getMessage());
                    // Update the progress toast for a failed download
                    progressToast.addFailed();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void checkQueueEmpty() {
        if (queue.isEmpty() && onQueueEmpty != null && !queueEmptyNotified) {
            synchronized (this) {
                if (queue.isEmpty() && !queueEmptyNotified) {
                    queueEmptyNotified = true;
                    onQueueEmpty.run();
                    progressToast.requestFinished();
                    stop(); // Stop the queue after notifying
                }
            }
        }
    }

    private void downloadFile(String fileURL, File destination) throws IOException {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(fileURL).openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                try (InputStream inputStream = connection.getInputStream();
                        FileOutputStream outputStream = new FileOutputStream(destination)) {
                    byte[] buffer = new byte[8192];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }
            } else {
                throw new IOException("Failed to download file: " + fileURL);
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private String sanitizeFileName(String fileName) {
        return fileName.replaceAll("[^a-zA-Z0-9._-]", "_");
    }
}
