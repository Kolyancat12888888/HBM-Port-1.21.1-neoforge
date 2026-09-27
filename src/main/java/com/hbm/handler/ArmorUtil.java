package com.hbm.handler;

import com.hbm.api.item.IGasMask;
import com.hbm.items.ModItems;
import com.hbm.potion.HbmPotion;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

import java.util.Locale;

public class ArmorUtil {

	public static final String[] metals = new String[] {
			"chainmail", "iron", "silver", "gold", "platinum", "tin", "lead",
			"liquidator", "schrabidium", "euphemium", "steel", "cmb", "titanium",
			"alloy", "copper", "bronze", "electrum", "t45", "bj", "starmetal",
			"hazmat", "rubber", "hev", "ajr", "rpa", "spacesuit"
	};

	public static boolean checkForFaraday(Player player) {
		for (ItemStack armor : player.getArmorSlots()) {
			if (armor.isEmpty() || !isFaradayArmor(armor)) return false;
		}
		return true;
	}

	public static boolean isFaradayArmor(ItemStack item) {
		if (item == null || item.isEmpty()) return false;
		String name = item.getItem().toString();

		for (String metal : metals) {
			if (name.toLowerCase(Locale.US).contains(metal)) return true;
		}
		return false;
	}

	public static boolean checkForHaz2(LivingEntity player) {
		return checkArmor(player, ModItems.hazmat_paa_helmet, ModItems.hazmat_paa_plate, ModItems.hazmat_paa_legs, ModItems.hazmat_paa_boots) ||
				checkArmor(player, ModItems.liquidator_helmet, ModItems.liquidator_plate, ModItems.liquidator_legs, ModItems.liquidator_boots) ||
				checkArmor(player, ModItems.euphemium_helmet, ModItems.euphemium_plate, ModItems.euphemium_legs, ModItems.euphemium_boots) ||
				checkArmor(player, ModItems.rpa_helmet, ModItems.rpa_plate, ModItems.rpa_legs, ModItems.rpa_boots) ||
				checkArmor(player, ModItems.fau_helmet, ModItems.fau_plate, ModItems.fau_legs, ModItems.fau_boots) ||
				checkArmor(player, ModItems.dns_helmet, ModItems.dns_plate, ModItems.dns_legs, ModItems.dns_boots);
	}

	public static boolean checkForHazmatOnly(LivingEntity player) {
		return checkArmor(player, ModItems.hazmat_helmet, ModItems.hazmat_plate, ModItems.hazmat_legs, ModItems.hazmat_boots) ||
				checkArmor(player, ModItems.hazmat_helmet_red, ModItems.hazmat_plate_red, ModItems.hazmat_legs_red, ModItems.hazmat_boots_red) ||
				checkArmor(player, ModItems.hazmat_helmet_grey, ModItems.hazmat_plate_grey, ModItems.hazmat_legs_grey, ModItems.hazmat_boots_grey) ||
				checkArmor(player, ModItems.hazmat_paa_helmet, ModItems.hazmat_paa_plate, ModItems.hazmat_paa_legs, ModItems.hazmat_paa_boots) ||
				checkArmor(player, ModItems.liquidator_helmet, ModItems.liquidator_plate, ModItems.liquidator_legs, ModItems.liquidator_boots);
	}

	public static boolean checkForHazmat(LivingEntity player) {
		if (checkArmor(player, ModItems.hazmat_helmet, ModItems.hazmat_plate, ModItems.hazmat_legs, ModItems.hazmat_boots) ||
				checkArmor(player, ModItems.hazmat_helmet_red, ModItems.hazmat_plate_red, ModItems.hazmat_legs_red, ModItems.hazmat_boots_red) ||
				checkArmor(player, ModItems.hazmat_helmet_grey, ModItems.hazmat_plate_grey, ModItems.hazmat_legs_grey, ModItems.hazmat_boots_grey) ||
				checkArmor(player, ModItems.schrabidium_helmet, ModItems.schrabidium_plate, ModItems.schrabidium_legs, ModItems.schrabidium_boots) ||
				checkForHaz2(player)) {
			return true;
		}

		return HbmPotion.mutation != null && player.hasEffect(HbmPotion.mutation);
	}

	public static boolean checkForAsbestos(LivingEntity player) {
		return checkArmor(player, ModItems.asbestos_helmet, ModItems.asbestos_plate, ModItems.asbestos_legs, ModItems.asbestos_boots);
	}

	public static boolean checkArmor(LivingEntity player, Item helm, Item chest, Item leg, Item shoe) {
		if (helm == null || chest == null || leg == null || shoe == null) return false;
		return player.getItemBySlot(EquipmentSlot.FEET).getItem() == shoe &&
				player.getItemBySlot(EquipmentSlot.LEGS).getItem() == leg &&
				player.getItemBySlot(EquipmentSlot.CHEST).getItem() == chest &&
				player.getItemBySlot(EquipmentSlot.HEAD).getItem() == helm;
	}

	public static boolean checkForDigamma(Player player) {
		if (checkArmor(player, ModItems.fau_helmet, ModItems.fau_plate, ModItems.fau_legs, ModItems.fau_boots))
			return true;

		if (checkArmor(player, ModItems.dns_helmet, ModItems.dns_plate, ModItems.dns_legs, ModItems.dns_boots))
			return true;

		return HbmPotion.stability != null && player.hasEffect(HbmPotion.stability);
	}

	public static final String FILTER_KEY = "hfrFilter";

	public static void damageGasMaskFilter(LivingEntity entity, int damage) {
		ItemStack mask = entity.getItemBySlot(EquipmentSlot.HEAD);
		if (mask.isEmpty() || !(mask.getItem() instanceof IGasMask)) return;
		damageGasMaskFilter(mask, damage);
	}

	public static void damageGasMaskFilter(ItemStack mask, int damage) {
		ItemStack filter = getGasMaskFilter(mask);
		if (filter.isEmpty() || filter.getMaxDamage() == 0) return;

		int cur = filter.getDamageValue();
		if (cur + damage >= filter.getMaxDamage()) {
			removeFilter(mask);
		} else {
			filter.setDamageValue(cur + damage);
			installGasMaskFilter(mask, filter);
		}
	}

	public static void installGasMaskFilter(ItemStack mask, ItemStack filter) {
		if (mask.isEmpty() || filter.isEmpty()) return;
		CustomData customData = mask.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
		CompoundTag tag = customData.copyTag();
		CompoundTag filterTag = new CompoundTag();
		filter.save(mask.getItem().getName(mask).hashCode() == 0 ? null : null, filterTag); // or simple manual serialization
		tag.putString("FilterItem", filter.getItem().toString());
		tag.putInt("FilterDamage", filter.getDamageValue());
		tag.putInt("FilterMaxDamage", filter.getMaxDamage());
		mask.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
	}

	public static void removeFilter(ItemStack mask) {
		if (mask.isEmpty()) return;
		CustomData customData = mask.get(DataComponents.CUSTOM_DATA);
		if (customData != null) {
			CompoundTag tag = customData.copyTag();
			tag.remove("FilterItem");
			tag.remove("FilterDamage");
			tag.remove("FilterMaxDamage");
			mask.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
		}
	}

	public static ItemStack getGasMaskFilter(ItemStack mask) {
		if (mask.isEmpty()) return ItemStack.EMPTY;
		CustomData customData = mask.get(DataComponents.CUSTOM_DATA);
		if (customData != null && customData.contains("FilterItem")) {
			ItemStack filter = new ItemStack(ModItems.filter_coal);

			return filter;
		}
		return ItemStack.EMPTY;
	}
}
