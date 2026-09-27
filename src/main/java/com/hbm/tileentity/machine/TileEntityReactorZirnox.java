package com.hbm.tileentity.machine;

import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTankNTM;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemZirnoxRod;
import com.hbm.items.machine.ItemZirnoxRod.EnumZirnoxType;
import com.hbm.tileentity.ModBlockEntities;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.ContaminationUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityReactorZirnox extends TileEntityMachineBase {

    public static final int maxHeat = 100_000;
    public static final int maxPressure = 100_000;

    public int heat;
    public int pressure;
    public boolean isOn = false;
    public boolean redstonePowered = false;
    public FluidTankNTM steam;
    public FluidTankNTM carbonDioxide;
    public FluidTankNTM water;
    protected int output;

    public TileEntityReactorZirnox(BlockPos pos, BlockState state) {
        super(ModBlockEntities.REACTOR_ZIRNOX.get(), pos, state, 28);
        steam = new FluidTankNTM(Fluids.SUPERHOTSTEAM, 8_000, 0);
        carbonDioxide = new FluidTankNTM(Fluids.CARBONDIOXIDE, 16_000, 1);
        water = new FluidTankNTM(Fluids.WATER, 32_000, 2);
    }

    @Override
    public String getDefaultName() {
        return "container.zirnox";
    }

    public void setRedstonePowered(boolean powered) {
        if (!powered && this.redstonePowered) {
            isOn = false;
        }
        this.redstonePowered = powered;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, TileEntityReactorZirnox zirnox) {
        if (level.isClientSide()) return;

        if (zirnox.redstonePowered) {
            zirnox.isOn = true;
        }
        zirnox.output = 0;

        if (zirnox.isOn) {
            for (int i = 0; i < 24; i++) {
                ItemStack stack = zirnox.inventory.getStackInSlot(i);
                if (!stack.isEmpty() && stack.getItem() instanceof ItemZirnoxRod) {
                    zirnox.decay(i);
                }
            }
        }

        // 2(fill) + (x * fill%)
        zirnox.pressure = (zirnox.carbonDioxide.getFill() * 2) + (int) ((float) zirnox.heat * ((float) zirnox.carbonDioxide.getFill() / (float) zirnox.carbonDioxide.getMaxFill()));

        if (zirnox.heat > 0 && zirnox.heat < maxHeat) {
            if (zirnox.water.getFill() > 0 && zirnox.carbonDioxide.getFill() > 0 && zirnox.steam.getFill() < zirnox.steam.getMaxFill()) {
                zirnox.generateSteam();
                zirnox.heat -= (int) ((float) zirnox.heat * (float) zirnox.pressure / 1_000_000F);
            } else {
                zirnox.heat -= 10;
            }
        }

        zirnox.checkIfMeltdown();
        zirnox.markChanged();
    }

    private void generateSteam() {
        if (this.heat > 10256) {
            int cycle = (int) ((((float) heat - 10256F) / (float) maxHeat) * Math.min(((float) carbonDioxide.getFill() / 14000F), 1F) * 25F * 7.5F);
            this.output = cycle;

            water.setFill(water.getFill() - cycle);
            steam.setFill(steam.getFill() + cycle);
        }
    }

    private int[] getNeighbouringSlots(int id) {
        return switch (id) {
            case 0 -> new int[]{1, 7};
            case 1 -> new int[]{0, 2, 8};
            case 2 -> new int[]{1, 9};
            case 3 -> new int[]{4, 10};
            case 4 -> new int[]{3, 5, 11};
            case 5 -> new int[]{4, 6, 12};
            case 6 -> new int[]{5, 13};
            case 7 -> new int[]{0, 8, 14};
            case 8 -> new int[]{1, 7, 9, 15};
            case 9 -> new int[]{2, 8, 16};
            case 10 -> new int[]{3, 11, 17};
            case 11 -> new int[]{4, 10, 12, 18};
            case 12 -> new int[]{5, 11, 13, 19};
            case 13 -> new int[]{6, 12, 20};
            case 14 -> new int[]{7, 15, 21};
            case 15 -> new int[]{8, 14, 16, 22};
            case 16 -> new int[]{9, 15, 23};
            case 17 -> new int[]{10, 18};
            case 18 -> new int[]{11, 17, 19};
            case 19 -> new int[]{12, 18, 20};
            case 20 -> new int[]{13, 19};
            case 21 -> new int[]{14, 22};
            case 22 -> new int[]{15, 21, 23};
            case 23 -> new int[]{16, 22};
            default -> null;
        };
    }

    private boolean hasFuelRod(int id) {
        ItemStack stack = inventory.getStackInSlot(id);
        if (!stack.isEmpty() && stack.getItem() instanceof ItemZirnoxRod rod) {
            return !rod.rodType.breeding;
        }
        return false;
    }

    private int getNeighbourCount(int id) {
        int[] neighbours = this.getNeighbouringSlots(id);
        if (neighbours == null) return 0;
        int count = 0;
        for (int neighbour : neighbours) {
            if (hasFuelRod(neighbour)) count++;
        }
        return count;
    }

    private void decay(int id) {
        int decay = getNeighbourCount(id);
        ItemStack stack = inventory.getStackInSlot(id);
        if (!(stack.getItem() instanceof ItemZirnoxRod rod)) return;

        if (!rod.rodType.breeding) decay++;

        for (int i = 0; i < decay; i++) {
            this.heat += rod.rodType.heat;
            ItemZirnoxRod.incrementLifeTime(stack);

            if (ItemZirnoxRod.getLifeTime(stack) > rod.rodType.maxLife) {
                inventory.setStackInSlot(id, ItemStack.EMPTY);
                break;
            }
        }
    }

    private void checkIfMeltdown() {
        if (this.pressure > maxPressure || this.heat > maxHeat) {
            meltdown();
        }
    }

    private void meltdown() {
        if (level == null) return;
        for (int i = 0; i < inventory.getSlots(); i++) {
            inventory.setStackInSlot(i, ItemStack.EMPTY);
        }

        level.explode(null, worldPosition.getX() + 0.5D, worldPosition.getY() + 2.0D, worldPosition.getZ() + 0.5D, 18.0F, Level.ExplosionInteraction.BLOCK);
        ContaminationUtil.radiate(level, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), 50, 500_000.0F);

        level.setBlockAndUpdate(worldPosition, Blocks.LAVA.defaultBlockState());
    }

    @Override
    protected void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.saveAdditional(compound, registries);
        compound.putInt("heat", heat);
        compound.putInt("pressure", pressure);
        compound.putBoolean("isOn", isOn);
        compound.putBoolean("redstonePowered", redstonePowered);
        steam.writeToNBT(compound, "steam");
        carbonDioxide.writeToNBT(compound, "carbondioxide");
        water.writeToNBT(compound, "water");
    }

    @Override
    protected void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.loadAdditional(compound, registries);
        heat = compound.getInt("heat");
        pressure = compound.getInt("pressure");
        isOn = compound.getBoolean("isOn");
        redstonePowered = compound.getBoolean("redstonePowered");
        steam.readFromNBT(compound, "steam");
        carbonDioxide.readFromNBT(compound, "carbondioxide");
        water.readFromNBT(compound, "water");
    }
}
