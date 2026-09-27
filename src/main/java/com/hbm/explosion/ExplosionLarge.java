package com.hbm.explosion;

import com.hbm.entity.logic.EntityNukeExplosionMK5;
import com.hbm.util.ContaminationUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;
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

	public static void spawnShrapnelShower(Level world, double x, double y, double z, double motionX, double motionY, double motionZ, int count, double deviation) {
		// Shrapnel effects / explosions
		explode(world, null, x, y, z, 3.0F, false, false, false);
	}

	public static void spawnShock(Level world, double x, double y, double z, int count, double strength) {
		// Shockwave visual & sonic jolt
	}

	public static void spawnParticles(Level world, double x, double y, double z, int count) {
		// Particle puff
	}

	public static void spawnRubble(Level world, double x, double y, double z, int count) {
		// Rubble debris
	}

	public static void spawnShrapnels(Level world, double x, double y, double z, int count) {
		// Shrapnel shards
	}

	public static void jolt(Level world, Entity detonator, double posX, double posY, double posZ, double strength, int count, double vel) {
		explode(world, detonator, posX, posY, posZ, (float) strength, false, false, false);
	}

	public static void spawnMissileDebris(Level world, double x, double y, double z, double motionX, double motionY, double motionZ, double deviation, List<ItemStack> debris, ItemStack rareDrop) {
		if (debris != null && !world.isClientSide) {
			for (ItemStack itemStack : debris) {
				if (itemStack != null && !itemStack.isEmpty()) {
					int k = rand.nextInt(itemStack.getCount() + 1);
					for (int j = 0; j < k; j++) {
						ItemStack copy = itemStack.copy();
						copy.setCount(1);
						ItemEntity item = new ItemEntity(world, x, y, z, copy);
						item.setDeltaMovement(
								(motionX + rand.nextGaussian() * deviation) * 0.85,
								(motionY + rand.nextGaussian() * deviation) * 0.85,
								(motionZ + rand.nextGaussian() * deviation) * 0.85
						);
						world.addFreshEntity(item);
					}
				}
			}
		}

		if (rareDrop != null && !rareDrop.isEmpty() && rand.nextInt(10) == 0 && !world.isClientSide) {
			ItemEntity item = new ItemEntity(world, x, y, z, rareDrop.copy());
			item.setDeltaMovement(
					motionX + rand.nextGaussian() * deviation * 0.1,
					motionY + rand.nextGaussian() * deviation * 0.1,
					motionZ + rand.nextGaussian() * deviation * 0.1
			);
			world.addFreshEntity(item);
		}
	}
}
