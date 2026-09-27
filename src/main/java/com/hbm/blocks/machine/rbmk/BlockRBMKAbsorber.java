package com.hbm.blocks.machine.rbmk;

import com.hbm.tileentity.ModBlockEntities;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKAbsorber;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKBase;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BlockRBMKAbsorber extends RBMKBase {

	public BlockRBMKAbsorber() {
		super();
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new TileEntityRBMKAbsorber(pos, state);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		return level.isClientSide() ? null : createTickerHelper(type, ModBlockEntities.RBMK_ABSORBER.get(), TileEntityRBMKBase::tick);
	}
}
