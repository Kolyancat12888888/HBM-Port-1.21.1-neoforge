package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.recipes.BlastFurnaceRecipe;
import com.hbm.inventory.recipes.BlastFurnaceRecipesNT;
import com.hbm.tileentity.TileEntityMachineBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityMachineBlastFurnace extends TileEntityMachineBase {

	public boolean isProgressing;
	public float progress;
	public float speed;
	public int fuel;

	public static final int FUEL_COAL = 200 * 8;
	public static final int FUEL_RATE = 200 * 4;
	public static final int MAX_FUEL = FUEL_COAL * 24;

	public TileEntityMachineBlastFurnace(BlockPos pos, BlockState state) {
		super(com.hbm.tileentity.ModBlockEntities.BLAST_FURNACE.get(), pos, state, 5);
	}

	@Override
	public String getDefaultName() {
		return "container.blastFurnace";
	}

	public static void tick(Level level, BlockPos pos, BlockState state, TileEntityMachineBlastFurnace te) {
		if (level == null || level.isClientSide) return;

		ItemStack fuelStack = te.inventory.getStackInSlot(0);
		if (!fuelStack.isEmpty()) {
			int capacity = MAX_FUEL - te.fuel;
			int burnValue = getBurnTime(fuelStack);
			if (burnValue > 0 && burnValue <= capacity) {
				te.fuel += burnValue;
				fuelStack.shrink(1);
				te.markChanged();
			}
		}

		te.speed = 0F;
		ItemStack in0 = te.inventory.getStackInSlot(1);
		ItemStack in1 = te.inventory.getStackInSlot(2);
		BlastFurnaceRecipe recipe = BlastFurnaceRecipesNT.INSTANCE.getRecipe(in0, in1);

		if (recipe != null && te.fuel >= FUEL_RATE && te.canOutput(recipe)) {
			te.speed = 1.0F;
			te.isProgressing = true;
			te.progress += te.speed;
			te.fuel -= 1;

			if (te.progress >= recipe.duration) {
				te.progress = 0;
				te.processItem(recipe);
			}
			te.markChanged();
		} else {
			te.isProgressing = false;
			if (te.progress > 0) {
				te.progress = 0;
				te.markChanged();
			}
		}
	}

	public static int getBurnTime(ItemStack stack) {
		if (stack.getItem() == Items.COAL || stack.getItem() == Items.CHARCOAL) return FUEL_COAL;
		if (stack.getItem() == Items.COAL_BLOCK) return FUEL_COAL * 10;
		if (stack.getItem() == Items.LAVA_BUCKET) return FUEL_COAL * 50;
		return 0;
	}

	public boolean canOutput(BlastFurnaceRecipe recipe) {
		if (recipe.outputItems == null) return false;
		for (int i = 0; i < Math.min(2, recipe.outputItems.length); i++) {
			ItemStack out = recipe.outputItems[i];
			if (out == null || out.isEmpty()) continue;
			ItemStack existing = inventory.getStackInSlot(i + 3);
			if (existing.isEmpty()) continue;
			if (!ItemStack.isSameItemSameComponents(existing, out)) return false;
			if (existing.getCount() + out.getCount() > existing.getMaxStackSize()) return false;
		}
		return true;
	}

	public void processItem(BlastFurnaceRecipe recipe) {
		if (recipe.inputItems != null) {
			for (var in : recipe.inputItems) {
				for (int s = 1; s <= 2; s++) {
					ItemStack slot = inventory.getStackInSlot(s);
					if (in.matchesRecipe(slot, false)) {
						slot.shrink(in.count());
						break;
					}
				}
			}
		}

		if (recipe.outputItems != null) {
			for (int i = 0; i < Math.min(2, recipe.outputItems.length); i++) {
				ItemStack out = recipe.outputItems[i];
				if (out != null && !out.isEmpty()) {
					ItemStack existing = inventory.getStackInSlot(i + 3);
					if (existing.isEmpty()) {
						inventory.setStackInSlot(i + 3, out.copy());
					} else if (ItemStack.isSameItemSameComponents(existing, out)) {
						existing.grow(out.getCount());
					}
				}
			}
		}
		this.markChanged();
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		tag.putInt("fuel", fuel);
		tag.putFloat("progress", progress);
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		fuel = tag.getInt("fuel");
		progress = tag.getFloat("progress");
	}
}
