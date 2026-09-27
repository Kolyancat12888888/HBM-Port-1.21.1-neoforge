package com.hbm.tileentity.bomb;

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

public class TileEntityNukeCustom extends TileEntityMachineBase {

	public UUID placerID;
	public float tnt = 0F;
	public float nuke = 0F;
	public float hydro = 0F;
	public float bale = 0F;
	public float dirty = 0F;
	public float schrab = 0F;
	public float sol = 0F;
	public float euph = 0F;

	public TileEntityNukeCustom(BlockPos pos, BlockState state) {
		super(ModBlockEntities.NUKE_CUSTOM.get(), pos, state, 27);
	}

	@Override
	public String getDefaultName() {
		return "container.nukeCustom";
	}

	public static void tick(Level level, BlockPos pos, BlockState state, TileEntityNukeCustom te) {
		if (level == null || level.isClientSide) return;
		te.recalculateYield();
	}

	public void recalculateYield() {
		float rawTnt = 0F;
		float rawNuke = 0F;
		float rawHydro = 0F;
		float rawBale = 0F;
		float rawDirty = 0F;
		float rawSchrab = 0F;
		float rawSol = 0F;
		float rawEuph = 0F;

		for (int i = 0; i < inventory.getSlots(); i++) {
			ItemStack stack = inventory.getStackInSlot(i);
			if (stack.isEmpty()) continue;

			if (stack.getItem() == ModItems.INGOT_U235.get()) rawNuke += 15F * stack.getCount();
			else if (stack.getItem() == ModItems.INGOT_PU239.get()) rawNuke += 25F * stack.getCount();
			else if (stack.getItem() == ModItems.INGOT_NEPTUNIUM.get()) rawNuke += 30F * stack.getCount();
			else if (stack.getItem() == ModItems.INGOT_SCHRABIDIUM.get()) rawSchrab += 5F * stack.getCount();
			else if (stack.getItem() == ModItems.SOLINIUM_CORE.get()) rawSol += 20F * stack.getCount();
			else if (stack.getItem() == ModItems.INGOT_EUPHEMIUM.get()) rawEuph += 1F * stack.getCount();
			else if (stack.getItem() == ModItems.EGG_BALEFIRE.get()) rawBale += 150F * stack.getCount();
			else if (stack.getItem() == ModItems.LITHIUM.get()) rawHydro += 20F * stack.getCount();
			else if (stack.getItem() == ModItems.INGOT_SEMTEX.get()) rawTnt += 8F * stack.getCount();
			else if (stack.getItem() == ModItems.INGOT_C4.get()) rawTnt += 10F * stack.getCount();
			else rawTnt += 0.5F * stack.getCount();
		}

		this.tnt = Math.min(rawTnt, 500F);
		this.nuke = Math.min(rawNuke, 250F);
		this.hydro = Math.min(rawHydro, 350F);
		this.bale = Math.min(rawBale, 350F);
		this.dirty = Math.min(rawDirty, 250F);
		this.schrab = Math.min(rawSchrab, 250F);
		this.sol = Math.min(rawSol, 250F);
		this.euph = Math.min(rawEuph, 100F);
	}

	public void clearSlots() {
		for (int i = 0; i < inventory.getSlots(); i++) {
			inventory.setStackInSlot(i, ItemStack.EMPTY);
		}
		markChanged();
	}

	public void destruct() {
		clearSlots();
		if (level != null) {
			level.setBlock(getBlockPos(), Blocks.AIR.defaultBlockState(), 3);
		}
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		if (placerID != null) tag.putUUID("placer", placerID);
		tag.putFloat("tnt", tnt);
		tag.putFloat("nuke", nuke);
		tag.putFloat("hydro", hydro);
		tag.putFloat("bale", bale);
		tag.putFloat("dirty", dirty);
		tag.putFloat("schrab", schrab);
		tag.putFloat("sol", sol);
		tag.putFloat("euph", euph);
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		if (tag.hasUUID("placer")) placerID = tag.getUUID("placer");
		tnt = tag.getFloat("tnt");
		nuke = tag.getFloat("nuke");
		hydro = tag.getFloat("hydro");
		bale = tag.getFloat("bale");
		dirty = tag.getFloat("dirty");
		schrab = tag.getFloat("schrab");
		sol = tag.getFloat("sol");
		euph = tag.getFloat("euph");
	}
}
