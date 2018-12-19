package com.zerofall.progressivealchemy.blocks;

import javax.annotation.Nonnull;

import com.zerofall.progressivealchemy.config.Config;

import moze_intel.projecte.gameObjs.container.slots.SlotPredicates;
import moze_intel.projecte.gameObjs.tiles.CondenserTile;
import moze_intel.projecte.gameObjs.tiles.WrappedItemHandler;
import moze_intel.projecte.utils.EMCHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.IItemHandler;

public class CondenserTieredTileEntity extends CondenserTile
{
	private int tier;
	
	public CondenserTieredTileEntity() {}
	
	public CondenserTieredTileEntity(int tier) {
		this.tier = tier;
	}
	
	public int getTier() {
		return this.tier;
	}
	
	public long getEmcLimit() {
		return (long)CondenserTieredTileEntity.getEmcLimit(this.tier);
	}
	
	public static int getEmcLimit(int tier) {
		switch (tier) {
		case 1: return Config.tier1EmcLimit;
		case 2: return Config.tier2EmcLimit;
		case 3: return Config.tier3EmcLimit;
		case 4: return Config.tier4EmcLimit;
		case 5: return Config.tier5EmcLimit;
		case 6: return Config.tier6EmcLimit;
		case 7: return Config.tier7EmcLimit;
		case 8: return Config.tier8EmcLimit;
		default: return Integer.MAX_VALUE;
		}
	}
	
	@Override
	protected IItemHandler createAutomationInventory()
	{
		return new WrappedItemHandler(inputInventory, WrappedItemHandler.WriteMode.IN_OUT)
		{
			@Override
			public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
			{
				return SlotPredicates.HAS_EMC.test(stack) && EMCHelper.getEmcValue(stack) <= getEmcLimit() && !isStackEqualToLock(stack)
						? super.insertItem(slot, stack, simulate)
						: stack;
			}

			@Override
			public ItemStack extractItem(int slot, int max, boolean simulate)
			{
				if (!getStackInSlot(slot).isEmpty() && isStackEqualToLock(getStackInSlot(slot)))
				{
					return super.extractItem(slot, max, simulate);
				}
				else
				{
					return ItemStack.EMPTY;
				}
			}
		};
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		tier = nbt.getInteger("Tier");
	}

	@Nonnull
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		nbt = super.writeToNBT(nbt);
		nbt.setInteger("Tier", tier);
		return nbt;
	}
}
