package com.hbm.tileentity.machine;

import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTankNTM;
import com.hbm.tileentity.ModBlockEntities;
import com.hbm.tileentity.TileEntityMachineBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityMachineGasFlare extends TileEntityMachineBase {

    public final FluidTankNTM tank;

    public TileEntityMachineGasFlare(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GAS_FLARE.get(), pos, state, 1);
        this.tank = new FluidTankNTM(Fluids.NONE, 8000);
    }

    @Override
    public String getDefaultName() {
        return "container.gas_flare";
    }

    public static void tick(Level level, BlockPos pos, BlockState state, TileEntityMachineGasFlare flare) {
        if (level == null) return;

        if (flare.tank.getFill() > 0) {
            int burned = Math.min(flare.tank.getFill(), 50);
            flare.tank.setFill(flare.tank.getFill() - burned);

            if (level.isClientSide) {
                level.addParticle(ParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 1.2, pos.getZ() + 0.5, 0, 0.1, 0);
                level.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, 0, 0.1, 0);
            }
        }
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
