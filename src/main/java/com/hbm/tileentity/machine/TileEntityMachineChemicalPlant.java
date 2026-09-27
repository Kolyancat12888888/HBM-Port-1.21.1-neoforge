package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.recipes.ChemicalPlantRecipes;
import com.hbm.inventory.recipes.ChemicalPlantRecipes.ChemRecipe;
import com.hbm.tileentity.TileEntityMachineBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityMachineChemicalPlant extends TileEntityMachineBase {

	public static int maxPower = 100_000;
	public long power = 10_000;
	public int progress;
	public boolean isProgressing;

	public TileEntityMachineChemicalPlant(BlockPos pos, BlockState state) {
		super(com.hbm.tileentity.ModBlockEntities.CHEMPLANT.get(), pos, state, 8); // 3 item inputs (0,1,2), 3 item outputs (3,4,5), 2 upgrade/battery (6,7)
	}

	@Override
	public String getDefaultName() {
		return "container.chemplant";
	}

	public static void tick(Level level, BlockPos pos, BlockState state, TileEntityMachineChemicalPlant te) {
		if (level == null || level.isClientSide) return;

		ItemStack[] inputs = new ItemStack[]{te.inventory.getStackInSlot(0), te.inventory.getStackInSlot(1), te.inventory.getStackInSlot(2)};
		ChemRecipe recipe = ChemicalPlantRecipes.INSTANCE.getRecipe(inputs);

		if (recipe != null && te.power >= recipe.powerConsumption && te.canOutput(recipe)) {
			te.isProgressing = true;
			te.power -= recipe.powerConsumption;
			te.progress++;

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

	public boolean canOutput(ChemRecipe recipe) {
		if (recipe.outputItems == null) return false;
		for (int i = 0; i < Math.min(3, recipe.outputItems.length); i++) {
			ItemStack out = recipe.outputItems[i];
			if (out == null || out.isEmpty()) continue;
			ItemStack existing = inventory.getStackInSlot(i + 3);
			if (existing.isEmpty()) continue;
			if (!ItemStack.isSameItemSameComponents(existing, out)) return false;
			if (existing.getCount() + out.getCount() > existing.getMaxStackSize()) return false;
		}
		return true;
	}

	public void processItem(ChemRecipe recipe) {
		if (recipe.inputItems != null) {
			for (var in : recipe.inputItems) {
				for (int s = 0; s < 3; s++) {
					ItemStack slot = inventory.getStackInSlot(s);
					if (in.matchesRecipe(slot, false)) {
						slot.shrink(in.count());
						break;
					}
				}
			}
		}

		if (recipe.outputItems != null) {
			for (int i = 0; i < Math.min(3, recipe.outputItems.length); i++) {
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
		tag.putLong("power", power);
		tag.putInt("progress", progress);
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		power = tag.getLong("power");
		progress = tag.getInt("progress");
	}
}
