package com.hbm.blocks.bomb;

import com.hbm.entity.effect.EntityCloudFleija;
import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.interfaces.IBomb;
import com.hbm.tileentity.ModBlockEntities;
import com.hbm.tileentity.bomb.TileEntityNukeFleija;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.Nullable;

public class BlockNukeFleija extends BaseEntityBlock implements IBomb {

	public static final MapCodec<BlockNukeFleija> CODEC = simpleCodec(BlockNukeFleija::new);
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

	public BlockNukeFleija(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	@Override
	protected MapCodec<? extends BaseEntityBlock> codec() {
		return CODEC;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new TileEntityNukeFleija(pos, state);
	}

	@Override
	protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
		if (!level.isClientSide && level.hasNeighborSignal(pos)) {
			BlockEntity be = level.getBlockEntity(pos);
			if (be instanceof TileEntityNukeFleija fleija && fleija.isReady()) {
				fleija.clearSlots();
				level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
				igniteBomb(level, null, pos.getX(), pos.getY(), pos.getZ());
			}
		}
		super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
	}

	public boolean igniteBomb(Level level, Entity detonator, int x, int y, int z) {
		if (!level.isClientSide) {
			level.playSound(null, x, y, z, SoundEvents.GENERIC_EXPLODE.value(), SoundSource.BLOCKS, 1.0F, 1.0F);

			EntityNukeExplosionMK3.statFacFleija(level, x + 0.5, y + 0.5, z + 0.5, 50);

			EntityCloudFleija cloud = new EntityCloudFleija(level, 50);
			cloud.setPos(x + 0.5, y + 0.5, z + 0.5);
			level.addFreshEntity(cloud);
		}
		return true;
	}

	@Override
	public BombReturnCode explode(Level level, BlockPos pos, Entity detonator) {
		if (!level.isClientSide) {
			BlockEntity be = level.getBlockEntity(pos);
			if (be instanceof TileEntityNukeFleija fleija) {
				if (fleija.isReady()) {
					fleija.clearSlots();
					level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
					igniteBomb(level, detonator, pos.getX(), pos.getY(), pos.getZ());
					return BombReturnCode.DETONATED;
				}
				return BombReturnCode.ERROR_MISSING_COMPONENT;
			}
		}
		return BombReturnCode.UNDEFINED;
	}
}
