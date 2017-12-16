package com.jakecrowley.redstonewifi.gui;

import com.jakecrowley.redstonewifi.Reference;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiListButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class WifiLeverGUI extends GuiScreen{

    GuiButton a;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        mc.renderEngine.bindTexture(new ResourceLocation(Reference.MOD_ID, "gui/background.png"));
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame(){
        return false;
    }

    @Override
    public void initGui(){
        this.buttonList.add(this.a = new GuiButton(0, this.width / 2 - 100, this.height / 2 - 24, "Test"));
    }

    @Override
    protected void actionPerformed(GuiButton button){
        if(button == this.a){
            System.out.println("button pressed");
        }
    }
}
