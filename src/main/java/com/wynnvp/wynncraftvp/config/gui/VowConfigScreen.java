/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.config.gui;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.config.ConfigChangeApplier;
import java.util.List;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.tabs.Tab;
import net.minecraft.client.gui.components.tabs.TabManager;
import net.minecraft.client.gui.components.tabs.TabNavigationBar;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

/** The tabbed Voices of Wynn settings screen, reachable through Mod Menu and {@code /vow-config}. */
public class VowConfigScreen extends Screen {
    private static final int DONE_BUTTON_WIDTH = 200;
    private static final int SEARCH_WIDTH = 200;
    private static final int SEARCH_HEIGHT = 20;
    private static final int SEARCH_GAP = 4;

    private final Screen lastScreen;
    private final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this);
    private final TabManager tabManager = new TabManager(
            this::addRenderableWidget, this::removeWidget, tab -> onTabSelected((VowSettingsTab) tab), tab -> {});

    private List<VowSettingsTab> tabs = List.of();
    private TabNavigationBar tabNavigationBar;
    private EditBox searchBox;

    public VowConfigScreen(Screen parent) {
        super(Component.translatable("wynnvp.config.title"));
        lastScreen = parent;
    }

    @Override
    protected void init() {
        tabs = VowSettings.all(ModCore.config).stream()
                .map(category -> new VowSettingsTab(minecraft, this, category))
                .toList();
        tabNavigationBar = TabNavigationBar.builder(tabManager, width)
                .addTabs(tabs.toArray(Tab[]::new))
                .build();
        addRenderableWidget(tabNavigationBar);

        searchBox = addRenderableWidget(new EditBox(font, SEARCH_WIDTH, SEARCH_HEIGHT, Component.empty()));
        searchBox.setHint(Component.translatable("wynnvp.config.search.hint"));
        searchBox.setResponder(this::onSearchChanged);

        layout.addToFooter(Button.builder(CommonComponents.GUI_DONE, button -> onClose())
                .width(DONE_BUTTON_WIDTH)
                .build());
        layout.visitWidgets(this::addRenderableWidget);
        tabNavigationBar.selectTab(0, false);
        repositionElements();
    }

    @Override
    protected void repositionElements() {
        if (tabNavigationBar == null) {
            return;
        }
        tabNavigationBar.setWidth(width);
        tabNavigationBar.arrangeElements();
        int top = tabNavigationBar.getRectangle().bottom();
        searchBox.setPosition((width - SEARCH_WIDTH) / 2, top + SEARCH_GAP);
        int contentTop = searchBox.getY() + searchBox.getHeight() + SEARCH_GAP;
        tabManager.setTabArea(
                new ScreenRectangle(0, contentTop, width, height - layout.getFooterHeight() - contentTop));
        layout.setHeaderHeight(contentTop);
        layout.arrangeElements();
    }

    @Override
    public boolean keyPressed(KeyEvent keyEvent) {
        if (tabNavigationBar.keyPressed(keyEvent)) {
            return true;
        }
        return super.keyPressed(keyEvent);
    }

    @Override
    public void removed() {
        super.removed();
        ModCore.config.save();
        ConfigChangeApplier.applyChanges();
    }

    @Override
    public void onClose() {
        minecraft.setScreen(lastScreen);
    }

    private void onTabSelected(VowSettingsTab tab) {
        tab.rebuild(searchBox == null ? "" : searchBox.getValue());
    }

    private void onSearchChanged(String query) {
        for (int index = 0; index < tabs.size(); index++) {
            VowSettingsTab tab = tabs.get(index);
            tab.rebuild(query);
            tabNavigationBar.setTabActiveState(index, tab.hasMatches(query));
        }
        VowSettingsTab current = tabManager.getCurrentTab() instanceof VowSettingsTab tab ? tab : null;
        if (current != null && !current.hasMatches(query)) {
            for (int index = 0; index < tabs.size(); index++) {
                if (tabs.get(index).hasMatches(query)) {
                    tabNavigationBar.selectTab(index, false);
                    return;
                }
            }
        }
    }
}
