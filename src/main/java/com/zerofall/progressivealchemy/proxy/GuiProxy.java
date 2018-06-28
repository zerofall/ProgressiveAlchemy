package com.zerofall.progressivealchemy.proxy;

import com.zerofall.progressivealchemy.blocks.CondenserTieredContainer;
import com.zerofall.progressivealchemy.blocks.CondenserTieredGui;
import com.zerofall.progressivealchemy.blocks.CondenserTieredTileEntity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiProxy implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof CondenserTieredTileEntity) {
            return new CondenserTieredContainer(player.inventory, (CondenserTieredTileEntity) te);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof CondenserTieredTileEntity) {
        	CondenserTieredTileEntity containerTileEntity = (CondenserTieredTileEntity) te;
        	return new CondenserTieredGui(player.inventory, containerTileEntity);
        }
        return null;
    }
}