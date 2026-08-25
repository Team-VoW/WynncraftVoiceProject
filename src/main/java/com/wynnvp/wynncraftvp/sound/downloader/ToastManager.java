/*
 * Copyright © Team-VoW 2025-2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.downloader;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

public class ToastManager {
    private static ToastManager instance;
    private final Minecraft client;
    private TimedToast currentTimedToast;
    private Runnable currentOnCancel;
    private boolean isListening = false;

    // Constructor
    public ToastManager(Minecraft client) {
        this.client = client;
        instance = this;
    }

    public static ToastManager getInstance() {
        return instance;
    }

    public void displayToast(Component title, Component message) {
        client.execute(() -> client.getToastManager()
                .addToast(new SystemToast(new SystemToast.SystemToastId(10000L), title, message)));
    }

    /**
     * Displays a timed toast that counts down and performs an action based on user input.
     *
     * @param action          The Runnable to execute if confirmed or time runs out.
     * @param durationSeconds Duration in seconds for the countdown.
     * @param titleText       The title text of the toast.
     * @param subtitleText    The subtitle text of the toast (without the countdown).
     */
    public void displayTimedToast(Runnable action, int durationSeconds, String titleText, String subtitleText) {
        displayTimedToast(action, () -> {}, durationSeconds, titleText, subtitleText);
    }

    /**
     * As {@link #displayTimedToast(Runnable, int, String, String)}, but also tells the caller when the
     * countdown was called off, so it can let go of whatever it was holding for the confirmed action.
     *
     * @param onCancel run if the user declines, or if another timed toast replaces this one before it
     *                 has had a chance to run.
     */
    public void displayTimedToast(
            Runnable action, Runnable onCancel, int durationSeconds, String titleText, String subtitleText) {
        client.execute(() -> {
            cancelCurrentToast();

            currentTimedToast = new TimedToast(action, durationSeconds, titleText, subtitleText);
            currentOnCancel = onCancel;
            client.getToastManager().addToast(currentTimedToast);
            isListening = true; // Start listening for key presses
        });
    }

    // Method to handle client ticks and check for key presses, called from ClientTickEvent
    public void onClientTick(Minecraft client) {
        if (!isListening || currentTimedToast == null) {
            return;
        }
        if (isKeyPressed(GLFW.GLFW_KEY_Y)) {
            // User confirmed; execute action immediately
            currentTimedToast.executeActionAndHide();
            stopListening();
        } else if (isKeyPressed(GLFW.GLFW_KEY_N)) {
            // User declined; do not execute action
            cancelCurrentToast();
            displayToast(Component.literal("Action cancelled"), Component.literal("Did not download VoW audio"));
            stopListening();
        }
    }

    /**
     * Hides the pending toast and reports the cancellation, unless its action has already run -- the
     * countdown can finish while the toast is still the current one, and that is not a cancellation.
     */
    private void cancelCurrentToast() {
        if (currentTimedToast == null) {
            return;
        }
        boolean actionStillPending = !currentTimedToast.isFinished();
        currentTimedToast.hide();
        if (actionStillPending && currentOnCancel != null) {
            currentOnCancel.run();
        }
        currentOnCancel = null;
    }

    private void stopListening() {
        isListening = false;
        currentTimedToast = null;
        currentOnCancel = null;
    }

    // Helper method to check if a key is pressed
    private boolean isKeyPressed(int keyCode) {
        long windowHandle = client.getWindow().handle();
        return GLFW.glfwGetKey(windowHandle, keyCode) == GLFW.GLFW_PRESS;
    }
}
