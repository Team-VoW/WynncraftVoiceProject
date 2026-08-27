/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.config.gui;

import java.util.List;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.tabs.Tab;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

/** A category tab that owns and rebuilds its scrolling list of settings. */
public final class VowSettingsTab implements Tab {
    private final Minecraft minecraft;
    private final VowSettings.Category category;
    private final VowOptionsList list;

    public VowSettingsTab(Minecraft minecraft, Screen screen, VowSettings.Category category) {
        this.minecraft = minecraft;
        this.category = category;
        list = new VowOptionsList(minecraft, screen);
    }

    @Override
    public Component getTabTitle() {
        return Component.translatable("wynnvp.config.category." + category.key());
    }

    @Override
    public Component getTabExtraNarration() {
        // The button already narrates the title; there is nothing further worth repeating.
        return Component.empty();
    }

    @Override
    public void visitChildren(Consumer<AbstractWidget> consumer) {
        consumer.accept(list);
    }

    @Override
    public void doLayout(ScreenRectangle rectangle) {
        list.updateSizeAndPosition(rectangle.width(), rectangle.height(), rectangle.left(), rectangle.top());
    }

    public void rebuild(String query) {
        list.clear();
        boolean addedSetting = false;
        for (VowSettings.Section section : category.sections()) {
            List<VowSetting> matching = section.settings().stream()
                    .filter(setting -> matches(setting, query))
                    .toList();
            if (!matching.isEmpty()) {
                addSection(section, matching);
                addedSetting = true;
            }
        }
        if (!addedSetting) {
            list.addHeader(Component.translatable("wynnvp.config.search.noResults"));
        }
    }

    public boolean hasMatches(String query) {
        return category.sections().stream()
                .flatMap(section -> section.settings().stream())
                .anyMatch(setting -> matches(setting, query));
    }

    private boolean matches(VowSetting setting, String query) {
        return SettingSearchFilter.matches(
                query,
                setting.label().getString(),
                setting.tooltip().map(Component::getString).orElse(""));
    }

    private void addSection(VowSettings.Section section, List<VowSetting> settings) {
        list.addHeader(Component.translatable("wynnvp.config.section." + section.key()));
        VowSetting pending = null;
        AbstractWidget pendingWidget = null;
        for (VowSetting setting : settings) {
            AbstractWidget widget = setting.createWidget(minecraft);
            if (setting.rowSpan() == VowSetting.RowSpan.FULL) {
                if (pending != null) {
                    list.addHalf(pendingWidget, pending.enabled());
                    pending = null;
                }
                list.addLabelled(setting.label(), widget, setting.enabled());
            } else if (pending == null) {
                pending = setting;
                pendingWidget = widget;
            } else {
                list.addPair(pendingWidget, pending.enabled(), widget, setting.enabled());
                pending = null;
            }
        }
        if (pending != null) {
            list.addHalf(pendingWidget, pending.enabled());
        }
    }
}
