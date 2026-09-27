package com.hbm.hazard.modifier;

import com.hbm.hazard.HazardRegistry;
import com.hbm.items.machine.ItemRBMKPellet;
import com.hbm.items.machine.ItemRBMKRod;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class HazardModifierRBMKRadiation implements IHazardModifier {

    final double target;
    final boolean linear;

    public HazardModifierRBMKRadiation(final double target, final boolean linear) {
        this.target = target;
        this.linear = linear;
    }

    @Override
    public double modify(final ItemStack stack, final LivingEntity holder, double level) {
        if (stack.getItem() instanceof ItemRBMKRod) {
            final double depletion = linear ? 1D - ItemRBMKRod.getEnrichment(stack) : 1D - Math.pow(ItemRBMKRod.getEnrichment(stack), 2);
            final double xenon = ItemRBMKRod.getPoisonLevel(stack);

            level = (level + (this.target - level) * depletion);
            level += HazardRegistry.xe135 * xenon;

        } else if (stack.getItem() instanceof ItemRBMKPellet) {
            int depl = ItemRBMKPellet.getDepletion(stack);
            level = level + (target - level) * ((ItemRBMKPellet.rectify(depl) % 5) / 4F);

            if (ItemRBMKPellet.hasXenon(stack) || ItemRBMKPellet.hasXenon(depl)) {
                level += HazardRegistry.xe135 * HazardRegistry.nugget;
            }
        }

        return level;
    }
}
