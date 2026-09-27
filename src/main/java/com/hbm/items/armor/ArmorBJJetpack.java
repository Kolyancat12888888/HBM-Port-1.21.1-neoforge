package com.hbm.items.armor;

import com.hbm.util.BobMathUtil;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class ArmorBJJetpack extends ArmorBJ {

	public ArmorBJJetpack(Holder<ArmorMaterial> material, Type type, Properties properties, long maxPower, long chargeRate, long consumption, long drain) {
		super(material, type, properties, maxPower, chargeRate, consumption, drain);
	}

	public static void handleJetpackTick(Player player, ItemStack stack) {
		if (ArmorFSB.hasFSBArmor(player)) {
			if (player.isSprinting() || player.isShiftKeyDown()) {
				if (player.getDeltaMovement().y < 0.4D) {
					player.setDeltaMovement(player.getDeltaMovement().x, Math.min(0.4D, player.getDeltaMovement().y + 0.1D), player.getDeltaMovement().z);
				}
				player.resetFallDistance();
			} else if (player.isShiftKeyDown() && player.getDeltaMovement().y < -0.08) {
				double mo = player.getDeltaMovement().y * -0.4;
				Vec3 look = player.getLookAngle().scale(mo);
				player.setDeltaMovement(player.getDeltaMovement().add(look));
			}
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		super.appendHoverText(stack, context, tooltip, flag);
		tooltip.add(Component.literal("§c  + Integrated Electric Jetpack"));
		tooltip.add(Component.literal("§7  + Glider Thrusters"));
	}
}
