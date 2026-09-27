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

import java.util.ArrayList;
import java.util.List;

public class ItemDosimeter extends ItemBase {

	public ItemDosimeter(Properties properties) {
		super(properties.stacksTo(1));
	}

	public ItemDosimeter() {
		super(new Properties().stacksTo(1));
	}

	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity entity, int itemSlot, boolean isSelected) {
		if (!(entity instanceof LivingEntity) || world.isClientSide())
			return;

		if (entity instanceof Player player) {
			double x = ContaminationUtil.getActualPlayerRads(player);

			if (world.getGameTime() % 5 == 0) {
				if (x > 1e-5) {
					List<Integer> list = new ArrayList<>();
					if (x < 0.5) list.add(0);
					if (x < 1) list.add(1);
					if (x >= 0.5 && x < 2) list.add(2);
					if (x >= 1 && x >= 2) list.add(3);

					int r = list.get(world.random.nextInt(list.size()));
					if (r > 0) {
						world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0F, 0.7F + world.random.nextFloat() * 0.4F);
					}
				} else if (world.random.nextInt(100) == 0) {
					world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 0.5F, 1.2F);
				}
			}
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
