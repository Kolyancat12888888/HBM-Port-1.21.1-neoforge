package com.hbm.tileentity.machine.rbmk;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityRBMKAbsorber extends TileEntityRBMKBase implements IRBMKFluxReceiver {

	public TileEntityRBMKAbsorber(BlockPos pos, BlockState state) {
		super(com.hbm.tileentity.ModBlockEntities.RBMK_ABSORBER.get(), pos, state);
	}

	@Override
	public RBMKColumn.ColumnType getConsoleType() {
		return RBMKColumn.ColumnType.ABSORBER;
	}

	@Override
	public void receiveFlux(double fluxQuantity, double fastRatio) {
		// Fully absorbs neutron flux and converts it to heat
		this.heat += fluxQuantity * 0.001D;
	}

	@Override
	public void meltdown() {
		if (level != null && !level.isClientSide()) {
			level.explode(null, worldPosition.getX() + 0.5, worldPosition.getY() + 0.5, worldPosition.getZ() + 0.5, 6.0F, Level.ExplosionInteraction.BLOCK);
		}
	}
}
