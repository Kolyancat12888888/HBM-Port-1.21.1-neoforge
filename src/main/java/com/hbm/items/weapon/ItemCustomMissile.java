package com.hbm.items.weapon;

import com.hbm.handler.MissileStruct;
import com.hbm.items.ItemBase;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemMissile.FuelType;
import com.hbm.items.weapon.ItemMissile.WarheadType;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;

import java.util.List;

public class ItemCustomMissile extends ItemBase {

	public ItemCustomMissile(Properties properties) {
		super(properties.stacksTo(1));
	}

	public ItemCustomMissile() {
		this(new Properties());
	}

	public static ItemStack buildMissile(Item chip, Item warhead, Item fuselage, Item stability, Item thruster) {
		if (stability == null) {
			return buildMissile(new ItemStack(chip), new ItemStack(warhead), new ItemStack(fuselage), null, new ItemStack(thruster));
		} else {
			return buildMissile(new ItemStack(chip), new ItemStack(warhead), new ItemStack(fuselage), new ItemStack(stability), new ItemStack(thruster));
		}
	}

	public static ItemStack buildMissile(ItemStack chip, ItemStack warhead, ItemStack fuselage, ItemStack stability, ItemStack thruster) {
		ItemStack missile = new ItemStack(ModItems.missile_custom);

		writeToNBT(missile, "chip", BuiltInRegistries.ITEM.getKey(chip.getItem()).toString());
		writeToNBT(missile, "warhead", BuiltInRegistries.ITEM.getKey(warhead.getItem()).toString());
		writeToNBT(missile, "fuselage", BuiltInRegistries.ITEM.getKey(fuselage.getItem()).toString());
		writeToNBT(missile, "thruster", BuiltInRegistries.ITEM.getKey(thruster.getItem()).toString());

		if (stability != null)
			writeToNBT(missile, "stability", BuiltInRegistries.ITEM.getKey(stability.getItem()).toString());

		return missile;
	}

	private static void writeToNBT(ItemStack stack, String key, String value) {
		CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putString(key, value));
	}

	public static String readFromNBT(ItemStack stack, String key) {
		CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
		return customData.copyTag().getString(key);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
		try {
			String chipKey = readFromNBT(stack, "chip");
			String warheadKey = readFromNBT(stack, "warhead");
			String fuselageKey = readFromNBT(stack, "fuselage");
			String stabilityKey = readFromNBT(stack, "stability");
			String thrusterKey = readFromNBT(stack, "thruster");

			if (chipKey.isEmpty() || warheadKey.isEmpty() || fuselageKey.isEmpty() || thrusterKey.isEmpty()) return;

			ItemMissile chip = (ItemMissile) BuiltInRegistries.ITEM.get(ResourceLocation.parse(chipKey));
			ItemMissile warhead = (ItemMissile) BuiltInRegistries.ITEM.get(ResourceLocation.parse(warheadKey));
			ItemMissile fuselage = (ItemMissile) BuiltInRegistries.ITEM.get(ResourceLocation.parse(fuselageKey));
			ItemMissile stability = !stabilityKey.isEmpty() && BuiltInRegistries.ITEM.get(ResourceLocation.parse(stabilityKey)) instanceof ItemMissile m ? m : null;
			ItemMissile thruster = (ItemMissile) BuiltInRegistries.ITEM.get(ResourceLocation.parse(thrusterKey));

			if (warhead != null && warhead.attributes != null) {
				tooltip.add(Component.literal(ChatFormatting.BOLD + "Warhead: " + ChatFormatting.GRAY + warhead.getWarhead((WarheadType) warhead.attributes[0])));
				tooltip.add(Component.literal(ChatFormatting.BOLD + "Strength: " + ChatFormatting.RED + warhead.attributes[1]));
			}
			if (fuselage != null && fuselage.attributes != null) {
				tooltip.add(Component.literal(ChatFormatting.BOLD + "Fuel Type: " + ChatFormatting.GRAY + fuselage.getFuel((FuelType) fuselage.attributes[0])));
				tooltip.add(Component.literal(ChatFormatting.BOLD + "Fuel amount: " + ChatFormatting.GRAY + fuselage.attributes[1] + "l"));
				tooltip.add(Component.literal(ChatFormatting.BOLD + "Size: " + ChatFormatting.GRAY + fuselage.getSize(fuselage.top) + "/" + fuselage.getSize(fuselage.bottom)));
			}
			if (chip != null && chip.attributes != null) {
				tooltip.add(Component.literal(ChatFormatting.BOLD + "Chip inaccuracy: " + ChatFormatting.GRAY + ((Float) chip.attributes[0] * 100) + "%"));
			}

			if (stability != null && stability.attributes != null)
				tooltip.add(Component.literal(ChatFormatting.BOLD + "Fin inaccuracy: " + ChatFormatting.GRAY + ((Float) stability.attributes[0] * 100) + "%"));
			else
				tooltip.add(Component.literal(ChatFormatting.BOLD + "Fin inaccuracy: " + ChatFormatting.GRAY + "100%"));

			float health = (warhead != null ? warhead.health : 0) + (fuselage != null ? fuselage.health : 0) + (thruster != null ? thruster.health : 0);
			if (stability != null)
				health += stability.health;

			tooltip.add(Component.literal(ChatFormatting.BOLD + "Health: " + ChatFormatting.GREEN + health + "HP"));
		} catch (Exception x) {
			// Catch formatting errors
		}
	}

	public static MissileStruct getStruct(ItemStack stack) {
		if (stack == null || stack.isEmpty() || !(stack.getItem() instanceof ItemCustomMissile))
			return null;
		try {
			String warheadKey = readFromNBT(stack, "warhead");
			String fuselageKey = readFromNBT(stack, "fuselage");
			String stabilityKey = readFromNBT(stack, "stability");
			String thrusterKey = readFromNBT(stack, "thruster");

			if (warheadKey.isEmpty() || fuselageKey.isEmpty() || thrusterKey.isEmpty()) return null;

			ItemMissile warhead = (ItemMissile) BuiltInRegistries.ITEM.get(ResourceLocation.parse(warheadKey));
			ItemMissile fuselage = (ItemMissile) BuiltInRegistries.ITEM.get(ResourceLocation.parse(fuselageKey));
			ItemMissile stability = !stabilityKey.isEmpty() && BuiltInRegistries.ITEM.get(ResourceLocation.parse(stabilityKey)) instanceof ItemMissile m ? m : null;
			ItemMissile thruster = (ItemMissile) BuiltInRegistries.ITEM.get(ResourceLocation.parse(thrusterKey));

			return new MissileStruct(warhead, fuselage, stability, thruster);
		} catch (Exception x) {
			return null;
		}
	}
}
