package com.hbm.tileentity.machine;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTankNTM;
import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemCanister;
import com.hbm.items.tool.ItemGasCanister;
import com.hbm.tileentity.ModBlockEntities;
import com.hbm.tileentity.TileEntityMachineBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityMachineStorageTank extends TileEntityMachineBase {

    public final FluidTankNTM tank;

    public TileEntityMachineStorageTank(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STORAGE_TANK.get(), pos, state, 4); // 0, 1 for unload/load, 2, 3 for aux
        this.tank = new FluidTankNTM(Fluids.NONE, 500_000);
    }

    @Override
    public String getDefaultName() {
        return "container.storage_tank";
    }

    public static void tick(Level level, BlockPos pos, BlockState state, TileEntityMachineStorageTank te) {
        if (level == null || level.isClientSide) return;

        ItemStack in = te.inventory.getStackInSlot(0);
        ItemStack out = te.inventory.getStackInSlot(1);

        if (!in.isEmpty()) {
            // Water bucket emptying
            if (in.getItem() == Items.WATER_BUCKET && (te.tank.getTankType() == Fluids.NONE || te.tank.getTankType() == Fluids.WATER) && te.tank.getSpace() >= 1000) {
                if (out.isEmpty() || (out.getItem() == Items.BUCKET && out.getCount() < out.getMaxStackSize())) {
                    te.tank.setTankType(Fluids.WATER);
                    te.tank.setFill(te.tank.getFill() + 1000);
                    in.shrink(1);
                    if (out.isEmpty()) {
                        te.inventory.setStackInSlot(1, new ItemStack(Items.BUCKET));
                    } else {
                        out.grow(1);
                    }
                    te.markChanged();
                    return;
                }
            }

            // Water bucket filling
            if (in.getItem() == Items.BUCKET && te.tank.getTankType() == Fluids.WATER && te.tank.getFill() >= 1000) {
                if (out.isEmpty() || (out.getItem() == Items.WATER_BUCKET && out.getCount() < out.getMaxStackSize())) {
                    te.tank.setFill(te.tank.getFill() - 1000);
                    if (te.tank.getFill() == 0) te.tank.setTankType(Fluids.NONE);
                    in.shrink(1);
                    if (out.isEmpty()) {
                        te.inventory.setStackInSlot(1, new ItemStack(Items.WATER_BUCKET));
                    } else {
                        out.grow(1);
                    }
                    te.markChanged();
                    return;
                }
            }

            // Canister full -> unload
            if (in.getItem() == ModItems.CANISTER_FULL.get()) {
                FluidType canisterFluid = ItemCanister.getFluidType(in);
                if (canisterFluid != Fluids.NONE && (te.tank.getTankType() == Fluids.NONE || te.tank.getTankType() == canisterFluid) && te.tank.getSpace() >= 1000) {
                    if (out.isEmpty() || (out.getItem() == ModItems.CANISTER_EMPTY.get() && out.getCount() < out.getMaxStackSize())) {
                        te.tank.setTankType(canisterFluid);
                        te.tank.setFill(te.tank.getFill() + 1000);
                        in.shrink(1);
                        if (out.isEmpty()) {
                            te.inventory.setStackInSlot(1, new ItemStack(ModItems.CANISTER_EMPTY.get()));
                        } else {
                            out.grow(1);
                        }
                        te.markChanged();
                        return;
                    }
                }
            }

            // Canister empty -> fill
            if (in.getItem() == ModItems.CANISTER_EMPTY.get() && te.tank.getTankType() != Fluids.NONE && te.tank.getFill() >= 1000) {
                ItemStack fullCanister = ItemCanister.createWithFluid(new ItemStack(ModItems.CANISTER_FULL.get()), te.tank.getTankType());
                if (out.isEmpty() || (ItemStack.isSameItemSameComponents(out, fullCanister) && out.getCount() < out.getMaxStackSize())) {
                    te.tank.setFill(te.tank.getFill() - 1000);
                    if (te.tank.getFill() == 0) te.tank.setTankType(Fluids.NONE);
                    in.shrink(1);
                    if (out.isEmpty()) {
                        te.inventory.setStackInSlot(1, fullCanister);
                    } else {
                        out.grow(1);
                    }
                    te.markChanged();
                }
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
