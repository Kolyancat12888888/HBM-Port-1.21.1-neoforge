package com.hbm.tileentity.machine.rbmk;

import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTankNTM;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityRBMKOutgasser extends TileEntityRBMKSlottedBase implements IRBMKFluxReceiver {

	public FluidTankNTM gasTank;

	public TileEntityRBMKOutgasser(BlockPos pos, BlockState state) {
		super(com.hbm.tileentity.ModBlockEntities.RBMK_OUTGASSER.get(), pos, state, 1);
		this.gasTank = new FluidTankNTM(Fluids.TRITIUM, 16_000, 0);
	}

	@Override
	public RBMKColumn.ColumnType getConsoleType() {
		return RBMKColumn.ColumnType.OUTGASSER;
	}

	@Override
	public void receiveFlux(double fluxQuantity, double fastRatio) {
		// Breeding tritium/helium from irradiation
		if (gasTank.getFill() < gasTank.getMaxFill()) {
			int produced = (int) Math.floor(fluxQuantity * 0.05D);
			gasTank.setFill(Math.min(gasTank.getMaxFill(), gasTank.getFill() + produced));
		}
	}

	@Override
	public void meltdown() {
		if (level != null && !level.isClientSide()) {
			level.explode(null, worldPosition.getX() + 0.5, worldPosition.getY() + 0.5, worldPosition.getZ() + 0.5, 6.0F, Level.ExplosionInteraction.BLOCK);
		}
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		if (gasTank != null) gasTank.writeToNBT(tag, "gasTank");
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		if (gasTank != null) gasTank.readFromNBT(tag, "gasTank");
	}
}
