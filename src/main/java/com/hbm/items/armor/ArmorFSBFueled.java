package com.hbm.items.armor;

import com.hbm.api.fluidmk2.IFillableItem;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.util.BobMathUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;

import java.util.List;

public class ArmorFSBFueled extends ArmorFSB implements IFillableItem {

	public FluidType fuelType;
	public int maxFuel;
	public int fillRate;
	public int consumption;
	public int drain;

	public ArmorFSBFueled(Holder<ArmorMaterial> material, Type type, Properties properties, FluidType fuelType, int maxFuel, int fillRate, int consumption, int drain) {
		super(material, type, properties);
		this.fuelType = fuelType;
		this.maxFuel = maxFuel;
		this.fillRate = fillRate;
		this.consumption = consumption;
		this.drain = drain;
	}

	@Override
	public boolean isArmorEnabled(ItemStack stack) {
		return getFill(stack) > 0;
	}

	@Override
	public int getFill(ItemStack stack) {
		CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
		if (customData != null && customData.contains("fuel")) {
			return customData.copyTag().getInt("fuel");
		}
		return maxFuel; // default full
	}

	public void setFill(ItemStack stack, int fill) {
		CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
		CompoundTag tag = customData.copyTag();
		tag.putInt("fuel", fill);
		stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
	}

	public int getMaxFill(ItemStack stack) {
		return this.maxFuel;
	}

	public int getLoadSpeed(ItemStack stack) {
		return this.fillRate;
	}

	public int getUnloadSpeed(ItemStack stack) {
		return 0;
	}

	@Override
	public boolean acceptsFluid(FluidType type, ItemStack stack) {
		return type == this.fuelType;
	}

	@Override
	public int tryFill(FluidType type, int amount, ItemStack stack) {
		if (!acceptsFluid(type, stack)) return amount;
		int toFill = Math.min(amount, this.fillRate);
		toFill = Math.min(toFill, this.maxFuel - this.getFill(stack));
		this.setFill(stack, this.getFill(stack) + toFill);
		return amount - toFill;
	}

	@Override
	public boolean providesFluid(FluidType type, ItemStack stack) {
		return false;
	}

	@Override
	public int tryEmpty(FluidType type, int amount, ItemStack stack) {
		return 0;
	}

	@Override
	public FluidType getFirstFluidType(ItemStack stack) {
		return this.fuelType;
	}

	@Override
	public boolean isBarVisible(ItemStack stack) {
		return getFill(stack) < getMaxFill(stack);
	}

	@Override
	public int getBarWidth(ItemStack stack) {
		return Math.round(13.0F * (float) getFill(stack) / (float) getMaxFill(stack));
	}

	@Override
	public int getBarColor(ItemStack stack) {
		return 0xFFAA00;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		super.appendHoverText(stack, context, tooltip, flag);
		tooltip.add(Component.literal("§6" + (fuelType != null ? fuelType.getLocalizedName() : "Fuel") + ": §f" + BobMathUtil.getShortNumber(getFill(stack)) + " / " + BobMathUtil.getShortNumber(getMaxFill(stack)) + " mB"));
	}
}
