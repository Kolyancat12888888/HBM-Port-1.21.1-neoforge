package com.hbm.items.armor;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorMaterial;

public class ArmorRPA extends ArmorFSBPowered {
	public ArmorRPA(Holder<ArmorMaterial> material, Type type, Properties properties, long maxPower, long chargeRate, long consumption, long drain) {
		super(material, type, properties, maxPower, chargeRate, consumption, drain);
	}
}
