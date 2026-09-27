package com.hbm.inventory.recipes;

import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.items.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BlastFurnaceRecipesNT {

	public static final BlastFurnaceRecipesNT INSTANCE = new BlastFurnaceRecipesNT();
	public final List<BlastFurnaceRecipe> recipes = new ArrayList<>();

	public BlastFurnaceRecipesNT() {
		registerDefaults();
	}

	public void registerDefaults() {
		// Steel from Iron + Sand
		recipes.add(new BlastFurnaceRecipe("blast.steelFromIngot").setDuration(800)
				.inputItems(new OreDictStack("ingotIron", 2), new ComparableStack(Blocks.SAND, 1))
				.outputItems(new ItemStack(ModItems.INGOT_STEEL.get(), 2)));

		recipes.add(new BlastFurnaceRecipe("blast.steelFromDust").setDuration(800)
				.inputItems(new OreDictStack("dustIron", 2), new ComparableStack(Blocks.SAND, 1))
				.outputItems(new ItemStack(ModItems.INGOT_STEEL.get(), 2)));

		recipes.add(new BlastFurnaceRecipe("blast.steelFromOre").setDuration(800)
				.inputItems(new OreDictStack("oreIron", 1), new ComparableStack(Blocks.SAND, 1))
				.outputItems(new ItemStack(ModItems.INGOT_STEEL.get(), 2)));

		// Red copper (Mingrade)
		recipes.add(new BlastFurnaceRecipe("blast.mingrade").setDuration(400)
				.inputItems(new OreDictStack("ingotCopper", 1), new ComparableStack(Items.REDSTONE, 1))
				.outputItems(new ItemStack(ModItems.INGOT_RED_COPPER.get(), 2)));

		// Starmetal / Meteorite
		recipes.add(new BlastFurnaceRecipe("blast.starmetal").setDuration(600)
				.inputItems(new OreDictStack("ingotCobalt", 1), new ComparableStack(ModItems.INGOT_METEORITE.get(), 1))
				.outputItems(new ItemStack(ModItems.INGOT_STARMETAL.get(), 1)));

		// Firebrick
		recipes.add(new BlastFurnaceRecipe("blast.firebrick").setDuration(800)
				.inputItems(new OreDictStack("dustAluminium", 1), new ComparableStack(Items.CLAY_BALL, 7))
				.outputItems(new ItemStack(ModItems.INGOT_FIREBRICK.get(), 8)));
	}

	@Nullable
	public BlastFurnaceRecipe getRecipe(ItemStack s0, ItemStack s1) {
		for (BlastFurnaceRecipe recipe : recipes) {
			if (recipe.matches(s0, s1)) return recipe;
		}
		return null;
	}
}
