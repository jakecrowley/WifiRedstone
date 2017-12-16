package com.jakecrowley.redstonewifi.gui;

import com.jakecrowley.redstonewifi.Reference;
import com.jakecrowley.redstonewifi.block.BlockReceiver;
import com.jakecrowley.redstonewifi.task.TaskSetLeverPair;
import com.jakecrowley.redstonewifi.tileentity.TileEntityReceiver;
import com.mrcrayfish.device.api.app.component.ItemList;
import com.mrcrayfish.device.api.task.Task;
import com.mrcrayfish.device.api.task.TaskManager;
import com.mrcrayfish.device.core.Laptop;
import com.mrcrayfish.device.core.network.task.TaskGetDevices;
import com.mrcrayfish.device.tileentity.TileEntityLaptop;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiListButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.client.GuiSlotModList;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class WifiLeverGUI extends GuiScreen{

    GuiButton pairDevice;
    GuiButton deviceName;
    GuiButton left;
    GuiButton right;

    private int index = 0;

    int guiWidth = 250;
    int guiHeight = 165;

    private BlockPos pos;

    private ArrayList<String> receiverList = new ArrayList<>();
    private ArrayList<IBlockState> receivers = new ArrayList<>();
    private ArrayList<BlockPos> recpos = new ArrayList<>();

    public WifiLeverGUI(BlockPos pos){
        this.pos = pos;
    }

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
        Task t = new TaskGetDevices(this.pos, TileEntityReceiver.class);
        t.setCallback((tagCompound, success) ->
        {
            if(success)
            {
                for(String s : tagCompound.toString().split("pos:")){
                    if(!s.equals("{network_devices:[{")) {
                        if(s.indexOf("L") > -1) {
                            String longstr = s.substring(0, s.indexOf("L"));
                            BlockPos blockPos = BlockPos.fromLong(Long.parseLong(longstr));
                            receivers.add(mc.world.getBlockState(blockPos));
                            recpos.add(blockPos);

                            String name = ((TileEntityReceiver)mc.world.getTileEntity(blockPos)).getName();
                            if(name != null) {
                                //System.out.println("Adding " + name);
                                receiverList.add(name);
                            }else {
                                //System.out.println("Adding Receiver #" + receivers.size());
                                receiverList.add("Receiver #" + receivers.size());
                            }
                        }
                    }
                }
                this.deviceName = new GuiButton(0, this.width / 2 - 50, this.height / 2, 100, 25, receiverList.get(0));
                deviceName.enabled = false;
                this.buttonList.add(this.deviceName);
            }
        });
        TaskManager.sendTask(t);

        this.buttonList.add(this.pairDevice = new GuiButton(0, this.width / 2 - 100, this.height / 2 + 25, "Pair Receiver"));
        this.buttonList.add(this.left = new GuiButton(0, this.width / 2 - 75, this.height / 2, 25, 25, "<"));
        this.buttonList.add(this.right = new GuiButton(0, this.width / 2 + 51, this.height / 2, 25, 25, ">"));
    }

    @Override
    protected void actionPerformed(GuiButton button){
        if(button == this.left){
            if(index == 0){
                index = receiverList.size()-1;
            } else {
                index--;
            }
            GuiButton btnnew = new GuiButton(0, this.width / 2 - 50, this.height / 2, 100, 25, receiverList.get(index));
            btnnew.enabled = false;
            buttonList.set(3, btnnew);
        } else if(button == this.right){
            if(index == receiverList.size()-1){
                index = 0;
            } else {
                index++;
            }
            GuiButton btnnew = new GuiButton(0, this.width / 2 - 50, this.height / 2, 100, 25, receiverList.get(index));
            btnnew.enabled = false;
            buttonList.set(3, btnnew);
        } else if(button == this.pairDevice){
            System.out.println("Paring lever to receiver " + receiverList.get(index));
            Task t = new TaskSetLeverPair(this.pos, recpos.get(index));
            TaskManager.sendTask(t);
        }
    }
}
