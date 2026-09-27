package com.hbm.main;

import com.hbm.attachment.HbmAttachments;
import com.hbm.creativetabs.ModCreativeTabs;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.potion.HbmPotion;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(MainRegistry.MODID)
public class MainRegistry {
    public static final String MODID = "hbm";
    public static final Logger LOGGER = LogManager.getLogger("HBM-NTM");

    public static int polaroidID = 1;

    static {
        java.util.Random rand = new java.util.Random();
        do {
            polaroidID = rand.nextInt(18) + 1;
        } while (polaroidID == 4 || polaroidID == 9);
    }

    public MainRegistry(IEventBus modEventBus) {
        LOGGER.info("Hbm's Nuclear Tech Mod is initializing on NeoForge 1.21.1!");

        // Initialize Fluids & Materials
        Fluids.init();

        // Register Potions / MobEffects
        HbmPotion.register(modEventBus);

        // Register Armor Materials
        com.hbm.items.armor.ModArmorMaterials.register(modEventBus);

        // Register Blocks
        com.hbm.blocks.ModBlocks.register(modEventBus);

        // Register Block Entities
        com.hbm.tileentity.ModBlockEntities.register(modEventBus);

        // Register Items
        com.hbm.items.ModItems.register(modEventBus);

        // Register Entities
        com.hbm.entity.ModEntities.register(modEventBus);

        // Register Creative Tabs
        ModCreativeTabs.register(modEventBus);

        // Register attachments
        HbmAttachments.register(modEventBus);

        // Initialize Hazard Registry & Transformers
        modEventBus.addListener((net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent event) -> {
            event.enqueueWork(() -> {
                com.hbm.blocks.ModBlocks.initAccessors();
                com.hbm.items.ModItems.initAccessors();
                com.hbm.hazard.HazardRegistry.registerItems();
                com.hbm.hazard.HazardRegistry.registerTrafos();
            });
        });
    }
}
