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

public class TileEntityMachineStorageTank extends TileEntityMachineBase {

    public final FluidTankNTM tank;

    public TileEntityMachineStorageTank(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STORAGE_TANK.get(), pos, state, 4);
        this.tank = new FluidTankNTM(Fluids.NONE, 500_000);
    }

    @Override
    public String getDefaultName() {
        return "container.storage_tank";
    }

    public static void tick(Level level, BlockPos pos, BlockState state, TileEntityMachineStorageTank te) {
        if (level == null || level.isClientSide) return;
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
