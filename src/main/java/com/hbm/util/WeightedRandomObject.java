package com.hbm.util;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class WeightedRandomObject {
    public final int itemWeight;
    public final Object item;

    public WeightedRandomObject(Object o, int weight) {
        this.itemWeight = weight;
        this.item = o;
    }

    public ItemStack asStack() {
        if (item instanceof ItemStack stack)
            return stack.copy();
        return null;
    }

    public Item asItem() {
        if (item instanceof Item it)
            return it;
        return null;
    }

    public String asString() {
        if (item instanceof String str)
            return str;
        return null;
    }
}
