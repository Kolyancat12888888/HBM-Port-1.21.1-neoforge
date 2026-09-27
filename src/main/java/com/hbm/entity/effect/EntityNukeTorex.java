package com.hbm.entity.effect;

import com.hbm.entity.ModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class EntityNukeTorex extends Entity {

	public static final EntityDataAccessor<Float> SCALE = SynchedEntityData.defineId(EntityNukeTorex.class, EntityDataSerializers.FLOAT);
	public static final EntityDataAccessor<Byte> TYPE = SynchedEntityData.defineId(EntityNukeTorex.class, EntityDataSerializers.BYTE);

	public int maxAge = 1000;

	public EntityNukeTorex(EntityType<?> type, Level level) {
		super(type, level);
		this.noPhysics = true;
	}

	public EntityNukeTorex(Level level) {
		this(ModEntities.EXPLOSION_MK5.get(), level);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		builder.define(SCALE, 1.0F);
		builder.define(TYPE, (byte) 0);
	}

	@Override
	public void tick() {
		super.tick();
		if (!this.level().isClientSide && this.tickCount > maxAge) {
			this.discard();
		}
	}

	public EntityNukeTorex setScale(float scale) {
		this.entityData.set(SCALE, scale);
		this.maxAge = (int) (45 * 20 * scale);
		return this;
	}

	public EntityNukeTorex setType(int type) {
		this.entityData.set(TYPE, (byte) type);
		return this;
	}

	public double getScale() {
		return this.entityData.get(SCALE);
	}

	public byte getTorexType() {
		return this.entityData.get(TYPE);
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag tag) {
		if (tag.contains("scale")) setScale(tag.getFloat("scale"));
		if (tag.contains("type")) this.entityData.set(TYPE, tag.getByte("type"));
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag tag) {
		tag.putFloat("scale", this.entityData.get(SCALE));
		tag.putByte("type", this.entityData.get(TYPE));
	}

	public static void statFac(Level world, double x, double y, double z, float scale) {
		if (world.isClientSide) return;
		EntityNukeTorex torex = new EntityNukeTorex(world);
		torex.setPos(x, y, z);
		torex.setScale(Mth.clamp(scale * 0.01F, 0.25F, 5F));
		world.addFreshEntity(torex);
	}

	public static void statFacBale(Level world, double x, double y, double z, float scale) {
		if (world.isClientSide) return;
		EntityNukeTorex torex = new EntityNukeTorex(world);
		torex.setPos(x, y, z);
		torex.setScale(Mth.clamp(scale * 0.01F, 0.25F, 5F));
		torex.setType(1);
		world.addFreshEntity(torex);
	}
}
