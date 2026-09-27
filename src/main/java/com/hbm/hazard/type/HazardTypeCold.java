package com.hbm.hazard.type;

import com.hbm.config.RadiationConfig;
import com.hbm.hazard.helper.HazardHelper;
import com.hbm.hazard.modifier.IHazardModifier;
import com.hbm.util.I18nUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.List;

import static com.hbm.hazard.helper.HazardHelper.applyMobEffect;

public class HazardTypeCold implements IHazardType {

    @Override
    public void onUpdate(final LivingEntity target, final double level, final ItemStack stack) {
        final boolean reacher = HazardHelper.isHoldingReacher(target);
        if (RadiationConfig.disableCold || reacher) return;

        final int baseLevel = (int) level - 1;
        final int witherLevel = (int) level - 3;

        applyMobEffect(target, MobEffects.DIG_SLOWDOWN, 110, baseLevel);
        applyMobEffect(target, MobEffects.MOVEMENT_SLOWDOWN, 110, Math.min(4, baseLevel));
        applyMobEffect(target, MobEffects.WEAKNESS, 110, baseLevel);

        if (level > 4) {
            applyMobEffect(target, MobEffects.WITHER, 110, witherLevel);
        }
    }

    @Override
    public void updateEntity(final ItemEntity item, final double level) {
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addHazardInformation(final Player player, final List<Component> list, final double level, final ItemStack stack, final List<IHazardModifier> modifiers) {
        list.add(Component.literal("§b[" + I18nUtil.resolveKey("trait.cryogenic") + "]"));
    }
}
