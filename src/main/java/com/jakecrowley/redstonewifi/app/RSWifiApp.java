package com.jakecrowley.redstonewifi.app;


import com.jakecrowley.redstonewifi.block.BlockReceiver;
import com.jakecrowley.redstonewifi.task.TaskSetState;
import com.jakecrowley.redstonewifi.tileentity.TileEntityReceiver;
import com.mrcrayfish.device.api.app.Application;
import com.mrcrayfish.device.api.app.Layout;
import com.mrcrayfish.device.api.app.component.Button;
import com.mrcrayfish.device.api.app.component.ItemList;
import com.mrcrayfish.device.api.app.component.Label;
import com.mrcrayfish.device.api.task.Task;
import com.mrcrayfish.device.api.task.TaskManager;
import com.mrcrayfish.device.core.Laptop;
import com.mrcrayfish.device.core.network.NetworkDevice;
import com.mrcrayfish.device.core.network.Router;
import com.mrcrayfish.device.core.network.task.TaskGetDevices;
import com.mrcrayfish.device.tileentity.TileEntityLaptop;
import com.mrcrayfish.device.tileentity.TileEntityRouter;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collection;

public class RSWifiApp extends Application {

	public static Application application;

	public static ItemList receiverList = new ItemList(10, 5, 70, 6);
	public static Label state = new Label("State:", 90, 20);
	public static Button toggleButton = new Button(90, 30, "Toggle");

	public RSWifiApp() {
		super();
	}
	
	@Override
	public void init() {
		Layout mainLayout = new Layout(200, 100);

		mainLayout.addComponent(receiverList);
		mainLayout.addComponent(state);
		mainLayout.addComponent(toggleButton);

		World world = Minecraft.getMinecraft().world;

		receiverList.removeAll();

		ArrayList<IBlockState> receivers = new ArrayList<>();
		ArrayList<BlockPos> recpos = new ArrayList<>();

		Task task = new TaskGetDevices(Laptop.getPos(), TileEntityReceiver.class);
		task.setCallback((tagCompound, success) ->
		{
			if(success)
			{
				for(String s : tagCompound.toString().split("pos:")){
					if(!s.equals("{network_devices:[{")) {
						if(s.indexOf("L") > -1) {
							String longstr = s.substring(0, s.indexOf("L"));
							BlockPos blockPos = BlockPos.fromLong(Long.parseLong(longstr));
							receivers.add(world.getBlockState(blockPos));
							recpos.add(blockPos);
							receiverList.addItem("Receiver #" + receivers.size());
						}
					}
				}
				if(receiverList.getSelectedIndex() != -1)
					state.setText("State: " + ((receivers.get(receiverList.getSelectedIndex()).getValue(BlockReceiver.ON)) ? "ON" : "OFF"));
				else if(receivers.size() >= 1)
					state.setText("State: " + ((receivers.get(0).getValue(BlockReceiver.ON)) ? "ON" : "OFF"));
			}
		});
		TaskManager.sendTask(task);

		receiverList.setItemClickListener((o, index, mouseButton) -> {
			Boolean on = receivers.get(index).getValue(BlockReceiver.ON);
			state.setText("State: " + (on ? "ON" : "OFF"));
		});

		toggleButton.setClickListener((x, y, mB) -> {
			Boolean on = receivers.get(receiverList.getSelectedIndex()).getValue(BlockReceiver.ON);

			IBlockState blockState = receivers.get(receiverList.getSelectedIndex()).withProperty(BlockReceiver.ON, !on);
			receivers.remove(receiverList.getSelectedIndex());
			receivers.add(receiverList.getSelectedIndex(), blockState);

			Task ts = new TaskSetState(world, recpos.get(receiverList.getSelectedIndex()), blockState);
			TaskManager.sendTask(ts);

			state.setText("State: " + (!on ? "ON" : "OFF"));
		});

		this.setCurrentLayout(mainLayout);
	}

	@Override
	public void load(NBTTagCompound arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save(NBTTagCompound arg0) {
		// TODO Auto-generated method stub
	}

}