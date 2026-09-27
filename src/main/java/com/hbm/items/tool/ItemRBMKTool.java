package com.hbm.items.tool;

import com.hbm.blocks.machine.rbmk.RBMKBase;
import com.hbm.items.ItemBase;
import net.minecraft.ChatFormatting;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class ItemRBMKTool extends ItemBase {

	public ItemRBMKTool(Properties properties) {
		super(properties.stacksTo(1));
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();
		BlockPos pos = context.getClickedPos();
		Player player = context.getPlayer();
		ItemStack stack = context.getItemInHand();
		BlockState state = level.getBlockState(pos);
		Block block = state.getBlock();

		if (block instanceof RBMKBase) {
			if (!level.isClientSide() && player != null) {
				CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> {
					tag.putInt("targetX", pos.getX());
					tag.putInt("targetY", pos.getY());
					tag.putInt("targetZ", pos.getZ());
				});
				player.sendSystemMessage(Component.literal("Linked RBMK Tool to column at " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ()).withStyle(ChatFormatting.YELLOW));
			}
			return InteractionResult.sidedSuccess(level.isClientSide());
		}

		return InteractionResult.PASS;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		CustomData data = stack.get(DataComponents.CUSTOM_DATA);
		if (data != null && data.contains("targetX")) {
			CompoundTag tag = data.copyTag();
			tooltip.add(Component.literal("Target: " + tag.getInt("targetX") + ", " + tag.getInt("targetY") + ", " + tag.getInt("targetZ")).withStyle(ChatFormatting.GREEN));
		} else {
			tooltip.add(Component.literal("Shift-Right-Click an RBMK column to link.").withStyle(ChatFormatting.GRAY));
		}
	}
}
