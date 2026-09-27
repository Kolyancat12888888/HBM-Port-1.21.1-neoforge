package com.hbm.inventory;

import com.hbm.items.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Contract;

import java.util.*;

public class RecipesCommon {

    @Contract("null -> null; !null -> !null")
    public static ItemStack[] copyStackArray(ItemStack[] array) {
        if (array == null) return null;
        ItemStack[] clone = new ItemStack[array.length];
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null && !array[i].isEmpty()) clone[i] = array[i].copy();
            else clone[i] = ItemStack.EMPTY;
        }
        return clone;
    }

    public static abstract class AStack implements Comparable<AStack> {
        public int stacksize = 1;

        public boolean isApplicable(ItemStack stack) {
            return matchesRecipe(stack, true);
        }

        public boolean isApplicable(ComparableStack comp) {
            if (this instanceof ComparableStack c) {
                return c.item == comp.item;
            }
            if (this instanceof OreDictStack o) {
                return o.matches(comp.item);
            }
            return false;
        }

        public AStack singulize() {
            this.stacksize = 1;
            return this;
        }

        public int count() {
            return stacksize;
        }

        public void setCount(int c) {
            this.stacksize = c;
        }

        public abstract boolean matchesRecipe(ItemStack stack, boolean ignoreSize);
        public abstract AStack copy();
        public abstract AStack copy(int stacksize);
        public abstract ItemStack getStack();
        public abstract List<ItemStack> getStackList();
        public abstract List<ItemStack> extractForJEI();

        @Override
        public int compareTo(AStack o) {
            return Integer.compare(this.hashCode(), o.hashCode());
        }
    }

    public static class ComparableStack extends AStack {
        public Item item;

        public ComparableStack(ItemStack stack) {
            this.item = stack.getItem();
            this.stacksize = stack.getCount();
        }

        public ComparableStack(ItemLike itemLike) {
            this.item = itemLike.asItem();
            this.stacksize = 1;
        }

        public ComparableStack(ItemLike itemLike, int stacksize) {
            this.item = itemLike.asItem();
            this.stacksize = stacksize;
        }

        public ComparableStack(ItemLike itemLike, int stacksize, int meta) {
            this(itemLike, stacksize);
        }

        public ComparableStack makeSingular() {
            this.stacksize = 1;
            return this;
        }

        public ItemStack toStack() {
            return new ItemStack(item == null ? Items.AIR : item, stacksize);
        }

        @Override
        public ItemStack getStack() {
            return toStack();
        }

        @Override
        public List<ItemStack> getStackList() {
            return Collections.singletonList(getStack());
        }

        @Override
        public boolean matchesRecipe(ItemStack stack, boolean ignoreSize) {
            if (stack == null || stack.isEmpty()) return false;
            if (!ignoreSize && stack.getCount() < this.stacksize) return false;
            return stack.getItem() == this.item;
        }

        @Override
        public AStack copy() {
            return new ComparableStack(item, stacksize);
        }

        @Override
        public AStack copy(int size) {
            return new ComparableStack(item, size);
        }

        @Override
        public List<ItemStack> extractForJEI() {
            return Collections.singletonList(toStack());
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj instanceof ComparableStack other) {
                return this.item == other.item;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return item != null ? item.hashCode() : 0;
        }
    }

    public static class OreDictStack extends AStack {
        public String name;
        public TagKey<Item> tag;

        public OreDictStack(String name) {
            this.name = name;
            this.stacksize = 1;
            this.tag = TagKey.create(Registries.ITEM, ResourceLocation.parse(convertOreDict(name)));
        }

        public OreDictStack(String name, int size) {
            this(name);
            this.stacksize = size;
        }

        public static String convertOreDict(String name) {
            if (name.contains(":")) return name;
            // Common convention c:ores/iron, c:ingots/copper, etc.
            return "c:" + name.toLowerCase();
        }

        public boolean matches(Item it) {
            if (it == null) return false;
            Holder<Item> holder = BuiltInRegistries.ITEM.wrapAsHolder(it);
            if (tag != null && holder.is(tag)) return true;
            String key = BuiltInRegistries.ITEM.getKey(it).getPath();
            return key.contains(name.toLowerCase());
        }

        @Override
        public boolean matchesRecipe(ItemStack stack, boolean ignoreSize) {
            if (stack == null || stack.isEmpty()) return false;
            if (!ignoreSize && stack.getCount() < this.stacksize) return false;
            return matches(stack.getItem());
        }

        @Override
        public AStack copy() {
            return new OreDictStack(name, stacksize);
        }

        @Override
        public AStack copy(int size) {
            return new OreDictStack(name, size);
        }

        @Override
        public ItemStack getStack() {
            return ItemStack.EMPTY;
        }

        @Override
        public List<ItemStack> getStackList() {
            List<ItemStack> list = new ArrayList<>();
            for (Holder<Item> holder : BuiltInRegistries.ITEM.getTagOrEmpty(tag)) {
                list.add(new ItemStack(holder.value(), stacksize));
            }
            return list;
        }

        @Override
        public List<ItemStack> extractForJEI() {
            return getStackList();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj instanceof OreDictStack other) {
                return Objects.equals(this.name, other.name);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return name != null ? name.hashCode() : 0;
        }
    }
}
