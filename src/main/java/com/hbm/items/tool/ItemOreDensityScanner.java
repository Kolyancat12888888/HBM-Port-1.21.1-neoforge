package com.hbm.items.tool;

import com.hbm.items.ItemBase;
import com.hbm.items.special.ItemBedrockOreBase;
import com.hbm.items.special.ItemBedrockOreNew;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemOreDensityScanner extends ItemBase {

	public ItemOreDensityScanner(Properties properties) {
		super(properties.stacksTo(1));
	}

	public ItemOreDensityScanner() {
		super(new Properties().stacksTo(1));
	}

	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean isSelected) {
		if (!(entity instanceof ServerPlayer player) || world.getGameTime() % 20 != 0 || !isSelected) return;

		double totalLevel = 0D;
		for (ItemBedrockOreNew.BedrockOreType type : ItemBedrockOreNew.BedrockOreType.VALUES) {
			double level = ItemBedrockOreBase.getOreLevel((int) Math.floor(player.getX()), (int) Math.floor(player.getZ()), type);
			totalLevel += level;
		}

		totalLevel /= ItemBedrockOreNew.BedrockOreType.VALUES.length;
		String densityKey = translateDensity(totalLevel);
		ChatFormatting color = getColor(totalLevel);

		player.sendSystemMessage(Component.literal("§e[Ore Density] ")
				.append(Component.translatable(densityKey).setStyle(Style.EMPTY.withColor(color)))
				.append(Component.literal(String.format(" (%.2f)", totalLevel))), true);
	}

	public static String translateDensity(double density) {
		if (density <= 0.1) return "item.ore_density_scanner.verypoor";
		if (density <= 0.35) return "item.ore_density_scanner.poor";
		if (density <= 0.75) return "item.ore_density_scanner.low";
		if (density >= 1.9) return "item.ore_density_scanner.excellent";
		if (density >= 1.65) return "item.ore_density_scanner.veryhigh";
		if (density >= 1.25) return "item.ore_density_scanner.high";
		return "item.ore_density_scanner.moderate";
	}

	public static ChatFormatting getColor(double density) {
		if (density <= 0.1) return ChatFormatting.DARK_RED;
		if (density <= 0.35) return ChatFormatting.RED;
		if (density <= 0.75) return ChatFormatting.GOLD;
		if (density >= 1.9) return ChatFormatting.AQUA;
		if (density >= 1.65) return ChatFormatting.BLUE;
		if (density >= 1.25) return ChatFormatting.GREEN;
		return ChatFormatting.YELLOW;
	}
}
