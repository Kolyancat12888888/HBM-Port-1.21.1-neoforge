package com.hbm.blocks.bomb;

import com.hbm.interfaces.IBomb;
import com.hbm.tileentity.ModBlockEntities;
import com.hbm.tileentity.bomb.TileEntityLaunchTable;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BlockLaunchTable extends BaseEntityBlock implements IBomb {

	public static final MapCodec<BlockLaunchTable> CODEC = simpleCodec(BlockLaunchTable::new);

	public BlockLaunchTable(Properties properties) {
		super(properties);
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
		return new TileEntityLaunchTable(pos, state);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
		return createTickerHelper(blockEntityType, ModBlockEntities.LAUNCH_TABLE.get(), TileEntityLaunchTable::tick);
	}

	@Override
	public BombReturnCode explode(Level level, BlockPos pos, Entity detonator) {
		if (!level.isClientSide) {
			BlockEntity be = level.getBlockEntity(pos);
			if (be instanceof TileEntityLaunchTable table) {
				if (table.canLaunch()) {
					table.launch();
					return BombReturnCode.LAUNCHED;
				}
				return BombReturnCode.ERROR_MISSING_COMPONENT;
			}
		}
		return BombReturnCode.UNDEFINED;
	}
}
