package com.hbm.items.machine;

import com.hbm.items.ItemBase;
import com.hbm.util.Function;
import com.hbm.util.Function.FunctionLogarithmic;
import com.hbm.util.Function.FunctionSqrt;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ItemPWRFuel extends ItemBase {

    protected final EnumPWRFuel fuelType;

    public ItemPWRFuel(Properties properties, EnumPWRFuel fuelType) {
        super(properties);
        this.fuelType = fuelType;
    }

    public ItemPWRFuel(EnumPWRFuel fuelType) {
        this(new Properties(), fuelType);
    }

    public EnumPWRFuel getFuelType() {
        return fuelType;
    }

    public enum EnumPWRFuel {
        MEU(		05.0D,	new FunctionLogarithmic(20 * 30).withDiv(2_500)),
        HEU233(		07.5D,	new FunctionSqrt(25)),
        HEU235(		07.5D,	new FunctionSqrt(22.5)),
        MEN(		07.5D,	new FunctionLogarithmic(22.5 * 30).withDiv(2_500)),
        HEN237(		07.5D,	new FunctionSqrt(27.5)),
        MOX(		07.5D,	new FunctionLogarithmic(20 * 30).withDiv(2_500)),
        MEP(		07.5D,	new FunctionLogarithmic(22.5 * 30).withDiv(2_500)),
        HEP239(		10.0D,	new FunctionSqrt(22.5)),
        HEP241(		10.0D,	new FunctionSqrt(25)),
        MEA(		07.5D,	new FunctionLogarithmic(25 * 30).withDiv(2_500)),
        HEA242(		10.0D,	new FunctionSqrt(25)),
        HES326(		12.5D,	new FunctionSqrt(27.5)),
        HES327(		12.5D,	new FunctionSqrt(30)),
        BFB_AM_MIX(	2.5D,	new FunctionSqrt(15), 250_000_000),
        BFB_PU241(	2.5D,	new FunctionSqrt(15), 250_000_000);

        public static final EnumPWRFuel[] VALUES = values();

        public final double yield;
        public final double heatEmission;
        public final Function function;

        EnumPWRFuel(double heatEmission, Function function, double yield) {
            this.heatEmission = heatEmission;
            this.function = function;
            this.yield = yield;
        }

        EnumPWRFuel(double heatEmission, Function function) {
            this(heatEmission, function, 1_000_000_000);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        String color = ChatFormatting.GOLD.toString();
        String reset = ChatFormatting.RESET.toString();

        tooltipComponents.add(Component.literal(color + "Heat per flux: " + reset + fuelType.heatEmission + " TU"));
        tooltipComponents.add(Component.literal(color + "Reaction function: " + reset + fuelType.function.getLabelForFuel()));
        tooltipComponents.add(Component.literal(color + "Fuel type: " + reset + fuelType.function.getDangerFromFuel()));
    }
}
