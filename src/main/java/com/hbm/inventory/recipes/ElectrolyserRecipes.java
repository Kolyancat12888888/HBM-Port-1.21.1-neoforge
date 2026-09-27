package com.hbm.inventory.recipes;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ModItems;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ElectrolyserRecipes {

	public static final ElectrolyserRecipes INSTANCE = new ElectrolyserRecipes();
	public final List<ElectrolyserRecipe> recipes = new ArrayList<>();

	public ElectrolyserRecipes() {
		registerDefaults();
	}

	public static class ElectrolyserRecipe {
		public String name;
		public int duration = 100;
		public int powerConsumption = 40;
		public FluidType inputFluid;
		public int inputFluidAmount;
		public FluidType outputFluid1;
		public int outputFluid1Amount;
		public FluidType outputFluid2;
		public int outputFluid2Amount;
		public ItemStack outputItem;

		public ElectrolyserRecipe(String name, int duration, int powerConsumption) {
			this.name = name;
			this.duration = duration;
			this.powerConsumption = powerConsumption;
		}

		public ElectrolyserRecipe input(FluidType fluid, int amount) {
			this.inputFluid = fluid;
			this.inputFluidAmount = amount;
			return this;
		}

		public ElectrolyserRecipe outputFluids(FluidType f1, int a1, FluidType f2, int a2) {
			this.outputFluid1 = f1;
			this.outputFluid1Amount = a1;
			this.outputFluid2 = f2;
			this.outputFluid2Amount = a2;
			return this;
		}

		public ElectrolyserRecipe outputItem(ItemStack item) {
			this.outputItem = item;
			return this;
		}

		public boolean matches(FluidType inFluid, int inAmount) {
			return inFluid == this.inputFluid && inAmount >= this.inputFluidAmount;
		}
	}

	public void registerDefaults() {
		// Water electrolysis -> Hydrogen + Oxygen
		recipes.add(new ElectrolyserRecipe("elect.water", 100, 40)
				.input(Fluids.WATER, 1000)
				.outputFluids(Fluids.HYDROGEN, 666, Fluids.OXYGEN, 333));

		// Heavy Water electrolysis -> Deuterium + Oxygen
		recipes.add(new ElectrolyserRecipe("elect.heavywater", 150, 60)
				.input(Fluids.HEAVYWATER, 1000)
				.outputFluids(Fluids.DEUTERIUM, 666, Fluids.OXYGEN, 333));
	}

	@Nullable
	public ElectrolyserRecipe getRecipe(FluidType inFluid, int inAmount) {
		for (ElectrolyserRecipe recipe : recipes) {
			if (recipe.matches(inFluid, inAmount)) return recipe;
		}
		return null;
	}
}
