package com.hbm.inventory.recipes;

import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ChemicalPlantRecipes {

	public static final ChemicalPlantRecipes INSTANCE = new ChemicalPlantRecipes();
	public final List<ChemRecipe> recipes = new ArrayList<>();

	public ChemicalPlantRecipes() {
		registerDefaults();
	}

	public static class ChemRecipe {
		public String name;
		public int duration = 100;
		public int powerConsumption = 20;
		public AStack[] inputItems;
		public ItemStack[] outputItems;

		public ChemRecipe(String name, int duration, int powerConsumption) {
			this.name = name;
			this.duration = duration;
			this.powerConsumption = powerConsumption;
		}

		public ChemRecipe inputItems(AStack... inputs) {
			this.inputItems = inputs;
			return this;
		}

		public ChemRecipe outputItems(ItemStack... outputs) {
			this.outputItems = outputs;
			return this;
		}

		public boolean matches(ItemStack[] inSlots) {
			if (inputItems == null || inputItems.length == 0) return false;
			for (AStack in : inputItems) {
				boolean found = false;
				for (ItemStack s : inSlots) {
					if (in.matchesRecipe(s, false)) {
						found = true;
						break;
					}
				}
				if (!found) return false;
			}
			return true;
		}
	}

	public void registerDefaults() {
		// Desh Mix from coal + rare earths
		recipes.add(new ChemRecipe("chem.desh", 200, 50)
				.inputItems(new OreDictStack("dustCoal", 2), new OreDictStack("nuggetZirconium", 2))
				.outputItems(new ItemStack(ModItems.POWDER_DESH_MIX.get(), 1)));

		// Nitan Mix
		recipes.add(new ChemRecipe("chem.nitan", 300, 100)
				.inputItems(new OreDictStack("dustThorium", 2), new OreDictStack("dustUranium", 2))
				.outputItems(new ItemStack(ModItems.POWDER_NITAN_MIX.get(), 1)));

		// Phosphorus
		recipes.add(new ChemRecipe("chem.phosphorus", 100, 40)
				.inputItems(new ComparableStack(Items.BLAZE_POWDER, 2), new ComparableStack(ModItems.POWDER_FIRE.get(), 2))
				.outputItems(new ItemStack(ModItems.INGOT_PHOSPHORUS.get(), 2)));
	}

	@Nullable
	public ChemRecipe getRecipe(ItemStack[] slots) {
		for (ChemRecipe recipe : recipes) {
			if (recipe.matches(slots)) return recipe;
		}
		return null;
	}
}
