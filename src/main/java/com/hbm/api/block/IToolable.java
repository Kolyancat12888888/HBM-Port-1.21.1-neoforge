package com.hbm.api.block;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface IToolable {
	boolean onScrew(Level world, Player player, int x, int y, int z, Direction side, float fX, float fY, float fZ, InteractionHand hand, ToolType tool);
	
	default boolean onScrew(Level world, Player player, BlockPos pos, Direction side, float fX, float fY, float fZ, InteractionHand hand, ToolType tool){
		return onScrew(world, player, pos.getX(), pos.getY(), pos.getZ(), side, fX, fY, fZ, hand, tool);
	}

	enum ToolType {
		SCREWDRIVER,
		HAND_DRILL,
		DEFUSER,
		WRENCH,
		TORCH,
		BOLT;

        public static final ToolType[] VALUES = values();

		public final List<ItemStack> stacksForDisplay = new ArrayList<>();
		private static final HashMap<String, ToolType> map = new HashMap<>();

		public void register(ItemStack stack) {
			stacksForDisplay.add(stack);
			map.put(stack.getItem().getDescriptionId(), this);
		}

		public static ToolType getType(ItemStack stack) {
			if (stack == null || stack.isEmpty()) return null;
			return map.get(stack.getItem().getDescriptionId());
		}
	}
}
