package com.hbm.inventory.recipes;

import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.items.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AssemblyMachineRecipes {

	public static final AssemblyMachineRecipes INSTANCE = new AssemblyMachineRecipes();
	public final List<AssemblyRecipe> recipes = new ArrayList<>();

	public AssemblyMachineRecipes() {
		registerDefaults();
	}

	public static class AssemblyRecipe {
		public String name;
		public int duration = 100;
		public int powerConsumption = 50;
		public AStack[] inputItems;
		public ItemStack outputItem;

		public AssemblyRecipe(String name, int duration, int powerConsumption) {
			this.name = name;
			this.duration = duration;
			this.powerConsumption = powerConsumption;
		}

		public AssemblyRecipe inputItems(AStack... inputs) {
			this.inputItems = inputs;
			return this;
		}

		public AssemblyRecipe outputItem(ItemStack output) {
			this.outputItem = output;
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
		// Assembly recipes
		recipes.add(new AssemblyRecipe("ass.gadget_core", 400, 200)
				.inputItems(new OreDictStack("dustPlutonium", 4), new ComparableStack(ModItems.EARLY_EXPLOSIVE_LENSES.get(), 4))
				.outputItem(new ItemStack(ModItems.GADGET_CORE.get(), 1)));

		recipes.add(new AssemblyRecipe("ass.boy_bullet", 300, 150)
				.inputItems(new OreDictStack("dustUranium", 4), new OreDictStack("ingotSteel", 2))
				.outputItem(new ItemStack(ModItems.BOY_BULLET.get(), 1)));

		recipes.add(new AssemblyRecipe("ass.boy_target", 300, 150)
				.inputItems(new OreDictStack("dustUranium", 6), new OreDictStack("ingotLead", 4))
				.outputItem(new ItemStack(ModItems.BOY_TARGET.get(), 1)));

		recipes.add(new AssemblyRecipe("ass.man_core", 500, 300)
				.inputItems(new OreDictStack("dustPlutonium", 6), new ComparableStack(ModItems.EXPLOSIVE_LENSES.get(), 8))
				.outputItem(new ItemStack(ModItems.MAN_CORE.get(), 1)));
	}

	@Nullable
	public AssemblyRecipe getRecipe(ItemStack[] slots) {
		for (AssemblyRecipe recipe : recipes) {
			if (recipe.matches(slots)) return recipe;
		}
		return null;
	}
}
