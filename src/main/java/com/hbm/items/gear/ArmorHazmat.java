package com.hbm.items.gear;

import com.hbm.api.item.IGasMask;
import com.hbm.handler.ArmorUtil;
import com.hbm.util.ArmorRegistry.HazardClass;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.Collections;
import java.util.List;

public class ArmorHazmat extends ArmorItem implements IGasMask {

	public ArmorHazmat(Holder<ArmorMaterial> material, Type type, Properties properties) {
		super(material, type, properties);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		super.appendHoverText(stack, context, tooltip, flag);
		if (this.getType() == Type.HELMET) {
			ItemStack filter = getFilter(stack);
			if (!filter.isEmpty()) {
				tooltip.add(Component.literal("§aInstalled Filter: §f" + filter.getHoverName().getString() + " §7(" + (filter.getMaxDamage() - filter.getDamageValue()) + "/" + filter.getMaxDamage() + " uses)"));
			} else {
				tooltip.add(Component.literal("§cNo Filter Installed"));
			}
		}
	}

	@Override
	public List<HazardClass> getBlacklist(ItemStack stack) {
		return Collections.emptyList();
	}

	@Override
	public ItemStack getFilter(ItemStack stack) {
		return ArmorUtil.getGasMaskFilter(stack);
	}

	@Override
	public void installFilter(ItemStack stack, ItemStack filter) {
		ArmorUtil.installGasMaskFilter(stack, filter);
	}

	@Override
	public void damageFilter(ItemStack stack, int damage) {
		ArmorUtil.damageGasMaskFilter(stack, damage);
	}

	@Override
	public boolean isFilterApplicable(ItemStack stack, ItemStack filter) {
		return this.getType() == Type.HELMET;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		if (this.getType() == Type.HELMET && player.isShiftKeyDown()) {
			ItemStack stack = player.getItemInHand(hand);
			ItemStack filter = this.getFilter(stack);
			if (!filter.isEmpty()) {
				ArmorUtil.removeFilter(stack);
				if (!player.getInventory().add(filter)) {
					player.drop(filter, false);
				}
				return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
			}
		}
		return super.use(level, player, hand);
	}
}
