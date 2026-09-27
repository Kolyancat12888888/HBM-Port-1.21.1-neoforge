package com.hbm.tileentity.machine.rbmk;

import com.hbm.items.machine.ItemRBMKRod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityRBMKRod extends TileEntityRBMKSlottedBase implements IRBMKFluxReceiver {

	public double fluxFastRatio;
	public double fluxQuantity;
	public double lastFluxQuantity;
	public double lastFluxRatio;

	public boolean hasRod;
	public int rodColor = 0;

	public TileEntityRBMKRod(BlockPos pos, BlockState state) {
		super(com.hbm.tileentity.ModBlockEntities.RBMK_ROD.get(), pos, state, 1);
	}

	@Override
	public RBMKColumn.ColumnType getConsoleType() {
		return RBMKColumn.ColumnType.FUEL;
	}

	@Override
	public void receiveFlux(double quantity, double fastRatio) {
		double fastFlux = this.fluxQuantity * this.fluxFastRatio;
		double fastFluxIn = quantity * fastRatio;

		this.fluxQuantity += quantity;
		if (this.fluxQuantity > 0) {
			this.fluxFastRatio = (fastFlux + fastFluxIn) / this.fluxQuantity;
		}
	}

	@Override
	public void updateRBMK() {
		if (level == null || level.isClientSide()) return;

		ItemStack stack = inventory.getStackInSlot(0).copy();
		if (stack.getItem() instanceof ItemRBMKRod rod) {
			this.rodColor = rod.colorTint;

			double fluxRatioOut = rod.rType == NType.SLOW ? 0.0D : 1.0D;
			double fluxIn = fluxFromType(rod.nType);
			double fluxQuantityOut = rod.burn(level, stack, fluxIn);

			rod.updateHeat(level, stack, 1.0D);
			this.heat += rod.provideHeat(level, stack, heat, 1.0D);
			inventory.setStackInSlot(0, stack);

			if (this.heat > this.maxHeat()) {
				this.meltdown();
				this.lastFluxRatio = 0;
				this.lastFluxQuantity = 0;
				this.fluxQuantity = 0;
				return;
			}

			if (this.heat > 10_000) this.heat = 10_000;
			this.lastFluxQuantity = this.fluxQuantity;
			this.lastFluxRatio = this.fluxFastRatio;

			this.fluxQuantity = 0;
			this.fluxFastRatio = 0;

			spreadFlux(fluxQuantityOut, fluxRatioOut);
			hasRod = true;
		} else {
			this.lastFluxRatio = 0;
			this.lastFluxQuantity = 0;
			this.fluxQuantity = 0;
			this.fluxFastRatio = 0;
			hasRod = false;
		}
	}

	private double fluxFromType(NType type) {
		double fastFlux = this.fluxQuantity * this.fluxFastRatio;
		double slowFlux = this.fluxQuantity * (1 - this.fluxFastRatio);
		return switch (type) {
			case SLOW -> slowFlux + fastFlux * 0.5;
			case FAST -> fastFlux + slowFlux * 0.3;
			case ANY -> this.fluxQuantity;
		};
	}

	protected void spreadFlux(double flux, double ratio) {
		if (flux <= 0 || level == null) return;

		double fluxPerDir = flux / 4.0D;
		for (Direction dir : Direction.Plane.HORIZONTAL) {
			for (int dist = 1; dist <= 4; dist++) {
				BlockPos checkPos = worldPosition.relative(dir, dist);
				if (level.getBlockEntity(checkPos) instanceof IRBMKFluxReceiver receiver) {
					receiver.receiveFlux(fluxPerDir / dist, ratio);
					break;
				}
			}
		}
	}

	@Override
	public void meltdown() {
		if (level != null && !level.isClientSide()) {
			level.explode(null, worldPosition.getX() + 0.5, worldPosition.getY() + 0.5, worldPosition.getZ() + 0.5, 12.0F, Level.ExplosionInteraction.BLOCK);
			com.hbm.util.ContaminationUtil.radiate(level, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), 50, 1000.0F);
		}
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		tag.putDouble("fluxQuantity", fluxQuantity);
		tag.putDouble("fluxMod", fluxFastRatio);
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		fluxQuantity = tag.getDouble("fluxQuantity");
		fluxFastRatio = tag.getDouble("fluxMod");
	}
}
