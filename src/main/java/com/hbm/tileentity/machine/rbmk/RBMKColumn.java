package com.hbm.tileentity.machine.rbmk;

import com.hbm.inventory.fluid.Fluids;
import com.hbm.util.I18nUtil;
import net.minecraft.ChatFormatting;

import java.util.ArrayList;
import java.util.List;

public abstract class RBMKColumn {
    public double heat;
    public double maxHeat;
    public boolean moderated;
    public int reasimWater;
    public int reasimSteam;
    public int indicator;

    public final ColumnType type;

    protected RBMKColumn(ColumnType type) {
        this.type = type;
    }

    public List<String> getFancyStats() {
        List<String> stats = new ArrayList<>();
        stats.add(ChatFormatting.YELLOW + I18nUtil.resolveKey("rbmk.heat", ((int) (heat * 10D)) / 10D + "°C"));

        if (moderated) {
            stats.add(ChatFormatting.YELLOW + I18nUtil.resolveKey("rbmk.moderated"));
        }

        return stats;
    }

    public static RBMKColumn createForType(ColumnType type) {
        return switch (type) {
            case FUEL, FUEL_SIM, BREEDER -> new FuelColumn(type);
            case BOILER -> new BoilerColumn();
            case CONTROL, CONTROL_AUTO -> new ControlColumn(type);
            case COOLER -> new CoolerColumn();
            case OUTGASSER -> new OutgasserColumn();
            case HEATEX -> new HeaterColumn();
            default -> new StandardColumn(type);
        };
    }

    public enum ColumnType {
        BLANK(0), FUEL(10), FUEL_SIM(90), CONTROL(20), CONTROL_AUTO(30), BOILER(40),
        MODERATOR(50), ABSORBER(60), REFLECTOR(70), OUTGASSER(80), BREEDER(100),
        STORAGE(110), COOLER(120), HEATEX(130);

        public static final ColumnType[] VALUES = values();
        public final int offset;

        ColumnType(int offset) {
            this.offset = offset;
        }
    }

    public static class StandardColumn extends RBMKColumn {
        public StandardColumn(ColumnType type) {
            super(type);
        }
    }

    public static class FuelColumn extends RBMKColumn {
        public double enrichment;
        public double xenon;
        public double c_coreHeat;
        public double c_heat;
        public double c_maxHeat;

        public FuelColumn(ColumnType type) {
            super(type);
        }

        @Override
        public List<String> getFancyStats() {
            List<String> stats = super.getFancyStats();
            stats.add(ChatFormatting.GREEN + I18nUtil.resolveKey("rbmk.rod.depletion", ((int) (((1D - enrichment) * 100000)) / 1000D) + "%"));
            stats.add(ChatFormatting.DARK_PURPLE + I18nUtil.resolveKey("rbmk.rod.xenon", ((int) ((xenon * 1000D)) / 1000D) + "%"));
            stats.add(ChatFormatting.DARK_RED + I18nUtil.resolveKey("rbmk.rod.coreTemp", ((int) (c_coreHeat * 10D)) / 10D + "°C"));
            stats.add(ChatFormatting.RED + I18nUtil.resolveKey("rbmk.rod.skinTemp", ((int) (c_heat * 10D)) / 10D + "°C", ((int) (c_maxHeat * 10D)) / 10D + "°C"));
            return stats;
        }
    }

    public static class BoilerColumn extends RBMKColumn {
        public int water;
        public int maxWater;
        public int steam;
        public int maxSteam;
        public short steamType;

        public BoilerColumn() {
            super(ColumnType.BOILER);
        }

        @Override
        public List<String> getFancyStats() {
            List<String> stats = super.getFancyStats();
            stats.add(ChatFormatting.BLUE + I18nUtil.resolveKey("rbmk.boiler.water", water, maxWater));
            stats.add(ChatFormatting.WHITE + I18nUtil.resolveKey("rbmk.boiler.steam", steam, maxSteam));
            stats.add(ChatFormatting.YELLOW + I18nUtil.resolveKey("rbmk.boiler.type", Fluids.fromID(steamType).getLocalizedName()));
            return stats;
        }
    }

    public static class ControlColumn extends RBMKColumn {
        public double level;
        public short color = -1;

        public ControlColumn(ColumnType type) {
            super(type);
        }

        @Override
        public List<String> getFancyStats() {
            List<String> stats = super.getFancyStats();
            stats.add(ChatFormatting.YELLOW + I18nUtil.resolveKey("rbmk.control.level", ((int) (level * 100D)) + "%"));
            return stats;
        }
    }

    public static class CoolerColumn extends RBMKColumn {
        public int cooled;
        public int cryo;
        public int maxCryo;
        public int hot;
        public int maxHot;
        public short coldType;
        public short hotType;

        public CoolerColumn() {
            super(ColumnType.COOLER);
        }

        @Override
        public List<String> getFancyStats() {
            List<String> stats = super.getFancyStats();
            stats.add(ChatFormatting.AQUA + I18nUtil.resolveKey("rbmk.cooler.cooling", cooled * 20));
            stats.add(ChatFormatting.BLUE + Fluids.fromID(coldType).getLocalizedName() + " " + cryo + "/" + maxCryo + "mB");
            stats.add(ChatFormatting.RED + Fluids.fromID(hotType).getLocalizedName() + " " + hot + "/" + maxHot + "mB");
            return stats;
        }
    }

    public static class OutgasserColumn extends RBMKColumn {
        public int gas;
        public int maxGas;
        public double progress;
        public double maxProgress;
        public double usedFlux;

        public OutgasserColumn() {
            super(ColumnType.OUTGASSER);
        }

        @Override
        public List<String> getFancyStats() {
            List<String> stats = super.getFancyStats();
            stats.add(ChatFormatting.AQUA + I18nUtil.resolveKey("rbmk.outgasser.flux", (long) usedFlux));
            stats.add(ChatFormatting.YELLOW + I18nUtil.resolveKey("rbmk.outgasser.gas", gas, maxGas));
            return stats;
        }
    }

    public static class HeaterColumn extends RBMKColumn {
        public int water;
        public int maxWater;
        public int steam;
        public int maxSteam;
        public short coldType;
        public short hotType;

        public HeaterColumn() {
            super(ColumnType.HEATEX);
        }

        @Override
        public List<String> getFancyStats() {
            List<String> stats = super.getFancyStats();
            stats.add(ChatFormatting.BLUE + Fluids.fromID(coldType).getLocalizedName() + " " + water + "/" + maxWater + "mB");
            stats.add(ChatFormatting.RED + Fluids.fromID(hotType).getLocalizedName() + " " + steam + "/" + maxSteam + "mB");
            return stats;
        }
    }
}
