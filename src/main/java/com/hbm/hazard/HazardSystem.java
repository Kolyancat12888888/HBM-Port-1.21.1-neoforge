package com.hbm.hazard;

import com.hbm.hazard.modifier.IHazardModifier;
import com.hbm.hazard.type.IHazardType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class HazardSystem {

    public static final Map<TagKey<Item>, HazardData> tagMap = new ConcurrentHashMap<>();
    public static final Map<String, HazardData> oreMap = new ConcurrentHashMap<>();
    public static final Map<Item, HazardData> itemMap = new ConcurrentHashMap<>();
    public static final Set<Item> itemBlacklist = ConcurrentHashMap.newKeySet();
    public static final Set<String> oreBlacklist = ConcurrentHashMap.newKeySet();

    public static void register(Item item, HazardData data) {
        itemMap.put(item, data);
    }

    public static void register(Block block, HazardData data) {
        itemMap.put(block.asItem(), data);
    }

    public static void register(TagKey<Item> tag, HazardData data) {
        tagMap.put(tag, data);
    }

    public static void register(String oreDictKey, HazardData data) {
        oreMap.put(oreDictKey, data);
    }

    public static void blacklist(Item item) {
        itemBlacklist.add(item);
    }

    public static void blacklist(String oreDictKey) {
        oreBlacklist.add(oreDictKey);
    }

    public static boolean isStackHazardous(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return false;
        return !getHazardsFromStack(stack).isEmpty();
    }

    public static List<HazardEntry> getHazardsFromStack(ItemStack stack) {
        if (stack.isEmpty() || itemBlacklist.contains(stack.getItem())) {
            return Collections.emptyList();
        }

        List<HazardEntry> entries = new ArrayList<>();
        int mutex = 0;

        // Check tag mappings
        for (Map.Entry<TagKey<Item>, HazardData> entry : tagMap.entrySet()) {
            if (stack.is(entry.getKey())) {
                HazardData data = entry.getValue();
                if (data.doesOverride) entries.clear();
                if ((data.getMutex() & mutex) == 0) {
                    entries.addAll(data.entries);
                    mutex |= data.getMutex();
                }
            }
        }

        // Check exact item mapping
        HazardData itemData = itemMap.get(stack.getItem());
        if (itemData != null) {
            if (itemData.doesOverride) entries.clear();
            if ((itemData.getMutex() & mutex) == 0) {
                entries.addAll(itemData.entries);
            }
        }

        return Collections.unmodifiableList(entries);
    }

    public static double getHazardLevelFromStack(ItemStack stack, IHazardType hazard) {
        double totalLevel = 0.0;
        for (HazardEntry entry : getHazardsFromStack(stack)) {
            if (entry.type == hazard) {
                totalLevel += IHazardModifier.evalAllModifiers(stack, null, entry.baseLevel, entry.mods);
            }
        }
        return totalLevel;
    }

    public static double getRawRadsFromStack(ItemStack stack) {
        return getHazardLevelFromStack(stack, HazardRegistry.RADIATION);
    }

    public static double getRawRadsFromBlock(Block b) {
        return getHazardLevelFromStack(new ItemStack(b), HazardRegistry.RADIATION);
    }

    public static void applyHazards(ItemStack stack, LivingEntity entity) {
        if (stack.isEmpty() || entity == null) return;
        List<HazardEntry> hazards = getHazardsFromStack(stack);
        for (HazardEntry entry : hazards) {
            entry.applyHazard(stack, entity);
        }
    }
}
