package com.hbm.entity.logic;

import com.hbm.config.BombConfig;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.util.ContaminationUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

import java.util.UUID;

public class EntityNukeExplosionMK5 extends Entity {

	private int strength;
	private int radius;
	private boolean fallout = true;
	private int falloutAdd = 0;
	public UUID detonator;

	public EntityNukeExplosionMK5(EntityType<?> type, Level level) {
		super(type, level);
		this.noPhysics = true;
	}

	public EntityNukeExplosionMK5(Level level, int strength, int radius) {
		super(com.hbm.entity.ModEntities.EXPLOSION_MK5.get(), level);
		this.strength = strength;
		this.radius = radius;
		this.noPhysics = true;
	}

	public static EntityNukeExplosionMK5 statFac(Level world, int r, double x, double y, double z) {
		if (r == 0) r = 25;
		EntityNukeExplosionMK5 mk5 = new EntityNukeExplosionMK5(world, 2 * r, r);
		mk5.setPos(x, y, z);
		if (BombConfig.disableNuclear) mk5.fallout = false;
		return mk5;
	}

	public static EntityNukeExplosionMK5 statFacNoRad(Level world, int r, double x, double y, double z) {
		EntityNukeExplosionMK5 mk5 = statFac(world, r, x, y, z);
		mk5.fallout = false;
		return mk5;
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
	}

	@Override
	public void tick() {
		super.tick();
		if (this.level().isClientSide) return;

		BlockPos pos = this.blockPosition();

		// Devastation phase
		ExplosionNukeGeneric.dealDamage(this.level(), this.getX(), this.getY(), this.getZ(), this.radius * 2.0D);

		if (fallout) {
			ExplosionNukeGeneric.waste(this.level(), pos, (int) (this.radius * 2.5 + falloutAdd));
		}

		this.discard();
	}

	public EntityNukeExplosionMK5 setDetonator(Entity detonator) {
		if (detonator != null) {
			this.detonator = detonator.getUUID();
		}
		return this;
	}

	public EntityNukeExplosionMK5 moreFallout(int fallout) {
		this.falloutAdd = fallout;
		return this;
	}

	public EntityNukeExplosionMK5 forceSpawn() {
		return this;
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag tag) {
		radius = tag.getInt("radius");
		strength = tag.getInt("strength");
		falloutAdd = tag.getInt("falloutAdd");
		fallout = tag.getBoolean("fallout");
		if (tag.hasUUID("detonator"))
			detonator = tag.getUUID("detonator");
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag tag) {
		tag.putInt("radius", radius);
		tag.putInt("strength", strength);
		tag.putInt("falloutAdd", falloutAdd);
		tag.putBoolean("fallout", fallout);
		if (detonator != null)
			tag.putUUID("detonator", detonator);
	}
}
