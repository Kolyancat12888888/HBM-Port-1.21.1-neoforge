package com.hbm.inventory.recipes;

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

public class ShredderRecipes {

	public static final Map<AStack, ItemStack> recipes = new HashMap<>();

	static {
		registerDefaults();
	}

	public static void registerDefaults() {
		// Metal Scrap / Scrap production
		add(new OreDictStack("ingotIron"), new ItemStack(ModItems.SCRAP.get(), 1));
		add(new OreDictStack("ingotGold"), new ItemStack(ModItems.SCRAP.get(), 2));
		add(new OreDictStack("ingotCopper"), new ItemStack(ModItems.SCRAP.get(), 1));
		add(new OreDictStack("ingotLead"), new ItemStack(ModItems.SCRAP.get(), 1));
		add(new OreDictStack("ingotSteel"), new ItemStack(ModItems.SCRAP.get(), 2));
		add(new OreDictStack("ingotTitanium"), new ItemStack(ModItems.SCRAP.get(), 2));
		add(new OreDictStack("ingotTungsten"), new ItemStack(ModItems.SCRAP.get(), 2));

		// Crushing blocks into powders / gravel / sand
		add(new ComparableStack(Blocks.COBBLESTONE), new ItemStack(Blocks.GRAVEL));
		add(new ComparableStack(Blocks.GRAVEL), new ItemStack(Blocks.SAND));
		add(new ComparableStack(Blocks.STONE), new ItemStack(Blocks.GRAVEL));
		add(new ComparableStack(Blocks.SANDSTONE), new ItemStack(Blocks.SAND, 2));

		// Plant matter into Biomass / Sawdust
		add(new OreDictStack("treeLeaves"), new ItemStack(ModItems.BIOMASS.get(), 1));
		add(new OreDictStack("logWood"), new ItemStack(ModItems.DUST_WOOD.get(), 4));
		add(new OreDictStack("plankWood"), new ItemStack(ModItems.DUST_WOOD.get(), 1));

		// Misc
		add(new ComparableStack(Items.BONE), new ItemStack(Items.BONE_MEAL, 4));
		add(new ComparableStack(Items.BLAZE_ROD), new ItemStack(Items.BLAZE_POWDER, 4));
		add(new ComparableStack(Items.COAL), new ItemStack(ModItems.POWDER_COAL.get(), 1));
		add(new ComparableStack(ModItems.LIGNITE.get()), new ItemStack(ModItems.POWDER_LIGNITE.get(), 1));
	}

	public static void add(AStack in, ItemStack out) {
		recipes.put(in, out);
	}

	@Nullable
	public static ItemStack getOutput(@Nullable ItemStack stack) {
		if (stack == null || stack.isEmpty()) return null;

		ComparableStack comp = new ComparableStack(stack).makeSingular();
		if (recipes.containsKey(comp)) {
			return recipes.get(comp).copy();
		}

		for (Map.Entry<AStack, ItemStack> entry : recipes.entrySet()) {
			if (entry.getKey().matchesRecipe(stack, false)) {
				return entry.getValue().copy();
			}
		}
		return null;
	}
}
