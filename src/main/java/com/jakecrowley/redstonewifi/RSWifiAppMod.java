package com.jakecrowley.redstonewifi;

import com.jakecrowley.redstonewifi.app.RSWifiApp;
import com.jakecrowley.redstonewifi.gui.GuiHandler;
import com.jakecrowley.redstonewifi.init.RegistrationHandler;
import com.jakecrowley.redstonewifi.task.TaskSetName;
import com.jakecrowley.redstonewifi.task.TaskSetState;
import com.mrcrayfish.device.api.ApplicationManager;
import com.mrcrayfish.device.api.task.Task;
import com.mrcrayfish.device.api.task.TaskManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

@Mod(modid = Reference.MOD_ID, version = Reference.MOD_VERSION, acceptedMinecraftVersions = Reference.MC_VERSION, dependencies = Reference.DEPENDS)
public class RSWifiAppMod
{

    @Mod.Instance
    public static RSWifiAppMod instance = new RSWifiAppMod();

    public static ArrayList<Task> toSet = new ArrayList<>();

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
        logger = event.getModLog();
        RegistrationHandler.init();
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

        ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID+"RSWifiApp", "RSWifi"), RSWifiApp.class);
        TaskManager.registerTask(TaskSetState.class);
        TaskManager.registerTask(TaskSetName.class);
    }

    public static Logger getLogger()
    {
        return logger;
    }
}
