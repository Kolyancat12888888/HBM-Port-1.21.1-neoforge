package com.hbm.tileentity.machine.rbmk;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityRBMKCooler extends TileEntityRBMKBase {

	public TileEntityRBMKCooler(BlockPos pos, BlockState state) {
		super(com.hbm.tileentity.ModBlockEntities.RBMK_COOLER.get(), pos, state);
	}

	@Override
	public RBMKColumn.ColumnType getConsoleType() {
		return RBMKColumn.ColumnType.COOLER;
	}

	@Override
	public void updateRBMK() {
		if (level == null || level.isClientSide()) return;
		// Active cooling dissipation
		if (this.heat > 20.0D) {
			this.heat = Math.max(20.0D, this.heat - 2.5D);
		}
	}

	@Override
	public void meltdown() {
		if (level != null && !level.isClientSide()) {
			level.explode(null, worldPosition.getX() + 0.5, worldPosition.getY() + 0.5, worldPosition.getZ() + 0.5, 6.0F, Level.ExplosionInteraction.BLOCK);
		}
	}
}
