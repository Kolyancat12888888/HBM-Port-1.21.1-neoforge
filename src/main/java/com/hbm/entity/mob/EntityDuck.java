package com.hbm.entity.mob;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.level.Level;

public class EntityDuck extends Chicken {

	public EntityDuck(EntityType<? extends Chicken> type, Level level) {
		super(type, level);
	}

	public EntityDuck(Level level) {
		super(EntityType.CHICKEN, level);
	}
}
