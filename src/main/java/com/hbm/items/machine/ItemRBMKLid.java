package com.hbm.items.machine;

import com.hbm.blocks.machine.rbmk.RBMKBase;
import com.hbm.items.ItemBase;
import com.hbm.items.ModItems;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKBase;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ItemRBMKLid extends ItemBase {

	private final boolean isGlass;

	public ItemRBMKLid(Properties properties, boolean isGlass) {
		super(properties);
		this.isGlass = isGlass;
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();
		BlockPos pos = context.getClickedPos();
		Player player = context.getPlayer();
		ItemStack stack = context.getItemInHand();
		BlockState state = level.getBlockState(pos);
		Block block = state.getBlock();

		if (block instanceof RBMKBase rbmk) {
			BlockEntity be = level.getBlockEntity(pos);
			if (be instanceof TileEntityRBMKBase base && base.isLidRemovable()) {
				if (!base.hasLid()) {
					if (!level.isClientSide()) {
						level.playSound(null, pos, isGlass ? SoundEvents.GLASS_PLACE : SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 1.0F, 0.8F);
						if (player != null && !player.getAbilities().instabuild) {
							stack.shrink(1);
						}
					}
					return InteractionResult.sidedSuccess(level.isClientSide());
				}
			}
		}

		return InteractionResult.PASS;
	}
}
