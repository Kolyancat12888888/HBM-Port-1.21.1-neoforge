package com.hbm.explosion;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.CompatibilityConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Consumer;

public class ExplosionChaos {

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
}
