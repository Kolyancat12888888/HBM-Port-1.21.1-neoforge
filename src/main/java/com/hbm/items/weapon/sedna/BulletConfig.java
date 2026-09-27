package com.hbm.items.weapon.sedna;

import com.hbm.entity.projectile.EntityBulletBaseMK4;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.lib.ModDamageSource;
import com.hbm.util.DamageResistanceHandler;
import com.hbm.util.DamageResistanceHandler.DamageClass;
import net.minecraft.core.Direction;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class BulletConfig implements Cloneable {

	public static final List<BulletConfig> configs = new ArrayList<>();

	public static final BiConsumer<EntityBulletBaseMK4, HitResult> LAMBDA_STANDARD_RICOCHET = (bullet, hit) -> {
		if (hit.getType() == HitResult.Type.BLOCK && hit instanceof BlockHitResult blockHit) {
			Direction side = blockHit.getDirection();
			Vec3 normal = Vec3.atLowerCornerOf(side.getNormal());
			Vec3 motion = bullet.getDeltaMovement();

			double dot = motion.normalize().dot(normal);
			double angle = Math.toDegrees(Math.acos(Math.abs(dot)));

			if (angle <= bullet.config.ricochetAngle && bullet.ricochets < bullet.config.maxRicochetCount) {
				bullet.ricochets++;
				Vec3 reflected = motion.subtract(normal.scale(2 * motion.dot(normal)));
				bullet.setDeltaMovement(reflected.scale(0.8));
			} else {
				bullet.discard();
			}
		}
	};

	public static final BiConsumer<EntityBulletBaseMK4, HitResult> LAMBDA_STANDARD_ENTITY_HIT = (bullet, hit) -> {
		if (hit.getType() == HitResult.Type.ENTITY && hit instanceof EntityHitResult entityHit) {
			Entity target = entityHit.getEntity();
			if (target == bullet.getOwner() && bullet.tickCount < bullet.config.selfDamageDelay) return;

			if (target instanceof LivingEntity living) {
				float dmg = bullet.damage;
				if (bullet.config.headshotMult > 1.0F && hit.getLocation().y > living.getEyeY() - 0.25) {
					dmg *= bullet.config.headshotMult;
				}
				DamageSource source = bullet.config.getDamage(bullet, bullet.getOwner(), bullet.config.dmgClass);
				target.hurt(source, dmg);
			} else {
				DamageSource source = bullet.config.getDamage(bullet, bullet.getOwner(), bullet.config.dmgClass);
				target.hurt(source, bullet.damage);
			}

			if (!bullet.config.doesPenetrate) {
				bullet.discard();
			}
		}
	};

	public int id;
	public ComparableStack ammo;
	public ItemStack casingItem = ItemStack.EMPTY;
	public int casingAmount;
	public int ammoReloadCount = 1;
	public float velocity = 3.0F;
	public float spread = 0.0F;
	public float wear = 1.0F;
	public int projectilesMin = 1;
	public int projectilesMax = 1;
	public float damageMult = 1.0F;
	public float armorThresholdNegation = 0.0F;
	public float armorPiercingPercent = 0.0F;
	public float knockbackMult = 0.1F;
	public float headshotMult = 1.5F;
	public DamageClass dmgClass = DamageClass.PHYSICAL;
	public float ricochetAngle = 15.0F;
	public int maxRicochetCount = 2;
	public float gravity = 0.02F;
	public int expires = 100;
	public boolean doesPenetrate = false;
	public int selfDamageDelay = 2;

	public Consumer<EntityBulletBaseMK4> onUpdate;
	public BiConsumer<EntityBulletBaseMK4, HitResult> onImpact;
	public BiConsumer<EntityBulletBaseMK4, HitResult> onRicochet = LAMBDA_STANDARD_RICOCHET;
	public BiConsumer<EntityBulletBaseMK4, HitResult> onEntityHit = LAMBDA_STANDARD_ENTITY_HIT;

	public BulletConfig() {
		this.id = configs.size();
		configs.add(this);
	}

	public DamageSource getDamage(Entity projectile, Entity shooter, DamageClass dmgClass) {
		if (projectile != null && projectile.level() != null) {
			if (dmgClass == DamageClass.EXPLOSIVE) {
				return projectile.level().damageSources().explosion(projectile, shooter);
			} else if (dmgClass == DamageClass.FIRE) {
				return projectile.level().damageSources().inFire();
			}
			return projectile.level().damageSources().thrown(projectile, shooter);
		}
		return null;
	}

	public BulletConfig setDamage(float mult) {
		this.damageMult = mult;
		return this;
	}

	public BulletConfig setVelocity(float vel) {
		this.velocity = vel;
		return this;
	}

	public BulletConfig setSpread(float spread) {
		this.spread = spread;
		return this;
	}

	public BulletConfig setProjectiles(int count) {
		this.projectilesMin = count;
		this.projectilesMax = count;
		return this;
	}

	public BulletConfig setArmorPiercing(float percent) {
		this.armorPiercingPercent = percent;
		return this;
	}

	public BulletConfig setGravity(float grav) {
		this.gravity = grav;
		return this;
	}
}
