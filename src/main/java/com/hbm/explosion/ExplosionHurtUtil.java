package com.hbm.explosion;

import com.hbm.util.ContaminationUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class ExplosionHurtUtil {

	/**
	 * Adds radiation to entities in an AoE
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param outer The least amount of radiation received on the very edge of the AoE
	 * @param inner The greatest amount of radiation received on the very center of the AoE
	 * @param radius
	 */
	public static void doRadiation(Level world, double x, double y, double z, float outer, float inner, double radius) {
		if (world == null || world.isClientSide) return;

		List<LivingEntity> entities = world.getEntitiesOfClass(LivingEntity.class, new AABB(x - radius, y - radius, z - radius, x + radius, y + radius, z + radius));

		for (LivingEntity entity : entities) {
			Vec3 vec = new Vec3(x - entity.getX(), y - entity.getY(), z - entity.getZ());
			double dist = vec.length();

			if (dist > radius)
				continue;

			double interpolation = 1 - (dist / radius);
			float rad = (float) (outer + (inner - outer) * interpolation);

			ContaminationUtil.contaminate(entity, ContaminationUtil.HazardType.RADIATION, ContaminationUtil.ContaminationType.CREATIVE, rad);
		}
	}
}
