package com.hbm.world.gen;

import com.hbm.main.MainRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModPlacedFeatures {

	public static final ResourceKey<PlacedFeature> ORE_URANIUM_PLACED_KEY = registerKey("ore_uranium_placed");
	public static final ResourceKey<PlacedFeature> ORE_THORIUM_PLACED_KEY = registerKey("ore_thorium_placed");
	public static final ResourceKey<PlacedFeature> ORE_TITANIUM_PLACED_KEY = registerKey("ore_titanium_placed");
	public static final ResourceKey<PlacedFeature> ORE_TUNGSTEN_PLACED_KEY = registerKey("ore_tungsten_placed");
	public static final ResourceKey<PlacedFeature> ORE_LEAD_PLACED_KEY = registerKey("ore_lead_placed");
	public static final ResourceKey<PlacedFeature> ORE_BERYLLIUM_PLACED_KEY = registerKey("ore_beryllium_placed");
	public static final ResourceKey<PlacedFeature> ORE_COPPER_PLACED_KEY = registerKey("ore_copper_placed");
	public static final ResourceKey<PlacedFeature> ORE_LIGNITE_PLACED_KEY = registerKey("ore_lignite_placed");
	public static final ResourceKey<PlacedFeature> ORE_SULFUR_PLACED_KEY = registerKey("ore_sulfur_placed");
	public static final ResourceKey<PlacedFeature> ORE_NITER_PLACED_KEY = registerKey("ore_niter_placed");
	public static final ResourceKey<PlacedFeature> ORE_COLTAN_PLACED_KEY = registerKey("ore_coltan_placed");

	public static void bootstrap(BootstrapContext<PlacedFeature> context) {
		HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

		register(context, ORE_URANIUM_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.ORE_URANIUM_KEY),
				orePlacement(CountPlacement.of(5), HeightRangePlacement.triangle(VerticalAnchor.absolute(-60), VerticalAnchor.absolute(32))));

		register(context, ORE_THORIUM_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.ORE_THORIUM_KEY),
				orePlacement(CountPlacement.of(4), HeightRangePlacement.triangle(VerticalAnchor.absolute(-50), VerticalAnchor.absolute(20))));

		register(context, ORE_TITANIUM_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.ORE_TITANIUM_KEY),
				orePlacement(CountPlacement.of(6), HeightRangePlacement.triangle(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(40))));

		register(context, ORE_TUNGSTEN_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.ORE_TUNGSTEN_KEY),
				orePlacement(CountPlacement.of(4), HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(0))));

		register(context, ORE_LEAD_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.ORE_LEAD_KEY),
				orePlacement(CountPlacement.of(8), HeightRangePlacement.triangle(VerticalAnchor.absolute(-30), VerticalAnchor.absolute(60))));

		register(context, ORE_BERYLLIUM_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.ORE_BERYLLIUM_KEY),
				orePlacement(CountPlacement.of(3), HeightRangePlacement.triangle(VerticalAnchor.absolute(-55), VerticalAnchor.absolute(15))));

		register(context, ORE_COPPER_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.ORE_COPPER_KEY),
				orePlacement(CountPlacement.of(10), HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(96))));

		register(context, ORE_LIGNITE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.ORE_LIGNITE_KEY),
				orePlacement(CountPlacement.of(12), HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(128))));

		register(context, ORE_SULFUR_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.ORE_SULFUR_KEY),
				orePlacement(CountPlacement.of(6), HeightRangePlacement.uniform(VerticalAnchor.absolute(-20), VerticalAnchor.absolute(50))));

		register(context, ORE_NITER_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.ORE_NITER_KEY),
				orePlacement(CountPlacement.of(6), HeightRangePlacement.uniform(VerticalAnchor.absolute(10), VerticalAnchor.absolute(80))));

		register(context, ORE_COLTAN_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.ORE_COLTAN_KEY),
				orePlacement(CountPlacement.of(2), HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(-10))));
	}

	public static List<PlacementModifier> orePlacement(PlacementModifier countModifier, PlacementModifier heightModifier) {
		return List.of(countModifier, InSquarePlacement.spread(), heightModifier, BiomeFilter.biome());
	}

	public static ResourceKey<PlacedFeature> registerKey(String name) {
		return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(MainRegistry.MODID, name));
	}

	private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key,
								 Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
		context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
	}
}
