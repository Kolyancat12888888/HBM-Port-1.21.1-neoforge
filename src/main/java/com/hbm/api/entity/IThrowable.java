package com.hbm.api.entity;

import net.minecraft.world.entity.LivingEntity;

public interface IThrowable {
	LivingEntity getThrower();
	void setThrower(LivingEntity thrower);
}
