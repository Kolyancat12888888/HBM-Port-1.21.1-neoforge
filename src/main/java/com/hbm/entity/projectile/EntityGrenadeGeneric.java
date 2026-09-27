package com.hbm.entity.projectile;

import com.hbm.explosion.ExplosionNukeGeneric;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public class EntityGrenadeGeneric extends ThrowableItemProjectile {

	public int fuse = 60; // 3 seconds
	public float explosionStrength = 4.0F;
	public boolean isNuclear = false;

	public EntityGrenadeGeneric(EntityType<? extends EntityGrenadeGeneric> type, Level level) {
		super(type, level);
	}

	public EntityGrenadeGeneric(EntityType<? extends EntityGrenadeGeneric> type, LivingEntity shooter, Level level, float explosionStrength, boolean isNuclear) {
		super(type, shooter, level);
		this.explosionStrength = explosionStrength;
		this.isNuclear = isNuclear;
	}

	@Override
	protected Item getDefaultItem() {
		return net.minecraft.world.item.Items.TNT;
	}

	@Override
	public void tick() {
		super.tick();

		if (!this.level().isClientSide) {
			this.fuse--;
			if (this.fuse <= 0) {
				this.explode();
			}
		}
	}

	@Override
	protected void onHit(HitResult result) {
		super.onHit(result);
		// Grenades bounce or explode
	}

	public void explode() {
		if (!this.level().isClientSide) {
			if (isNuclear) {
				ExplosionNukeGeneric.waste(this.level(), this.blockPosition(), (int) (explosionStrength * 10));
				this.level().explode(this, this.getX(), this.getY(), this.getZ(), explosionStrength * 2, Level.ExplosionInteraction.BLOCK);
			} else {
				this.level().explode(this, this.getX(), this.getY(), this.getZ(), explosionStrength, Level.ExplosionInteraction.BLOCK);
			}
			this.discard();
		}
	}
}
