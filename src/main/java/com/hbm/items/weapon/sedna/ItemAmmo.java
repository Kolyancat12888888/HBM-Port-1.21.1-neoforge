package com.hbm.items.weapon.sedna;

import com.hbm.items.ItemBase;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ItemAmmo extends ItemBase {

	public BulletConfig config;

	public ItemAmmo(Properties properties, BulletConfig config) {
		super(properties);
		this.config = config;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		if (config != null) {
			tooltip.add(Component.literal("Damage Mult: " + config.damageMult + "x"));
			if (config.armorPiercingPercent > 0) {
				tooltip.add(Component.literal("Armor Piercing: " + (int)(config.armorPiercingPercent * 100) + "%"));
			}
		}
	}
}
