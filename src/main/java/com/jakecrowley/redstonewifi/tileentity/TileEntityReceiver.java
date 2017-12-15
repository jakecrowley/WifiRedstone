package com.jakecrowley.redstonewifi.tileentity;

import com.jakecrowley.redstonewifi.RSWifiAppMod;
import com.jakecrowley.redstonewifi.app.RSWifiApp;
import com.jakecrowley.redstonewifi.block.BlockReceiver;
import com.jakecrowley.redstonewifi.task.TaskSetState;
import com.mrcrayfish.device.api.task.Task;
import com.mrcrayfish.device.api.task.TaskManager;
import com.mrcrayfish.device.core.network.Connection;
import com.mrcrayfish.device.tileentity.TileEntityDevice;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ConcurrentModificationException;

public class TileEntityReceiver extends TileEntityDevice {

    private NBTTagCompound nbt;

    @Override
    public String getDeviceName() {
        return "receiver";
    }

    @Override
    public void onLoad()
    {
        if(this.nbt != null) {
            RSWifiAppMod.toSet.add(new TaskSetState(this.getPos(), this.nbt.getBoolean("on")));
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        try {
            if (RSWifiAppMod.toSet.size() > 0) {
                for (Task t : RSWifiAppMod.toSet) {
                    RSWifiAppMod.toSet.remove(t);
                    TaskManager.sendTask(t);
                }
            }
        } catch (ConcurrentModificationException e){ /* NOOP */ }

        BlockPos pos = new BlockPos(nbt.getInteger("x"), nbt.getInteger("y"), nbt.getInteger("z"));
        world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockReceiver.ON, nbt.getBoolean("on")));

        super.readFromNBT(nbt);
        this.nbt = nbt;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setBoolean("on", world.getBlockState(this.getPos()).getValue(BlockReceiver.ON));
        return nbt;
    }


    @Override
    public NBTTagCompound writeSyncTag()
    {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setBoolean("on", world.getBlockState(this.getPos()).getValue(BlockReceiver.ON));
        return tag;
    }


    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState.getBlock() != newSate.getBlock();
    }
}
