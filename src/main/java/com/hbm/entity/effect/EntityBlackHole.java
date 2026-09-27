package com.hbm.entity.effect;

import com.hbm.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class EntityBlackHole extends Entity {

	public float size = 1.5F;
	public int age = 0;

	public EntityBlackHole(EntityType<?> type, Level level) {
		super(type, level);
		this.noPhysics = true;
	}

	public EntityBlackHole(Level level, float size) {
		this(ModEntities.EXPLOSION_MK5.get(), level);
		this.size = size;
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
	}

	@Override
	public void tick() {
		super.tick();
		if (this.level().isClientSide) return;

		this.age++;
		if (this.age > 200) {
			this.discard();
			return;
		}

		BlockPos center = this.blockPosition();
		int r = (int) (size * 5);
		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

		for (int x = -r; x <= r; x++) {
			for (int y = -r; y <= r; y++) {
				for (int z = -r; z <= r; z++) {
					if (x * x + y * y + z * z <= r * r) {
						pos.set(center.getX() + x, center.getY() + y, center.getZ() + z);
						if (!this.level().getBlockState(pos).isAir() && this.level().getBlockState(pos).getBlock() != Blocks.BEDROCK) {
							this.level().setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
						}
					}
				}
			}
		}
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag tag) {
		size = tag.getFloat("size");
		age = tag.getInt("age");
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag tag) {
		tag.putFloat("size", size);
		tag.putInt("age", age);
	}
}
