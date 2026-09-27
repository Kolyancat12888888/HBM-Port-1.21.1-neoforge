package com.hbm.tileentity.machine.rbmk;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

public abstract class TileEntityRBMKSlottedBase extends TileEntityRBMKBase {

	public ItemStackHandler inventory;

	public TileEntityRBMKSlottedBase(BlockEntityType<?> type, BlockPos pos, BlockState state, int slots) {
		super(type, pos, state);
		this.inventory = new ItemStackHandler(slots) {
			@Override
			protected void onContentsChanged(int slot) {
				super.onContentsChanged(slot);
				markChanged();
			}
		};
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		if (inventory != null) {
			tag.put("inventory", inventory.serializeNBT(registries));
		}
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		if (tag.contains("inventory") && inventory != null) {
			inventory.deserializeNBT(registries, tag.getCompound("inventory"));
		}
	}
}
