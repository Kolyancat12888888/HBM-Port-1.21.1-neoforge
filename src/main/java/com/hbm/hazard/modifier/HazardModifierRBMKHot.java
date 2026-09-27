package com.hbm.hazard.modifier;

import com.hbm.items.machine.ItemRBMKRod;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class HazardModifierRBMKHot implements IHazardModifier {

    @Override
    public double modify(final ItemStack stack, final LivingEntity holder, double level) {
        level = 0;
        if (stack.getItem() instanceof ItemRBMKRod) {
            final double heat = ItemRBMKRod.getHullHeat(stack);
            final int fire = (int) Math.min(Math.ceil((heat - 100) / 10D), 60);
            level = Math.max(0, fire);
        }
        return level;
    }
}
