package com.hbm.items.tool;

import com.hbm.api.item.IGasMask;
import com.hbm.items.ItemBase;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemFilter extends ItemBase {

	public ItemFilter(int durability, Properties properties) {
		super(properties.durability(durability));
	}

	public ItemFilter(int durability) {
		super(new Properties().durability(durability));
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
		ItemStack stack = player.getItemInHand(hand);

		if (helmet.isEmpty() || !(helmet.getItem() instanceof IGasMask mask)) {
			return InteractionResultHolder.pass(stack);
		}

		if (!mask.isFilterApplicable(helmet, stack)) {
			return InteractionResultHolder.pass(stack);
		}

		ItemStack copy = stack.copyWithCount(1);
		ItemStack current = mask.getFilter(helmet);

		stack.shrink(1);
		if (stack.isEmpty()) {
			stack = current;
		} else if (!current.isEmpty()) {
			if (!player.getInventory().add(current)) {
				player.drop(current, true, false);
			}
		}

		mask.installFilter(helmet, copy);
		world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARMOR_EQUIP_GENERIC.value(), SoundSource.PLAYERS, 1.0F, 1.0F);

		return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
	}
}
