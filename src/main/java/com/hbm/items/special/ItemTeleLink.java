package com.hbm.items.special;

import com.hbm.items.ItemBase;
import com.hbm.util.I18nUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import java.util.List;

public class ItemTeleLink extends ItemBase {

	public ItemTeleLink(Properties properties) {
		super(properties.stacksTo(1));
	}

	public ItemTeleLink() {
		super(new Properties().stacksTo(1));
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Player player = context.getPlayer();
		Level world = context.getLevel();
		BlockPos pos = context.getClickedPos();
		ItemStack stack = context.getItemInHand();

		if (player != null && !player.isCrouching()) {
			CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> {
				tag.putInt("x", pos.getX());
				tag.putInt("y", pos.getY());
				tag.putInt("z", pos.getZ());
			});
			if (world.isClientSide()) {
				player.displayClientMessage(Component.translatable("chat.telelink.set", pos.getX(), pos.getY(), pos.getZ()), false);
			}
			return InteractionResult.SUCCESS;
		}

		return super.useOn(context);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
		CustomData data = stack.get(DataComponents.CUSTOM_DATA);
		if (data != null && data.contains("x")) {
			int x = data.copyTag().getInt("x");
			int y = data.copyTag().getInt("y");
			int z = data.copyTag().getInt("z");
			tooltipComponents.add(Component.literal(ChatFormatting.GREEN + I18nUtil.resolveKey("chat.possetxyz", x, y, z)));
		} else {
			tooltipComponents.add(Component.literal(I18nUtil.resolveKey("item.linker.desc1")));
			tooltipComponents.add(Component.literal(I18nUtil.resolveKey("item.linker.desc2")));
			tooltipComponents.add(Component.literal(ChatFormatting.YELLOW + I18nUtil.resolveKey("chat.posnoset")));
		}
	}
}
