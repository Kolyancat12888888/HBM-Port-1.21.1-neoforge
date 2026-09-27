package com.hbm.creativetabs;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MainRegistry.MODID);

    // 1. Blocks Tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BLOCK_TAB = CREATIVE_MODE_TABS.register("tab_blocks",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.tabBlocks"))
                    .icon(() -> new ItemStack(ModBlocks.CONCRETE.get()))
                    .displayItems((parameters, output) -> {
                        for (Block block : ModBlocks.ALL_BLOCKS) {
                            output.accept(block);
                        }
                    })
                    .build());

    // 2. Resource & Materials Tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> RESOURCE_TAB = CREATIVE_MODE_TABS.register("tab_resource",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.tabResource"))
                    .icon(() -> new ItemStack(ModItems.INGOT_URANIUM.get()))
                    .displayItems((parameters, output) -> {
                        for (Item item : ModItems.ALL_ITEMS) {
                            output.accept(item);
                        }
                    })
                    .build());

    // 3. Machines & Reactors Tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MACHINE_TAB = CREATIVE_MODE_TABS.register("tab_machine",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.tabMachine"))
                    .icon(() -> new ItemStack(ModBlocks.MACHINE_CENTRIFUGE.get()))
                    .displayItems((parameters, output) -> {
                        output.accept(ModBlocks.MACHINE_CENTRIFUGE.get());
                        output.accept(ModBlocks.MACHINE_BLAST_FURNACE.get());
                        output.accept(ModBlocks.MACHINE_CHEMPLANT.get());
                        output.accept(ModBlocks.MACHINE_ELECTROLYSER.get());
                        output.accept(ModBlocks.MACHINE_SHREDDER.get());
                        output.accept(ModBlocks.MACHINE_CRYSTALLIZER.get());
                        output.accept(ModBlocks.MACHINE_EPRESS.get());
                        output.accept(ModBlocks.MACHINE_ASSEMBLY_MACHINE.get());
                        output.accept(ModBlocks.MACHINE_SIREN.get());
                        output.accept(ModBlocks.MACHINE_BATTERY.get());
                        output.accept(ModBlocks.MACHINE_RTG.get());
                        output.accept(ModBlocks.MACHINE_TURBINE.get());
                        output.accept(ModBlocks.MACHINE_INDUSTRIAL_TURBINE.get());
                        output.accept(ModBlocks.MACHINE_GENERATOR.get());
                        output.accept(ModBlocks.MACHINE_FLUID_TANK.get());
                        output.accept(ModBlocks.MACHINE_STORAGE_TANK.get());
                        output.accept(ModBlocks.MACHINE_GAS_FLARE.get());
                        output.accept(ModBlocks.MACHINE_PUMP.get());
                        output.accept(ModBlocks.CABLE.get());
                        output.accept(ModBlocks.PYLON.get());
                        output.accept(ModBlocks.PYLON_MEDIUM.get());
                        output.accept(ModBlocks.PYLON_LARGE.get());
                        output.accept(ModBlocks.SUBSTATION.get());
                        output.accept(ModBlocks.PIPE.get());
                    })
                    .build());

    // 4. Nuclear & Nuke Tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> NUKE_TAB = CREATIVE_MODE_TABS.register("tab_nuke",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.tabNuke"))
                    .icon(() -> new ItemStack(ModBlocks.NUKE_BOY.get()))
                    .displayItems((parameters, output) -> {
                        output.accept(ModBlocks.BOMB_BOY.get());
                        output.accept(ModBlocks.BOMB_MAN.get());
                        output.accept(ModBlocks.BOMB_MIKE.get());
                        output.accept(ModBlocks.BOMB_TSAR.get());
                        output.accept(ModBlocks.BOMB_GADGET.get());
                        output.accept(ModBlocks.NUKE_BOY.get());
                        output.accept(ModBlocks.NUKE_MAN.get());
                        output.accept(ModBlocks.NUKE_MIKE.get());
                        output.accept(ModBlocks.NUKE_TSAR.get());
                        output.accept(ModBlocks.NUKE_GADGET.get());
                        output.accept(ModBlocks.NUKE_FLEIJA.get());
                        output.accept(ModBlocks.NUKE_BALEFIRE.get());
                        output.accept(ModBlocks.NUKE_N2.get());
                        output.accept(ModBlocks.NUKE_SOLINIUM.get());
                        output.accept(ModBlocks.NUKE_PROTOTYPE.get());
                        output.accept(ModBlocks.NUKE_CUSTOM.get());
                    })
                    .build());

    // 5. Missiles Tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MISSILE_TAB = CREATIVE_MODE_TABS.register("tab_missile",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.tabMissile"))
                    .icon(() -> new ItemStack(ModBlocks.LAUNCH_PAD.get()))
                    .displayItems((parameters, output) -> {
                        output.accept(ModBlocks.LAUNCH_PAD.get());
                        output.accept(ModBlocks.LAUNCH_PAD_LARGE.get());
                        output.accept(ModBlocks.LAUNCH_TABLE.get());
                        output.accept(ModBlocks.COMPACT_LAUNCHER.get());
                    })
                    .build());

    // 6. Weapons Tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> WEAPON_TAB = CREATIVE_MODE_TABS.register("tab_weapon",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.tabWeapon"))
                    .icon(() -> new ItemStack(ModItems.GUN_44.get()))
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.GUN_9MM.get());
                        output.accept(ModItems.GUN_44.get());
                        output.accept(ModItems.GUN_50BMG.get());
                        output.accept(ModItems.GUN_12GA.get());
                        output.accept(ModItems.GUN_FATMAN.get());
                        output.accept(ModItems.AMMO_9MM.get());
                        output.accept(ModItems.AMMO_44.get());
                        output.accept(ModItems.AMMO_50BMG.get());
                        output.accept(ModItems.AMMO_12GA.get());
                        output.accept(ModItems.AMMO_MINI_NUKE.get());
                        output.accept(ModItems.GRENADE_GENERIC.get());
                        output.accept(ModItems.GRENADE_NUCLEAR.get());
                    })
                    .build());

    public static void register(IEventBus modEventBus) {
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}
