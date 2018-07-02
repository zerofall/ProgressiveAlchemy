package com.zerofall.progressivealchemy;

import org.apache.logging.log4j.Logger;

import com.zerofall.progressivealchemy.proxy.CommonProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ProgressiveAlchemy.MODID, name = ProgressiveAlchemy.MODNAME, version = ProgressiveAlchemy.MODVERSION, dependencies = "required-after:projecte@[1.12-PE1.3.0,);", useMetadata = true)
public class ProgressiveAlchemy {

    public static final String MODID = "progressivealchemy";
    public static final String MODNAME = "ProgressiveAlchemy";
    public static final String MODVERSION = "1.12.2-1.0.0";

    @SidedProxy(clientSide = "com.zerofall.progressivealchemy.proxy.ClientProxy", serverSide = "com.zerofall.progressivealchemy.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static ProgressiveAlchemy instance;

    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        //proxy.postInit(e);
    }
}