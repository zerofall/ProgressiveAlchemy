package com.zerofall.progressivealchemy.blocks;

import java.util.List;

import javax.annotation.Nonnull;

import com.zerofall.progressivealchemy.ProgressiveAlchemy;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoAccessor;
import mcjty.theoneprobe.api.ProbeMode;
import moze_intel.projecte.gameObjs.blocks.Condenser;
import moze_intel.projecte.utils.Constants;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.common.Optional;


@Optional.Interface(iface = "mcjty.theoneprobe.api.IProbeInfoAccessor", modid = "theoneprobe") 
public class CondenserTieredBlock extends Condenser implements IProbeInfoAccessor {
	
	private final int tier;

	public CondenserTieredBlock(int tier) 
	{
		this.tier = tier;
		this.setUnlocalizedName("condensertiered_"+tier);
		this.setRegistryName("condensertiered_"+tier);
	}
	
	@Nonnull
	@Override
	public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state)
	{
		return new CondenserTieredTileEntity(tier);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote) 
		{
			player.openGui(ProgressiveAlchemy.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
		}
		
		return true;
	}
	
	@Optional.Method(modid="theoneprobe")
	@Override
	public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world,
			IBlockState blockState, IProbeHitData data) {
		TileEntity te = world.getTileEntity(data.getPos());
        if (te instanceof CondenserTieredTileEntity) {
        	CondenserTieredTileEntity tile = (CondenserTieredTileEntity) te;
        	probeInfo.horizontal().text(TextFormatting.DARK_AQUA + IProbeInfo.STARTLOC + "EMC Limit: " + Constants.EMC_FORMATTER.format(tile.getEmcLimit()) + IProbeInfo.ENDLOC);
        }
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
        tooltip.add(TextFormatting.DARK_AQUA + "EMC Limit: " + Constants.EMC_FORMATTER.format(CondenserTieredTileEntity.getEmcLimit(tier)));
	}
	
	@SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation("condensertiered", "inventory"));
    }
}
