package com.hbm.explosion;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.CompatibilityConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class ExplosionTom {
	public int posX;
	public int posY;
	public int posZ;
	public int lastposX = 0;
	public int lastposZ = 0;
	public int radius;
	public int radius2;
	public Level world;
	private int n = 1;
	private int nlimit;
	private int shell;
	private int leg;
	private int element;

	public void saveToNbt(CompoundTag nbt, String name) {
		nbt.putInt(name + "posX", posX);
		nbt.putInt(name + "posY", posY);
		nbt.putInt(name + "posZ", posZ);
		nbt.putInt(name + "lastposX", lastposX);
		nbt.putInt(name + "lastposZ", lastposZ);
		nbt.putInt(name + "radius", radius);
		nbt.putInt(name + "radius2", radius2);
		nbt.putInt(name + "n", n);
		nbt.putInt(name + "nlimit", nlimit);
		nbt.putInt(name + "shell", shell);
		nbt.putInt(name + "leg", leg);
		nbt.putInt(name + "element", element);
	}

	public void readFromNbt(CompoundTag nbt, String name) {
		posX = nbt.getInt(name + "posX");
		posY = nbt.getInt(name + "posY");
		posZ = nbt.getInt(name + "posZ");
		lastposX = nbt.getInt(name + "lastposX");
		lastposZ = nbt.getInt(name + "lastposZ");
		radius = nbt.getInt(name + "radius");
		radius2 = nbt.getInt(name + "radius2");
		n = nbt.getInt(name + "n");
		nlimit = nbt.getInt(name + "nlimit");
		shell = nbt.getInt(name + "shell");
		leg = nbt.getInt(name + "leg");
		element = nbt.getInt(name + "element");
	}

	public ExplosionTom(int x, int y, int z, Level world, int rad) {
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		this.world = world;
		this.radius = rad;
		this.radius2 = this.radius * this.radius;
		this.nlimit = this.radius2 * 4;
	}

	public boolean update() {
		if (!CompatibilityConfig.isWarDim(world)) {
			return true;
		}
		breakColumn(this.lastposX, this.lastposZ);
		this.shell = (int) Math.floor((Math.sqrt(n) + 1) / 2);
		if (shell == 0) shell = 1;
		int shell2 = this.shell * 2;
		this.leg = (int) Math.floor((this.n - (shell2 - 1) * (shell2 - 1)) / (double) shell2);
		this.element = (this.n - (shell2 - 1) * (shell2 - 1)) - shell2 * this.leg - this.shell + 1;
		this.lastposX = this.leg == 0 ? this.shell : this.leg == 1 ? -this.element : this.leg == 2 ? -this.shell : this.element;
		this.lastposZ = this.leg == 0 ? this.element : this.leg == 1 ? this.shell : this.leg == 2 ? -this.element : -this.shell;
		this.n++;
		return this.n > this.nlimit;
	}

	private void breakColumn(final int x, final int z) {
		final int r2 = x * x + z * z;
		final int dist = this.radius2 - r2;
		if (dist <= 0) return;
		final int pX = posX + x;
		final int pZ = posZ + z;
		final double r = Math.sqrt(r2);
		final boolean insideRim = r < 500.0;

		int y = 256;
		final int terrain = 63;

		final double cA = (terrain - Math.exp(-(r2) / 40000.0) * 13.0) + world.random.nextInt(2);
		final double rMinus200 = r - 200.0;
		final double cB = cA + Math.exp(-(rMinus200 * rMinus200) / 400.0) * 13.0;
		final double rMinus500 = r - 500.0;
		final int craterFloor = (int) (cB + Math.exp(-(rMinus500 * rMinus500) / 2000.0) * 37.0);

		final BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

		for (int i = 256; i > world.getMinBuildHeight(); i--) {
			pos.set(pX, i, pZ);
			if (i == craterFloor || !world.isEmptyBlock(pos)) {
				y = i;
				break;
			}
		}

		final int height = terrain - 14;
		final int offset = 20;
		final int threshold = (int) (r * (height + offset) / (double) this.radius) + world.random.nextInt(2) - offset;

		while (y > threshold) {
			if (y <= world.getMinBuildHeight()) break;

			if (y <= craterFloor) {
				pos.set(pX, y, pZ);
				world.setBlock(pos, ModBlocks.tektite.defaultBlockState(), 2);
			} else {
				if (y > terrain + 1) {
					if (insideRim) {
						pos.set(pX, y, pZ);
						world.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
					}
				} else {
					pos.set(pX, y, pZ);
					world.setBlock(pos, Blocks.LAVA.defaultBlockState(), 2);
				}
			}
			y--;
		}
	}
}
