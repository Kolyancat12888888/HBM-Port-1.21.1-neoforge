package com.hbm.blocks.bomb;

import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.logic.EntityNukeExplosionMK5;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.interfaces.IBomb;
import com.hbm.tileentity.ModBlockEntities;
import com.hbm.tileentity.bomb.TileEntityNukeGadget;
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

public class BlockNukeGadget extends BaseEntityBlock implements IBomb {

	public static final MapCodec<BlockNukeGadget> CODEC = simpleCodec(BlockNukeGadget::new);
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

	public BlockNukeGadget(Properties properties) {
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
		return new TileEntityNukeGadget(pos, state);
	}

	@Override
	protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
		if (!level.isClientSide && level.hasNeighborSignal(pos)) {
			BlockEntity be = level.getBlockEntity(pos);
			if (be instanceof TileEntityNukeGadget gadget && gadget.isReady()) {
				gadget.clearSlots();
				level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
				igniteBomb(level, null, pos.getX(), pos.getY(), pos.getZ());
			}
		}
		super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
	}

	public boolean igniteBomb(Level level, Entity detonator, int x, int y, int z) {
		if (!level.isClientSide) {
			level.playSound(null, x, y, z, SoundEvents.GENERIC_EXPLODE.value(), SoundSource.BLOCKS, 1.0F, 1.0F);

			float radius = 60.0F;
			EntityNukeExplosionMK5 explosion = EntityNukeExplosionMK5.statFac(level, (int) radius, x + 0.5, y + 0.5, z + 0.5);
			if (detonator != null) explosion.setDetonator(detonator);
			level.addFreshEntity(explosion);

			EntityNukeTorex.statFac(level, x, y, z, radius);
			ExplosionNukeGeneric.waste(level, new BlockPos(x, y, z), 100);
		}
		return true;
	}

	@Override
	public BombReturnCode explode(Level level, BlockPos pos, Entity detonator) {
		if (!level.isClientSide) {
			BlockEntity be = level.getBlockEntity(pos);
			if (be instanceof TileEntityNukeGadget gadget) {
				if (gadget.isReady()) {
					gadget.clearSlots();
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
