package com.jakecrowley.redstonewifi.init;

import com.jakecrowley.redstonewifi.block.BlockReceiver;
import com.jakecrowley.redstonewifi.block.BlockWifiLever;
import com.jakecrowley.redstonewifi.tileentity.TileEntityReceiver;
import com.jakecrowley.redstonewifi.tileentity.render.ReceiverRenderer;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class BlockRegister {
    public static final Block RECEIVER;
    public static final Block WIFILEVER;

    static
    {
        RECEIVER = new BlockReceiver();
        WIFILEVER = new BlockWifiLever();
    }

    public static void register()
    {
        registerBlock(RECEIVER);
        registerBlock(WIFILEVER);
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
