package com.hbm.items.machine;

import com.hbm.items.ItemBase;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ItemCatalyst extends ItemBase {

	public final int color;
	public final long powerAbs;
	public final float powerMod;
	public final float heatMod;
	public final float fuelMod;

	public ItemCatalyst(int color) {
		this(color, 0, 1.0F, 1.0F, 1.0F);
	}

	public ItemCatalyst(int color, long powerAbs, float powerMod, float heatMod, float fuelMod) {
		super(new Properties().stacksTo(1));
		this.color = color;
		this.powerAbs = powerAbs;
		this.powerMod = powerMod;
		this.heatMod = heatMod;
		this.fuelMod = fuelMod;
	}

	public int getColor() {
		return this.color;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(Component.literal("Adds spice to the core."));
		tooltip.add(Component.literal("Look at all those colors!"));
	}

	public static long getPowerAbs(ItemStack stack) {
		if (stack == null || !(stack.getItem() instanceof ItemCatalyst cat))
			return 0;
		return cat.powerAbs;
	}

	public static float getPowerMod(ItemStack stack) {
		if (stack == null || !(stack.getItem() instanceof ItemCatalyst cat))
			return 1F;
		return cat.powerMod;
	}

	public static float getHeatMod(ItemStack stack) {
		if (stack == null || !(stack.getItem() instanceof ItemCatalyst cat))
			return 1F;
		return cat.heatMod;
	}

	public static float getFuelMod(ItemStack stack) {
		if (stack == null || !(stack.getItem() instanceof ItemCatalyst cat))
			return 1F;
		return cat.fuelMod;
	}
}
