package com.hbm.creativetabs;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.items.armor.ArmorFSB;
import com.hbm.items.weapon.sedna.ItemAmmo;
import com.hbm.items.weapon.sedna.ItemGunBaseSedna;
import com.hbm.items.weapon.grenade.ItemGrenade;
import com.hbm.items.weapon.ItemMissile;
import com.hbm.items.weapon.ItemMissileStandard;
import com.hbm.items.weapon.ItemCustomMissile;
import com.hbm.items.food.ItemPill;
import com.hbm.items.food.ItemRadaway;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.items.machine.ItemZirnoxRod;
import com.hbm.items.machine.ItemRBMKRod;
import com.hbm.items.tool.ItemDesignator;
import com.hbm.items.tool.ItemDosimeter;
import com.hbm.items.tool.ItemGeigerCounter;
import com.hbm.items.tool.ItemSurveyScanner;
import com.hbm.main.MainRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MainRegistry.MODID);

    private static Supplier<ItemStack> makeIcon(Supplier<? extends Item> itemSupplier, Item fallback) {
        return () -> {
            try {
                Item item = itemSupplier.get();
                if (item != null && item != Items.AIR) {
                    return new ItemStack(item);
                }
            } catch (Exception ignored) {
            }
            return new ItemStack(fallback);
        };
    }

    private static Supplier<ItemStack> makeBlockIcon(Supplier<? extends Block> blockSupplier, Item fallback) {
        return () -> {
            try {
                Block block = blockSupplier.get();
                if (block != null && block.asItem() != Items.AIR) {
                    return new ItemStack(block);
                }
            } catch (Exception ignored) {
            }
            return new ItemStack(fallback);
        };
    }

    // 1. Blocks Tab (All Mod Blocks)
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BLOCK_TAB = CREATIVE_MODE_TABS.register("tab_blocks",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.tabBlocks"))
                    .icon(makeBlockIcon(ModBlocks.CONCRETE, Items.BRICKS))
                    .displayItems((parameters, output) -> {
                        for (DeferredHolder<Block, ? extends Block> entry : ModBlocks.BLOCKS.getEntries()) {
                            Block block = entry.get();
                            if (block != null && block.asItem() != Items.AIR) {
                                output.accept(block);
                            }
                        }
                    })
                    .build());

    // 2. Resource & Materials Tab (Ingots, Powders, Nuggets, Crystals, Plates, Ore items)
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> RESOURCE_TAB = CREATIVE_MODE_TABS.register("tab_resource",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.tabResource"))
                    .icon(makeIcon(ModItems.INGOT_URANIUM, Items.IRON_INGOT))
                    .displayItems((parameters, output) -> {
                        for (DeferredHolder<Item, ? extends Item> entry : ModItems.ITEMS.getEntries()) {
                            Item item = entry.get();
                            if (item != null && item != Items.AIR) {
                                ResourceLocation id = BuiltInRegistries.ITEM.getKey(item);
                                String name = id != null ? id.getPath() : "";
                                if (name.startsWith("ingot_") || name.startsWith("powder_") || name.startsWith("nugget_")
                                        || name.startsWith("crystal_") || name.startsWith("plate_") || name.startsWith("dust_")
                                        || name.equals("sulfur") || name.equals("niter") || name.equals("fluorite")
                                        || name.equals("lignite") || name.equals("yellowcake") || name.equals("scrap")
                                        || name.equals("biomass") || name.equals("cell") || name.equals("rod_empty")) {
                                    output.accept(item);
                                }
                            }
                        }
                    })
                    .build());

    // 3. Machine Parts & Components Tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> PARTS_TAB = CREATIVE_MODE_TABS.register("tab_parts",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.tabParts"))
                    .icon(makeIcon(ModItems.ROTOR_STEEL, Items.COMPASS))
                    .displayItems((parameters, output) -> {
                        for (DeferredHolder<Item, ? extends Item> entry : ModItems.ITEMS.getEntries()) {
                            Item item = entry.get();
                            if (item != null && item != Items.AIR) {
                                ResourceLocation id = BuiltInRegistries.ITEM.getKey(item);
                                String name = id != null ? id.getPath() : "";
                                if (name.startsWith("thruster_") || name.startsWith("fins_") || name.startsWith("mp_")
                                        || name.startsWith("rotor_") || name.startsWith("generator_") || name.startsWith("seg_")
                                        || name.startsWith("fuel_tank_") || name.startsWith("cap_") || name.startsWith("sphere_")
                                        || name.startsWith("pedestal_") || name.equals("tank_steel") || name.equals("filter_coal")
                                        || name.equals("dysfunctional_reactor") || name.startsWith("battery_")) {
                                    output.accept(item);
                                }
                            }
                        }
                    })
                    .build());

    // 4. Reactor Control & Upgrades Tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CONTROL_TAB = CREATIVE_MODE_TABS.register("tab_control",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.tabControl"))
                    .icon(makeIcon(ModItems.PELLET_RTG, Items.REDSTONE))
                    .displayItems((parameters, output) -> {
                        for (DeferredHolder<Item, ? extends Item> entry : ModItems.ITEMS.getEntries()) {
                            Item item = entry.get();
                            if (item != null && item != Items.AIR) {
                                if (item instanceof ItemMachineUpgrade || item instanceof ItemZirnoxRod || item instanceof ItemRBMKRod
                                        || item instanceof ItemDosimeter || item instanceof ItemGeigerCounter || item instanceof ItemSurveyScanner
                                        || item instanceof ItemDesignator) {
                                    output.accept(item);
                                } else {
                                    ResourceLocation id = BuiltInRegistries.ITEM.getKey(item);
                                    String name = id != null ? id.getPath() : "";
                                    if (name.startsWith("upgrade_") || name.startsWith("ams_core_") || name.startsWith("pellet_")
                                            || name.equals("designator") || name.startsWith("designator_") || name.startsWith("launch_")
                                            || name.equals("dyatlov") || name.equals("digamma_diagnostic") || name.equals("lung_diagnostic")
                                            || name.equals("ore_density_scanner")) {
                                        output.accept(item);
                                    }
                                }
                            }
                        }
                    })
                    .build());

    // 5. Machines & Industry Tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MACHINE_TAB = CREATIVE_MODE_TABS.register("tab_machine",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.tabMachine"))
                    .icon(makeBlockIcon(ModBlocks.MACHINE_CENTRIFUGE, Items.FURNACE))
                    .displayItems((parameters, output) -> {
                        for (DeferredHolder<Block, ? extends Block> entry : ModBlocks.BLOCKS.getEntries()) {
                            Block block = entry.get();
                            if (block != null && block.asItem() != Items.AIR) {
                                ResourceLocation id = BuiltInRegistries.BLOCK.getKey(block);
                                String name = id != null ? id.getPath() : "";
                                if (name.startsWith("machine_") || name.startsWith("pylon") || name.equals("substation")
                                        || name.equals("cable") || name.equals("pipe") || name.startsWith("rbmk_")
                                        || name.startsWith("dfc_") || name.startsWith("reactor_") || name.startsWith("zirnox_")) {
                                    output.accept(block);
                                }
                            }
                        }
                    })
                    .build());

    // 6. Nuclear Weapons & Explosives Tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> NUKE_TAB = CREATIVE_MODE_TABS.register("tab_nuke",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.tabNuke"))
                    .icon(makeBlockIcon(ModBlocks.NUKE_BOY, Items.TNT))
                    .displayItems((parameters, output) -> {
                        for (DeferredHolder<Block, ? extends Block> entry : ModBlocks.BLOCKS.getEntries()) {
                            Block block = entry.get();
                            if (block != null && block.asItem() != Items.AIR) {
                                ResourceLocation id = BuiltInRegistries.BLOCK.getKey(block);
                                String name = id != null ? id.getPath() : "";
                                if (name.startsWith("nuke_") || name.startsWith("bomb_")) {
                                    output.accept(block);
                                }
                            }
                        }
                        for (DeferredHolder<Item, ? extends Item> entry : ModItems.ITEMS.getEntries()) {
                            Item item = entry.get();
                            if (item != null && item != Items.AIR) {
                                ResourceLocation id = BuiltInRegistries.ITEM.getKey(item);
                                String name = id != null ? id.getPath() : "";
                                if (name.startsWith("boy_") || name.startsWith("man_") || name.startsWith("mike_")
                                        || name.startsWith("tsar_") || name.startsWith("gadget_") || name.startsWith("fleija_")
                                        || name.startsWith("solinium_") || name.startsWith("early_explosive_") || name.startsWith("explosive_")
                                        || name.equals("n2_charge") || name.equals("egg_balefire")) {
                                    output.accept(item);
                                }
                            }
                        }
                    })
                    .build());

    // 7. Missiles & Rocketry Tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MISSILE_TAB = CREATIVE_MODE_TABS.register("tab_missile",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.tabMissile"))
                    .icon(makeBlockIcon(ModBlocks.LAUNCH_PAD, Items.FIREWORK_ROCKET))
                    .displayItems((parameters, output) -> {
                        for (DeferredHolder<Block, ? extends Block> entry : ModBlocks.BLOCKS.getEntries()) {
                            Block block = entry.get();
                            if (block != null && block.asItem() != Items.AIR) {
                                ResourceLocation id = BuiltInRegistries.BLOCK.getKey(block);
                                String name = id != null ? id.getPath() : "";
                                if (name.startsWith("launch_") || name.startsWith("compact_launcher")) {
                                    output.accept(block);
                                }
                            }
                        }
                        for (DeferredHolder<Item, ? extends Item> entry : ModItems.ITEMS.getEntries()) {
                            Item item = entry.get();
                            if (item != null && item != Items.AIR) {
                                if (item instanceof ItemMissile || item instanceof ItemMissileStandard || item instanceof ItemCustomMissile) {
                                    output.accept(item);
                                } else {
                                    ResourceLocation id = BuiltInRegistries.ITEM.getKey(item);
                                    String name = id != null ? id.getPath() : "";
                                    if (name.startsWith("missile_") || name.startsWith("warhead_")) {
                                        output.accept(item);
                                    }
                                }
                            }
                        }
                    })
                    .build());

    // 8. Weapons, Guns, Ammo & Armor Tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> WEAPON_TAB = CREATIVE_MODE_TABS.register("tab_weapon",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.tabWeapon"))
                    .icon(makeIcon(ModItems.GUN_44, Items.BOW))
                    .displayItems((parameters, output) -> {
                        for (DeferredHolder<Item, ? extends Item> entry : ModItems.ITEMS.getEntries()) {
                            Item item = entry.get();
                            if (item != null && item != Items.AIR) {
                                if (item instanceof ItemGunBaseSedna || item instanceof ItemAmmo || item instanceof ItemGrenade || item instanceof ArmorFSB
                                        || item instanceof net.minecraft.world.item.ArmorItem) {
                                    output.accept(item);
                                } else {
                                    ResourceLocation id = BuiltInRegistries.ITEM.getKey(item);
                                    String name = id != null ? id.getPath() : "";
                                    if (name.startsWith("gun_") || name.startsWith("ammo_") || name.startsWith("grenade_")
                                            || name.endsWith("_helmet") || name.endsWith("_plate") || name.endsWith("_legs") || name.endsWith("_boots")
                                            || name.startsWith("hazmat_") || name.startsWith("gas_mask") || name.startsWith("jackt")) {
                                        output.accept(item);
                                    }
                                }
                            }
                        }
                    })
                    .build());

    // 9. Medical & Consumables Tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CONSUMABLE_TAB = CREATIVE_MODE_TABS.register("tab_consumable",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.tabConsumable"))
                    .icon(makeIcon(ModItems.RADAWAY, Items.POTION))
                    .displayItems((parameters, output) -> {
                        for (DeferredHolder<Item, ? extends Item> entry : ModItems.ITEMS.getEntries()) {
                            Item item = entry.get();
                            if (item != null && item != Items.AIR) {
                                if (item instanceof ItemPill || item instanceof ItemRadaway) {
                                    output.accept(item);
                                } else {
                                    ResourceLocation id = BuiltInRegistries.ITEM.getKey(item);
                                    String name = id != null ? id.getPath() : "";
                                    if (name.startsWith("radaway") || name.startsWith("pill_") || name.startsWith("iv_")
                                            || name.equals("plan_c") || name.equals("radx") || name.equals("siox")
                                            || name.equals("xanax") || name.equals("fmn") || name.equals("five_htp")
                                            || name.equals("chocolate") || name.startsWith("marshmallow") || name.startsWith("rag_")
                                            || name.startsWith("mask_") || name.startsWith("canister_") || name.startsWith("gas_")) {
                                        output.accept(item);
                                    }
                                }
                            }
                        }
                    })
                    .build());

    // 10. Kits, Blueprints & Templates Tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TEMPLATE_TAB = CREATIVE_MODE_TABS.register("tab_template",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.tabTemplate"))
                    .icon(makeIcon(ModItems.TEMPLATE_FOLDER, Items.MAP))
                    .displayItems((parameters, output) -> {
                        for (DeferredHolder<Item, ? extends Item> entry : ModItems.ITEMS.getEntries()) {
                            Item item = entry.get();
                            if (item != null && item != Items.AIR) {
                                ResourceLocation id = BuiltInRegistries.ITEM.getKey(item);
                                String name = id != null ? id.getPath() : "";
                                if (name.startsWith("kit_") || name.endsWith("_kit") || name.startsWith("loot_")
                                        || name.equals("template_folder") || name.equals("book_lore") || name.equals("polaroid")
                                        || name.startsWith("spawn_") || name.equals("glitch") || name.equals("stealth_boy")) {
                                    output.accept(item);
                                }
                            }
                        }
                    })
                    .build());

    public static void register(IEventBus modEventBus) {
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}
