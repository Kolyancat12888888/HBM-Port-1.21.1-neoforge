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

public class TileEntityMachineFluidTank extends TileEntityMachineBase {

    public final FluidTankNTM tank;

    public TileEntityMachineFluidTank(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FLUID_TANK.get(), pos, state, 2); // 0 input (fill/drain source), 1 output (empty/filled result)
        this.tank = new FluidTankNTM(Fluids.NONE, 64_000);
    }

    @Override
    public String getDefaultName() {
        return "container.fluid_tank";
    }

    public static void tick(Level level, BlockPos pos, BlockState state, TileEntityMachineFluidTank te) {
        if (level == null || level.isClientSide) return;

        ItemStack in = te.inventory.getStackInSlot(0);
        ItemStack out = te.inventory.getStackInSlot(1);

        if (!in.isEmpty()) {
            // 1. Water bucket emptying into tank
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

            // 2. Empty bucket filling with Water
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

            // 3. Lava bucket emptying into tank
            if (in.getItem() == Items.LAVA_BUCKET && (te.tank.getTankType() == Fluids.NONE || te.tank.getTankType() == Fluids.LAVA) && te.tank.getSpace() >= 1000) {
                if (out.isEmpty() || (out.getItem() == Items.BUCKET && out.getCount() < out.getMaxStackSize())) {
                    te.tank.setTankType(Fluids.LAVA);
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

            // 4. Empty bucket filling with Lava
            if (in.getItem() == Items.BUCKET && te.tank.getTankType() == Fluids.LAVA && te.tank.getFill() >= 1000) {
                if (out.isEmpty() || (out.getItem() == Items.LAVA_BUCKET && out.getCount() < out.getMaxStackSize())) {
                    te.tank.setFill(te.tank.getFill() - 1000);
                    if (te.tank.getFill() == 0) te.tank.setTankType(Fluids.NONE);
                    in.shrink(1);
                    if (out.isEmpty()) {
                        te.inventory.setStackInSlot(1, new ItemStack(Items.LAVA_BUCKET));
                    } else {
                        out.grow(1);
                    }
                    te.markChanged();
                    return;
                }
            }

            // 5. ItemCanister full -> unload fluid into tank
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

            // 6. ItemCanister empty -> fill fluid from tank
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
                    return;
                }
            }

            // 7. ItemGasCanister full -> unload gas into tank
            if (in.getItem() == ModItems.GAS_FULL.get()) {
                FluidType gasFluid = ItemGasCanister.getFluidType(in);
                if (gasFluid != Fluids.NONE && (te.tank.getTankType() == Fluids.NONE || te.tank.getTankType() == gasFluid) && te.tank.getSpace() >= 1000) {
                    if (out.isEmpty() || (out.getItem() == ModItems.GAS_EMPTY.get() && out.getCount() < out.getMaxStackSize())) {
                        te.tank.setTankType(gasFluid);
                        te.tank.setFill(te.tank.getFill() + 1000);
                        in.shrink(1);
                        if (out.isEmpty()) {
                            te.inventory.setStackInSlot(1, new ItemStack(ModItems.GAS_EMPTY.get()));
                        } else {
                            out.grow(1);
                        }
                        te.markChanged();
                        return;
                    }
                }
            }

            // 8. ItemGasCanister empty -> fill gas from tank
            if (in.getItem() == ModItems.GAS_EMPTY.get() && te.tank.getTankType() != Fluids.NONE && te.tank.getFill() >= 1000) {
                ItemStack fullGas = ItemGasCanister.createWithFluid(new ItemStack(ModItems.GAS_FULL.get()), te.tank.getTankType());
                if (out.isEmpty() || (ItemStack.isSameItemSameComponents(out, fullGas) && out.getCount() < out.getMaxStackSize())) {
                    te.tank.setFill(te.tank.getFill() - 1000);
                    if (te.tank.getFill() == 0) te.tank.setTankType(Fluids.NONE);
                    in.shrink(1);
                    if (out.isEmpty()) {
                        te.inventory.setStackInSlot(1, fullGas);
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
