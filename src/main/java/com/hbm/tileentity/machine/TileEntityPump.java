package com.hbm.tileentity.machine;

import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTankNTM;
import com.hbm.tileentity.ModBlockEntities;
import com.hbm.tileentity.TileEntityMachineBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityPump extends TileEntityMachineBase {

    public final FluidTankNTM tank;
    public long power;
    public long maxPower = 100_000;

    public TileEntityPump(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PUMP.get(), pos, state, 2);
        this.tank = new FluidTankNTM(Fluids.WATER, 16_000);
    }

    @Override
    public String getDefaultName() {
        return "container.pump";
    }

    public static void tick(Level level, BlockPos pos, BlockState state, TileEntityPump pump) {
        if (level == null || level.isClientSide) return;

        if (pump.tank.getSpace() >= 50 && (pump.power >= 10 || pump.power == 0)) {
            if (pump.power >= 10) pump.power -= 10;
            pump.tank.setFill(pump.tank.getFill() + 50);
            pump.setChanged();
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putLong("power", power);
        tank.writeToNBT(tag, "tank");
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        power = tag.getLong("power");
        tank.readFromNBT(tag, "tank");
    }
}
