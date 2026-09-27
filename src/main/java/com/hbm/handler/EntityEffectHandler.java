package com.hbm.handler;

import com.hbm.capability.HbmLivingProps;
import com.hbm.config.GeneralConfig;
import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.potion.HbmPotion;
import com.hbm.util.ContaminationUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.Random;

public class EntityEffectHandler {

    private static final Random RANDOM = new Random();

    public static void onUpdate(LivingEntity entity) {
        if (entity.level().isClientSide() || !entity.isAlive()) return;

        handleEnvironmentalRadiation(entity);
        handleRadiationSickness(entity);
        handleDigamma(entity);
        handleLungDisease(entity);
    }

    private static void handleEnvironmentalRadiation(LivingEntity entity) {
        if (ContaminationUtil.isRadImmune(entity)) return;

        Level world = entity.level();
        BlockPos pos = entity.blockPosition();
        double chunkRad = ChunkRadiationManager.proxy.getRadiation(world, pos);

        if (chunkRad > 0.0) {
            ContaminationUtil.contaminate(entity, ContaminationUtil.HazardType.RADIATION, ContaminationUtil.ContaminationType.CREATIVE, chunkRad / 20.0);
        }

        if (entity.tickCount % 20 == 0) {
            HbmLivingProps.setRadBuf(entity, HbmLivingProps.getRadEnv(entity));
            HbmLivingProps.setRadEnv(entity, 0);
        }
    }

    private static void handleRadiationSickness(LivingEntity entity) {
        if (!GeneralConfig.enableRads || (entity instanceof Player player && (player.isCreative() || player.isSpectator()))) {
            return;
        }

        double eRad = HbmLivingProps.getRadiation(entity);
        if (eRad < 50) return;

        int rng = RANDOM.nextInt(21000);

        // Entity Mutations at high rads
        if (eRad >= 200 && entity instanceof Creeper creeper) {
            if (rng % 10 == 0) {
                entity.hurt(entity.damageSources().generic(), 100.0F);
            }
        } else if (eRad >= 50 && entity instanceof Cow cow && !(cow instanceof MushroomCow)) {
            MushroomCow mooshroom = EntityType.MOOSHROOM.create(entity.level());
            if (mooshroom != null) {
                mooshroom.moveTo(entity.getX(), entity.getY(), entity.getZ(), entity.getYRot(), entity.getXRot());
                entity.level().addFreshEntity(mooshroom);
                entity.discard();
                return;
            }
        } else if (eRad >= 500 && entity instanceof Villager villager) {
            ZombieVillager zombieVillager = EntityType.ZOMBIE_VILLAGER.create(entity.level());
            if (zombieVillager != null) {
                zombieVillager.moveTo(entity.getX(), entity.getY(), entity.getZ(), entity.getYRot(), entity.getXRot());
                entity.level().addFreshEntity(zombieVillager);
                entity.discard();
                return;
            }
        }

        if (eRad < 200) return;

        if (eRad >= 1000) {
            entity.hurt(entity.damageSources().generic(), 1000.0F);
            HbmLivingProps.setRadiation(entity, 0);
        } else if (eRad >= 800) {
            if (rng % 300 == 0) entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 5 * 30, 0));
            if (rng % 300 == 50) entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10 * 20, 2));
            if (rng % 300 == 100) entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 10 * 20, 2));
            if (rng % 500 == 0) entity.addEffect(new MobEffectInstance(MobEffects.POISON, 3 * 20, 2));
            if (rng % 700 == 0) entity.addEffect(new MobEffectInstance(MobEffects.WITHER, 3 * 20, 1));
            if (rng % 300 == 150) entity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 5 * 20, 3));
            if (rng % 300 == 200) entity.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 5 * 20, 3));
        } else if (eRad >= 600) {
            if (rng % 300 == 0) entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 5 * 30, 0));
            if (rng % 300 == 50) entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10 * 20, 2));
            if (rng % 300 == 100) entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 10 * 20, 2));
            if (rng % 500 == 0) entity.addEffect(new MobEffectInstance(MobEffects.POISON, 3 * 20, 1));
            if (rng % 300 == 150) entity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 3 * 20, 3));
            if (rng % 400 == 0) entity.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 6 * 20, 2));
        } else if (eRad >= 400) {
            if (rng % 300 == 0) entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 5 * 30, 0));
            if (rng % 500 == 50) entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 5 * 20, 0));
            if (rng % 300 == 100) entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 5 * 20, 1));
            if (rng % 500 == 150) entity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 3 * 20, 2));
            if (rng % 600 == 0) entity.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 4 * 20, 1));
        } else {
            if (rng % 300 == 0) entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 5 * 20, 0));
            if (rng % 500 == 0) entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 5 * 20, 0));
            if (rng % 700 == 0) entity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 3 * 20, 2));
            if (rng % 800 == 0) entity.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 4 * 20, 0));
        }
    }

    private static void handleDigamma(LivingEntity entity) {
        double digamma = HbmLivingProps.getDigamma(entity);
        if (digamma <= 0.0) return;

        if (digamma >= 10.0) {
            entity.hurt(entity.damageSources().generic(), 5000000.0F);
        }
    }

    private static void handleLungDisease(LivingEntity entity) {
        if (entity instanceof Player player && player.isCreative()) {
            HbmLivingProps.setBlackLung(entity, 0);
            HbmLivingProps.setAsbestos(entity, 0);
            return;
        }

        int asbestos = HbmLivingProps.getAsbestos(entity);
        int blacklung = HbmLivingProps.getBlackLung(entity);

        if (asbestos >= HbmLivingProps.maxAsbestos) {
            HbmLivingProps.setAsbestos(entity, 0);
            entity.hurt(entity.damageSources().generic(), 1000.0F);
        }

        if (blacklung >= HbmLivingProps.maxBlacklung) {
            HbmLivingProps.setBlackLung(entity, 0);
            entity.hurt(entity.damageSources().generic(), 1000.0F);
        }
    }
}
