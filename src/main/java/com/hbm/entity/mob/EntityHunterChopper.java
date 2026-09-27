package com.hbm.entity.mob;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;

public class EntityHunterChopper extends PathfinderMob {

	public EntityHunterChopper(EntityType<? extends PathfinderMob> type, Level level) {
		super(type, level);
	}

	public EntityHunterChopper(Level level) {
		super(EntityType.IRON_GOLEM, level);
	}
}
