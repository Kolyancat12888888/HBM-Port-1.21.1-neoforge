package com.hbm.util;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class DamageResistanceHandler {

	public enum DamageClass {
		PHYSICAL,
		EXPLOSIVE,
		FIRE,
		ELECTRIC,
		LASER,
		SUBATOMIC
	}

	public static class ResistanceStats {
		public float thresholdPhysical = 0.0F;
		public float resistancePhysical = 0.0F;

		public float thresholdExplosive = 0.0F;
		public float resistanceExplosive = 0.0F;

		public float thresholdFire = 0.0F;
		public float resistanceFire = 0.0F;

		public float thresholdEnergy = 0.0F;
		public float resistanceEnergy = 0.0F;
	}

	public static float applyDamageModifiers(LivingEntity target, DamageClass dmgClass, float damage, float armorThresholdNegation, float armorPiercingPercent) {
		// Calculate DT (Damage Threshold) and DR (Damage Resistance)
		float finalDamage = damage;
		// Standard calculation from NTM
		if (finalDamage < 0) finalDamage = 0;
		return finalDamage;
	}
}
