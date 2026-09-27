package com.hbm.tileentity.machine;

import com.hbm.hazard.HazardSystem;
import com.hbm.items.ModItems;
import com.hbm.tileentity.ModBlockEntities;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.tileentity.network.energy.TileEntityCableBaseNT;
import com.hbm.util.ContaminationUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityMachineRTG extends TileEntityMachineBase {

    public long power;
    public long maxPower = 500_000;
    public int genRate = 0;

    public TileEntityMachineRTG(BlockPos pos, BlockState state) {
        super(ModBlockEntities.RTG.get(), pos, state, 15);
    }

    @Override
    public String getDefaultName() {
        return "container.rtg";
    }

    public static void tick(Level level, BlockPos pos, BlockState state, TileEntityMachineRTG rtg) {
        if (level == null || level.isClientSide) return;

        int currentGen = 0;
        float totalRad = 0;

        for (int i = 0; i < rtg.inventory.getSlots(); i++) {
            ItemStack stack = rtg.inventory.getStackInSlot(i);
            if (!stack.isEmpty()) {
                if (stack.is(ModItems.pellet_rtg)) {
                    currentGen += 500 * stack.getCount();
                    totalRad += 10.0F * stack.getCount();
                } else if (stack.is(ModItems.nugget_ra226)) {
                    currentGen += 100 * stack.getCount();
                    totalRad += 5.0F * stack.getCount();
                } else {
                    double radLevel = HazardSystem.getRawRadsFromStack(stack);
                    if (radLevel > 0) {
                        currentGen += (int) (radLevel * 20.0);
                        totalRad += (float) (radLevel * 0.5);
                    }
                }
            }
        }

        rtg.genRate = currentGen;
        rtg.power = Math.min(rtg.maxPower, rtg.power + rtg.genRate);

        if (totalRad > 0 && level.getGameTime() % 20 == 0) {
            ContaminationUtil.radiate(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 8.0, totalRad);
        }

        if (rtg.power > 0) {
            for (Direction dir : Direction.values()) {
                BlockPos targetPos = pos.relative(dir);
                if (!level.hasChunkAt(targetPos)) continue;

                BlockEntity te = level.getBlockEntity(targetPos);
                if (te instanceof TileEntityCableBaseNT cable) {
                    long space = cable.maxPower - cable.power;
                    long transfer = Math.min(rtg.power, Math.min(cable.maxTransfer, space));
                    if (transfer > 0) {
                        rtg.power -= transfer;
                        cable.power += transfer;
                        cable.setChanged();
                        rtg.setChanged();
                    }
                } else if (te instanceof TileEntityMachineBattery battery) {
                    long space = battery.maxPower - battery.power;
                    long transfer = Math.min(rtg.power, space);
                    if (transfer > 0) {
                        rtg.power -= transfer;
                        battery.power += transfer;
                        battery.setChanged();
                        rtg.setChanged();
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
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        power = tag.getLong("power");
        if (tag.contains("maxPower")) maxPower = tag.getLong("maxPower");
        genRate = tag.getInt("genRate");
    }
}
