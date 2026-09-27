package com.hbm.tileentity.network.energy;

import com.hbm.tileentity.ModBlockEntities;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.tileentity.machine.TileEntityMachineBattery;
import com.hbm.tileentity.machine.TileEntityMachineCentrifuge;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityCableBaseNT extends TileEntityLoadedBase {

    public long power;
    public long maxPower = 100_000;
    public long maxTransfer = 25_000;

    public TileEntityCableBaseNT(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CABLE.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, TileEntityCableBaseNT cable) {
        if (level == null || level.isClientSide) return;

        if (cable.power > 0) {
            for (Direction dir : Direction.values()) {
                BlockPos targetPos = pos.relative(dir);
                if (!level.hasChunkAt(targetPos)) continue;

                BlockEntity te = level.getBlockEntity(targetPos);
                if (te instanceof TileEntityCableBaseNT otherCable) {
                    if (cable.power > otherCable.power) {
                        long diff = (cable.power - otherCable.power) / 2;
                        long transfer = Math.min(diff, Math.min(cable.maxTransfer, otherCable.maxPower - otherCable.power));
                        if (transfer > 0) {
                            cable.power -= transfer;
                            otherCable.power += transfer;
                            otherCable.setChanged();
                        }
                    }
                } else if (te instanceof TileEntityMachineBattery battery) {
                    long space = battery.maxPower - battery.power;
                    long transfer = Math.min(cable.power, Math.min(cable.maxTransfer, space));
                    if (transfer > 0) {
                        cable.power -= transfer;
                        battery.power += transfer;
                        battery.setChanged();
                    }
                } else if (te instanceof TileEntityMachineCentrifuge centrifuge) {
                    long space = TileEntityMachineCentrifuge.maxPower - centrifuge.power;
                    long transfer = Math.min(cable.power, Math.min(cable.maxTransfer, space));
                    if (transfer > 0) {
                        cable.power -= transfer;
                        centrifuge.power += transfer;
                        centrifuge.setChanged();
                    }
                }

                if (cable.power <= 0) break;
            }
            cable.setChanged();
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
