package com.hbm.entity.logic;

import com.hbm.entity.ModEntities;
import com.hbm.explosion.ExplosionNukeGeneric;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

import java.util.UUID;

public class EntityBalefire extends Entity {

	public int destructionRange = 50;
	public UUID detonator;

	public EntityBalefire(EntityType<?> type, Level level) {
		super(type, level);
		this.noPhysics = true;
	}

	public EntityBalefire(Level level) {
		this(ModEntities.EXPLOSION_MK5.get(), level);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
	}

	@Override
	public void tick() {
		super.tick();
		if (this.level().isClientSide) return;

		BlockPos pos = this.blockPosition();
		ExplosionNukeGeneric.dealDamage(this.level(), this.getX(), this.getY(), this.getZ(), this.destructionRange * 2);
		ExplosionNukeGeneric.waste(this.level(), pos, (int) (this.destructionRange * 1.5));
		this.discard();
	}

	public void setDetonator(Entity detonator) {
		if (detonator != null) {
			this.detonator = detonator.getUUID();
		}
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag tag) {
		destructionRange = tag.getInt("destructionRange");
		if (tag.hasUUID("detonator")) detonator = tag.getUUID("detonator");
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag tag) {
		tag.putInt("destructionRange", destructionRange);
		if (detonator != null) tag.putUUID("detonator", detonator);
	}
}
