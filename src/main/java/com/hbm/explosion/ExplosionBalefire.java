package com.hbm.explosion;

import com.hbm.blocks.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.UUID;

public class ExplosionBalefire {

	public int posX;
	public int posY;
	public int posZ;
	public int lastposX = 0;
	public int lastposZ = 0;
	public int radius;
	public int radius2;
	public Level worldObj;
	private int n = 1;
	private int nlimit;
	private int shell;
	private int leg;
	private int element;
	public UUID detonator = null;

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
		if (detonator != null)
			nbt.putUUID(name + "detonator", detonator);
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
		if (nbt.hasUUID(name + "detonator"))
			detonator = nbt.getUUID(name + "detonator");
	}

	public ExplosionBalefire(int x, int y, int z, Level world, int rad) {
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		this.worldObj = world;
		this.radius = rad;
		this.radius2 = this.radius * this.radius;
		this.nlimit = this.radius2 * 4;
	}

	public boolean update() {
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

	private void breakColumn(int x, int z) {
		int dist = (int) (radius - Math.sqrt(x * x + z * z));
		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
		if (dist > 0) {
			int pX = posX + x;
			int pZ = posZ + z;

			int y = worldObj.getHeight(net.minecraft.world.level.levelgen.Heightmap.Types.WORLD_SURFACE, pX, pZ);
			int maxdepth = (int) (10 + radius * 0.25);
			int depth = (int) ((maxdepth * dist / (double) radius) + (Math.sin(dist * 0.15 + 2) * 2));
			depth = Math.max(y - depth, worldObj.getMinBuildHeight());

			while (y > depth) {
				pos.set(pX, y, pZ);
				worldObj.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
				y--;
			}

			if (worldObj.random.nextInt(10) == 0) {
				pos.set(pX, depth + 1, pZ);
				worldObj.setBlock(pos, ModBlocks.balefire.defaultBlockState(), 3);
			}

			for (int i = depth; i > depth - 5; i--) {
				pos.set(pX, i, pZ);
				BlockState state = worldObj.getBlockState(pos);
				if (state.is(Blocks.STONE) || state.is(Blocks.DEEPSLATE)) {
					worldObj.setBlock(pos, ModBlocks.sellafield_slaked.defaultBlockState(), 3);
				}
			}
		}
	}
}
