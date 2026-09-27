package com.hbm.explosion;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.CompatibilityConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

public class ExplosionNT {

	public final Level world;
	public final Entity exploder;
	public final double x, y, z;
	public float size;
	public final Set<ExAttrib> atttributes = new HashSet<>();
	protected int resolution = 16;

	public ExplosionNT(Level world, Entity exploder, double x, double y, double z, float strength) {
		this.world = world;
		this.exploder = exploder;
		this.x = x;
		this.y = y;
		this.z = z;
		this.size = strength;
	}

	public ExplosionNT addAttrib(ExAttrib attrib) {
		atttributes.add(attrib);
		return this;
	}

	public ExplosionNT addAllAttrib(List<ExAttrib> attrib) {
		atttributes.addAll(attrib);
		return this;
	}

	public ExplosionNT overrideResolution(int res) {
		this.resolution = res;
		return this;
	}

	public boolean has(ExAttrib attrib) {
		return this.atttributes.contains(attrib);
	}

	public void explode() {
		if (CompatibilityConfig.isWarDim(this.world)) {
			this.world.explode(exploder, x, y, z, size, has(ExAttrib.FIRE), Level.ExplosionInteraction.BLOCK);
		}
	}

	public enum ExAttrib {
		FIRE,
		BALEFIRE,
		DIGAMMA,
		DIGAMMA_CIRCUIT,
		LAVA,
		LAVA_V,
		LAVA_R,
		ERRODE,
		ALLMOD,
		ALLDROP,
		NODROP,
		NOPARTICLE,
		NOSOUND,
		NOHURT
	}

	public static final Map<Block, Block> errosion = new HashMap<>();

	static {
		errosion.put(ModBlocks.concrete, Blocks.GRAVEL);
		errosion.put(ModBlocks.concrete_smooth, Blocks.GRAVEL);
		errosion.put(ModBlocks.brick_concrete, ModBlocks.brick_concrete_broken);
		errosion.put(ModBlocks.brick_concrete_broken, Blocks.GRAVEL);
	}
}
