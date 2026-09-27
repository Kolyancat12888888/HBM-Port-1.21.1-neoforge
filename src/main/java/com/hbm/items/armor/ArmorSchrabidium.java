package com.hbm.items.armor;

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

public class ArmorSchrabidium extends ArmorItem {

	public ArmorSchrabidium(Holder<ArmorMaterial> material, Type type, Properties properties) {
		super(material, type, properties);
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
		if (level.isClientSide || !(entity instanceof Player player)) return;

		if (player.getItemBySlot(EquipmentSlot.HEAD) == stack) {
			player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 20, 0, true, false));
			player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 20, 0, true, false));
		}
		if (player.getItemBySlot(EquipmentSlot.CHEST) == stack) {
			player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 20, 0, true, false));
		}
		if (player.getItemBySlot(EquipmentSlot.LEGS) == stack) {
			player.addEffect(new MobEffectInstance(MobEffects.JUMP, 20, 2, true, false));
		}
		if (player.getItemBySlot(EquipmentSlot.FEET) == stack) {
			player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20, 2, true, false));
		}
	}
}
