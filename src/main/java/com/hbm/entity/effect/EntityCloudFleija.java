package com.hbm.entity.effect;

import com.hbm.config.CompatibilityConfig;
import com.hbm.entity.ModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class EntityCloudFleija extends Entity {

	public static final EntityDataAccessor<Integer> MAXAGE = SynchedEntityData.defineId(EntityCloudFleija.class, EntityDataSerializers.INT);

	public int age;
	public float scale = 0;

	public EntityCloudFleija(EntityType<?> type, Level worldIn) {
		super(type, worldIn);
		this.noPhysics = true;
	}

	public EntityCloudFleija(Level p_i1582_1_, int maxAge) {
		this(ModEntities.EXPLOSION_MK5.get(), p_i1582_1_);
		this.setMaxAge(maxAge);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		builder.define(MAXAGE, 100);
	}

	@Override
	public void tick() {
		super.tick();
		if (!CompatibilityConfig.isWarDim(this.level())) {
			this.discard();
			return;
		}
		this.age++;
		if (this.age >= this.getMaxAge()) {
			this.discard();
		}
		this.scale++;
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag compound) {
		this.age = compound.getInt("age");
		this.scale = compound.getFloat("scale");
		if (compound.contains("maxAge")) setMaxAge(compound.getInt("maxAge"));
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag compound) {
		compound.putInt("age", age);
		compound.putFloat("scale", scale);
		compound.putInt("maxAge", getMaxAge());
	}

	public void setMaxAge(int maxAge) {
		this.entityData.set(MAXAGE, maxAge);
	}

	public int getMaxAge() {
		return this.entityData.get(MAXAGE);
	}
}
