package com.zerofall.progressivealchemy.blocks;

import java.lang.reflect.Field;

import javax.annotation.Nonnull;

import moze_intel.projecte.gameObjs.container.CondenserContainer;
import moze_intel.projecte.gameObjs.container.slots.SlotPredicates;
import moze_intel.projecte.gameObjs.container.slots.ValidatedSlot;
import moze_intel.projecte.gameObjs.tiles.CondenserTile;
import moze_intel.projecte.network.PacketHandler;
import moze_intel.projecte.utils.Constants;
import moze_intel.projecte.utils.EMCHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;

public class CondenserTieredContainer extends Container {
	
	final CondenserTile tile;
	public int displayEmc;
	public int requiredEmc;
		
	public CondenserTieredContainer(InventoryPlayer invPlayer, CondenserTieredTileEntity condenser)
	{
		tile = condenser;
		tile.numPlayersUsing++;
		initSlots(invPlayer);
	}
	
	void initSlots(InventoryPlayer invPlayer) {
		
		this.addSlotToContainer(new SlotCondenserTieredLock(tile.getLock(), 0, 12, 6, s -> SlotPredicates.HAS_EMC.test(s) && EMCHelper.getEmcValue(s) <= ((CondenserTieredTileEntity)tile).getEmcLimit()));

		IItemHandler handler = tile.getInput();

		int counter = 0;
		//Condenser Inventory
		for (int i = 0; i < 7; i++)
			for (int j = 0; j < 13; j++)
				this.addSlotToContainer(new ValidatedSlot(handler, counter++, 12 + j * 18, 26 + i * 18, s -> SlotPredicates.HAS_EMC.test(s) && EMCHelper.getEmcValue(s) <= ((CondenserTieredTileEntity)tile).getEmcLimit() && !tile.isStackEqualToLock(s)));

		//Player Inventory
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 9; j++)
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 48 + j * 18, 154 + i * 18));

		//Player Hotbar
		for (int i = 0; i < 9; i++)
			this.addSlotToContainer(new Slot(invPlayer, i, 48 + i * 18, 212));
	}
	
	@Override
	public void addListener(IContainerListener listener)
	{
		super.addListener(listener);
		PacketHandler.sendProgressBarUpdateInt(listener, this, 0, tile.displayEmc);
		PacketHandler.sendProgressBarUpdateInt(listener, this, 1, tile.requiredEmc);
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		if (displayEmc != tile.displayEmc)
		{
			for (IContainerListener listener : listeners)
			{
				PacketHandler.sendProgressBarUpdateInt(listener, this, 0, tile.displayEmc);
			}

			displayEmc = tile.displayEmc;
		}

		if (requiredEmc != tile.requiredEmc)
		{
			for (IContainerListener listener : listeners)
			{
				PacketHandler.sendProgressBarUpdateInt(listener, this, 1, tile.requiredEmc);
			}

			requiredEmc = tile.requiredEmc;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data)
	{
		switch(id)
		{
			case 0: displayEmc = data; break;
			case 1: requiredEmc = data; break;
		}
	}

	@Nonnull
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex)
	{
		Slot slot = this.getSlot(slotIndex);
		
		if (slot == null || !slot.getHasStack())
		{
			return ItemStack.EMPTY;
		}
		
		ItemStack stack = slot.getStack();
		ItemStack newStack = stack.copy();

		if (slotIndex <= 91)
		{
			if (!this.mergeItemStack(stack, 92, 127, false))
			{
				return ItemStack.EMPTY;
			}
		}
		else if (!EMCHelper.doesItemHaveEmc(stack) || !this.mergeItemStack(stack, 1, 91, false))
		{
			return ItemStack.EMPTY;
		}
		
		if (stack.isEmpty())
		{
			slot.putStack(ItemStack.EMPTY);
		}
		
		else slot.onSlotChanged();
		return slot.onTake(player, stack);
	}

	@Override
	public boolean canInteractWith(@Nonnull EntityPlayer player)
	{
		return player.getDistanceSq(tile.getPos().getX() + 0.5, tile.getPos().getY() + 0.5, tile.getPos().getZ() + 0.5) <= 64.0;
	}
	
	@Override
	public void onContainerClosed(EntityPlayer player)
	{
		super.onContainerClosed(player);
		tile.numPlayersUsing--;
	}

	@Nonnull
	@Override
	public ItemStack slotClick(int slot, int button, ClickType flag, EntityPlayer player)
	{
		if (slot == 0 && !tile.getLock().getStackInSlot(0).isEmpty())
		{
			if (!player.getEntityWorld().isRemote)
			{
				tile.getLock().setStackInSlot(0, ItemStack.EMPTY);
				this.detectAndSendChanges();
			}

			return ItemStack.EMPTY;
		} else return super.slotClick(slot, button, flag, player);
	}

	public int getProgressScaled()
	{
		if (requiredEmc == 0)
		{
			return 0;
		}

		if (displayEmc >= requiredEmc)
		{
			return Constants.MAX_CONDENSER_PROGRESS;
		}

		return (displayEmc * Constants.MAX_CONDENSER_PROGRESS) / requiredEmc;
	}
}

