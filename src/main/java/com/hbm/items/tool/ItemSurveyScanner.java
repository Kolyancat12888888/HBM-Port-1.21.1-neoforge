package com.hbm.items.tool;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ItemBase;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class ItemSurveyScanner extends ItemBase {

	public ItemSurveyScanner(Properties properties) {
		super(properties.stacksTo(1));
	}

	public ItemSurveyScanner() {
		super(new Properties().stacksTo(1));
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (!world.isClientSide()) {
			BlockPos playerPos = player.blockPosition();
			int x = playerPos.getX();
			int y = playerPos.getY();
			int z = playerPos.getZ();

			boolean hasOil = false;
			boolean hasColtan = false;
			boolean hasDepth = false;
			boolean hasSchist = false;
			boolean hasAussie = false;

			for (int a = -5; a <= 5; a++) {
				for (int b = -5; b <= 5; b++) {
					for (int i = y + 15; i > 1; i -= 2) {
						BlockPos checkPos = new BlockPos(x + a * 5, i, z + b * 5);
						Block block = world.getBlockState(checkPos).getBlock();

						if (block == ModBlocks.ore_oil) hasOil = true;
						else if (block == ModBlocks.ore_coltan) hasColtan = true;
						else if (block == ModBlocks.stone_depth) hasDepth = true;
						else if (block == ModBlocks.stone_depth_nether) hasDepth = true;
						else if (block == ModBlocks.stone_gneiss) hasSchist = true;
						else if (block == ModBlocks.ore_australium) hasAussie = true;
					}
				}
			}

			if (hasOil) player.sendSystemMessage(Component.translatable("chat.surveyscanner.oil").setStyle(Style.EMPTY.withColor(ChatFormatting.BLACK)));
			if (hasColtan) player.sendSystemMessage(Component.translatable("chat.surveyscanner.coltan").setStyle(Style.EMPTY.withColor(ChatFormatting.GOLD)));
			if (hasDepth) player.sendSystemMessage(Component.translatable("chat.surveyscanner.depth").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
			if (hasSchist) player.sendSystemMessage(Component.translatable("chat.surveyscanner.schist").setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_AQUA)));
			if (hasAussie) player.sendSystemMessage(Component.translatable("chat.surveyscanner.australium").setStyle(Style.EMPTY.withColor(ChatFormatting.YELLOW)));
		}

		world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0F, 1.5F);
		player.swing(hand);

		return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Player player = context.getPlayer();
		Level world = context.getLevel();
		BlockPos pos = context.getClickedPos();

		// Alcater: o_o DAMN an Easteregg
		if (player != null && world.getBlockState(pos).getBlock() == ModBlocks.block_beryllium && player.getInventory().contains(new ItemStack(ModItems.entanglement_kit))) {
			if (!world.isClientSide()) {
				// Teleport to the End (dimension 1 in legacy)
				if (world instanceof net.minecraft.server.level.ServerLevel serverLevel) {
					net.minecraft.server.level.ServerLevel endLevel = serverLevel.getServer().getLevel(Level.END);
					if (endLevel != null && player instanceof net.minecraft.server.level.ServerPlayer serverPlayer) {
						serverPlayer.teleportTo(endLevel, 0.5, 70, 0.5, player.getYRot(), player.getXRot());
					}
				}
			}
			return InteractionResult.SUCCESS;
		}

		return InteractionResult.PASS;
	}
}
