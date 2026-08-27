/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.config.gui;

import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;

/** Declarative definition for one setting and the vanilla widget that edits it. */
public class VowSetting {
    private static final String OPTION_PREFIX = "wynnvp.config.option.";
    private static final int TEXT_MAX_LENGTH = 512;
    private static final int WIDGET_WIDTH = 150;
    private static final int WIDGET_HEIGHT = 20;

    public enum RowSpan {
        HALF,
        FULL
    }

    private final String key;
    private final RowSpan rowSpan;
    private final WidgetFactory widgetFactory;
    private BooleanSupplier enabled = () -> true;

    private VowSetting(String key, RowSpan rowSpan, WidgetFactory widgetFactory) {
        this.key = key;
        this.rowSpan = rowSpan;
        this.widgetFactory = widgetFactory;
    }

    public static VowSetting bool(String key, BooleanSupplier get, Consumer<Boolean> set) {
        return new VowSetting(
                key,
                RowSpan.HALF,
                minecraft -> OptionInstance.createBoolean(OPTION_PREFIX + key, tooltip(key), get.getAsBoolean(), set)
                        .createButton(minecraft.options));
    }

    public static SliderSetting slider(String key, int min, int max, IntSupplier get, IntConsumer set) {
        return new SliderSetting(key, min, max, get, set);
    }

    public static VowSetting text(String key, Supplier<String> get, Consumer<String> set) {
        return new VowSetting(key, RowSpan.FULL, minecraft -> {
            Component caption = Component.translatable(OPTION_PREFIX + key);
            EditBox editBox = new EditBox(minecraft.font, WIDGET_WIDTH, WIDGET_HEIGHT, caption);
            editBox.setMaxLength(TEXT_MAX_LENGTH);
            editBox.setValue(get.get());
            editBox.setResponder(set);
            tooltipComponent(key).ifPresent(text -> editBox.setTooltip(Tooltip.create(text)));
            return editBox;
        });
    }

    public VowSetting enabledWhen(BooleanSupplier enabled) {
        this.enabled = enabled;
        return this;
    }

    public Component label() {
        return Component.translatable(OPTION_PREFIX + key);
    }

    public Optional<Component> tooltip() {
        return tooltipComponent(key);
    }

    public RowSpan rowSpan() {
        return rowSpan;
    }

    public BooleanSupplier enabled() {
        return enabled;
    }

    public AbstractWidget createWidget(Minecraft minecraft) {
        return widgetFactory.create(minecraft);
    }

    private static <T> OptionInstance.TooltipSupplier<T> tooltip(String key) {
        return tooltipComponent(key)
                .<OptionInstance.TooltipSupplier<T>>map(OptionInstance::cachedConstantTooltip)
                .orElseGet(OptionInstance::noTooltip);
    }

    /** Missing translation keys deliberately mean that an option has no help text. */
    private static Optional<Component> tooltipComponent(String key) {
        String tooltipKey = OPTION_PREFIX + key + ".tooltip";
        if (!Language.getInstance().has(tooltipKey)) {
            return Optional.empty();
        }
        return Optional.of(Component.translatable(tooltipKey));
    }

    @FunctionalInterface
    private interface WidgetFactory {
        AbstractWidget create(Minecraft minecraft);
    }

    /** A slider that may replace vanilla's generic integer display with a translated unit format. */
    public static final class SliderSetting extends VowSetting {
        private final String key;
        private final int min;
        private final int max;
        private final IntSupplier get;
        private final IntConsumer set;
        private String valueFormat = "options.generic_value";

        private SliderSetting(String key, int min, int max, IntSupplier get, IntConsumer set) {
            super(key, RowSpan.HALF, minecraft -> null);
            this.key = key;
            this.min = min;
            this.max = max;
            this.get = get;
            this.set = set;
        }

        public SliderSetting withValueFormat(String langKey) {
            valueFormat = langKey;
            return this;
        }

        /** Narrowed so the two builder calls can be chained in either order. */
        @Override
        public SliderSetting enabledWhen(BooleanSupplier enabled) {
            super.enabledWhen(enabled);
            return this;
        }

        @Override
        public AbstractWidget createWidget(Minecraft minecraft) {
            return new OptionInstance<Integer>(
                            OPTION_PREFIX + key,
                            VowSetting.tooltip(key),
                            (caption, value) -> Component.translatable(valueFormat, caption, value),
                            new OptionInstance.IntRange(min, max),
                            Math.clamp(get.getAsInt(), min, max),
                            value -> set.accept(value))
                    .createButton(minecraft.options);
        }
    }
}
