package com.hbm.tileentity.machine.rbmk;

import com.hbm.config.GeneralConfig;
import net.minecraft.util.Mth;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;

public class RBMKDials {

    public static double getPassiveCooling(Level world) {
        return (double) RBMKKeys.KEY_PASSIVE_COOLING.defValue;
    }

    public static double getPassiveCoolingInner(Level world) {
        return (double) RBMKKeys.KEY_PASSIVE_COOLING_INNER.defValue;
    }

    public static double getColumnHeatFlow(Level world) {
        return (double) RBMKKeys.KEY_COLUMN_HEAT_FLOW.defValue;
    }

    public static double getFuelDiffusionMod(Level world) {
        return (double) RBMKKeys.KEY_FUEL_DIFFUSION_MOD.defValue;
    }

    public static double getFuelHeatProvision(Level world) {
        return (double) RBMKKeys.KEY_HEAT_PROVISION.defValue;
    }

    public static int getColumnHeightRuleValue(Level world) {
        return (int) RBMKKeys.KEY_COLUMN_HEIGHT.defValue;
    }

    public static int getColumnHeight(Level world) {
        return getColumnHeightRuleValue(world) - 1;
    }

    public static boolean getPermaScrap(Level world) {
        return (boolean) RBMKKeys.KEY_PERMANENT_SCRAP.defValue;
    }

    public static double getBoilerHeatConsumption(Level world) {
        return (double) RBMKKeys.KEY_BOILER_HEAT_CONSUMPTION.defValue;
    }

    public static double getControlSpeed(Level world) {
        return (double) RBMKKeys.KEY_CONTROL_SPEED_MOD.defValue;
    }

    public static double getReactivityMod(Level world) {
        return (double) RBMKKeys.KEY_REACTIVITY_MOD.defValue;
    }

    public static double getOutgasserMod(Level world) {
        return (double) RBMKKeys.KEY_OUTGASSER_MOD.defValue;
    }

    public static double getSurgeMod(Level world) {
        return (double) RBMKKeys.KEY_SURGE_MOD.defValue;
    }

    public static int getFluxRange(Level world) {
        return (int) RBMKKeys.KEY_FLUX_RANGE.defValue;
    }

    public static int getReaSimRange(Level world) {
        return (int) RBMKKeys.KEY_REASIM_RANGE.defValue;
    }

    public static int getReaSimCount(Level world) {
        return (int) RBMKKeys.KEY_REASIM_COUNT.defValue;
    }

    public static double getReaSimOutputMod(Level world) {
        return (double) RBMKKeys.KEY_REASIM_MOD.defValue;
    }

    public static boolean getReasimBoilers(Level world) {
        return (boolean) RBMKKeys.KEY_REASIM_BOILERS.defValue;
    }

    public static double getReaSimBoilerSpeed(Level world) {
        return (double) RBMKKeys.KEY_REASIM_BOILER_SPEED.defValue;
    }

    public static boolean getMeltdownsDisabled(Level world) {
        return (boolean) RBMKKeys.KEY_DISABLE_MELTDOWNS.defValue;
    }

    public static boolean getOverpressure(Level world) {
        return (boolean) RBMKKeys.KEY_ENABLE_MELTDOWN_OVERPRESSURE.defValue;
    }

    public static double getModeratorEfficiency(Level world) {
        return (double) RBMKKeys.KEY_MODERATOR_EFFICIENCY.defValue;
    }

    public static double getAbsorberEfficiency(Level world) {
        return (double) RBMKKeys.KEY_ABSORBER_EFFICIENCY.defValue;
    }

    public static double getAbsorberHeatConversion(Level world) {
        return (double) RBMKKeys.KEY_ABSORBER_HEAT_CONVERSION.defValue;
    }

    public static double getReflectorEfficiency(Level world) {
        return (double) RBMKKeys.KEY_REFLECTOR_EFFICIENCY.defValue;
    }

    public static boolean getDepletion(Level world) {
        return !(boolean) RBMKKeys.KEY_DISABLE_DEPLETION.defValue;
    }

    public static boolean getXenon(Level world) {
        return !(boolean) RBMKKeys.KEY_DISABLE_XENON.defValue;
    }

    public enum RBMKKeys {
        KEY_SAVE_DIALS("dialSaveDials", true),
        KEY_PASSIVE_COOLING("dialPassiveCooling", 2.5),
        KEY_PASSIVE_COOLING_INNER("dialPassiveCoolingInner", 0.1),
        KEY_COLUMN_HEAT_FLOW("dialColumnHeatFlow", 0.2),
        KEY_FUEL_DIFFUSION_MOD("dialDiffusionMod", 1.0),
        KEY_HEAT_PROVISION("dialHeatProvision", 0.2),
        KEY_COLUMN_HEIGHT("dialColumnHeight", 4),
        KEY_PERMANENT_SCRAP("dialEnablePermaScrap", true),
        KEY_BOILER_HEAT_CONSUMPTION("dialBoilerHeatConsumption", 0.1),
        KEY_CONTROL_SPEED_MOD("dialControlSpeed", 1.0),
        KEY_REACTIVITY_MOD("dialReactivityMod", 1.0),
        KEY_OUTGASSER_MOD("dialOutgasserSpeedMod", 1.0),
        KEY_SURGE_MOD("dialControlSurgeMod", 1.0),
        KEY_FLUX_RANGE("dialFluxRange", 5),
        KEY_REASIM_RANGE("dialReasimRange", 10),
        KEY_REASIM_COUNT("dialReasimCount", 6),
        KEY_REASIM_MOD("dialReasimOutputMod", 1.0),
        KEY_REASIM_BOILERS("dialReasimBoilers", false),
        KEY_REASIM_BOILER_SPEED("dialReasimBoilerSpeed", 0.05),
        KEY_DISABLE_MELTDOWNS("dialDisableMeltdowns", false),
        KEY_ENABLE_MELTDOWN_OVERPRESSURE("dialEnableMeltdownOverpressure", false),
        KEY_MODERATOR_EFFICIENCY("dialModeratorEfficiency", 1.0),
        KEY_ABSORBER_EFFICIENCY("dialAbsorberEfficiency", 1.0),
        KEY_REFLECTOR_EFFICIENCY("dialReflectorEfficiency", 1.0),
        KEY_DISABLE_DEPLETION("dialDisableDepletion", false),
        KEY_DISABLE_XENON("dialDisableXenon", false),
        KEY_ABSORBER_HEAT_CONVERSION("dialAbsorberHeatConversion", 0.05);

        public static final RBMKKeys[] VALUES = values();

        public final String keyString;
        public final Object defValue;

        RBMKKeys(String key, Object def) {
            keyString = key;
            defValue = def;
        }
    }
}
