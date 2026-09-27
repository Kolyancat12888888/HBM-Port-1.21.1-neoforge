package com.hbm.tileentity.machine.rbmk;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityRBMKControl extends TileEntityRBMKSlottedBase {

	public double lastLevel;
	public double levelVal;
	public static final double speed = 0.00277D; // ~18 seconds to fully insert/retract control rods (Chernobyl delay)
	public double targetLevel;

	public TileEntityRBMKControl(BlockPos pos, BlockState state) {
		super(com.hbm.tileentity.ModBlockEntities.RBMK_CONTROL.get(), pos, state, 0);
	}

	@Override
	public RBMKColumn.ColumnType getConsoleType() {
		return RBMKColumn.ColumnType.CONTROL;
	}

	@Override
	public void updateRBMK() {
		if (level == null || level.isClientSide()) return;

		this.lastLevel = this.levelVal;
		if (this.levelVal < this.targetLevel) {
			this.levelVal += speed;
			if (this.levelVal > this.targetLevel) this.levelVal = this.targetLevel;
		}
		if (this.levelVal > this.targetLevel) {
			this.levelVal -= speed;
			if (this.levelVal < this.targetLevel) this.levelVal = this.targetLevel;
		}
	}

	@Override
	public void meltdown() {
		if (level != null && !level.isClientSide()) {
			level.explode(null, worldPosition.getX() + 0.5, worldPosition.getY() + 0.5, worldPosition.getZ() + 0.5, 8.0F, Level.ExplosionInteraction.BLOCK);
		}
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		tag.putDouble("level", levelVal);
		tag.putDouble("target", targetLevel);
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		levelVal = tag.getDouble("level");
		targetLevel = tag.getDouble("target");
	}
}
