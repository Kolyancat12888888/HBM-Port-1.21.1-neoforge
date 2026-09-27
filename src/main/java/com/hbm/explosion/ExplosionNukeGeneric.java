package com.hbm.explosion;

import com.hbm.handler.radiation.ChunkRadiationManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class ExplosionNukeGeneric {

	private static final Random random = new Random();

	public static void waste(Level world, BlockPos center, int radius) {
		if (world == null || world.isClientSide) return;

		int r2 = radius * radius;
		int r22 = r2 / 2;
		BlockPos.MutableBlockPos mpos = new BlockPos.MutableBlockPos();

		for (int xx = -radius; xx < radius; xx++) {
			int X = xx + center.getX();
			int XX = xx * xx;
			for (int yy = -radius; yy < radius; yy++) {
				int Y = yy + center.getY();
				int YY = XX + yy * yy;
				for (int zz = -radius; zz < radius; zz++) {
					int Z = zz + center.getZ();
					int ZZ = YY + zz * zz;
					if (ZZ < r22) {
						mpos.set(X, Y, Z);
						BlockState state = world.getBlockState(mpos);
						if (!state.isAir()) {
							wasteDest(world, mpos, state);
						}
					}
				}
			}
		}

		// Apply radioactive contamination to the center chunk
		ChunkRadiationManager.proxy.incrementRad(world, center, radius * 100.0F);
	}

	public static void wasteDest(Level world, BlockPos pos, BlockState state) {
		if (state.is(Blocks.GRASS_BLOCK)) {
			world.setBlock(pos, Blocks.COARSE_DIRT.defaultBlockState(), 2);
		} else if (state.is(Blocks.SAND)) {
			if (random.nextInt(10) == 0) {
				world.setBlock(pos, Blocks.GLASS.defaultBlockState(), 2); // Trinitite precursor
			}
		} else if (state.is(Blocks.OAK_LEAVES) || state.is(Blocks.BIRCH_LEAVES) || state.is(Blocks.SPRUCE_LEAVES)) {
			world.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
		}
	}
}
