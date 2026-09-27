package com.hbm.items.tool;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ItemBase;
import com.hbm.items.ModItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;

import java.util.List;

public class ItemCanister extends ItemBase {

	public static final String KEY_FLUID = "hbm_fluid";
	public int capacity;

	public ItemCanister(int capacity, Properties properties) {
		super(properties.stacksTo(16));
		this.capacity = capacity;
	}

	public ItemCanister(int capacity) {
		super(new Properties().stacksTo(16));
		this.capacity = capacity;
	}

	public static ItemStack createWithFluid(ItemStack baseStack, FluidType type) {
		ItemStack stack = baseStack.copy();
		CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putString(KEY_FLUID, type.getName()));
		return stack;
	}

	public static FluidType getFluidType(ItemStack stack) {
		CustomData data = stack.get(DataComponents.CUSTOM_DATA);
		if (data != null && data.contains(KEY_FLUID)) {
			return Fluids.fromName(data.copyTag().getString(KEY_FLUID));
		}
		return Fluids.NONE;
	}

	@Override
	public Component getName(ItemStack stack) {
		FluidType fluid = getFluidType(stack);
		if (fluid != Fluids.NONE) {
			return Component.translatable("item.canister_full.name").append(" ").append(Component.translatable(fluid.getConditionalName()));
		}
		return Component.translatable("item.canister_empty.name");
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
		FluidType fluid = getFluidType(stack);
		if (fluid != Fluids.NONE) {
			tooltip.add(Component.literal("§7" + capacity + "/" + capacity + " mB " + fluid.getConditionalName()));
		}
	}
}
