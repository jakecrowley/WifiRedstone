package com.jakecrowley.redstonewifi.tileentity;

import com.jakecrowley.redstonewifi.block.BlockReceiver;
import com.mrcrayfish.device.core.network.Connection;
import com.mrcrayfish.device.tileentity.TileEntityDevice;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityReceiver extends TileEntityDevice {

    @Override
    public String getDeviceName() {
        return "receiver";
    }

    @Override
    public void onLoad()
    {
        System.out.println(this.getTileData().getBoolean("on"));
    }

    @Override
    public NBTTagCompound writeSyncTag()
    {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setBoolean("on", world.getBlockState(this.getPos()).getValue(BlockReceiver.ON));
        this.writeToNBT(tag);
        return tag;
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState.getBlock() != newSate.getBlock();
    }
}
