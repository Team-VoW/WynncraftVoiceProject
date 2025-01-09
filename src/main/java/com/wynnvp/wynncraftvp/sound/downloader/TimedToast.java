/*
 * Copyright © Team-VoW 2025.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.downloader;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.network.chat.Component;

public class TimedToast implements Toast {
    private final Runnable action;
    private final int durationSeconds;
    private final Component title;
    private final Component subtitle;
    private boolean finished = false;
    private long lastChanged;
    private final SystemToast systemToast;

    public TimedToast(Runnable action, int durationSeconds, String titleText, String subtitleText) {
        this.action = action;
        this.durationSeconds = durationSeconds;
        this.title = Component.literal(titleText);
        this.subtitle = Component.literal(subtitleText);
        this.systemToast = new SystemToast(SystemToast.SystemToastId.PERIODIC_NOTIFICATION, this.title, this.subtitle);
    }

    public void executeActionAndHide() {
        if (finished) return;
        action.run();
        hide();
    }

    // Hide the toast without executing the action
    public void hide() {
        if (finished) return;
        finished = true;
        systemToast.forceHide();
    }

    @Override
    public Toast.Visibility render(GuiGraphics guiGraphics, ToastComponent toastComponent, long timeSinceLastVisible) {
        if (finished) {
            return Toast.Visibility.HIDE;
        }

        if (this.lastChanged == 0L) {
            this.lastChanged = timeSinceLastVisible;
        }

        // Calculate remaining time
        long elapsedTime = (timeSinceLastVisible - this.lastChanged) / 1000L;
        int secondsLeft = durationSeconds - (int) elapsedTime;

        if (secondsLeft <= 0) {
            // Time's up; execute the action
            action.run();
            finished = true;
            return Toast.Visibility.HIDE;
        }

        // Update the subtitle with the remaining time
        Component message = Component.literal(this.subtitle.getString() + " Starts in " + secondsLeft + "s");

        // Use SystemToast's render method
        systemToast.reset(this.title, message);
        return systemToast.render(guiGraphics, toastComponent, timeSinceLastVisible);
    }

    @Override
    public int width() {
        return systemToast.width();
    }

    @Override
    public int height() {
        return systemToast.height();
    }
}
