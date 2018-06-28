package com.zerofall.progressivealchemy;

import com.zerofall.progressivealchemy.blocks.CondenserTieredBlock;

import moze_intel.projecte.api.state.PEStateProps;
import moze_intel.projecte.gameObjs.ObjHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {

    @GameRegistry.ObjectHolder("progressivealchemy:condensertiered_1")
    public static CondenserTieredBlock condenserTieredBlockT1;
    @GameRegistry.ObjectHolder("progressivealchemy:condensertiered_2")
    public static CondenserTieredBlock condenserTieredBlockT2;
    @GameRegistry.ObjectHolder("progressivealchemy:condensertiered_3")
    public static CondenserTieredBlock condenserTieredBlockT3;
    @GameRegistry.ObjectHolder("progressivealchemy:condensertiered_4")
    public static CondenserTieredBlock condenserTieredBlockT4;
    @GameRegistry.ObjectHolder("progressivealchemy:condensertiered_5")
    public static CondenserTieredBlock condenserTieredBlockT5;


    @SideOnly(Side.CLIENT)
    public static void initModels() {
    	ModelLoader.setCustomStateMapper(
				ObjHandler.condenser,
				(new StateMap.Builder()).ignore(PEStateProps.FACING).build()
		);
    	
    	
    	registerBlock(condenserTieredBlockT1);
    	registerBlock(condenserTieredBlockT2);
    	registerBlock(condenserTieredBlockT3);
    	registerBlock(condenserTieredBlockT4);
    	registerBlock(condenserTieredBlockT5);
    }
    
    private static void registerBlock(Block b)
	{
		//String name = ForgeRegistries.BLOCKS.getKey(b).toString();
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(b), 0, new ModelResourceLocation("progressivealchemy:condensertiered", "inventory"));
	}
}
