package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.tileentity.TileEntityMachineBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityMachineCrystallizer extends TileEntityMachineBase {

	public static int maxPower = 50_000;
	public long power = 5_000;
	public int progress;
	public static final int DURATION = 200;
	public static final int CONSUMPTION = 25;

	public TileEntityMachineCrystallizer(BlockPos pos, BlockState state) {
		super(com.hbm.tileentity.ModBlockEntities.CRYSTALLIZER.get(), pos, state, 3); // 0 input, 1 output, 2 battery
	}

	@Override
	public String getDefaultName() {
		return "container.crystallizer";
	}

	public static void tick(Level level, BlockPos pos, BlockState state, TileEntityMachineCrystallizer te) {
		if (level == null || level.isClientSide) return;

		ItemStack in = te.inventory.getStackInSlot(0);
		if (!in.isEmpty() && te.power >= CONSUMPTION && te.canCrystallize(in)) {
			te.power -= CONSUMPTION;
			te.progress++;

			if (te.progress >= DURATION) {
				te.progress = 0;
				te.processCrystallization(in);
			}
			te.markChanged();
		} else {
			if (te.progress > 0) {
				te.progress = 0;
				te.markChanged();
			}
		}
	}

	public boolean canCrystallize(ItemStack in) {
		return in.getItem() == ModItems.POWDER_BERYLLIUM.get() || in.getItem() == ModItems.POWDER_SCHRABIDIUM.get() || in.getItem() == ModItems.POWDER_COAL.get();
	}

	public void processCrystallization(ItemStack in) {
		ItemStack out = inventory.getStackInSlot(1);
		ItemStack result = ItemStack.EMPTY;

		if (in.getItem() == ModItems.POWDER_BERYLLIUM.get()) {
			result = new ItemStack(ModItems.CRYSTAL_BERYLLIUM.get(), 1);
		} else if (in.getItem() == ModItems.POWDER_SCHRABIDIUM.get()) {
			result = new ItemStack(ModItems.CRYSTAL_SCHRABIDIUM.get(), 1);
		} else if (in.getItem() == ModItems.POWDER_COAL.get()) {
			result = new ItemStack(ModItems.CRYSTAL_COAL.get(), 1);
		}

		if (!result.isEmpty()) {
			in.shrink(1);
			if (out.isEmpty()) {
				inventory.setStackInSlot(1, result);
			} else if (ItemStack.isSameItemSameComponents(out, result) && out.getCount() < out.getMaxStackSize()) {
				out.grow(1);
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
