package com.hbm.items.machine;

import com.hbm.items.ItemBase;
import com.hbm.util.Function;
import com.hbm.util.Function.FunctionLinear;
import com.hbm.util.Function.FunctionQuadratic;
import com.hbm.util.Function.FunctionSqrt;
import com.hbm.util.Function.FunctionSqrtFalling;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;

import java.util.List;
import java.util.Locale;

public class ItemWatzPellet extends ItemBase {

    public final EnumWatzType watzType;
    public final boolean isDepleted;

    public ItemWatzPellet(EnumWatzType type, boolean isDepleted) {
        super(new Properties().stacksTo(16));
        this.watzType = type;
        this.isDepleted = isDepleted;
    }

    public static double getEnrichment(ItemStack stack) {
        if (stack.getItem() instanceof ItemWatzPellet pellet) {
            return getYield(stack, pellet.watzType.yield) / pellet.watzType.yield;
        }
        return 1.0D;
    }

    public static double getYield(ItemStack stack, double def) {
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        if (data != null && data.contains("yield")) {
            return data.copyTag().getDouble("yield");
        }
        return def;
    }

    public static void setYield(ItemStack stack, double yield) {
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putDouble("yield", yield));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> list, TooltipFlag flagIn) {
        if (!isDepleted) {
            list.add(Component.literal(ChatFormatting.GREEN + "Depletion: " + String.format(Locale.US, "%.1f", (1.0D - getEnrichment(stack)) * 100D) + "%"));
            String color = ChatFormatting.GOLD.toString();
            String reset = ChatFormatting.RESET.toString();

            if (watzType.passive > 0) {
                list.add(Component.literal(color + "Base fission rate: " + reset + watzType.passive));
                list.add(Component.literal(ChatFormatting.RED + "Self-igniting!"));
            }
            if (watzType.heatEmission > 0) list.add(Component.literal(color + "Heat per flux: " + reset + watzType.heatEmission + " TU"));
            if (watzType.burnFunc != null) {
                list.add(Component.literal(color + "Reaction function: " + reset + watzType.burnFunc.getLabelForFuel()));
                list.add(Component.literal(color + "Fuel type: " + reset + watzType.burnFunc.getDangerFromFuel()));
            }
            if (watzType.heatDiv != null)
                list.add(Component.literal(color + "Thermal multiplier: " + reset + watzType.heatDiv.getLabelForFuel() + " TU⁻¹"));
            if (watzType.absorbFunc != null) list.add(Component.literal(color + "Flux capture: " + reset + watzType.absorbFunc.getLabelForFuel()));
        }
    }

    public enum EnumWatzType {
        SCHRABIDIUM(0x32FFFF, 0x005C5C, 2_000, 20D, 0.01D, new FunctionLinear(1.5D), new FunctionSqrtFalling(10D), null),
        HES(0x66DCD6, 0x023933, 1_750, 20D, 0.005D, new FunctionLinear(1.25D), new FunctionSqrtFalling(15D), null),
        MES(0xCBEADF, 0x28473C, 1_500, 15D, 0.0025D, new FunctionLinear(1.15D), new FunctionSqrtFalling(15D), null),
        LES(0xABB4A8, 0x0C1105, 1_250, 15D, 0.00125D, new FunctionLinear(1D), new FunctionSqrtFalling(20D), null),
        HEN(0xA6B2A6, 0x030F03, 0, 10D, 0.0005D, new FunctionSqrt(100), new FunctionSqrtFalling(10D), null),
        MEU(0xC1C7BD, 0x2B3227, 0, 10D, 0.0005D, new FunctionSqrt(75), new FunctionSqrtFalling(10D), null),
        MEP(0x9AA3A0, 0x111A17, 0, 15D, 0.0005D, new FunctionSqrt(150), new FunctionSqrtFalling(10D), null),
        LEAD(0xA6A6B2, 0x03030F, 0, 0, 0.0025D, null, null, new FunctionSqrt(10)),
        BORON(0xBDC8D2, 0x29343E, 0, 0, 0.0025D, null, null, new FunctionLinear(10)),
        DU(0xC1C7BD, 0x2B3227, 0, 0, 0.0025D, null, null, new FunctionQuadratic(1D, 1D).withDiv(100)),
        NQD(0x4B4B4B, 0x121212, 2_000, 20, 0.01D, new FunctionLinear(2D), new FunctionSqrt(1D / 25D).withOff(25D * 25D), null),
        NQR(0x2D2D2D, 0x0B0B0B, 2_500, 30, 0.01D, new FunctionLinear(1.5D), new FunctionSqrt(1D / 25D).withOff(25D * 25D), null);

        public static final EnumWatzType[] VALUES = values();
        public final int colorLight;
        public final int colorDark;
        public final double mudContent;
        public final double passive;
        public final double heatEmission;
        public final Function burnFunc;
        public final Function heatDiv;
        public final Function absorbFunc;
        public double yield = 500_000_000;

        EnumWatzType(int colorLight, int colorDark, double passive, double heatEmission, double mudContent, Function burnFunction, Function heatDivisor, Function absorbFunction) {
            this.colorLight = colorLight;
            this.colorDark = colorDark;
            this.passive = passive;
            this.heatEmission = heatEmission;
            this.mudContent = mudContent / 2D;
            this.burnFunc = burnFunction;
            this.heatDiv = heatDivisor;
            this.absorbFunc = absorbFunction;
        }
    }
}
