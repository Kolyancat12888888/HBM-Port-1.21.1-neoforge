package com.hbm.explosion;

import com.hbm.entity.logic.EntityNukeExplosionMK5;
import com.hbm.util.ContaminationUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class ExplosionLarge {

	static Random rand = new Random();

	public static void explode(Level world, Entity detonator, double x, double y, double z, float strength, boolean cloud, boolean rubble, boolean shrapnel) {
		world.explode(detonator, x, y, z, strength, Level.ExplosionInteraction.BLOCK);
	}

	public static int cloudFunction(int i) {
		return (int) (545 * (1 - Math.pow(Math.E, -i / 15.0)) + 15);
	}

	public static int rubbleFunction(int i) {
		return i / 10;
	}

	public static int shrapnelFunction(int i) {
		return i / 3;
	}

	public static void explodeFire(Level world, Entity detonator, double x, double y, double z, float strength, boolean cloud, boolean rubble, boolean shrapnel) {
		world.addFreshEntity(EntityNukeExplosionMK5.statFacNoRad(world, (int) strength, x, y, z));
		ContaminationUtil.radiate(world, x, y, z, strength, 0, 0, strength * 20F, strength * 5F);
	}

	public static void buster(Level world, Entity detonator, double x, double y, double z, Vec3 vector, float strength, float depth) {
		vector = vector.normalize();
		for (int i = 0; i <= depth; i += 3) {
			ContaminationUtil.radiate(world, x + vector.x * i, y + vector.y * i, z + vector.z * i, strength, 0, 0, 0, strength * 10F);
			world.addFreshEntity(EntityNukeExplosionMK5.statFacNoRad(world, (int) strength, x + vector.x * i, y + vector.y * i, z + vector.z * i));
		}
	}
}
