package com.hbm.inventory.fluid.tank;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import net.minecraft.nbt.CompoundTag;

public class FluidTankNTM implements Cloneable {
    private FluidType type = Fluids.NONE;
    private int fill = 0;
    private int maxFill;
    private int pressure = 0;
    public int index = 0;

    public FluidTankNTM(FluidType type, int maxFill) {
        this.type = type != null ? type : Fluids.NONE;
        this.maxFill = maxFill;
    }

    public FluidTankNTM(FluidType type, int maxFill, int index) {
        this.type = type != null ? type : Fluids.NONE;
        this.maxFill = maxFill;
        this.index = index;
    }

    public FluidTankNTM(int maxFill) {
        this(Fluids.NONE, maxFill);
    }

    public FluidType getTankType() {
        return type;
    }

    public void setTankType(FluidType type) {
        this.type = type != null ? type : Fluids.NONE;
    }

    public int getFill() {
        return fill;
    }

    public void setFill(int fill) {
        this.fill = Math.min(Math.max(fill, 0), maxFill);
    }

    public int getMaxFill() {
        return maxFill;
    }

    public void setMaxFill(int maxFill) {
        this.maxFill = maxFill;
    }

    public int getPressure() {
        return pressure;
    }

    public FluidTankNTM withPressure(int pressure) {
        this.pressure = pressure;
        return this;
    }

    public int getSpace() {
        return Math.max(maxFill - fill, 0);
    }

    public void writeToNBT(CompoundTag nbt, String s) {
        nbt.putInt(s, fill);
        nbt.putInt(s + "_max", maxFill);
        if (type != null) {
            nbt.putString(s + "_type", type.getName());
        }
        nbt.putShort(s + "_p", (short) pressure);
    }

    public void readFromNBT(CompoundTag nbt, String s) {
        fill = nbt.getInt(s);
        int max = nbt.getInt(s + "_max");
        if (max > 0) maxFill = max;
        fill = Math.max(0, Math.min(fill, maxFill));

        if (nbt.contains(s + "_type")) {
            type = Fluids.fromName(nbt.getString(s + "_type"));
        }
        this.pressure = nbt.getShort(s + "_p");
    }

    @Override
    public FluidTankNTM clone() {
        try {
            return (FluidTankNTM) super.clone();
        } catch (CloneNotSupportedException e) {
            FluidTankNTM tank = new FluidTankNTM(this.type, this.maxFill, this.index);
            tank.fill = this.fill;
            tank.pressure = this.pressure;
            return tank;
        }
    }
}
