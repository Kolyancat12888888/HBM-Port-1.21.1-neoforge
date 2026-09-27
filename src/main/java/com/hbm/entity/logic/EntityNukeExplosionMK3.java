package com.hbm.entity.logic;

import com.hbm.config.BombConfig;
import com.hbm.entity.ModEntities;
import com.hbm.explosion.ExplosionNukeGeneric;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

import java.util.UUID;

public class EntityNukeExplosionMK3 extends Entity {

	public int destructionRange = 0;
	public float coefficient = 1.0F;
	public boolean waste = true;
	public UUID detonator = null;

	public EntityNukeExplosionMK3(EntityType<?> type, Level level) {
		super(type, level);
		this.noPhysics = true;
	}

	public EntityNukeExplosionMK3(Level level) {
		this(ModEntities.EXPLOSION_MK5.get(), level);
	}

	public static EntityNukeExplosionMK3 statFacFleija(Level world, double x, double y, double z, int range) {
		EntityNukeExplosionMK3 entity = new EntityNukeExplosionMK3(world);
		entity.setPos(x, y, z);
		entity.destructionRange = range;
		entity.waste = false;
		return entity;
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

		if (waste) {
			ExplosionNukeGeneric.waste(this.level(), pos, (int) (this.destructionRange * 1.8));
		} else {
			this.level().explode(this, this.getX(), this.getY(), this.getZ(), this.destructionRange, Level.ExplosionInteraction.BLOCK);
		}

		this.discard();
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag tag) {
		destructionRange = tag.getInt("destructionRange");
		waste = tag.getBoolean("waste");
		if (tag.hasUUID("detonator")) detonator = tag.getUUID("detonator");
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag tag) {
		tag.putInt("destructionRange", destructionRange);
		tag.putBoolean("waste", waste);
		if (detonator != null) tag.putUUID("detonator", detonator);
	}
}
