package com.hbm.api.item;

import com.hbm.util.ArmorRegistry.HazardClass;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface IGasMask {
	/**
	 * Returns a list of HazardClasses which cannot be protected against by this mask
	 */
	List<HazardClass> getBlacklist(ItemStack stack);

	/**
	 * Returns the loaded filter, if there is any
	 */
	@NotNull
	ItemStack getFilter(ItemStack stack);

	/**
	 * Checks whether the provided filter can be screwed into the mask
	 */
	boolean isFilterApplicable(ItemStack stack, ItemStack filter);

	/**
	 * Writes the filter to the stack's data components
	 */
	void installFilter(ItemStack stack, ItemStack filter);

	default void setFilter(ItemStack stack, ItemStack filter) {
		installFilter(stack, filter);
	}

	/**
	 * Damages the installed filter
	 */
	void damageFilter(ItemStack stack, int damage);
}
