package com.hbm.creativetabs;

import com.hbm.main.MainRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MainRegistry.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> PARTS_TAB = CREATIVE_MODE_TABS.register("tab_parts",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.tabParts"))
                    .icon(() -> new ItemStack(Items.IRON_INGOT))
                    .build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CONTROL_TAB = CREATIVE_MODE_TABS.register("tab_control",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.tabControl"))
                    .icon(() -> new ItemStack(Items.REDSTONE))
                    .build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TEMPLATE_TAB = CREATIVE_MODE_TABS.register("tab_template",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.tabTemplate"))
                    .icon(() -> new ItemStack(Items.PAPER))
                    .build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> RESOURCE_TAB = CREATIVE_MODE_TABS.register("tab_resource",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.tabResource"))
                    .icon(() -> new ItemStack(Items.RAW_IRON))
                    .build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BLOCK_TAB = CREATIVE_MODE_TABS.register("tab_blocks",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.tabBlocks"))
                    .icon(() -> new ItemStack(Items.STONE_BRICKS))
                    .build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MACHINE_TAB = CREATIVE_MODE_TABS.register("tab_machine",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.tabMachine"))
                    .icon(() -> new ItemStack(Items.FURNACE))
                    .build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> NUKE_TAB = CREATIVE_MODE_TABS.register("tab_nuke",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.tabNuke"))
                    .icon(() -> new ItemStack(Items.TNT))
                    .build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MISSILE_TAB = CREATIVE_MODE_TABS.register("tab_missile",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.tabMissile"))
                    .icon(() -> new ItemStack(Items.FIREWORK_ROCKET))
                    .build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> WEAPON_TAB = CREATIVE_MODE_TABS.register("tab_weapon",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.tabWeapon"))
                    .icon(() -> new ItemStack(Items.IRON_SWORD))
                    .build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CONSUMABLE_TAB = CREATIVE_MODE_TABS.register("tab_consumable",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.tabConsumable"))
                    .icon(() -> new ItemStack(Items.POTION))
                    .build());

    public static void register(IEventBus modEventBus) {
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}
