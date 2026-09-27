package com.hbm.tileentity.machine.rbmk;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityRBMKModerator extends TileEntityRBMKBase implements IRBMKFluxReceiver {

	public TileEntityRBMKModerator(BlockPos pos, BlockState state) {
		super(com.hbm.tileentity.ModBlockEntities.RBMK_MODERATOR.get(), pos, state);
	}

	@Override
	public RBMKColumn.ColumnType getConsoleType() {
		return RBMKColumn.ColumnType.MODERATOR;
	}

	@Override
	public void receiveFlux(double fluxQuantity, double fastRatio) {
		// Moderates fast flux into thermal slow flux (fastRatio -> 0)
		spreadModeratedFlux(fluxQuantity * 0.95D);
	}

	private void spreadModeratedFlux(double flux) {
		if (flux <= 0 || level == null) return;
		double perDir = flux / 4.0D;
		for (net.minecraft.core.Direction dir : net.minecraft.core.Direction.Plane.HORIZONTAL) {
			BlockPos target = worldPosition.relative(dir);
			if (level.getBlockEntity(target) instanceof IRBMKFluxReceiver receiver) {
				receiver.receiveFlux(perDir, 0.0D); // 100% slow thermal neutrons
			}
		}
	}

	@Override
	public void meltdown() {
		if (level != null && !level.isClientSide()) {
			level.explode(null, worldPosition.getX() + 0.5, worldPosition.getY() + 0.5, worldPosition.getZ() + 0.5, 6.0F, Level.ExplosionInteraction.BLOCK);
		}
	}
}
