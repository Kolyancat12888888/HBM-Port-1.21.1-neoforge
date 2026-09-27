package com.hbm.inventory.recipes;

import com.hbm.inventory.RecipesCommon;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CrystallizerRecipes {

	public static final CrystallizerRecipes INSTANCE = new CrystallizerRecipes();
	public final List<CrystallizerRecipe> recipes = new ArrayList<>();

	public CrystallizerRecipes() {
		registerDefaults();
	}

	public static class CrystallizerRecipe {
		public String name;
		public int duration = 200;
		public int powerConsumption = 50;
		public AStack inputItem;
		public FluidType inputFluid;
		public int inputFluidAmount;
		public ItemStack outputItem;

		public CrystallizerRecipe(String name, int duration, int powerConsumption) {
			this.name = name;
			this.duration = duration;
			this.powerConsumption = powerConsumption;
		}

		public CrystallizerRecipe input(AStack item, FluidType fluid, int fluidAmount) {
			this.inputItem = item;
			this.inputFluid = fluid;
			this.inputFluidAmount = fluidAmount;
			return this;
		}

		public CrystallizerRecipe output(ItemStack output) {
			this.outputItem = output;
			return this;
		}

		public boolean matches(ItemStack inItem, FluidType inFluid, int inFluidAmount) {
			if (inFluid != this.inputFluid || inFluidAmount < this.inputFluidAmount) return false;
			if (this.inputItem == null) return inItem == null || inItem.isEmpty();
			return this.inputItem.matchesRecipe(inItem, false);
		}
	}

	public void registerDefaults() {
		// Crystallize Yellowcake / Acid
		recipes.add(new CrystallizerRecipe("cryst.yellowcake", 200, 50)
				.input(new OreDictStack("dustSulfur", 1), Fluids.SULFURIC_ACID, 500)
				.output(new ItemStack(ModItems.YELLOWCAKE.get(), 1)));

		// Sodalite crystal
		recipes.add(new CrystallizerRecipe("cryst.sodalite", 300, 100)
				.input(new ComparableStack(Items.LAPIS_LAZULI, 4), Fluids.WATER, 1000)
				.output(new ItemStack(ModItems.GEM_SODALITE.get(), 2)));
	}

	@Nullable
	public CrystallizerRecipe getRecipe(ItemStack inItem, FluidType inFluid, int inFluidAmount) {
		for (CrystallizerRecipe recipe : recipes) {
			if (recipe.matches(inItem, inFluid, inFluidAmount)) return recipe;
		}
		return null;
	}
}
