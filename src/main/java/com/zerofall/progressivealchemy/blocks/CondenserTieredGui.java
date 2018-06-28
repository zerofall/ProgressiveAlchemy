package com.zerofall.progressivealchemy.blocks;

import com.zerofall.progressivealchemy.ProgressiveAlchemy;

import moze_intel.projecte.utils.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class CondenserTieredGui extends GuiContainer {

	private static final ResourceLocation texture = new ResourceLocation(ProgressiveAlchemy.MODID.toLowerCase(), "textures/gui/condensertiered.png");
	private final CondenserTieredContainer container;
	
	public CondenserTieredGui(InventoryPlayer invPlayer, CondenserTieredTileEntity tile) {
		super(new CondenserTieredContainer(invPlayer, tile));
		this.container = (CondenserTieredContainer)this.inventorySlots;
		this.xSize = 255;
		this.ySize = 233;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) 
	{
		GlStateManager.color(1, 1, 1, 1);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		
		int progress = container.getProgressScaled();
		this.drawTexturedModalRect(x + 33, y + 10, 0, 235, progress, 10);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int var1, int var2) 
	{
		long toDisplay = container.displayEmc > container.requiredEmc ? container.requiredEmc : container.displayEmc;
		this.fontRenderer.drawString(Constants.EMC_FORMATTER.format(toDisplay), 140, 10, 4210752);
	}
}
