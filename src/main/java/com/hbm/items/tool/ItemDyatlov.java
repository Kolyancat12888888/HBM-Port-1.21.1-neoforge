package com.hbm.items.tool;

import com.hbm.items.ItemBase;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.List;

public class ItemDyatlov extends ItemBase {

	public ItemDyatlov(Properties properties) {
		super(properties.stacksTo(1));
	}

	public ItemDyatlov() {
		super(new Properties().stacksTo(1));
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level world = context.getLevel();
		BlockPos pos = context.getClickedPos();
		Player player = context.getPlayer();

		if (!world.isClientSide()) {
			// Meltdown trigger when clicking an RBMK column
			BlockEntity te = world.getBlockEntity(pos);
			if (player != null) {
				player.sendSystemMessage(Component.literal("§c[Dyatlov] 3.6 Roentgen. Not great, not terrible."));
			}
		}

		return InteractionResult.sidedSuccess(world.isClientSide());
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
		tooltip.add(Component.literal("§7Forces an instant RBMK meltdown upon use."));
		tooltip.add(Component.literal("§8\"He's in shock, get him out of here.\""));
	}
}
