package com.hbm.blocks.bomb;

import com.hbm.tileentity.bomb.TileEntityBombMulti;
import com.hbm.tileentity.bomb.TileEntityBombMulti.BombType;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class BlockBombMulti extends BaseEntityBlock {

	public static final MapCodec<BlockBombMulti> CODEC = simpleCodec(BlockBombMulti::new);
	public final BombType defaultType;

	public BlockBombMulti(Properties properties, BombType type) {
		super(properties);
		this.defaultType = type;
	}

	public BlockBombMulti(Properties properties) {
		this(properties, BombType.BOY);
	}

	@Override
	protected MapCodec<? extends BaseEntityBlock> codec() {
		return CODEC;
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new TileEntityBombMulti(pos, state, defaultType);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
		return createTickerHelper(blockEntityType, com.hbm.tileentity.ModBlockEntities.BOMB_MULTI.get(), TileEntityBombMulti::tick);
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
		if (level.isClientSide) return InteractionResult.SUCCESS;
		return InteractionResult.CONSUME;
	}
}
