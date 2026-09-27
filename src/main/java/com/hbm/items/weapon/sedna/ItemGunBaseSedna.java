package com.hbm.items.weapon.sedna;

import com.hbm.entity.ModEntities;
import com.hbm.entity.projectile.EntityBulletBaseMK4;
import com.hbm.items.ItemBase;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemGunBaseSedna extends ItemBase {

	protected GunConfig config;
	protected BulletConfig defaultBullet;

	public ItemGunBaseSedna(Properties properties, GunConfig config, BulletConfig defaultBullet) {
		super(properties.durability(config != null ? config.durability : 500).stacksTo(1));
		this.config = config;
		this.defaultBullet = defaultBullet;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);

		if (!level.isClientSide) {
			Receiver receiver = config != null ? config.getReceiver(0) : null;
			float baseDamage = receiver != null ? receiver.baseDamage : 10.0F;

			EntityBulletBaseMK4 bullet = new EntityBulletBaseMK4(ModEntities.BULLET_MK4.get(), player, level, defaultBullet, baseDamage);
			bullet.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, defaultBullet != null ? defaultBullet.velocity : 3.0F, receiver != null ? receiver.spreadInnate : 1.0F);
			level.addFreshEntity(bullet);

			stack.hurtAndBreak(1, player, net.minecraft.world.entity.LivingEntity.getSlotForHand(hand));
			player.getCooldowns().addCooldown(this, receiver != null ? receiver.delayAfterFire : 10);
		}

		return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
	}
}
