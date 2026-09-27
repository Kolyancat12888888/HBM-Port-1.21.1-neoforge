package com.hbm.items.special;

import com.hbm.items.ModItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemContainerContents;

import java.util.Arrays;
import java.util.List;

public class ItemKitCustom extends ItemKitNBT {

    public ItemKitCustom(Properties properties) {
        super(properties);
    }

    public ItemKitCustom() {
        super();
    }

    public static ItemStack create(String name, String lore, int color1, int color2, ItemStack... contents) {
        ItemStack stack = new ItemStack(ModItems.kit_custom);

        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> {
            tag.putInt("color1", color1);
            tag.putInt("color2", color2);
        });

        stack.set(DataComponents.CUSTOM_NAME, Component.literal(name));
        stack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(Arrays.asList(contents)));

        return stack;
    }

    public static void setColor(ItemStack stack, int color, int index) {
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putInt("color" + index, color));
    }

    public static int getColor(ItemStack stack, int index) {
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        if (data == null || !data.contains("color" + index)) return 0;
        return data.copyTag().getInt("color" + index);
    }
}
