package com.hbm.items.special;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ItemBase;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemCell extends ItemBase {

    public ItemCell(Properties properties) {
        super(properties);
    }

    public ItemCell() {
        super(new Properties());
    }

    public static boolean isEmptyCell(ItemStack stack) {
        if (stack.isEmpty()) return false;
        FluidType type = getFluidType(stack);
        return type == null || type == Fluids.NONE;
    }

    @Nullable
    public static FluidType getFluidType(ItemStack stack) {
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        if (data != null && data.contains("fluid")) {
            String name = data.copyTag().getString("fluid");
            return Fluids.fromName(name);
        }
        return Fluids.NONE;
    }

    public static ItemStack getFullCell(FluidType fluid, int amount) {
        ItemStack stack = new ItemStack(net.minecraft.core.registries.BuiltInRegistries.ITEM.get(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("hbm", "cell")), amount);
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putString("fluid", fluid.getName()));
        return stack;
    }

    public static ItemStack getFullCell(FluidType fluid) {
        return getFullCell(fluid, 1);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entityItem) {
        if (entityItem.onGround() || entityItem.isOnFire() || entityItem.isInLava()) {
            FluidType type = getFluidType(stack);
            if (type == Fluids.AMAT) {
                Level world = entityItem.level();
                if (!world.isClientSide()) {
                    world.explode(null, entityItem.getX(), entityItem.getY(), entityItem.getZ(), 10.0F, Level.ExplosionInteraction.BLOCK);
                    entityItem.discard();
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        FluidType type = getFluidType(stack);
        if (type == Fluids.AMAT) {
            tooltipComponents.add(Component.literal(ChatFormatting.YELLOW + "Exposure to matter will lead to violent annihilation!" + ChatFormatting.RESET));
            tooltipComponents.add(Component.literal(ChatFormatting.RED + "[Dangerous Drop]" + ChatFormatting.RESET));
        } else if (type == Fluids.ASCHRAB) {
            tooltipComponents.add(Component.literal(ChatFormatting.YELLOW + "Exposure to matter will create a fólkvangr field!" + ChatFormatting.RESET));
            tooltipComponents.add(Component.literal(ChatFormatting.RED + "[Dangerous Drop]" + ChatFormatting.RESET));
        }
    }
}
