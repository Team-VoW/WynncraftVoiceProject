package com.wynnvp.wynncraftvp.gui;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.config.VOWAutoConfig;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WSprite;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import io.github.cottonmc.cotton.gui.widget.icon.ItemIcon;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class LineReportGUI extends LightweightGuiDescription {

    private final ResourceLocation image =  ResourceLocation.fromNamespaceAndPath(ModCore.MODID, "wynnvplogo.png");


    public LineReportGUI() {
        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(300, 200);

        AddLogo(root);
        AddText(root);
        AddButtons(root);
    }

    private void AddLogo(WGridPanel root) {
        WSprite sprite = new WSprite(image);
        root.add(sprite, 6, -4, 5, 5);
    }

    private void AddText(WGridPanel root) {
        root.add(new WLabel(Component.literal("§5VOICES OF WYNN")).setHorizontalAlignment(HorizontalAlignment.CENTER), 8, 1);
        root.add(new WLabel(Component.literal("§fWould you like your game to report unvoiced dialogues")).setHorizontalAlignment(HorizontalAlignment.CENTER), 8, 2);
        root.add(new WLabel(Component.literal("§fencountered while playing Wynncraft to improve")).setHorizontalAlignment(HorizontalAlignment.CENTER), 8, 3);
        root.add(new WLabel(Component.literal("§fVoices of Wynn?")).setHorizontalAlignment(HorizontalAlignment.CENTER), 8, 4);
        root.add(new WLabel(Component.literal("§fWith full report, your nickname will be sent with the")).setHorizontalAlignment(HorizontalAlignment.CENTER), 8, 6);
        root.add(new WLabel(Component.literal("§freport, so that we could contact you in case we")).setHorizontalAlignment(HorizontalAlignment.CENTER), 8, 7);
        root.add(new WLabel(Component.literal("§fhave questions about it.")).setHorizontalAlignment(HorizontalAlignment.CENTER), 8, 8);
    }


    private void AddButtons(WGridPanel root) {
        WButton noneButton = new WButton(Component.literal("None"));
        noneButton.setOnClick(this::onNoneButtonClick);
        noneButton.setAlignment(HorizontalAlignment.CENTER);
        noneButton.setIcon(new ItemIcon(new ItemStack(Items.COAL)));


        WButton anonymousButton = new WButton(Component.literal("Anonymous"));
        anonymousButton.setOnClick(this::onAnonymousButtonClick);
        anonymousButton.setAlignment(HorizontalAlignment.CENTER);
        //    anonymousButton.setIcon(new ItemIcon(new ItemStack(Items.EMERALD)));

        WButton fullButton = new WButton(Component.literal("Full"));
        fullButton.setOnClick(this::onFullButtonClick);
        fullButton.setAlignment(HorizontalAlignment.CENTER);
        fullButton.setIcon(new ItemIcon(new ItemStack(Items.DIAMOND)));

        root.add(fullButton, 1, 9, 4, 1);
        root.add(anonymousButton, 6, 9, 4, 1);
        root.add(noneButton, 11, 9, 4, 1);

    }

    public static void OpenGui() {
        Minecraft.getInstance().setScreen(new LineReportScreen(new LineReportGUI()));
    }


    private void onFullButtonClick() {
        ModCore.config.setReportMissingLines(true);
        ModCore.config.setAnonymous(false);

        HasDecided();

    }

    private void onAnonymousButtonClick() {
        ModCore.config.setReportMissingLines(true);
        ModCore.config.setAnonymous(true);
        HasDecided();

    }

    private void onNoneButtonClick() {
        ModCore.config.setReportMissingLines(false);
        HasDecided();
    }

    private void HasDecided() {
        ModCore.config.setHasChosenLineReport(true);
        AutoConfig.getConfigHolder(VOWAutoConfig.class).save();
        Minecraft.getInstance().setScreen(new TitleScreen());
    }
}
