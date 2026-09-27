package com.hbm.tileentity.bomb;

import com.hbm.entity.ModEntities;
import com.hbm.entity.missile.EntityMissileGeneric;
import com.hbm.tileentity.TileEntityMachineBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityLaunchPad extends TileEntityMachineBase {

	public double targetX;
	public double targetZ;
	public int cooldown = 0;

	public TileEntityLaunchPad(BlockPos pos, BlockState state) {
		super(com.hbm.tileentity.ModBlockEntities.LAUNCH_PAD.get(), pos, state, 3); // 0 missile slot, 1 designator slot, 2 battery
	}

	@Override
	public String getDefaultName() {
		return "container.launchPad";
	}

	public static void tick(Level level, BlockPos pos, BlockState state, TileEntityLaunchPad te) {
		if (level == null || level.isClientSide) return;

		if (te.cooldown > 0) te.cooldown--;

		if (level.hasNeighborSignal(pos) && te.cooldown <= 0) {
			te.launch();
		}
	}

	public void launch() {
		if (level == null || level.isClientSide) return;

		ItemStack missileStack = inventory.getStackInSlot(0);
		if (!missileStack.isEmpty()) {
			EntityMissileGeneric missile = new EntityMissileGeneric(
					ModEntities.MISSILE_GENERIC.get(),
					level,
					this.getBlockPos().getX() + 0.5,
					this.getBlockPos().getY() + 1.5,
					this.getBlockPos().getZ() + 0.5,
					targetX,
					targetZ,
					20.0F,
					true
			);
			level.addFreshEntity(missile);
			missileStack.shrink(1);
			this.cooldown = 100; // 5s cooldown
			this.markChanged();
		}
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		tag.putDouble("targetX", targetX);
		tag.putDouble("targetZ", targetZ);
		tag.putInt("cooldown", cooldown);
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		targetX = tag.getDouble("targetX");
		targetZ = tag.getDouble("targetZ");
		cooldown = tag.getInt("cooldown");
	}
}
