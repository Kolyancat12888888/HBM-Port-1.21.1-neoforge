package com.hbm.items.special;

import com.hbm.items.ItemBase;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ItemSiegeCoin extends ItemBase {

	protected final int tier;

	public ItemSiegeCoin(Properties properties, int tier) {
		super(properties.rarity(Rarity.UNCOMMON));
		this.tier = tier;
	}

	public ItemSiegeCoin(int tier) {
		this(new Properties(), tier);
	}

	public int getTier() {
		return tier;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
		tooltip.add(Component.literal(ChatFormatting.YELLOW + "Tier " + tier));
	}
}
