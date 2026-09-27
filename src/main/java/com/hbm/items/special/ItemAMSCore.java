package com.hbm.items.special;

import com.hbm.items.ItemBase;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ItemAMSCore extends ItemBase {

	public final int powerBase;
	public final float heatBase;
	public final float fuelBase;
	
	public ItemAMSCore(Properties properties, int powerBase, float heatBase, float fuelBase) {
		super(properties.stacksTo(1));
		this.powerBase = powerBase;
		this.heatBase = heatBase;
		this.fuelBase = fuelBase;
	}

	public ItemAMSCore(int powerBase, float heatBase, float fuelBase) {
		this(new Properties(), powerBase, heatBase, fuelBase);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> list, TooltipFlag tooltipFlag) {
		if (this == ModItems.ams_core_sing) {
			list.add(Component.literal("A modified undefined state of spacetime"));
			list.add(Component.literal("used to aid in inter-gluon fusion and"));
			list.add(Component.literal("spacetime annihilation. Yes, this destroys"));
			list.add(Component.literal("the universe itself, slowly but steadily,"));
			list.add(Component.literal("but at least you can power your toaster with"));
			list.add(Component.literal("this, so it's all good."));
		}

		if (this == ModItems.ams_core_wormhole) {
			list.add(Component.literal("A cloud of billions of nano-wormholes which"));
			list.add(Component.literal("deliberately fail at tunneling matter from"));
			list.add(Component.literal("another dimension, rather it converts all"));
			list.add(Component.literal("that matter into pure energy. That means"));
			list.add(Component.literal("you're actively contributing to the destruction"));
			list.add(Component.literal("of another dimension, sucking it dry like a"));
			list.add(Component.literal("juicebox."));
			list.add(Component.literal("That dimension probably sucked, anyways. I"));
			list.add(Component.literal("bet it was full of wasps or some crap, man,"));
			list.add(Component.literal("I hate these things."));
		}

		if (this == ModItems.ams_core_eyeofharmony) {
			list.add(Component.literal("A star collapsing in on itself, mere nanoseconds"));
			list.add(Component.literal("away from being turned into a black hole,"));
			list.add(Component.literal("frozen in time. If I didn't know better I"));
			list.add(Component.literal("would say this is some deep space magic"));
			list.add(Component.literal("bullcrap some guy made up to sound intellectual."));
			list.add(Component.literal("Probably Steve from accounting. You still owe me"));
			list.add(Component.literal("ten bucks."));
		}

		if (this == ModItems.ams_core_thingy) {
			if (MainRegistry.polaroidID == 11) {
				list.add(Component.literal("Yeah I'm not even gonna question that one."));
			} else {
				list.add(Component.literal("..."));
				list.add(Component.literal("..."));
				list.add(Component.literal("...am I even holding this right?"));
				list.add(Component.literal("It's a small metal thing. I dunno where it's from"));
				list.add(Component.literal("or what it does, maybe they found it on a"));
				list.add(Component.literal("junkyard and sold it as some kind of antique"));
				list.add(Component.literal("artifact. If it weren't for the fact that I can"));
				list.add(Component.literal("actually stuff this into some great big laser"));
				list.add(Component.literal("reactor thing, I'd probably bring it back to where"));
				list.add(Component.literal("it belongs. In the trash."));
			}
		}

		list.add(Component.literal(ChatFormatting.DARK_AQUA + "[DFC Core]" + ChatFormatting.RESET));
		list.add(Component.literal(ChatFormatting.AQUA + " Power: " + Library.getShortNumber(this.powerBase)));
		list.add(Component.literal(ChatFormatting.AQUA + " Heat: " + (this.heatBase > 1 ? ChatFormatting.RED + "+" : ChatFormatting.GREEN.toString()) + (Math.round(this.heatBase * 1000) * .10 - 100) + "%"));
		list.add(Component.literal(ChatFormatting.AQUA + " Fuel: " + (this.fuelBase > 1 ? ChatFormatting.RED + "+" : ChatFormatting.GREEN.toString()) + (Math.round(this.fuelBase * 1000) * .10 - 100) + "%"));
	}

	public static int getPowerBase(ItemStack stack) {
		if (stack == null || !(stack.getItem() instanceof ItemAMSCore core)) return 0;
		return core.powerBase;
	}

	public static float getHeatBase(ItemStack stack) {
		if (stack == null || !(stack.getItem() instanceof ItemAMSCore core)) return 1F;
		return core.heatBase;
	}

	public static float getFuelBase(ItemStack stack) {
		if (stack == null || !(stack.getItem() instanceof ItemAMSCore core)) return 1F;
		return core.fuelBase;
	}
}
