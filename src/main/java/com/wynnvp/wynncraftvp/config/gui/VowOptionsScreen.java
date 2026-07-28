/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.config.gui;

import com.wynnvp.wynncraftvp.ModCore;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

/**
 * Shared plumbing for the mod's option screens: header, scrolling option list, Done button, and the
 * factories that turn a config field into a widget.
 * <p>
 * Widgets write straight into {@link ModCore#config}; the file is written once when the screen is
 * closed rather than on every keystroke.
 */
public abstract class VowOptionsScreen extends Screen {
    private static final String OPTION_PREFIX = "wynnvp.config.option.";
    private static final int TEXT_MAX_LENGTH = 512;
    private static final int DONE_BUTTON_WIDTH = 200;

    /** Placeholder size; the list resizes and repositions its widgets every frame. */
    private static final int WIDGET_WIDTH = 150;

    private static final int WIDGET_HEIGHT = 20;

    private final Screen lastScreen;
    private final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this);

    protected VowOptionsList list;

    protected VowOptionsScreen(Screen lastScreen, Component title) {
        super(title);
        this.lastScreen = lastScreen;
    }

    /** Populate {@link #list}. Called once, while the screen is being built. */
    protected abstract void addOptions();

    @Override
    protected void init() {
        layout.addTitleHeader(title, font);
        list = layout.addToContents(new VowOptionsList(minecraft, this, layout));
        addOptions();
        layout.addToFooter(Button.builder(CommonComponents.GUI_DONE, button -> onClose())
                .width(DONE_BUTTON_WIDTH)
                .build());
        layout.visitWidgets(this::addRenderableWidget);
        repositionElements();
    }

    @Override
    protected void repositionElements() {
        layout.arrangeElements();
        list.updateSize(width, layout);
    }

    @Override
    public void removed() {
        super.removed();
        ModCore.config.save();
    }

    @Override
    public void onClose() {
        minecraft.setScreen(lastScreen);
    }

    /** Adds the options two per row, with a lone trailing option kept at half width. */
    protected void addPaired(List<OptionInstance<?>> options) {
        for (int i = 0; i < options.size(); i += 2) {
            if (i + 1 < options.size()) {
                list.addPair(
                        options.get(i).createButton(minecraft.options),
                        options.get(i + 1).createButton(minecraft.options));
            } else {
                list.addHalf(options.get(i).createButton(minecraft.options));
            }
        }
    }

    protected OptionInstance<Boolean> bool(String option, boolean value, Consumer<Boolean> setter) {
        return OptionInstance.createBoolean(OPTION_PREFIX + option, tooltip(option), value, setter);
    }

    protected OptionInstance<Integer> slider(String option, int min, int max, int value, Consumer<Integer> setter) {
        return new OptionInstance<>(
                OPTION_PREFIX + option,
                tooltip(option),
                (caption, sliderValue) -> Component.translatable("options.generic_value", caption, sliderValue),
                new OptionInstance.IntRange(min, max),
                // A value edited into wynnvp.toml outside the slider range would break the slider's math.
                Math.clamp(value, min, max),
                setter);
    }

    /** Adds a label plus text field, since options can only be toggles or sliders. */
    protected void addTextRow(String option, String value, Consumer<String> setter) {
        Component caption = Component.translatable(OPTION_PREFIX + option);

        EditBox editBox = new EditBox(font, WIDGET_WIDTH, WIDGET_HEIGHT, caption);
        editBox.setMaxLength(TEXT_MAX_LENGTH);
        editBox.setValue(value);
        editBox.setResponder(setter);
        tooltipComponent(option).ifPresent(text -> editBox.setTooltip(Tooltip.create(text)));

        list.addLabelled(caption, editBox);
    }

    private <T> OptionInstance.TooltipSupplier<T> tooltip(String option) {
        return tooltipComponent(option)
                .<OptionInstance.TooltipSupplier<T>>map(OptionInstance::cachedConstantTooltip)
                .orElseGet(OptionInstance::noTooltip);
    }

    /** Not every option has help text, so a missing key means "no tooltip" rather than a broken one. */
    private Optional<Component> tooltipComponent(String option) {
        String key = OPTION_PREFIX + option + ".tooltip";
        if (!Language.getInstance().has(key)) {
            return Optional.empty();
        }
        return Optional.of(Component.translatable(key));
    }
}
