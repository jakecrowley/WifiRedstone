package com.jakecrowley.redstonewifi.gui;

import com.jakecrowley.redstonewifi.Reference;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiListButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class WifiLeverGUI extends GuiScreen{

    GuiButton a;

    int guiWidth = 250;
    int guiHeight = 165;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        int guiX = (width - guiWidth) / 2;
        int guiY = (height - guiHeight) / 2;
        GL11.glColor4f(1, 1, 1, 1);
        this.drawDefaultBackground();
        mc.renderEngine.bindTexture(new ResourceLocation(Reference.MOD_ID, "gui/background.png"));
        drawTexturedModalRect(guiX, guiY, 0, 0, guiWidth, guiHeight);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame(){
        return false;
    }

    @Override
    public void initGui(){
        //this.buttonList.add(this.a = new GuiButton(0, this.width / 2 - 100, this.height / 2 - 24, "Test"));
    }

    @Override
    protected void actionPerformed(GuiButton button){
        if(button == this.a){
            System.out.println("button pressed");
        }
    }
}
