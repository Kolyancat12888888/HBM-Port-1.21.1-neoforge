package com.hbm.items.tool;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ItemBase;
import com.hbm.items.ModItems;
import com.hbm.util.ContaminationUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class ItemGeigerCounter extends ItemBase {

	public ItemGeigerCounter(Properties properties) {
		super(properties.stacksTo(1));
	}

	public ItemGeigerCounter() {
		super(new Properties().stacksTo(1));
	}

	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity entity, int itemSlot, boolean isSelected) {
		if (!(entity instanceof LivingEntity) || world.isClientSide())
			return;

		if (entity instanceof Player player) {
			playGeiger(world, player);
		}
	}

	public static void playGeiger(Level world, Player player) {
		if (world.isClientSide()) return;
		double x = ContaminationUtil.getActualPlayerRads(player);

		if (world.getGameTime() % 5 == 0) {
			if (x > 1e-5) {
				List<Integer> list = new ArrayList<>();
				if (x < 1) list.add(0);
				if (x < 5) list.add(0);
				if (x < 10) list.add(1);
				if (x > 5 && x < 15) list.add(2);
				if (x > 10 && x < 20) list.add(3);
				if (x > 15 && x < 25) list.add(4);
				if (x > 20 && x < 30) list.add(5);
				if (x > 25) list.add(6);
				int r = list.get(world.random.nextInt(list.size()));

				if (r > 0) {
					world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0F, 0.5F + world.random.nextFloat() * 0.5F);
				}
			} else if (world.random.nextInt(100) == 0) {
				world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 0.5F, 1.5F);
			}
		}
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level world = context.getLevel();
		BlockPos pos = context.getClickedPos();
		Player player = context.getPlayer();

		if (world.getBlockState(pos).getBlock() == ModBlocks.block_red_copper && player != null) {
			if (!world.isClientSide()) {
				context.getItemInHand().shrink(1);
				player.getInventory().add(new ItemStack(ModItems.survey_scanner));
			}
			return InteractionResult.SUCCESS;
		}

		return InteractionResult.PASS;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (!world.isClientSide()) {
			world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.UI_BUTTON_CLICK.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
			ContaminationUtil.printGeigerData(player);
		}
		return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
	}
}
