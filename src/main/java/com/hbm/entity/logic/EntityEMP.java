package com.hbm.entity.logic;

import com.hbm.entity.ModEntities;
import com.hbm.explosion.ExplosionNukeGeneric;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class EntityEMP extends Entity {

	public EntityEMP(EntityType<?> type, Level level) {
		super(type, level);
		this.noPhysics = true;
	}

	public EntityEMP(Level level) {
		this(ModEntities.EXPLOSION_MK5.get(), level);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
	}

	@Override
	public void tick() {
		super.tick();
		if (this.level().isClientSide) return;
		ExplosionNukeGeneric.empBlast(this.level(), this, (int) this.getX(), (int) this.getY(), (int) this.getZ(), 100);
		this.discard();
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag tag) {
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag tag) {
	}
}
