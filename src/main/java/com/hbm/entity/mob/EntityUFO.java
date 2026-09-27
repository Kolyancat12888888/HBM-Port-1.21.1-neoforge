package com.hbm.entity.mob;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.level.Level;

public class EntityUFO extends FlyingMob {

	public int scanCooldown = 0;

	public EntityUFO(EntityType<? extends FlyingMob> type, Level level) {
		super(type, level);
	}

	public EntityUFO(Level level) {
		super(EntityType.GHAST, level);
	}
}
