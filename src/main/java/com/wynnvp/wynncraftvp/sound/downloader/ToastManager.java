/*
 * Copyright © Team-VoW 2025.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.downloader;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

public class ToastManager {
    private static ToastManager instance;
    private final Minecraft client;
    private TimedToast currentTimedToast;
    private boolean isListening = false;

    // Constructor
    public ToastManager(Minecraft client) {
        this.client = client;
        instance = this;
        // Register the tick listener once, but use the flag to control when it's active
        ClientTickEvents.END_CLIENT_TICK.register(this::onClientTick);
    }

    public static ToastManager getInstance() {
        return instance;
    }

    public void displayToast(Component title, Component message) {
        Minecraft.getInstance()
                .getToasts()
                .addToast(new SystemToast(new SystemToast.SystemToastId(10000L), title, message));
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
        if (currentTimedToast != null) {
            currentTimedToast.hide();
        }

        currentTimedToast = new TimedToast(action, durationSeconds, titleText, subtitleText);
        client.getToasts().addToast(currentTimedToast);
        isListening = true; // Start listening for key presses
    }

    // Method to handle client ticks and check for key presses
    private void onClientTick(Minecraft client) {
        if (!isListening || currentTimedToast == null) {
            return;
        }
        if (isKeyPressed(GLFW.GLFW_KEY_Y)) {
            // User confirmed; execute action immediately
            currentTimedToast.executeActionAndHide();
            stopListening();
        } else if (isKeyPressed(GLFW.GLFW_KEY_N)) {
            // User declined; do not execute action
            currentTimedToast.hide();
            displayToast(Component.literal("Action cancelled"), Component.literal("Did not download VoW audio"));
            stopListening();
        }
    }

    private void stopListening() {
        isListening = false;
        currentTimedToast = null;
    }

    // Helper method to check if a key is pressed
    private boolean isKeyPressed(int keyCode) {
        long windowHandle = client.getWindow().getWindow();
        return GLFW.glfwGetKey(windowHandle, keyCode) == GLFW.GLFW_PRESS;
    }
}
