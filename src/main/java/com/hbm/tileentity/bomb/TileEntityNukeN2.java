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

public class TileEntityNukeN2 extends TileEntityMachineBase {

	public UUID placerID;

	public TileEntityNukeN2(BlockPos pos, BlockState state) {
		super(ModBlockEntities.NUKE_N2.get(), pos, state, 12);
	}

	@Override
	public String getDefaultName() {
		return "container.nukeN2";
	}

	public int countCharges() {
		int charges = 0;
		for (int i = 0; i < 12; i++) {
			if (inventory.getStackInSlot(i).getItem() == ModItems.N2_CHARGE.get()) {
				charges++;
			}
		}
		return charges;
	}

	public boolean isReady() {
		return countCharges() > 0;
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
