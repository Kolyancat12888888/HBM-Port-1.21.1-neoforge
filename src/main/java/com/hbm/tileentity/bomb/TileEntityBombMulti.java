package com.hbm.tileentity.bomb;

import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.tileentity.TileEntityMachineBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityBombMulti extends TileEntityMachineBase {

	public enum BombType {
		BOY(25.0F, 80),
		MAN(35.0F, 120),
		MIKE(60.0F, 250),
		TSAR(120.0F, 500),
		GADGET(30.0F, 100);

		public final float explosionRadius;
		public final int wasteRadius;

		BombType(float explosionRadius, int wasteRadius) {
			this.explosionRadius = explosionRadius;
			this.wasteRadius = wasteRadius;
		}
	}

	public BombType bombType = BombType.BOY;
	public boolean isArmed = false;

	public TileEntityBombMulti(BlockPos pos, BlockState state) {
		super(com.hbm.tileentity.ModBlockEntities.BOMB_MULTI.get(), pos, state, 6);
	}

	public TileEntityBombMulti(BlockPos pos, BlockState state, BombType type) {
		this(pos, state);
		this.bombType = type;
	}

	@Override
	public String getDefaultName() {
		return "container.bomb_" + bombType.name().toLowerCase();
	}

	public static void tick(Level level, BlockPos pos, BlockState state, TileEntityBombMulti te) {
		if (level == null || level.isClientSide) return;

		if (level.hasNeighborSignal(pos)) {
			te.detonate();
		}
	}

	public void detonate() {
		if (level == null || level.isClientSide) return;

		BlockPos pos = this.getBlockPos();
		level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);

		// Nuke devastation & Fallout
		ExplosionNukeGeneric.waste(level, pos, bombType.wasteRadius);
		level.explode(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, bombType.explosionRadius, Level.ExplosionInteraction.BLOCK);
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		tag.putString("bombType", bombType.name());
		tag.putBoolean("isArmed", isArmed);
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		if (tag.contains("bombType")) {
			try {
				bombType = BombType.valueOf(tag.getString("bombType"));
			} catch (Exception ignored) {}
		}
		isArmed = tag.getBoolean("isArmed");
	}
}
