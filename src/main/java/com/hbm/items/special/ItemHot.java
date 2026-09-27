package com.hbm.items.special;

import com.hbm.items.ItemBakedBase;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;

public class ItemHot extends ItemBakedBase {

	public static int heat;
	protected final String baseTexturePath;
	private final String overlayTexturePath;

	public ItemHot(Properties properties, int heat, String s) {
		super(properties, s);
		ItemHot.heat = heat;
		this.baseTexturePath = s;
		this.overlayTexturePath = s + "_hot";
	}

	public ItemHot(int heat, String s) {
		this(new Properties(), heat, s);
	}

	public static ItemStack heatUp(ItemStack stack) {
		if (!(stack.getItem() instanceof ItemHot)) return stack;
		CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putInt("heat", heat));
		return stack;
	}

	public static ItemStack heatUp(ItemStack stack, double d) {
		if (!(stack.getItem() instanceof ItemHot)) return stack;
		CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putInt("heat", (int) (d * heat)));
		return stack;
	}

	public static double getHeat(ItemStack stack) {
		if (!(stack.getItem() instanceof ItemHot)) return 0;
		CustomData data = stack.get(DataComponents.CUSTOM_DATA);
		if (data != null && data.contains("heat")) {
			int h = data.copyTag().getInt("heat");
			return (double) h / (double) heat;
		}
		return 0;
	}

	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity entity, int itemSlot, boolean isSelected) {
		if (!world.isClientSide()) {
			CustomData data = stack.get(DataComponents.CUSTOM_DATA);
			if (data != null && data.contains("heat")) {
				int h = data.copyTag().getInt("heat");
				if (h > 0) {
					CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putInt("heat", h - 1));
				} else {
					CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.remove("heat"));
				}
			}
		}
	}
}
