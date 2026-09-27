package com.hbm.items.special;

import com.hbm.items.ItemBase;
import com.hbm.items.ModItems;
import com.hbm.util.I18nUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import java.util.List;

public class ItemDrop extends ItemBase {

	protected final String dropType;

	public ItemDrop(Properties properties, String dropType) {
		super(properties);
		this.dropType = dropType;
	}

	public ItemDrop(String dropType) {
		this(new Properties(), dropType);
	}

	@Override
	public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entityItem) {
		if (entityItem.onGround() || entityItem.isInLava() || entityItem.isOnFire()) {
			Level world = entityItem.level();
			if (!world.isClientSide()) {
				if (this == ModItems.pellet_antimatter) {
					world.explode(null, entityItem.getX(), entityItem.getY(), entityItem.getZ(), 100.0F, Level.ExplosionInteraction.BLOCK);
				} else if (this == ModItems.singularity || this == ModItems.black_hole) {
					world.explode(null, entityItem.getX(), entityItem.getY(), entityItem.getZ(), 10.0F, Level.ExplosionInteraction.BLOCK);
				} else if (this == ModItems.detonator_de) {
					world.explode(null, entityItem.getX(), entityItem.getY(), entityItem.getZ(), 15.0F, Level.ExplosionInteraction.BLOCK);
				}
				entityItem.discard();
				return true;
			}
		}
		return false;
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Player player = context.getPlayer();
		Level world = context.getLevel();
		BlockPos pos = context.getClickedPos();
		ItemStack stack = context.getItemInHand();

		if (this == ModItems.detonator_deadman && player != null && player.isCrouching()) {
			CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> {
				tag.putInt("x", pos.getX());
				tag.putInt("y", pos.getY());
				tag.putInt("z", pos.getZ());
			});
			if (world.isClientSide()) {
				player.displayClientMessage(Component.translatable("chat.posset"), false);
			}
			return InteractionResult.SUCCESS;
		}
		return super.useOn(context);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag tooltipFlag) {
		if (this == ModItems.pellet_antimatter) {
			tooltip.add(Component.literal("Very heavy antimatter cluster."));
			tooltip.add(Component.literal("Gets rid of black holes."));
		}
		if (this == ModItems.singularity) {
			tooltip.add(Component.literal("You may be asking:"));
			tooltip.add(Component.literal("\"But HBM, a manifold with an undefined"));
			tooltip.add(Component.literal("state of spacetime? How is this possible?\""));
			tooltip.add(Component.literal("Long answer short:"));
			tooltip.add(Component.literal("\"I have no idea!\""));
		}
		if (this == ModItems.singularity_counter_resonant) {
			tooltip.add(Component.literal("Nullifies resonance of objects in"));
			tooltip.add(Component.literal("non-euclidean space, creates variable"));
			tooltip.add(Component.literal("gravity well. Spontaneously spawns"));
			tooltip.add(Component.literal("tesseracts. If a tesseract happens to"));
			tooltip.add(Component.literal("appear near you, do not look directly"));
			tooltip.add(Component.literal("at it."));
		}
		if (this == ModItems.singularity_super_heated) {
			tooltip.add(Component.literal("Continuously heats up matter by"));
			tooltip.add(Component.literal("resonating every planck second."));
			tooltip.add(Component.literal("Tends to catch fire or to create"));
			tooltip.add(Component.literal("small plamsa arcs. Not edible."));
		}
		if (this == ModItems.black_hole) {
			tooltip.add(Component.literal("Contains a regular singularity"));
			tooltip.add(Component.literal("in the center. Large enough to"));
			tooltip.add(Component.literal("stay stable. It's not the end"));
			tooltip.add(Component.literal("of the world as we know it,"));
			tooltip.add(Component.literal("and I don't feel fine."));
		}
		if (this == ModItems.detonator_deadman) {
			tooltip.add(Component.literal("Shift right-click to set position,"));
			tooltip.add(Component.literal("drop to detonate!"));
			CustomData data = stack.get(DataComponents.CUSTOM_DATA);
			if (data == null || !data.contains("x")) {
				tooltip.add(Component.literal("No position set!"));
			} else {
				tooltip.add(Component.literal("Set pos to " + data.copyTag().getInt("x") + ", " + data.copyTag().getInt("y") + ", " + data.copyTag().getInt("z")));
			}
		}
		if (this == ModItems.detonator_de) {
			tooltip.add(Component.literal("Explodes when dropped!"));
		}
		tooltip.add(Component.literal(ChatFormatting.RED + "[" + I18nUtil.resolveKey("trait.drop") + "]"));
	}
}
