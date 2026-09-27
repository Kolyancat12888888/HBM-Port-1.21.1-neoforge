package com.hbm.items.special;

import com.hbm.items.ItemBase;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemMissile;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemLootCrate extends ItemBase {

	public static List<ItemMissile> list10 = new ArrayList<>();
	public static List<ItemMissile> list15 = new ArrayList<>();
	public static List<ItemMissile> listMisc = new ArrayList<>();
	private static final Random rand = new Random();

	public ItemLootCrate(Properties properties) {
		super(properties.stacksTo(1));
	}

	public ItemLootCrate() {
		super(new Properties().stacksTo(1));
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		ItemStack stack = playerIn.getItemInHand(handIn);

		if (!worldIn.isClientSide()) {
			if (this == ModItems.loot_10 && !list10.isEmpty())
				playerIn.getInventory().add(new ItemStack(choose(list10)));
			if (this == ModItems.loot_15 && !list15.isEmpty())
				playerIn.getInventory().add(new ItemStack(choose(list15)));
			if (this == ModItems.loot_misc && !listMisc.isEmpty())
				playerIn.getInventory().add(new ItemStack(choose(listMisc)));
		}

		stack.shrink(1);
		return InteractionResultHolder.sidedSuccess(stack, worldIn.isClientSide());
	}

	private ItemMissile choose(List<ItemMissile> parts) {
		boolean flag = true;
		ItemMissile item = null;

		while (flag) {
			item = parts.get(rand.nextInt(parts.size()));
			if (item.rarity == null) return item;

			switch (item.rarity) {
				case COMMON:
					flag = false;
					break;
				case UNCOMMON:
					if (rand.nextInt(5) == 0) flag = false;
					break;
				case RARE:
					if (rand.nextInt(10) == 0) flag = false;
					break;
				case EPIC:
					if (rand.nextInt(25) == 0) flag = false;
					break;
				case LEGENDARY:
					if (rand.nextInt(50) == 0) flag = false;
					break;
				case SEWS_CLOTHES_AND_SUCKS_HORSE_COCK:
					if (rand.nextInt(100) == 0) flag = false;
					break;
			}
		}

		return item;
	}
}
