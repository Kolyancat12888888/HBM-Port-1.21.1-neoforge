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

public class TileEntityMachineFluidTank extends TileEntityMachineBase {

    public final FluidTankNTM tank;

    public TileEntityMachineFluidTank(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FLUID_TANK.get(), pos, state, 2);
        this.tank = new FluidTankNTM(Fluids.NONE, 64_000);
    }

    @Override
    public String getDefaultName() {
        return "container.fluid_tank";
    }

    public static void tick(Level level, BlockPos pos, BlockState state, TileEntityMachineFluidTank te) {
        if (level == null || level.isClientSide) return;
        // Container loading / unloading logic
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tank.writeToNBT(tag, "tank");
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        tank.readFromNBT(tag, "tank");
    }
}
