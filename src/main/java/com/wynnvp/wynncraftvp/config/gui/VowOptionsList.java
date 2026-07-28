/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.config.gui;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

/**
 * The option list used by {@link VowOptionsScreen}.
 * <p>
 * Vanilla's {@code OptionsList} hard-codes a 310 wide row with two 150 wide columns, which clips
 * the longer option labels. This one scales the row with the window instead and resizes its widgets
 * every frame to match, so options stay readable on wide screens.
 */
public class VowOptionsList extends ContainerObjectSelectionList<VowOptionsList.Entry> {
    private static final int ROW_HEIGHT = 25;
    private static final int COLUMN_GAP = 8;
    private static final int SIDE_MARGIN = 16;

    /** Vanilla's row width; never go narrower than the layout players already know. */
    private static final int MIN_ROW_WIDTH = 310;

    private static final int MAX_ROW_WIDTH = 480;
    private static final int LABEL_WIDTH = 150;

    private final Font font;

    public VowOptionsList(Minecraft minecraft, Screen screen, HeaderAndFooterLayout layout) {
        super(minecraft, screen.width, layout.getContentHeight(), layout.getHeaderHeight(), ROW_HEIGHT);
        this.font = minecraft.font;
    }

    @Override
    public int getRowWidth() {
        return Math.clamp(getWidth() - 2 * SIDE_MARGIN, MIN_ROW_WIDTH, MAX_ROW_WIDTH);
    }

    /** Two widgets side by side, each filling half the row. */
    public void addPair(AbstractWidget left, AbstractWidget right) {
        addEntry(new WidgetEntry(List.of(left, right), false));
    }

    /** A single widget in the left column, for the odd one out at the end of a section. */
    public void addHalf(AbstractWidget widget) {
        addEntry(new WidgetEntry(List.of(widget), false));
    }

    /** A single widget spanning the whole row. */
    public void addFull(AbstractWidget widget) {
        addEntry(new WidgetEntry(List.of(widget), true));
    }

    /** A label on the left with the widget filling the rest of the row, for free text fields. */
    public void addLabelled(Component label, AbstractWidget widget) {
        addEntry(new LabelledEntry(label, widget, font));
    }

    public abstract static class Entry extends ContainerObjectSelectionList.Entry<Entry> {
        protected int centreVertically(AbstractWidget widget) {
            return getContentY() + Math.max(0, (getContentHeight() - widget.getHeight()) / 2);
        }
    }

    private static class WidgetEntry extends Entry {
        private final List<AbstractWidget> widgets;
        private final boolean fullWidth;

        WidgetEntry(List<AbstractWidget> widgets, boolean fullWidth) {
            this.widgets = widgets;
            this.fullWidth = fullWidth;
        }

        @Override
        public void renderContent(GuiGraphics guiGraphics, int mouseX, int mouseY, boolean hovered, float partialTick) {
            int columnWidth = fullWidth ? getContentWidth() : (getContentWidth() - COLUMN_GAP) / 2;
            int x = getContentX();
            for (AbstractWidget widget : widgets) {
                widget.setWidth(columnWidth);
                widget.setPosition(x, centreVertically(widget));
                widget.render(guiGraphics, mouseX, mouseY, partialTick);
                x += columnWidth + COLUMN_GAP;
            }
        }

        @Override
        public List<? extends AbstractWidget> children() {
            return widgets;
        }

        @Override
        public List<? extends NarratableEntry> narratables() {
            return widgets;
        }
    }

    private static class LabelledEntry extends Entry {
        private final Component label;
        private final AbstractWidget widget;
        private final Font font;

        LabelledEntry(Component label, AbstractWidget widget, Font font) {
            this.label = label;
            this.widget = widget;
            this.font = font;
        }

        @Override
        public void renderContent(GuiGraphics guiGraphics, int mouseX, int mouseY, boolean hovered, float partialTick) {
            guiGraphics.drawString(
                    font,
                    label,
                    getContentX(),
                    getContentY() + Math.max(0, (getContentHeight() - font.lineHeight) / 2),
                    0xFFFFFFFF);

            widget.setWidth(Math.max(20, getContentWidth() - LABEL_WIDTH - COLUMN_GAP));
            widget.setPosition(getContentX() + LABEL_WIDTH + COLUMN_GAP, centreVertically(widget));
            widget.render(guiGraphics, mouseX, mouseY, partialTick);
        }

        @Override
        public List<? extends AbstractWidget> children() {
            return List.of(widget);
        }

        @Override
        public List<? extends NarratableEntry> narratables() {
            return List.of(widget);
        }
    }
}
