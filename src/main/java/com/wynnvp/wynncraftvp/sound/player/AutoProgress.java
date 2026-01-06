/*
 * Copyright © Team-VoW 2024-2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.player;

import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.game.ServerboundPlayerInputPacket;
import net.minecraft.world.entity.player.Input;

public class AutoProgress {
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    private ScheduledFuture<?> scheduledSneak;

    public void start(long milliSecondDelay) {
        cancelShift();
        scheduledSneak = scheduleSneak(milliSecondDelay);
    }

    public void cancelShift() {
        if (scheduledSneak != null) {
            scheduledSneak.cancel(true);
            scheduledSneak = null;
            sendShiftPacket(false);
        }
    }

    private void sendShiftPacket(boolean shiftDown) {
        var mc = Minecraft.getInstance();
        assert mc.player != null;

        var options = mc.options;

        Input input = new Input(
                options.keyUp.isDown(),
                options.keyDown.isDown(),
                options.keyLeft.isDown(),
                options.keyRight.isDown(),
                options.keyJump.isDown(),
                shiftDown,
                options.keySprint.isDown());

        Objects.requireNonNull(mc.getConnection()).send(new ServerboundPlayerInputPacket(input));
    }

    private ScheduledFuture<?> scheduleRelease(long milliSecondDelay) {
        return scheduler.schedule(() -> sendShiftPacket(false), milliSecondDelay, TimeUnit.MILLISECONDS);
    }

    private ScheduledFuture<?> scheduleSneak(long milliSecondDelay) {
        return scheduler.schedule(
                () -> {
                    sendShiftPacket(true);
                    scheduleRelease(100);
                },
                milliSecondDelay,
                TimeUnit.MILLISECONDS);
    }
}
