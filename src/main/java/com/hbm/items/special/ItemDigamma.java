package com.hbm.items.special;

import com.hbm.items.ItemBase;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.I18nUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class ItemDigamma extends ItemBase {

	protected final int digamma;

	public ItemDigamma(Properties properties, int digamma) {
		super(properties);
		this.digamma = digamma;
	}

	public ItemDigamma(int digamma) {
		this(new Properties(), digamma);
	}

	@Override
	public void inventoryTick(ItemStack stack, Level worldIn, Entity entity, int itemSlot, boolean isSelected) {
		super.inventoryTick(stack, worldIn, entity, itemSlot, isSelected);
		if (entity instanceof Player player) {
			ContaminationUtil.applyDigammaData(player, 1.0F / (float) digamma);
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
		tooltipComponents.add(Component.literal(ChatFormatting.GOLD + I18nUtil.resolveKey("trait.hlParticle", "1.67*10³⁴ a")));
		tooltipComponents.add(Component.literal(ChatFormatting.RED + I18nUtil.resolveKey("trait.hlPlayer", (digamma / 20.0) + "s")));
		tooltipComponents.add(Component.literal(""));
		tooltipComponents.add(Component.literal(ChatFormatting.RED + "[" + I18nUtil.resolveKey("trait.drop") + "]"));
	}

	@Override
	public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entityItem) {
		if (entityItem != null && entityItem.onGround() && !entityItem.level().isClientSide()) {
			entityItem.level().explode(null, entityItem.getX(), entityItem.getY(), entityItem.getZ(), 15.0F, Level.ExplosionInteraction.BLOCK);
			entityItem.discard();
			return true;
		}
		return false;
	}
}
