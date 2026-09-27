package com.hbm.tileentity.machine;

import com.hbm.inventory.recipes.AssemblyMachineRecipes;
import com.hbm.inventory.recipes.AssemblyMachineRecipes.AssemblyRecipe;
import com.hbm.tileentity.TileEntityMachineBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityMachineAssemblyMachine extends TileEntityMachineBase {

	public static int maxPower = 100_000;
	public long power = 10_000;
	public int progress;
	public boolean isProgressing;

	public TileEntityMachineAssemblyMachine(BlockPos pos, BlockState state) {
		super(com.hbm.tileentity.ModBlockEntities.ASSEMBLY_MACHINE.get(), pos, state, 17);
	}

	@Override
	public String getDefaultName() {
		return "container.assemblyMachine";
	}

	public static void tick(Level level, BlockPos pos, BlockState state, TileEntityMachineAssemblyMachine te) {
		if (level == null || level.isClientSide) return;

		ItemStack[] inputs = new ItemStack[12];
		for (int i = 0; i < 12; i++) {
			inputs[i] = te.inventory.getStackInSlot(i);
		}

		AssemblyRecipe recipe = AssemblyMachineRecipes.INSTANCE.getRecipe(inputs);

		if (recipe != null && te.power >= recipe.powerConsumption && te.canOutput(recipe.outputItem)) {
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

	public boolean canOutput(ItemStack out) {
		if (out == null || out.isEmpty()) return false;
		ItemStack existing = inventory.getStackInSlot(16);
		if (existing.isEmpty()) return true;
		if (!ItemStack.isSameItemSameComponents(existing, out)) return false;
		return existing.getCount() + out.getCount() <= existing.getMaxStackSize();
	}

	public void processItem(AssemblyRecipe recipe) {
		if (recipe.inputItems != null) {
			for (var in : recipe.inputItems) {
				for (int s = 0; s < 12; s++) {
					ItemStack slot = inventory.getStackInSlot(s);
					if (in.matchesRecipe(slot, false)) {
						slot.shrink(in.count());
						break;
					}
				}
			}
		}

		if (recipe.outputItem != null && !recipe.outputItem.isEmpty()) {
			ItemStack existing = inventory.getStackInSlot(16);
			if (existing.isEmpty()) {
				inventory.setStackInSlot(16, recipe.outputItem.copy());
			} else if (ItemStack.isSameItemSameComponents(existing, recipe.outputItem)) {
				existing.grow(recipe.outputItem.getCount());
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
