package com.hbm.tileentity.machine;

import com.hbm.tileentity.TileEntityMachineBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityMachineSiren extends TileEntityMachineBase {

	public boolean active;
	public int sirenTrack;

	public TileEntityMachineSiren(BlockPos pos, BlockState state) {
		super(com.hbm.tileentity.ModBlockEntities.SIREN.get(), pos, state, 1);
	}

	@Override
	public String getDefaultName() {
		return "container.siren";
	}

	public static void tick(Level level, BlockPos pos, BlockState state, TileEntityMachineSiren te) {
		if (level == null || level.isClientSide) return;

		boolean powered = level.hasNeighborSignal(pos);
		if (powered != te.active) {
			te.active = powered;
			te.markChanged();
		}
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		tag.putBoolean("active", active);
		tag.putInt("track", sirenTrack);
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		active = tag.getBoolean("active");
		sirenTrack = tag.getInt("track");
	}
}
