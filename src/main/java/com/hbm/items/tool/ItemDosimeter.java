package com.hbm.items.tool;

import com.hbm.items.ItemBase;
import com.hbm.util.ContaminationUtil;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemDosimeter extends ItemBase {

    public ItemDosimeter() {
        super(new Properties().stacksTo(1));
    }

    public ItemDosimeter(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int itemSlot, boolean isSelected) {
        if (!(entity instanceof LivingEntity) || world.isClientSide()) return;

        if (entity instanceof Player player) {
            ItemGeigerCounter.playGeiger(world, player);
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!world.isClientSide()) {
            world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.UI_BUTTON_CLICK.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
            ContaminationUtil.printDosimeterData(player);
        }
        return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
    }
}
