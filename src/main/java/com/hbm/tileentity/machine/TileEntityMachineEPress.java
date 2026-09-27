package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.recipes.PressRecipes;
import com.hbm.tileentity.TileEntityMachineBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityMachineEPress extends TileEntityMachineBase {

	public static int maxPower = 50_000;
	public long power = 5_000;
	public int progress;
	public static final int DURATION = 20;
	public static final int CONSUMPTION = 200;

	public TileEntityMachineEPress(BlockPos pos, BlockState state) {
		super(com.hbm.tileentity.ModBlockEntities.EPRESS.get(), pos, state, 4); // 0 input, 1 stamp, 2 output, 3 battery
	}

	@Override
	public String getDefaultName() {
		return "container.epress";
	}

	public static void tick(Level level, BlockPos pos, BlockState state, TileEntityMachineEPress te) {
		if (level == null || level.isClientSide) return;

		ItemStack in = te.inventory.getStackInSlot(0);
		ItemStack stamp = te.inventory.getStackInSlot(1);

		if (!in.isEmpty() && !stamp.isEmpty() && te.power >= CONSUMPTION) {
			ItemStack out = PressRecipes.getOutput(in, stamp);
			if (!out.isEmpty() && te.canOutput(out)) {
				te.power -= CONSUMPTION;
				te.progress++;

				if (te.progress >= DURATION) {
					te.progress = 0;
					te.processItem(out);
				}
				te.markChanged();
				return;
			}
		}

		if (te.progress > 0) {
			te.progress = 0;
			te.markChanged();
		}
	}

	public boolean canOutput(ItemStack out) {
		ItemStack existing = inventory.getStackInSlot(2);
		if (existing.isEmpty()) return true;
		if (!ItemStack.isSameItemSameComponents(existing, out)) return false;
		return existing.getCount() + out.getCount() <= existing.getMaxStackSize();
	}

	public void processItem(ItemStack out) {
		ItemStack in = inventory.getStackInSlot(0);
		ItemStack stamp = inventory.getStackInSlot(1);
		in.shrink(1);

		// Damage stamp
		if (stamp.isDamageableItem()) {
			stamp.setDamageValue(stamp.getDamageValue() + 1);
			if (stamp.getDamageValue() >= stamp.getMaxDamage()) {
				inventory.setStackInSlot(1, ItemStack.EMPTY);
			}
		}

		ItemStack existing = inventory.getStackInSlot(2);
		if (existing.isEmpty()) {
			inventory.setStackInSlot(2, out.copy());
		} else if (ItemStack.isSameItemSameComponents(existing, out)) {
			existing.grow(out.getCount());
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
