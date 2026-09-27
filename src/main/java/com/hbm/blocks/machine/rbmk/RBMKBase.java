package com.hbm.blocks.machine.rbmk;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public abstract class RBMKBase extends BaseEntityBlock {

	public static final MapCodec<RBMKBase> CODEC = simpleCodec(properties -> new RBMKBase(properties) {
		@Override
		public net.minecraft.world.level.block.entity.BlockEntity newBlockEntity(net.minecraft.core.BlockPos pos, BlockState state) {
			return null;
		}
	});

	public RBMKBase(BlockBehaviour.Properties properties) {
		super(properties);
	}

	public RBMKBase() {
		super(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(3.0F, 30.0F));
	}

	@Override
	protected MapCodec<? extends BaseEntityBlock> codec() {
		return CODEC;
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}
}
