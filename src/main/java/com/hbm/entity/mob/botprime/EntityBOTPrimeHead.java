package com.hbm.entity.mob.botprime;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class EntityBOTPrimeHead extends Monster {

	public EntityBOTPrimeHead(EntityType<? extends Monster> type, Level level) {
		super(type, level);
	}

	public EntityBOTPrimeHead(Level level) {
		super(EntityType.RAVAGER, level);
	}
}
