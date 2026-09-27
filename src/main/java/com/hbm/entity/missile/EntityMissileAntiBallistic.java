package com.hbm.entity.missile;

import com.hbm.api.entity.IRadarDetectableNT;
import com.hbm.api.entity.IThrowable;
import com.hbm.entity.ModEntities;
import com.hbm.entity.logic.IChunkLoader;
import com.hbm.explosion.ExplosionLarge;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class EntityMissileAntiBallistic extends Entity implements IChunkLoader, IRadarDetectableNT, IThrowable {

	public Entity tracking;
	public double velocity;
	private int activationTimer;
	private static final double baseSpeed = 1.5D;
	protected LivingEntity thrower;

	public EntityMissileAntiBallistic(EntityType<? extends EntityMissileAntiBallistic> type, Level world) {
		super(type, world);
		this.setDeltaMovement(0, baseSpeed, 0);
	}

	public EntityMissileAntiBallistic(Level world) {
		this(ModEntities.MISSILE_ANTI_BALLISTIC.get(), world);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
	}

	@Override
	public void tick() {
		super.tick();

		if (!this.level().isClientSide) {
			if (velocity < 6) velocity += 0.1;

			if (activationTimer < 10) {
				activationTimer++;
				this.setDeltaMovement(0, baseSpeed, 0);
			} else {
				if (this.tracking == null || this.tracking.isRemoved()) this.targetMissile();

				if (this.tracking != null) {
					double distSq = this.distanceToSqr(this.tracking);
					if (distSq < 225) { // within 15 blocks
						AABB box = new AABB(this.getX() - 15, this.getY() - 15, this.getZ() - 15, this.getX() + 15, this.getY() + 15, this.getZ() + 15);
						List<Entity> list = this.level().getEntities(this, box);
						for (Entity entity : list) {
							if (entity instanceof EntityMissileBaseNT target) {
								target.health -= 51;
							}
						}
						this.discard();
						ExplosionLarge.explode(this.level(), thrower, this.getX(), this.getY(), this.getZ(), 20F, true, false, false);
						return;
					}
					this.aimAtTarget();
				} else {
					if (this.tickCount > 600) this.discard();
				}
			}

			if (this.getY() > 2000 && (this.tracking == null || this.tracking.isRemoved())) this.discard();
		}

		Vec3 delta = this.getDeltaMovement();
		this.setPos(this.getX() + delta.x, this.getY() + delta.y, this.getZ() + delta.z);

		float f2 = (float) Math.sqrt(delta.x * delta.x + delta.z * delta.z);
		this.setYRot((float) (Math.atan2(delta.x, delta.z) * 180.0D / Math.PI));
		this.setXRot((float) (Math.atan2(delta.y, f2) * 180.0D / Math.PI) - 90);
	}

	private void targetMissile() {
		Entity closest = null;
		double dist = 1_000;
		int radarRange = 1000;
		AABB box = new AABB(this.getX() - radarRange, this.getY(), this.getZ() - radarRange, this.getX() + radarRange, this.getY() + radarRange, this.getZ() + radarRange);
		List<Entity> list = this.level().getEntities(this, box);

		for (Entity e : list) {
			if (!(e instanceof EntityMissileBaseNT)) continue;
			if (e instanceof EntityMissileTier4.EntityMissileStealth) continue;

			double d = this.distanceTo(e);
			if (d < dist) {
				dist = d;
				closest = e;
			}
		}
		this.tracking = closest;
	}

	private void aimAtTarget() {
		Vec3 delta = tracking.position().subtract(this.position());
		double intercept = delta.length() / (baseSpeed * this.velocity);
		Vec3 predicted = tracking.position().add(tracking.getDeltaMovement().scale(intercept));
		Vec3 motion = predicted.subtract(this.position()).normalize().scale(baseSpeed);

		this.setDeltaMovement(motion);
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
	public String getTranslationKey() {
		return "radar.target.abm";
	}

	@Override
	public int getBlipLevel() {
		return IRadarDetectableNT.TIER_AB;
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag nbt) {
		this.velocity = nbt.getDouble("veloc");
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag nbt) {
		nbt.putDouble("veloc", this.velocity);
	}
}
