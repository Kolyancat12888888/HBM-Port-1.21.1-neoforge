package com.hbm.items.machine;

import com.hbm.inventory.fluid.FluidStack;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ItemBakedBase;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;

import java.util.List;

public class ItemFluidIcon extends ItemBakedBase {

	public ItemFluidIcon(Properties properties, String texturePath) {
		super(properties.stacksTo(1), texturePath);
	}

	public ItemFluidIcon(String texturePath) {
		this(new Properties(), texturePath);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
		int fill = getQuantity(stack);
		int pressure = getPressure(stack);
		if (fill > 0) tooltipComponents.add(Component.literal(fill + "mB"));
		if (pressure > 0) tooltipComponents.add(Component.literal(ChatFormatting.RED + "" + pressure + "PU"));

		FluidType type = getFluidType(stack);
		if (type != null) {
			type.addInfo(tooltipComponents);
		}
	}

	public static ItemStack addQuantity(ItemStack stack, int i) {
		if (i <= 0) return stack;
		CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putInt("fill", i));
		return stack;
	}

	public static ItemStack addPressure(ItemStack stack, int i) {
		if (i <= 0) return stack;
		CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putInt("pressure", i));
		return stack;
	}

	public static ItemStack make(FluidStack stack) {
		if (stack == null) return ItemStack.EMPTY;
		return make(stack.type, stack.fill, stack.pressure);
	}

	public static ItemStack make(FluidType fluid, int i) {
		return make(fluid, i, 0);
	}

	public static ItemStack make(FluidType fluid, int i, int pressure) {
		ItemStack stack = new ItemStack(net.minecraft.core.registries.BuiltInRegistries.ITEM.get(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("hbm", "fluid_icon")));
		if (stack.isEmpty()) return stack;
		CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> {
			tag.putString("fluid", fluid.getName());
			tag.putInt("fill", i);
			tag.putInt("pressure", pressure);
		});
		return stack;
	}

	public static int getQuantity(ItemStack stack) {
		CustomData data = stack.get(DataComponents.CUSTOM_DATA);
		if (data == null || !data.contains("fill")) return 0;
		return data.copyTag().getInt("fill");
	}

	public static int getPressure(ItemStack stack) {
		CustomData data = stack.get(DataComponents.CUSTOM_DATA);
		if (data == null || !data.contains("pressure")) return 0;
		return data.copyTag().getInt("pressure");
	}

	public static FluidType getFluidType(ItemStack stack) {
		CustomData data = stack.get(DataComponents.CUSTOM_DATA);
		if (data != null && data.contains("fluid")) {
			String name = data.copyTag().getString("fluid");
			return Fluids.fromName(name);
		}
		return Fluids.NONE;
	}
}
