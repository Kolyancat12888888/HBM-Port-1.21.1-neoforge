package com.hbm.tileentity.bomb;

import com.hbm.items.ModItems;
import com.hbm.tileentity.ModBlockEntities;
import com.hbm.tileentity.TileEntityMachineBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import java.util.UUID;

public class TileEntityNukePrototype extends TileEntityMachineBase {

	public UUID placerID;

	public TileEntityNukePrototype(BlockPos pos, BlockState state) {
		super(ModBlockEntities.NUKE_PROTOTYPE.get(), pos, state, 14);
	}

	@Override
	public String getDefaultName() {
		return "container.nukePrototype";
	}

	public boolean isReady() {
		return !inventory.getStackInSlot(0).isEmpty() &&
				!inventory.getStackInSlot(1).isEmpty() &&
				!inventory.getStackInSlot(2).isEmpty() &&
				!inventory.getStackInSlot(3).isEmpty() &&
				!inventory.getStackInSlot(4).isEmpty() &&
				!inventory.getStackInSlot(5).isEmpty() &&
				!inventory.getStackInSlot(6).isEmpty() &&
				!inventory.getStackInSlot(7).isEmpty() &&
				!inventory.getStackInSlot(8).isEmpty() &&
				!inventory.getStackInSlot(9).isEmpty() &&
				!inventory.getStackInSlot(10).isEmpty() &&
				!inventory.getStackInSlot(11).isEmpty() &&
				!inventory.getStackInSlot(12).isEmpty() &&
				!inventory.getStackInSlot(13).isEmpty();
	}

	public void clearSlots() {
		for (int i = 0; i < inventory.getSlots(); i++) {
			inventory.setStackInSlot(i, ItemStack.EMPTY);
		}
		markChanged();
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		if (placerID != null) tag.putUUID("placer", placerID);
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		if (tag.hasUUID("placer")) placerID = tag.getUUID("placer");
	}
}
