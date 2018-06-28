package com.zerofall.progressivealchemy.blocks;

import javax.annotation.Nonnull;

import moze_intel.projecte.gameObjs.tiles.CondenserTile;
import net.minecraft.nbt.NBTTagCompound;

public class CondenserTieredTileEntity extends CondenserTile
{
	private int emcLimit;
	
	public CondenserTieredTileEntity() {}
	
	public CondenserTieredTileEntity(int emcLimit) {
		this.emcLimit = emcLimit;
	}
	
	public int getEmcLimit() {
		return this.emcLimit;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		emcLimit = nbt.getInteger("EmcLimit");
	}

	@Nonnull
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		nbt = super.writeToNBT(nbt);
		nbt.setInteger("EmcLimit", emcLimit);
		return nbt;
	}
}
