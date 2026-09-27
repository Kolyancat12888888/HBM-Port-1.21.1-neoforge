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

public class TileEntityMachineIGenerator extends TileEntityMachineBase {

    public final FluidTankNTM fuelTank;
    public long power;
    public long maxPower = 2_000_000;
    public int genRate = 0;

    public TileEntityMachineIGenerator(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GENERATOR.get(), pos, state, 2);
        this.fuelTank = new FluidTankNTM(Fluids.DIESEL, 16_000);
    }

    @Override
    public String getDefaultName() {
        return "container.generator";
    }

    public static void tick(Level level, BlockPos pos, BlockState state, TileEntityMachineIGenerator gen) {
        if (level == null || level.isClientSide) return;

        int producedHE = 0;
        if (gen.fuelTank.getFill() >= 5 && gen.power < gen.maxPower) {
            var type = gen.fuelTank.getTankType();
            int powerPerUnit = 250;
            if (type == Fluids.KEROSENE) powerPerUnit = 400;
            else if (type == Fluids.BIOFUEL) powerPerUnit = 200;
            else if (type == Fluids.PETROIL) powerPerUnit = 300;

            gen.fuelTank.setFill(gen.fuelTank.getFill() - 5);
            producedHE = 5 * powerPerUnit;
        }

        gen.genRate = producedHE;
        gen.power = Math.min(gen.maxPower, gen.power + producedHE);

        if (gen.power > 0) {
            for (Direction dir : Direction.values()) {
                BlockPos targetPos = pos.relative(dir);
                if (!level.hasChunkAt(targetPos)) continue;

                BlockEntity te = level.getBlockEntity(targetPos);
                if (te instanceof TileEntityCableBaseNT cable) {
                    long space = cable.maxPower - cable.power;
                    long transfer = Math.min(gen.power, Math.min(cable.maxTransfer, space));
                    if (transfer > 0) {
                        gen.power -= transfer;
                        cable.power += transfer;
                        cable.setChanged();
                        gen.setChanged();
                    }
                } else if (te instanceof TileEntityMachineBattery battery) {
                    long space = battery.maxPower - battery.power;
                    long transfer = Math.min(gen.power, space);
                    if (transfer > 0) {
                        gen.power -= transfer;
                        battery.power += transfer;
                        battery.setChanged();
                        gen.setChanged();
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
        fuelTank.writeToNBT(tag, "fuel");
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        power = tag.getLong("power");
        if (tag.contains("maxPower")) maxPower = tag.getLong("maxPower");
        genRate = tag.getInt("genRate");
        fuelTank.readFromNBT(tag, "fuel");
    }
}
