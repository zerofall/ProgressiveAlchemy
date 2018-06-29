package com.zerofall.progressivealchemy.blocks;

import javax.annotation.Nonnull;

import moze_intel.projecte.gameObjs.container.slots.SlotPredicates;
import moze_intel.projecte.gameObjs.tiles.CondenserTile;
import moze_intel.projecte.gameObjs.tiles.WrappedItemHandler;
import moze_intel.projecte.utils.EMCHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.IItemHandler;

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
	protected IItemHandler createAutomationInventory()
	{
		return new WrappedItemHandler(inputInventory, WrappedItemHandler.WriteMode.IN_OUT)
		{
			@Override
			public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
			{
				return SlotPredicates.HAS_EMC.test(stack) && EMCHelper.getEmcValue(stack) <= emcLimit && !isStackEqualToLock(stack)
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
