package com.wynnvp.wynncraftvp.sound.downloader;

import java.util.concurrent.atomic.AtomicInteger;

class DownloadTask implements Comparable<DownloadTask> {
    private static final AtomicInteger count = new AtomicInteger(0);
    private final int id;
    private final String audioName;
    private int priority;

    public DownloadTask(String audioName, int priority) {
        this.id = count.incrementAndGet();
        this.audioName = audioName;
        this.priority = priority;
    }

    public String getAudioName() {
        return audioName;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }




    @Override
    public int compareTo(DownloadTask other) {
        return Integer.compare(this.priority, other.priority);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DownloadTask that = (DownloadTask) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}