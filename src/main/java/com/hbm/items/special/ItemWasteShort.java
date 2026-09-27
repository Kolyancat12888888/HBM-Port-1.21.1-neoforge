package com.hbm.items.special;

import com.hbm.items.ItemBase;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ItemWasteShort extends ItemBase {

	protected final WasteClass wasteClass;

	public ItemWasteShort(Properties properties, WasteClass wasteClass) {
		super(properties);
		this.wasteClass = wasteClass;
	}

	public ItemWasteShort(WasteClass wasteClass) {
		this(new Properties(), wasteClass);
	}

	public WasteClass getWasteClass() {
		return wasteClass;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
		tooltipComponents.add(Component.literal(ChatFormatting.ITALIC + wasteClass.name));
	}

	public enum WasteClass {
		URANIUM233("Uranium-233", 50, 100),
		URANIUM235("Uranium-235", 0, 100),
		NEPTUNIUM("Neptunium-237", 150, 500),
		PLUTONIUM239("Plutonium-239", 250, 1000),
		PLUTONIUM240("Plutonium-240", 350, 1000),
		PLUTONIUM241("Plutonium-241", 500, 1000),
		AMERICIUM242("Americium-242", 750, 1000),
		SCHRABIDIUM("Schrabidium-326", 1000, 1000);

		public static final WasteClass[] VALUES = values();

		public final String name;
		public final int liquid;
		public final int gas;

		WasteClass(String name, int liquid, int gas){
			this.name = name;
			this.liquid = liquid;
			this.gas = gas;
		}
	}
}
