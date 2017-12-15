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
public class TaskSetName extends Task
{

    private BlockPos bPos;
    private String name;

    private TaskSetName()
    {
        super("set_name");
    }

    public TaskSetName(BlockPos bPos, String name)
    {
        this();
        this.bPos = bPos;
        this.name = name;
    }

    @Override
    public void prepareRequest(NBTTagCompound nbt) {
        nbt.setLong("pos", bPos.toLong());
        nbt.setString("name", name);
    }

    @Override
    public void processRequest(NBTTagCompound nbt, World world, EntityPlayer player) {
        BlockPos pos = BlockPos.fromLong(nbt.getLong("pos"));
        String name = nbt.getString("name");
        TileEntityReceiver receiver = ((TileEntityReceiver)world.getTileEntity(pos));
        NBTTagCompound tag = receiver.writeSyncTag();
        tag.setString("name", name);
        receiver.writeToNBT(tag);
    }

    @Override
    public void prepareResponse(NBTTagCompound nbt) { }

    @Override
    public void processResponse(NBTTagCompound nbt) { }
}