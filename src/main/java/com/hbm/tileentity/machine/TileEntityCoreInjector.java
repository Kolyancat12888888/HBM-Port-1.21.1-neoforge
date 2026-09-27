package com.hbm.tileentity.machine;

import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTankNTM;
import com.hbm.tileentity.ModBlockEntities;
import com.hbm.tileentity.TileEntityMachineBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class TileEntityCoreInjector extends TileEntityMachineBase {

    public static final int range = 15;
    public FluidTankNTM[] tanks;
    public int beam;

    public TileEntityCoreInjector(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DFC_INJECTOR.get(), pos, state, 4);
        tanks = new FluidTankNTM[2];
        tanks[0] = new FluidTankNTM(Fluids.DEUTERIUM, 128_000, 0);
        tanks[1] = new FluidTankNTM(Fluids.TRITIUM, 128_000, 1);
    }

    @Override
    public String getDefaultName() {
        return "container.dfcInjector";
    }

    public static void tick(Level level, BlockPos pos, BlockState state, TileEntityCoreInjector injector) {
        if (level.isClientSide()) return;

        injector.beam = 0;
        Direction dir = state.getValue(BlockStateProperties.FACING);

        for (int i = 1; i <= range; i++) {
            BlockPos targetPos = pos.relative(dir, i);
            BlockEntity te = level.getBlockEntity(targetPos);

            if (te instanceof TileEntityCore core) {
                for (int t = 0; t < 2; t++) {
                    if (core.tanks[t].getTankType() == injector.tanks[t].getTankType()) {
                        int f = Math.min(injector.tanks[t].getFill(), core.tanks[t].getMaxFill() - core.tanks[t].getFill());
                        injector.tanks[t].setFill(injector.tanks[t].getFill() - f);
                        core.tanks[t].setFill(core.tanks[t].getFill() + f);
                        core.markChanged();
                    } else if (core.tanks[t].getFill() == 0) {
                        core.tanks[t].setTankType(injector.tanks[t].getTankType());
                        int f = Math.min(injector.tanks[t].getFill(), core.tanks[t].getMaxFill() - core.tanks[t].getFill());
                        injector.tanks[t].setFill(injector.tanks[t].getFill() - f);
                        core.tanks[t].setFill(core.tanks[t].getFill() + f);
                        core.markChanged();
                    }
                }
                injector.beam = i;
                break;
            }

            if (!level.getBlockState(targetPos).isAir()) {
                break;
            }
        }

        injector.markChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.saveAdditional(compound, registries);
        tanks[0].writeToNBT(compound, "fuel1");
        tanks[1].writeToNBT(compound, "fuel2");
    }

    @Override
    protected void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.loadAdditional(compound, registries);
        tanks[0].readFromNBT(compound, "fuel1");
        tanks[1].readFromNBT(compound, "fuel2");
    }
}
