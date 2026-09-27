package com.hbm.items.special;

import com.hbm.util.Tuple.Pair;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.function.BiConsumer;

public class ItemSimpleConsumable extends ItemCustomLore {

	private BiConsumer<ItemStack, Player> useAction;
	private BiConsumer<ItemStack, Player> useActionServer;
	private BiConsumer<ItemStack, Pair<LivingEntity, LivingEntity>> hitAction;
	private BiConsumer<ItemStack, Pair<LivingEntity, LivingEntity>> hitActionServer;

	public ItemSimpleConsumable(Properties properties) {
		super(properties);
	}

	public ItemSimpleConsumable() {
		super(new Properties());
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (this.useAction != null)
			this.useAction.accept(stack, player);

		if (!world.isClientSide() && this.useActionServer != null)
			this.useActionServer.accept(stack, player);

		return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), world.isClientSide());
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity attacker) {
		if (this.hitAction != null)
			this.hitAction.accept(stack, new Pair<>(entity, attacker));

		if (!entity.level().isClientSide() && this.hitActionServer != null)
			this.hitActionServer.accept(stack, new Pair<>(entity, attacker));

		return false;
	}

	public static void giveSoundAndDecrement(ItemStack stack, LivingEntity entity, SoundEvent sound, ItemStack container) {
		stack.shrink(1);
		entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), sound, SoundSource.PLAYERS, 1.0F, 1.0F);
		ItemSimpleConsumable.tryAddItem(entity, container);
	}

	public static void addPotionEffect(LivingEntity entity, Holder<MobEffect> effect, int duration, int level) {
		if (!entity.hasEffect(effect)) {
			entity.addEffect(new MobEffectInstance(effect, duration, level));
		} else {
			MobEffectInstance active = entity.getEffect(effect);
			int d = duration;
			if (active != null && level == active.getAmplifier())
				d += active.getDuration();
			entity.addEffect(new MobEffectInstance(effect, d, level));
		}
	}

	public static void tryAddItem(LivingEntity entity, ItemStack stack) {
		if (entity instanceof Player player) {
			if (!player.getInventory().add(stack)) {
				player.drop(stack, false);
			}
		}
	}

	public ItemSimpleConsumable setUseAction(BiConsumer<ItemStack, Player> delegate) { this.useAction = delegate; return this; }
	public ItemSimpleConsumable setUseActionServer(BiConsumer<ItemStack, Player> delegate) { this.useActionServer = delegate; return this; }
	public ItemSimpleConsumable setHitAction(BiConsumer<ItemStack, Pair<LivingEntity, LivingEntity>> delegate) { this.hitAction = delegate; return this; }
	public ItemSimpleConsumable setHitActionServer(BiConsumer<ItemStack, Pair<LivingEntity, LivingEntity>> delegate) { this.hitActionServer = delegate; return this; }
}
