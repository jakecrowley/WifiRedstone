package com.jakecrowley.redstonewifi.init;

import com.jakecrowley.redstonewifi.block.BlockReceiver;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class BlockRegister {
    public static final Block RECEIVER;

    static
    {
        RECEIVER = new BlockReceiver();
    }

    public static void register()
    {
        registerBlock(RECEIVER);
    }

    private static void registerBlock(Block block)
    {
        registerBlock(block, new ItemBlock(block));
    }

    private static void registerBlock(Block block, ItemBlock item)
    {
        if(block.getRegistryName() == null)
            throw new IllegalArgumentException("A block being registered does not have a registry name and could be successfully registered.");

        com.jakecrowley.redstonewifi.init.RegistrationHandler.Blocks.add(block);
        item.setRegistryName(block.getRegistryName());
        RegistrationHandler.Items.add(item);
    }
}
