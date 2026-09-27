package com.hbm.explosion;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.CompatibilityConfig;
import com.hbm.handler.ArmorUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class ExplosionThermo {

	public static void freeze(Level world, Entity detonator, int x, int y, int z, int bombStartStrength) {
		if (!CompatibilityConfig.isWarDim(world)) return;

		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
		int r = bombStartStrength * 2;
		int r2 = r * r;
		int r22 = r2 / 2;
		for (int xx = -r; xx < r; xx++) {
			int X = xx + x;
			int XX = xx * xx;
			for (int yy = -r; yy < r; yy++) {
				int Y = yy + y;
				int YY = XX + yy * yy;
				for (int zz = -r; zz < r; zz++) {
					int Z = zz + z;
					int ZZ = YY + zz * zz;
					if (ZZ < r22 + world.random.nextInt(Math.max(1, r22 / 2))) {
						pos.set(X, Y, Z);
						freezeDest(world, pos);
					}
				}
			}
		}
	}

	public static void scorch(Level world, Entity detonator, int x, int y, int z, int bombStartStrength) {
		if (!CompatibilityConfig.isWarDim(world)) return;

		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
		int r = bombStartStrength * 2;
		int r2 = r * r;
		int r22 = r2 / 2;
		for (int xx = -r; xx < r; xx++) {
			int X = xx + x;
			int XX = xx * xx;
			for (int yy = -r; yy < r; yy++) {
				int Y = yy + y;
				int YY = XX + yy * yy;
				for (int zz = -r; zz < r; zz++) {
					int Z = zz + z;
					int ZZ = YY + zz * zz;
					if (ZZ < r22 + world.random.nextInt(Math.max(1, r22 / 2))) {
						pos.set(X, Y, Z);
						scorchDest(world, pos);
					}
				}
			}
		}
	}

	public static void scorchDest(Level world, BlockPos pos) {
		BlockState state = world.getBlockState(pos);
		Block block = state.getBlock();

		if (block == Blocks.GRASS_BLOCK || block == ModBlocks.frozen_grass) {
			world.setBlock(pos, Blocks.DIRT.defaultBlockState(), 3);
		} else if (block == Blocks.DIRT || block == ModBlocks.frozen_dirt) {
			world.setBlock(pos, Blocks.NETHERRACK.defaultBlockState(), 3);
		} else if (block == Blocks.NETHERRACK || block == Blocks.STONE || block == Blocks.COBBLESTONE || block == Blocks.STONE_BRICKS || block == Blocks.OBSIDIAN) {
			world.setBlock(pos, Blocks.LAVA.defaultBlockState(), 3);
		} else if (block == ModBlocks.frozen_log || state.is(net.minecraft.tags.BlockTags.LOGS)) {
			Direction.Axis axis = state.hasProperty(RotatedPillarBlock.AXIS) ? state.getValue(RotatedPillarBlock.AXIS) : Direction.Axis.Y;
			world.setBlock(pos, ModBlocks.waste_log.defaultBlockState().setValue(RotatedPillarBlock.AXIS, axis), 3);
		} else if (block == ModBlocks.frozen_planks || state.is(net.minecraft.tags.BlockTags.PLANKS)) {
			world.setBlock(pos, ModBlocks.waste_planks.defaultBlockState(), 3);
		} else if (state.is(net.minecraft.tags.BlockTags.LEAVES) || block == Blocks.WATER || block == Blocks.ICE || block == Blocks.SNOW || block == Blocks.SNOW_BLOCK) {
			world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
		} else if (block == Blocks.PACKED_ICE) {
			world.setBlock(pos, Blocks.WATER.defaultBlockState(), 3);
		}
	}

	public static void freezeDest(Level world, BlockPos pos) {
		BlockState state = world.getBlockState(pos);
		Block block = state.getBlock();

		if (block == Blocks.GRASS_BLOCK) {
			world.setBlock(pos, ModBlocks.frozen_grass.defaultBlockState(), 3);
		} else if (block == Blocks.DIRT) {
			world.setBlock(pos, ModBlocks.frozen_dirt.defaultBlockState(), 3);
		} else if (state.is(net.minecraft.tags.BlockTags.PLANKS) || block == ModBlocks.waste_planks) {
			world.setBlock(pos, ModBlocks.frozen_planks.defaultBlockState(), 3);
		} else if (block == ModBlocks.waste_log || state.is(net.minecraft.tags.BlockTags.LOGS)) {
			Direction.Axis axis = state.hasProperty(RotatedPillarBlock.AXIS) ? state.getValue(RotatedPillarBlock.AXIS) : Direction.Axis.Y;
			world.setBlock(pos, ModBlocks.frozen_log.defaultBlockState().setValue(RotatedPillarBlock.AXIS, axis), 3);
		} else if (block == Blocks.STONE || block == Blocks.COBBLESTONE || block == Blocks.STONE_BRICKS) {
			world.setBlock(pos, Blocks.PACKED_ICE.defaultBlockState(), 3);
		} else if (state.is(net.minecraft.tags.BlockTags.LEAVES)) {
			world.setBlock(pos, Blocks.SNOW_BLOCK.defaultBlockState(), 3);
		} else if (block == Blocks.LAVA) {
			world.setBlock(pos, Blocks.OBSIDIAN.defaultBlockState(), 3);
		} else if (block == Blocks.WATER) {
			world.setBlock(pos, Blocks.ICE.defaultBlockState(), 3);
		}
	}

	public static void setEntitiesOnFire(Level world, double x, double y, double z, int radius) {
		if (!CompatibilityConfig.isWarDim(world)) return;

		List<LivingEntity> list = world.getEntitiesOfClass(LivingEntity.class, new AABB(x - radius, y - radius, z - radius, x + radius, y + radius, z + radius));

		for (LivingEntity e : list) {
			if (Math.sqrt(e.distanceToSqr(x, y, z)) <= radius) {
				if (!(e instanceof Player player && ArmorUtil.checkForAsbestos(player))) {
					e.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 15 * 20, 4));
					e.igniteForSeconds(10);
				}
			}
		}
	}
}
