package com.hbm.tileentity.network.energy;

import com.hbm.tileentity.ModBlockEntities;
import com.hbm.tileentity.TileEntityLoadedBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityPylon extends TileEntityLoadedBase {

    public long power;
    public long maxPower = 5_000_000;
    public long maxTransfer = 500_000;
    public int range = 32;

    public BlockPos connectedPylon = null;

    public TileEntityPylon(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PYLON.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, TileEntityPylon pylon) {
        if (level == null || level.isClientSide) return;

        if (pylon.connectedPylon != null && level.hasChunkAt(pylon.connectedPylon)) {
            BlockEntity te = level.getBlockEntity(pylon.connectedPylon);
            if (te instanceof TileEntityPylon other) {
                if (pylon.power > other.power) {
                    long diff = (pylon.power - other.power) / 2;
                    long transfer = Math.min(diff, Math.min(pylon.maxTransfer, other.maxPower - other.power));
                    if (transfer > 0) {
                        pylon.power -= transfer;
                        other.power += transfer;
                        other.setChanged();
                        pylon.setChanged();
                    }
                }
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putLong("power", power);
        tag.putLong("maxPower", maxPower);
        if (connectedPylon != null) {
            tag.putLong("targetPylon", connectedPylon.asLong());
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        power = tag.getLong("power");
        if (tag.contains("maxPower")) maxPower = tag.getLong("maxPower");
        if (tag.contains("targetPylon")) connectedPylon = BlockPos.of(tag.getLong("targetPylon"));
    }
}
