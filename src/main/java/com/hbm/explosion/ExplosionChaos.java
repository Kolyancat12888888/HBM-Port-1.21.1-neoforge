package com.hbm.explosion;

import com.hbm.config.CompatibilityConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;
import java.util.function.Consumer;

public class ExplosionChaos {

	private static final Random rand = new Random();

	private static void forEachBlockInSphere(Level world, Entity detonator, int x, int y, int z, int radius, Consumer<BlockPos.MutableBlockPos> action) {
		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
		int radiusSqHalf = (radius * radius) / 2;

		for (int yy = -radius; yy < radius; yy++) {
			int currentY = y + yy;
			if (currentY < world.getMinBuildHeight() || currentY > world.getMaxBuildHeight()) continue;

			int YY = yy * yy;
			if (YY >= radiusSqHalf) continue;

			int xzRadius = (int) Math.sqrt(radiusSqHalf - YY);

			for (int xx = -xzRadius; xx <= xzRadius; xx++) {
				int XX = xx * xx;
				int YY_XX = YY + XX;
				if (YY_XX >= radiusSqHalf) continue;

				int zRadius = (int) Math.sqrt(radiusSqHalf - YY_XX);

				for (int zz = -zRadius; zz <= zRadius; zz++) {
					action.accept(pos.set(x + xx, currentY, z + zz));
				}
			}
		}
	}

	public static void explode(Level world, Entity detonator, int x, int y, int z, int bombStartStrength) {
		if (!CompatibilityConfig.isWarDim(world)) return;
		forEachBlockInSphere(world, detonator, x, y, z, bombStartStrength, pos -> destruction(world, detonator, pos));
	}

	private static void destruction(Level world, Entity detonator, BlockPos pos) {
		BlockState state = world.getBlockState(pos);
		Block b = state.getBlock();
		if (b == Blocks.BEDROCK || b.getExplosionResistance() > 2_000_000) {
			// Indestructible
		} else {
			world.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
		}
	}

	public static void cluster(Level world, int x, int y, int z, int count, double gravity) {
		if (world.isClientSide) return;
		for (int i = 0; i < count; i++) {
			double rx = x + (rand.nextDouble() - 0.5) * 20.0;
			double ry = y + (rand.nextDouble() - 0.5) * 10.0;
			double rz = z + (rand.nextDouble() - 0.5) * 20.0;
			world.explode(null, rx, ry, rz, 3.5F, Level.ExplosionInteraction.BLOCK);
		}
	}

	public static void flameDeath(Level world, Entity detonator, BlockPos pos, int bound) {
		if (!CompatibilityConfig.isWarDim(world)) return;
		BlockPos.MutableBlockPos mPosUp = new BlockPos.MutableBlockPos();

		forEachBlockInSphere(world, detonator, pos.getX(), pos.getY(), pos.getZ(), bound, mPos -> {
			mPosUp.set(mPos.getX(), mPos.getY() + 1, mPos.getZ());
			if (world.getBlockState(mPos).isFlammable(world, mPos, Direction.UP) && world.getBlockState(mPosUp).isAir()) {
				world.setBlock(mPosUp, Blocks.FIRE.defaultBlockState(), 3);
			}
		});
	}

	public static void burn(Level world, Entity detonator, BlockPos pos, int bound) {
		if (!CompatibilityConfig.isWarDim(world)) return;
		BlockPos.MutableBlockPos mPosUp = new BlockPos.MutableBlockPos();

		forEachBlockInSphere(world, detonator, pos.getX(), pos.getY(), pos.getZ(), bound, mPos -> {
			mPosUp.set(mPos.getX(), mPos.getY() + 1, mPos.getZ());
			BlockState upState = world.getBlockState(mPosUp);
			if (upState.isAir() && !world.getBlockState(mPos).isAir()) {
				world.setBlock(mPosUp, Blocks.FIRE.defaultBlockState(), 3);
			}
		});
	}

	public static void spawnChlorine(Level world, double x, double y, double z, int count, double speed, int type) {
		// Gas cloud effect / damage
		if (world.isClientSide) return;
		explode(world, null, (int) x, (int) y, (int) z, 10);
	}
}
