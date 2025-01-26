/*
 * Copyright © Team-VoW 2025.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.downloader;

import java.text.DecimalFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.network.chat.Component;

public class DownloadProgressToast {
    private final Minecraft client;
    private Component title;
    private int count;
    private final int totalAmount;
    private int failedAmount;

    private final SystemToast.SystemToastId toastId;

    public DownloadProgressToast(Minecraft client, String title, int totalAmount) {
        this.client = client; // Use the provided Minecraft instance
        this.toastId = new SystemToast.SystemToastId();
        this.title = Component.literal(title);
        this.totalAmount = totalAmount;
    }

    /**
     * Updates or adds a progress toast.
     */
    private synchronized void updateToast() {
        // Cast to float to avoid integer division and format to a maximum of two decimal places
        float percent = ((float) count / totalAmount) * 100;
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String formattedPercent = decimalFormat.format(percent);

        String toastMessage = "Progress: " + count + " / " + totalAmount;
        // If there are failed downloads, add that to the toast message
        toastMessage += failedAmount > 0 ? " (" + failedAmount + " failed)" : "";

        // Component displayMessage = Component.literal("Progress: " + count + " / " + totalAmount + " (" +
        // formattedPercent + "%)");
        Component displayMessage = Component.literal(toastMessage);

        SystemToast.addOrUpdate(client.getToastManager(), this.toastId, this.title, displayMessage);
    }

    public synchronized void requestFinished() {
        SystemToast.forceHide(client.getToastManager(), this.toastId);
    }

    /**
     * Set the title of the progress listener.
     */
    public synchronized void setTitle(Component title) {
        this.title = title;
        updateToast();
    }

    /**
     * Update progress.
     *
     * @param count The number of files downloaded
     */
    public synchronized void updateProgress(int count) {
        this.count = count;
        updateToast();
    }

    public synchronized void increaseCount() {
        this.count++;
        updateToast();
    }

    public synchronized void addFailed() {
        this.failedAmount++;
        updateToast();
    }
}
