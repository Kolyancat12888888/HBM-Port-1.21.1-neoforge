package com.hbm.items.armor;

import com.hbm.inventory.fluid.FluidType;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorMaterial;

public class ArmorDesh extends ArmorFSBFueled {
	public ArmorDesh(Holder<ArmorMaterial> material, Type type, Properties properties, FluidType fuelType, int maxFuel, int fillRate, int consumption, int drain) {
		super(material, type, properties, fuelType, maxFuel, fillRate, consumption, drain);
	}
}
