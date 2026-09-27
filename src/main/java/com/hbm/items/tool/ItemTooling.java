package com.hbm.items.tool;

import com.hbm.api.block.IToolable;
import com.hbm.api.block.IToolable.ToolType;
import com.hbm.items.ItemBakedBase;
import com.hbm.util.I18nUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class ItemTooling extends ItemBakedBase {

	protected ToolType type;

	public ItemTooling(Properties properties, ToolType type, int dura, String texturePath) {
		super(properties.durability(dura > 0 ? dura : 0).stacksTo(1), texturePath);
		this.type = type;
		type.register(new ItemStack(this));
	}

	public ItemTooling(ToolType type, int dura, String texturePath) {
		this(new Properties(), type, dura, texturePath);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level world = context.getLevel();
		BlockPos pos = context.getClickedPos();
		Player player = context.getPlayer();
		InteractionHand hand = context.getHand();
		Direction facing = context.getClickedFace();
		float hitX = (float) (context.getClickLocation().x - pos.getX());
		float hitY = (float) (context.getClickLocation().y - pos.getY());
		float hitZ = (float) (context.getClickLocation().z - pos.getZ());

		Block b = world.getBlockState(pos).getBlock();

		if (player != null && b instanceof IToolable toolable) {
			if (toolable.onScrew(world, player, pos.getX(), pos.getY(), pos.getZ(), facing, hitX, hitY, hitZ, hand, this.type)) {
				ItemStack held = player.getItemInHand(hand);
				if (held.isDamageableItem()) {
					held.hurtAndBreak(1, player, player.getEquipmentSlotForItem(held));
				}
				return InteractionResult.SUCCESS;
			}
		}
		return super.useOn(context);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
		if (type == ToolType.SCREWDRIVER) {
			tooltipComponents.add(Component.literal(I18nUtil.resolveKey("desc.screwdriver1")));
		}
	}

	public ToolType getType() {
		return type;
	}
}
