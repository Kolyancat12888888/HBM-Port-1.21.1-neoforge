package com.hbm.items.tool;

import com.hbm.capability.HbmLivingProps;
import com.hbm.items.ItemBase;
import com.hbm.util.ContaminationUtil;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class ItemDigammaDiagnostic extends ItemBase {

	public ItemDigammaDiagnostic(Properties properties) {
		super(properties.stacksTo(1));
	}

	public ItemDigammaDiagnostic() {
		super(new Properties().stacksTo(1));
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (!world.isClientSide()) {
			world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.UI_BUTTON_CLICK.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
			ContaminationUtil.printDiagnosticData(player);
		}
		return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
	}

	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity entity, int itemSlot, boolean isSelected) {
		if (world.isClientSide() || !(entity instanceof Player player)) return;
		playVoices(world, player);
	}

	public static void playVoices(Level world, Player player) {
		double x = HbmLivingProps.getDigamma(player);
		if (x > 0.01 && world.getGameTime() % 10 == 0) {
			int bound = (int) Math.max(1, 20 / x);
			if (world.random.nextInt(bound) == 0) {
				world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PORTAL_AMBIENT, SoundSource.PLAYERS, (float) x * 0.04F + 0.04F, 0.5F + world.random.nextFloat() * 0.5F);
			}
		}
	}
}
