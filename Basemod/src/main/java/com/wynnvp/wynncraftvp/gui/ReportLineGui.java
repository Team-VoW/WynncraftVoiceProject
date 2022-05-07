package com.wynnvp.wynncraftvp.gui;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.config.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class ReportLineGui extends GuiScreen {

    ResourceLocation texture = new ResourceLocation(ModCore.MODID, "scroll.png");
    int guiHeight = 188;
    int guiWidth = 256;

    GuiButton button1;
    final int BUTTON1 = 0;

    GuiButton button2;
    final int BUTTON2 = 1;

    GuiButton button3;
    final int BUTTON3 = 2;

    protected int textColor = 0x000000;

    private final boolean displayMainMenuAfterClose;
    public ReportLineGui(boolean displayMainMenuAfterClose){
        super();
        this.displayMainMenuAfterClose = displayMainMenuAfterClose;
    }

    @Override
    public void initGui() {
        int yPos = (height / 2) - (guiHeight / 2) + guiHeight - 50;
        int xPos = (width / 2) - (guiWidth / 2) + 30;

        buttonList.clear();
        buttonList.add(button1 = new GuiButton(BUTTON1, xPos + 137, yPos, 60, 20, "No"));
        buttonList.add(button2 = new GuiButton(BUTTON2, (width / 2) - 33, yPos, 66, 20, "Anonymous"));
        buttonList.add(button3 = new GuiButton(BUTTON3, xPos, yPos, 60, 20, "Full"));


        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();


        int centerY = (height / 2) - guiHeight / 2;
        int centerX = (width / 2) - guiWidth / 2;

        GlStateManager.pushMatrix();
        {
            GlStateManager.enableAlpha();
            Minecraft.getMinecraft().renderEngine.bindTexture(texture);
            drawTexturedModalRect(centerX, centerY, 0, 0, guiWidth, guiHeight);
        }
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(width / 2, centerY + fontRenderer.FONT_HEIGHT * 3, 0);
            GlStateManager.scale(1.3f, 1.3f, 1.3f);
            drawStr(fontRenderer, "Voices Of Wynn", 0, 0, 0xB202B4);
        }
        GlStateManager.popMatrix();
        drawStr(fontRenderer, "Would you like your game to report", width / 2, centerY + fontRenderer.FONT_HEIGHT * 4 + 4, textColor);
        drawStr(fontRenderer, "unvoiced dialogues encountered while", width / 2, centerY + fontRenderer.FONT_HEIGHT * 5 + 4, textColor);
        drawStr(fontRenderer, "playing Wynncraft to improve VOW?", width / 2, centerY + fontRenderer.FONT_HEIGHT * 6 + 4, textColor);
        drawStr(fontRenderer, "With full report, your name will be sent", width / 2, centerY + fontRenderer.FONT_HEIGHT * 8 + 4, textColor);
        drawStr(fontRenderer, "with the report, so that we could contact", width / 2, centerY + fontRenderer.FONT_HEIGHT * 9 + 4, textColor);
        drawStr(fontRenderer, "you in case we have questions about it.", width / 2, centerY + fontRenderer.FONT_HEIGHT * 10 + 4, textColor);
        drawStr(fontRenderer, "You can always change this with:", width / 2, centerY + fontRenderer.FONT_HEIGHT * 12 + 4, textColor);
        drawStr(fontRenderer, " /toggle logging", width / 2, centerY + fontRenderer.FONT_HEIGHT * 13 + 4, 0x2A6072);


        super.drawScreen(mouseX, mouseY, partialTicks);

        //Draw cursor
        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(mouseX, mouseY, 0);
            GlStateManager.scale(0.3f, 0.3f, 0.3f);
            GlStateManager.color(1, 1, 1, 1);
            Minecraft.getMinecraft().renderEngine.bindTexture(texture);
            drawTexturedModalRect(0, 0, 2, 193, 50, 62);
        }
        GlStateManager.popMatrix();
    }

    private void drawStr(FontRenderer fontRendererIn, String text, int x, int y, int color) {
        fontRendererIn.drawString(text, (x - fontRendererIn.getStringWidth(text) / 2), y, color);
    }


    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case BUTTON1:
                decline();
                break;
            case BUTTON2:
                anonymous();
                break;
            case BUTTON3:
                full();
                break;
        }
        super.actionPerformed(button);
        closeGui();
    }

    @Override
    protected void setText(String newChatText, boolean shouldOverwrite) {
        super.setText(newChatText, shouldOverwrite);
    }

    private void decline() {
        ConfigHandler.setLogMissingLines(false);
    }

    private void anonymous() {
        ConfigHandler.setLogMissingLines(true);
        ConfigHandler.setAnonymous(true);
    }

    private void full() {
        ConfigHandler.setLogMissingLines(true);
        ConfigHandler.setAnonymous(false);
    }

    protected void closeGui() {
        if (displayMainMenuAfterClose){
            Minecraft.getMinecraft().displayGuiScreen(new GuiMainMenu());
        } else {
            Minecraft.getMinecraft().displayGuiScreen(null);

        }
    }

}
