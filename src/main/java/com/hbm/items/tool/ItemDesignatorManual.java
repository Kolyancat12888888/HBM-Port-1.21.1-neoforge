package com.hbm.items.tool;

import com.hbm.api.item.IDesignatorItem;
import com.hbm.items.ItemBase;
import com.hbm.util.I18nUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class ItemDesignatorManual extends ItemBase implements IDesignatorItem {

	public ItemDesignatorManual(Properties properties) {
		super(properties.stacksTo(1));
	}

	public ItemDesignatorManual() {
		this(new Properties());
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (!world.isClientSide) {
			CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> {
				tag.putInt("xCoord", (int) player.getX());
				tag.putInt("zCoord", (int) player.getZ());
			});
			player.displayClientMessage(Component.literal(ChatFormatting.GREEN + I18nUtil.resolveKey("chat.possetxz", (int) player.getX(), (int) player.getZ())), true);
		}
		return InteractionResultHolder.sidedSuccess(stack, world.isClientSide);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
		CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
		CompoundTag tag = customData.copyTag();
		if (tag.contains("xCoord") && tag.contains("zCoord")) {
			tooltip.add(Component.literal(ChatFormatting.GREEN + I18nUtil.resolveKey("desc.targetcoord")));
			tooltip.add(Component.literal("§aX: " + tag.getInt("xCoord")));
			tooltip.add(Component.literal("§aZ: " + tag.getInt("zCoord")));
		} else {
			tooltip.add(Component.literal(ChatFormatting.YELLOW + I18nUtil.resolveKey("desc.choosetarget2")));
		}
	}

	@Override
	public boolean isReady(Level world, ItemStack stack, int x, int y, int z) {
		CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
		return customData.copyTag().contains("xCoord");
	}

	@Override
	public Vec3 getCoords(Level world, ItemStack stack, int x, int y, int z) {
		CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
		CompoundTag tag = customData.copyTag();
		return new Vec3(tag.getInt("xCoord"), 0, tag.getInt("zCoord"));
	}
}
