package com.hbm.items.special;

import com.hbm.items.machine.ItemBattery;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;

import java.util.Random;

public class ItemPotatos extends ItemBattery {

	private static final Random rand = new Random();

	public ItemPotatos(Properties properties, long dura, long chargeRate, long dischargeRate) {
		super(properties, dura, chargeRate, dischargeRate);
	}

	public ItemPotatos(long dura, long chargeRate, long dischargeRate) {
		this(new Properties(), dura, chargeRate, dischargeRate);
	}

	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity entity, int itemSlot, boolean isSelected) {
		if (getCharge(stack) == 0) return;

		int timer = getTimer(stack);
		if (timer > 0) {
			setTimer(stack, timer - 1);
		} else {
			if (entity instanceof Player p) {
				if (p.getMainHandItem() == stack || p.getOffhandItem() == stack) {
					setTimer(stack, 200 + rand.nextInt(100));
				}
			}
		}
	}

	private static int getTimer(ItemStack stack) {
		CustomData data = stack.get(DataComponents.CUSTOM_DATA);
		if (data != null && data.contains("timer")) {
			return data.copyTag().getInt("timer");
		}
		return 0;
	}

	private static void setTimer(ItemStack stack, int i) {
		CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putInt("timer", i));
	}
}
