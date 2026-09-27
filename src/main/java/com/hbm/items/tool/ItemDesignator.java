package com.hbm.items.tool;

import com.hbm.blocks.bomb.BlockLaunchPad;
import com.hbm.items.ItemBase;
import com.hbm.tileentity.bomb.TileEntityLaunchPad;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.List;

public class ItemDesignator extends ItemBase {

	public ItemDesignator(Properties properties) {
		super(properties.stacksTo(1));
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();
		BlockPos pos = context.getClickedPos();
		Player player = context.getPlayer();
		ItemStack stack = context.getItemInHand();

		if (level.getBlockState(pos).getBlock() instanceof BlockLaunchPad) {
			BlockEntity be = level.getBlockEntity(pos);
			if (be instanceof TileEntityLaunchPad launchPad) {
				CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
				CompoundTag tag = customData.copyTag();

				if (tag.contains("targetX") && tag.contains("targetZ")) {
					launchPad.targetX = tag.getDouble("targetX");
					launchPad.targetZ = tag.getDouble("targetZ");
					launchPad.markChanged();
					if (player != null && !level.isClientSide) {
						player.displayClientMessage(Component.literal("Target coordinates loaded into Launch Pad: X: " + (int)launchPad.targetX + ", Z: " + (int)launchPad.targetZ), true);
					}
					return InteractionResult.sidedSuccess(level.isClientSide);
				}
			}
		} else {
			// Save target coordinates to designator
			CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
			CompoundTag tag = customData.copyTag();
			tag.putDouble("targetX", pos.getX());
			tag.putDouble("targetZ", pos.getZ());
			stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));

			if (player != null && !level.isClientSide) {
				player.displayClientMessage(Component.literal("Target set to: X: " + pos.getX() + ", Z: " + pos.getZ()), true);
			}
			return InteractionResult.sidedSuccess(level.isClientSide);
		}

		return InteractionResult.PASS;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
		CompoundTag tag = customData.copyTag();
		if (tag.contains("targetX") && tag.contains("targetZ")) {
			tooltip.add(Component.literal("Target X: " + (int)tag.getDouble("targetX") + ", Z: " + (int)tag.getDouble("targetZ")));
		} else {
			tooltip.add(Component.literal("Shift+Right Click ground to set target"));
		}
	}
}
