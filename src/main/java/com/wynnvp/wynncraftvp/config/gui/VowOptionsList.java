/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.config.gui;

import java.util.List;
import java.util.function.BooleanSupplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

/**
 * The option list used by the category tabs on {@link VowConfigScreen}.
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

    public VowOptionsList(Minecraft minecraft, Screen screen) {
        super(minecraft, screen.width, screen.height, 0, ROW_HEIGHT);
        this.font = minecraft.font;
    }

    @Override
    public int getRowWidth() {
        return Math.clamp(getWidth() - 2 * SIDE_MARGIN, MIN_ROW_WIDTH, MAX_ROW_WIDTH);
    }

    /** Two widgets side by side, each filling half the row and each with its own prerequisite. */
    public void addPair(
            AbstractWidget left, BooleanSupplier leftEnabled, AbstractWidget right, BooleanSupplier rightEnabled) {
        addEntry(new WidgetEntry(List.of(left, right), List.of(leftEnabled, rightEnabled)));
    }

    /** A single widget in the left column, for the odd one out at the end of a section. */
    public void addHalf(AbstractWidget widget, BooleanSupplier enabled) {
        addEntry(new WidgetEntry(List.of(widget), List.of(enabled)));
    }

    /** A label on the left with the widget filling the rest of the row, for free text fields. */
    public void addLabelled(Component label, AbstractWidget widget, BooleanSupplier enabled) {
        addEntry(new LabelledEntry(label, widget, font, enabled));
    }

    /** Adds a visual divider before a related group of settings. */
    public void addHeader(Component title) {
        addEntry(new HeaderEntry(title, font), 22);
    }

    /** Removes all current rows so this tab can be rebuilt for a new search. */
    public void clear() {
        clearEntries();
    }

    public abstract static class Entry extends ContainerObjectSelectionList.Entry<Entry> {
        protected int centreVertically(AbstractWidget widget) {
            return getContentY() + Math.max(0, (getContentHeight() - widget.getHeight()) / 2);
        }
    }

    private static class WidgetEntry extends Entry {
        private final List<AbstractWidget> widgets;
        private final List<BooleanSupplier> enabled;

        WidgetEntry(List<AbstractWidget> widgets, List<BooleanSupplier> enabled) {
            this.widgets = widgets;
            this.enabled = enabled;
        }

        @Override
        public void renderContent(GuiGraphics guiGraphics, int mouseX, int mouseY, boolean hovered, float partialTick) {
            int columnWidth = (getContentWidth() - COLUMN_GAP) / 2;
            int x = getContentX();
            for (int index = 0; index < widgets.size(); index++) {
                AbstractWidget widget = widgets.get(index);
                boolean isEnabled = enabled.get(index).getAsBoolean();
                widget.active = isEnabled;
                if (widget instanceof EditBox editBox) {
                    editBox.setEditable(isEnabled);
                }
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
        private final BooleanSupplier enabled;

        LabelledEntry(Component label, AbstractWidget widget, Font font, BooleanSupplier enabled) {
            this.label = label;
            this.widget = widget;
            this.font = font;
            this.enabled = enabled;
        }

        @Override
        public void renderContent(GuiGraphics guiGraphics, int mouseX, int mouseY, boolean hovered, float partialTick) {
            boolean isEnabled = enabled.getAsBoolean();
            guiGraphics.drawString(
                    font,
                    label,
                    getContentX(),
                    getContentY() + Math.max(0, (getContentHeight() - font.lineHeight) / 2),
                    isEnabled ? 0xFFFFFFFF : 0xFF808080);

            widget.active = isEnabled;
            if (widget instanceof EditBox editBox) {
                editBox.setEditable(isEnabled);
            }
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

    private static class HeaderEntry extends Entry {
        private static final int TITLE_COLOR = 0xFFFFFFFF;
        private static final int SEPARATOR_COLOR = 0xFF606060;

        private final Component title;
        private final Font font;

        HeaderEntry(Component title, Font font) {
            this.title = title;
            this.font = font;
        }

        @Override
        public void renderContent(GuiGraphics guiGraphics, int mouseX, int mouseY, boolean hovered, float partialTick) {
            int textY = getContentY() + Math.max(2, (getContentHeight() - font.lineHeight) / 2);
            guiGraphics.drawString(font, title, getContentX(), textY, TITLE_COLOR);
            int lineStart = getContentX() + font.width(title) + 8;
            int lineY = getContentY() + getContentHeight() / 2;
            if (lineStart < getContentX() + getContentWidth()) {
                guiGraphics.fill(lineStart, lineY, getContentX() + getContentWidth(), lineY + 1, SEPARATOR_COLOR);
            }
        }

        @Override
        public List<? extends AbstractWidget> children() {
            return List.of();
        }

        @Override
        public List<? extends NarratableEntry> narratables() {
            return List.of();
        }
    }
}
