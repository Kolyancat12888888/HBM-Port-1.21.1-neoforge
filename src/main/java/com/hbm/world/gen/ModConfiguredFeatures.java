package com.hbm.world.gen;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.MainRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModConfiguredFeatures {

	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_URANIUM_KEY = registerKey("ore_uranium");
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_THORIUM_KEY = registerKey("ore_thorium");
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_TITANIUM_KEY = registerKey("ore_titanium");
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_SULFUR_KEY = registerKey("ore_sulfur");
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_NITER_KEY = registerKey("ore_niter");
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_TUNGSTEN_KEY = registerKey("ore_tungsten");
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_BERYLLIUM_KEY = registerKey("ore_beryllium");
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_LEAD_KEY = registerKey("ore_lead");
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_COPPER_KEY = registerKey("ore_copper");
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_LIGNITE_KEY = registerKey("ore_lignite");
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_COLTAN_KEY = registerKey("ore_coltan");

	public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
		RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
		RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

		List<OreConfiguration.TargetBlockState> overworldUraniumOres = List.of(
				OreConfiguration.target(stoneReplaceables, ModBlocks.ORE_URANIUM.get().defaultBlockState()),
				OreConfiguration.target(deepslateReplaceables, ModBlocks.ORE_URANIUM.get().defaultBlockState())
		);
		List<OreConfiguration.TargetBlockState> overworldThoriumOres = List.of(
				OreConfiguration.target(stoneReplaceables, ModBlocks.ORE_THORIUM.get().defaultBlockState()),
				OreConfiguration.target(deepslateReplaceables, ModBlocks.ORE_THORIUM.get().defaultBlockState())
		);
		List<OreConfiguration.TargetBlockState> overworldTitaniumOres = List.of(
				OreConfiguration.target(stoneReplaceables, ModBlocks.ORE_TITANIUM.get().defaultBlockState()),
				OreConfiguration.target(deepslateReplaceables, ModBlocks.ORE_TITANIUM.get().defaultBlockState())
		);
		List<OreConfiguration.TargetBlockState> overworldTungstenOres = List.of(
				OreConfiguration.target(stoneReplaceables, ModBlocks.ORE_TUNGSTEN.get().defaultBlockState()),
				OreConfiguration.target(deepslateReplaceables, ModBlocks.ORE_TUNGSTEN.get().defaultBlockState())
		);
		List<OreConfiguration.TargetBlockState> overworldLeadOres = List.of(
				OreConfiguration.target(stoneReplaceables, ModBlocks.ORE_LEAD.get().defaultBlockState()),
				OreConfiguration.target(deepslateReplaceables, ModBlocks.ORE_LEAD.get().defaultBlockState())
		);
		List<OreConfiguration.TargetBlockState> overworldBerylliumOres = List.of(
				OreConfiguration.target(stoneReplaceables, ModBlocks.ORE_BERYLLIUM.get().defaultBlockState()),
				OreConfiguration.target(deepslateReplaceables, ModBlocks.ORE_BERYLLIUM.get().defaultBlockState())
		);
		List<OreConfiguration.TargetBlockState> overworldCopperOres = List.of(
				OreConfiguration.target(stoneReplaceables, ModBlocks.ORE_COPPER.get().defaultBlockState()),
				OreConfiguration.target(deepslateReplaceables, ModBlocks.ORE_COPPER.get().defaultBlockState())
		);
		List<OreConfiguration.TargetBlockState> overworldLigniteOres = List.of(
				OreConfiguration.target(stoneReplaceables, ModBlocks.ORE_LIGNITE.get().defaultBlockState()),
				OreConfiguration.target(deepslateReplaceables, ModBlocks.ORE_LIGNITE.get().defaultBlockState())
		);
		List<OreConfiguration.TargetBlockState> overworldSulfurOres = List.of(
				OreConfiguration.target(stoneReplaceables, ModBlocks.ORE_SULFUR.get().defaultBlockState()),
				OreConfiguration.target(deepslateReplaceables, ModBlocks.ORE_SULFUR.get().defaultBlockState())
		);
		List<OreConfiguration.TargetBlockState> overworldNiterOres = List.of(
				OreConfiguration.target(stoneReplaceables, ModBlocks.ORE_NITER.get().defaultBlockState()),
				OreConfiguration.target(deepslateReplaceables, ModBlocks.ORE_NITER.get().defaultBlockState())
		);
		List<OreConfiguration.TargetBlockState> overworldColtanOres = List.of(
				OreConfiguration.target(stoneReplaceables, ModBlocks.ORE_COLTAN.get().defaultBlockState()),
				OreConfiguration.target(deepslateReplaceables, ModBlocks.ORE_COLTAN.get().defaultBlockState())
		);

		register(context, ORE_URANIUM_KEY, Feature.ORE, new OreConfiguration(overworldUraniumOres, 6));
		register(context, ORE_THORIUM_KEY, Feature.ORE, new OreConfiguration(overworldThoriumOres, 6));
		register(context, ORE_TITANIUM_KEY, Feature.ORE, new OreConfiguration(overworldTitaniumOres, 7));
		register(context, ORE_TUNGSTEN_KEY, Feature.ORE, new OreConfiguration(overworldTungstenOres, 5));
		register(context, ORE_LEAD_KEY, Feature.ORE, new OreConfiguration(overworldLeadOres, 8));
		register(context, ORE_BERYLLIUM_KEY, Feature.ORE, new OreConfiguration(overworldBerylliumOres, 4));
		register(context, ORE_COPPER_KEY, Feature.ORE, new OreConfiguration(overworldCopperOres, 9));
		register(context, ORE_LIGNITE_KEY, Feature.ORE, new OreConfiguration(overworldLigniteOres, 12));
		register(context, ORE_SULFUR_KEY, Feature.ORE, new OreConfiguration(overworldSulfurOres, 8));
		register(context, ORE_NITER_KEY, Feature.ORE, new OreConfiguration(overworldNiterOres, 8));
		register(context, ORE_COLTAN_KEY, Feature.ORE, new OreConfiguration(overworldColtanOres, 4));
	}

	public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(MainRegistry.MODID, name));
	}

	private static <FC extends net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration, F extends Feature<FC>> void register(
			BootstrapContext<ConfiguredFeature<?, ?>> context,
			ResourceKey<ConfiguredFeature<?, ?>> key,
			F feature,
			FC configuration) {
		context.register(key, new ConfiguredFeature<>(feature, configuration));
	}
}
