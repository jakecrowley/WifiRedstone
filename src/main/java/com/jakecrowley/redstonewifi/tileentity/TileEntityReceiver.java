package com.jakecrowley.redstonewifi.tileentity;

import com.jakecrowley.redstonewifi.RSWifiAppMod;
import com.jakecrowley.redstonewifi.app.RSWifiApp;
import com.jakecrowley.redstonewifi.block.BlockReceiver;
import com.jakecrowley.redstonewifi.task.TaskSetState;
import com.mrcrayfish.device.api.task.Task;
import com.mrcrayfish.device.api.task.TaskManager;
import com.mrcrayfish.device.core.network.Connection;
import com.mrcrayfish.device.tileentity.TileEntityDevice;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.annotation.Nullable;
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

        try {
            FMLCommonHandler.instance().getMinecraftServerInstance().getServerOwner();
        } catch (NullPointerException e){
            BlockPos pos = new BlockPos(nbt.getInteger("x"), nbt.getInteger("y"), nbt.getInteger("z"));
            world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockReceiver.ON, nbt.getBoolean("on")));
        }

        super.readFromNBT(nbt);
        this.nbt = nbt;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        nbt = (this.nbt != null) ? this.nbt : nbt;
        nbt.setBoolean("on", world.getBlockState(this.getPos()).getValue(BlockReceiver.ON));
        this.nbt = nbt;
        super.writeToNBT(nbt);
        return nbt;
    }


    @Override
    public NBTTagCompound writeSyncTag()
    {
        NBTTagCompound tag = (this.nbt != null) ? this.nbt : new NBTTagCompound();
        tag.setBoolean("on", world.getBlockState(this.getPos()).getValue(BlockReceiver.ON));
        this.nbt = tag;
        return tag;
    }

    @Nullable
    @Override
    public ITextComponent getDisplayName()
    {
        if(nbt != null)
            return nbt.hasKey("name") ? new TextComponentString(nbt.getString("name")) : null;
        return null;
    }


    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState.getBlock() != newSate.getBlock();
    }

    public String getName(){
        return (nbt.hasKey("name")) ? nbt.getString("name") : null;
    }
}
