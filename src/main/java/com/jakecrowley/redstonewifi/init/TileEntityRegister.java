package com.jakecrowley.redstonewifi.init;

import com.jakecrowley.redstonewifi.tileentity.TEWifiLever;
import com.jakecrowley.redstonewifi.tileentity.TileEntityReceiver;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityRegister {
    public static void register(){
        GameRegistry.registerTileEntity(TileEntityReceiver.class, "dm_rswifi:receiver");
        GameRegistry.registerTileEntity(TEWifiLever.class, "dm_rswifi:wifilever");
    }
}
