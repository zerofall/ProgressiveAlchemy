package com.zerofall.progressivealchemy.blocks;

import moze_intel.projecte.gameObjs.container.CondenserContainer;
import moze_intel.projecte.gameObjs.container.slots.SlotPredicates;
import moze_intel.projecte.gameObjs.container.slots.ValidatedSlot;
import moze_intel.projecte.utils.EMCHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraftforge.items.IItemHandler;

public class CondenserTieredContainer extends CondenserContainer {
		
	public CondenserTieredContainer(InventoryPlayer invPlayer, CondenserTieredTileEntity condenser)
	{
		super(invPlayer,condenser);
		
	}
	
	@Override
	protected void initSlots(InventoryPlayer invPlayer) {
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
}

