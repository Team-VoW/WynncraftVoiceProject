/*
 * Copyright © Team-VoW 2024.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.player;

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

    public void autoProgress(long milliSecondDelay) {
        cancelShift();

        scheduledSneak = scheduleSneak(milliSecondDelay);
    }

    public void cancelShift() {
        if (scheduledSneak != null) {
            scheduledSneak.cancel(true);
            scheduledSneak = null;
            sendReleasePacket();
        }
    }

    private void sendReleasePacket() {
        var mc = Minecraft.getInstance();
        assert mc.player != null;
        Objects.requireNonNull(mc.getConnection())
                .send(new ServerboundPlayerCommandPacket(
                        mc.player, ServerboundPlayerCommandPacket.Action.RELEASE_SHIFT_KEY));
    }

    private ScheduledFuture<?> scheduleRelease(long milliSecondDelay) {
        return scheduler.schedule(this::sendReleasePacket, milliSecondDelay, TimeUnit.MILLISECONDS);
    }

    private ScheduledFuture<?> scheduleSneak(long milliSecondDelay) {
        return scheduler.schedule(
                () -> {
                    var mc = Minecraft.getInstance();
                    assert mc.player != null;
                    Objects.requireNonNull(mc.getConnection())
                            .send(new ServerboundPlayerCommandPacket(
                                    mc.player, ServerboundPlayerCommandPacket.Action.PRESS_SHIFT_KEY));
                    scheduleRelease(100);
                },
                milliSecondDelay,
                TimeUnit.MILLISECONDS);
    }
}
