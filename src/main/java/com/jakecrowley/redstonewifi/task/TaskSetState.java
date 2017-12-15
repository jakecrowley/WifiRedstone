package com.jakecrowley.redstonewifi.task;

import com.jakecrowley.redstonewifi.RSWifiAppMod;
import com.jakecrowley.redstonewifi.app.RSWifiApp;
import com.mrcrayfish.device.api.task.Task;
import com.mrcrayfish.device.core.network.NetworkDevice;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Collection;

/**
 * Author: MrCrayfish
 */
public class TaskSetState extends Task
{

    private String task;
    private TaskSetState()
    {
        super("set_state");
    }

    public TaskSetState(World world, BlockPos bPos, IBlockState st)
    {
        this();
        this.task = this.toString();
        RSWifiAppMod.tvars.addVariable(this.task, st, bPos);
    }

    @Override
    public void prepareRequest(NBTTagCompound nbt) {
        nbt.setString("task", this.task);
    }

    @Override
    public void processRequest(NBTTagCompound nbt, World world, EntityPlayer player) {
        BlockPos blockPos = RSWifiAppMod.tvars.positions.get(nbt.getString("task"));
        IBlockState blockState = RSWifiAppMod.tvars.states.get(nbt.getString("task"));
        world.setBlockState(blockPos, blockState);
    }

    @Override
    public void prepareResponse(NBTTagCompound nbt) { }

    @Override
    public void processResponse(NBTTagCompound nbt) {
        RSWifiAppMod.tvars.clearVariables(this.task);
    }
}