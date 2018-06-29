package com.zerofall.progressivealchemy.proxy;

import java.io.File;

import com.zerofall.progressivealchemy.ModBlocks;
import com.zerofall.progressivealchemy.ProgressiveAlchemy;
import com.zerofall.progressivealchemy.blocks.CondenserTieredBlock;
import com.zerofall.progressivealchemy.blocks.CondenserTieredTileEntity;
import com.zerofall.progressivealchemy.config.Config;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber
public class CommonProxy {
	
	public static Configuration config;

    public void preInit(FMLPreInitializationEvent e) {
    	File directory = e.getModConfigurationDirectory();
        config = new Configuration(new File(directory.getPath(), "progressivealchemy.cfg"));
        Config.readConfig();
    }

    public void init(FMLInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler(ProgressiveAlchemy.instance, new GuiProxy());
    }
    
    public void postInit(FMLPostInitializationEvent e) {
        if (config.hasChanged()) {
            config.save();
        }
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        if (Config.tier1Enabled) event.getRegistry().register(new CondenserTieredBlock(1, Config.tier1EmcLimit));
        if (Config.tier2Enabled) event.getRegistry().register(new CondenserTieredBlock(2, Config.tier2EmcLimit));
        if (Config.tier3Enabled) event.getRegistry().register(new CondenserTieredBlock(3, Config.tier3EmcLimit));
        if (Config.tier4Enabled) event.getRegistry().register(new CondenserTieredBlock(4, Config.tier4EmcLimit));
        if (Config.tier5Enabled) event.getRegistry().register(new CondenserTieredBlock(5, Config.tier5EmcLimit));
        if (Config.tier6Enabled) event.getRegistry().register(new CondenserTieredBlock(6, Config.tier6EmcLimit));
        if (Config.tier7Enabled) event.getRegistry().register(new CondenserTieredBlock(7, Config.tier7EmcLimit));
        if (Config.tier8Enabled) event.getRegistry().register(new CondenserTieredBlock(8, Config.tier8EmcLimit));
        GameRegistry.registerTileEntity(CondenserTieredTileEntity.class, ProgressiveAlchemy.MODID + "_condensertiered");
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        //event.getRegistry().register(new FirstItem());
    	if (Config.tier1Enabled) event.getRegistry().register(new ItemBlock(ModBlocks.condenserTieredBlockT1).setRegistryName(ModBlocks.condenserTieredBlockT1.getRegistryName()));
    	if (Config.tier2Enabled) event.getRegistry().register(new ItemBlock(ModBlocks.condenserTieredBlockT2).setRegistryName(ModBlocks.condenserTieredBlockT2.getRegistryName()));
    	if (Config.tier3Enabled) event.getRegistry().register(new ItemBlock(ModBlocks.condenserTieredBlockT3).setRegistryName(ModBlocks.condenserTieredBlockT3.getRegistryName()));
    	if (Config.tier4Enabled) event.getRegistry().register(new ItemBlock(ModBlocks.condenserTieredBlockT4).setRegistryName(ModBlocks.condenserTieredBlockT4.getRegistryName()));
    	if (Config.tier5Enabled) event.getRegistry().register(new ItemBlock(ModBlocks.condenserTieredBlockT5).setRegistryName(ModBlocks.condenserTieredBlockT5.getRegistryName()));
    	if (Config.tier6Enabled) event.getRegistry().register(new ItemBlock(ModBlocks.condenserTieredBlockT6).setRegistryName(ModBlocks.condenserTieredBlockT6.getRegistryName()));
    	if (Config.tier7Enabled) event.getRegistry().register(new ItemBlock(ModBlocks.condenserTieredBlockT7).setRegistryName(ModBlocks.condenserTieredBlockT7.getRegistryName()));
    	if (Config.tier8Enabled) event.getRegistry().register(new ItemBlock(ModBlocks.condenserTieredBlockT8).setRegistryName(ModBlocks.condenserTieredBlockT8.getRegistryName()));
    }

}
