package com.hbm.items.machine;

import com.hbm.items.ItemBakedBase;
import com.hbm.items.ModItems;
import com.hbm.util.I18nUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ItemMachineUpgrade extends ItemBakedBase {
	public UpgradeType type;
	public int tier;

	public ItemMachineUpgrade(Properties properties, String s, UpgradeType type, int tier) {
		super(properties, s);
		this.type = type;
		this.tier = tier;
	}

	public ItemMachineUpgrade(String s, UpgradeType type, int tier) {
		this(new Properties(), s, type, tier);
	}

	public ItemMachineUpgrade(String s, UpgradeType type) {
		this(s, type, 0);
	}

	public ItemMachineUpgrade(String s) {
		this(s, UpgradeType.SPECIAL, 0);
	}

	public int getSpeed() {
		if (this == ModItems.upgrade_speed_1) return 1;
		if (this == ModItems.upgrade_speed_2) return 2;
		if (this == ModItems.upgrade_speed_3) return 3;
		if (this == ModItems.upgrade_overdrive_1) return 4;
		if (this == ModItems.upgrade_overdrive_2) return 6;
		if (this == ModItems.upgrade_overdrive_3) return 8;
		if (this == ModItems.upgrade_screm) return 10;
		return 0;
	}

	public static int getSpeed(ItemStack stack) {
		if (stack == null || stack.isEmpty()) return 0;
		Item upgrade = stack.getItem();
		if (upgrade == ModItems.upgrade_speed_1) return 1;
		if (upgrade == ModItems.upgrade_speed_2) return 2;
		if (upgrade == ModItems.upgrade_speed_3) return 3;
		if (upgrade == ModItems.upgrade_overdrive_1) return 4;
		if (upgrade == ModItems.upgrade_overdrive_2) return 6;
		if (upgrade == ModItems.upgrade_overdrive_3) return 8;
		if (upgrade == ModItems.upgrade_screm) return 10;
		return 0;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> list, TooltipFlag flagIn) {
		if (this == ModItems.upgrade_radius) {
			list.add(Component.literal(ChatFormatting.GOLD + I18nUtil.resolveKey("desc.upgrade7")));
			list.add(Component.literal(" " + I18nUtil.resolveKey("desc.upgraderd")));
			list.add(Component.literal(""));
			list.add(Component.literal(" " + I18nUtil.resolveKey("desc.upgradestack")));
		}

		if (this == ModItems.upgrade_health) {
			list.add(Component.literal(ChatFormatting.GOLD + I18nUtil.resolveKey("desc.upgrade8")));
			list.add(Component.literal(" " + I18nUtil.resolveKey("desc.upgradeht")));
			list.add(Component.literal(""));
			list.add(Component.literal(" " + I18nUtil.resolveKey("desc.upgradestack")));
		}

		if (this == ModItems.upgrade_smelter) {
			list.add(Component.literal(ChatFormatting.GOLD + I18nUtil.resolveKey("desc.upgrade9")));
			list.add(Component.literal(" " + I18nUtil.resolveKey("desc.upgrade12")));
		}

		if (this == ModItems.upgrade_shredder) {
			list.add(Component.literal(ChatFormatting.GOLD + I18nUtil.resolveKey("desc.upgrade9")));
			list.add(Component.literal(" " + I18nUtil.resolveKey("desc.upgrade13")));
		}

		if (this == ModItems.upgrade_centrifuge) {
			list.add(Component.literal(ChatFormatting.GOLD + I18nUtil.resolveKey("desc.upgrade9")));
			list.add(Component.literal(" " + I18nUtil.resolveKey("desc.upgrade21")));
		}

		if (this == ModItems.upgrade_crystallizer) {
			list.add(Component.literal(ChatFormatting.GOLD + I18nUtil.resolveKey("desc.upgrade9")));
			list.add(Component.literal(" " + I18nUtil.resolveKey("desc.upgrade14")));
		}

		if (this == ModItems.upgrade_screm) {
			list.add(Component.literal(ChatFormatting.GOLD + I18nUtil.resolveKey("desc.upgrade9")));
			list.add(Component.literal(" " + I18nUtil.resolveKey("desc.upgrade15")));
			list.add(Component.literal(" " + I18nUtil.resolveKey("desc.upgrade16")));
			list.add(Component.literal(" " + I18nUtil.resolveKey("desc.upgrade17")));
		}

		if (this == ModItems.upgrade_nullifier) {
			list.add(Component.literal(ChatFormatting.GOLD + I18nUtil.resolveKey("desc.upgrade10")));
			list.add(Component.literal(" " + I18nUtil.resolveKey("desc.upgrade19")));
		}

		if (this == ModItems.upgrade_gc_speed) {
			list.add(Component.literal(ChatFormatting.RED + "Gas Centrifuge Upgrade"));
			list.add(Component.literal("Allows for total isotopic separation of HEUF6"));
			list.add(Component.literal(ChatFormatting.YELLOW + "also your centrifuge goes sicko mode"));
		}
	}

	public enum UpgradeType {
		SPEED,
		EFFECT,
		POWER,
		FORTUNE,
		AFTERBURN,
		OVERDRIVE,
		NULLIFIER,
		SCREAM,
		SPECIAL
	}
}
