package com.hbm.inventory.recipes;

import com.hbm.inventory.RecipesCommon.AStack;
import net.minecraft.world.item.ItemStack;

public class BlastFurnaceRecipe {

	public String name;
	public int duration = 800;
	public AStack[] inputItems;
	public ItemStack[] outputItems;

	public BlastFurnaceRecipe(String name) {
		this.name = name;
	}

	public BlastFurnaceRecipe setDuration(int duration) {
		this.duration = duration;
		return this;
	}

	public BlastFurnaceRecipe inputItems(AStack... inputs) {
		this.inputItems = inputs;
		return this;
	}

	public BlastFurnaceRecipe outputItems(ItemStack... outputs) {
		this.outputItems = outputs;
		return this;
	}

	public boolean matches(ItemStack s0, ItemStack s1) {
		if (inputItems == null || inputItems.length == 0) return false;
		if (inputItems.length == 1) {
			if (!s0.isEmpty() && s1.isEmpty() && inputItems[0].matchesRecipe(s0, false)) return true;
			if (s0.isEmpty() && !s1.isEmpty() && inputItems[0].matchesRecipe(s1, false)) return true;
		} else if (inputItems.length == 2 && !s0.isEmpty() && !s1.isEmpty()) {
			if (inputItems[0].matchesRecipe(s0, false) && inputItems[1].matchesRecipe(s1, false)) return true;
			if (inputItems[1].matchesRecipe(s0, false) && inputItems[0].matchesRecipe(s1, false)) return true;
		}
		return false;
	}
}
