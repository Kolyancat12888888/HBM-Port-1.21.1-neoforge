package com.hbm.items.armor;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

public class ArmorDiesel extends ArmorFSBFueled {

	public ArmorDiesel(Holder<ArmorMaterial> material, Type type, Properties properties, FluidType fuelType, int maxFuel, int fillRate, int consumption, int drain) {
		super(material, type, properties, fuelType, maxFuel, fillRate, consumption, drain);
	}

	@Override
	public boolean acceptsFluid(FluidType type, ItemStack stack) {
		return type == Fluids.DIESEL || type == Fluids.DIESEL_CRACK;
	}
}
