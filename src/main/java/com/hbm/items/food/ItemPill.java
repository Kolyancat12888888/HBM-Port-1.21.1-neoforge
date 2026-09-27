package com.hbm.items.food;

import com.hbm.capability.HbmLivingProps;
import com.hbm.items.ItemBase;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Random;

public class ItemPill extends ItemBase {

    protected final int hunger;
    protected final String pillType;
    private static final Random rand = new Random();

    public ItemPill(Properties properties, int hunger, String pillType) {
        super(properties);
        this.hunger = hunger;
        this.pillType = pillType;
    }

    public ItemPill(int hunger, String pillType) {
        this(new Properties(), hunger, pillType);
    }

    public ItemPill(int hunger, String pillType, String texture) {
        this(new Properties(), hunger, pillType);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 10;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.EAT;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
        if (!world.isClientSide() && entity instanceof Player player) {
            applyPillEffects(player);
        }
        if (entity instanceof Player player && !player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        return stack;
    }

    protected void applyPillEffects(Player player) {
        switch (pillType) {
            case "pill_iodine" -> {
                player.removeEffect(MobEffects.BLINDNESS);
                player.removeEffect(MobEffects.CONFUSION);
                player.removeEffect(MobEffects.DIG_SLOWDOWN);
                player.removeEffect(MobEffects.HUNGER);
                player.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
                player.removeEffect(MobEffects.POISON);
                player.removeEffect(MobEffects.WEAKNESS);
                player.removeEffect(MobEffects.WITHER);
            }
            case "plan_c" -> {
                player.hurt(player.damageSources().genericKill(), 1000);
            }
            case "siox" -> {
                HbmLivingProps.setAsbestos(player, 0);
            }
            case "pill_herbal" -> {
                HbmLivingProps.setAsbestos(player, 0);
                HbmLivingProps.incrementRadiation(player, -100F);
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 10 * 20, 0));
                player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 10 * 60 * 20, 2));
                player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 10 * 60 * 20, 2));
                player.addEffect(new MobEffectInstance(MobEffects.POISON, 5 * 20, 2));
            }
            case "xanax" -> {
                double digamma = HbmLivingProps.getDigamma(player);
                HbmLivingProps.setDigamma(player, Math.max(digamma - 0.5D, 0D));
            }
            case "chocolate" -> {
                player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 60 * 20, 3));
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 60 * 20, 3));
                player.addEffect(new MobEffectInstance(MobEffects.JUMP, 60 * 20, 3));
            }
            case "fmn" -> {
                double digamma = HbmLivingProps.getDigamma(player);
                HbmLivingProps.setDigamma(player, Math.min(digamma, 2D));
                player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 60, 0));
            }
            case "five_htp" -> {
                HbmLivingProps.setDigamma(player, 0D);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        switch (pillType) {
            case "pill_iodine" -> tooltipComponents.add(Component.literal("Removes negative effects"));
            case "plan_c" -> tooltipComponents.add(Component.literal("Deadly"));
            case "radx" -> tooltipComponents.add(Component.literal("Increases radiation resistance by 0.4 for 3 minutes"));
            case "siox" -> tooltipComponents.add(Component.literal("Reverses mesothelioma with the power of Asbestos!"));
            case "pill_herbal" -> {
                tooltipComponents.add(Component.literal("Effective treatment against lung disease and mild radiation poisoning"));
                tooltipComponents.add(Component.literal("Comes with side effects"));
            }
            case "xanax" -> tooltipComponents.add(Component.literal("Removes 500mDRX"));
            case "fmn" -> tooltipComponents.add(Component.literal("Removes all DRX above 2,000mDRX"));
            case "five_htp" -> tooltipComponents.add(Component.literal("Removes all DRX, Stability for 10 minutes"));
        }
    }
}
