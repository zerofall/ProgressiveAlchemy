package com.zerofall.progressivealchemy.blocks;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.annotation.Nonnull;

import com.zerofall.progressivealchemy.config.Config;

import moze_intel.projecte.gameObjs.container.slots.SlotPredicates;
import moze_intel.projecte.gameObjs.tiles.CondenserTile;
import moze_intel.projecte.utils.EMCHelper;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class CondenserTieredTileEntity extends CondenserTile
{
	private int tier;
	private int ticksSinceSync;
	
	public CondenserTieredTileEntity() {}
	
	public CondenserTieredTileEntity(int emcLimit) {
		this.tier = emcLimit;
	}
	
	public int getTier() {
		return this.tier;
	}
	
	public int getEmcLimit() {
		return CondenserTieredTileEntity.getEmcLimit(this.tier);
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
		
		ItemStackHandler inputInventory = null;
		try {
			Field f = CondenserTieredTileEntity.class.getSuperclass().getDeclaredField("inputInventory");
			f.setAccessible(true);
			inputInventory = (ItemStackHandler)f.get(this);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return new ReflectedItemHandler(inputInventory, ReflectedItemHandler.WriteMode.IN_OUT)
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
	public void update()
	{
		updateChest();

		if (this.getWorld().isRemote)
		{
			return;
		}

		try {
			Method m = getClass().getSuperclass().getDeclaredMethod("checkLockAndUpdate", new Class<?>[] {});
			m.setAccessible(true);
			m.invoke(this, (Object[])null);
			displayEmc = (int) this.getStoredEmc();
			
			Field f = CondenserTieredTileEntity.class.getSuperclass().getDeclaredField("lock");
			f.setAccessible(true);
			ItemStackHandler lock = (ItemStackHandler)f.get(this);

			if (!lock.getStackInSlot(0).isEmpty() && requiredEmc != 0)
			{
				condense();
			}
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			e.printStackTrace();
		}
	}
	
	private void updateChest()
	{
		if (++ticksSinceSync % 20 * 4 == 0)
		{
			world.addBlockEvent(pos, getBlockType(), 1, numPlayersUsing);
		}	

		prevLidAngle = lidAngle;
		float angleIncrement = 0.1F;

		if (numPlayersUsing > 0 && lidAngle == 0.0F)
		{
			world.playSound(null, pos, SoundEvents.BLOCK_CHEST_OPEN, SoundCategory.BLOCKS, 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
		}

		if (numPlayersUsing == 0 && lidAngle > 0.0F || numPlayersUsing > 0 && lidAngle < 1.0F)
		{
			float var8 = lidAngle;

			if (numPlayersUsing > 0)
			{
				lidAngle += angleIncrement;
			}
			else
			{
				lidAngle -= angleIncrement;
			}

			if (lidAngle > 1.0F)
			{
				lidAngle = 1.0F;
			}

			if (lidAngle < 0.5F && var8 >= 0.5F)
			{
				world.playSound(null, pos, SoundEvents.BLOCK_CHEST_CLOSE, SoundCategory.BLOCKS, 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
			}

			if (lidAngle < 0.0F)
			{
				lidAngle = 0.0F;
			}
		}
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
