package com.hbm.hazard.type;

import com.hbm.config.RadiationConfig;
import com.hbm.hazard.modifier.IHazardModifier;
import com.hbm.util.I18nUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.List;

public class HazardTypeContaminating implements IHazardType {

    private static final int MAX_RADIUS = 500;

    private static int computeRadius(double level) {
        return (int) Math.min(Math.sqrt(level) + 0.5D, MAX_RADIUS);
    }

    @Override
    public void onUpdate(LivingEntity target, double level, ItemStack stack) {
    }

    @Override
    public void updateEntity(ItemEntity item, double level) {
        if (!RadiationConfig.enableContaminationOnGround) return;
        if (item == null) return;
        Level world = item.level();
        if (world.isClientSide()) return;

        if (item.onGround()) {
            item.discard();
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addHazardInformation(Player player, List<Component> list, double level, ItemStack stack, List<IHazardModifier> modifiers) {
        if (!RadiationConfig.enableContaminationOnGround) return;
        int radius = computeRadius(level);
        if (radius > 1) {
            list.add(Component.literal("§2[" + I18nUtil.resolveKey("trait.contaminating") + "]"));
            list.add(Component.literal("§a " + I18nUtil.resolveKey("trait.contaminating.radius", radius)));
        }
    }
}
