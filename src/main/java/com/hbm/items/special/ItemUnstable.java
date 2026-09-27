package com.hbm.items.special;

import com.hbm.items.ItemBakedBase;
import com.hbm.util.I18nUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;

import java.util.List;

public class ItemUnstable extends ItemBakedBase {

	protected final int radius;
	protected final int timer;

	public ItemUnstable(Properties properties, int radius, int timer, String s) {
		super(properties, s);
		this.radius = radius;
		this.timer = timer;
	}

	public ItemUnstable(int radius, int timer, String s) {
		this(new Properties(), radius, timer, s);
	}

	private int scaledRadiusForCount(int count) {
		if (count <= 1) return radius;
		return (int) Math.max(1, Math.round(radius * Math.cbrt(count)));
	}

	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity entity, int itemSlot, boolean isSelected) {
		int t = getTimer(stack) + 1;
		setTimer(stack, t);

		if (t >= timer && !world.isClientSide()) {
			int count = stack.getCount();
			int r = scaledRadiusForCount(count);
			world.explode(null, entity.getX(), entity.getY(), entity.getZ(), (float) r, Level.ExplosionInteraction.BLOCK);
			entity.hurt(world.damageSources().genericKill(), 10000);
			stack.shrink(count);
		}
	}

	@Override
	public boolean onEntityItemUpdate(ItemStack stack, ItemEntity itemEntity) {
		Level world = itemEntity.level();
		int t = getTimer(stack) + 1;
		setTimer(stack, t);

		if (t >= timer && !world.isClientSide()) {
			int count = stack.getCount();
			int r = scaledRadiusForCount(count);
			world.explode(null, itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(), (float) r, Level.ExplosionInteraction.BLOCK);
			itemEntity.discard();
			return true;
		}
		return false;
	}

	private void setTimer(ItemStack stack, int time) {
		CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putInt("timer", time));
	}

	private int getTimer(ItemStack stack) {
		CustomData data = stack.get(DataComponents.CUSTOM_DATA);
		if (data != null && data.contains("timer")) {
			return data.copyTag().getInt("timer");
		}
		return 0;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
		tooltipComponents.add(Component.literal(ChatFormatting.DARK_RED + I18nUtil.resolveKey("trait.unstable") + ChatFormatting.RESET));
		tooltipComponents.add(Component.literal(ChatFormatting.RED + "Decay Time: " + (timer / 20) + "s - Explosion Radius: " + scaledRadiusForCount(stack.getCount()) + "m" + ChatFormatting.RESET));
		tooltipComponents.add(Component.literal(ChatFormatting.RED + "Decay: " + (getTimer(stack) * 100 / (timer > 0 ? timer : 1)) + "%" + ChatFormatting.RESET));
	}
}
