package com.hbm.entity.effect;

import com.hbm.entity.ModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class EntityEMPBlast extends Entity {

	public static final EntityDataAccessor<Integer> MAXAGE = SynchedEntityData.defineId(EntityEMPBlast.class, EntityDataSerializers.INT);

	public int age = 0;
	public float scale = 0;

	public EntityEMPBlast(EntityType<?> type, Level level) {
		super(type, level);
		this.noPhysics = true;
	}

	public EntityEMPBlast(Level level, int maxAge) {
		this(ModEntities.EXPLOSION_MK5.get(), level);
		this.setMaxAge(maxAge);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		builder.define(MAXAGE, 100);
	}

	@Override
	public void tick() {
		super.tick();
		this.age++;
		if (this.age >= this.getMaxAge()) {
			this.discard();
		}
		this.scale++;
	}

	public void setMaxAge(int i) {
		this.entityData.set(MAXAGE, i);
	}

	public int getMaxAge() {
		return this.entityData.get(MAXAGE);
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag compound) {
		this.scale = compound.getFloat("scale");
		this.age = compound.getInt("age");
		if (compound.contains("maxage")) this.setMaxAge(compound.getInt("maxage"));
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag compound) {
		compound.putFloat("scale", scale);
		compound.putInt("age", age);
		compound.putInt("maxage", getMaxAge());
	}
}
