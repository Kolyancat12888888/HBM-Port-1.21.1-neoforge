package com.hbm.world;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class WorldUtil {
	public static void loadAndSpawnEntityInWorld(Entity entity) {
		if (entity != null && entity.level() != null && !entity.level().isClientSide) {
			entity.level().addFreshEntity(entity);
		}
	}
}
