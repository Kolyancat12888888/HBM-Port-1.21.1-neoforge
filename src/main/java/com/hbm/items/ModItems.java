package com.hbm.items;

import com.hbm.items.machine.*;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.items.special.*;
import com.hbm.items.tool.*;
import com.hbm.main.MainRegistry;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ModItems {

	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MainRegistry.MODID);
	public static final List<Item> ALL_ITEMS = new ArrayList<>();

	private static <T extends Item> DeferredItem<T> register(String name, Supplier<T> supplier) {
		return ITEMS.register(name, () -> {
			T item = supplier.get();
			ALL_ITEMS.add(item);
			return item;
		});
	}

	private static DeferredItem<ItemBase> reg(String name) {
		return register(name, () -> new ItemBase(new Item.Properties()));
	}

	// Diagnostic & Radiation Tools
	public static final DeferredItem<ItemDosimeter> DOSIMETER = register("dosimeter", ItemDosimeter::new);
	public static final DeferredItem<ItemGeigerCounter> GEIGER_COUNTER = register("geiger_counter", ItemGeigerCounter::new);
	public static final DeferredItem<ItemDigammaDiagnostic> DIGAMMA_DIAGNOSTIC = register("digamma_diagnostic", ItemDigammaDiagnostic::new);
	public static final DeferredItem<ItemLungDiagnostic> LUNG_DIAGNOSTIC = register("lung_diagnostic", ItemLungDiagnostic::new);
	public static final DeferredItem<ItemSurveyScanner> SURVEY_SCANNER = register("survey_scanner", ItemSurveyScanner::new);
	public static final DeferredItem<ItemOreDensityScanner> ORE_DENSITY_SCANNER = register("ore_density_scanner", ItemOreDensityScanner::new);
	public static final DeferredItem<ItemDyatlov> DYATLOV = register("dyatlov", ItemDyatlov::new);

	// Special Drops & Materials
	public static final DeferredItem<ItemBase> PELLET_ANTIMATTER = reg("pellet_antimatter");
	public static final DeferredItem<ItemBase> SINGULARITY = reg("singularity");
	public static final DeferredItem<ItemBase> SINGULARITY_COUNTER_RESONANT = reg("singularity_counter_resonant");
	public static final DeferredItem<ItemBase> SINGULARITY_SUPER_HEATED = reg("singularity_super_heated");
	public static final DeferredItem<ItemBase> BLACK_HOLE = reg("black_hole");
	public static final DeferredItem<ItemBase> DETONATOR_DEADMAN = reg("detonator_deadman");
	public static final DeferredItem<ItemBase> DETONATOR_DE = reg("detonator_de");

	// Spawners & Easter Eggs
	public static final DeferredItem<ItemChopper> SPAWN_CHOPPER = register("spawn_chopper", ItemChopper::new);
	public static final DeferredItem<ItemBase> SPAWN_WORM = reg("spawn_worm");
	public static final DeferredItem<ItemBase> SPAWN_UFO = reg("spawn_ufo");
	public static final DeferredItem<ItemBase> SPAWN_DUCK = reg("spawn_duck");
	public static final DeferredItem<ItemPolaroid> POLAROID = register("polaroid", ItemPolaroid::new);
	public static final DeferredItem<ItemBase> GLITCH = reg("glitch");

	// Kits & Crates
	public static final DeferredItem<ItemKitCustom> KIT_CUSTOM = register("kit_custom", ItemKitCustom::new);
	public static final DeferredItem<ItemKitNBT> KIT_NBT = register("kit_nbt", ItemKitNBT::new);
	public static final DeferredItem<ItemLootCrate> LOOT_10 = register("loot_10", ItemLootCrate::new);
	public static final DeferredItem<ItemLootCrate> LOOT_15 = register("loot_15", ItemLootCrate::new);
	public static final DeferredItem<ItemLootCrate> LOOT_MISC = register("loot_misc", ItemLootCrate::new);
	public static final DeferredItem<ItemStarterKit> NUKE_STARTER_KIT = register("nuke_starter_kit", ItemStarterKit::new);
	public static final DeferredItem<ItemStarterKit> NUKE_ADVANCED_KIT = register("nuke_advanced_kit", ItemStarterKit::new);
	public static final DeferredItem<ItemStarterKit> GADGET_KIT = register("gadget_kit", ItemStarterKit::new);
	public static final DeferredItem<ItemStarterKit> BOY_KIT = register("boy_kit", ItemStarterKit::new);
	public static final DeferredItem<ItemStarterKit> MAN_KIT = register("man_kit", ItemStarterKit::new);
	public static final DeferredItem<ItemStarterKit> MIKE_KIT = register("mike_kit", ItemStarterKit::new);
	public static final DeferredItem<ItemStarterKit> TSAR_KIT = register("tsar_kit", ItemStarterKit::new);
	public static final DeferredItem<ItemStarterKit> HAZMAT_KIT = register("hazmat_kit", ItemStarterKit::new);
	public static final DeferredItem<ItemStarterKit> HAZMAT_RED_KIT = register("hazmat_red_kit", ItemStarterKit::new);
	public static final DeferredItem<ItemStarterKit> HAZMAT_GREY_KIT = register("hazmat_grey_kit", ItemStarterKit::new);
	public static final DeferredItem<ItemStarterKit> STEALTH_BOY = register("stealth_boy", ItemStarterKit::new);
	public static final DeferredItem<ItemStarterKit> EUPHEMIUM_KIT = register("euphemium_kit", ItemStarterKit::new);
	public static final DeferredItem<ItemStarterKit> PROTOTYPE_KIT = register("prototype_kit", ItemStarterKit::new);
	public static final DeferredItem<ItemStarterKit> FLEIJA_KIT = register("fleija_kit", ItemStarterKit::new);
	public static final DeferredItem<ItemStarterKit> SOLINIUM_KIT = register("solinium_kit", ItemStarterKit::new);
	public static final DeferredItem<ItemStarterKit> BALEFIRE_KIT = register("balefire_kit", ItemStarterKit::new);
	public static final DeferredItem<ItemStarterKit> GRENADE_KIT = register("grenade_kit", ItemStarterKit::new);
	public static final DeferredItem<ItemStarterKit> MISSILE_KIT = register("missile_kit", ItemStarterKit::new);
	public static final DeferredItem<ItemStarterKit> T45_KIT = register("t45_kit", ItemStarterKit::new);
	public static final DeferredItem<ItemStarterKit> MULTI_KIT = register("multi_kit", ItemStarterKit::new);

	// Fuels, Powders & Dust
	public static final DeferredItem<ItemFuel> DUST = register("dust", () -> new ItemFuel("dust", 1600));
	public static final DeferredItem<ItemFuel> POWDER_FIRE = register("powder_fire", () -> new ItemFuel("powder_fire", 3200));

	// Materials, Ingots & Powders
	public static final DeferredItem<ItemBase> INGOT_URANIUM = reg("ingot_uranium");
	public static final DeferredItem<ItemBase> POWDER_YELLOWCAKE = reg("powder_yellowcake");
	public static final DeferredItem<ItemBase> POWDER_PLUTONIUM = reg("powder_plutonium");
	public static final DeferredItem<ItemBase> INGOT_STEEL = reg("ingot_steel");
	public static final DeferredItem<ItemBase> INGOT_LEAD = reg("ingot_lead");
	public static final DeferredItem<ItemBase> INGOT_COPPER = reg("ingot_copper");
	public static final DeferredItem<ItemBase> INGOT_TUNGSTEN = reg("ingot_tungsten");
	public static final DeferredItem<ItemBase> INGOT_POLYMER = reg("ingot_polymer");
	public static final DeferredItem<ItemBase> PELLET_RTG = reg("pellet_rtg");
	public static final DeferredItem<ItemBase> CELL = reg("cell");
	public static final DeferredItem<ItemBase> ROD_EMPTY = reg("rod_empty");
	public static final DeferredItem<ItemBase> TEMPLATE_FOLDER = reg("template_folder");
	public static final DeferredItem<ItemBase> RADAWAY = reg("radaway");
	public static final DeferredItem<ItemBase> RADAWAY_STRONG = reg("radaway_strong");
	public static final DeferredItem<ItemBase> RADX = reg("radx");
	public static final DeferredItem<ItemBase> PILL_IODINE = reg("pill_iodine");

	// Bomb Parts
	public static final DeferredItem<ItemBase> EARLY_EXPLOSIVE_LENSES = reg("early_explosive_lenses");
	public static final DeferredItem<ItemBase> EXPLOSIVE_LENSES = reg("explosive_lenses");
	public static final DeferredItem<ItemBase> GADGET_WIREING = reg("gadget_wireing");
	public static final DeferredItem<ItemBase> GADGET_CORE = reg("gadget_core");
	public static final DeferredItem<ItemBase> BOY_SHIELDING = reg("boy_shielding");
	public static final DeferredItem<ItemBase> BOY_TARGET = reg("boy_target");
	public static final DeferredItem<ItemBase> BOY_BULLET = reg("boy_bullet");
	public static final DeferredItem<ItemBase> BOY_PROPELLANT = reg("boy_propellant");
	public static final DeferredItem<ItemBase> BOY_IGNITER = reg("boy_igniter");
	public static final DeferredItem<ItemBase> MAN_IGNITER = reg("man_igniter");
	public static final DeferredItem<ItemBase> MAN_CORE = reg("man_core");
	public static final DeferredItem<ItemBase> MIKE_CORE = reg("mike_core");
	public static final DeferredItem<ItemBase> MIKE_DEUT = reg("mike_deut");
	public static final DeferredItem<ItemBase> MIKE_COOLING_UNIT = reg("mike_cooling_unit");
	public static final DeferredItem<ItemBase> TSAR_CORE = reg("tsar_core");

	// Armor pieces - Hazmat & Power Armors
	public static final DeferredItem<com.hbm.items.gear.ArmorGasMask> GAS_MASK = register("gas_mask", () -> new com.hbm.items.gear.ArmorGasMask(com.hbm.items.armor.ModArmorMaterials.HAZMAT, net.minecraft.world.item.ArmorItem.Type.HELMET, new Item.Properties(), false));
	public static final DeferredItem<com.hbm.items.gear.ArmorGasMask> GAS_MASK_M65 = register("gas_mask_m65", () -> new com.hbm.items.gear.ArmorGasMask(com.hbm.items.armor.ModArmorMaterials.HAZMAT, net.minecraft.world.item.ArmorItem.Type.HELMET, new Item.Properties(), true));
	public static final DeferredItem<com.hbm.items.gear.ModArmor> JACKT = register("jackt", () -> new com.hbm.items.gear.ModArmor(com.hbm.items.armor.ModArmorMaterials.STEEL, net.minecraft.world.item.ArmorItem.Type.CHESTPLATE, new Item.Properties()));
	public static final DeferredItem<com.hbm.items.gear.ModArmor> JACKT2 = register("jackt2", () -> new com.hbm.items.gear.ModArmor(com.hbm.items.armor.ModArmorMaterials.STEEL, net.minecraft.world.item.ArmorItem.Type.CHESTPLATE, new Item.Properties()));

	public static final DeferredItem<com.hbm.items.gear.ArmorHazmat> HAZMAT_HELMET = register("hazmat_helmet", () -> new com.hbm.items.gear.ArmorHazmat(com.hbm.items.armor.ModArmorMaterials.HAZMAT, net.minecraft.world.item.ArmorItem.Type.HELMET, new Item.Properties()));
	public static final DeferredItem<com.hbm.items.gear.ArmorHazmat> HAZMAT_PLATE = register("hazmat_plate", () -> new com.hbm.items.gear.ArmorHazmat(com.hbm.items.armor.ModArmorMaterials.HAZMAT, net.minecraft.world.item.ArmorItem.Type.CHESTPLATE, new Item.Properties()));
	public static final DeferredItem<com.hbm.items.gear.ArmorHazmat> HAZMAT_LEGS = register("hazmat_legs", () -> new com.hbm.items.gear.ArmorHazmat(com.hbm.items.armor.ModArmorMaterials.HAZMAT, net.minecraft.world.item.ArmorItem.Type.LEGGINGS, new Item.Properties()));
	public static final DeferredItem<com.hbm.items.gear.ArmorHazmat> HAZMAT_BOOTS = register("hazmat_boots", () -> new com.hbm.items.gear.ArmorHazmat(com.hbm.items.armor.ModArmorMaterials.HAZMAT, net.minecraft.world.item.ArmorItem.Type.BOOTS, new Item.Properties()));

	public static final DeferredItem<com.hbm.items.gear.ArmorHazmat> HAZMAT_HELMET_RED = register("hazmat_helmet_red", () -> new com.hbm.items.gear.ArmorHazmat(com.hbm.items.armor.ModArmorMaterials.HAZMAT2, net.minecraft.world.item.ArmorItem.Type.HELMET, new Item.Properties()));
	public static final DeferredItem<com.hbm.items.gear.ArmorHazmat> HAZMAT_PLATE_RED = register("hazmat_plate_red", () -> new com.hbm.items.gear.ArmorHazmat(com.hbm.items.armor.ModArmorMaterials.HAZMAT2, net.minecraft.world.item.ArmorItem.Type.CHESTPLATE, new Item.Properties()));
	public static final DeferredItem<com.hbm.items.gear.ArmorHazmat> HAZMAT_LEGS_RED = register("hazmat_legs_red", () -> new com.hbm.items.gear.ArmorHazmat(com.hbm.items.armor.ModArmorMaterials.HAZMAT2, net.minecraft.world.item.ArmorItem.Type.LEGGINGS, new Item.Properties()));
	public static final DeferredItem<com.hbm.items.gear.ArmorHazmat> HAZMAT_BOOTS_RED = register("hazmat_boots_red", () -> new com.hbm.items.gear.ArmorHazmat(com.hbm.items.armor.ModArmorMaterials.HAZMAT2, net.minecraft.world.item.ArmorItem.Type.BOOTS, new Item.Properties()));

	public static final DeferredItem<com.hbm.items.gear.ArmorHazmat> HAZMAT_HELMET_GREY = register("hazmat_helmet_grey", () -> new com.hbm.items.gear.ArmorHazmat(com.hbm.items.armor.ModArmorMaterials.HAZMAT3, net.minecraft.world.item.ArmorItem.Type.HELMET, new Item.Properties()));
	public static final DeferredItem<com.hbm.items.gear.ArmorHazmat> HAZMAT_PLATE_GREY = register("hazmat_plate_grey", () -> new com.hbm.items.gear.ArmorHazmat(com.hbm.items.armor.ModArmorMaterials.HAZMAT3, net.minecraft.world.item.ArmorItem.Type.CHESTPLATE, new Item.Properties()));
	public static final DeferredItem<com.hbm.items.gear.ArmorHazmat> HAZMAT_LEGS_GREY = register("hazmat_legs_grey", () -> new com.hbm.items.gear.ArmorHazmat(com.hbm.items.armor.ModArmorMaterials.HAZMAT3, net.minecraft.world.item.ArmorItem.Type.LEGGINGS, new Item.Properties()));
	public static final DeferredItem<com.hbm.items.gear.ArmorHazmat> HAZMAT_BOOTS_GREY = register("hazmat_boots_grey", () -> new com.hbm.items.gear.ArmorHazmat(com.hbm.items.armor.ModArmorMaterials.HAZMAT3, net.minecraft.world.item.ArmorItem.Type.BOOTS, new Item.Properties()));

	public static final DeferredItem<com.hbm.items.gear.ArmorHazmat> HAZMAT_PAA_HELMET = register("hazmat_paa_helmet", () -> new com.hbm.items.gear.ArmorHazmat(com.hbm.items.armor.ModArmorMaterials.PAA, net.minecraft.world.item.ArmorItem.Type.HELMET, new Item.Properties()));
	public static final DeferredItem<com.hbm.items.gear.ArmorHazmat> HAZMAT_PAA_PLATE = register("hazmat_paa_plate", () -> new com.hbm.items.gear.ArmorHazmat(com.hbm.items.armor.ModArmorMaterials.PAA, net.minecraft.world.item.ArmorItem.Type.CHESTPLATE, new Item.Properties()));
	public static final DeferredItem<com.hbm.items.gear.ArmorHazmat> HAZMAT_PAA_LEGS = register("hazmat_paa_legs", () -> new com.hbm.items.gear.ArmorHazmat(com.hbm.items.armor.ModArmorMaterials.PAA, net.minecraft.world.item.ArmorItem.Type.LEGGINGS, new Item.Properties()));
	public static final DeferredItem<com.hbm.items.gear.ArmorHazmat> HAZMAT_PAA_BOOTS = register("hazmat_paa_boots", () -> new com.hbm.items.gear.ArmorHazmat(com.hbm.items.armor.ModArmorMaterials.PAA, net.minecraft.world.item.ArmorItem.Type.BOOTS, new Item.Properties()));
	public static final DeferredItem<com.hbm.items.gear.ModArmor> PAA_PLATE = register("paa_plate", () -> new com.hbm.items.gear.ModArmor(com.hbm.items.armor.ModArmorMaterials.PAA, net.minecraft.world.item.ArmorItem.Type.CHESTPLATE, new Item.Properties()));
	public static final DeferredItem<com.hbm.items.gear.ModArmor> PAA_LEGS = register("paa_legs", () -> new com.hbm.items.gear.ModArmor(com.hbm.items.armor.ModArmorMaterials.PAA, net.minecraft.world.item.ArmorItem.Type.LEGGINGS, new Item.Properties()));
	public static final DeferredItem<com.hbm.items.gear.ModArmor> PAA_BOOTS = register("paa_boots", () -> new com.hbm.items.gear.ModArmor(com.hbm.items.armor.ModArmorMaterials.PAA, net.minecraft.world.item.ArmorItem.Type.BOOTS, new Item.Properties()));

	public static final DeferredItem<com.hbm.items.gear.ModArmor> LIQUIDATOR_HELMET = register("liquidator_helmet", () -> new com.hbm.items.gear.ModArmor(com.hbm.items.armor.ModArmorMaterials.LIQUIDATOR, net.minecraft.world.item.ArmorItem.Type.HELMET, new Item.Properties()));
	public static final DeferredItem<com.hbm.items.gear.ModArmor> LIQUIDATOR_PLATE = register("liquidator_plate", () -> new com.hbm.items.gear.ModArmor(com.hbm.items.armor.ModArmorMaterials.LIQUIDATOR, net.minecraft.world.item.ArmorItem.Type.CHESTPLATE, new Item.Properties()));
	public static final DeferredItem<com.hbm.items.gear.ModArmor> LIQUIDATOR_LEGS = register("liquidator_legs", () -> new com.hbm.items.gear.ModArmor(com.hbm.items.armor.ModArmorMaterials.LIQUIDATOR, net.minecraft.world.item.ArmorItem.Type.LEGGINGS, new Item.Properties()));
	public static final DeferredItem<com.hbm.items.gear.ModArmor> LIQUIDATOR_BOOTS = register("liquidator_boots", () -> new com.hbm.items.gear.ModArmor(com.hbm.items.armor.ModArmorMaterials.LIQUIDATOR, net.minecraft.world.item.ArmorItem.Type.BOOTS, new Item.Properties()));

	public static final DeferredItem<com.hbm.items.gear.ModArmor> SECURITY_HELMET = register("security_helmet", () -> new com.hbm.items.gear.ModArmor(com.hbm.items.armor.ModArmorMaterials.SECURITY, net.minecraft.world.item.ArmorItem.Type.HELMET, new Item.Properties()));
	public static final DeferredItem<com.hbm.items.gear.ModArmor> SECURITY_PLATE = register("security_plate", () -> new com.hbm.items.gear.ModArmor(com.hbm.items.armor.ModArmorMaterials.SECURITY, net.minecraft.world.item.ArmorItem.Type.CHESTPLATE, new Item.Properties()));
	public static final DeferredItem<com.hbm.items.gear.ModArmor> SECURITY_LEGS = register("security_legs", () -> new com.hbm.items.gear.ModArmor(com.hbm.items.armor.ModArmorMaterials.SECURITY, net.minecraft.world.item.ArmorItem.Type.LEGGINGS, new Item.Properties()));
	public static final DeferredItem<com.hbm.items.gear.ModArmor> SECURITY_BOOTS = register("security_boots", () -> new com.hbm.items.gear.ModArmor(com.hbm.items.armor.ModArmorMaterials.SECURITY, net.minecraft.world.item.ArmorItem.Type.BOOTS, new Item.Properties()));

	public static final DeferredItem<com.hbm.items.gear.ModArmor> STARMETAL_HELMET = register("starmetal_helmet", () -> new com.hbm.items.gear.ModArmor(com.hbm.items.armor.ModArmorMaterials.STARMETAL, net.minecraft.world.item.ArmorItem.Type.HELMET, new Item.Properties()));
	public static final DeferredItem<com.hbm.items.gear.ModArmor> STARMETAL_PLATE = register("starmetal_plate", () -> new com.hbm.items.gear.ModArmor(com.hbm.items.armor.ModArmorMaterials.STARMETAL, net.minecraft.world.item.ArmorItem.Type.CHESTPLATE, new Item.Properties()));
	public static final DeferredItem<com.hbm.items.gear.ModArmor> STARMETAL_LEGS = register("starmetal_legs", () -> new com.hbm.items.gear.ModArmor(com.hbm.items.armor.ModArmorMaterials.STARMETAL, net.minecraft.world.item.ArmorItem.Type.LEGGINGS, new Item.Properties()));
	public static final DeferredItem<com.hbm.items.gear.ModArmor> STARMETAL_BOOTS = register("starmetal_boots", () -> new com.hbm.items.gear.ModArmor(com.hbm.items.armor.ModArmorMaterials.STARMETAL, net.minecraft.world.item.ArmorItem.Type.BOOTS, new Item.Properties()));

	public static final DeferredItem<com.hbm.items.gear.ModArmor> STEEL_HELMET = register("steel_helmet", () -> new com.hbm.items.gear.ModArmor(com.hbm.items.armor.ModArmorMaterials.STEEL, net.minecraft.world.item.ArmorItem.Type.HELMET, new Item.Properties()));
	public static final DeferredItem<com.hbm.items.gear.ModArmor> STEEL_PLATE = register("steel_plate", () -> new com.hbm.items.gear.ModArmor(com.hbm.items.armor.ModArmorMaterials.STEEL, net.minecraft.world.item.ArmorItem.Type.CHESTPLATE, new Item.Properties()));
	public static final DeferredItem<com.hbm.items.gear.ModArmor> STEEL_LEGS = register("steel_legs", () -> new com.hbm.items.gear.ModArmor(com.hbm.items.armor.ModArmorMaterials.STEEL, net.minecraft.world.item.ArmorItem.Type.LEGGINGS, new Item.Properties()));
	public static final DeferredItem<com.hbm.items.gear.ModArmor> STEEL_BOOTS = register("steel_boots", () -> new com.hbm.items.gear.ModArmor(com.hbm.items.armor.ModArmorMaterials.STEEL, net.minecraft.world.item.ArmorItem.Type.BOOTS, new Item.Properties()));

	public static final DeferredItem<com.hbm.items.gear.ModArmor> TITANIUM_HELMET = register("titanium_helmet", () -> new com.hbm.items.gear.ModArmor(com.hbm.items.armor.ModArmorMaterials.TITANIUM, net.minecraft.world.item.ArmorItem.Type.HELMET, new Item.Properties()));
	public static final DeferredItem<com.hbm.items.gear.ModArmor> TITANIUM_PLATE = register("titanium_plate", () -> new com.hbm.items.gear.ModArmor(com.hbm.items.armor.ModArmorMaterials.TITANIUM, net.minecraft.world.item.ArmorItem.Type.CHESTPLATE, new Item.Properties()));
	public static final DeferredItem<com.hbm.items.gear.ModArmor> TITANIUM_LEGS = register("titanium_legs", () -> new com.hbm.items.gear.ModArmor(com.hbm.items.armor.ModArmorMaterials.TITANIUM, net.minecraft.world.item.ArmorItem.Type.LEGGINGS, new Item.Properties()));
	public static final DeferredItem<com.hbm.items.gear.ModArmor> TITANIUM_BOOTS = register("titanium_boots", () -> new com.hbm.items.gear.ModArmor(com.hbm.items.armor.ModArmorMaterials.TITANIUM, net.minecraft.world.item.ArmorItem.Type.BOOTS, new Item.Properties()));

	public static final DeferredItem<com.hbm.items.gear.ModArmor> COBALT_HELMET = register("cobalt_helmet", () -> new com.hbm.items.gear.ModArmor(com.hbm.items.armor.ModArmorMaterials.COBALT, net.minecraft.world.item.ArmorItem.Type.HELMET, new Item.Properties()));
	public static final DeferredItem<com.hbm.items.gear.ModArmor> COBALT_PLATE = register("cobalt_plate", () -> new com.hbm.items.gear.ModArmor(com.hbm.items.armor.ModArmorMaterials.COBALT, net.minecraft.world.item.ArmorItem.Type.CHESTPLATE, new Item.Properties()));
	public static final DeferredItem<com.hbm.items.gear.ModArmor> COBALT_LEGS = register("cobalt_legs", () -> new com.hbm.items.gear.ModArmor(com.hbm.items.armor.ModArmorMaterials.COBALT, net.minecraft.world.item.ArmorItem.Type.LEGGINGS, new Item.Properties()));
	public static final DeferredItem<com.hbm.items.gear.ModArmor> COBALT_BOOTS = register("cobalt_boots", () -> new com.hbm.items.gear.ModArmor(com.hbm.items.armor.ModArmorMaterials.COBALT, net.minecraft.world.item.ArmorItem.Type.BOOTS, new Item.Properties()));

	public static final DeferredItem<com.hbm.items.gear.ModArmor> ALLOY_HELMET = register("alloy_helmet", () -> new com.hbm.items.gear.ModArmor(com.hbm.items.armor.ModArmorMaterials.ALLOY, net.minecraft.world.item.ArmorItem.Type.HELMET, new Item.Properties()));
	public static final DeferredItem<com.hbm.items.gear.ModArmor> ALLOY_PLATE = register("alloy_plate", () -> new com.hbm.items.gear.ModArmor(com.hbm.items.armor.ModArmorMaterials.ALLOY, net.minecraft.world.item.ArmorItem.Type.CHESTPLATE, new Item.Properties()));
	public static final DeferredItem<com.hbm.items.gear.ModArmor> ALLOY_LEGS = register("alloy_legs", () -> new com.hbm.items.gear.ModArmor(com.hbm.items.armor.ModArmorMaterials.ALLOY, net.minecraft.world.item.ArmorItem.Type.LEGGINGS, new Item.Properties()));
	public static final DeferredItem<com.hbm.items.gear.ModArmor> ALLOY_BOOTS = register("alloy_boots", () -> new com.hbm.items.gear.ModArmor(com.hbm.items.armor.ModArmorMaterials.ALLOY, net.minecraft.world.item.ArmorItem.Type.BOOTS, new Item.Properties()));

	public static final DeferredItem<com.hbm.items.gear.ModArmor> CMB_HELMET = register("cmb_helmet", () -> new com.hbm.items.gear.ModArmor(com.hbm.items.armor.ModArmorMaterials.CMB, net.minecraft.world.item.ArmorItem.Type.HELMET, new Item.Properties()));
	public static final DeferredItem<com.hbm.items.gear.ModArmor> CMB_PLATE = register("cmb_plate", () -> new com.hbm.items.gear.ModArmor(com.hbm.items.armor.ModArmorMaterials.CMB, net.minecraft.world.item.ArmorItem.Type.CHESTPLATE, new Item.Properties()));
	public static final DeferredItem<com.hbm.items.gear.ModArmor> CMB_LEGS = register("cmb_legs", () -> new com.hbm.items.gear.ModArmor(com.hbm.items.armor.ModArmorMaterials.CMB, net.minecraft.world.item.ArmorItem.Type.LEGGINGS, new Item.Properties()));
	public static final DeferredItem<com.hbm.items.gear.ModArmor> CMB_BOOTS = register("cmb_boots", () -> new com.hbm.items.gear.ModArmor(com.hbm.items.armor.ModArmorMaterials.CMB, net.minecraft.world.item.ArmorItem.Type.BOOTS, new Item.Properties()));

	public static final DeferredItem<com.hbm.items.armor.ArmorSchrabidium> SCHRABIDIUM_HELMET = register("schrabidium_helmet", () -> new com.hbm.items.armor.ArmorSchrabidium(com.hbm.items.armor.ModArmorMaterials.SCHRABIDIUM, net.minecraft.world.item.ArmorItem.Type.HELMET, new Item.Properties()));
	public static final DeferredItem<com.hbm.items.armor.ArmorSchrabidium> SCHRABIDIUM_PLATE = register("schrabidium_plate", () -> new com.hbm.items.armor.ArmorSchrabidium(com.hbm.items.armor.ModArmorMaterials.SCHRABIDIUM, net.minecraft.world.item.ArmorItem.Type.CHESTPLATE, new Item.Properties()));
	public static final DeferredItem<com.hbm.items.armor.ArmorSchrabidium> SCHRABIDIUM_LEGS = register("schrabidium_legs", () -> new com.hbm.items.armor.ArmorSchrabidium(com.hbm.items.armor.ModArmorMaterials.SCHRABIDIUM, net.minecraft.world.item.ArmorItem.Type.LEGGINGS, new Item.Properties()));
	public static final DeferredItem<com.hbm.items.armor.ArmorSchrabidium> SCHRABIDIUM_BOOTS = register("schrabidium_boots", () -> new com.hbm.items.armor.ArmorSchrabidium(com.hbm.items.armor.ModArmorMaterials.SCHRABIDIUM, net.minecraft.world.item.ArmorItem.Type.BOOTS, new Item.Properties()));

	public static final DeferredItem<com.hbm.items.armor.ArmorEuphemium> EUPHEMIUM_HELMET = register("euphemium_helmet", () -> new com.hbm.items.armor.ArmorEuphemium(com.hbm.items.armor.ModArmorMaterials.EUPHEMIUM, net.minecraft.world.item.ArmorItem.Type.HELMET, new Item.Properties()));
	public static final DeferredItem<com.hbm.items.armor.ArmorEuphemium> EUPHEMIUM_PLATE = register("euphemium_plate", () -> new com.hbm.items.armor.ArmorEuphemium(com.hbm.items.armor.ModArmorMaterials.EUPHEMIUM, net.minecraft.world.item.ArmorItem.Type.CHESTPLATE, new Item.Properties()));
	public static final DeferredItem<com.hbm.items.armor.ArmorEuphemium> EUPHEMIUM_LEGS = register("euphemium_legs", () -> new com.hbm.items.armor.ArmorEuphemium(com.hbm.items.armor.ModArmorMaterials.EUPHEMIUM, net.minecraft.world.item.ArmorItem.Type.LEGGINGS, new Item.Properties()));
	public static final DeferredItem<com.hbm.items.armor.ArmorEuphemium> EUPHEMIUM_BOOTS = register("euphemium_boots", () -> new com.hbm.items.armor.ArmorEuphemium(com.hbm.items.armor.ModArmorMaterials.EUPHEMIUM, net.minecraft.world.item.ArmorItem.Type.BOOTS, new Item.Properties()));

	public static final DeferredItem<com.hbm.items.gear.ArmorAsbestos> ASBESTOS_HELMET = register("asbestos_helmet", () -> new com.hbm.items.gear.ArmorAsbestos(com.hbm.items.armor.ModArmorMaterials.ASBESTOS, net.minecraft.world.item.ArmorItem.Type.HELMET, new Item.Properties()));
	public static final DeferredItem<com.hbm.items.gear.ArmorAsbestos> ASBESTOS_PLATE = register("asbestos_plate", () -> new com.hbm.items.gear.ArmorAsbestos(com.hbm.items.armor.ModArmorMaterials.ASBESTOS, net.minecraft.world.item.ArmorItem.Type.CHESTPLATE, new Item.Properties()));
	public static final DeferredItem<com.hbm.items.gear.ArmorAsbestos> ASBESTOS_LEGS = register("asbestos_legs", () -> new com.hbm.items.gear.ArmorAsbestos(com.hbm.items.armor.ModArmorMaterials.ASBESTOS, net.minecraft.world.item.ArmorItem.Type.LEGGINGS, new Item.Properties()));
	public static final DeferredItem<com.hbm.items.gear.ArmorAsbestos> ASBESTOS_BOOTS = register("asbestos_boots", () -> new com.hbm.items.gear.ArmorAsbestos(com.hbm.items.armor.ModArmorMaterials.ASBESTOS, net.minecraft.world.item.ArmorItem.Type.BOOTS, new Item.Properties()));

	// Power Armor: RPA (Remnant Power Armor)
	public static final DeferredItem<com.hbm.items.armor.ArmorRPA> RPA_HELMET = register("rpa_helmet", () -> (com.hbm.items.armor.ArmorRPA) new com.hbm.items.armor.ArmorRPA(com.hbm.items.armor.ModArmorMaterials.RPA, net.minecraft.world.item.ArmorItem.Type.HELMET, new Item.Properties(), 2500000, 10000, 1000, 25)
			.enableVATS(true).setHasGeigerSound(true).setHasHardLanding(true).setRadResist(2.0D).addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.DAMAGE_BOOST, 40, 3)));
	public static final DeferredItem<com.hbm.items.armor.ArmorRPA> RPA_PLATE = register("rpa_plate", () -> (com.hbm.items.armor.ArmorRPA) new com.hbm.items.armor.ArmorRPA(com.hbm.items.armor.ModArmorMaterials.RPA, net.minecraft.world.item.ArmorItem.Type.CHESTPLATE, new Item.Properties(), 2500000, 10000, 10000, 25).cloneStats(RPA_HELMET.get()));
	public static final DeferredItem<com.hbm.items.armor.ArmorRPA> RPA_LEGS = register("rpa_legs", () -> (com.hbm.items.armor.ArmorRPA) new com.hbm.items.armor.ArmorRPA(com.hbm.items.armor.ModArmorMaterials.RPA, net.minecraft.world.item.ArmorItem.Type.LEGGINGS, new Item.Properties(), 2500000, 10000, 10000, 25).cloneStats(RPA_HELMET.get()));
	public static final DeferredItem<com.hbm.items.armor.ArmorRPA> RPA_BOOTS = register("rpa_boots", () -> (com.hbm.items.armor.ArmorRPA) new com.hbm.items.armor.ArmorRPA(com.hbm.items.armor.ModArmorMaterials.RPA, net.minecraft.world.item.ArmorItem.Type.BOOTS, new Item.Properties(), 2500000, 10000, 10000, 25).cloneStats(RPA_HELMET.get()));

	// Power Armor: FAU / Digamma
	public static final DeferredItem<com.hbm.items.armor.ArmorDigamma> FAU_HELMET = register("fau_helmet", () -> (com.hbm.items.armor.ArmorDigamma) new com.hbm.items.armor.ArmorDigamma(com.hbm.items.armor.ModArmorMaterials.FAU, net.minecraft.world.item.ArmorItem.Type.HELMET, new Item.Properties(), 10000000, 100000, 25000, 1000)
			.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.JUMP, 40, 1)).setHasGeigerSound(true).enableThermalSight(true).setHasHardLanding(true).setRadResist(4.0D));
	public static final DeferredItem<com.hbm.items.armor.ArmorDigamma> FAU_PLATE = register("fau_plate", () -> (com.hbm.items.armor.ArmorDigamma) new com.hbm.items.armor.ArmorDigamma(com.hbm.items.armor.ModArmorMaterials.FAU, net.minecraft.world.item.ArmorItem.Type.CHESTPLATE, new Item.Properties(), 10000000, 100000, 25000, 1000).cloneStats(FAU_HELMET.get()));
	public static final DeferredItem<com.hbm.items.armor.ArmorDigamma> FAU_LEGS = register("fau_legs", () -> (com.hbm.items.armor.ArmorDigamma) new com.hbm.items.armor.ArmorDigamma(com.hbm.items.armor.ModArmorMaterials.FAU, net.minecraft.world.item.ArmorItem.Type.LEGGINGS, new Item.Properties(), 10000000, 100000, 25000, 1000).cloneStats(FAU_HELMET.get()));
	public static final DeferredItem<com.hbm.items.armor.ArmorDigamma> FAU_BOOTS = register("fau_boots", () -> (com.hbm.items.armor.ArmorDigamma) new com.hbm.items.armor.ArmorDigamma(com.hbm.items.armor.ModArmorMaterials.FAU, net.minecraft.world.item.ArmorItem.Type.BOOTS, new Item.Properties(), 10000000, 100000, 25000, 1000).cloneStats(FAU_HELMET.get()));

	// Power Armor: DNS (Dineutronium Nanotech Suit)
	public static final DeferredItem<com.hbm.items.armor.ArmorDNT> DNS_HELMET = register("dns_helmet", () -> (com.hbm.items.armor.ArmorDNT) new com.hbm.items.armor.ArmorDNT(com.hbm.items.armor.ModArmorMaterials.DNS, net.minecraft.world.item.ArmorItem.Type.HELMET, new Item.Properties(), 1000000000L, 1000000L, 100000L, 115)
			.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.DAMAGE_BOOST, 40, 9))
			.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.DIG_SPEED, 40, 7))
			.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.JUMP, 40, 2))
			.setHasGeigerSound(true).enableVATS(true).enableThermalSight(true).setHasHardLanding(true).setRadResist(5.0D));
	public static final DeferredItem<com.hbm.items.armor.ArmorDNT> DNS_PLATE = register("dns_plate", () -> (com.hbm.items.armor.ArmorDNT) new com.hbm.items.armor.ArmorDNT(com.hbm.items.armor.ModArmorMaterials.DNS, net.minecraft.world.item.ArmorItem.Type.CHESTPLATE, new Item.Properties(), 1000000000L, 1000000L, 100000L, 115).cloneStats(DNS_HELMET.get()));
	public static final DeferredItem<com.hbm.items.armor.ArmorDNT> DNS_LEGS = register("dns_legs", () -> (com.hbm.items.armor.ArmorDNT) new com.hbm.items.armor.ArmorDNT(com.hbm.items.armor.ModArmorMaterials.DNS, net.minecraft.world.item.ArmorItem.Type.LEGGINGS, new Item.Properties(), 1000000000L, 1000000L, 100000L, 115).cloneStats(DNS_HELMET.get()));
	public static final DeferredItem<com.hbm.items.armor.ArmorDNT> DNS_BOOTS = register("dns_boots", () -> (com.hbm.items.armor.ArmorDNT) new com.hbm.items.armor.ArmorDNT(com.hbm.items.armor.ModArmorMaterials.DNS, net.minecraft.world.item.ArmorItem.Type.BOOTS, new Item.Properties(), 1000000000L, 1000000L, 100000L, 115).cloneStats(DNS_HELMET.get()));

	// Power Armor: T-51
	public static final DeferredItem<com.hbm.items.armor.ArmorT51> T51_HELMET = register("t51_helmet", () -> (com.hbm.items.armor.ArmorT51) new com.hbm.items.armor.ArmorT51(com.hbm.items.armor.ModArmorMaterials.T51, net.minecraft.world.item.ArmorItem.Type.HELMET, new Item.Properties(), 1000000, 10000, 1000, 5)
			.enableVATS(true).setHasGeigerSound(true).setHasHardLanding(true).setRadResist(1.0D).addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.DAMAGE_BOOST, 40, 0)));
	public static final DeferredItem<com.hbm.items.armor.ArmorT51> T51_PLATE = register("t51_plate", () -> (com.hbm.items.armor.ArmorT51) new com.hbm.items.armor.ArmorT51(com.hbm.items.armor.ModArmorMaterials.T51, net.minecraft.world.item.ArmorItem.Type.CHESTPLATE, new Item.Properties(), 1000000, 10000, 1000, 5).cloneStats(T51_HELMET.get()));
	public static final DeferredItem<com.hbm.items.armor.ArmorT51> T51_LEGS = register("t51_legs", () -> (com.hbm.items.armor.ArmorT51) new com.hbm.items.armor.ArmorT51(com.hbm.items.armor.ModArmorMaterials.T51, net.minecraft.world.item.ArmorItem.Type.LEGGINGS, new Item.Properties(), 1000000, 10000, 1000, 5).cloneStats(T51_HELMET.get()));
	public static final DeferredItem<com.hbm.items.armor.ArmorT51> T51_BOOTS = register("t51_boots", () -> (com.hbm.items.armor.ArmorT51) new com.hbm.items.armor.ArmorT51(com.hbm.items.armor.ModArmorMaterials.T51, net.minecraft.world.item.ArmorItem.Type.BOOTS, new Item.Properties(), 1000000, 10000, 1000, 5).cloneStats(T51_HELMET.get()));

	// Power Armor: HEV Hazard Suit
	public static final DeferredItem<com.hbm.items.armor.ArmorHEV> HEV_HELMET = register("hev_helmet", () -> (com.hbm.items.armor.ArmorHEV) new com.hbm.items.armor.ArmorHEV(com.hbm.items.armor.ModArmorMaterials.HEV, net.minecraft.world.item.ArmorItem.Type.HELMET, new Item.Properties(), 1000000, 10000, 2500, 0)
			.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.JUMP, 40, 0))
			.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.MOVEMENT_SPEED, 40, 1))
			.setRadResist(2.3D).setHasGeigerSound(true).setHasCustomGeiger(true));
	public static final DeferredItem<com.hbm.items.armor.ArmorHEV> HEV_PLATE = register("hev_plate", () -> (com.hbm.items.armor.ArmorHEV) new com.hbm.items.armor.ArmorHEV(com.hbm.items.armor.ModArmorMaterials.HEV, net.minecraft.world.item.ArmorItem.Type.CHESTPLATE, new Item.Properties(), 1000000, 10000, 2500, 0).cloneStats(HEV_HELMET.get()));
	public static final DeferredItem<com.hbm.items.armor.ArmorHEV> HEV_LEGS = register("hev_legs", () -> (com.hbm.items.armor.ArmorHEV) new com.hbm.items.armor.ArmorHEV(com.hbm.items.armor.ModArmorMaterials.HEV, net.minecraft.world.item.ArmorItem.Type.LEGGINGS, new Item.Properties(), 1000000, 10000, 2500, 0).cloneStats(HEV_HELMET.get()));
	public static final DeferredItem<com.hbm.items.armor.ArmorHEV> HEV_BOOTS = register("hev_boots", () -> (com.hbm.items.armor.ArmorHEV) new com.hbm.items.armor.ArmorHEV(com.hbm.items.armor.ModArmorMaterials.HEV, net.minecraft.world.item.ArmorItem.Type.BOOTS, new Item.Properties(), 1000000, 10000, 2500, 0).cloneStats(HEV_HELMET.get()));

	// Power Armor: Blackjack (BJ)
	public static final DeferredItem<com.hbm.items.armor.ArmorBJ> BJ_HELMET = register("bj_helmet", () -> (com.hbm.items.armor.ArmorBJ) new com.hbm.items.armor.ArmorBJ(com.hbm.items.armor.ModArmorMaterials.BJ, net.minecraft.world.item.ArmorItem.Type.HELMET, new Item.Properties(), 10000000, 10000, 1000, 100)
			.enableVATS(true).enableThermalSight(true).setHasHardLanding(true).setHasGeigerSound(true).setRadResist(1.0D)
			.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.MOVEMENT_SPEED, 40, 1))
			.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.JUMP, 40, 0))
			.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.SATURATION, 40, 0)));
	public static final DeferredItem<com.hbm.items.armor.ArmorBJ> BJ_PLATE = register("bj_plate", () -> (com.hbm.items.armor.ArmorBJ) new com.hbm.items.armor.ArmorBJ(com.hbm.items.armor.ModArmorMaterials.BJ, net.minecraft.world.item.ArmorItem.Type.CHESTPLATE, new Item.Properties(), 10000000, 10000, 1000, 100).cloneStats(BJ_HELMET.get()));
	public static final DeferredItem<com.hbm.items.armor.ArmorBJJetpack> BJ_PLATE_JETPACK = register("bj_plate_jetpack", () -> (com.hbm.items.armor.ArmorBJJetpack) new com.hbm.items.armor.ArmorBJJetpack(com.hbm.items.armor.ModArmorMaterials.BJ, net.minecraft.world.item.ArmorItem.Type.CHESTPLATE, new Item.Properties(), 10000000, 10000, 1000, 100).cloneStats(BJ_HELMET.get()));
	public static final DeferredItem<com.hbm.items.armor.ArmorBJ> BJ_LEGS = register("bj_legs", () -> (com.hbm.items.armor.ArmorBJ) new com.hbm.items.armor.ArmorBJ(com.hbm.items.armor.ModArmorMaterials.BJ, net.minecraft.world.item.ArmorItem.Type.LEGGINGS, new Item.Properties(), 10000000, 10000, 1000, 100).cloneStats(BJ_HELMET.get()));
	public static final DeferredItem<com.hbm.items.armor.ArmorBJ> BJ_BOOTS = register("bj_boots", () -> (com.hbm.items.armor.ArmorBJ) new com.hbm.items.armor.ArmorBJ(com.hbm.items.armor.ModArmorMaterials.BJ, net.minecraft.world.item.ArmorItem.Type.BOOTS, new Item.Properties(), 10000000, 10000, 1000, 100).cloneStats(BJ_HELMET.get()));

	// Power Armor: AJR
	public static final DeferredItem<com.hbm.items.armor.ArmorAJR> AJR_HELMET = register("ajr_helmet", () -> (com.hbm.items.armor.ArmorAJR) new com.hbm.items.armor.ArmorAJR(com.hbm.items.armor.ModArmorMaterials.AJR, net.minecraft.world.item.ArmorItem.Type.HELMET, new Item.Properties(), 2500000, 10000, 2000, 25)
			.enableVATS(true).setHasGeigerSound(true).setHasHardLanding(true).setRadResist(1.3D)
			.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.JUMP, 40, 0))
			.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.DAMAGE_BOOST, 40, 0)));
	public static final DeferredItem<com.hbm.items.armor.ArmorAJR> AJR_PLATE = register("ajr_plate", () -> (com.hbm.items.armor.ArmorAJR) new com.hbm.items.armor.ArmorAJR(com.hbm.items.armor.ModArmorMaterials.AJR, net.minecraft.world.item.ArmorItem.Type.CHESTPLATE, new Item.Properties(), 2500000, 10000, 2000, 25).cloneStats(AJR_HELMET.get()));
	public static final DeferredItem<com.hbm.items.armor.ArmorAJR> AJR_LEGS = register("ajr_legs", () -> (com.hbm.items.armor.ArmorAJR) new com.hbm.items.armor.ArmorAJR(com.hbm.items.armor.ModArmorMaterials.AJR, net.minecraft.world.item.ArmorItem.Type.LEGGINGS, new Item.Properties(), 2500000, 10000, 2000, 25).cloneStats(AJR_HELMET.get()));
	public static final DeferredItem<com.hbm.items.armor.ArmorAJR> AJR_BOOTS = register("ajr_boots", () -> (com.hbm.items.armor.ArmorAJR) new com.hbm.items.armor.ArmorAJR(com.hbm.items.armor.ModArmorMaterials.AJR, net.minecraft.world.item.ArmorItem.Type.BOOTS, new Item.Properties(), 2500000, 10000, 2000, 25).cloneStats(AJR_HELMET.get()));


	// Miscellaneous & Materials
	public static final DeferredItem<ItemBase> AMMO_CONTAINER = reg("ammo_container");
	public static final DeferredItem<ItemBase> BOTTLE_RAD = reg("bottle_rad");
	public static final DeferredItem<ItemBase> MISSILE_NUCLEAR = reg("missile_nuclear");
	public static final DeferredItem<ItemBase> PLATE_SATURNITE = reg("plate_saturnite");
	public static final DeferredItem<ItemBase> ENTANGLEMENT_KIT = reg("entanglement_kit");
	public static final DeferredItem<ItemBase> MARSHMALLOW = reg("marshmallow");
	public static final DeferredItem<ItemBase> MARSHMALLOW_ROASTED = reg("marshmallow_roasted");
	public static final DeferredItem<com.hbm.items.tool.ItemDesignator> DESIGNATOR = register("designator", () -> new com.hbm.items.tool.ItemDesignator(new Item.Properties()));

	// Firearms & Weapons
	public static final DeferredItem<com.hbm.items.weapon.sedna.ItemGunBaseSedna> GUN_9MM = register("gun_9mm", com.hbm.items.weapon.sedna.factory.GunFactory::create9mmPistol);
	public static final DeferredItem<com.hbm.items.weapon.sedna.ItemGunBaseSedna> GUN_44 = register("gun_44", com.hbm.items.weapon.sedna.factory.GunFactory::create44Revolver);
	public static final DeferredItem<com.hbm.items.weapon.sedna.ItemGunBaseSedna> GUN_50BMG = register("gun_50bmg", com.hbm.items.weapon.sedna.factory.GunFactory::create50BMGAntiMateriel);
	public static final DeferredItem<com.hbm.items.weapon.sedna.ItemGunBaseSedna> GUN_12GA = register("gun_12ga", com.hbm.items.weapon.sedna.factory.GunFactory::create12gaShotgun);
	public static final DeferredItem<com.hbm.items.weapon.sedna.ItemGunBaseSedna> GUN_FATMAN = register("gun_fatman", com.hbm.items.weapon.sedna.factory.GunFactory::createFatMan);

	// Ammunition
	public static final DeferredItem<com.hbm.items.weapon.sedna.ItemAmmo> AMMO_9MM = register("ammo_9mm", () -> new com.hbm.items.weapon.sedna.ItemAmmo(new Item.Properties(), com.hbm.items.weapon.sedna.factory.GunFactory.B9_STANDARD));
	public static final DeferredItem<com.hbm.items.weapon.sedna.ItemAmmo> AMMO_44 = register("ammo_44", () -> new com.hbm.items.weapon.sedna.ItemAmmo(new Item.Properties(), com.hbm.items.weapon.sedna.factory.GunFactory.B44_STANDARD));
	public static final DeferredItem<com.hbm.items.weapon.sedna.ItemAmmo> AMMO_50BMG = register("ammo_50bmg", () -> new com.hbm.items.weapon.sedna.ItemAmmo(new Item.Properties(), com.hbm.items.weapon.sedna.factory.GunFactory.B50_STANDARD));
	public static final DeferredItem<com.hbm.items.weapon.sedna.ItemAmmo> AMMO_12GA = register("ammo_12ga", () -> new com.hbm.items.weapon.sedna.ItemAmmo(new Item.Properties(), com.hbm.items.weapon.sedna.factory.GunFactory.B12GA_BUCK));
	public static final DeferredItem<com.hbm.items.weapon.sedna.ItemAmmo> AMMO_MINI_NUKE = register("ammo_mini_nuke", () -> new com.hbm.items.weapon.sedna.ItemAmmo(new Item.Properties(), com.hbm.items.weapon.sedna.factory.GunFactory.B_MINI_NUKE));

	// Grenades & Explosives
	public static final DeferredItem<com.hbm.items.weapon.grenade.ItemGrenade> GRENADE_GENERIC = register("grenade_generic", () -> new com.hbm.items.weapon.grenade.ItemGrenade(new Item.Properties(), 4.0F, false));
	public static final DeferredItem<com.hbm.items.weapon.grenade.ItemGrenade> GRENADE_NUCLEAR = register("grenade_nuclear", () -> new com.hbm.items.weapon.grenade.ItemGrenade(new Item.Properties(), 8.0F, true));

	// Materials, Ingots, Powders, Nuggets & Crystals
	public static final DeferredItem<ItemBase> POWDER_COAL = reg("powder_coal");
	public static final DeferredItem<ItemBase> POWDER_IRON = reg("powder_iron");
	public static final DeferredItem<ItemBase> POWDER_GOLD = reg("powder_gold");
	public static final DeferredItem<ItemBase> POWDER_DIAMOND = reg("powder_diamond");
	public static final DeferredItem<ItemBase> POWDER_EMERALD = reg("powder_emerald");
	public static final DeferredItem<ItemBase> POWDER_TITANIUM = reg("powder_titanium");
	public static final DeferredItem<ItemBase> POWDER_COPPER = reg("powder_copper");
	public static final DeferredItem<ItemBase> POWDER_TUNGSTEN = reg("powder_tungsten");
	public static final DeferredItem<ItemBase> POWDER_LEAD = reg("powder_lead");
	public static final DeferredItem<ItemBase> POWDER_URANIUM = reg("powder_uranium");
	public static final DeferredItem<ItemBase> POWDER_THORIUM = reg("powder_thorium");
	public static final DeferredItem<ItemBase> POWDER_BERYLLIUM = reg("powder_beryllium");
	public static final DeferredItem<ItemBase> POWDER_LAPIS = reg("powder_lapis");
	public static final DeferredItem<ItemBase> POWDER_COBALT = reg("powder_cobalt");
	public static final DeferredItem<ItemBase> POWDER_COBALT_TINY = reg("powder_cobalt_tiny");
	public static final DeferredItem<ItemBase> POWDER_SCHRABIDIUM = reg("powder_schrabidium");
	public static final DeferredItem<ItemBase> POWDER_DESH_MIX = reg("powder_desh_mix");
	public static final DeferredItem<ItemBase> POWDER_NITAN_MIX = reg("powder_nitan_mix");
	public static final DeferredItem<ItemBase> POWDER_LITHIUM = reg("powder_lithium");
	public static final DeferredItem<ItemBase> POWDER_LITHIUM_TINY = reg("powder_lithium_tiny");

	public static final DeferredItem<ItemBase> NUGGET_SCHRABIDIUM = reg("nugget_schrabidium");
	public static final DeferredItem<ItemBase> NUGGET_URANIUM = reg("nugget_uranium");
	public static final DeferredItem<ItemBase> NUGGET_NEPTUNIUM = reg("nugget_neptunium");
	public static final DeferredItem<ItemBase> NUGGET_RA226 = reg("nugget_ra226");
	public static final DeferredItem<ItemBase> NUGGET_ZIRCONIUM = reg("nugget_zirconium");

	public static final DeferredItem<ItemBase> INGOT_RED_COPPER = reg("ingot_red_copper");
	public static final DeferredItem<ItemBase> INGOT_STARMETAL = reg("ingot_starmetal");
	public static final DeferredItem<ItemBase> INGOT_FIREBRICK = reg("ingot_firebrick");
	public static final DeferredItem<ItemBase> INGOT_SCHRARANIUM = reg("ingot_schraranium");
	public static final DeferredItem<ItemBase> INGOT_PHOSPHORUS = reg("ingot_phosphorus");
	public static final DeferredItem<ItemBase> INGOT_MERCURY = reg("ingot_mercury");
	public static final DeferredItem<ItemBase> INGOT_METEORITE = reg("ingot_meteorite");

	public static final DeferredItem<ItemBase> CRYSTAL_COAL = reg("crystal_coal");
	public static final DeferredItem<ItemBase> CRYSTAL_BERYLLIUM = reg("crystal_beryllium");
	public static final DeferredItem<ItemBase> CRYSTAL_SCHRABIDIUM = reg("crystal_schrabidium");

	public static final DeferredItem<ItemBase> GEM_SODALITE = reg("gem_sodalite");
	public static final DeferredItem<ItemBase> SCRAP = reg("scrap");

	// AMS Cores
	public static final DeferredItem<ItemAMSCore> AMS_CORE_SING = register("ams_core_sing", () -> new ItemAMSCore(100_000, 100, 1.0F));
	public static final DeferredItem<ItemAMSCore> AMS_CORE_WORMHOLE = register("ams_core_wormhole", () -> new ItemAMSCore(500_000, 250, 2.5F));
	public static final DeferredItem<ItemAMSCore> AMS_CORE_THINGY = register("ams_core_thingy", () -> new ItemAMSCore(2_000_000, 500, 5.0F));
	public static final DeferredItem<ItemAMSCore> AMS_CORE_EYEOFHARMONY = register("ams_core_eyeofharmony", () -> new ItemAMSCore(10_000_000, 1000, 10.0F));

	// Machine Upgrades
	public static final DeferredItem<ItemMachineUpgrade> UPGRADE_SPEED_1 = register("upgrade_speed_1", () -> new ItemMachineUpgrade("upgrade_speed_1", UpgradeType.SPEED, 1));
	public static final DeferredItem<ItemMachineUpgrade> UPGRADE_SPEED_2 = register("upgrade_speed_2", () -> new ItemMachineUpgrade("upgrade_speed_2", UpgradeType.SPEED, 2));
	public static final DeferredItem<ItemMachineUpgrade> UPGRADE_SPEED_3 = register("upgrade_speed_3", () -> new ItemMachineUpgrade("upgrade_speed_3", UpgradeType.SPEED, 3));
	public static final DeferredItem<ItemMachineUpgrade> UPGRADE_OVERDRIVE_1 = register("upgrade_overdrive_1", () -> new ItemMachineUpgrade("upgrade_overdrive_1", UpgradeType.OVERDRIVE, 1));
	public static final DeferredItem<ItemMachineUpgrade> UPGRADE_OVERDRIVE_2 = register("upgrade_overdrive_2", () -> new ItemMachineUpgrade("upgrade_overdrive_2", UpgradeType.OVERDRIVE, 2));
	public static final DeferredItem<ItemMachineUpgrade> UPGRADE_OVERDRIVE_3 = register("upgrade_overdrive_3", () -> new ItemMachineUpgrade("upgrade_overdrive_3", UpgradeType.OVERDRIVE, 3));
	public static final DeferredItem<ItemMachineUpgrade> UPGRADE_POWER_1 = register("upgrade_power_1", () -> new ItemMachineUpgrade("upgrade_power_1", UpgradeType.POWER, 1));
	public static final DeferredItem<ItemMachineUpgrade> UPGRADE_POWER_2 = register("upgrade_power_2", () -> new ItemMachineUpgrade("upgrade_power_2", UpgradeType.POWER, 2));
	public static final DeferredItem<ItemMachineUpgrade> UPGRADE_POWER_3 = register("upgrade_power_3", () -> new ItemMachineUpgrade("upgrade_power_3", UpgradeType.POWER, 3));
	public static final DeferredItem<ItemMachineUpgrade> UPGRADE_AFTERBURN_1 = register("upgrade_afterburn_1", () -> new ItemMachineUpgrade("upgrade_afterburn_1", UpgradeType.AFTERBURN, 1));
	public static final DeferredItem<ItemMachineUpgrade> UPGRADE_AFTERBURN_2 = register("upgrade_afterburn_2", () -> new ItemMachineUpgrade("upgrade_afterburn_2", UpgradeType.AFTERBURN, 2));
	public static final DeferredItem<ItemMachineUpgrade> UPGRADE_AFTERBURN_3 = register("upgrade_afterburn_3", () -> new ItemMachineUpgrade("upgrade_afterburn_3", UpgradeType.AFTERBURN, 3));
	public static final DeferredItem<ItemMachineUpgrade> UPGRADE_RADIUS = register("upgrade_radius", () -> new ItemMachineUpgrade("upgrade_radius", UpgradeType.SPECIAL));
	public static final DeferredItem<ItemMachineUpgrade> UPGRADE_HEALTH = register("upgrade_health", () -> new ItemMachineUpgrade("upgrade_health", UpgradeType.SPECIAL));
	public static final DeferredItem<ItemMachineUpgrade> UPGRADE_SMELTER = register("upgrade_smelter", () -> new ItemMachineUpgrade("upgrade_smelter", UpgradeType.SPECIAL));
	public static final DeferredItem<ItemMachineUpgrade> UPGRADE_SHREDDER = register("upgrade_shredder", () -> new ItemMachineUpgrade("upgrade_shredder", UpgradeType.SPECIAL));
	public static final DeferredItem<ItemMachineUpgrade> UPGRADE_CENTRIFUGE = register("upgrade_centrifuge", () -> new ItemMachineUpgrade("upgrade_centrifuge", UpgradeType.SPECIAL));
	public static final DeferredItem<ItemMachineUpgrade> UPGRADE_CRYSTALLIZER = register("upgrade_crystallizer", () -> new ItemMachineUpgrade("upgrade_crystallizer", UpgradeType.SPECIAL));
	public static final DeferredItem<ItemMachineUpgrade> UPGRADE_SCREM = register("upgrade_screm", () -> new ItemMachineUpgrade("upgrade_screm", UpgradeType.SPECIAL));
	public static final DeferredItem<ItemMachineUpgrade> UPGRADE_NULLIFIER = register("upgrade_nullifier", () -> new ItemMachineUpgrade("upgrade_nullifier", UpgradeType.SPECIAL));
	public static final DeferredItem<ItemMachineUpgrade> UPGRADE_GC_SPEED = register("upgrade_gc_speed", () -> new ItemMachineUpgrade("upgrade_gc_speed", UpgradeType.SPECIAL));

	// Lore & Rags
	public static final DeferredItem<ItemBase> BOOK_LORE = reg("book_lore");
	public static final DeferredItem<ItemBase> RAG = reg("rag");
	public static final DeferredItem<ItemBase> RAG_DAMP = reg("rag_damp");
	public static final DeferredItem<ItemBase> MASK_DAMP = reg("mask_damp");
	public static final DeferredItem<ItemBase> RAG_PISS = reg("rag_piss");
	public static final DeferredItem<ItemBase> MASK_PISS = reg("mask_piss");

	// Fluid Containers & Filters
	public static final DeferredItem<ItemCanister> CANISTER_EMPTY = register("canister_empty", () -> new ItemCanister(1000));
	public static final DeferredItem<ItemCanister> CANISTER_FULL = register("canister_full", () -> new ItemCanister(1000));
	public static final DeferredItem<ItemGasCanister> GAS_EMPTY = register("gas_empty", ItemGasCanister::new);
	public static final DeferredItem<ItemGasCanister> GAS_FULL = register("gas_full", ItemGasCanister::new);
	public static final DeferredItem<ItemFilter> FILTER_COAL = register("filter_coal", () -> new ItemFilter(100));

	// Compatibility accessors
	public static Item dosimeter;
	public static Item geiger_counter;
	public static Item digamma_diagnostic;
	public static Item lung_diagnostic;
	public static Item survey_scanner;
	public static Item ore_density_scanner;
	public static Item dyatlov;

	public static Item pellet_antimatter;
	public static Item singularity;
	public static Item singularity_counter_resonant;
	public static Item singularity_super_heated;
	public static Item black_hole;
	public static Item detonator_deadman;
	public static Item detonator_de;

	public static Item spawn_chopper;
	public static Item spawn_worm;
	public static Item spawn_ufo;
	public static Item spawn_duck;
	public static Item polaroid;
	public static Item glitch;

	public static Item kit_custom;
	public static Item kit_nbt;
	public static Item loot_10;
	public static Item loot_15;
	public static Item loot_misc;
	public static Item nuke_starter_kit;
	public static Item nuke_advanced_kit;
	public static Item gadget_kit;
	public static Item boy_kit;
	public static Item man_kit;
	public static Item mike_kit;
	public static Item tsar_kit;
	public static Item hazmat_kit;
	public static Item hazmat_red_kit;
	public static Item hazmat_grey_kit;
	public static Item stealth_boy;
	public static Item euphemium_kit;
	public static Item prototype_kit;
	public static Item fleija_kit;
	public static Item solinium_kit;
	public static Item balefire_kit;
	public static Item grenade_kit;
	public static Item missile_kit;
	public static Item t45_kit;
	public static Item multi_kit;

	public static Item dust;
	public static Item powder_fire;
	public static Item powder_coal;
	public static Item powder_iron;
	public static Item powder_gold;
	public static Item powder_diamond;
	public static Item powder_emerald;
	public static Item powder_titanium;
	public static Item powder_copper;
	public static Item powder_tungsten;
	public static Item powder_lead;
	public static Item powder_uranium;
	public static Item powder_thorium;
	public static Item powder_beryllium;
	public static Item powder_lapis;
	public static Item powder_cobalt;
	public static Item powder_cobalt_tiny;
	public static Item powder_schrabidium;
	public static Item powder_desh_mix;
	public static Item powder_nitan_mix;
	public static Item powder_lithium;
	public static Item powder_lithium_tiny;

	public static Item nugget_schrabidium;
	public static Item nugget_uranium;
	public static Item nugget_neptunium;
	public static Item nugget_ra226;
	public static Item nugget_zirconium;

	public static Item ingot_uranium;
	public static Item ingot_red_copper;
	public static Item ingot_starmetal;
	public static Item ingot_firebrick;
	public static Item ingot_schraranium;
	public static Item ingot_phosphorus;
	public static Item ingot_mercury;
	public static Item ingot_meteorite;

	public static Item crystal_coal;
	public static Item crystal_beryllium;
	public static Item crystal_schrabidium;
	public static Item gem_sodalite;
	public static Item scrap;
	public static Item powder_yellowcake;
	public static Item powder_plutonium;
	public static Item ingot_steel;
	public static Item ingot_lead;
	public static Item ingot_copper;
	public static Item ingot_tungsten;
	public static Item ingot_polymer;
	public static Item pellet_rtg;
	public static Item cell;
	public static Item rod_empty;
	public static Item template_folder;
	public static Item radaway;
	public static Item radaway_strong;
	public static Item radx;
	public static Item pill_iodine;

	public static Item early_explosive_lenses;
	public static Item explosive_lenses;
	public static Item gadget_wireing;
	public static Item gadget_core;
	public static Item boy_shielding;
	public static Item boy_target;
	public static Item boy_bullet;
	public static Item boy_propellant;
	public static Item boy_igniter;
	public static Item man_igniter;
	public static Item man_core;
	public static Item mike_core;
	public static Item mike_deut;
	public static Item mike_cooling_unit;
	public static Item tsar_core;

	public static Item gas_mask;
	public static Item gas_mask_m65;
	public static Item jackt;
	public static Item jackt2;

	public static Item hazmat_helmet;
	public static Item hazmat_plate;
	public static Item hazmat_legs;
	public static Item hazmat_boots;
	public static Item hazmat_helmet_red;
	public static Item hazmat_plate_red;
	public static Item hazmat_legs_red;
	public static Item hazmat_boots_red;
	public static Item hazmat_helmet_grey;
	public static Item hazmat_plate_grey;
	public static Item hazmat_legs_grey;
	public static Item hazmat_boots_grey;

	public static Item hazmat_paa_helmet;
	public static Item hazmat_paa_plate;
	public static Item hazmat_paa_legs;
	public static Item hazmat_paa_boots;
	public static Item paa_plate;
	public static Item paa_legs;
	public static Item paa_boots;

	public static Item liquidator_helmet;
	public static Item liquidator_plate;
	public static Item liquidator_legs;
	public static Item liquidator_boots;

	public static Item security_helmet;
	public static Item security_plate;
	public static Item security_legs;
	public static Item security_boots;

	public static Item starmetal_helmet;
	public static Item starmetal_plate;
	public static Item starmetal_legs;
	public static Item starmetal_boots;

	public static Item steel_helmet;
	public static Item steel_plate;
	public static Item steel_legs;
	public static Item steel_boots;

	public static Item titanium_helmet;
	public static Item titanium_plate;
	public static Item titanium_legs;
	public static Item titanium_boots;

	public static Item cobalt_helmet;
	public static Item cobalt_plate;
	public static Item cobalt_legs;
	public static Item cobalt_boots;

	public static Item alloy_helmet;
	public static Item alloy_plate;
	public static Item alloy_legs;
	public static Item alloy_boots;

	public static Item cmb_helmet;
	public static Item cmb_plate;
	public static Item cmb_legs;
	public static Item cmb_boots;

	public static Item schrabidium_helmet;
	public static Item schrabidium_plate;
	public static Item schrabidium_legs;
	public static Item schrabidium_boots;

	public static Item euphemium_helmet;
	public static Item euphemium_plate;
	public static Item euphemium_legs;
	public static Item euphemium_boots;

	public static Item asbestos_helmet;
	public static Item asbestos_plate;
	public static Item asbestos_legs;
	public static Item asbestos_boots;

	public static Item rpa_helmet;
	public static Item rpa_plate;
	public static Item rpa_legs;
	public static Item rpa_boots;

	public static Item fau_helmet;
	public static Item fau_plate;
	public static Item fau_legs;
	public static Item fau_boots;

	public static Item dns_helmet;
	public static Item dns_plate;
	public static Item dns_legs;
	public static Item dns_boots;

	public static Item ammo_container;
	public static Item bottle_rad;
	public static Item missile_nuclear;
	public static Item plate_saturnite;
	public static Item entanglement_kit;
	public static Item marshmallow;
	public static Item marshmallow_roasted;
	public static Item designator;

	public static Item ams_core_sing;
	public static Item ams_core_wormhole;
	public static Item ams_core_thingy;
	public static Item ams_core_eyeofharmony;

	public static Item upgrade_speed_1;
	public static Item upgrade_speed_2;
	public static Item upgrade_speed_3;
	public static Item upgrade_overdrive_1;
	public static Item upgrade_overdrive_2;
	public static Item upgrade_overdrive_3;
	public static Item upgrade_power_1;
	public static Item upgrade_power_2;
	public static Item upgrade_power_3;
	public static Item upgrade_afterburn_1;
	public static Item upgrade_afterburn_2;
	public static Item upgrade_afterburn_3;
	public static Item upgrade_radius;
	public static Item upgrade_health;
	public static Item upgrade_smelter;
	public static Item upgrade_shredder;
	public static Item upgrade_centrifuge;
	public static Item upgrade_crystallizer;
	public static Item upgrade_screm;
	public static Item upgrade_nullifier;
	public static Item upgrade_gc_speed;

	public static Item book_lore;
	public static Item rag;
	public static Item rag_damp;
	public static Item mask_damp;
	public static Item rag_piss;
	public static Item mask_piss;

	public static Item canister_empty;
	public static Item canister_full;
	public static Item gas_empty;
	public static Item gas_full;
	public static Item filter_coal;

	public static void register(IEventBus bus) {
		ITEMS.register(bus);
	}

	public static void initAccessors() {
		dosimeter = DOSIMETER.get();
		geiger_counter = GEIGER_COUNTER.get();
		digamma_diagnostic = DIGAMMA_DIAGNOSTIC.get();
		lung_diagnostic = LUNG_DIAGNOSTIC.get();
		survey_scanner = SURVEY_SCANNER.get();
		ore_density_scanner = ORE_DENSITY_SCANNER.get();
		dyatlov = DYATLOV.get();

		pellet_antimatter = PELLET_ANTIMATTER.get();
		singularity = SINGULARITY.get();
		singularity_counter_resonant = SINGULARITY_COUNTER_RESONANT.get();
		singularity_super_heated = SINGULARITY_SUPER_HEATED.get();
		black_hole = BLACK_HOLE.get();
		detonator_deadman = DETONATOR_DEADMAN.get();
		detonator_de = DETONATOR_DE.get();

		spawn_chopper = SPAWN_CHOPPER.get();
		spawn_worm = SPAWN_WORM.get();
		spawn_ufo = SPAWN_UFO.get();
		spawn_duck = SPAWN_DUCK.get();
		polaroid = POLAROID.get();
		glitch = GLITCH.get();

		kit_custom = KIT_CUSTOM.get();
		kit_nbt = KIT_NBT.get();
		loot_10 = LOOT_10.get();
		loot_15 = LOOT_15.get();
		loot_misc = LOOT_MISC.get();
		nuke_starter_kit = NUKE_STARTER_KIT.get();
		nuke_advanced_kit = NUKE_ADVANCED_KIT.get();
		gadget_kit = GADGET_KIT.get();
		boy_kit = BOY_KIT.get();
		man_kit = MAN_KIT.get();
		mike_kit = MIKE_KIT.get();
		tsar_kit = TSAR_KIT.get();
		hazmat_kit = HAZMAT_KIT.get();
		hazmat_red_kit = HAZMAT_RED_KIT.get();
		hazmat_grey_kit = HAZMAT_GREY_KIT.get();
		stealth_boy = STEALTH_BOY.get();
		euphemium_kit = EUPHEMIUM_KIT.get();
		prototype_kit = PROTOTYPE_KIT.get();
		fleija_kit = FLEIJA_KIT.get();
		solinium_kit = SOLINIUM_KIT.get();
		balefire_kit = BALEFIRE_KIT.get();
		grenade_kit = GRENADE_KIT.get();
		missile_kit = MISSILE_KIT.get();
		t45_kit = T45_KIT.get();
		multi_kit = MULTI_KIT.get();

		dust = DUST.get();
		powder_fire = POWDER_FIRE.get();
		powder_coal = POWDER_COAL.get();
		powder_iron = POWDER_IRON.get();
		powder_gold = POWDER_GOLD.get();
		powder_diamond = POWDER_DIAMOND.get();
		powder_emerald = POWDER_EMERALD.get();
		powder_titanium = POWDER_TITANIUM.get();
		powder_copper = POWDER_COPPER.get();
		powder_tungsten = POWDER_TUNGSTEN.get();
		powder_lead = POWDER_LEAD.get();
		powder_uranium = POWDER_URANIUM.get();
		powder_thorium = POWDER_THORIUM.get();
		powder_beryllium = POWDER_BERYLLIUM.get();
		powder_lapis = POWDER_LAPIS.get();
		powder_cobalt = POWDER_COBALT.get();
		powder_cobalt_tiny = POWDER_COBALT_TINY.get();
		powder_schrabidium = POWDER_SCHRABIDIUM.get();
		powder_desh_mix = POWDER_DESH_MIX.get();
		powder_nitan_mix = POWDER_NITAN_MIX.get();
		powder_lithium = POWDER_LITHIUM.get();
		powder_lithium_tiny = POWDER_LITHIUM_TINY.get();

		nugget_schrabidium = NUGGET_SCHRABIDIUM.get();
		nugget_uranium = NUGGET_URANIUM.get();
		nugget_neptunium = NUGGET_NEPTUNIUM.get();
		nugget_ra226 = NUGGET_RA226.get();
		nugget_zirconium = NUGGET_ZIRCONIUM.get();

		ingot_uranium = INGOT_URANIUM.get();
		ingot_red_copper = INGOT_RED_COPPER.get();
		ingot_starmetal = INGOT_STARMETAL.get();
		ingot_firebrick = INGOT_FIREBRICK.get();
		ingot_schraranium = INGOT_SCHRARANIUM.get();
		ingot_phosphorus = INGOT_PHOSPHORUS.get();
		ingot_mercury = INGOT_MERCURY.get();
		ingot_meteorite = INGOT_METEORITE.get();

		crystal_coal = CRYSTAL_COAL.get();
		crystal_beryllium = CRYSTAL_BERYLLIUM.get();
		crystal_schrabidium = CRYSTAL_SCHRABIDIUM.get();
		gem_sodalite = GEM_SODALITE.get();
		scrap = SCRAP.get();

		powder_yellowcake = POWDER_YELLOWCAKE.get();
		powder_plutonium = POWDER_PLUTONIUM.get();
		ingot_steel = INGOT_STEEL.get();
		ingot_lead = INGOT_LEAD.get();
		ingot_copper = INGOT_COPPER.get();
		ingot_tungsten = INGOT_TUNGSTEN.get();
		ingot_polymer = INGOT_POLYMER.get();
		pellet_rtg = PELLET_RTG.get();
		cell = CELL.get();
		rod_empty = ROD_EMPTY.get();
		template_folder = TEMPLATE_FOLDER.get();
		radaway = RADAWAY.get();
		radaway_strong = RADAWAY_STRONG.get();
		radx = RADX.get();
		pill_iodine = PILL_IODINE.get();

		early_explosive_lenses = EARLY_EXPLOSIVE_LENSES.get();
		explosive_lenses = EXPLOSIVE_LENSES.get();
		gadget_wireing = GADGET_WIREING.get();
		gadget_core = GADGET_CORE.get();
		boy_shielding = BOY_SHIELDING.get();
		boy_target = BOY_TARGET.get();
		boy_bullet = BOY_BULLET.get();
		boy_propellant = BOY_PROPELLANT.get();
		boy_igniter = BOY_IGNITER.get();
		man_igniter = MAN_IGNITER.get();
		man_core = MAN_CORE.get();
		mike_core = MIKE_CORE.get();
		mike_deut = MIKE_DEUT.get();
		mike_cooling_unit = MIKE_COOLING_UNIT.get();
		tsar_core = TSAR_CORE.get();

		gas_mask = GAS_MASK.get();
		gas_mask_m65 = GAS_MASK_M65.get();
		jackt = JACKT.get();
		jackt2 = JACKT2.get();

		hazmat_helmet = HAZMAT_HELMET.get();
		hazmat_plate = HAZMAT_PLATE.get();
		hazmat_legs = HAZMAT_LEGS.get();
		hazmat_boots = HAZMAT_BOOTS.get();
		hazmat_helmet_red = HAZMAT_HELMET_RED.get();
		hazmat_plate_red = HAZMAT_PLATE_RED.get();
		hazmat_legs_red = HAZMAT_LEGS_RED.get();
		hazmat_boots_red = HAZMAT_BOOTS_RED.get();
		hazmat_helmet_grey = HAZMAT_HELMET_GREY.get();
		hazmat_plate_grey = HAZMAT_PLATE_GREY.get();
		hazmat_legs_grey = HAZMAT_LEGS_GREY.get();
		hazmat_boots_grey = HAZMAT_BOOTS_GREY.get();

		hazmat_paa_helmet = HAZMAT_PAA_HELMET.get();
		hazmat_paa_plate = HAZMAT_PAA_PLATE.get();
		hazmat_paa_legs = HAZMAT_PAA_LEGS.get();
		hazmat_paa_boots = HAZMAT_PAA_BOOTS.get();
		paa_plate = PAA_PLATE.get();
		paa_legs = PAA_LEGS.get();
		paa_boots = PAA_BOOTS.get();

		liquidator_helmet = LIQUIDATOR_HELMET.get();
		liquidator_plate = LIQUIDATOR_PLATE.get();
		liquidator_legs = LIQUIDATOR_LEGS.get();
		liquidator_boots = LIQUIDATOR_BOOTS.get();

		security_helmet = SECURITY_HELMET.get();
		security_plate = SECURITY_PLATE.get();
		security_legs = SECURITY_LEGS.get();
		security_boots = SECURITY_BOOTS.get();

		starmetal_helmet = STARMETAL_HELMET.get();
		starmetal_plate = STARMETAL_PLATE.get();
		starmetal_legs = STARMETAL_LEGS.get();
		starmetal_boots = STARMETAL_BOOTS.get();

		steel_helmet = STEEL_HELMET.get();
		steel_plate = STEEL_PLATE.get();
		steel_legs = STEEL_LEGS.get();
		steel_boots = STEEL_BOOTS.get();

		titanium_helmet = TITANIUM_HELMET.get();
		titanium_plate = TITANIUM_PLATE.get();
		titanium_legs = TITANIUM_LEGS.get();
		titanium_boots = TITANIUM_BOOTS.get();

		cobalt_helmet = COBALT_HELMET.get();
		cobalt_plate = COBALT_PLATE.get();
		cobalt_legs = COBALT_LEGS.get();
		cobalt_boots = COBALT_BOOTS.get();

		alloy_helmet = ALLOY_HELMET.get();
		alloy_plate = ALLOY_PLATE.get();
		alloy_legs = ALLOY_LEGS.get();
		alloy_boots = ALLOY_BOOTS.get();

		cmb_helmet = CMB_HELMET.get();
		cmb_plate = CMB_PLATE.get();
		cmb_legs = CMB_LEGS.get();
		cmb_boots = CMB_BOOTS.get();

		schrabidium_helmet = SCHRABIDIUM_HELMET.get();
		schrabidium_plate = SCHRABIDIUM_PLATE.get();
		schrabidium_legs = SCHRABIDIUM_LEGS.get();
		schrabidium_boots = SCHRABIDIUM_BOOTS.get();

		euphemium_helmet = EUPHEMIUM_HELMET.get();
		euphemium_plate = EUPHEMIUM_PLATE.get();
		euphemium_legs = EUPHEMIUM_LEGS.get();
		euphemium_boots = EUPHEMIUM_BOOTS.get();

		asbestos_helmet = ASBESTOS_HELMET.get();
		asbestos_plate = ASBESTOS_PLATE.get();
		asbestos_legs = ASBESTOS_LEGS.get();
		asbestos_boots = ASBESTOS_BOOTS.get();

		rpa_helmet = RPA_HELMET.get();
		rpa_plate = RPA_PLATE.get();
		rpa_legs = RPA_LEGS.get();
		rpa_boots = RPA_BOOTS.get();

		fau_helmet = FAU_HELMET.get();
		fau_plate = FAU_PLATE.get();
		fau_legs = FAU_LEGS.get();
		fau_boots = FAU_BOOTS.get();

		dns_helmet = DNS_HELMET.get();
		dns_plate = DNS_PLATE.get();
		dns_legs = DNS_LEGS.get();
		dns_boots = DNS_BOOTS.get();

		ammo_container = AMMO_CONTAINER.get();
		bottle_rad = BOTTLE_RAD.get();
		missile_nuclear = MISSILE_NUCLEAR.get();
		plate_saturnite = PLATE_SATURNITE.get();
		entanglement_kit = ENTANGLEMENT_KIT.get();
		marshmallow = MARSHMALLOW.get();
		marshmallow_roasted = MARSHMALLOW_ROASTED.get();
		designator = DESIGNATOR.get();

		ams_core_sing = AMS_CORE_SING.get();
		ams_core_wormhole = AMS_CORE_WORMHOLE.get();
		ams_core_thingy = AMS_CORE_THINGY.get();
		ams_core_eyeofharmony = AMS_CORE_EYEOFHARMONY.get();

		upgrade_speed_1 = UPGRADE_SPEED_1.get();
		upgrade_speed_2 = UPGRADE_SPEED_2.get();
		upgrade_speed_3 = UPGRADE_SPEED_3.get();
		upgrade_overdrive_1 = UPGRADE_OVERDRIVE_1.get();
		upgrade_overdrive_2 = UPGRADE_OVERDRIVE_2.get();
		upgrade_overdrive_3 = UPGRADE_OVERDRIVE_3.get();
		upgrade_power_1 = UPGRADE_POWER_1.get();
		upgrade_power_2 = UPGRADE_POWER_2.get();
		upgrade_power_3 = UPGRADE_POWER_3.get();
		upgrade_afterburn_1 = UPGRADE_AFTERBURN_1.get();
		upgrade_afterburn_2 = UPGRADE_AFTERBURN_2.get();
		upgrade_afterburn_3 = UPGRADE_AFTERBURN_3.get();
		upgrade_radius = UPGRADE_RADIUS.get();
		upgrade_health = UPGRADE_HEALTH.get();
		upgrade_smelter = UPGRADE_SMELTER.get();
		upgrade_shredder = UPGRADE_SHREDDER.get();
		upgrade_centrifuge = UPGRADE_CENTRIFUGE.get();
		upgrade_crystallizer = UPGRADE_CRYSTALLIZER.get();
		upgrade_screm = UPGRADE_SCREM.get();
		upgrade_nullifier = UPGRADE_NULLIFIER.get();
		upgrade_gc_speed = UPGRADE_GC_SPEED.get();

		book_lore = BOOK_LORE.get();
		rag = RAG.get();
		rag_damp = RAG_DAMP.get();
		mask_damp = MASK_DAMP.get();
		rag_piss = RAG_PISS.get();
		mask_piss = MASK_PISS.get();

		canister_empty = CANISTER_EMPTY.get();
		canister_full = CANISTER_FULL.get();
		gas_empty = GAS_EMPTY.get();
		gas_full = GAS_FULL.get();
		filter_coal = FILTER_COAL.get();
	}
}
