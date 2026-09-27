package com.hbm.tileentity.machine;

import com.hbm.tileentity.TileEntityMachineBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityMachineBattery extends TileEntityMachineBase {

	public long power;
	public long maxPower = 1_000_000;

	public TileEntityMachineBattery(BlockPos pos, BlockState state) {
		super(com.hbm.tileentity.ModBlockEntities.BATTERY.get(), pos, state, 2); // 0 charge slot, 1 discharge slot
	}

	@Override
	public String getDefaultName() {
		return "container.battery";
	}

	public static void tick(Level level, BlockPos pos, BlockState state, TileEntityMachineBattery te) {
		if (level == null || level.isClientSide) return;
		// Charging/discharging battery items
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		tag.putLong("power", power);
		tag.putLong("maxPower", maxPower);
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		power = tag.getLong("power");
		if (tag.contains("maxPower")) {
			maxPower = tag.getLong("maxPower");
		}
	}
}
