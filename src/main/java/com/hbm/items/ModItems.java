package com.hbm.items;

import com.hbm.items.food.*;
import com.hbm.items.machine.*;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.items.special.*;
import com.hbm.items.tool.*;
import com.hbm.items.weapon.ItemMissile;
import com.hbm.items.weapon.ItemMissile.FuelType;
import com.hbm.items.weapon.ItemMissile.PartSize;
import com.hbm.items.weapon.ItemMissile.WarheadType;
import com.hbm.items.weapon.ItemMissile.Rarity;
import com.hbm.items.weapon.ItemMissileStandard;
import com.hbm.items.weapon.ItemMissileStandard.MissileFormFactor;
import com.hbm.items.weapon.ItemMissileStandard.MissileTier;
import com.hbm.items.weapon.ItemMissileStandard.MissileFuel;
import com.hbm.items.weapon.ItemCustomMissile;
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

	// Medical & Anti-Rad Treatments
	public static final DeferredItem<ItemRadaway> RADAWAY = register("radaway", () -> new ItemRadaway(ItemRadaway.RadawayType.NORMAL));
	public static final DeferredItem<ItemRadaway> RADAWAY_STRONG = register("radaway_strong", () -> new ItemRadaway(ItemRadaway.RadawayType.STRONG));
	public static final DeferredItem<ItemRadaway> RADAWAY_FLUSH = register("radaway_flush", () -> new ItemRadaway(ItemRadaway.RadawayType.FLUSH));
	public static final DeferredItem<ItemBase> IV_EMPTY = reg("iv_empty");
	public static final DeferredItem<ItemBase> IV_BLOOD = reg("iv_blood");

	public static final DeferredItem<ItemPill> PILL_IODINE = register("pill_iodine", () -> new ItemPill(ItemPill.PillType.IODINE));
	public static final DeferredItem<ItemPill> PLAN_C = register("plan_c", () -> new ItemPill(ItemPill.PillType.PLAN_C));
	public static final DeferredItem<ItemPill> PILL_RED = register("pill_red", () -> new ItemPill(ItemPill.PillType.RED));
	public static final DeferredItem<ItemPill> RADX = register("radx", () -> new ItemPill(ItemPill.PillType.RADX));
	public static final DeferredItem<ItemPill> SIOX = register("siox", () -> new ItemPill(ItemPill.PillType.SIOX));
	public static final DeferredItem<ItemPill> PILL_HERBAL = register("pill_herbal", () -> new ItemPill(ItemPill.PillType.HERBAL));
	public static final DeferredItem<ItemPill> XANAX = register("xanax", () -> new ItemPill(ItemPill.PillType.XANAX));
	public static final DeferredItem<ItemPill> FMN = register("fmn", () -> new ItemPill(ItemPill.PillType.FMN));
	public static final DeferredItem<ItemPill> FIVE_HTP = register("five_htp", () -> new ItemPill(ItemPill.PillType.FIVE_HTP));
	public static final DeferredItem<ItemPill> CHOCOLATE = register("chocolate", () -> new ItemPill(ItemPill.PillType.CHOCOLATE));

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
	private static com.hbm.items.armor.ArmorRPA createRPA(net.minecraft.world.item.ArmorItem.Type type, long consumption) {
		return (com.hbm.items.armor.ArmorRPA) new com.hbm.items.armor.ArmorRPA(com.hbm.items.armor.ModArmorMaterials.RPA, type, new Item.Properties(), 2500000, 10000, consumption, 25)
				.enableVATS(true).setHasGeigerSound(true).setHasHardLanding(true).setRadResist(2.0D).addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.DAMAGE_BOOST, 40, 3));
	}
	public static final DeferredItem<com.hbm.items.armor.ArmorRPA> RPA_HELMET = register("rpa_helmet", () -> createRPA(net.minecraft.world.item.ArmorItem.Type.HELMET, 1000));
	public static final DeferredItem<com.hbm.items.armor.ArmorRPA> RPA_PLATE = register("rpa_plate", () -> createRPA(net.minecraft.world.item.ArmorItem.Type.CHESTPLATE, 10000));
	public static final DeferredItem<com.hbm.items.armor.ArmorRPA> RPA_LEGS = register("rpa_legs", () -> createRPA(net.minecraft.world.item.ArmorItem.Type.LEGGINGS, 10000));
	public static final DeferredItem<com.hbm.items.armor.ArmorRPA> RPA_BOOTS = register("rpa_boots", () -> createRPA(net.minecraft.world.item.ArmorItem.Type.BOOTS, 10000));

	// Power Armor: FAU / Digamma
	private static com.hbm.items.armor.ArmorDigamma createFAU(net.minecraft.world.item.ArmorItem.Type type) {
		return (com.hbm.items.armor.ArmorDigamma) new com.hbm.items.armor.ArmorDigamma(com.hbm.items.armor.ModArmorMaterials.FAU, type, new Item.Properties(), 10000000, 100000, 25000, 1000)
				.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.JUMP, 40, 1)).setHasGeigerSound(true).enableThermalSight(true).setHasHardLanding(true).setRadResist(4.0D);
	}
	public static final DeferredItem<com.hbm.items.armor.ArmorDigamma> FAU_HELMET = register("fau_helmet", () -> createFAU(net.minecraft.world.item.ArmorItem.Type.HELMET));
	public static final DeferredItem<com.hbm.items.armor.ArmorDigamma> FAU_PLATE = register("fau_plate", () -> createFAU(net.minecraft.world.item.ArmorItem.Type.CHESTPLATE));
	public static final DeferredItem<com.hbm.items.armor.ArmorDigamma> FAU_LEGS = register("fau_legs", () -> createFAU(net.minecraft.world.item.ArmorItem.Type.LEGGINGS));
	public static final DeferredItem<com.hbm.items.armor.ArmorDigamma> FAU_BOOTS = register("fau_boots", () -> createFAU(net.minecraft.world.item.ArmorItem.Type.BOOTS));

	// Power Armor: DNS (Dineutronium Nanotech Suit)
	private static com.hbm.items.armor.ArmorDNT createDNS(net.minecraft.world.item.ArmorItem.Type type) {
		return (com.hbm.items.armor.ArmorDNT) new com.hbm.items.armor.ArmorDNT(com.hbm.items.armor.ModArmorMaterials.DNS, type, new Item.Properties(), 1000000000L, 1000000L, 100000L, 115)
				.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.DAMAGE_BOOST, 40, 9))
				.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.DIG_SPEED, 40, 7))
				.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.JUMP, 40, 2))
				.setHasGeigerSound(true).enableVATS(true).enableThermalSight(true).setHasHardLanding(true).setRadResist(5.0D);
	}
	public static final DeferredItem<com.hbm.items.armor.ArmorDNT> DNS_HELMET = register("dns_helmet", () -> createDNS(net.minecraft.world.item.ArmorItem.Type.HELMET));
	public static final DeferredItem<com.hbm.items.armor.ArmorDNT> DNS_PLATE = register("dns_plate", () -> createDNS(net.minecraft.world.item.ArmorItem.Type.CHESTPLATE));
	public static final DeferredItem<com.hbm.items.armor.ArmorDNT> DNS_LEGS = register("dns_legs", () -> createDNS(net.minecraft.world.item.ArmorItem.Type.LEGGINGS));
	public static final DeferredItem<com.hbm.items.armor.ArmorDNT> DNS_BOOTS = register("dns_boots", () -> createDNS(net.minecraft.world.item.ArmorItem.Type.BOOTS));

	// Power Armor: T-51
	private static com.hbm.items.armor.ArmorT51 createT51(net.minecraft.world.item.ArmorItem.Type type) {
		return (com.hbm.items.armor.ArmorT51) new com.hbm.items.armor.ArmorT51(com.hbm.items.armor.ModArmorMaterials.T51, type, new Item.Properties(), 1000000, 10000, 1000, 5)
				.enableVATS(true).setHasGeigerSound(true).setHasHardLanding(true).setRadResist(1.0D).addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.DAMAGE_BOOST, 40, 0));
	}
	public static final DeferredItem<com.hbm.items.armor.ArmorT51> T51_HELMET = register("t51_helmet", () -> createT51(net.minecraft.world.item.ArmorItem.Type.HELMET));
	public static final DeferredItem<com.hbm.items.armor.ArmorT51> T51_PLATE = register("t51_plate", () -> createT51(net.minecraft.world.item.ArmorItem.Type.CHESTPLATE));
	public static final DeferredItem<com.hbm.items.armor.ArmorT51> T51_LEGS = register("t51_legs", () -> createT51(net.minecraft.world.item.ArmorItem.Type.LEGGINGS));
	public static final DeferredItem<com.hbm.items.armor.ArmorT51> T51_BOOTS = register("t51_boots", () -> createT51(net.minecraft.world.item.ArmorItem.Type.BOOTS));

	// Power Armor: HEV Hazard Suit
	private static com.hbm.items.armor.ArmorHEV createHEV(net.minecraft.world.item.ArmorItem.Type type) {
		return (com.hbm.items.armor.ArmorHEV) new com.hbm.items.armor.ArmorHEV(com.hbm.items.armor.ModArmorMaterials.HEV, type, new Item.Properties(), 1000000, 10000, 2500, 0)
				.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.JUMP, 40, 0))
				.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.MOVEMENT_SPEED, 40, 1))
				.setRadResist(2.3D).setHasGeigerSound(true).setHasCustomGeiger(true);
	}
	public static final DeferredItem<com.hbm.items.armor.ArmorHEV> HEV_HELMET = register("hev_helmet", () -> createHEV(net.minecraft.world.item.ArmorItem.Type.HELMET));
	public static final DeferredItem<com.hbm.items.armor.ArmorHEV> HEV_PLATE = register("hev_plate", () -> createHEV(net.minecraft.world.item.ArmorItem.Type.CHESTPLATE));
	public static final DeferredItem<com.hbm.items.armor.ArmorHEV> HEV_LEGS = register("hev_legs", () -> createHEV(net.minecraft.world.item.ArmorItem.Type.LEGGINGS));
	public static final DeferredItem<com.hbm.items.armor.ArmorHEV> HEV_BOOTS = register("hev_boots", () -> createHEV(net.minecraft.world.item.ArmorItem.Type.BOOTS));

	// Power Armor: Blackjack (BJ)
	private static com.hbm.items.armor.ArmorBJ createBJ(net.minecraft.world.item.ArmorItem.Type type) {
		return (com.hbm.items.armor.ArmorBJ) new com.hbm.items.armor.ArmorBJ(com.hbm.items.armor.ModArmorMaterials.BJ, type, new Item.Properties(), 10000000, 10000, 1000, 100)
				.enableVATS(true).enableThermalSight(true).setHasHardLanding(true).setHasGeigerSound(true).setRadResist(1.0D)
				.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.MOVEMENT_SPEED, 40, 1))
				.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.JUMP, 40, 0))
				.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.SATURATION, 40, 0));
	}
	public static final DeferredItem<com.hbm.items.armor.ArmorBJ> BJ_HELMET = register("bj_helmet", () -> createBJ(net.minecraft.world.item.ArmorItem.Type.HELMET));
	public static final DeferredItem<com.hbm.items.armor.ArmorBJ> BJ_PLATE = register("bj_plate", () -> createBJ(net.minecraft.world.item.ArmorItem.Type.CHESTPLATE));
	public static final DeferredItem<com.hbm.items.armor.ArmorBJJetpack> BJ_PLATE_JETPACK = register("bj_plate_jetpack", () -> (com.hbm.items.armor.ArmorBJJetpack) new com.hbm.items.armor.ArmorBJJetpack(com.hbm.items.armor.ModArmorMaterials.BJ, net.minecraft.world.item.ArmorItem.Type.CHESTPLATE, new Item.Properties(), 10000000, 10000, 1000, 100)
			.enableVATS(true).enableThermalSight(true).setHasHardLanding(true).setHasGeigerSound(true).setRadResist(1.0D)
			.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.MOVEMENT_SPEED, 40, 1))
			.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.JUMP, 40, 0))
			.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.SATURATION, 40, 0)));
	public static final DeferredItem<com.hbm.items.armor.ArmorBJ> BJ_LEGS = register("bj_legs", () -> createBJ(net.minecraft.world.item.ArmorItem.Type.LEGGINGS));
	public static final DeferredItem<com.hbm.items.armor.ArmorBJ> BJ_BOOTS = register("bj_boots", () -> createBJ(net.minecraft.world.item.ArmorItem.Type.BOOTS));

	// Power Armor: AJR
	private static com.hbm.items.armor.ArmorAJR createAJR(net.minecraft.world.item.ArmorItem.Type type) {
		return (com.hbm.items.armor.ArmorAJR) new com.hbm.items.armor.ArmorAJR(com.hbm.items.armor.ModArmorMaterials.AJR, type, new Item.Properties(), 2500000, 10000, 2000, 25)
				.enableVATS(true).setHasGeigerSound(true).setHasHardLanding(true).setRadResist(1.3D)
				.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.JUMP, 40, 0))
				.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.DAMAGE_BOOST, 40, 0));
	}
	public static final DeferredItem<com.hbm.items.armor.ArmorAJR> AJR_HELMET = register("ajr_helmet", () -> createAJR(net.minecraft.world.item.ArmorItem.Type.HELMET));
	public static final DeferredItem<com.hbm.items.armor.ArmorAJR> AJR_PLATE = register("ajr_plate", () -> createAJR(net.minecraft.world.item.ArmorItem.Type.CHESTPLATE));
	public static final DeferredItem<com.hbm.items.armor.ArmorAJR> AJR_LEGS = register("ajr_legs", () -> createAJR(net.minecraft.world.item.ArmorItem.Type.LEGGINGS));
	public static final DeferredItem<com.hbm.items.armor.ArmorAJR> AJR_BOOTS = register("ajr_boots", () -> createAJR(net.minecraft.world.item.ArmorItem.Type.BOOTS));


	// Designators & Targeting
	public static final DeferredItem<com.hbm.items.tool.ItemDesignator> DESIGNATOR = register("designator", () -> new com.hbm.items.tool.ItemDesignator(new Item.Properties()));
	public static final DeferredItem<com.hbm.items.tool.ItemDesignatorRange> DESIGNATOR_RANGE = register("designator_range", () -> new com.hbm.items.tool.ItemDesignatorRange(new Item.Properties()));
	public static final DeferredItem<com.hbm.items.tool.ItemDesignatorManual> DESIGNATOR_MANUAL = register("designator_manual", () -> new com.hbm.items.tool.ItemDesignatorManual(new Item.Properties()));

	// Launch Items & Codes
	public static final DeferredItem<ItemBase> LAUNCH_CODE = reg("launch_code");
	public static final DeferredItem<ItemBase> LAUNCH_KEY = reg("launch_key");
	public static final DeferredItem<ItemBase> MISSILE_ASSEMBLY = reg("missile_assembly");
	public static final DeferredItem<ItemBase> ROCKET_FUEL = reg("rocket_fuel");

	// Standard Missiles - Tier 0
	public static final DeferredItem<ItemMissileStandard> MISSILE_MICRO = register("missile_micro", () -> new ItemMissileStandard(new Item.Properties(), MissileFormFactor.MICRO, MissileTier.TIER0));
	public static final DeferredItem<ItemMissileStandard> MISSILE_SCHRABIDIUM = register("missile_schrabidium", () -> new ItemMissileStandard(new Item.Properties(), MissileFormFactor.MICRO, MissileTier.TIER0));
	public static final DeferredItem<ItemMissileStandard> MISSILE_BHOLE = register("missile_bhole", () -> new ItemMissileStandard(new Item.Properties(), MissileFormFactor.MICRO, MissileTier.TIER0));
	public static final DeferredItem<ItemMissileStandard> MISSILE_TAINT = register("missile_taint", () -> new ItemMissileStandard(new Item.Properties(), MissileFormFactor.MICRO, MissileTier.TIER0));
	public static final DeferredItem<ItemMissileStandard> MISSILE_EMP = register("missile_emp", () -> new ItemMissileStandard(new Item.Properties(), MissileFormFactor.MICRO, MissileTier.TIER0));

	// Standard Missiles - Tier 1
	public static final DeferredItem<ItemMissileStandard> MISSILE_GENERIC = register("missile_generic", () -> new ItemMissileStandard(new Item.Properties(), MissileFormFactor.V2, MissileTier.TIER1));
	public static final DeferredItem<ItemMissileStandard> MISSILE_DECOY = register("missile_decoy", () -> new ItemMissileStandard(new Item.Properties(), MissileFormFactor.V2, MissileTier.TIER1));
	public static final DeferredItem<ItemMissileStandard> MISSILE_INCENDIARY = register("missile_incendiary", () -> new ItemMissileStandard(new Item.Properties(), MissileFormFactor.V2, MissileTier.TIER1));
	public static final DeferredItem<ItemMissileStandard> MISSILE_CLUSTER = register("missile_cluster", () -> new ItemMissileStandard(new Item.Properties(), MissileFormFactor.V2, MissileTier.TIER1));
	public static final DeferredItem<ItemMissileStandard> MISSILE_BUSTER = register("missile_buster", () -> new ItemMissileStandard(new Item.Properties(), MissileFormFactor.V2, MissileTier.TIER1));
	public static final DeferredItem<ItemMissileStandard> MISSILE_ANTI_BALLISTIC = register("missile_anti_ballistic", () -> new ItemMissileStandard(new Item.Properties(), MissileFormFactor.ABM, MissileTier.TIER1));
	public static final DeferredItem<ItemMissileStandard> MISSILE_STEALTH = register("missile_stealth", () -> new ItemMissileStandard(new Item.Properties(), MissileFormFactor.STRONG, MissileTier.TIER1));

	// Standard Missiles - Tier 2
	public static final DeferredItem<ItemMissileStandard> MISSILE_STRONG = register("missile_strong", () -> new ItemMissileStandard(new Item.Properties(), MissileFormFactor.STRONG, MissileTier.TIER2));
	public static final DeferredItem<ItemMissileStandard> MISSILE_INCENDIARY_STRONG = register("missile_incendiary_strong", () -> new ItemMissileStandard(new Item.Properties(), MissileFormFactor.STRONG, MissileTier.TIER2));
	public static final DeferredItem<ItemMissileStandard> MISSILE_CLUSTER_STRONG = register("missile_cluster_strong", () -> new ItemMissileStandard(new Item.Properties(), MissileFormFactor.STRONG, MissileTier.TIER2));
	public static final DeferredItem<ItemMissileStandard> MISSILE_BUSTER_STRONG = register("missile_buster_strong", () -> new ItemMissileStandard(new Item.Properties(), MissileFormFactor.STRONG, MissileTier.TIER2));
	public static final DeferredItem<ItemMissileStandard> MISSILE_EMP_STRONG = register("missile_emp_strong", () -> new ItemMissileStandard(new Item.Properties(), MissileFormFactor.STRONG, MissileTier.TIER2));

	// Standard Missiles - Tier 3
	public static final DeferredItem<ItemMissileStandard> MISSILE_BURST = register("missile_burst", () -> new ItemMissileStandard(new Item.Properties(), MissileFormFactor.HUGE, MissileTier.TIER3));
	public static final DeferredItem<ItemMissileStandard> MISSILE_INFERNO = register("missile_inferno", () -> new ItemMissileStandard(new Item.Properties(), MissileFormFactor.HUGE, MissileTier.TIER3));
	public static final DeferredItem<ItemMissileStandard> MISSILE_RAIN = register("missile_rain", () -> new ItemMissileStandard(new Item.Properties(), MissileFormFactor.HUGE, MissileTier.TIER3));
	public static final DeferredItem<ItemMissileStandard> MISSILE_DRILL = register("missile_drill", () -> new ItemMissileStandard(new Item.Properties(), MissileFormFactor.HUGE, MissileTier.TIER3));
	public static final DeferredItem<ItemMissileStandard> MISSILE_SHUTTLE = register("missile_shuttle", () -> new ItemMissileStandard(new Item.Properties(), MissileFormFactor.OTHER, MissileTier.TIER3, MissileFuel.KEROSENE_PEROXIDE));
	public static final DeferredItem<ItemMissileStandard> MISSILE_N2 = register("missile_n2", () -> new ItemMissileStandard(new Item.Properties(), MissileFormFactor.HUGE, MissileTier.TIER3));

	// Standard Missiles - Tier 4
	public static final DeferredItem<ItemMissileStandard> MISSILE_NUCLEAR = register("missile_nuclear", () -> new ItemMissileStandard(new Item.Properties(), MissileFormFactor.ATLAS, MissileTier.TIER4));
	public static final DeferredItem<ItemMissileStandard> MISSILE_NUCLEAR_CLUSTER = register("missile_nuclear_cluster", () -> new ItemMissileStandard(new Item.Properties(), MissileFormFactor.ATLAS, MissileTier.TIER4));
	public static final DeferredItem<ItemMissileStandard> MISSILE_VOLCANO = register("missile_volcano", () -> new ItemMissileStandard(new Item.Properties(), MissileFormFactor.ATLAS, MissileTier.TIER4));
	public static final DeferredItem<ItemMissileStandard> MISSILE_DOOMSDAY = register("missile_doomsday", () -> new ItemMissileStandard(new Item.Properties(), MissileFormFactor.ATLAS, MissileTier.TIER4));
	public static final DeferredItem<ItemMissileStandard> MISSILE_DOOMSDAY_RUSTED = register("missile_doomsday_rusted", () -> new ItemMissileStandard(new Item.Properties(), MissileFormFactor.ATLAS, MissileTier.TIER4).notLaunchable());

	// Custom Modular Missile
	public static final DeferredItem<ItemCustomMissile> MISSILE_CUSTOM = register("missile_custom", () -> new ItemCustomMissile(new Item.Properties()));

	// Warhead Crafting Parts
	public static final DeferredItem<ItemBase> WARHEAD_GENERIC_SMALL = reg("warhead_generic_small");
	public static final DeferredItem<ItemBase> WARHEAD_INCENDIARY_SMALL = reg("warhead_incendiary_small");
	public static final DeferredItem<ItemBase> WARHEAD_CLUSTER_SMALL = reg("warhead_cluster_small");
	public static final DeferredItem<ItemBase> WARHEAD_BUSTER_SMALL = reg("warhead_buster_small");
	public static final DeferredItem<ItemBase> WARHEAD_GENERIC_MEDIUM = reg("warhead_generic_medium");
	public static final DeferredItem<ItemBase> WARHEAD_INCENDIARY_MEDIUM = reg("warhead_incendiary_medium");
	public static final DeferredItem<ItemBase> WARHEAD_CLUSTER_MEDIUM = reg("warhead_cluster_medium");
	public static final DeferredItem<ItemBase> WARHEAD_BUSTER_MEDIUM = reg("warhead_buster_medium");
	public static final DeferredItem<ItemBase> WARHEAD_GENERIC_LARGE = reg("warhead_generic_large");
	public static final DeferredItem<ItemBase> WARHEAD_INCENDIARY_LARGE = reg("warhead_incendiary_large");
	public static final DeferredItem<ItemBase> WARHEAD_CLUSTER_LARGE = reg("warhead_cluster_large");
	public static final DeferredItem<ItemBase> WARHEAD_BUSTER_LARGE = reg("warhead_buster_large");
	public static final DeferredItem<ItemBase> WARHEAD_N2 = reg("warhead_n2");
	public static final DeferredItem<ItemBase> WARHEAD_NUCLEAR = reg("warhead_nuclear");
	public static final DeferredItem<ItemBase> WARHEAD_MIRVLET = reg("warhead_mirvlet");
	public static final DeferredItem<ItemBase> WARHEAD_MIRV = reg("warhead_mirv");
	public static final DeferredItem<ItemBase> WARHEAD_VOLCANO = reg("warhead_volcano");
	public static final DeferredItem<ItemBase> WARHEAD_THERMO_ENDO = reg("warhead_thermo_endo");
	public static final DeferredItem<ItemBase> WARHEAD_THERMO_EXO = reg("warhead_thermo_exo");

	// Thrusters & Hardware Components
	public static final DeferredItem<ItemBase> THRUSTER_SMALL = reg("thruster_small");
	public static final DeferredItem<ItemBase> THRUSTER_MEDIUM = reg("thruster_medium");
	public static final DeferredItem<ItemBase> THRUSTER_LARGE = reg("thruster_large");
	public static final DeferredItem<ItemBase> CAP_ALUMINIUM = reg("cap_aluminium");
	public static final DeferredItem<ItemBase> FINS_FLAT = reg("fins_flat");
	public static final DeferredItem<ItemBase> FINS_SMALL_STEEL = reg("fins_small_steel");
	public static final DeferredItem<ItemBase> FINS_BIG_STEEL = reg("fins_big_steel");
	public static final DeferredItem<ItemBase> FINS_TRI_STEEL = reg("fins_tri_steel");
	public static final DeferredItem<ItemBase> FINS_QUAD_TITANIUM = reg("fins_quad_titanium");
	public static final DeferredItem<ItemBase> SPHERE_STEEL = reg("sphere_steel");
	public static final DeferredItem<ItemBase> PEDESTAL_STEEL = reg("pedestal_steel");
	public static final DeferredItem<ItemBase> DYSFUNCTIONAL_REACTOR = reg("dysfunctional_reactor");
	public static final DeferredItem<ItemBase> ROTOR_STEEL = reg("rotor_steel");
	public static final DeferredItem<ItemBase> GENERATOR_STEEL = reg("generator_steel");
	public static final DeferredItem<ItemBase> SEG_10 = reg("seg_10");
	public static final DeferredItem<ItemBase> SEG_15 = reg("seg_15");
	public static final DeferredItem<ItemBase> SEG_20 = reg("seg_20");
	public static final DeferredItem<ItemBase> FUEL_TANK_SMALL = reg("fuel_tank_small");
	public static final DeferredItem<ItemBase> FUEL_TANK_MEDIUM = reg("fuel_tank_medium");
	public static final DeferredItem<ItemBase> FUEL_TANK_LARGE = reg("fuel_tank_large");
	public static final DeferredItem<ItemBase> TANK_STEEL = reg("tank_steel");

	// Modular Missile Parts: Thrusters
	public static final DeferredItem<ItemMissile> MP_THRUSTER_10_KEROSENE = register("mp_thruster_10_kerosene", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeThruster(FuelType.KEROSENE, 1F, 1.5F, PartSize.SIZE_10).setHealth(10F));
	public static final DeferredItem<ItemMissile> MP_THRUSTER_10_SOLID = register("mp_thruster_10_solid", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeThruster(FuelType.SOLID, 1F, 1.5F, PartSize.SIZE_10).setHealth(15F));
	public static final DeferredItem<ItemMissile> MP_THRUSTER_10_XENON = register("mp_thruster_10_xenon", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeThruster(FuelType.XENON, 1F, 1.5F, PartSize.SIZE_10).setHealth(5F));
	public static final DeferredItem<ItemMissile> MP_THRUSTER_15_KEROSENE = register("mp_thruster_15_kerosene", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeThruster(FuelType.KEROSENE, 1F, 7.5F, PartSize.SIZE_15).setHealth(15F));
	public static final DeferredItem<ItemMissile> MP_THRUSTER_15_KEROSENE_DUAL = register("mp_thruster_15_kerosene_dual", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeThruster(FuelType.KEROSENE, 1F, 6.5F, PartSize.SIZE_15).setHealth(15F));
	public static final DeferredItem<ItemMissile> MP_THRUSTER_15_KEROSENE_TRIPLE = register("mp_thruster_15_kerosene_triple", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeThruster(FuelType.KEROSENE, 1F, 5F, PartSize.SIZE_15).setHealth(15F));
	public static final DeferredItem<ItemMissile> MP_THRUSTER_15_SOLID = register("mp_thruster_15_solid", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeThruster(FuelType.SOLID, 1F, 5F, PartSize.SIZE_15).setHealth(20F));
	public static final DeferredItem<ItemMissile> MP_THRUSTER_15_SOLID_HEXDECUPLE = register("mp_thruster_15_solid_hexdecuple", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeThruster(FuelType.SOLID, 1F, 7F, PartSize.SIZE_15).setHealth(25F).setRarity(Rarity.UNCOMMON));
	public static final DeferredItem<ItemMissile> MP_THRUSTER_15_HYDROGEN = register("mp_thruster_15_hydrogen", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeThruster(FuelType.HYDROGEN, 1F, 7.5F, PartSize.SIZE_15).setHealth(20F));
	public static final DeferredItem<ItemMissile> MP_THRUSTER_15_HYDROGEN_DUAL = register("mp_thruster_15_hydrogen_dual", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeThruster(FuelType.HYDROGEN, 1F, 5.0F, PartSize.SIZE_15).setHealth(15F));
	public static final DeferredItem<ItemMissile> MP_THRUSTER_15_BALEFIRE_SHORT = register("mp_thruster_15_balefire_short", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeThruster(FuelType.BALEFIRE, 1F, 5F, PartSize.SIZE_15).setHealth(25F));
	public static final DeferredItem<ItemMissile> MP_THRUSTER_15_BALEFIRE = register("mp_thruster_15_balefire", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeThruster(FuelType.BALEFIRE, 1F, 6.5F, PartSize.SIZE_15).setHealth(25F));
	public static final DeferredItem<ItemMissile> MP_THRUSTER_15_BALEFIRE_LARGE = register("mp_thruster_15_balefire_large", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeThruster(FuelType.BALEFIRE, 1F, 7.0F, PartSize.SIZE_15).setHealth(35F));
	public static final DeferredItem<ItemMissile> MP_THRUSTER_15_BALEFIRE_LARGE_RAD = register("mp_thruster_15_balefire_large_rad", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeThruster(FuelType.BALEFIRE, 1F, 7.5F, PartSize.SIZE_15).setAuthor("The Master").setHealth(35F).setRarity(Rarity.UNCOMMON));
	public static final DeferredItem<ItemMissile> MP_THRUSTER_20_KEROSENE = register("mp_thruster_20_kerosene", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeThruster(FuelType.KEROSENE, 1F, 100F, PartSize.SIZE_20).setHealth(30F));
	public static final DeferredItem<ItemMissile> MP_THRUSTER_20_KEROSENE_DUAL = register("mp_thruster_20_kerosene_dual", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeThruster(FuelType.KEROSENE, 1F, 100F, PartSize.SIZE_20).setHealth(30F));
	public static final DeferredItem<ItemMissile> MP_THRUSTER_20_KEROSENE_TRIPLE = register("mp_thruster_20_kerosene_triple", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeThruster(FuelType.KEROSENE, 1F, 100F, PartSize.SIZE_20).setHealth(30F));
	public static final DeferredItem<ItemMissile> MP_THRUSTER_20_SOLID = register("mp_thruster_20_solid", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeThruster(FuelType.SOLID, 1F, 100F, PartSize.SIZE_20).setHealth(35F));
	public static final DeferredItem<ItemMissile> MP_THRUSTER_20_SOLID_MULTI = register("mp_thruster_20_solid_multi", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeThruster(FuelType.SOLID, 1F, 100F, PartSize.SIZE_20).setHealth(35F));
	public static final DeferredItem<ItemMissile> MP_THRUSTER_20_SOLID_MULTIER = register("mp_thruster_20_solid_multier", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeThruster(FuelType.SOLID, 1F, 100F, PartSize.SIZE_20).setHealth(35F));

	// Modular Missile Parts: Stability / Fins
	public static final DeferredItem<ItemMissile> MP_STABILITY_10_FLAT = register("mp_stability_10_flat", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeStability(0.5F, PartSize.SIZE_10).setHealth(10F));
	public static final DeferredItem<ItemMissile> MP_STABILITY_10_CRUISE = register("mp_stability_10_cruise", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeStability(0.25F, PartSize.SIZE_10).setHealth(5F));
	public static final DeferredItem<ItemMissile> MP_STABILITY_10_SPACE = register("mp_stability_10_space", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeStability(0.35F, PartSize.SIZE_10).setHealth(5F).setRarity(Rarity.COMMON));
	public static final DeferredItem<ItemMissile> MP_STABILITY_15_FLAT = register("mp_stability_15_flat", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeStability(0.5F, PartSize.SIZE_15).setHealth(10F));
	public static final DeferredItem<ItemMissile> MP_STABILITY_15_THIN = register("mp_stability_15_thin", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeStability(0.35F, PartSize.SIZE_15).setHealth(5F));
	public static final DeferredItem<ItemMissile> MP_STABILITY_15_SOYUZ = register("mp_stability_15_soyuz", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeStability(0.25F, PartSize.SIZE_15).setHealth(15F).setRarity(Rarity.COMMON));
	public static final DeferredItem<ItemMissile> MP_STABILITY_20_FLAT = register("mp_stability_20_flat", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeStability(0.5F, PartSize.SIZE_20));

	// Modular Missile Parts: Fuselages
	public static final DeferredItem<ItemMissile> MP_FUSELAGE_10_KEROSENE = register("mp_fuselage_10_kerosene", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeFuselage(FuelType.KEROSENE, 2500F, 1000, PartSize.SIZE_10, PartSize.SIZE_10).setHealth(20F));
	public static final DeferredItem<ItemMissile> MP_FUSELAGE_10_SOLID = register("mp_fuselage_10_solid", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeFuselage(FuelType.SOLID, 2500F, 1000, PartSize.SIZE_10, PartSize.SIZE_10).setHealth(25F));
	public static final DeferredItem<ItemMissile> MP_FUSELAGE_10_XENON = register("mp_fuselage_10_xenon", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeFuselage(FuelType.XENON, 5000F, 1000, PartSize.SIZE_10, PartSize.SIZE_10).setHealth(20F));
	public static final DeferredItem<ItemMissile> MP_FUSELAGE_10_LONG_KEROSENE = register("mp_fuselage_10_long_kerosene", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeFuselage(FuelType.KEROSENE, 5000F, 1000, PartSize.SIZE_10, PartSize.SIZE_10).setHealth(30F));
	public static final DeferredItem<ItemMissile> MP_FUSELAGE_10_LONG_SOLID = register("mp_fuselage_10_long_solid", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeFuselage(FuelType.SOLID, 5000F, 1000, PartSize.SIZE_10, PartSize.SIZE_10).setHealth(35F));
	public static final DeferredItem<ItemMissile> MP_FUSELAGE_10_15_KEROSENE = register("mp_fuselage_10_15_kerosene", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeFuselage(FuelType.KEROSENE, 10000F, 1000, PartSize.SIZE_10, PartSize.SIZE_15).setHealth(40F));
	public static final DeferredItem<ItemMissile> MP_FUSELAGE_10_15_SOLID = register("mp_fuselage_10_15_solid", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeFuselage(FuelType.SOLID, 10000F, 1000, PartSize.SIZE_10, PartSize.SIZE_15).setHealth(40F));
	public static final DeferredItem<ItemMissile> MP_FUSELAGE_10_15_HYDROGEN = register("mp_fuselage_10_15_hydrogen", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeFuselage(FuelType.HYDROGEN, 10000F, 1000, PartSize.SIZE_10, PartSize.SIZE_15).setHealth(40F));
	public static final DeferredItem<ItemMissile> MP_FUSELAGE_10_15_BALEFIRE = register("mp_fuselage_10_15_balefire", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeFuselage(FuelType.BALEFIRE, 10000F, 1000, PartSize.SIZE_10, PartSize.SIZE_15).setHealth(40F));
	public static final DeferredItem<ItemMissile> MP_FUSELAGE_15_KEROSENE = register("mp_fuselage_15_kerosene", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeFuselage(FuelType.KEROSENE, 15000F, 1000, PartSize.SIZE_15, PartSize.SIZE_15).setHealth(50F));
	public static final DeferredItem<ItemMissile> MP_FUSELAGE_15_SOLID = register("mp_fuselage_15_solid", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeFuselage(FuelType.SOLID, 15000F, 1000, PartSize.SIZE_15, PartSize.SIZE_15).setHealth(60F));
	public static final DeferredItem<ItemMissile> MP_FUSELAGE_15_HYDROGEN = register("mp_fuselage_15_hydrogen", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeFuselage(FuelType.HYDROGEN, 15000F, 1000, PartSize.SIZE_15, PartSize.SIZE_15).setHealth(50F));
	public static final DeferredItem<ItemMissile> MP_FUSELAGE_15_BALEFIRE = register("mp_fuselage_15_balefire", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeFuselage(FuelType.BALEFIRE, 15000F, 1000, PartSize.SIZE_15, PartSize.SIZE_15).setHealth(75F));
	public static final DeferredItem<ItemMissile> MP_FUSELAGE_15_20_KEROSENE = register("mp_fuselage_15_20_kerosene", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeFuselage(FuelType.KEROSENE, 20000F, 1000, PartSize.SIZE_15, PartSize.SIZE_20).setHealth(70F));
	public static final DeferredItem<ItemMissile> MP_FUSELAGE_15_20_SOLID = register("mp_fuselage_15_20_solid", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeFuselage(FuelType.SOLID, 20000F, 1000, PartSize.SIZE_15, PartSize.SIZE_20).setHealth(70F));

	// Modular Missile Parts: Warheads
	public static final DeferredItem<ItemMissile> MP_WARHEAD_10_HE = register("mp_warhead_10_he", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeWarhead(WarheadType.HE, 15F, 1.5F, PartSize.SIZE_10).setHealth(5F));
	public static final DeferredItem<ItemMissile> MP_WARHEAD_10_INCENDIARY = register("mp_warhead_10_incendiary", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeWarhead(WarheadType.INC, 15F, 1.5F, PartSize.SIZE_10).setHealth(5F));
	public static final DeferredItem<ItemMissile> MP_WARHEAD_10_BUSTER = register("mp_warhead_10_buster", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeWarhead(WarheadType.BUSTER, 15F, 1.5F, PartSize.SIZE_10).setHealth(5F));
	public static final DeferredItem<ItemMissile> MP_WARHEAD_10_NUCLEAR = register("mp_warhead_10_nuclear", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeWarhead(WarheadType.NUCLEAR, 35F, 1.5F, PartSize.SIZE_10).setTitle("Tater Tot").setHealth(10F));
	public static final DeferredItem<ItemMissile> MP_WARHEAD_10_NUCLEAR_LARGE = register("mp_warhead_10_nuclear_large", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeWarhead(WarheadType.NUCLEAR, 75F, 2.5F, PartSize.SIZE_10).setTitle("Chernobyl Boris").setHealth(15F));
	public static final DeferredItem<ItemMissile> MP_WARHEAD_10_TAINT = register("mp_warhead_10_taint", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeWarhead(WarheadType.TAINT, 15F, 1.5F, PartSize.SIZE_10).setHealth(20F).setRarity(Rarity.UNCOMMON));
	public static final DeferredItem<ItemMissile> MP_WARHEAD_10_CLOUD = register("mp_warhead_10_cloud", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeWarhead(WarheadType.CLOUD, 15F, 1.5F, PartSize.SIZE_10).setHealth(20F).setRarity(Rarity.RARE));
	public static final DeferredItem<ItemMissile> MP_WARHEAD_15_HE = register("mp_warhead_15_he", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeWarhead(WarheadType.HE, 50F, 2.5F, PartSize.SIZE_15).setHealth(10F));
	public static final DeferredItem<ItemMissile> MP_WARHEAD_15_INCENDIARY = register("mp_warhead_15_incendiary", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeWarhead(WarheadType.INC, 35F, 2.5F, PartSize.SIZE_15).setHealth(10F));
	public static final DeferredItem<ItemMissile> MP_WARHEAD_15_NUCLEAR = register("mp_warhead_15_nuclear", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeWarhead(WarheadType.NUCLEAR, 125F, 5F, PartSize.SIZE_15).setTitle("Auntie Bertha").setHealth(15F));
	public static final DeferredItem<ItemMissile> MP_WARHEAD_15_THERMO = register("mp_warhead_15_thermo", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeWarhead(WarheadType.TX, 250F, 6.5F, PartSize.SIZE_15).setHealth(25F).setRarity(Rarity.RARE));
	public static final DeferredItem<ItemMissile> MP_WARHEAD_15_MIRV = register("mp_warhead_15_mirv", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeWarhead(WarheadType.MIRV, 500F, 7.0F, PartSize.SIZE_15).setHealth(20F).setRarity(Rarity.LEGENDARY));
	public static final DeferredItem<ItemMissile> MP_WARHEAD_15_BOXCAR = register("mp_warhead_15_boxcar", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeWarhead(WarheadType.TX, 500F, 7.5F, PartSize.SIZE_15).setHealth(35F).setRarity(Rarity.LEGENDARY));
	public static final DeferredItem<ItemMissile> MP_WARHEAD_15_N2 = register("mp_warhead_15_n2", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeWarhead(WarheadType.N2, 100F, 5F, PartSize.SIZE_15).setHealth(20F).setRarity(Rarity.RARE));
	public static final DeferredItem<ItemMissile> MP_WARHEAD_15_BALEFIRE = register("mp_warhead_15_balefire", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeWarhead(WarheadType.BALEFIRE, 100F, 7.5F, PartSize.SIZE_15).setHealth(15F).setRarity(Rarity.LEGENDARY));
	public static final DeferredItem<ItemMissile> MP_WARHEAD_15_VOLCANO = register("mp_warhead_15_volcano", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeWarhead(WarheadType.VOLCANO, 10F, 6.5F, PartSize.SIZE_15).setHealth(25F).setRarity(Rarity.LEGENDARY));
	public static final DeferredItem<ItemMissile> MP_WARHEAD_15_TURBINE = register("mp_warhead_15_turbine", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeWarhead(WarheadType.TURBINE, 200F, 5F, PartSize.SIZE_15).setHealth(250F).setRarity(Rarity.SEWS_CLOTHES_AND_SUCKS_HORSE_COCK));

	// Modular Missile Parts: Chips
	public static final DeferredItem<ItemMissile> MP_CHIP_1 = register("mp_c_1", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeChip(0.1F));
	public static final DeferredItem<ItemMissile> MP_CHIP_2 = register("mp_c_2", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeChip(0.05F));
	public static final DeferredItem<ItemMissile> MP_CHIP_3 = register("mp_c_3", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeChip(0.01F));
	public static final DeferredItem<ItemMissile> MP_CHIP_4 = register("mp_c_4", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeChip(0.005F));
	public static final DeferredItem<ItemMissile> MP_CHIP_5 = register("mp_c_5", () -> (ItemMissile) new ItemMissile(new Item.Properties()).makeChip(0.0F));

	// Missile Skins
	public static final DeferredItem<ItemBase> MISSILE_SKIN_CAMO = reg("missile_skin_camo");
	public static final DeferredItem<ItemBase> MISSILE_SKIN_DESERT = reg("missile_skin_desert");
	public static final DeferredItem<ItemBase> MISSILE_SKIN_FLAMES = reg("missile_skin_flames");
	public static final DeferredItem<ItemBase> MISSILE_SKIN_MANLY_PINK = reg("missile_skin_manly_pink");
	public static final DeferredItem<ItemBase> MISSILE_SKIN_ORANGE_INSULATION = reg("missile_skin_orange_insulation");
	public static final DeferredItem<ItemBase> MISSILE_SKIN_SLEEK = reg("missile_skin_sleek");
	public static final DeferredItem<ItemBase> MISSILE_SKIN_SOVIET_GLORY = reg("missile_skin_soviet_glory");
	public static final DeferredItem<ItemBase> MISSILE_SKIN_SOVIET_STANK = reg("missile_skin_soviet_stank");
	public static final DeferredItem<ItemBase> MISSILE_SKIN_METAL = reg("missile_skin_metal");

	// Nuclear Bomb Components (Special)
	public static final DeferredItem<ItemBase> FLEIJA_CORE = reg("fleija_core");
	public static final DeferredItem<ItemBase> FLEIJA_IGNITER = reg("fleija_igniter");
	public static final DeferredItem<ItemBase> FLEIJA_PROPELLANT = reg("fleija_propellant");

	public static final DeferredItem<ItemBase> SOLINIUM_CORE = reg("solinium_core");
	public static final DeferredItem<ItemBase> SOLINIUM_IGNITER = reg("solinium_igniter");
	public static final DeferredItem<ItemBase> SOLINIUM_PROPELLANT = reg("solinium_propellant");

	public static final DeferredItem<ItemBase> N2_CHARGE = reg("n2_charge");
	public static final DeferredItem<ItemBase> EGG_BALEFIRE = reg("egg_balefire");
	public static final DeferredItem<ItemBase> BATTERY_SPARK = reg("battery_spark");
	public static final DeferredItem<ItemBase> BATTERY_TRIXITE = reg("battery_trixite");
	public static final DeferredItem<ItemBase> INGOT_EUPHEMIUM = reg("ingot_euphemium");

	public static final DeferredItem<ItemBase> INGOT_U235 = reg("ingot_u235");
	public static final DeferredItem<ItemBase> INGOT_PU239 = reg("ingot_pu239");
	public static final DeferredItem<ItemBase> INGOT_NEPTUNIUM = reg("ingot_neptunium");
	public static final DeferredItem<ItemBase> INGOT_SCHRABIDIUM = reg("ingot_schrabidium");
	public static final DeferredItem<ItemBase> INGOT_TITANIUM = reg("ingot_titanium");
	public static final DeferredItem<ItemBase> LITHIUM = reg("lithium");
	public static final DeferredItem<ItemBase> INGOT_SEMTEX = reg("ingot_semtex");
	public static final DeferredItem<ItemBase> INGOT_C4 = reg("ingot_c4");

	public static final DeferredItem<ItemBase> AMMO_CONTAINER = reg("ammo_container");
	public static final DeferredItem<ItemBase> BOTTLE_RAD = reg("bottle_rad");
	public static final DeferredItem<ItemBase> PLATE_SATURNITE = reg("plate_saturnite");
	public static final DeferredItem<ItemBase> PLATE_IRON = reg("plate_iron");
	public static final DeferredItem<ItemBase> PLATE_GOLD = reg("plate_gold");
	public static final DeferredItem<ItemBase> PLATE_TITANIUM = reg("plate_titanium");
	public static final DeferredItem<ItemBase> PLATE_STEEL = reg("plate_steel");
	public static final DeferredItem<ItemBase> PLATE_LEAD = reg("plate_lead");
	public static final DeferredItem<ItemBase> PLATE_COPPER = reg("plate_copper");
	public static final DeferredItem<ItemBase> PLATE_ALUMINIUM = reg("plate_aluminium");
	public static final DeferredItem<ItemBase> PLATE_ADVANCED_ALLOY = reg("plate_advanced_alloy");
	public static final DeferredItem<ItemBase> PLATE_SCHRABIDIUM = reg("plate_schrabidium");
	public static final DeferredItem<ItemBase> PLATE_COMBINE_STEEL = reg("plate_combine_steel");
	public static final DeferredItem<ItemBase> PLATE_TUNGSTEN = reg("plate_tungsten");
	public static final DeferredItem<ItemBase> ENTANGLEMENT_KIT = reg("entanglement_kit");
	public static final DeferredItem<ItemBase> MARSHMALLOW = reg("marshmallow");
	public static final DeferredItem<ItemBase> MARSHMALLOW_ROASTED = reg("marshmallow_roasted");

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
	public static final DeferredItem<ItemBase> LIGNITE = reg("lignite");
	public static final DeferredItem<ItemBase> POWDER_LIGNITE = reg("powder_lignite");
	public static final DeferredItem<ItemBase> DUST_WOOD = reg("dust_wood");
	public static final DeferredItem<ItemBase> BIOMASS = reg("biomass");
	public static final DeferredItem<ItemBase> SULFUR = reg("sulfur");
	public static final DeferredItem<ItemBase> NITER = reg("niter");
	public static final DeferredItem<ItemBase> FLUORITE = reg("fluorite");
	public static final DeferredItem<ItemBase> INGOT_THORIUM = reg("ingot_thorium");
	public static final DeferredItem<ItemBase> INGOT_BERYLLIUM = reg("ingot_beryllium");
	public static final DeferredItem<ItemBase> YELLOWCAKE = reg("yellowcake");

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

	// RBMK Pellets
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKPellet> RBMK_PELLET_UEU = register("rbmk_pellet_ueu", () -> new com.hbm.items.machine.ItemRBMKPellet("Pellet (UEU)"));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKPellet> RBMK_PELLET_MEU = register("rbmk_pellet_meu", () -> new com.hbm.items.machine.ItemRBMKPellet("Pellet (MEU)"));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKPellet> RBMK_PELLET_HEU233 = register("rbmk_pellet_heu233", () -> new com.hbm.items.machine.ItemRBMKPellet("Pellet (HEU-233)"));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKPellet> RBMK_PELLET_HEU235 = register("rbmk_pellet_heu235", () -> new com.hbm.items.machine.ItemRBMKPellet("Pellet (HEU-235)"));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKPellet> RBMK_PELLET_UZH = register("rbmk_pellet_uzh", () -> new com.hbm.items.machine.ItemRBMKPellet("Pellet (UZH)"));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKPellet> RBMK_PELLET_THMEU = register("rbmk_pellet_thmeu", () -> new com.hbm.items.machine.ItemRBMKPellet("Pellet (Th-MEU)"));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKPellet> RBMK_PELLET_LEP = register("rbmk_pellet_lep", () -> new com.hbm.items.machine.ItemRBMKPellet("Pellet (LEP)"));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKPellet> RBMK_PELLET_MEP = register("rbmk_pellet_mep", () -> new com.hbm.items.machine.ItemRBMKPellet("Pellet (MEP)"));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKPellet> RBMK_PELLET_HEP239 = register("rbmk_pellet_hep", () -> new com.hbm.items.machine.ItemRBMKPellet("Pellet (HEP-239)"));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKPellet> RBMK_PELLET_HEP241 = register("rbmk_pellet_hep241", () -> new com.hbm.items.machine.ItemRBMKPellet("Pellet (HEP-241)"));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKPellet> RBMK_PELLET_LEA = register("rbmk_pellet_lea", () -> new com.hbm.items.machine.ItemRBMKPellet("Pellet (LEA)"));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKPellet> RBMK_PELLET_MEA = register("rbmk_pellet_mea", () -> new com.hbm.items.machine.ItemRBMKPellet("Pellet (MEA)"));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKPellet> RBMK_PELLET_HEA241 = register("rbmk_pellet_hea241", () -> new com.hbm.items.machine.ItemRBMKPellet("Pellet (HEA-241)"));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKPellet> RBMK_PELLET_HEA242 = register("rbmk_pellet_hea242", () -> new com.hbm.items.machine.ItemRBMKPellet("Pellet (HEA-242)"));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKPellet> RBMK_PELLET_MEN = register("rbmk_pellet_men", () -> new com.hbm.items.machine.ItemRBMKPellet("Pellet (MEN)"));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKPellet> RBMK_PELLET_HEN = register("rbmk_pellet_hen", () -> new com.hbm.items.machine.ItemRBMKPellet("Pellet (HEN)"));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKPellet> RBMK_PELLET_MOX = register("rbmk_pellet_mox", () -> new com.hbm.items.machine.ItemRBMKPellet("Pellet (MOX)"));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKPellet> RBMK_PELLET_LES = register("rbmk_pellet_les", () -> new com.hbm.items.machine.ItemRBMKPellet("Pellet (LES)"));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKPellet> RBMK_PELLET_MES = register("rbmk_pellet_mes", () -> new com.hbm.items.machine.ItemRBMKPellet("Pellet (MES)"));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKPellet> RBMK_PELLET_HES = register("rbmk_pellet_hes", () -> new com.hbm.items.machine.ItemRBMKPellet("Pellet (HES)"));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKPellet> RBMK_PELLET_LEAUS = register("rbmk_pellet_leaus", () -> new com.hbm.items.machine.ItemRBMKPellet("Pellet (LEAUS)"));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKPellet> RBMK_PELLET_HEAUS = register("rbmk_pellet_heaus", () -> new com.hbm.items.machine.ItemRBMKPellet("Pellet (HEAUS)"));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKPellet> RBMK_PELLET_RA226BE = register("rbmk_pellet_ra226be", () -> new com.hbm.items.machine.ItemRBMKPellet("Pellet (Ra-226/Be)"));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKPellet> RBMK_PELLET_PO210BE = register("rbmk_pellet_po210be", () -> new com.hbm.items.machine.ItemRBMKPellet("Pellet (Po-210/Be)"));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKPellet> RBMK_PELLET_PU238BE = register("rbmk_pellet_pu238be", () -> new com.hbm.items.machine.ItemRBMKPellet("Pellet (Pu-238/Be)"));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKPellet> RBMK_PELLET_BALEFIRE_GOLD = register("rbmk_pellet_balefire_gold", () -> new com.hbm.items.machine.ItemRBMKPellet("Pellet (Flashgold)"));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKPellet> RBMK_PELLET_FLASHLEAD = register("rbmk_pellet_flashlead", () -> new com.hbm.items.machine.ItemRBMKPellet("Pellet (Flashlead)"));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKPellet> RBMK_PELLET_ZFB_BISMUTH = register("rbmk_pellet_zfb_bismuth", () -> new com.hbm.items.machine.ItemRBMKPellet("Pellet (ZFB Bismuth)"));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKPellet> RBMK_PELLET_ZFB_PU241 = register("rbmk_pellet_zfb_pu241", () -> new com.hbm.items.machine.ItemRBMKPellet("Pellet (ZFB Pu-241)"));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKPellet> RBMK_PELLET_ZFB_AM_MIX = register("rbmk_pellet_zfb_am_mix", () -> new com.hbm.items.machine.ItemRBMKPellet("Pellet (ZFB Am-Mix)"));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKPellet> RBMK_PELLET_BALEFIRE = register("rbmk_pellet_balefire", () -> new com.hbm.items.machine.ItemRBMKPellet("Pellet (Balefire)"));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKPellet> RBMK_PELLET_DRX = register("rbmk_pellet_drx", () -> new com.hbm.items.machine.ItemRBMKPellet("Pellet (DRX)"));

	// RBMK Fuel Rods & Hardware
	public static final DeferredItem<ItemBase> RBMK_FUEL_EMPTY = reg("rbmk_fuel_empty");
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKLid> RBMK_LID = register("rbmk_lid", () -> new com.hbm.items.machine.ItemRBMKLid(new Item.Properties(), false));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKLid> RBMK_LID_GLASS = register("rbmk_lid_glass", () -> new com.hbm.items.machine.ItemRBMKLid(new Item.Properties(), true));
	public static final DeferredItem<com.hbm.items.tool.ItemRBMKTool> RBMK_TOOL = register("rbmk_tool", () -> new com.hbm.items.tool.ItemRBMKTool(new Item.Properties()));

	public static final DeferredItem<com.hbm.items.machine.ItemRBMKRod> RBMK_FUEL_UEU = register("rbmk_fuel_ueu", () -> (com.hbm.items.machine.ItemRBMKRod) new com.hbm.items.machine.ItemRBMKRod("Fuel Rod (UEU)").setYield(100000000D).setStats(15).setFunction(com.hbm.items.machine.ItemRBMKRod.EnumBurnFunc.LOG_TEN).setDepletionFunction(com.hbm.items.machine.ItemRBMKRod.EnumDepleteFunc.RAISING_SLOPE).setHeat(0.65).setMeltingPoint(2865).setTint(0x4A6B3A));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKRod> RBMK_FUEL_MEU = register("rbmk_fuel_meu", () -> (com.hbm.items.machine.ItemRBMKRod) new com.hbm.items.machine.ItemRBMKRod("Fuel Rod (MEU)").setYield(100000000D).setStats(20).setFunction(com.hbm.items.machine.ItemRBMKRod.EnumBurnFunc.LOG_TEN).setDepletionFunction(com.hbm.items.machine.ItemRBMKRod.EnumDepleteFunc.RAISING_SLOPE).setHeat(0.65).setMeltingPoint(2865).setTint(0x4A6B3A));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKRod> RBMK_FUEL_HEU233 = register("rbmk_fuel_heu233", () -> (com.hbm.items.machine.ItemRBMKRod) new com.hbm.items.machine.ItemRBMKRod("Fuel Rod (HEU-233)").setYield(100000000D).setStats(27.5D).setFunction(com.hbm.items.machine.ItemRBMKRod.EnumBurnFunc.LINEAR).setHeat(1.25D).setMeltingPoint(2865).setTint(0x4A6B3A));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKRod> RBMK_FUEL_HEU235 = register("rbmk_fuel_heu235", () -> (com.hbm.items.machine.ItemRBMKRod) new com.hbm.items.machine.ItemRBMKRod("Fuel Rod (HEU-235)").setYield(100000000D).setStats(50).setFunction(com.hbm.items.machine.ItemRBMKRod.EnumBurnFunc.SQUARE_ROOT).setMeltingPoint(2865).setTint(0x4A6B3A));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKRod> RBMK_FUEL_UZH = register("rbmk_fuel_uzh", () -> (com.hbm.items.machine.ItemRBMKRod) new com.hbm.items.machine.ItemRBMKRod("Fuel Rod (UZH)").setYield(50_000_000D).setStats(30).setFunction(com.hbm.items.machine.ItemRBMKRod.EnumBurnFunc.LOG_TEN).setDepletionFunction(com.hbm.items.machine.ItemRBMKRod.EnumDepleteFunc.GENTLE_SLOPE).setHeat(0.75).setHeatCoeff(1000D, 500D).setDiffusion(0.1D).setMeltingPoint(1845).setTint(0x7077AF));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKRod> RBMK_FUEL_THMEU = register("rbmk_fuel_thmeu", () -> (com.hbm.items.machine.ItemRBMKRod) new com.hbm.items.machine.ItemRBMKRod("Fuel Rod (Th-MEU)").setYield(100000000D).setStats(20).setFunction(com.hbm.items.machine.ItemRBMKRod.EnumBurnFunc.PLATEU).setDepletionFunction(com.hbm.items.machine.ItemRBMKRod.EnumDepleteFunc.BOOSTED_SLOPE).setHeat(0.65D).setMeltingPoint(3350).setTint(0x472314));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKRod> RBMK_FUEL_LEP = register("rbmk_fuel_lep", () -> (com.hbm.items.machine.ItemRBMKRod) new com.hbm.items.machine.ItemRBMKRod("Fuel Rod (LEP)").setYield(100000000D).setStats(35).setFunction(com.hbm.items.machine.ItemRBMKRod.EnumBurnFunc.LOG_TEN).setDepletionFunction(com.hbm.items.machine.ItemRBMKRod.EnumDepleteFunc.RAISING_SLOPE).setHeat(0.75D).setMeltingPoint(2744).setTint(0x9E7356));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKRod> RBMK_FUEL_MEP = register("rbmk_fuel_mep", () -> (com.hbm.items.machine.ItemRBMKRod) new com.hbm.items.machine.ItemRBMKRod("Fuel Rod (MEP)").setYield(100000000D).setStats(35).setFunction(com.hbm.items.machine.ItemRBMKRod.EnumBurnFunc.SQUARE_ROOT).setMeltingPoint(2744).setTint(0x9E7356));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKRod> RBMK_FUEL_HEP239 = register("rbmk_fuel_hep", () -> (com.hbm.items.machine.ItemRBMKRod) new com.hbm.items.machine.ItemRBMKRod("Fuel Rod (HEP-239)").setYield(100000000D).setStats(30).setFunction(com.hbm.items.machine.ItemRBMKRod.EnumBurnFunc.LINEAR).setHeat(1.25D).setMeltingPoint(2744).setTint(0x9E7356));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKRod> RBMK_FUEL_HEP241 = register("rbmk_fuel_hep241", () -> (com.hbm.items.machine.ItemRBMKRod) new com.hbm.items.machine.ItemRBMKRod("Fuel Rod (HEP-241)").setYield(100000000D).setStats(40).setFunction(com.hbm.items.machine.ItemRBMKRod.EnumBurnFunc.LINEAR).setHeat(1.5D).setMeltingPoint(2744).setTint(0x9E7356));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKRod> RBMK_FUEL_LEA = register("rbmk_fuel_lea", () -> (com.hbm.items.machine.ItemRBMKRod) new com.hbm.items.machine.ItemRBMKRod("Fuel Rod (LEA)").setYield(100000000D).setStats(20).setFunction(com.hbm.items.machine.ItemRBMKRod.EnumBurnFunc.LOG_TEN).setDepletionFunction(com.hbm.items.machine.ItemRBMKRod.EnumDepleteFunc.BOOSTED_SLOPE).setHeat(0.5D).setMeltingPoint(2448).setTint(0x403F40));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKRod> RBMK_FUEL_MEA = register("rbmk_fuel_mea", () -> (com.hbm.items.machine.ItemRBMKRod) new com.hbm.items.machine.ItemRBMKRod("Fuel Rod (MEA)").setYield(100000000D).setStats(35).setFunction(com.hbm.items.machine.ItemRBMKRod.EnumBurnFunc.SQUARE_ROOT).setHeat(1.0D).setMeltingPoint(2448).setTint(0x403F40));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKRod> RBMK_FUEL_HEA241 = register("rbmk_fuel_hea241", () -> (com.hbm.items.machine.ItemRBMKRod) new com.hbm.items.machine.ItemRBMKRod("Fuel Rod (HEA-241)").setYield(100000000D).setStats(30).setFunction(com.hbm.items.machine.ItemRBMKRod.EnumBurnFunc.LINEAR).setHeat(1.25D).setMeltingPoint(2448).setTint(0x403F40));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKRod> RBMK_FUEL_HEA242 = register("rbmk_fuel_hea242", () -> (com.hbm.items.machine.ItemRBMKRod) new com.hbm.items.machine.ItemRBMKRod("Fuel Rod (HEA-242)").setYield(100000000D).setStats(40).setFunction(com.hbm.items.machine.ItemRBMKRod.EnumBurnFunc.LINEAR).setHeat(1.5D).setMeltingPoint(2448).setTint(0x403F40));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKRod> RBMK_FUEL_MEN = register("rbmk_fuel_men", () -> (com.hbm.items.machine.ItemRBMKRod) new com.hbm.items.machine.ItemRBMKRod("Fuel Rod (MEN)").setYield(100000000D).setStats(35).setFunction(com.hbm.items.machine.ItemRBMKRod.EnumBurnFunc.SQUARE_ROOT).setHeat(1.0D).setMeltingPoint(2550).setTint(0x354451));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKRod> RBMK_FUEL_HEN = register("rbmk_fuel_hen", () -> (com.hbm.items.machine.ItemRBMKRod) new com.hbm.items.machine.ItemRBMKRod("Fuel Rod (HEN)").setYield(100000000D).setStats(40).setFunction(com.hbm.items.machine.ItemRBMKRod.EnumBurnFunc.LINEAR).setHeat(1.5D).setMeltingPoint(2550).setTint(0x354451));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKRod> RBMK_FUEL_MOX = register("rbmk_fuel_mox", () -> (com.hbm.items.machine.ItemRBMKRod) new com.hbm.items.machine.ItemRBMKRod("Fuel Rod (MOX)").setYield(100000000D).setStats(25).setFunction(com.hbm.items.machine.ItemRBMKRod.EnumBurnFunc.PLATEU).setDepletionFunction(com.hbm.items.machine.ItemRBMKRod.EnumDepleteFunc.BOOSTED_SLOPE).setHeat(0.85D).setMeltingPoint(2865).setTint(0x605A44));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKRod> RBMK_FUEL_LES = register("rbmk_fuel_les", () -> (com.hbm.items.machine.ItemRBMKRod) new com.hbm.items.machine.ItemRBMKRod("Fuel Rod (LES)").setYield(100000000D).setStats(50).setFunction(com.hbm.items.machine.ItemRBMKRod.EnumBurnFunc.PLATEU).setDepletionFunction(com.hbm.items.machine.ItemRBMKRod.EnumDepleteFunc.RAISING_SLOPE).setHeat(2.0D).setMeltingPoint(5000).setTint(0x00FF88));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKRod> RBMK_FUEL_MES = register("rbmk_fuel_mes", () -> (com.hbm.items.machine.ItemRBMKRod) new com.hbm.items.machine.ItemRBMKRod("Fuel Rod (MES)").setYield(100000000D).setStats(75).setFunction(com.hbm.items.machine.ItemRBMKRod.EnumBurnFunc.PLATEU).setDepletionFunction(com.hbm.items.machine.ItemRBMKRod.EnumDepleteFunc.RAISING_SLOPE).setHeat(2.5D).setMeltingPoint(5000).setTint(0x00FF88));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKRod> RBMK_FUEL_HES = register("rbmk_fuel_hes", () -> (com.hbm.items.machine.ItemRBMKRod) new com.hbm.items.machine.ItemRBMKRod("Fuel Rod (HES)").setYield(100000000D).setStats(100).setFunction(com.hbm.items.machine.ItemRBMKRod.EnumBurnFunc.PLATEU).setDepletionFunction(com.hbm.items.machine.ItemRBMKRod.EnumDepleteFunc.RAISING_SLOPE).setHeat(3.0D).setMeltingPoint(5000).setTint(0x00FF88));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKRod> RBMK_FUEL_LEAUS = register("rbmk_fuel_leaus", () -> (com.hbm.items.machine.ItemRBMKRod) new com.hbm.items.machine.ItemRBMKRod("Fuel Rod (LEAUS)").setYield(100000000D).setStats(30).setFunction(com.hbm.items.machine.ItemRBMKRod.EnumBurnFunc.SIGMOID).setDepletionFunction(com.hbm.items.machine.ItemRBMKRod.EnumDepleteFunc.LINEAR).setXenon(0.05D, 50D).setHeat(1.5D).setMeltingPoint(7029).setTint(0xE4A834));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKRod> RBMK_FUEL_HEAUS = register("rbmk_fuel_heaus", () -> (com.hbm.items.machine.ItemRBMKRod) new com.hbm.items.machine.ItemRBMKRod("Fuel Rod (HEAUS)").setYield(100000000D).setStats(35).setFunction(com.hbm.items.machine.ItemRBMKRod.EnumBurnFunc.LINEAR).setXenon(0.05D, 50D).setHeat(1.5D).setMeltingPoint(5211).setTint(0xE4A834));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKRod> RBMK_FUEL_RA226BE = register("rbmk_fuel_ra226be", () -> (com.hbm.items.machine.ItemRBMKRod) new com.hbm.items.machine.ItemRBMKRod("Fuel Rod (Ra-226/Be)").setYield(100000000D).setStats(0D, 20).setFunction(com.hbm.items.machine.ItemRBMKRod.EnumBurnFunc.PASSIVE).setDepletionFunction(com.hbm.items.machine.ItemRBMKRod.EnumDepleteFunc.LINEAR).setXenon(0.0D, 50D).setHeat(0.035D).setDiffusion(0.5D).setMeltingPoint(700).setNeutronTypes(com.hbm.tileentity.machine.rbmk.IRBMKFluxReceiver.NType.SLOW, com.hbm.tileentity.machine.rbmk.IRBMKFluxReceiver.NType.SLOW).setTint(0x656A60));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKRod> RBMK_FUEL_PO210BE = register("rbmk_fuel_po210be", () -> (com.hbm.items.machine.ItemRBMKRod) new com.hbm.items.machine.ItemRBMKRod("Fuel Rod (Po-210/Be)").setYield(25000000D).setStats(0D, 50).setFunction(com.hbm.items.machine.ItemRBMKRod.EnumBurnFunc.PASSIVE).setDepletionFunction(com.hbm.items.machine.ItemRBMKRod.EnumDepleteFunc.LINEAR).setXenon(0.0D, 50D).setHeat(0.1D).setDiffusion(0.05D).setMeltingPoint(1287).setNeutronTypes(com.hbm.tileentity.machine.rbmk.IRBMKFluxReceiver.NType.SLOW, com.hbm.tileentity.machine.rbmk.IRBMKFluxReceiver.NType.SLOW).setTint(0x3B4842));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKRod> RBMK_FUEL_PU238BE = register("rbmk_fuel_pu238be", () -> (com.hbm.items.machine.ItemRBMKRod) new com.hbm.items.machine.ItemRBMKRod("Fuel Rod (Pu-238/Be)").setYield(50000000D).setStats(40, 40).setFunction(com.hbm.items.machine.ItemRBMKRod.EnumBurnFunc.SQUARE_ROOT).setHeat(0.1D).setDiffusion(0.05D).setMeltingPoint(1287).setNeutronTypes(com.hbm.tileentity.machine.rbmk.IRBMKFluxReceiver.NType.SLOW, com.hbm.tileentity.machine.rbmk.IRBMKFluxReceiver.NType.SLOW).setTint(0x9E7356));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKRod> RBMK_FUEL_BALEFIRE_GOLD = register("rbmk_fuel_balefire_gold", () -> (com.hbm.items.machine.ItemRBMKRod) new com.hbm.items.machine.ItemRBMKRod("Fuel Rod (Flashgold)").setYield(100000000D).setStats(50, 10).setFunction(com.hbm.items.machine.ItemRBMKRod.EnumBurnFunc.ARCH).setDepletionFunction(com.hbm.items.machine.ItemRBMKRod.EnumDepleteFunc.LINEAR).setXenon(0.0D, 50D).setMeltingPoint(2000).setTint(0xFFE438));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKRod> RBMK_FUEL_FLASHLEAD = register("rbmk_fuel_flashlead", () -> (com.hbm.items.machine.ItemRBMKRod) new com.hbm.items.machine.ItemRBMKRod("Fuel Rod (Flashlead)").setYield(250000000D).setStats(40, 50).setFunction(com.hbm.items.machine.ItemRBMKRod.EnumBurnFunc.ARCH).setDepletionFunction(com.hbm.items.machine.ItemRBMKRod.EnumDepleteFunc.LINEAR).setXenon(0.0D, 50D).setMeltingPoint(2050).setTint(0x7B7B87));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKRod> RBMK_FUEL_ZFB_BISMUTH = register("rbmk_fuel_zfb_bismuth", () -> (com.hbm.items.machine.ItemRBMKRod) new com.hbm.items.machine.ItemRBMKRod("Fuel Rod (ZFB Bismuth)").setYield(50000000D).setStats(20).setFunction(com.hbm.items.machine.ItemRBMKRod.EnumBurnFunc.SQUARE_ROOT).setHeat(1.75D).setMeltingPoint(2744).setTint(0x606560));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKRod> RBMK_FUEL_ZFB_PU241 = register("rbmk_fuel_zfb_pu241", () -> (com.hbm.items.machine.ItemRBMKRod) new com.hbm.items.machine.ItemRBMKRod("Fuel Rod (ZFB Pu-241)").setYield(50000000D).setStats(20).setFunction(com.hbm.items.machine.ItemRBMKRod.EnumBurnFunc.SQUARE_ROOT).setMeltingPoint(2865).setTint(0x606560));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKRod> RBMK_FUEL_ZFB_AM_MIX = register("rbmk_fuel_zfb_am_mix", () -> (com.hbm.items.machine.ItemRBMKRod) new com.hbm.items.machine.ItemRBMKRod("Fuel Rod (ZFB Am-Mix)").setYield(50000000D).setStats(20).setFunction(com.hbm.items.machine.ItemRBMKRod.EnumBurnFunc.LINEAR).setHeat(1.75D).setMeltingPoint(2744).setTint(0x606560));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKRod> RBMK_FUEL_BALEFIRE = register("rbmk_fuel_balefire", () -> (com.hbm.items.machine.ItemRBMKRod) new com.hbm.items.machine.ItemRBMKRod("Fuel Rod (Balefire)").setYield(100000000D).setStats(100, 35).setFunction(com.hbm.items.machine.ItemRBMKRod.EnumBurnFunc.LINEAR).setXenon(0.0D, 50D).setHeat(3D).setMeltingPoint(3652).setTint(0xB2FF1B));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKRod> RBMK_FUEL_DRX = register("rbmk_fuel_drx", () -> (com.hbm.items.machine.ItemRBMKRod) new com.hbm.items.machine.ItemRBMKRod("Fuel Rod (DRX)").setYield(10000000D).setStats(1000, 10).setFunction(com.hbm.items.machine.ItemRBMKRod.EnumBurnFunc.QUADRATIC).setHeat(0.1D).setMeltingPoint(100000).setTint(0xD77276));
	public static final DeferredItem<com.hbm.items.machine.ItemRBMKRod> RBMK_FUEL_TEST = register("rbmk_fuel_test", () -> (com.hbm.items.machine.ItemRBMKRod) new com.hbm.items.machine.ItemRBMKRod("Fuel Rod (THE VOICES)").setYield(1000000D).setStats(100).setFunction(com.hbm.items.machine.ItemRBMKRod.EnumBurnFunc.EXPERIMENTAL).setHeat(1.0D).setMeltingPoint(100000));

	// Zirnox Fuel Rods
	public static final DeferredItem<com.hbm.items.machine.ItemZirnoxRod> ZIRNOX_NATURAL_URANIUM = register("zirnox_natural_uranium", () -> new com.hbm.items.machine.ItemZirnoxRod(com.hbm.items.machine.ItemZirnoxRod.EnumZirnoxType.NATURAL_URANIUM_FUEL));
	public static final DeferredItem<com.hbm.items.machine.ItemZirnoxRod> ZIRNOX_URANIUM = register("zirnox_uranium", () -> new com.hbm.items.machine.ItemZirnoxRod(com.hbm.items.machine.ItemZirnoxRod.EnumZirnoxType.URANIUM_FUEL));
	public static final DeferredItem<com.hbm.items.machine.ItemZirnoxRod> ZIRNOX_TH232 = register("zirnox_th232", () -> new com.hbm.items.machine.ItemZirnoxRod(com.hbm.items.machine.ItemZirnoxRod.EnumZirnoxType.TH232_FUEL));
	public static final DeferredItem<com.hbm.items.machine.ItemZirnoxRod> ZIRNOX_THORIUM = register("zirnox_thorium", () -> new com.hbm.items.machine.ItemZirnoxRod(com.hbm.items.machine.ItemZirnoxRod.EnumZirnoxType.THORIUM_FUEL));
	public static final DeferredItem<com.hbm.items.machine.ItemZirnoxRod> ZIRNOX_MOX = register("zirnox_mox", () -> new com.hbm.items.machine.ItemZirnoxRod(com.hbm.items.machine.ItemZirnoxRod.EnumZirnoxType.MOX_FUEL));
	public static final DeferredItem<com.hbm.items.machine.ItemZirnoxRod> ZIRNOX_PLUTONIUM = register("zirnox_plutonium", () -> new com.hbm.items.machine.ItemZirnoxRod(com.hbm.items.machine.ItemZirnoxRod.EnumZirnoxType.PLUTONIUM_FUEL));
	public static final DeferredItem<com.hbm.items.machine.ItemZirnoxRod> ZIRNOX_U233 = register("zirnox_u233", () -> new com.hbm.items.machine.ItemZirnoxRod(com.hbm.items.machine.ItemZirnoxRod.EnumZirnoxType.U233_FUEL));
	public static final DeferredItem<com.hbm.items.machine.ItemZirnoxRod> ZIRNOX_U235 = register("zirnox_u235", () -> new com.hbm.items.machine.ItemZirnoxRod(com.hbm.items.machine.ItemZirnoxRod.EnumZirnoxType.U235_FUEL));
	public static final DeferredItem<com.hbm.items.machine.ItemZirnoxRod> ZIRNOX_LES = register("zirnox_les", () -> new com.hbm.items.machine.ItemZirnoxRod(com.hbm.items.machine.ItemZirnoxRod.EnumZirnoxType.LES_FUEL));
	public static final DeferredItem<com.hbm.items.machine.ItemZirnoxRod> ZIRNOX_LITHIUM = register("zirnox_lithium", () -> new com.hbm.items.machine.ItemZirnoxRod(com.hbm.items.machine.ItemZirnoxRod.EnumZirnoxType.LITHIUM_FUEL));
	public static final DeferredItem<com.hbm.items.machine.ItemZirnoxRod> ZIRNOX_ZFB_MOX = register("zirnox_zfb_mox", () -> new com.hbm.items.machine.ItemZirnoxRod(com.hbm.items.machine.ItemZirnoxRod.EnumZirnoxType.ZFB_MOX_FUEL));

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
	public static Item lignite;
	public static Item powder_lignite;
	public static Item dust_wood;
	public static Item biomass;
	public static Item sulfur;
	public static Item niter;
	public static Item fluorite;
	public static Item ingot_thorium;
	public static Item ingot_beryllium;
	public static Item yellowcake;
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
	public static Item plate_iron;
	public static Item plate_gold;
	public static Item plate_titanium;
	public static Item plate_steel;
	public static Item plate_lead;
	public static Item plate_copper;
	public static Item plate_aluminium;
	public static Item plate_advanced_alloy;
	public static Item plate_schrabidium;
	public static Item plate_combine_steel;
	public static Item plate_tungsten;
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

	// Missiles & Launch
	public static Item designator_range;
	public static Item designator_manual;
	public static Item launch_code;
	public static Item launch_key;
	public static Item missile_assembly;
	public static Item rocket_fuel;

	public static Item missile_micro;
	public static Item missile_schrabidium;
	public static Item missile_bhole;
	public static Item missile_taint;
	public static Item missile_emp;
	public static Item missile_generic;
	public static Item missile_decoy;
	public static Item missile_incendiary;
	public static Item missile_cluster;
	public static Item missile_buster;
	public static Item missile_anti_ballistic;
	public static Item missile_stealth;
	public static Item missile_strong;
	public static Item missile_incendiary_strong;
	public static Item missile_cluster_strong;
	public static Item missile_buster_strong;
	public static Item missile_emp_strong;
	public static Item missile_burst;
	public static Item missile_inferno;
	public static Item missile_rain;
	public static Item missile_drill;
	public static Item missile_shuttle;
	public static Item missile_n2;
	public static Item missile_nuclear_cluster;
	public static Item missile_volcano;
	public static Item missile_doomsday;
	public static Item missile_doomsday_rusted;
	public static Item missile_custom;

	public static Item warhead_generic_small;
	public static Item warhead_incendiary_small;
	public static Item warhead_cluster_small;
	public static Item warhead_buster_small;
	public static Item warhead_generic_medium;
	public static Item warhead_incendiary_medium;
	public static Item warhead_cluster_medium;
	public static Item warhead_buster_medium;
	public static Item warhead_generic_large;
	public static Item warhead_incendiary_large;
	public static Item warhead_cluster_large;
	public static Item warhead_buster_large;
	public static Item warhead_n2;
	public static Item warhead_nuclear;
	public static Item warhead_mirvlet;
	public static Item warhead_mirv;
	public static Item warhead_volcano;
	public static Item warhead_thermo_endo;
	public static Item warhead_thermo_exo;

	public static Item thruster_small;
	public static Item thruster_medium;
	public static Item thruster_large;
	public static Item cap_aluminium;
	public static Item fins_flat;
	public static Item fins_small_steel;
	public static Item fins_big_steel;
	public static Item fins_tri_steel;
	public static Item fins_quad_titanium;
	public static Item sphere_steel;
	public static Item pedestal_steel;
	public static Item dysfunctional_reactor;
	public static Item rotor_steel;
	public static Item generator_steel;
	public static Item seg_10;
	public static Item seg_15;
	public static Item seg_20;
	public static Item fuel_tank_small;
	public static Item fuel_tank_medium;
	public static Item fuel_tank_large;
	public static Item tank_steel;

	public static Item fleija_core;
	public static Item fleija_igniter;
	public static Item fleija_propellant;
	public static Item solinium_core;
	public static Item solinium_igniter;
	public static Item solinium_propellant;
	public static Item n2_charge;
	public static Item egg_balefire;
	public static Item battery_spark;
	public static Item battery_trixite;
	public static Item ingot_euphemium;

	public static Item radaway;
	public static Item radaway_strong;
	public static Item radaway_flush;
	public static Item iv_empty;
	public static Item iv_blood;

	public static Item pill_iodine;
	public static Item plan_c;
	public static Item pill_red;
	public static Item radx;
	public static Item siox;
	public static Item pill_herbal;
	public static Item xanax;
	public static Item fmn;
	public static Item five_htp;
	public static Item chocolate;
	public static Item gun_9mm;
	public static Item gun_44;
	public static Item gun_50bmg;
	public static Item gun_12ga;
	public static Item gun_fatman;
	public static Item ammo_9mm;
	public static Item ammo_44;
	public static Item ammo_50bmg;
	public static Item ammo_12ga;
	public static Item ammo_mini_nuke;
	public static Item grenade_generic;
	public static Item grenade_nuclear;

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

		radaway = RADAWAY.get();
		radaway_strong = RADAWAY_STRONG.get();
		radaway_flush = RADAWAY_FLUSH.get();
		iv_empty = IV_EMPTY.get();
		iv_blood = IV_BLOOD.get();

		pill_iodine = PILL_IODINE.get();
		plan_c = PLAN_C.get();
		pill_red = PILL_RED.get();
		radx = RADX.get();
		siox = SIOX.get();
		pill_herbal = PILL_HERBAL.get();
		xanax = XANAX.get();
		fmn = FMN.get();
		five_htp = FIVE_HTP.get();
		chocolate = CHOCOLATE.get();

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
		lignite = LIGNITE.get();
		powder_lignite = POWDER_LIGNITE.get();
		dust_wood = DUST_WOOD.get();
		biomass = BIOMASS.get();
		sulfur = SULFUR.get();
		niter = NITER.get();
		fluorite = FLUORITE.get();
		ingot_thorium = INGOT_THORIUM.get();
		ingot_beryllium = INGOT_BERYLLIUM.get();
		yellowcake = YELLOWCAKE.get();

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

		gun_9mm = GUN_9MM.get();
		gun_44 = GUN_44.get();
		gun_50bmg = GUN_50BMG.get();
		gun_12ga = GUN_12GA.get();
		gun_fatman = GUN_FATMAN.get();
		ammo_9mm = AMMO_9MM.get();
		ammo_44 = AMMO_44.get();
		ammo_50bmg = AMMO_50BMG.get();
		ammo_12ga = AMMO_12GA.get();
		ammo_mini_nuke = AMMO_MINI_NUKE.get();
		grenade_generic = GRENADE_GENERIC.get();
		grenade_nuclear = GRENADE_NUCLEAR.get();

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

		designator_range = DESIGNATOR_RANGE.get();
		designator_manual = DESIGNATOR_MANUAL.get();
		launch_code = LAUNCH_CODE.get();
		launch_key = LAUNCH_KEY.get();
		missile_assembly = MISSILE_ASSEMBLY.get();
		rocket_fuel = ROCKET_FUEL.get();

		missile_micro = MISSILE_MICRO.get();
		missile_schrabidium = MISSILE_SCHRABIDIUM.get();
		missile_bhole = MISSILE_BHOLE.get();
		missile_taint = MISSILE_TAINT.get();
		missile_emp = MISSILE_EMP.get();
		missile_generic = MISSILE_GENERIC.get();
		missile_decoy = MISSILE_DECOY.get();
		missile_incendiary = MISSILE_INCENDIARY.get();
		missile_cluster = MISSILE_CLUSTER.get();
		missile_buster = MISSILE_BUSTER.get();
		missile_anti_ballistic = MISSILE_ANTI_BALLISTIC.get();
		missile_stealth = MISSILE_STEALTH.get();
		missile_strong = MISSILE_STRONG.get();
		missile_incendiary_strong = MISSILE_INCENDIARY_STRONG.get();
		missile_cluster_strong = MISSILE_CLUSTER_STRONG.get();
		missile_buster_strong = MISSILE_BUSTER_STRONG.get();
		missile_emp_strong = MISSILE_EMP_STRONG.get();
		missile_burst = MISSILE_BURST.get();
		missile_inferno = MISSILE_INFERNO.get();
		missile_rain = MISSILE_RAIN.get();
		missile_drill = MISSILE_DRILL.get();
		missile_shuttle = MISSILE_SHUTTLE.get();
		missile_n2 = MISSILE_N2.get();
		missile_nuclear_cluster = MISSILE_NUCLEAR_CLUSTER.get();
		missile_volcano = MISSILE_VOLCANO.get();
		missile_doomsday = MISSILE_DOOMSDAY.get();
		missile_doomsday_rusted = MISSILE_DOOMSDAY_RUSTED.get();
		missile_custom = MISSILE_CUSTOM.get();

		warhead_generic_small = WARHEAD_GENERIC_SMALL.get();
		warhead_incendiary_small = WARHEAD_INCENDIARY_SMALL.get();
		warhead_cluster_small = WARHEAD_CLUSTER_SMALL.get();
		warhead_buster_small = WARHEAD_BUSTER_SMALL.get();
		warhead_generic_medium = WARHEAD_GENERIC_MEDIUM.get();
		warhead_incendiary_medium = WARHEAD_INCENDIARY_MEDIUM.get();
		warhead_cluster_medium = WARHEAD_CLUSTER_MEDIUM.get();
		warhead_buster_medium = WARHEAD_BUSTER_MEDIUM.get();
		warhead_generic_large = WARHEAD_GENERIC_LARGE.get();
		warhead_incendiary_large = WARHEAD_INCENDIARY_LARGE.get();
		warhead_cluster_large = WARHEAD_CLUSTER_LARGE.get();
		warhead_buster_large = WARHEAD_BUSTER_LARGE.get();
		warhead_n2 = WARHEAD_N2.get();
		warhead_nuclear = WARHEAD_NUCLEAR.get();
		warhead_mirvlet = WARHEAD_MIRVLET.get();
		warhead_mirv = WARHEAD_MIRV.get();
		warhead_volcano = WARHEAD_VOLCANO.get();
		warhead_thermo_endo = WARHEAD_THERMO_ENDO.get();
		warhead_thermo_exo = WARHEAD_THERMO_EXO.get();

		thruster_small = THRUSTER_SMALL.get();
		thruster_medium = THRUSTER_MEDIUM.get();
		thruster_large = THRUSTER_LARGE.get();
		cap_aluminium = CAP_ALUMINIUM.get();
		fins_flat = FINS_FLAT.get();
		fins_small_steel = FINS_SMALL_STEEL.get();
		fins_big_steel = FINS_BIG_STEEL.get();
		fins_tri_steel = FINS_TRI_STEEL.get();
		fins_quad_titanium = FINS_QUAD_TITANIUM.get();
		sphere_steel = SPHERE_STEEL.get();
		pedestal_steel = PEDESTAL_STEEL.get();
		dysfunctional_reactor = DYSFUNCTIONAL_REACTOR.get();
		rotor_steel = ROTOR_STEEL.get();
		generator_steel = GENERATOR_STEEL.get();
		seg_10 = SEG_10.get();
		seg_15 = SEG_15.get();
		seg_20 = SEG_20.get();
		fuel_tank_small = FUEL_TANK_SMALL.get();
		fuel_tank_medium = FUEL_TANK_MEDIUM.get();
		fuel_tank_large = FUEL_TANK_LARGE.get();
		tank_steel = TANK_STEEL.get();

		fleija_core = FLEIJA_CORE.get();
		fleija_igniter = FLEIJA_IGNITER.get();
		fleija_propellant = FLEIJA_PROPELLANT.get();
		solinium_core = SOLINIUM_CORE.get();
		solinium_igniter = SOLINIUM_IGNITER.get();
		solinium_propellant = SOLINIUM_PROPELLANT.get();
		n2_charge = N2_CHARGE.get();
		egg_balefire = EGG_BALEFIRE.get();
		battery_spark = BATTERY_SPARK.get();
		battery_trixite = BATTERY_TRIXITE.get();
		ingot_euphemium = INGOT_EUPHEMIUM.get();

		plate_iron = PLATE_IRON.get();
		plate_gold = PLATE_GOLD.get();
		plate_titanium = PLATE_TITANIUM.get();
		plate_steel = PLATE_STEEL.get();
		plate_lead = PLATE_LEAD.get();
		plate_copper = PLATE_COPPER.get();
		plate_aluminium = PLATE_ALUMINIUM.get();
		plate_advanced_alloy = PLATE_ADVANCED_ALLOY.get();
		plate_schrabidium = PLATE_SCHRABIDIUM.get();
		plate_combine_steel = PLATE_COMBINE_STEEL.get();
		plate_tungsten = PLATE_TUNGSTEN.get();
	}
}
