package com.jakecrowley.redstonewifi.task;

import com.jakecrowley.redstonewifi.tileentity.TEWifiLever;
import com.jakecrowley.redstonewifi.tileentity.TileEntityReceiver;
import com.mrcrayfish.device.api.task.Task;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Author: MrCrayfish
 */
public class TaskSetLeverPair extends Task
{

    private BlockPos leverPos;
    private BlockPos receiverPos;

    private TaskSetLeverPair()
    {
        super("set_pair");
    }

    public TaskSetLeverPair(BlockPos leverPos, BlockPos receiverPos)
    {
        this();
        this.leverPos = leverPos;
        this.receiverPos = receiverPos;
    }

    @Override
    public void prepareRequest(NBTTagCompound nbt) {
        nbt.setLong("leverpos", leverPos.toLong());
        nbt.setLong("receiverpos", receiverPos.toLong());
    }

    @Override
    public void processRequest(NBTTagCompound nbt, World world, EntityPlayer player) {
        BlockPos leverpos = BlockPos.fromLong(nbt.getLong("leverpos"));
        Long receiverpos = nbt.getLong("receiverpos");

        TEWifiLever lever = ((TEWifiLever)world.getTileEntity(leverpos));
        NBTTagCompound tag = lever.writeSyncTag();
        tag.setLong("pair", receiverpos);
        lever.writeToNBT(tag);

        TileEntityReceiver receiver = ((TileEntityReceiver)world.getTileEntity(BlockPos.fromLong(receiverpos)));
        NBTTagCompound rtag = receiver.writeSyncTag();
        if(rtag.hasKey("pair"))
            rtag.setString("pair", rtag.getString("pair") + "," + leverpos.toLong());
        else
            rtag.setString("pair", ""+leverpos.toLong());
        receiver.writeToNBT(rtag);
    }

    @Override
    public void prepareResponse(NBTTagCompound nbt) { }

    @Override
    public void processResponse(NBTTagCompound nbt) { }
}