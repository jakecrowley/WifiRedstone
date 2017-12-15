package com.jakecrowley.redstonewifi.task;

import com.mrcrayfish.device.api.task.Task;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;

public class TaskVariables {

    public HashMap<String, IBlockState> states = new HashMap<>();
    public HashMap<String, BlockPos> positions = new HashMap<>();

    public TaskVariables(){}

    public void addVariable(String t, IBlockState blockState, BlockPos blockPos){
        states.put(t, blockState);
        positions.put(t, blockPos);
    }

    public void clearVariables(String t){
        states.remove(t);
        positions.remove(t);
    }
}
