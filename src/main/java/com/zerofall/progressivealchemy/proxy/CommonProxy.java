package com.zerofall.progressivealchemy.proxy;

import com.zerofall.progressivealchemy.ModBlocks;
import com.zerofall.progressivealchemy.ProgressiveAlchemy;
import com.zerofall.progressivealchemy.blocks.CondenserTieredBlock;
import com.zerofall.progressivealchemy.blocks.CondenserTieredTileEntity;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent e) {


        // Initialization of blocks and items typically goes here:
//        ModEntities.init();
//        ModDimensions.init();
//
//        MainCompatHandler.registerWaila();
//        MainCompatHandler.registerTOP();

    }

    public void init(FMLInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler(ProgressiveAlchemy.instance, new GuiProxy());
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(new CondenserTieredBlock(1, 1));
        event.getRegistry().register(new CondenserTieredBlock(2, 8));
        event.getRegistry().register(new CondenserTieredBlock(3, 16));
        event.getRegistry().register(new CondenserTieredBlock(4, 64));
        event.getRegistry().register(new CondenserTieredBlock(5, 256));
        GameRegistry.registerTileEntity(CondenserTieredTileEntity.class, ProgressiveAlchemy.MODID + "_condensertiered");
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        //event.getRegistry().register(new FirstItem());
        event.getRegistry().register(new ItemBlock(ModBlocks.condenserTieredBlockT1).setRegistryName(ModBlocks.condenserTieredBlockT1.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.condenserTieredBlockT2).setRegistryName(ModBlocks.condenserTieredBlockT2.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.condenserTieredBlockT3).setRegistryName(ModBlocks.condenserTieredBlockT3.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.condenserTieredBlockT4).setRegistryName(ModBlocks.condenserTieredBlockT4.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.condenserTieredBlockT5).setRegistryName(ModBlocks.condenserTieredBlockT5.getRegistryName()));
    }

}
