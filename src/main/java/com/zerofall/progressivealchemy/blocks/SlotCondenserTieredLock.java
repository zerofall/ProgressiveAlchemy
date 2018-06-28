package com.zerofall.progressivealchemy.blocks;

import java.util.function.Predicate;

import javax.annotation.Nonnull;

import moze_intel.projecte.gameObjs.container.slots.SlotGhost;
import moze_intel.projecte.utils.ItemHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class SlotCondenserTieredLock extends SlotGhost {
	public SlotCondenserTieredLock(IItemHandler itemHandler, int index, int xPosition, int yPosition, Predicate<ItemStack> predicate) {
		super(itemHandler, index, xPosition, yPosition, predicate);
	}

	@Override
	public void putStack(@Nonnull ItemStack stack)
	{
		if (!stack.isEmpty() && ItemHelper.isDamageable(stack))
		{
			stack.setItemDamage(0);
		}

		super.putStack(stack);
	}
}