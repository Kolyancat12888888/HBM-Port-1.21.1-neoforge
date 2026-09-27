package com.hbm.hazard.modifier;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface IHazardModifier {

    double modify(ItemStack stack, LivingEntity holder, double level);

    static double evalAllModifiers(final ItemStack stack, final LivingEntity entity, double level, final List<IHazardModifier> mods) {
        if (mods != null) {
            for (final IHazardModifier mod : mods) {
                level = mod.modify(stack, entity, level);
            }
        }
        return level;
    }
}
