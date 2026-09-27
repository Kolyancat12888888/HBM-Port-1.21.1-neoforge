package com.hbm.explosion;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.CompatibilityConfig;
import com.hbm.handler.ArmorUtil;
import com.hbm.lib.ModDamageSource;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.MutableVec3d;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ExplosionNukeGeneric {

	private static final Random random = new Random();
	public static Map<Block, Block> soliniumConfig = new HashMap<>();

	public static void empBlast(Level world, Entity detonator, int x, int y, int z, int bombStartStrength) {
		if (!CompatibilityConfig.isWarDim(world)) return;

		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
		int r = bombStartStrength;
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
					if (ZZ < r22) {
						pos.set(X, Y, Z);
						emp(world, pos);
					}
				}
			}
		}
	}

	public static void dealDamage(Level world, List<Entity> list, double x, double y, double z, double radius) {
		dealDamage(world, list, x, y, z, radius, 250F);
	}

	public static void dealDamage(Level world, double x, double y, double z, double radius) {
		dealDamage(world, x, y, z, radius, 250F);
	}

	public static void dealDamage(Level world, double x, double y, double z, double radius, float maxDamage) {
		if (world == null || world.isClientSide) return;
		List<Entity> list = world.getEntitiesOfClass(Entity.class, new AABB(x - radius, y - radius, z - radius, x + radius, y + radius, z + radius));
		dealDamage(world, list, x, y, z, radius, maxDamage);
	}

	public static void dealDamage(Level world, List<Entity> list, double x, double y, double z, double radius, float maxDamage) {
		MutableVec3d knock = new MutableVec3d();
		for (Entity e : list) {
			double dist = e.distanceToSqr(x, y, z);
			double rDist = Math.sqrt(dist);

			if (rDist <= radius) {
				if (!isExplosionExempt(e)) {
					boolean doKnockback = true;
					double damage = maxDamage * (radius - rDist) / radius;
					if (e instanceof LivingEntity living && e.isAlive()) {
						living.hurt(ModDamageSource.of(world, ModDamageSource.NUCLEAR_BLAST), (float) damage);
					} else {
						e.hurt(ModDamageSource.of(world, ModDamageSource.NUCLEAR_BLAST), (float) damage);
					}

					e.igniteForSeconds(5);

					if (doKnockback) {
						knock.set(e.getX() - x, e.getEyeY() - y, e.getZ() - z).normalizeSelf();
						e.setDeltaMovement(e.getDeltaMovement().add(knock.x * 0.2D, knock.y * 0.2D, knock.z * 0.2D));
					}
				}
			}
		}
	}

	private static boolean isExplosionExempt(Entity e) {
		return false;
	}

	public static void succ(Level world, int x, int y, int z, int radius) {
		if (world == null || world.isClientSide) return;

		List<Entity> list = world.getEntitiesOfClass(Entity.class, new AABB(x - radius, y - radius, z - radius, x + radius, y + radius, z + radius));

		for (Entity entity : list) {
			double dist = Math.sqrt(entity.distanceToSqr(x, y, z));

			if (dist <= radius && dist > 0) {
				double d5 = (entity.getX() - x) / dist;
				double d6 = (entity.getEyeY() - y) / dist;
				double d7 = (entity.getZ() - z) / dist;

				if (!(entity instanceof Player player && player.isCreative())) {
					double d8 = 0.125 + (random.nextDouble() * 0.25);
					entity.setDeltaMovement(entity.getDeltaMovement().add(-d5 * d8, -d6 * d8, -d7 * d8));
				}
			}
		}
	}

	public static int destruction(Level world, BlockPos pos) {
		if (!world.isClientSide) {
			BlockState b = world.getBlockState(pos);
			float res = b.getBlock().getExplosionResistance();
			if (res >= 200f) {
				int protection = (int) (res / 300f);
				if (b.is(ModBlocks.brick_concrete)) {
					if (random.nextInt(8) == 0) {
						world.setBlock(pos, Blocks.GRAVEL.defaultBlockState(), 3);
						return 0;
					}
				} else if (b.is(ModBlocks.brick_obsidian)) {
					if (random.nextInt(20) == 0) {
						world.setBlock(pos, Blocks.OBSIDIAN.defaultBlockState(), 3);
					}
				} else if (b.is(Blocks.OBSIDIAN)) {
					world.setBlock(pos, ModBlocks.gravel_obsidian.defaultBlockState(), 3);
					return 0;
				} else if (random.nextInt(protection + 3) == 0) {
					world.setBlock(pos, ModBlocks.block_scrap.defaultBlockState(), 3);
				}
				return protection;
			} else {
				world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
			}
		}
		return 0;
	}

	public static int vaporDest(Level world, BlockPos pos) {
		if (!world.isClientSide) {
			BlockState b = world.getBlockState(pos);
			float res = b.getBlock().getExplosionResistance();
			if (res < 0.5f || b.is(Blocks.COBWEB) || !b.getFluidState().isEmpty()) {
				world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
				return 0;
			} else if (res <= 3.0f && !b.isSolid()) {
				if (!b.is(Blocks.CHEST) && !b.is(Blocks.FARMLAND)) {
					world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
					return 0;
				}
			}

			if (b.isFlammable(world, pos, Direction.UP) && world.getBlockState(pos.above()).isAir()) {
				world.setBlock(pos.above(), Blocks.FIRE.defaultBlockState(), 2);
			}
			return (int) (res / 300f);
		}
		return 0;
	}

	public static void waste(Level world, BlockPos center, int radius) {
		if (world == null || world.isClientSide || !CompatibilityConfig.isWarDim(world)) return;

		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
		int r2 = radius * radius;
		int r22 = r2 / 2;
		int bound = Math.max(1, r22 / 5);

		for (int xx = -radius; xx < radius; xx++) {
			int X = xx + center.getX();
			int XX = xx * xx;
			for (int yy = -radius; yy < radius; yy++) {
				int Y = yy + center.getY();
				int YY = XX + yy * yy;
				for (int zz = -radius; zz < radius; zz++) {
					int Z = zz + center.getZ();
					int ZZ = YY + zz * zz;
					if (ZZ < r22 + world.random.nextInt(bound)) {
						pos.set(X, Y, Z);
						if (!world.getBlockState(pos).isAir()) {
							wasteDest(world, pos);
						}
					}
				}
			}
		}

		ContaminationUtil.radiate(world, center.getX(), center.getY(), center.getZ(), radius, 0, 0, radius * 20F, radius * 5F);
	}

	public static void wasteDest(Level world, BlockPos pos) {
		if (!world.isClientSide) {
			BlockState bs = world.getBlockState(pos);
			Block b = bs.getBlock();
			if (bs.isAir()) {
				return;
			}
			if (b == Blocks.ACACIA_DOOR || b == Blocks.BIRCH_DOOR || b == Blocks.DARK_OAK_DOOR || b == Blocks.JUNGLE_DOOR || b == Blocks.OAK_DOOR || b == Blocks.SPRUCE_DOOR || b == Blocks.IRON_DOOR) {
				world.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
			} else if (b == Blocks.GRASS_BLOCK || b == Blocks.DIRT) {
				world.setBlock(pos, ModBlocks.waste_earth.defaultBlockState(), 3);
			} else if (b == Blocks.MYCELIUM) {
				world.setBlock(pos, ModBlocks.waste_mycelium.defaultBlockState(), 3);
			} else if (b == Blocks.SAND) {
				if (random.nextInt(20) == 1) {
					world.setBlock(pos, ModBlocks.waste_trinitite.defaultBlockState(), 3);
				}
			} else if (b == Blocks.RED_SAND) {
				if (random.nextInt(20) == 1) {
					world.setBlock(pos, ModBlocks.waste_trinitite_red.defaultBlockState(), 3);
				}
			} else if (b == Blocks.CLAY) {
				world.setBlock(pos, Blocks.TERRACOTTA.defaultBlockState(), 3);
			} else if (b == Blocks.MOSSY_COBBLESTONE) {
				world.setBlock(pos, Blocks.COAL_ORE.defaultBlockState(), 3);
			} else if (b == Blocks.COAL_ORE) {
				int rand = random.nextInt(10);
				if (rand == 1 || rand == 2 || rand == 3) {
					world.setBlock(pos, Blocks.DIAMOND_ORE.defaultBlockState(), 3);
				} else if (rand == 9) {
					world.setBlock(pos, Blocks.EMERALD_ORE.defaultBlockState(), 3);
				}
			} else if (b instanceof RotatedPillarBlock && bs.is(net.minecraft.tags.BlockTags.LOGS)) {
				world.setBlock(pos, ModBlocks.waste_log.defaultBlockState().setValue(RotatedPillarBlock.AXIS, bs.getValue(RotatedPillarBlock.AXIS)), 3);
			} else if (b instanceof HugeMushroomBlock) {
				world.setBlock(pos, ModBlocks.waste_log.defaultBlockState(), 3);
			} else if (bs.is(net.minecraft.tags.BlockTags.PLANKS)) {
				world.setBlock(pos, ModBlocks.waste_planks.defaultBlockState(), 3);
			} else if (b == ModBlocks.ore_uranium) {
				int rand = random.nextInt(20);
				if (rand == 1) {
					world.setBlock(pos, ModBlocks.ore_schrabidium.defaultBlockState(), 3);
				} else {
					world.setBlock(pos, ModBlocks.ore_uranium_scorched.defaultBlockState(), 3);
				}
			} else if (b == ModBlocks.ore_nether_uranium) {
				int rand = random.nextInt(20);
				if (rand == 1) {
					world.setBlock(pos, ModBlocks.ore_nether_schrabidium.defaultBlockState(), 3);
				} else {
					world.setBlock(pos, ModBlocks.ore_nether_uranium_scorched.defaultBlockState(), 3);
				}
			}
		}
	}

	public static void wasteNoSchrab(Level world, BlockPos pos, int radius) {
		waste(world, pos, radius);
	}

	public static void wasteDestNoSchrab(Level world, BlockPos pos) {
		wasteDest(world, pos);
	}

	public static void emp(Level world, BlockPos pos) {
		if (!world.isClientSide && CompatibilityConfig.isWarDim(world)) {
			if (world.getBlockEntity(pos) != null) {
				if (random.nextInt(5) < 1) {
					world.setBlock(pos, ModBlocks.block_electrical_scrap.defaultBlockState(), 3);
				}
			}
		}
	}

	public static void solinium(Level world, BlockPos pos) {
		if (!world.isClientSide) {
			BlockState b = world.getBlockState(pos);

			if (soliniumConfig.containsKey(b.getBlock())) {
				world.setBlock(pos, soliniumConfig.get(b.getBlock()).defaultBlockState(), 3);
				return;
			}

			if (b.is(Blocks.GRASS_BLOCK) || b.is(Blocks.MYCELIUM) || b.is(ModBlocks.waste_earth) || b.is(ModBlocks.waste_mycelium)) {
				world.setBlock(pos, Blocks.DIRT.defaultBlockState(), 3);
				return;
			}
			if (b.is(ModBlocks.sellafield) || b.is(ModBlocks.sellafield_slaked)) {
				world.setBlock(pos, Blocks.STONE.defaultBlockState(), 3);
				return;
			}
			if (b.is(ModBlocks.waste_trinitite) || b.is(ModBlocks.waste_trinitite_red)) {
				world.setBlock(pos, Blocks.SAND.defaultBlockState(), 3);
				return;
			}
			if (b.is(ModBlocks.taint)) {
				world.setBlock(pos, ModBlocks.stone_gneiss.defaultBlockState(), 3);
				return;
			}
			if (b.is(net.minecraft.tags.BlockTags.LEAVES) || b.is(net.minecraft.tags.BlockTags.FLOWERS) || b.is(net.minecraft.tags.BlockTags.CROPS)) {
				world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
			}
		}
	}
}
