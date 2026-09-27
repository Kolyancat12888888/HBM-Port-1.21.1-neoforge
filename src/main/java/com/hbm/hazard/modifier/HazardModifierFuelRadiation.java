package com.hbm.hazard.modifier;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class HazardModifierFuelRadiation implements IHazardModifier {

    final double target;

    public HazardModifierFuelRadiation(final double target) {
        this.target = target;
    }

    @Override
    public double modify(final ItemStack stack, final LivingEntity holder, double level) {
        double durability = stack.isDamageableItem() ? (double) stack.getDamageValue() / (double) stack.getMaxDamage() : 0.0D;
        final double depletion = Math.pow(durability, 0.4D);
        level = (level + (this.target - level) * depletion);
        return level;
    }
}
