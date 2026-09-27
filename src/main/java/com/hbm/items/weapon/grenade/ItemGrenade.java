package com.hbm.items.weapon.grenade;

import com.hbm.entity.ModEntities;
import com.hbm.entity.projectile.EntityGrenadeGeneric;
import com.hbm.items.ItemBase;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemGrenade extends ItemBase {

	protected float explosionStrength;
	protected boolean isNuclear;

	public ItemGrenade(Properties properties, float explosionStrength, boolean isNuclear) {
		super(properties.stacksTo(16));
		this.explosionStrength = explosionStrength;
		this.isNuclear = isNuclear;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);

		level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.PLAYERS, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));

		if (!level.isClientSide) {
			EntityGrenadeGeneric grenade = new EntityGrenadeGeneric(ModEntities.GRENADE_GENERIC.get(), player, level, explosionStrength, isNuclear);
			grenade.setItem(stack);
			grenade.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.2F, 1.0F);
			level.addFreshEntity(grenade);
		}

		if (!player.getAbilities().instabuild) {
			stack.shrink(1);
		}

		return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
	}
}
