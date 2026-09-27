package com.hbm.tileentity.machine.rbmk;

import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTankNTM;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityRBMKBoiler extends TileEntityRBMKSlottedBase {

	public FluidTankNTM feed;
	public FluidTankNTM steam;

	public TileEntityRBMKBoiler(BlockPos pos, BlockState state) {
		super(com.hbm.tileentity.ModBlockEntities.RBMK_BOILER.get(), pos, state, 2);
		this.feed = new FluidTankNTM(Fluids.WATER, 16_000, 0);
		this.steam = new FluidTankNTM(Fluids.STEAM, 16_000, 1);
	}

	@Override
	public RBMKColumn.ColumnType getConsoleType() {
		return RBMKColumn.ColumnType.BOILER;
	}

	@Override
	public void updateRBMK() {
		if (level == null || level.isClientSide()) return;

		if (heat >= 100D && feed.getFill() > 0 && steam.getFill() < steam.getMaxFill()) {
			double heatConsumption = 0.05D;
			double availableHeat = (this.heat - 100D) / heatConsumption;
			int availableWater = feed.getFill();
			int availableSpace = steam.getMaxFill() - steam.getFill();

			int toBoil = (int) Math.floor(Math.min(availableHeat, Math.min(availableWater, availableSpace)));
			if (toBoil > 0) {
				feed.setFill(feed.getFill() - toBoil);
				steam.setFill(steam.getFill() + toBoil);
				this.heat -= toBoil * heatConsumption;
			}
		}
	}

	@Override
	public void meltdown() {
		if (level != null && !level.isClientSide()) {
			level.explode(null, worldPosition.getX() + 0.5, worldPosition.getY() + 0.5, worldPosition.getZ() + 0.5, 10.0F, Level.ExplosionInteraction.BLOCK);
		}
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		if (feed != null) feed.writeToNBT(tag, "feed");
		if (steam != null) steam.writeToNBT(tag, "steam");
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		if (feed != null) feed.readFromNBT(tag, "feed");
		if (steam != null) steam.readFromNBT(tag, "steam");
	}
}
