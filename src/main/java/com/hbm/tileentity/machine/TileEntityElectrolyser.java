package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.items.ModItems;
import com.hbm.tileentity.TileEntityMachineBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityElectrolyser extends TileEntityMachineBase {

	public static int maxPower = 50_000;
	public long power = 5_000;
	public int progress;
	public static final int DURATION = 100;
	public static final int CONSUMPTION = 50;

	public TileEntityElectrolyser(BlockPos pos, BlockState state) {
		super(com.hbm.tileentity.ModBlockEntities.ELECTROLYSER.get(), pos, state, 4); // 0 input, 1 output1, 2 output2, 3 battery
	}

	@Override
	public String getDefaultName() {
		return "container.electrolyser";
	}

	public static void tick(Level level, BlockPos pos, BlockState state, TileEntityElectrolyser te) {
		if (level == null || level.isClientSide) return;

		ItemStack in = te.inventory.getStackInSlot(0);
		if (!in.isEmpty() && te.power >= CONSUMPTION && te.canElectrolyse(in)) {
			te.power -= CONSUMPTION;
			te.progress++;

			if (te.progress >= DURATION) {
				te.progress = 0;
				te.processElectrolysis(in);
			}
			te.markChanged();
		} else {
			if (te.progress > 0) {
				te.progress = 0;
				te.markChanged();
			}
		}
	}

	public boolean canElectrolyse(ItemStack in) {
		// Water bucket or heavy water or bauxite
		return in.getItem() == Items.WATER_BUCKET || in.getItem() == ModItems.POWDER_LITHIUM.get();
	}

	public void processElectrolysis(ItemStack in) {
		if (in.getItem() == Items.WATER_BUCKET) {
			inventory.setStackInSlot(0, new ItemStack(Items.BUCKET));
			// Outputs: Hydrogen / Oxygen canisters or items
			ItemStack out1 = inventory.getStackInSlot(1);
			if (out1.isEmpty()) {
				inventory.setStackInSlot(1, new ItemStack(ModItems.GAS_FULL.get(), 1));
			} else {
				out1.grow(1);
			}
		} else if (in.getItem() == ModItems.POWDER_LITHIUM.get()) {
			in.shrink(1);
			ItemStack out1 = inventory.getStackInSlot(1);
			if (out1.isEmpty()) {
				inventory.setStackInSlot(1, new ItemStack(ModItems.POWDER_LITHIUM_TINY.get(), 9));
			} else {
				out1.grow(9);
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
