package com.hbm.items.armor;

import com.hbm.handler.ArmorUtil;
import com.hbm.items.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.List;

@EventBusSubscriber(modid = com.hbm.main.MainRegistry.MODID)
public class ArmorDNT extends ArmorFSBPowered {

	public ArmorDNT(Holder<ArmorMaterial> material, Type type, Properties properties, long maxPower, long chargeRate, long consumption, long drain) {
		super(material, type, properties, maxPower, chargeRate, consumption, drain);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		super.appendHoverText(stack, context, tooltip, flag);
		tooltip.add(Component.literal("§b  + Nanotech Kinetic Shield (Ricochet & Negation)"));
		tooltip.add(Component.literal("§b  + Anti-Grav Thrusters (Jetpack/Sprint Boost)"));
	}

	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		Player player = event.getEntity();
		if (player.level().isClientSide) return;

		ArmorFSB.handlePlayerTick(player);

		if (ArmorFSB.hasFSBArmor(player)) {
			ItemStack plate = player.getItemBySlot(EquipmentSlot.CHEST);
			if (plate.getItem() instanceof ArmorDNT) {
				if (player.isSprinting()) {
					player.setDeltaMovement(player.getDeltaMovement().multiply(1.05, 1.0, 1.05));
				}
			}
		}
	}

	@SubscribeEvent
	public static void onIncomingDamage(LivingIncomingDamageEvent event) {
		if (event.getEntity() instanceof Player player && ArmorFSB.hasFSBArmor(player)) {
			ItemStack plate = player.getItemBySlot(EquipmentSlot.CHEST);
			if (plate.getItem() instanceof ArmorDNT) {
				if (!event.getSource().is(net.minecraft.tags.DamageTypeTags.BYPASSES_INVULNERABILITY)) {
					if (event.getSource().is(net.minecraft.tags.DamageTypeTags.IS_EXPLOSION)) {
						event.setAmount(event.getAmount() * 0.001F);
					} else {
						event.setCanceled(true);
					}
				}
			} else if (plate.getItem() instanceof ArmorTrenchmaster) {
				if (player.getRandom().nextInt(3) == 0) {
					event.setCanceled(true);
				}
			}
		}
	}
}
