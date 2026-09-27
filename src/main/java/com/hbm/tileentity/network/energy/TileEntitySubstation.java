package com.hbm.tileentity.network.energy;

import com.hbm.tileentity.ModBlockEntities;
import com.hbm.tileentity.TileEntityMachineBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntitySubstation extends TileEntityMachineBase {

    public long power;
    public long maxPower = 50_000_000;
    public long maxTransfer = 5_000_000;

    public TileEntitySubstation(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SUBSTATION.get(), pos, state, 4);
    }

    @Override
    public String getDefaultName() {
        return "container.substation";
    }

    public static void tick(Level level, BlockPos pos, BlockState state, TileEntitySubstation sub) {
        if (level == null || level.isClientSide) return;

        if (sub.power > 0) {
            for (Direction dir : Direction.values()) {
                BlockPos targetPos = pos.relative(dir);
                if (!level.hasChunkAt(targetPos)) continue;

                BlockEntity te = level.getBlockEntity(targetPos);
                if (te instanceof TileEntityCableBaseNT cable) {
                    long space = cable.maxPower - cable.power;
                    long transfer = Math.min(sub.power, Math.min(sub.maxTransfer, space));
                    if (transfer > 0) {
                        sub.power -= transfer;
                        cable.power += transfer;
                        cable.setChanged();
                        sub.setChanged();
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
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        power = tag.getLong("power");
        if (tag.contains("maxPower")) maxPower = tag.getLong("maxPower");
    }
}
