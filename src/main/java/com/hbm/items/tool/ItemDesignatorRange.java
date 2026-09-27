package com.hbm.items.tool;

import com.hbm.api.item.IDesignatorItem;
import com.hbm.blocks.bomb.BlockLaunchPad;
import com.hbm.items.ItemBase;
import com.hbm.util.I18nUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class ItemDesignatorRange extends ItemBase implements IDesignatorItem {

	public ItemDesignatorRange(Properties properties) {
		super(properties.stacksTo(1));
	}

	public ItemDesignatorRange() {
		this(new Properties());
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		Vec3 eyePos = player.getEyePosition(1.0F);
		Vec3 lookVec = player.getViewVector(1.0F).scale(300.0);
		Vec3 reach = eyePos.add(lookVec);

		BlockHitResult hit = world.clip(new ClipContext(eyePos, reach, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
		if (hit.getType() == HitResult.Type.BLOCK) {
			BlockPos pos = hit.getBlockPos();
			if (!(world.getBlockState(pos).getBlock() instanceof BlockLaunchPad)) {
				CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> {
					tag.putInt("xCoord", pos.getX());
					tag.putInt("zCoord", pos.getZ());
				});

				if (world.isClientSide) {
					player.displayClientMessage(Component.literal(ChatFormatting.GREEN + I18nUtil.resolveKey("chat.possetxz", pos.getX(), pos.getZ())), true);
				}
				world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.UI_BUTTON_CLICK.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
				return InteractionResultHolder.sidedSuccess(stack, world.isClientSide);
			}
		}

		return super.use(world, player, hand);
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
			tooltip.add(Component.literal(ChatFormatting.YELLOW + I18nUtil.resolveKey("desc.choosetarget3")));
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
