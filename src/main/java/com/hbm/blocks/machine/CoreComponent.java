package com.hbm.blocks.machine;

import com.hbm.tileentity.ModBlockEntities;
import com.hbm.tileentity.machine.TileEntityCoreEmitter;
import com.hbm.tileentity.machine.TileEntityCoreInjector;
import com.hbm.tileentity.machine.TileEntityCoreReceiver;
import com.hbm.tileentity.machine.TileEntityCoreStabilizer;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class CoreComponent extends BaseEntityBlock {

    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public final ComponentType componentType;

    public enum ComponentType {
        EMITTER, RECEIVER, INJECTOR, STABILIZER
    }

    public static final MapCodec<CoreComponent> CODEC = simpleCodec(properties -> new CoreComponent(properties, ComponentType.EMITTER));

    public CoreComponent(BlockBehaviour.Properties properties, ComponentType componentType) {
        super(properties);
        this.componentType = componentType;
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    public CoreComponent(ComponentType componentType) {
        this(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(5.0F, 30.0F), componentType);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return switch (componentType) {
            case EMITTER -> new TileEntityCoreEmitter(pos, state);
            case RECEIVER -> new TileEntityCoreReceiver(pos, state);
            case INJECTOR -> new TileEntityCoreInjector(pos, state);
            case STABILIZER -> new TileEntityCoreStabilizer(pos, state);
        };
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide()) return null;
        return switch (componentType) {
            case EMITTER -> createTickerHelper(type, ModBlockEntities.DFC_EMITTER.get(), TileEntityCoreEmitter::tick);
            case RECEIVER -> createTickerHelper(type, ModBlockEntities.DFC_RECEIVER.get(), TileEntityCoreReceiver::tick);
            case INJECTOR -> createTickerHelper(type, ModBlockEntities.DFC_INJECTOR.get(), TileEntityCoreInjector::tick);
            case STABILIZER -> createTickerHelper(type, ModBlockEntities.DFC_STABILIZER.get(), TileEntityCoreStabilizer::tick);
        };
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
}
