package com.hbm.tileentity.machine;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTankNTM;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemCatalyst;
import com.hbm.items.special.ItemAMSCore;
import com.hbm.tileentity.ModBlockEntities;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.ContaminationUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class TileEntityCore extends TileEntityMachineBase {

    public int field;
    public int heat;
    public int prevHeat;
    public int color;
    public FluidTankNTM[] tanks;
    public boolean meltdownTick = false;
    protected int consumption;
    protected int prevConsumption;
    private boolean lastTickValid = true;

    public TileEntityCore(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DFC_CORE.get(), pos, state, 3);
        tanks = new FluidTankNTM[2];
        tanks[0] = new FluidTankNTM(Fluids.DEUTERIUM, 128_000, 0);
        tanks[1] = new FluidTankNTM(Fluids.TRITIUM, 128_000, 1);
    }

    @Override
    public String getDefaultName() {
        return "container.dfcCore";
    }

    public static void tick(Level level, BlockPos pos, BlockState state, TileEntityCore core) {
        if (level.isClientSide()) return;

        core.prevConsumption = core.consumption;
        core.consumption = 0;
        core.meltdownTick = false;
        core.lastTickValid = level.isLoaded(pos);

        if (core.lastTickValid && core.heat > 0 && core.heat >= core.field) {
            int fill = core.tanks[0].getFill() + core.tanks[1].getFill();
            int max = core.tanks[0].getMaxFill() + core.tanks[1].getMaxFill();
            int mod = core.heat * 10;
            float size = Math.max(Math.min((float) fill * mod / max, 100.0F), 10.0F);

            level.explode(null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, size, Level.ExplosionInteraction.BLOCK);
            ContaminationUtil.radiate(level, pos.getX(), pos.getY(), pos.getZ(), 50, 5000.0F);
            core.meltdownTick = true;
        }

        ItemStack cat0 = core.inventory.getStackInSlot(0);
        ItemStack cat2 = core.inventory.getStackInSlot(2);
        if (cat0.getItem() instanceof ItemCatalyst c0 && cat2.getItem() instanceof ItemCatalyst c2) {
            core.color = core.calcAvgHex(c0.getColor(), c2.getColor());
        } else {
            core.color = 0;
        }

        if (core.heat > 0) {
            core.radiation();
        }

        core.prevHeat = core.heat;
        core.heat = 0;

        if (core.lastTickValid && core.field > 0) {
            core.field -= 1;
        }

        core.markChanged();
    }

    private void radiation() {
        if (level == null) return;
        double range = this.meltdownTick ? 50 : 10;
        AABB aabb = new AABB(worldPosition.getX() - range, worldPosition.getY() - range, worldPosition.getZ() - range,
                worldPosition.getX() + range + 1, worldPosition.getY() + range + 1, worldPosition.getZ() + range + 1);

        List<Entity> list = level.getEntities((Entity) null, aabb, e -> true);
        for (Entity e : list) {
            if (e instanceof LivingEntity living) {
                ContaminationUtil.contaminate(living, ContaminationUtil.HazardType.RADIATION, ContaminationUtil.ContaminationType.CREATIVE, 50.0D);
            }
            e.igniteForSeconds(3);
        }
    }

    public boolean isReady() {
        if (!lastTickValid) return false;
        if (getCore() == 0) return false;
        if (color == 0) return false;
        return getFuelEfficiency(tanks[0].getTankType()) > 0 && getFuelEfficiency(tanks[1].getTankType()) > 0;
    }

    // 100 emitter watt = 10000 joules = 1 heat = 10mB burned
    public long burn(long joules) {
        if (!isReady()) return joules;

        int demand = (int) Math.ceil((double) joules / 1000D);
        if (tanks[0].getFill() < demand || tanks[1].getFill() < demand) return joules;

        this.consumption += demand;
        heat += (int) Math.ceil((double) joules / 10000D);

        tanks[0].setFill(tanks[0].getFill() - demand);
        tanks[1].setFill(tanks[1].getFill() - demand);

        return (long) (joules * getCore() * getFuelEfficiency(tanks[0].getTankType()) * getFuelEfficiency(tanks[1].getTankType()));
    }

    public float getFuelEfficiency(FluidType type) {
        if (type == Fluids.HYDROGEN) return 1.0F;
        if (type == Fluids.DEUTERIUM) return 1.5F;
        if (type == Fluids.TRITIUM) return 1.7F;
        if (type == Fluids.OXYGEN) return 1.2F;
        if (type == Fluids.PEROXIDE) return 1.4F;
        if (type == Fluids.XENON) return 1.5F;
        if (type == Fluids.SAS3) return 2.0F;
        if (type == Fluids.BALEFIRE) return 2.5F;
        if (type == Fluids.AMAT) return 2.2F;
        if (type == Fluids.ASCHRAB) return 2.7F;
        return 0;
    }

    public boolean hasCore() {
        return getCore() != 0;
    }

    public int getCore() {
        ItemStack slot = inventory.getStackInSlot(1);
        if (slot.isEmpty()) return 0;
        if (slot.getItem() == ModItems.ams_core_sing) return 500;
        if (slot.getItem() == ModItems.ams_core_wormhole) return 650;
        if (slot.getItem() == ModItems.ams_core_eyeofharmony) return 800;
        if (slot.getItem() == ModItems.ams_core_thingy) return 2500;
        return 0;
    }

    public int getCorePower() {
        return ItemAMSCore.getPowerBase(inventory.getStackInSlot(1));
    }

    private int calcAvgHex(int h1, int h2) {
        int r1 = ((h1 & 0xFF0000) >> 16);
        int g1 = ((h1 & 0x00FF00) >> 8);
        int b1 = ((h1 & 0x0000FF) >> 0);

        int r2 = ((h2 & 0xFF0000) >> 16);
        int g2 = ((h2 & 0x00FF00) >> 8);
        int b2 = ((h2 & 0x0000FF) >> 0);

        int r = (((r1 + r2) / 2) << 16);
        int g = (((g1 + g2) / 2) << 8);
        int b = (((b1 + b2) / 2) << 0);

        return r | g | b;
    }

    @Override
    protected void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.saveAdditional(compound, registries);
        tanks[0].writeToNBT(compound, "fuel1");
        tanks[1].writeToNBT(compound, "fuel2");
        compound.putInt("field", this.field);
    }

    @Override
    protected void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.loadAdditional(compound, registries);
        tanks[0].readFromNBT(compound, "fuel1");
        tanks[1].readFromNBT(compound, "fuel2");
        this.field = compound.getInt("field");
    }
}
