package com.hbm.entity.projectile;

import com.hbm.items.weapon.sedna.BulletConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class EntityBulletBaseMK4 extends ThrowableProjectile {

	public BulletConfig config;
	public float damage;
	public int ricochets = 0;

	public EntityBulletBaseMK4(EntityType<? extends EntityBulletBaseMK4> type, Level level) {
		super(type, level);
	}

	public EntityBulletBaseMK4(EntityType<? extends EntityBulletBaseMK4> type, LivingEntity shooter, Level level, BulletConfig config, float baseDamage) {
		super(type, shooter, level);
		this.config = config;
		this.damage = baseDamage * (config != null ? config.damageMult : 1.0F);
	}

	@Override
	protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
	}

	@Override
	public void tick() {
		super.tick();

		if (config != null) {
			if (config.onUpdate != null) {
				config.onUpdate.accept(this);
			}

			if (this.tickCount >= config.expires) {
				this.discard();
				return;
			}

			// Custom gravity
			Vec3 motion = this.getDeltaMovement();
			this.setDeltaMovement(motion.x, motion.y - config.gravity, motion.z);
		}
	}

	@Override
	protected void onHit(HitResult result) {
		super.onHit(result);

		if (config != null) {
			if (config.onImpact != null) {
				config.onImpact.accept(this, result);
			}

			if (result.getType() == HitResult.Type.ENTITY) {
				if (config.onEntityHit != null) {
					config.onEntityHit.accept(this, result);
				}
			} else if (result.getType() == HitResult.Type.BLOCK) {
				if (config.onRicochet != null) {
					config.onRicochet.accept(this, result);
				} else {
					this.discard();
				}
			}
		} else {
			this.discard();
		}
	}
}
