package com.hbm.handler;

import com.hbm.items.ModItems;
import com.hbm.potion.HbmPotion;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.HashMap;

public class HazmatRegistry {

	public static double helmet = 0.2D;
	public static double chest = 0.4D;
	public static double legs = 0.3D;
	public static double boots = 0.1D;

	private static final HashMap<Item, Double> entries = new HashMap<>();

	static {
		initDefault();
	}

	public static void initDefault() {
		double iron = 0.0225D; // 5%
		double gold = 0.0225D; // 5%
		double steel = 0.045D; // 10%
		double titanium = 0.045D; // 10%
		double alloy = 0.07D; // 15%
		double cobalt = 0.125D; // 25%

		double hazYellow = 0.6D; // 50%
		double hazRed = 1.0D; // 90%
		double hazGray = 2D; // 99%
		double paa = 1.7D; // 97%
		double liquidator = 2.4D; // 99.6%

		double security = 0.825D; // 85%
		double star = 1D; // 90%
		double cmb = 1.3D; // 95%
		double schrab = 3D; // 99.9%
		double euph = 10D; // <100%

		registerHazmat(ModItems.hazmat_helmet, hazYellow * helmet);
		registerHazmat(ModItems.hazmat_plate, hazYellow * chest);
		registerHazmat(ModItems.hazmat_legs, hazYellow * legs);
		registerHazmat(ModItems.hazmat_boots, hazYellow * boots);

		registerHazmat(ModItems.hazmat_helmet_red, hazRed * helmet);
		registerHazmat(ModItems.hazmat_plate_red, hazRed * chest);
		registerHazmat(ModItems.hazmat_legs_red, hazRed * legs);
		registerHazmat(ModItems.hazmat_boots_red, hazRed * boots);

		registerHazmat(ModItems.hazmat_helmet_grey, hazGray * helmet);
		registerHazmat(ModItems.hazmat_plate_grey, hazGray * chest);
		registerHazmat(ModItems.hazmat_legs_grey, hazGray * legs);
		registerHazmat(ModItems.hazmat_boots_grey, hazGray * boots);

		registerHazmat(ModItems.liquidator_helmet, liquidator * helmet);
		registerHazmat(ModItems.liquidator_plate, liquidator * chest);
		registerHazmat(ModItems.liquidator_legs, liquidator * legs);
		registerHazmat(ModItems.liquidator_boots, liquidator * boots);

		registerHazmat(ModItems.paa_plate, paa * chest);
		registerHazmat(ModItems.paa_legs, paa * legs);
		registerHazmat(ModItems.paa_boots, paa * boots);

		registerHazmat(ModItems.hazmat_paa_helmet, paa * helmet);
		registerHazmat(ModItems.hazmat_paa_plate, paa * chest);
		registerHazmat(ModItems.hazmat_paa_legs, paa * legs);
		registerHazmat(ModItems.hazmat_paa_boots, paa * boots);

		registerHazmat(ModItems.security_helmet, security * helmet);
		registerHazmat(ModItems.security_plate, security * chest);
		registerHazmat(ModItems.security_legs, security * legs);
		registerHazmat(ModItems.security_boots, security * boots);

		registerHazmat(ModItems.starmetal_helmet, star * helmet);
		registerHazmat(ModItems.starmetal_plate, star * chest);
		registerHazmat(ModItems.starmetal_legs, star * legs);
		registerHazmat(ModItems.starmetal_boots, star * boots);

		registerHazmat(ModItems.jackt, 0.1);
		registerHazmat(ModItems.jackt2, 0.1);

		registerHazmat(ModItems.gas_mask, 0.07);
		registerHazmat(ModItems.gas_mask_m65, 0.095);

		registerHazmat(ModItems.steel_helmet, steel * helmet);
		registerHazmat(ModItems.steel_plate, steel * chest);
		registerHazmat(ModItems.steel_legs, steel * legs);
		registerHazmat(ModItems.steel_boots, steel * boots);

		registerHazmat(ModItems.titanium_helmet, titanium * helmet);
		registerHazmat(ModItems.titanium_plate, titanium * chest);
		registerHazmat(ModItems.titanium_legs, titanium * legs);
		registerHazmat(ModItems.titanium_boots, titanium * boots);

		registerHazmat(ModItems.cobalt_helmet, cobalt * helmet);
		registerHazmat(ModItems.cobalt_plate, cobalt * chest);
		registerHazmat(ModItems.cobalt_legs, cobalt * legs);
		registerHazmat(ModItems.cobalt_boots, cobalt * boots);

		registerHazmat(Items.IRON_HELMET, iron * helmet);
		registerHazmat(Items.IRON_CHESTPLATE, iron * chest);
		registerHazmat(Items.IRON_LEGGINGS, iron * legs);
		registerHazmat(Items.IRON_BOOTS, iron * boots);

		registerHazmat(Items.GOLDEN_HELMET, gold * helmet);
		registerHazmat(Items.GOLDEN_CHESTPLATE, gold * chest);
		registerHazmat(Items.GOLDEN_LEGGINGS, gold * legs);
		registerHazmat(Items.GOLDEN_BOOTS, gold * boots);

		registerHazmat(ModItems.alloy_helmet, alloy * helmet);
		registerHazmat(ModItems.alloy_plate, alloy * chest);
		registerHazmat(ModItems.alloy_legs, alloy * legs);
		registerHazmat(ModItems.alloy_boots, alloy * boots);

		registerHazmat(ModItems.cmb_helmet, cmb * helmet);
		registerHazmat(ModItems.cmb_plate, cmb * chest);
		registerHazmat(ModItems.cmb_legs, cmb * legs);
		registerHazmat(ModItems.cmb_boots, cmb * boots);

		registerHazmat(ModItems.schrabidium_helmet, schrab * helmet);
		registerHazmat(ModItems.schrabidium_plate, schrab * chest);
		registerHazmat(ModItems.schrabidium_legs, schrab * legs);
		registerHazmat(ModItems.schrabidium_boots, schrab * boots);

		registerHazmat(ModItems.euphemium_helmet, euph * helmet);
		registerHazmat(ModItems.euphemium_plate, euph * chest);
		registerHazmat(ModItems.euphemium_legs, euph * legs);
		registerHazmat(ModItems.euphemium_boots, euph * boots);
	}

	public static void registerHazmat(Item item, double resistance) {
		if (item != null) {
			entries.put(item, resistance);
		}
	}

	public static double getResistance(ItemStack stack) {
		if (stack == null || stack.isEmpty()) return 0;
		Double f = entries.get(stack.getItem());
		return f != null ? f : 0.0D;
	}

	public static float getResistance(LivingEntity player) {
		float res = 0.0F;

		if (player instanceof Player plr) {
			for (ItemStack armor : plr.getArmorSlots()) {
				res += (float) getResistance(armor);
			}
		}

		if (HbmPotion.radx != null && player.hasEffect(HbmPotion.radx)) {
			res += 0.2F;
		}

		return res;
	}
}
