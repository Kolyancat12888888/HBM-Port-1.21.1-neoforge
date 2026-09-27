package com.hbm.hazard.helper;

import com.hbm.config.GeneralConfig;
import com.hbm.lib.Library;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class HazardHelper {
    public static Item reacherItem = null;

    public static boolean isHoldingReacher(final LivingEntity target) {
        if (target instanceof Player && !GeneralConfig.enable528 && reacherItem != null) {
            return Library.checkForHeld((Player) target, reacherItem);
        }
        return false;
    }

    public static void applyMobEffect(final LivingEntity target, final Holder<MobEffect> effect, final int duration, final int amplifier) {
        target.addEffect(new MobEffectInstance(effect, duration, amplifier));
    }
}
