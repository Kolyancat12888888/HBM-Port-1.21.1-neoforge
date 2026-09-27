package com.hbm.inventory.recipes;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.RecipesCommon;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.items.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class CentrifugeRecipes {

	public static final Map<AStack, ItemStack[]> recipes = new HashMap<>();

	static {
		registerDefaults();
	}

	public static void registerDefaults() {
		// Ores separation
		add(new OreDictStack("oreCoal"), new ItemStack(ModItems.POWDER_COAL.get(), 2), new ItemStack(ModItems.POWDER_COAL.get(), 2), new ItemStack(ModItems.POWDER_COAL.get(), 2), new ItemStack(Blocks.GRAVEL));
		add(new OreDictStack("oreIron"), new ItemStack(ModItems.POWDER_IRON.get(), 1), new ItemStack(ModItems.POWDER_IRON.get(), 1), new ItemStack(ModItems.POWDER_IRON.get(), 1), new ItemStack(Blocks.GRAVEL));
		add(new OreDictStack("oreGold"), new ItemStack(ModItems.POWDER_GOLD.get(), 1), new ItemStack(ModItems.POWDER_GOLD.get(), 1), new ItemStack(ModItems.POWDER_GOLD.get(), 1), new ItemStack(Blocks.GRAVEL));
		add(new OreDictStack("oreDiamond"), new ItemStack(ModItems.POWDER_DIAMOND.get(), 1), new ItemStack(ModItems.POWDER_DIAMOND.get(), 1), new ItemStack(ModItems.POWDER_DIAMOND.get(), 1), new ItemStack(Blocks.GRAVEL));
		add(new OreDictStack("oreEmerald"), new ItemStack(ModItems.POWDER_EMERALD.get(), 1), new ItemStack(ModItems.POWDER_EMERALD.get(), 1), new ItemStack(ModItems.POWDER_EMERALD.get(), 1), new ItemStack(Blocks.GRAVEL));
		add(new OreDictStack("oreTitanium"), new ItemStack(ModItems.POWDER_TITANIUM.get(), 1), new ItemStack(ModItems.POWDER_TITANIUM.get(), 1), new ItemStack(ModItems.POWDER_IRON.get(), 1), new ItemStack(Blocks.GRAVEL));
		add(new OreDictStack("oreCopper"), new ItemStack(ModItems.POWDER_COPPER.get(), 1), new ItemStack(ModItems.POWDER_COPPER.get(), 1), new ItemStack(ModItems.POWDER_GOLD.get(), 1), new ItemStack(Blocks.GRAVEL));
		add(new OreDictStack("oreTungsten"), new ItemStack(ModItems.POWDER_TUNGSTEN.get(), 1), new ItemStack(ModItems.POWDER_TUNGSTEN.get(), 1), new ItemStack(ModItems.POWDER_IRON.get(), 1), new ItemStack(Blocks.GRAVEL));
		add(new OreDictStack("oreLead"), new ItemStack(ModItems.POWDER_LEAD.get(), 1), new ItemStack(ModItems.POWDER_LEAD.get(), 1), new ItemStack(ModItems.POWDER_GOLD.get(), 1), new ItemStack(Blocks.GRAVEL));
		add(new OreDictStack("oreUranium"), new ItemStack(ModItems.POWDER_URANIUM.get(), 1), new ItemStack(ModItems.POWDER_URANIUM.get(), 1), new ItemStack(ModItems.NUGGET_RA226.get(), 1), new ItemStack(Blocks.GRAVEL));
		add(new OreDictStack("oreThorium"), new ItemStack(ModItems.POWDER_THORIUM.get(), 1), new ItemStack(ModItems.POWDER_THORIUM.get(), 1), new ItemStack(ModItems.POWDER_URANIUM.get(), 1), new ItemStack(Blocks.GRAVEL));
		add(new OreDictStack("oreBeryllium"), new ItemStack(ModItems.POWDER_BERYLLIUM.get(), 1), new ItemStack(ModItems.POWDER_BERYLLIUM.get(), 1), new ItemStack(ModItems.POWDER_EMERALD.get(), 1), new ItemStack(Blocks.GRAVEL));
		add(new OreDictStack("oreRedstone"), new ItemStack(Items.REDSTONE, 3), new ItemStack(Items.REDSTONE, 3), new ItemStack(ModItems.INGOT_MERCURY.get(), 1), new ItemStack(Blocks.GRAVEL));
		add(new OreDictStack("oreLapis"), new ItemStack(ModItems.POWDER_LAPIS.get(), 6), new ItemStack(ModItems.POWDER_COBALT_TINY.get(), 1), new ItemStack(ModItems.GEM_SODALITE.get(), 1), new ItemStack(Blocks.GRAVEL));
		add(new OreDictStack("oreCobalt"), new ItemStack(ModItems.POWDER_COBALT.get(), 2), new ItemStack(ModItems.POWDER_IRON.get(), 1), new ItemStack(ModItems.POWDER_COPPER.get(), 1), new ItemStack(Blocks.GRAVEL));

		// Special vanilla & NTM recipes
		add(new ComparableStack(Items.BLAZE_ROD), new ItemStack(Items.BLAZE_POWDER, 1), new ItemStack(Items.BLAZE_POWDER, 1), new ItemStack(ModItems.POWDER_FIRE.get(), 1), new ItemStack(ModItems.POWDER_FIRE.get(), 1));
		add(new ComparableStack(ModItems.INGOT_SCHRARANIUM.get()), new ItemStack(ModItems.NUGGET_SCHRABIDIUM.get(), 2), new ItemStack(ModItems.NUGGET_SCHRABIDIUM.get(), 1), new ItemStack(ModItems.NUGGET_URANIUM.get(), 3), new ItemStack(ModItems.NUGGET_NEPTUNIUM.get(), 2));
	}

	private static void add(AStack in, ItemStack out1, ItemStack out2, ItemStack out3, ItemStack out4) {
		recipes.put(in, new ItemStack[]{out1, out2, out3, out4});
	}

	@Nullable
	public static ItemStack[] getOutput(@Nullable ItemStack stack) {
		if (stack == null || stack.isEmpty()) return null;

		ComparableStack comp = new ComparableStack(stack).makeSingular();
		if (recipes.containsKey(comp)) {
			return RecipesCommon.copyStackArray(recipes.get(comp));
		}

		for (Map.Entry<AStack, ItemStack[]> entry : recipes.entrySet()) {
			if (entry.getKey().isApplicable(stack)) {
				return RecipesCommon.copyStackArray(entry.getValue());
			}
		}
		return null;
	}
}
