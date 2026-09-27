package com.hbm.items.special;

import com.hbm.items.ItemBase;
import com.hbm.items.ModItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemRag extends ItemBase {

	public ItemRag(Properties properties) {
		super(properties);
	}

	public ItemRag() {
		super(new Properties());
	}

	@Override
	public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entityItem) {
		if (!entityItem.getItem().isEmpty() && !entityItem.level().isClientSide()) {
			if (entityItem.isInWater()) {
				ItemStack it = entityItem.getItem();
				if (it.getItem() == ModItems.rag)
					entityItem.setItem(new ItemStack(ModItems.rag_damp, it.getCount()));
				else
					entityItem.setItem(new ItemStack(ModItems.mask_damp, it.getCount()));
				return true;
			}
		}
		return false;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (stack.getItem() == ModItems.rag)
			player.drop(new ItemStack(ModItems.rag_piss, 1), false);
		else
			player.drop(new ItemStack(ModItems.mask_piss, 1), false);
		stack.shrink(1);
		return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), world.isClientSide());
	}
}
