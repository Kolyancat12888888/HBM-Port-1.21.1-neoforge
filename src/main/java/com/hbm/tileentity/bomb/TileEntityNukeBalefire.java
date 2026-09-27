package com.hbm.tileentity.bomb;

import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.logic.EntityBalefire;
import com.hbm.items.ModItems;
import com.hbm.tileentity.ModBlockEntities;
import com.hbm.tileentity.TileEntityMachineBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.UUID;

public class TileEntityNukeBalefire extends TileEntityMachineBase {

	public boolean started = false;
	public int timer = 18000;
	public UUID placerID;

	public TileEntityNukeBalefire(BlockPos pos, BlockState state) {
		super(ModBlockEntities.NUKE_BALEFIRE.get(), pos, state, 2);
	}

	@Override
	public String getDefaultName() {
		return "container.nukeFstbmb";
	}

	public static void tick(Level level, BlockPos pos, BlockState state, TileEntityNukeBalefire te) {
		if (level == null || level.isClientSide) return;

		if (!te.isLoaded()) {
			te.started = false;
		}

		if (te.started) {
			te.timer--;
		}

		if (te.timer <= 0) {
			te.explode();
		}

		te.markChanged();
	}

	public boolean isLoaded() {
		return hasEgg() && hasBattery();
	}

	public boolean hasEgg() {
		return inventory.getStackInSlot(0).getItem() == ModItems.EGG_BALEFIRE.get();
	}

	public boolean hasBattery() {
		ItemStack stack = inventory.getStackInSlot(1);
		return stack.getItem() == ModItems.BATTERY_SPARK.get() || stack.getItem() == ModItems.BATTERY_TRIXITE.get();
	}

	public void explode() {
		if (level == null || level.isClientSide) return;

		for (int i = 0; i < inventory.getSlots(); i++) {
			inventory.setStackInSlot(i, ItemStack.EMPTY);
		}

		level.setBlock(getBlockPos(), Blocks.AIR.defaultBlockState(), 3);

		EntityBalefire bf = new EntityBalefire(level);
		bf.setPos(getBlockPos().getX() + 0.5, getBlockPos().getY() + 0.5, getBlockPos().getZ() + 0.5);
		bf.destructionRange = 250;
		level.addFreshEntity(bf);

		EntityNukeTorex.statFacBale(level, getBlockPos().getX() + 0.5, getBlockPos().getY() + 5, getBlockPos().getZ() + 0.5, 250.0F);
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		tag.putBoolean("started", started);
		tag.putInt("timer", timer);
		if (placerID != null) tag.putUUID("placer", placerID);
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		started = tag.getBoolean("started");
		timer = tag.getInt("timer");
		if (tag.hasUUID("placer")) placerID = tag.getUUID("placer");
	}
}
