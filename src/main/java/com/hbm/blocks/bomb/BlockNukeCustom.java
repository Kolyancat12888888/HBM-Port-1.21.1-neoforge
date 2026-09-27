package com.hbm.blocks.bomb;

import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.logic.EntityNukeExplosionMK5;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.interfaces.IBomb;
import com.hbm.tileentity.ModBlockEntities;
import com.hbm.tileentity.bomb.TileEntityNukeCustom;
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
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.Nullable;

public class BlockNukeCustom extends BaseEntityBlock implements IBomb {

	public static final MapCodec<BlockNukeCustom> CODEC = simpleCodec(BlockNukeCustom::new);
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

	public BlockNukeCustom(Properties properties) {
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
		return new TileEntityNukeCustom(pos, state);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
		return createTickerHelper(blockEntityType, ModBlockEntities.NUKE_CUSTOM.get(), TileEntityNukeCustom::tick);
	}

	@Override
	protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
		if (!level.isClientSide && level.hasNeighborSignal(pos)) {
			BlockEntity be = level.getBlockEntity(pos);
			if (be instanceof TileEntityNukeCustom nuke) {
				igniteBomb(level, null, pos.getX(), pos.getY(), pos.getZ(), nuke);
			}
		}
		super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
	}

	public boolean igniteBomb(Level level, Entity detonator, int x, int y, int z, TileEntityNukeCustom nuke) {
		if (!level.isClientSide) {
			nuke.recalculateYield();
			float totalRadius = nuke.tnt + nuke.nuke + nuke.hydro + nuke.bale + nuke.sol + nuke.schrab;
			nuke.clearSlots();
			level.setBlock(new BlockPos(x, y, z), Blocks.AIR.defaultBlockState(), 3);

			level.playSound(null, x, y, z, SoundEvents.GENERIC_EXPLODE.value(), SoundSource.BLOCKS, 1.0F, 1.0F);

			if (totalRadius > 30.0F) {
				EntityNukeExplosionMK5 explosion = EntityNukeExplosionMK5.statFac(level, (int) totalRadius, x + 0.5, y + 0.5, z + 0.5);
				if (detonator != null) explosion.setDetonator(detonator);
				level.addFreshEntity(explosion);

				EntityNukeTorex.statFac(level, x, y, z, totalRadius);
				ExplosionNukeGeneric.waste(level, new BlockPos(x, y, z), (int) (totalRadius * 1.5));
			} else {
				ExplosionLarge.explode(level, detonator, x + 0.5, y + 0.5, z + 0.5, Math.max(10.0F, totalRadius), true, true, true);
			}
		}
		return true;
	}

	@Override
	public BombReturnCode explode(Level level, BlockPos pos, Entity detonator) {
		if (!level.isClientSide) {
			BlockEntity be = level.getBlockEntity(pos);
			if (be instanceof TileEntityNukeCustom nuke) {
				igniteBomb(level, detonator, pos.getX(), pos.getY(), pos.getZ(), nuke);
				return BombReturnCode.DETONATED;
			}
		}
		return BombReturnCode.UNDEFINED;
	}
}
