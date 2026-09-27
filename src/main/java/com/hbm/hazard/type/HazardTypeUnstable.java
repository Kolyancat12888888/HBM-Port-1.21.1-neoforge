package com.hbm.hazard.type;

import com.hbm.hazard.modifier.IHazardModifier;
import com.hbm.util.I18nUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.List;

public class HazardTypeUnstable implements IHazardType {
    private int timer = -1;

    public HazardTypeUnstable(int timer) {
        this.timer = timer;
    }

    @Override
    public void onUpdate(LivingEntity target, double level, ItemStack stack) {
        Level world = target.level();
        if (stack.getCount() > 0 && this.timer > 0) {
            // Unstable decay countdown
        }
    }

    @Override
    public void updateEntity(ItemEntity item, double level) {
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addHazardInformation(Player player, List<Component> tooltip, double level, ItemStack stack, List<IHazardModifier> modifiers) {
        if (this.timer != -1) {
            tooltip.add(Component.literal("§4" + I18nUtil.resolveKey("trait.unstable")));
            tooltip.add(Component.literal("§cDecay Time: " + (this.timer / 20) + "s"));
        }
    }
}
