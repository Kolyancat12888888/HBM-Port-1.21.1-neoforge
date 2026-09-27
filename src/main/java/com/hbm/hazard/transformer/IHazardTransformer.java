package com.hbm.hazard.transformer;

import com.hbm.hazard.HazardEntry;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface IHazardTransformer {
    void transformPre(ItemStack stack, List<HazardEntry> entries);
    void transformPost(ItemStack stack, List<HazardEntry> entries);
}
