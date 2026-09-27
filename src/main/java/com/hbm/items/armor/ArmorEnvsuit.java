package com.hbm.items.armor;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class ArmorEnvsuit extends ArmorFSBPowered {

	public ArmorEnvsuit(Holder<ArmorMaterial> material, Type type, Properties properties, long maxPower, long chargeRate, long consumption, long drain) {
		super(material, type, properties, maxPower, chargeRate, consumption, drain);
	}

	public static void handleWaterMovement(Player player) {
		if (ArmorFSB.hasFSBArmor(player)) {
			ItemStack plate = player.getItemBySlot(EquipmentSlot.CHEST);
			if (plate.getItem() instanceof ArmorEnvsuit) {
				if (player.isInWater()) {
					player.setAirSupply(300);
					player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 300, 0, true, false));
					Vec3 look = player.getLookAngle().scale(0.05);
					player.setDeltaMovement(player.getDeltaMovement().add(look));
				}
			}
		}
	}
}
