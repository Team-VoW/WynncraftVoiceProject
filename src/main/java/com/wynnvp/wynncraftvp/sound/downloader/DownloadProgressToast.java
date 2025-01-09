package com.wynnvp.wynncraftvp.sound.downloader;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.network.chat.Component;

import java.text.DecimalFormat;

public class DownloadProgressToast {
    private final Minecraft client;
    private Component title;
    private int count;
    private int totalAmount;

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

    private void updateToast() {
        // Cast to float to avoid integer division and format to a maximum of two decimal places
        float percent = ((float) count / totalAmount) * 100;
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String formattedPercent = decimalFormat.format(percent);

       // Component displayMessage = Component.literal("Progress: " + count + " / " + totalAmount + " (" + formattedPercent + "%)");
        Component displayMessage = Component.literal("Progress: " + count + " / " + totalAmount);

        System.out.println("Updating toast: " + displayMessage.getString());
        System.out.println("Percent is " + formattedPercent + "%");

        SystemToast.addOrUpdate(client.getToasts(), this.toastId, this.title, displayMessage);
    }

    public void requestFinished(boolean success) {
        if (!success) {
            //  DownloadedPackSource.LOGGER.info("Pack {} failed to download", this.count);
            //this.failCount++;
        } else {
            //   DownloadedPackSource.LOGGER.debug("Download ended for pack {}", this.count);
        }

        SystemToast.forceHide(client.getToasts(), this.toastId);

    }

    /**
     * Set the title of the progress listener.
     */
    public void setTitle(Component title) {
        this.title = title;
        updateToast();
    }

    /**
     * Update progress.
     *
     * @param count The number of files downloaded
     */
    public void updateProgress(int count) {
        this.count = count;
        updateToast();
    }
    public void increaseCount() {
        this.count++;
        updateToast();
    }
}

