package com.hbm.entity.missile;

import com.hbm.explosion.ExplosionNukeGeneric;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EntityMissileGeneric extends Entity {

	public double targetX;
	public double targetZ;
	public float warheadStrength = 15.0F;
	public boolean isNuclear = true;
	public int flightState = 0; // 0 = ascend, 1 = cruise, 2 = descend

	public EntityMissileGeneric(EntityType<? extends EntityMissileGeneric> type, Level level) {
		super(type, level);
		this.noPhysics = false;
	}

	public EntityMissileGeneric(EntityType<? extends EntityMissileGeneric> type, Level level, double x, double y, double z, double targetX, double targetZ, float strength, boolean nuclear) {
		this(type, level);
		this.setPos(x, y, z);
		this.targetX = targetX;
		this.targetZ = targetZ;
		this.warheadStrength = strength;
		this.isNuclear = nuclear;
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
	}

	@Override
	public void tick() {
		super.tick();

		if (!this.level().isClientSide) {
			if (flightState == 0) { // Ascend
				this.setDeltaMovement(0, 1.5, 0);
				if (this.getY() > 250) {
					flightState = 1;
				}
			} else if (flightState == 1) { // Cruise towards target
				double dx = targetX - this.getX();
				double dz = targetZ - this.getZ();
				double dist = Math.sqrt(dx * dx + dz * dz);
				if (dist < 20) {
					flightState = 2;
				} else {
					Vec3 dir = new Vec3(dx, 0, dz).normalize().scale(2.0);
					this.setDeltaMovement(dir.x, 0, dir.z);
				}
			} else if (flightState == 2) { // Descend onto target
				double dx = targetX - this.getX();
				double dz = targetZ - this.getZ();
				Vec3 dir = new Vec3(dx, -50, dz).normalize().scale(3.0);
				this.setDeltaMovement(dir);

				if (this.onGround() || this.getY() <= this.level().getMinBuildHeight() + 5) {
					this.detonate();
					return;
				}
			}

			this.move(net.minecraft.world.entity.MoverType.SELF, this.getDeltaMovement());

			if (this.tickCount > 1200) { // 60s max flight
				this.detonate();
			}
		}
	}

	public void detonate() {
		if (!this.level().isClientSide) {
			BlockPos pos = this.blockPosition();
			if (isNuclear) {
				ExplosionNukeGeneric.waste(this.level(), pos, (int) (warheadStrength * 5));
				this.level().explode(this, this.getX(), this.getY(), this.getZ(), warheadStrength * 2.5F, Level.ExplosionInteraction.BLOCK);
			} else {
				this.level().explode(this, this.getX(), this.getY(), this.getZ(), warheadStrength, Level.ExplosionInteraction.BLOCK);
			}
			this.discard();
		}
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag tag) {
		targetX = tag.getDouble("targetX");
		targetZ = tag.getDouble("targetZ");
		warheadStrength = tag.getFloat("warheadStrength");
		isNuclear = tag.getBoolean("isNuclear");
		flightState = tag.getInt("flightState");
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag tag) {
		tag.putDouble("targetX", targetX);
		tag.putDouble("targetZ", targetZ);
		tag.putFloat("warheadStrength", warheadStrength);
		tag.putBoolean("isNuclear", isNuclear);
		tag.putInt("flightState", flightState);
	}
}
