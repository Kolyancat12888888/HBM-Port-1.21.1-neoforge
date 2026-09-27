package com.hbm.items.machine;

import com.hbm.items.ItemBase;
import com.hbm.items.ModItems;
import com.hbm.util.I18nUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;

import java.util.List;

public class ItemRBMKPellet extends ItemBase {

    public String fullName = "";

    public ItemRBMKPellet(String fullName) {
        super(new Properties().stacksTo(64));
        this.fullName = fullName;
    }

    public static int getDepletion(ItemStack stack) {
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        if (data != null && data.contains("depletion")) {
            return data.copyTag().getInt("depletion");
        }
        return 0;
    }

    public static void setDepletion(ItemStack stack, int val) {
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putInt("depletion", rectify(val)));
    }

    public static boolean hasXenon(ItemStack stack) {
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        if (data != null && data.contains("xenon")) {
            return data.copyTag().getBoolean("xenon");
        }
        return false;
    }

    public static void setXenon(ItemStack stack, boolean val) {
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putBoolean("xenon", val));
    }

    public static boolean hasXenon(int meta) {
        return rectify(meta) >= 5;
    }

    public static int rectify(int meta) {
        return Math.abs(meta) % 10;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, context, tooltip, flagIn);

        tooltip.add(Component.literal(ChatFormatting.ITALIC + this.fullName));
        tooltip.add(Component.literal(ChatFormatting.DARK_GRAY.toString() + ChatFormatting.ITALIC + "Pellet for recycling"));

        int meta = getDepletion(stack);

        switch (meta % 5) {
            case 0 -> tooltip.add(Component.literal(ChatFormatting.GOLD + "Brand New"));
            case 1 -> tooltip.add(Component.literal(ChatFormatting.YELLOW + "Barely Depleted"));
            case 2 -> tooltip.add(Component.literal(ChatFormatting.GREEN + "Moderately Depleted"));
            case 3 -> tooltip.add(Component.literal(ChatFormatting.DARK_GREEN + "Highly Depleted"));
            case 4 -> tooltip.add(Component.literal(ChatFormatting.DARK_GRAY + "Fully Depleted"));
        }

        if (hasXenon(stack) || hasXenon(meta)) {
            tooltip.add(Component.literal(ChatFormatting.DARK_PURPLE + "High Xenon Poison"));
        }
    }
}
