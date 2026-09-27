package com.hbm.items.special;

import com.hbm.items.ItemBase;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;

import java.util.List;

public class ItemFusionShield extends ItemBase {

	public final long maxDamage;
	public final int maxTemp;

	public ItemFusionShield(Properties properties, long maxDamage, int maxTemp) {
		super(properties.stacksTo(1));
		this.maxDamage = maxDamage;
		this.maxTemp = maxTemp;
	}

	public ItemFusionShield(long maxDamage, int maxTemp) {
		this(new Properties(), maxDamage, maxTemp);
	}

	public static long getShieldDamage(ItemStack stack) {
		CustomData data = stack.get(DataComponents.CUSTOM_DATA);
		if (data == null || !data.contains("damage")) {
			return 0;
		}
		return data.copyTag().getLong("damage");
	}

	public static void setShieldDamage(ItemStack stack, long damage) {
		CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putLong("damage", damage));
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
		long damage = getShieldDamage(stack);
		int percent = (int) ((maxDamage - damage) * 100 / (maxDamage > 0 ? maxDamage : 1));

		tooltip.add(Component.literal("Durability: " + (maxDamage - damage) + "/" + maxDamage + " (" + percent + "%)"));
		tooltip.add(Component.literal("Maximum Plasma Heat: " + ChatFormatting.RED + "" + maxTemp + "°C"));
	}

	@Override
	public boolean isBarVisible(ItemStack stack) {
		return getShieldDamage(stack) != 0;
	}

	@Override
	public int getBarWidth(ItemStack stack) {
		return Math.round((float) (maxDamage - getShieldDamage(stack)) * 13.0F / (float) maxDamage);
	}

	@Override
	public int getBarColor(ItemStack stack) {
		return 0x00FF88;
	}
}
