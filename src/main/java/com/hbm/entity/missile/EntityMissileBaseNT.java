package com.hbm.entity.missile;

import com.hbm.api.entity.IRadarDetectableNT;
import com.hbm.api.entity.IThrowable;
import com.hbm.entity.logic.IChunkLoader;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.ExplosionNT;
import com.hbm.items.weapon.ItemMissileStandard;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public abstract class EntityMissileBaseNT extends Entity implements IChunkLoader, IRadarDetectableNT, IThrowable {

	public int startX;
	public int startZ;
	public int targetX;
	public int targetZ;
	public double velocity;
	public double decelY;
	public double accelXZ;
	public boolean isCluster = false;
	public int health = 50;
	protected LivingEntity thrower;

	public EntityMissileBaseNT(EntityType<? extends EntityMissileBaseNT> type, Level level) {
		super(type, level);
		this.noPhysics = false;
		this.startX = (int) this.getX();
		this.startZ = (int) this.getZ();
		this.targetX = (int) this.getX();
		this.targetZ = (int) this.getZ();
	}

	public EntityMissileBaseNT(EntityType<? extends EntityMissileBaseNT> type, Level level, float x, float y, float z, int targetX, int targetZ) {
		this(type, level);
		this.setPos(x, y, z);
		this.startX = (int) x;
		this.startZ = (int) z;
		this.targetX = targetX;
		this.targetZ = targetZ;
		this.setDeltaMovement(0, 2.0, 0);

		Vec3 vector = new Vec3(targetX - startX, 0, targetZ - startZ);
		double len = Math.max(vector.length(), 1.0);
		accelXZ = decelY = 1.0 / len;
		decelY *= 2;
		velocity = 0;

		this.setYRot((float) (Math.atan2(targetX - x, targetZ - z) * 180.0D / Math.PI));
	}

	public abstract ItemStack getMissileItemForInfo();

	@Override
	public boolean canBeSeenBy(Object radar) {
		return true;
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
	}

	@Override
	public void tick() {
		super.tick();

		if (this.health <= 0) {
			this.killMissile();
			return;
		}

		if (velocity < 4) {
			velocity += Mth.clamp(this.tickCount / 60D * 0.05D, 0, 0.05);
		}

		Vec3 motion = this.getDeltaMovement();

		if (!this.level().isClientSide) {
			if (hasPropulsion()) {
				double moY = motion.y - (decelY * velocity);

				Vec3 vector = new Vec3(targetX - startX, 0, targetZ - startZ).normalize();
				double vx = vector.x * accelXZ;
				double vz = vector.z * accelXZ;

				double moX = motion.x;
				double moZ = motion.z;

				if (moY > 0) {
					moX += vx * velocity;
					moZ += vz * velocity;
				}

				if (moY < 0) {
					moX -= vx * velocity;
					moZ -= vz * velocity;
				}
				motion = new Vec3(moX, moY, moZ);
			} else {
				double moX = motion.x * 0.99;
				double moZ = motion.z * 0.99;
				double moY = motion.y;
				if (moY > -1.5) moY -= 0.05;
				motion = new Vec3(moX, moY, moZ);
			}

			this.setDeltaMovement(motion);

			if (motion.y < -1.5 && this.isCluster) {
				cluster();
				this.discard();
				return;
			}

			this.setYRot((float) (Math.atan2(targetX - this.getX(), targetZ - this.getZ()) * 180.0D / Math.PI));
			float f2 = (float) Math.sqrt(motion.x * motion.x + motion.z * motion.z);
			this.setXRot((float) (Math.atan2(motion.y, f2) * 180.0D / Math.PI) - 90);

			// Move and check impact
			Vec3 from = this.position();
			Vec3 to = from.add(motion.scale(velocity > 0 ? velocity : 1.0));
			HitResult hit = this.level().clip(new net.minecraft.world.level.ClipContext(from, to, net.minecraft.world.level.ClipContext.Block.COLLIDER, net.minecraft.world.level.ClipContext.Fluid.NONE, this));

			if (hit.getType() == HitResult.Type.BLOCK) {
				this.setPos(hit.getLocation());
				this.onMissileImpact((BlockHitResult) hit);
				this.discard();
				return;
			}

			this.move(MoverType.SELF, motion.scale(velocity > 0 ? velocity : 1.0));

			if (this.onGround() || this.getY() <= this.level().getMinBuildHeight() + 5) {
				this.onMissileImpact(new BlockHitResult(this.position(), net.minecraft.core.Direction.UP, this.blockPosition(), false));
				this.discard();
			}
		} else {
			this.spawnContrail();
		}
	}

	public boolean hasPropulsion() {
		return true;
	}

	protected void spawnContrail() {
	}

	protected float getContrailScale() {
		return 1.0F;
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (this.isInvulnerableTo(source)) {
			return false;
		} else {
			if (this.health > 0 && !this.level().isClientSide) {
				health -= amount;
				if (this.health <= 0) {
					this.killMissile();
				}
			}
			return true;
		}
	}

	protected void killMissile() {
		if (!this.isRemoved()) {
			this.discard();
			ExplosionLarge.explode(this.level(), thrower, this.getX(), this.getY(), this.getZ(), 5, true, false, true);
			Vec3 delta = this.getDeltaMovement();
			ExplosionLarge.spawnShrapnelShower(this.level(), this.getX(), this.getY(), this.getZ(), delta.x, delta.y, delta.z, 15, 0.075);
			ExplosionLarge.spawnMissileDebris(this.level(), this.getX(), this.getY(), this.getZ(), delta.x, delta.y, delta.z, 0.25, getDebris(), getDebrisRareDrop());
		}
	}

	public abstract void onMissileImpact(BlockHitResult hit);

	public abstract List<ItemStack> getDebris();

	public abstract ItemStack getDebrisRareDrop();

	public void cluster() {
	}

	public void explodeStandard(float strength, int resolution, boolean fire) {
		if (this.level().isClientSide) return;
		this.level().explode(thrower, this.getX(), this.getY(), this.getZ(), strength, fire, Level.ExplosionInteraction.BLOCK);
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
		ItemStack item = this.getMissileItemForInfo();
		if (item != null && item.getItem() instanceof ItemMissileStandard missile) {
			return switch (missile.tier) {
				case TIER0 -> "radar.target.tier0";
				case TIER1 -> "radar.target.tier1";
				case TIER2 -> "radar.target.tier2";
				case TIER3 -> "radar.target.tier3";
				case TIER4 -> "radar.target.tier4";
			};
		}
		return "Unknown";
	}

	@Override
	public int getBlipLevel() {
		ItemStack item = this.getMissileItemForInfo();
		if (item != null && item.getItem() instanceof ItemMissileStandard missile) {
			return switch (missile.tier) {
				case TIER0 -> IRadarDetectableNT.TIER0;
				case TIER1 -> IRadarDetectableNT.TIER1;
				case TIER2 -> IRadarDetectableNT.TIER2;
				case TIER3 -> IRadarDetectableNT.TIER3;
				case TIER4 -> IRadarDetectableNT.TIER4;
			};
		}
		return IRadarDetectableNT.SPECIAL;
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag nbt) {
		decelY = nbt.getDouble("decel");
		accelXZ = nbt.getDouble("accel");
		targetX = nbt.getInt("tX");
		targetZ = nbt.getInt("tZ");
		startX = nbt.getInt("sX");
		startZ = nbt.getInt("sZ");
		velocity = nbt.getDouble("veloc");
		health = nbt.getInt("health");
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag nbt) {
		nbt.putDouble("decel", decelY);
		nbt.putDouble("accel", accelXZ);
		nbt.putInt("tX", targetX);
		nbt.putInt("tZ", targetZ);
		nbt.putInt("sX", startX);
		nbt.putInt("sZ", startZ);
		nbt.putDouble("veloc", velocity);
		nbt.putInt("health", health);
	}
}
