package com.hbm.items.food;

import com.hbm.items.ItemBase;
import com.hbm.items.ModItems;
import com.hbm.potion.HbmPotion;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class ItemRadaway extends ItemBase {

    public enum RadawayType {
        NORMAL(200, 24),
        STRONG(100, 99),
        FLUSH(50, 399);

        public final int duration;
        public final int amplifier;

        RadawayType(int duration, int amplifier) {
            this.duration = duration;
            this.amplifier = amplifier;
        }
    }

    private final RadawayType type;

    public ItemRadaway(RadawayType type) {
        super(new Properties().stacksTo(16));
        this.type = type;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 32;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
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
            if (HbmPotion.radaway != null) {
                player.addEffect(new MobEffectInstance(HbmPotion.radaway, type.duration, type.amplifier));
            }
            world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.GENERIC_DRINK, SoundSource.PLAYERS, 1.0F, 1.0F);

            if (!player.isCreative()) {
                stack.shrink(1);
                if (ModItems.iv_empty != null) {
                    player.getInventory().add(new ItemStack(ModItems.iv_empty));
                }
            }
        }
        return stack;
    }
}
