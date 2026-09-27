package com.hbm.items;

import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemBase extends Item {

	public static final List<Item> ALL_ITEMS = new ArrayList<>();

	public ItemBase(Properties properties) {
		super(properties);
		ALL_ITEMS.add(this);
	}

	public ItemBase() {
		this(new Properties());
	}
}
