package com.jakecrowley.redstonewifi.task;

import com.jakecrowley.redstonewifi.block.BlockReceiver;
import com.jakecrowley.redstonewifi.block.BlockWifiLever;
import com.mrcrayfish.device.api.task.Task;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Author: MrCrayfish
 */
public class TaskSetStateWL extends Task
{

    private BlockPos bPos;
    private boolean state;

    private TaskSetStateWL()
    {
        super("set_state");
    }

    public TaskSetStateWL(BlockPos bPos, boolean state)
    {
        this();
        this.bPos = bPos;
        this.state = state;
    }

    @Override
    public void prepareRequest(NBTTagCompound nbt) {
        nbt.setLong("pos", bPos.toLong());
        nbt.setBoolean("state", state);
    }

    @Override
    public void processRequest(NBTTagCompound nbt, World world, EntityPlayer player) {
        BlockPos blockPos = BlockPos.fromLong(nbt.getLong("pos"));
        IBlockState blockState = world.getBlockState(blockPos).withProperty(BlockWifiLever.ON, nbt.getBoolean("state"));
        world.setBlockState(blockPos, blockState);
    }

    @Override
    public void prepareResponse(NBTTagCompound nbt) { }

    @Override
    public void processResponse(NBTTagCompound nbt) { }
}