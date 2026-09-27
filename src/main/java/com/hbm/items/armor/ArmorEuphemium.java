package com.hbm.items.armor;

import com.hbm.handler.ArmorUtil;
import com.hbm.items.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ArmorEuphemium extends ArmorItem {

	public ArmorEuphemium(Holder<ArmorMaterial> material, Type type, Properties properties) {
		super(material, type, properties);
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
		if (level.isClientSide || !(entity instanceof Player player)) return;

		if (player.getItemBySlot(EquipmentSlot.CHEST) == stack) {
			if (ArmorUtil.checkArmor(player, ModItems.euphemium_helmet, ModItems.euphemium_plate, ModItems.euphemium_legs, ModItems.euphemium_boots)) {
				player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 20, 127, true, false));
				player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20, 127, true, false));
				player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 20, 127, true, false));
				player.addEffect(new MobEffectInstance(MobEffects.SATURATION, 20, 127, true, false));

				if (player.getDeltaMovement().y < -0.25D) {
					player.setDeltaMovement(player.getDeltaMovement().x, -0.25D, player.getDeltaMovement().z);
					player.resetFallDistance();
				}
			}
		}
	}
}
