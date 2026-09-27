package com.hbm.tileentity.machine;

import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTankNTM;
import com.hbm.tileentity.ModBlockEntities;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.tileentity.network.energy.TileEntityCableBaseNT;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityMachineTurbine extends TileEntityMachineBase {

    public final FluidTankNTM steamTank;
    public final FluidTankNTM waterTank;
    public long power;
    public long maxPower = 1_000_000;
    public int genRate = 0;

    public TileEntityMachineTurbine(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TURBINE.get(), pos, state, 2);
        this.steamTank = new FluidTankNTM(Fluids.STEAM, 32_000);
        this.waterTank = new FluidTankNTM(Fluids.WATER, 32_000);
    }

    @Override
    public String getDefaultName() {
        return "container.turbine";
    }

    public static void tick(Level level, BlockPos pos, BlockState state, TileEntityMachineTurbine turbine) {
        if (level == null || level.isClientSide) return;

        int consumedSteam = 0;
        int producedHE = 0;

        if (turbine.steamTank.getFill() > 0 && turbine.waterTank.getSpace() > 0) {
            int rate = Math.min(100, Math.min(turbine.steamTank.getFill(), turbine.waterTank.getSpace()));
            var type = turbine.steamTank.getTankType();

            int multiplier = 50;
            if (type == Fluids.HOTSTEAM) multiplier = 150;
            else if (type == Fluids.SUPERHOTSTEAM) multiplier = 400;
            else if (type == Fluids.ULTRAHOTSTEAM) multiplier = 1000;

            consumedSteam = rate;
            producedHE = rate * multiplier;

            turbine.steamTank.setFill(turbine.steamTank.getFill() - rate);
            turbine.waterTank.setFill(turbine.waterTank.getFill() + rate);
        }

        turbine.genRate = producedHE;
        turbine.power = Math.min(turbine.maxPower, turbine.power + producedHE);

        if (turbine.power > 0) {
            for (Direction dir : Direction.values()) {
                BlockPos targetPos = pos.relative(dir);
                if (!level.hasChunkAt(targetPos)) continue;

                BlockEntity te = level.getBlockEntity(targetPos);
                if (te instanceof TileEntityCableBaseNT cable) {
                    long space = cable.maxPower - cable.power;
                    long transfer = Math.min(turbine.power, Math.min(cable.maxTransfer, space));
                    if (transfer > 0) {
                        turbine.power -= transfer;
                        cable.power += transfer;
                        cable.setChanged();
                        turbine.setChanged();
                    }
                } else if (te instanceof TileEntityMachineBattery battery) {
                    long space = battery.maxPower - battery.power;
                    long transfer = Math.min(turbine.power, space);
                    if (transfer > 0) {
                        turbine.power -= transfer;
                        battery.power += transfer;
                        battery.setChanged();
                        turbine.setChanged();
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
        tag.putInt("genRate", genRate);
        steamTank.writeToNBT(tag, "steam");
        waterTank.writeToNBT(tag, "water");
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        power = tag.getLong("power");
        if (tag.contains("maxPower")) maxPower = tag.getLong("maxPower");
        genRate = tag.getInt("genRate");
        steamTank.readFromNBT(tag, "steam");
        waterTank.readFromNBT(tag, "water");
    }
}
