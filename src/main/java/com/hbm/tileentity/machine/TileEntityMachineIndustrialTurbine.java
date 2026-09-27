package com.hbm.tileentity.machine;

import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTankNTM;
import com.hbm.tileentity.ModBlockEntities;
import com.hbm.tileentity.network.energy.TileEntityCableBaseNT;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityMachineIndustrialTurbine extends TileEntityMachineTurbine {

    public TileEntityMachineIndustrialTurbine(BlockPos pos, BlockState state) {
        super(pos, state);
        this.maxPower = 20_000_000;
        this.steamTank.setMaxFill(256_000);
        this.waterTank.setMaxFill(256_000);
    }

    @Override
    public String getDefaultName() {
        return "container.industrial_turbine";
    }

    public static void tick(Level level, BlockPos pos, BlockState state, TileEntityMachineIndustrialTurbine turbine) {
        if (level == null || level.isClientSide) return;

        int consumedSteam = 0;
        int producedHE = 0;

        if (turbine.steamTank.getFill() > 0 && turbine.waterTank.getSpace() > 0) {
            int rate = Math.min(1000, Math.min(turbine.steamTank.getFill(), turbine.waterTank.getSpace()));
            var type = turbine.steamTank.getTankType();

            int multiplier = 60;
            if (type == Fluids.HOTSTEAM) multiplier = 180;
            else if (type == Fluids.SUPERHOTSTEAM) multiplier = 500;
            else if (type == Fluids.ULTRAHOTSTEAM) multiplier = 1500;

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
}
