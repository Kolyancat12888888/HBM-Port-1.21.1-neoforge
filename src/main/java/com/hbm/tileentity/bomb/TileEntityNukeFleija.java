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

public class TileEntityNukeFleija extends TileEntityMachineBase {

	public UUID placerID;

	public TileEntityNukeFleija(BlockPos pos, BlockState state) {
		super(ModBlockEntities.NUKE_FLEIJA.get(), pos, state, 11);
	}

	@Override
	public String getDefaultName() {
		return "container.nukeFleija";
	}

	public boolean isReady() {
		return inventory.getStackInSlot(0).getItem() == ModItems.FLEIJA_IGNITER.get() &&
				inventory.getStackInSlot(1).getItem() == ModItems.FLEIJA_IGNITER.get() &&
				inventory.getStackInSlot(2).getItem() == ModItems.FLEIJA_PROPELLANT.get() &&
				inventory.getStackInSlot(3).getItem() == ModItems.FLEIJA_PROPELLANT.get() &&
				inventory.getStackInSlot(4).getItem() == ModItems.FLEIJA_PROPELLANT.get() &&
				inventory.getStackInSlot(5).getItem() == ModItems.FLEIJA_CORE.get() &&
				inventory.getStackInSlot(6).getItem() == ModItems.FLEIJA_CORE.get() &&
				inventory.getStackInSlot(7).getItem() == ModItems.FLEIJA_CORE.get() &&
				inventory.getStackInSlot(8).getItem() == ModItems.FLEIJA_CORE.get() &&
				inventory.getStackInSlot(9).getItem() == ModItems.FLEIJA_CORE.get() &&
				inventory.getStackInSlot(10).getItem() == ModItems.FLEIJA_CORE.get();
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
