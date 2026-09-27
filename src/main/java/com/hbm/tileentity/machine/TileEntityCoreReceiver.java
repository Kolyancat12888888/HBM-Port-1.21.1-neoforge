package com.hbm.tileentity.machine;

import com.hbm.interfaces.ILaserable;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTankNTM;
import com.hbm.tileentity.ModBlockEntities;
import com.hbm.tileentity.TileEntityMachineBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityCoreReceiver extends TileEntityMachineBase implements ILaserable {

    public long power;
    public long joules;
    public long prevJoules;
    public FluidTankNTM tank;

    public TileEntityCoreReceiver(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DFC_RECEIVER.get(), pos, state, 0);
        tank = new FluidTankNTM(Fluids.CRYOGEL, 64_000);
    }

    @Override
    public String getDefaultName() {
        return "container.dfcReceiver";
    }

    public static void tick(Level level, BlockPos pos, BlockState state, TileEntityCoreReceiver receiver) {
        if (level.isClientSide()) return;

        receiver.power = receiver.joules * 5000;

        if (receiver.joules > 0) {
            if (receiver.tank.getFill() >= 20) {
                receiver.tank.setFill(receiver.tank.getFill() - 20);
            } else {
                level.setBlockAndUpdate(pos, Blocks.LAVA.defaultBlockState());
                return;
            }
        }

        receiver.prevJoules = receiver.joules;
        receiver.joules = 0;
        receiver.markChanged();
    }

    @Override
    public void addEnergy(Level level, BlockPos pos, long energy, Direction dir) {
        // Only accept lasers from the front face
        joules += energy;
    }

    @Override
    protected void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.saveAdditional(compound, registries);
        compound.putLong("power", power);
        compound.putLong("joules", joules);
        tank.writeToNBT(compound, "tank");
    }

    @Override
    protected void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.loadAdditional(compound, registries);
        power = compound.getLong("power");
        joules = compound.getLong("joules");
        tank.readFromNBT(compound, "tank");
    }
}
