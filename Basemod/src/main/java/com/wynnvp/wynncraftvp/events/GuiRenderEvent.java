package com.wynnvp.wynncraftvp.events;

import com.wynnvp.wynncraftvp.config.ConfigHandler;
import com.wynnvp.wynncraftvp.gui.ReportLineGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class GuiRenderEvent {
    @SubscribeEvent
    public static void guiRender(GuiOpenEvent event) {
        if (event.getGui() instanceof GuiMainMenu && !ConfigHandler.hasShownReportLineGui) {
            Minecraft.getMinecraft().displayGuiScreen(new ReportLineGui(true));
            event.setCanceled(true);
            ConfigHandler.setHasShownReportLineGui(true);
        }

    }
}
