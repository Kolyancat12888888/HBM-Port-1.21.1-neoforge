package com.hbm.items.machine;

import com.hbm.items.ItemBase;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemStamp extends ItemBase {

	protected StampType type;
	public static final Map<StampType, List<ItemStack>> stamps = new HashMap<>();

	public ItemStamp(StampType type, int dura) {
		super(new Item.Properties().durability(dura).stacksTo(1));
		this.type = type;
		if (type != null) {
			this.addStampToList(this, type);
		}
	}

	protected void addStampToList(Item item, StampType type) {
		List<ItemStack> list = stamps.computeIfAbsent(type, k -> new ArrayList<>());
		list.add(new ItemStack(item, 1));
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		if (stack.getMaxDamage() > 0) {
			tooltip.add(Component.literal("Durability: " + (stack.getMaxDamage() - stack.getDamageValue()) + " / " + stack.getMaxDamage()));
		}
	}

	public StampType getStampType(ItemStack stack) {
		return type;
	}

	public enum StampType {
		FLAT,
		PLATE,
		WIRE,
		CIRCUIT,
		C357,
		C44,
		C50,
		C9,
		PRINTING1,
		PRINTING2,
		PRINTING3,
		PRINTING4,
		PRINTING5,
		PRINTING6,
		PRINTING7,
		PRINTING8
	}
}
