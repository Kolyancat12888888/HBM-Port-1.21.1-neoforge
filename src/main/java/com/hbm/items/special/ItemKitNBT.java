package com.hbm.items.special;

import com.hbm.items.ItemBase;
import com.hbm.items.ModItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemKitNBT extends ItemBase {

    public ItemKitNBT(Properties properties) {
        super(properties.stacksTo(1));
    }

    public ItemKitNBT() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);

        ItemContainerContents contents = stack.get(DataComponents.CONTAINER);
        if (contents != null) {
            for (ItemStack item : contents.stream().toList()) {
                if (!item.isEmpty()) {
                    playerIn.getInventory().add(item.copy());
                }
            }
        }

        stack.shrink(1);
        worldIn.playSound(playerIn, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.BUNDLE_DROP_CONTENTS, SoundSource.PLAYERS, 1.0F, 1.0F);

        return InteractionResultHolder.sidedSuccess(stack, worldIn.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> list, TooltipFlag flagIn) {
        ItemContainerContents contents = stack.get(DataComponents.CONTAINER);
        if (contents != null && contents.stream().findAny().isPresent()) {
            list.add(Component.literal("Contains:"));
            for (ItemStack item : contents.stream().toList()) {
                list.add(Component.literal("-" + item.getHoverName().getString() + (item.getCount() > 1 ? (" x" + item.getCount()) : "")));
            }
        }
    }
}
