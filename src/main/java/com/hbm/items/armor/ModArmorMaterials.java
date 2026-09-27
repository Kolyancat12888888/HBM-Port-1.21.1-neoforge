package com.hbm.items.armor;

import com.hbm.main.MainRegistry;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;

public class ModArmorMaterials {

	public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS =
			DeferredRegister.create(Registries.ARMOR_MATERIAL, MainRegistry.MODID);

	private static DeferredHolder<ArmorMaterial, ArmorMaterial> register(String name, int defenseBoots, int defenseLegs, int defenseChest, int defenseHelmet, int enchantability, float toughness, float knockbackResistance) {
		return ARMOR_MATERIALS.register(name, () -> new ArmorMaterial(
				Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
					map.put(ArmorItem.Type.BOOTS, defenseBoots);
					map.put(ArmorItem.Type.LEGGINGS, defenseLegs);
					map.put(ArmorItem.Type.CHESTPLATE, defenseChest);
					map.put(ArmorItem.Type.HELMET, defenseHelmet);
					map.put(ArmorItem.Type.BODY, defenseChest);
				}),
				enchantability,
				SoundEvents.ARMOR_EQUIP_GENERIC,
				() -> Ingredient.EMPTY,
				List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(MainRegistry.MODID, name))),
				toughness,
				knockbackResistance
		));
	}

	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> T51 = register("t51", 3, 6, 8, 3, 0, 2.0F, 0.1F);
	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> BJ = register("bj", 3, 6, 8, 3, 0, 2.0F, 0.1F);
	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> AJR = register("ajr", 3, 6, 8, 3, 0, 2.0F, 0.1F);
	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> RPA = register("rpa", 3, 6, 8, 3, 100, 2.0F, 0.2F);
	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> HEV = register("hev", 3, 6, 8, 3, 0, 2.0F, 0.0F);
	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> HAZMAT = register("hazmat", 2, 5, 4, 1, 5, 0.0F, 0.0F);
	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> HAZMAT2 = register("hazmat2", 2, 5, 4, 1, 5, 0.0F, 0.0F);
	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> HAZMAT3 = register("hazmat3", 2, 5, 4, 1, 5, 0.0F, 0.0F);
	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> PAA = register("paa", 3, 6, 8, 3, 25, 2.0F, 0.1F);
	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> SCHRABIDIUM = register("schrabidium", 3, 6, 8, 3, 50, 2.0F, 0.2F);
	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> EUPHEMIUM = register("euphemium", 3, 6, 8, 3, 100, 2.0F, 1.0F);
	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> STEEL = register("steel", 3, 6, 8, 3, 5, 0.0F, 0.0F);
	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> TITANIUM = register("titanium", 3, 6, 8, 3, 9, 2.0F, 0.05F);
	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> ALLOY = register("alloy", 3, 6, 8, 3, 12, 0.0F, 0.0F);
	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> CMB = register("cmb", 3, 6, 8, 3, 50, 2.0F, 0.1F);
	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> COBALT = register("cobalt", 3, 6, 8, 3, 25, 2.0F, 0.1F);
	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> STARMETAL = register("starmetal", 3, 6, 8, 3, 100, 2.0F, 0.1F);
	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> LIQUIDATOR = register("liquidator", 3, 6, 8, 3, 10, 2.0F, 0.1F);
	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> ASBESTOS = register("asbestos", 1, 4, 3, 1, 5, 0.0F, 0.0F);
	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> SECURITY = register("security", 3, 6, 8, 3, 15, 2.0F, 0.05F);
	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> FAU = register("fau", 3, 6, 8, 3, 0, 2.0F, 0.2F);
	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> DNS = register("dns", 3, 6, 8, 3, 0, 2.0F, 0.2F);

	public static void register(IEventBus bus) {
		ARMOR_MATERIALS.register(bus);
	}
}
