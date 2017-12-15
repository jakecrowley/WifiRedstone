package com.jakecrowley.redstonewifi.task;

import com.jakecrowley.redstonewifi.RSWifiAppMod;
import com.jakecrowley.redstonewifi.app.RSWifiApp;
import com.jakecrowley.redstonewifi.block.BlockReceiver;
import com.jakecrowley.redstonewifi.tileentity.TileEntityReceiver;
import com.mrcrayfish.device.api.task.Task;
import com.mrcrayfish.device.core.network.NetworkDevice;
import com.mrcrayfish.device.object.tiles.Tile;
import net.minecraft.block.Block;
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

    private BlockPos bPos;
    private boolean state;

    private TaskSetState()
    {
        super("set_state");
    }

    public TaskSetState(BlockPos bPos, boolean state)
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
        System.out.println(nbt);
        BlockPos blockPos = BlockPos.fromLong(nbt.getLong("pos"));
        IBlockState blockState = world.getBlockState(blockPos).withProperty(BlockReceiver.ON, nbt.getBoolean("state"));
        world.setBlockState(blockPos, blockState);
    }

    @Override
    public void prepareResponse(NBTTagCompound nbt) { }

    @Override
    public void processResponse(NBTTagCompound nbt) { }
}