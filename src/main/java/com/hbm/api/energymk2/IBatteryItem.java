package com.hbm.api.energymk2;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.nbt.CompoundTag;

public interface IBatteryItem {

	void chargeBattery(ItemStack stack, long i);

	void setCharge(ItemStack stack, long i);

	void dischargeBattery(ItemStack stack, long i);

	long getCharge(ItemStack stack);

	long getMaxCharge(ItemStack stack);

	long getChargeRate(ItemStack stack);

	long getDischargeRate(ItemStack stack);

	default String getChargeTagName() {
		return "charge";
	}

	static String getChargeTagName(ItemStack stack) {
		if (stack.getItem() instanceof IBatteryItem battery) {
			return battery.getChargeTagName();
		}
		return "charge";
	}

	static ItemStack getEmptyBattery(ItemStack stack) {
		ItemStack copy = stack.copy();
		if (copy.getItem() instanceof IBatteryItem battery) {
			battery.setCharge(copy, 0);
		}
		return copy;
	}

	static ItemStack getFullBattery(ItemStack stack) {
		ItemStack copy = stack.copy();
		if (copy.getItem() instanceof IBatteryItem battery) {
			battery.setCharge(copy, battery.getMaxCharge(copy));
		}
		return copy;
	}
}
