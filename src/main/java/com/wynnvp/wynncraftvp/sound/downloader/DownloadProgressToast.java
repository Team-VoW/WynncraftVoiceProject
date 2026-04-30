/*
 * Copyright © Team-VoW 2025-2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound.downloader;

import com.wynnvp.wynncraftvp.ModCore;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastManager;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public class DownloadProgressToast implements Toast {
    private static final Identifier BACKGROUND_SPRITE =
            Identifier.fromNamespaceAndPath(ModCore.MODID, "toast/download");
    private static final Identifier LOGO_SPRITE = Identifier.fromNamespaceAndPath(ModCore.MODID, "logo");
    private static final Identifier EXPERIENCE_BAR_BACKGROUND_SPRITE =
            Identifier.withDefaultNamespace("hud/experience_bar_background");
    private static final Identifier EXPERIENCE_BAR_PROGRESS_SPRITE =
            Identifier.withDefaultNamespace("hud/experience_bar_progress");
    private static final Component TITLE_COMPONENT =
            Component.translatable("text.toast.downloadProgress.title").withStyle(ChatFormatting.YELLOW);

    private Toast.Visibility visibility = Visibility.HIDE;

    private int currentAmount = 0;
    private int failedAmount = 0;
    private final int maxAmount;

    public DownloadProgressToast(int maxAmount) {
        this.maxAmount = maxAmount;
        Minecraft.getInstance().getToastManager().addToast(this);
        visibility = Visibility.SHOW;
    }

    @Override
    public @NonNull Visibility getWantedVisibility() {
        return visibility;
    }

    @Override
    public int width() {
        int titleWidth = Minecraft.getInstance().font.width(TITLE_COMPONENT);
        int progressWidth = Minecraft.getInstance().font.width(progressComponent());
        if (titleWidth >= progressWidth) {
            return 30 + titleWidth;
        }
        return 30 + progressWidth;
    }

    @Override
    public void update(@NonNull ToastManager toastManager, long visibilityTime) {}

    @Override
    public void render(GuiGraphics guiGraphics, @NonNull Font font, long visibilityTime) {
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, BACKGROUND_SPRITE, 0, 0, width(), height());
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, LOGO_SPRITE, 6, 5, 22, 22, 0.4f);
        guiGraphics.drawString(font, TITLE_COMPONENT, 8, 5, -1);
        guiGraphics.drawString(font, progressComponent(), 8, 14, -1);
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, EXPERIENCE_BAR_BACKGROUND_SPRITE, 6, 23, width() - 12, 5);
        if (getCombinedAmount() != 0) {
            guiGraphics.blitSprite(
                    RenderPipelines.GUI_TEXTURED,
                    EXPERIENCE_BAR_PROGRESS_SPRITE,
                    width() - 12,
                    5,
                    0,
                    0,
                    6,
                    23,
                    (int) ((width() - 12) * getProgress()),
                    5);
        }
    }

    private Component progressComponent() {
        MutableComponent test = Component.translatable(
                "text.toast.downloadProgress.progress",
                getCombinedAmount(),
                maxAmount,
                Math.round(getProgress() * 1000) / 10f);
        if (failedAmount != 0) {
            test.append(" ")
                    .append(Component.translatable("text.toast.downloadProgress.failed", failedAmount)
                            .withStyle(ChatFormatting.RED));
        }
        return test;
    }

    private int getCombinedAmount() {
        return currentAmount + failedAmount;
    }

    private float getProgress() {
        return (float) getCombinedAmount() / maxAmount;
    }

    public void increaseCount() {
        this.currentAmount++;
    }

    public void addFailed() {
        this.failedAmount++;
    }

    public void requestFinished() {
        this.visibility = Visibility.HIDE;
    }
}
