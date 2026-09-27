package com.hbm.items.food;

import com.hbm.capability.HbmLivingProps;
import com.hbm.items.ItemBase;
import com.hbm.items.ModItems;
import com.hbm.potion.HbmPotion;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import java.util.List;

public class ItemPill extends ItemBase {

    public enum PillType {
        IODINE,
        PLAN_C,
        RED,
        RADX,
        SIOX,
        HERBAL,
        XANAX,
        FMN,
        FIVE_HTP,
        CHOCOLATE
    }

    private final PillType type;

    public ItemPill(PillType type) {
        super(new Properties().stacksTo(64));
        this.type = type;
    }

    public ItemPill(PillType type, Properties properties) {
        super(properties.stacksTo(64));
        this.type = type;
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
        ItemStack stack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
        if (!world.isClientSide() && entity instanceof Player player) {
            applyPillEffects(player);
            if (!player.isCreative()) {
                stack.shrink(1);
            }
        }
        return stack;
    }

    private void applyPillEffects(Player player) {
        switch (type) {
            case IODINE -> {
                player.removeEffect(MobEffects.BLINDNESS);
                player.removeEffect(MobEffects.CONFUSION);
                player.removeEffect(MobEffects.DIG_SLOWDOWN);
                player.removeEffect(MobEffects.HUNGER);
                player.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
                player.removeEffect(MobEffects.POISON);
                player.removeEffect(MobEffects.WEAKNESS);
                player.removeEffect(MobEffects.WITHER);
                if (HbmPotion.radiation != null) player.removeEffect(HbmPotion.radiation);
            }
            case PLAN_C -> {
                player.hurt(player.damageSources().genericKill(), 100000.0F);
            }
            case RED -> {
                if (HbmPotion.death != null) {
                    player.addEffect(new MobEffectInstance(HbmPotion.death, 60 * 60 * 20, 0));
                }
            }
            case RADX -> {
                if (HbmPotion.radx != null) {
                    player.addEffect(new MobEffectInstance(HbmPotion.radx, 3 * 60 * 20, 3));
                }
            }
            case SIOX -> {
                HbmLivingProps.setAsbestos(player, 0);
                HbmLivingProps.setBlackLung(player, Math.min(HbmLivingProps.getBlackLung(player), HbmLivingProps.maxBlacklung / 5));
            }
            case HERBAL -> {
                HbmLivingProps.setAsbestos(player, 0);
                HbmLivingProps.setBlackLung(player, Math.min(HbmLivingProps.getBlackLung(player), HbmLivingProps.maxBlacklung / 5));
                HbmLivingProps.incrementRadiation(player, -100.0F);
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 10 * 20, 0));
                player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 10 * 60 * 20, 2));
                player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 10 * 60 * 20, 2));
                player.addEffect(new MobEffectInstance(MobEffects.POISON, 5 * 20, 2));
            }
            case XANAX -> {
                double digamma = HbmLivingProps.getDigamma(player);
                HbmLivingProps.setDigamma(player, Math.max(digamma - 0.5, 0.0));
            }
            case FMN -> {
                double digamma = HbmLivingProps.getDigamma(player);
                HbmLivingProps.setDigamma(player, Math.min(digamma, 2.0));
                player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 60, 0));
            }
            case FIVE_HTP -> {
                HbmLivingProps.setDigamma(player, 0.0);
                if (HbmPotion.stability != null) {
                    player.addEffect(new MobEffectInstance(HbmPotion.stability, 10 * 60 * 20, 0));
                }
            }
            case CHOCOLATE -> {
                player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 60 * 20, 3));
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 60 * 20, 3));
                player.addEffect(new MobEffectInstance(MobEffects.JUMP, 60 * 20, 3));
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        switch (type) {
            case IODINE -> tooltip.add(Component.literal("Removes negative effects and radiation potion").withStyle(ChatFormatting.GRAY));
            case PLAN_C -> tooltip.add(Component.literal("Deadly").withStyle(ChatFormatting.DARK_RED));
            case RADX -> tooltip.add(Component.literal("Increases radiation resistance for 3 minutes").withStyle(ChatFormatting.GOLD));
            case SIOX -> tooltip.add(Component.literal("Reverses mesothelioma with the power of Asbestos!").withStyle(ChatFormatting.AQUA));
            case HERBAL -> {
                tooltip.add(Component.literal("Effective treatment against lung disease and mild radiation poisoning").withStyle(ChatFormatting.GREEN));
                tooltip.add(Component.literal("Comes with side effects").withStyle(ChatFormatting.RED));
            }
            case XANAX -> tooltip.add(Component.literal("Removes 500mDRX").withStyle(ChatFormatting.LIGHT_PURPLE));
            case FMN -> tooltip.add(Component.literal("Removes all DRX above 2,000mDRX").withStyle(ChatFormatting.LIGHT_PURPLE));
            case FIVE_HTP -> tooltip.add(Component.literal("Removes all DRX, grants Stability for 10 minutes").withStyle(ChatFormatting.LIGHT_PURPLE));
            case CHOCOLATE -> tooltip.add(Component.literal("Sugar rush!").withStyle(ChatFormatting.GOLD));
            case RED -> tooltip.add(Component.literal("Pure death").withStyle(ChatFormatting.DARK_RED));
        }
    }
}
