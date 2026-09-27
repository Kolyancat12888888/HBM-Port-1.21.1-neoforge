package com.hbm.entity.missile;

import com.hbm.api.entity.IThrowable;
import com.hbm.config.BombConfig;
import com.hbm.entity.ModEntities;
import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.logic.EntityNukeExplosionMK5;
import com.hbm.entity.logic.IChunkLoader;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.world.WorldUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EntityMIRV extends Entity implements IChunkLoader, IThrowable {

	public int health = 25;
	protected LivingEntity thrower;

	public EntityMIRV(EntityType<? extends EntityMIRV> type, Level level) {
		super(type, level);
		this.noPhysics = false;
	}

	public EntityMIRV(Level level) {
		this(ModEntities.MIRVLET.get(), level);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (!this.isRemoved() && !this.level().isClientSide) {
			health -= amount;
			if (this.health <= 0) {
				this.discard();
				this.killMissile();
			}
			return true;
		}
		return false;
	}

	private void killMissile() {
		ExplosionLarge.explode(this.level(), thrower, this.getX(), this.getY(), this.getZ(), 5, true, false, true);
		Vec3 delta = this.getDeltaMovement();
		ExplosionLarge.spawnShrapnelShower(this.level(), this.getX(), this.getY(), this.getZ(), delta.x, delta.y, delta.z, 15, 0.075);
	}

	@Override
	public void tick() {
		super.tick();

		Vec3 delta = this.getDeltaMovement();
		this.setPos(this.getX() + delta.x, this.getY() + delta.y, this.getZ() + delta.z);
		this.setDeltaMovement(delta.x, delta.y - 0.03, delta.z);

		if (!this.level().isClientSide) {
			BlockPos pos = this.blockPosition();
			if (!this.level().getBlockState(pos).isAir()) {
				WorldUtil.loadAndSpawnEntityInWorld(EntityNukeExplosionMK5.statFac(this.level(), BombConfig.mirvRadius, this.getX(), this.getY(), this.getZ()).setDetonator(thrower));
				if (BombConfig.enableNukeClouds) {
					EntityNukeTorex.statFac(this.level(), this.getX(), this.getY(), this.getZ(), BombConfig.mirvRadius);
				}
				this.discard();
			}
		}
	}

	@Override
	public LivingEntity getThrower() {
		return this.thrower;
	}

	@Override
	public void setThrower(LivingEntity thrower) {
		this.thrower = thrower;
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag tag) {
		health = tag.getInt("health");
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag tag) {
		tag.putInt("health", health);
	}
}
