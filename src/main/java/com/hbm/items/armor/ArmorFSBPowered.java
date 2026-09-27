package com.hbm.items.armor;

import com.hbm.api.energymk2.IBatteryItem;
import com.hbm.util.BobMathUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;

import java.util.List;

public class ArmorFSBPowered extends ArmorFSB implements IBatteryItem {

	public long maxPower;
	public long chargeRate;
	public long consumption;
	public long drain;

	public ArmorFSBPowered(Holder<ArmorMaterial> material, Type type, Properties properties, long maxPower, long chargeRate, long consumption, long drain) {
		super(material, type, properties);
		this.maxPower = maxPower;
		this.chargeRate = chargeRate;
		this.consumption = consumption;
		this.drain = drain;
	}

	@Override
	public boolean isArmorEnabled(ItemStack stack) {
		return getCharge(stack) > 0;
	}

	@Override
	public void chargeBattery(ItemStack stack, long i) {
		setCharge(stack, Math.min(getMaxCharge(stack), Math.max(0, getCharge(stack) + i)));
	}

	@Override
	public void setCharge(ItemStack stack, long i) {
		CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
		CompoundTag tag = customData.copyTag();
		tag.putLong("charge", i);
		stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
	}

	@Override
	public void dischargeBattery(ItemStack stack, long i) {
		setCharge(stack, Math.min(getMaxCharge(stack), Math.max(0, getCharge(stack) - i)));
	}

	@Override
	public long getCharge(ItemStack stack) {
		CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
		if (customData != null && customData.contains("charge")) {
			return customData.copyTag().getLong("charge");
		}
		return maxPower; // default fully charged
	}

	@Override
	public long getMaxCharge(ItemStack stack) {
		return maxPower;
	}

	@Override
	public long getChargeRate(ItemStack stack) {
		return chargeRate;
	}

	@Override
	public long getDischargeRate(ItemStack stack) {
		return 0;
	}

	@Override
	public boolean isBarVisible(ItemStack stack) {
		return getCharge(stack) < getMaxCharge(stack);
	}

	@Override
	public int getBarWidth(ItemStack stack) {
		return Math.round(13.0F * (float) getCharge(stack) / (float) getMaxCharge(stack));
	}

	@Override
	public int getBarColor(ItemStack stack) {
		return 0x00FF00;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		super.appendHoverText(stack, context, tooltip, flag);
		tooltip.add(Component.literal("§eCharge: §f" + BobMathUtil.getShortNumber(getCharge(stack)) + " / " + BobMathUtil.getShortNumber(getMaxCharge(stack)) + " HE"));
	}
}
