package com.hbm.items.special;

import com.hbm.items.ItemBakedBase;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemFuel extends ItemBakedBase {

	private final int burntime;

	public ItemFuel(Properties properties, String texturePath, int burntime) {
		super(properties, texturePath);
		this.burntime = burntime;
	}

	public ItemFuel(String texturePath, int burntime) {
		this(new Properties(), texturePath, burntime);
	}

	@Override
	public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
		return burntime;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> list, TooltipFlag tooltipFlag) {
		if (this == ModItems.dust) {
			if (MainRegistry.polaroidID == 11) {
				list.add(Component.literal("Another one bites the dust!"));
			} else {
				list.add(Component.literal("I hate dust!"));
			}
		}
		if (this == ModItems.powder_fire) {
			list.add(Component.literal("Used in multi purpose bombs:"));
			list.add(Component.literal("Incendiary bombs are fun!"));
		}
	}
}
