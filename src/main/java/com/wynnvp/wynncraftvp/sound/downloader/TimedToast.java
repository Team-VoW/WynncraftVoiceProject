/*
 * Copyright © Team-VoW 2025.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.downloader;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastManager;
import net.minecraft.network.chat.Component;

public class TimedToast implements Toast {
    private final Runnable action;
    private final int durationSeconds;
    private final Component title;
    private final Component subtitle;
    private boolean finished = false;
    private long lastChanged;
    private final SystemToast systemToast;
    private Toast.Visibility wantedVisibility = Toast.Visibility.HIDE;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public TimedToast(Runnable action, int durationSeconds, String titleText, String subtitleText) {
        this.action = action;
        this.durationSeconds = durationSeconds;
        this.title = Component.literal(titleText);
        this.subtitle = Component.literal(subtitleText);
        this.systemToast = new SystemToast(SystemToast.SystemToastId.PERIODIC_NOTIFICATION, this.title, this.subtitle);
    }

    public void executeActionAndHide() {
        if (finished) return;
        hide();

        // We add a delay to ensure the toast is hidden before executing the action.
        scheduler.schedule(() -> Minecraft.getInstance().execute(action), 1, TimeUnit.SECONDS);
    }

    public void hide() {
        if (finished) return;
        finished = true;
        systemToast.forceHide();
    }

    @Override
    public Toast.Visibility getWantedVisibility() {
        return this.wantedVisibility;
    }

    @Override
    public void update(ToastManager toastManager, long visibilityTime) {
        if (this.lastChanged == 0L) {
            this.lastChanged = visibilityTime;
        }

        // Calculate remaining time
        long elapsedTime = (visibilityTime - this.lastChanged) / 1000L;
        int secondsLeft = durationSeconds - (int) elapsedTime;

        if (secondsLeft <= 0 || finished) {
            if (!finished && secondsLeft <= 0) {
                // Time's up; execute the action
                action.run();
                finished = true;
            }
            this.wantedVisibility = Toast.Visibility.HIDE;
        } else {
            this.wantedVisibility = Toast.Visibility.SHOW;
        }

        // Update the subtitle with the remaining time
        Component message = Component.literal(this.subtitle.getString() + " Starts in " + secondsLeft + "s");
        systemToast.reset(this.title, message);
    }

    @Override
    public void render(GuiGraphics guiGraphics, Font font, long visibilityTime) {
        systemToast.render(guiGraphics, font, visibilityTime);
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
