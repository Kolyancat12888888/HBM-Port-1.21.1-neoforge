package com.hbm.items.gear;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

public class ModArmor extends ArmorItem {
	public ModArmor(Holder<ArmorMaterial> material, Type type, Properties properties) {
		super(material, type, properties);
	}
}
