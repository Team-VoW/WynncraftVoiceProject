/*
 * Copyright © Team-VoW 2024.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.utils;

import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;

public class AutoProgress {
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    private ScheduledFuture<?> scheduledSneak;

    private void autoProgress(long milliSecondDelay) {
        if (scheduledSneak != null) {
            scheduledSneak.cancel(true);
            scheduledSneak = null;

            var mc = Minecraft.getInstance();
            assert mc.player != null;
            Objects.requireNonNull(mc.getConnection())
                    .send(new ServerboundPlayerCommandPacket(
                            mc.player, ServerboundPlayerCommandPacket.Action.RELEASE_SHIFT_KEY));
        }

        scheduledSneak = scheduleSneak(milliSecondDelay);
    }

    private ScheduledFuture<?> scheduleSneak(long milliSecondDelay) {
        return scheduler.schedule(
                () -> {
                    var mc = Minecraft.getInstance();
                    assert mc.player != null;
                    Objects.requireNonNull(mc.getConnection())
                            .send(new ServerboundPlayerCommandPacket(
                                    mc.player, ServerboundPlayerCommandPacket.Action.PRESS_SHIFT_KEY));
                },
                milliSecondDelay,
                TimeUnit.MILLISECONDS);
    }
}
