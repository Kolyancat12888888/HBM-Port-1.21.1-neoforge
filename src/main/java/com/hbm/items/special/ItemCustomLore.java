package com.hbm.items.special;

import com.hbm.util.I18nUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;

import java.util.Arrays;
import java.util.List;

public class ItemCustomLore extends Item {

	public Rarity rarity;

	public ItemCustomLore(Properties properties) {
		super(properties);
	}

	public ItemCustomLore() {
		super(new Properties());
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
		String unloc = this.getDescriptionId() + ".desc";
		String loc = I18nUtil.resolveKey(unloc);

		if (!unloc.equals(loc)) {
			String[] locs = loc.split("\\$");
			for (String line : locs) {
				tooltipComponents.add(Component.literal(line));
			}
		}
	}

	public ItemCustomLore(Properties properties, Rarity rarity) {
		super(properties.rarity(rarity));
		this.rarity = rarity;
	}

	public ItemCustomLore setRarity(Rarity rarity) {
		this.rarity = rarity;
		return this;
	}
}
