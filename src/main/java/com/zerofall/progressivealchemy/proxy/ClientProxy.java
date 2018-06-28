package com.zerofall.progressivealchemy.proxy;

import com.zerofall.progressivealchemy.ModBlocks;
import com.zerofall.progressivealchemy.blocks.CondenserTieredRenderer;
import com.zerofall.progressivealchemy.blocks.CondenserTieredTileEntity;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        ModBlocks.initModels();
        ClientRegistry.bindTileEntitySpecialRenderer(CondenserTieredTileEntity.class, new CondenserTieredRenderer());
    }

}
