package com.hbm.inventory.recipes;

import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemStamp;
import com.hbm.items.machine.ItemStamp.StampType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class PressRecipes {

	public record PressKey(AStack ingredient, StampType stamp) {}

	public static final Map<PressKey, ItemStack> recipes = new HashMap<>();

	static {
		registerDefaults();
	}

	public static void registerDefaults() {
		// FLAT Stamp Recipes
		add(StampType.FLAT, new OreDictStack("dustQuartz"), new ItemStack(Items.QUARTZ));
		add(StampType.FLAT, new OreDictStack("dustDiamond"), new ItemStack(Items.DIAMOND));
		add(StampType.FLAT, new OreDictStack("dustEmerald"), new ItemStack(Items.EMERALD));

		// PLATE Stamp Recipes
		add(StampType.PLATE, new OreDictStack("ingotIron"), new ItemStack(ModItems.PLATE_SATURNITE.get(), 1)); // plates
		add(StampType.PLATE, new OreDictStack("ingotSteel"), new ItemStack(ModItems.PLATE_SATURNITE.get(), 1));
		add(StampType.PLATE, new OreDictStack("ingotLead"), new ItemStack(ModItems.PLATE_SATURNITE.get(), 1));
		add(StampType.PLATE, new OreDictStack("ingotCopper"), new ItemStack(ModItems.PLATE_SATURNITE.get(), 1));
		add(StampType.PLATE, new OreDictStack("ingotTitanium"), new ItemStack(ModItems.PLATE_SATURNITE.get(), 1));

		// WIRE Stamp Recipes
		add(StampType.WIRE, new OreDictStack("ingotCopper"), new ItemStack(ModItems.POWDER_COPPER.get(), 4));
		add(StampType.WIRE, new OreDictStack("ingotGold"), new ItemStack(ModItems.POWDER_GOLD.get(), 4));
	}

	public static void add(StampType stamp, AStack in, ItemStack out) {
		recipes.put(new PressKey(in, stamp), out);
	}

	@NotNull
	public static ItemStack getOutput(ItemStack ingredient, ItemStack stamp) {
		if (ingredient == null || ingredient.isEmpty() || stamp == null || stamp.isEmpty())
			return ItemStack.EMPTY;

		if (!(stamp.getItem() instanceof ItemStamp itemStamp))
			return ItemStack.EMPTY;

		StampType type = itemStamp.getStampType(stamp);

		for (Map.Entry<PressKey, ItemStack> entry : recipes.entrySet()) {
			if (entry.getKey().stamp() == type && entry.getKey().ingredient().matchesRecipe(ingredient, true)) {
				return entry.getValue().copy();
			}
		}
		return ItemStack.EMPTY;
	}
}
