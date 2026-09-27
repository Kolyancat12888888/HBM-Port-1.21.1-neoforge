package com.hbm.tileentity.bomb;

import com.hbm.tileentity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityLaunchPad extends TileEntityLaunchPadBase {

	public int delay = 0;

	public TileEntityLaunchPad(BlockPos pos, BlockState state) {
		super(ModBlockEntities.LAUNCH_PAD.get(), pos, state, 7);
	}

	@Override
	public boolean isReadyForLaunch() {
		return delay <= 0;
	}

	@Override
	public double getLaunchOffset() {
		return 1.0D;
	}

	public static void tick(Level level, BlockPos pos, BlockState state, TileEntityLaunchPad te) {
		if (level == null || level.isClientSide) return;

		if (te.delay > 0) te.delay--;

		if (!te.isMissileValid() || !te.hasFuel()) {
			te.delay = 100;
			te.state = STATE_MISSING;
		} else {
			if (te.delay > 0) {
				te.state = STATE_LOADING;
			} else {
				te.state = STATE_READY;
			}
		}

		te.updateBase();
	}

	@Override
	public void finalizeLaunch(net.minecraft.world.entity.Entity missile) {
		super.finalizeLaunch(missile);
		this.delay = 100;
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		tag.putInt("delay", delay);
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		delay = tag.getInt("delay");
	}
}
