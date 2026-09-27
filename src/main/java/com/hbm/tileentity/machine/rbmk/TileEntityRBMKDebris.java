package com.hbm.tileentity.machine.rbmk;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityRBMKDebris extends TileEntityRBMKBase {

	public TileEntityRBMKDebris(BlockPos pos, BlockState state) {
		super(com.hbm.tileentity.ModBlockEntities.RBMK_DEBRIS.get(), pos, state);
		this.heat = 1200.0D;
	}

	@Override
	public RBMKColumn.ColumnType getConsoleType() {
		return RBMKColumn.ColumnType.BLANK;
	}

	@Override
	public void updateRBMK() {
		if (level == null || level.isClientSide()) return;
		if (level.getGameTime() % 20 == 0) {
			com.hbm.util.ContaminationUtil.radiate(level, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), 20, 50.0F);
		}
	}

	@Override
	public void meltdown() {
	}
}
