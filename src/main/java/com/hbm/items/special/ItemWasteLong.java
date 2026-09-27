package com.hbm.items.special;

import com.hbm.items.ItemBase;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ItemWasteLong extends ItemBase {

	protected final WasteClass wasteClass;

	public ItemWasteLong(Properties properties, WasteClass wasteClass) {
		super(properties);
		this.wasteClass = wasteClass;
	}

	public ItemWasteLong(WasteClass wasteClass) {
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
		THORIUM("Thorium-232", 0, 0),
		URANIUM233("Uranium-233", 0, 50),
		URANIUM235("Uranium-235", 0, 0),
		NEPTUNIUM("Neptunium-237", 0, 100),
		SCHRABIDIUM("Schrabidium-326", 0, 250);

		public static final WasteClass[] VALUES = values();

		public final String name;
		public final int liquid;
		public final int gas;

		WasteClass(String name, int liquid, int gas) {
			this.name = name;
			this.liquid = liquid;
			this.gas = gas;
		}
	}
}
