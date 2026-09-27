package com.hbm.items.special;

import com.hbm.items.ItemBase;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;

import java.util.Random;

public class ItemClayTablet extends ItemBase {

    private static final Random rand = new Random();

    public ItemClayTablet(Properties properties) {
        super(properties.stacksTo(1));
    }

    public ItemClayTablet() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        if (hand == InteractionHand.OFF_HAND) return InteractionResultHolder.fail(player.getOffhandItem());
        ItemStack stack = player.getMainHandItem();

        if (!world.isClientSide()) {
            CustomData data = stack.get(DataComponents.CUSTOM_DATA);
            if (data == null || !data.contains("tabletSeed")) {
                CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putLong("tabletSeed", rand.nextLong()));
            }
        }

        return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
    }
}
