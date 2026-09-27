package com.hbm.items.machine;

import com.hbm.items.ItemBase;
import com.hbm.util.I18nUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;

import java.util.List;

public class ItemLens extends ItemBase {

	public long maxDamage;

	public ItemLens(long maxDamage) {
		super(new Properties().stacksTo(1));
		this.maxDamage = maxDamage;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> list, TooltipFlag flag) {
		long damage = getLensDamage(stack);
		double percent = (int)((maxDamage - damage) * 100000000D / maxDamage) / 1000000D;

		list.add(Component.literal(ChatFormatting.DARK_AQUA + I18nUtil.resolveKey("desc.durticks") + " " + (maxDamage - damage) + " / " + maxDamage));
		list.add(Component.literal(ChatFormatting.DARK_AQUA + I18nUtil.resolveKey("desc.durpercents") + " " + percent + "%"));
	}

	public static long getLensDamage(ItemStack stack) {
		if (stack == null || stack.isEmpty()) return 0;
		CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
		if (customData != null) {
			CompoundTag tag = customData.copyTag();
			if (tag.contains("damage")) {
				return tag.getLong("damage");
			}
		}
		return 0;
	}

	public static void setLensDamage(ItemStack stack, long damage) {
		if (stack == null || stack.isEmpty()) return;
		CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
		CompoundTag tag = customData.copyTag();
		tag.putLong("damage", damage);
		stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
	}
}
