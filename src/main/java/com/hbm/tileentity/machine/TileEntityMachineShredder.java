package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.tileentity.TileEntityMachineBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityMachineShredder extends TileEntityMachineBase {

	public static int maxPower = 50_000;
	public long power = 5_000;
	public int progress;
	public static final int DURATION = 80;
	public static final int CONSUMPTION = 100;

	public TileEntityMachineShredder(BlockPos pos, BlockState state) {
		super(com.hbm.tileentity.ModBlockEntities.SHREDDER.get(), pos, state, 3); // 0 input, 1 output, 2 battery
	}

	@Override
	public String getDefaultName() {
		return "container.shredder";
	}

	public static void tick(Level level, BlockPos pos, BlockState state, TileEntityMachineShredder te) {
		if (level == null || level.isClientSide) return;

		ItemStack in = te.inventory.getStackInSlot(0);
		if (!in.isEmpty() && te.power >= CONSUMPTION && te.canShred(in)) {
			te.power -= CONSUMPTION;
			te.progress++;

			if (te.progress >= DURATION) {
				te.progress = 0;
				te.processShredding(in);
			}
			te.markChanged();
		} else {
			if (te.progress > 0) {
				te.progress = 0;
				te.markChanged();
			}
		}
	}

	public boolean canShred(ItemStack in) {
		return true; // Almost any solid item can be shredded into scrap or dust
	}

	public void processShredding(ItemStack in) {
		in.shrink(1);
		ItemStack out = inventory.getStackInSlot(1);
		ItemStack scrap = new ItemStack(ModItems.SCRAP.get(), 1);

		if (out.isEmpty()) {
			inventory.setStackInSlot(1, scrap);
		} else if (ItemStack.isSameItemSameComponents(out, scrap) && out.getCount() < out.getMaxStackSize()) {
			out.grow(1);
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
