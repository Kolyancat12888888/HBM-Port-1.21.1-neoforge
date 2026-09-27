package com.hbm.tileentity.machine;

import com.hbm.api.energymk2.IBatteryItem;
import com.hbm.tileentity.ModBlockEntities;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.tileentity.network.energy.TileEntityCableBaseNT;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityMachineBattery extends TileEntityMachineBase {

	public long power;
	public long maxPower = 1_000_000;

	public TileEntityMachineBattery(BlockPos pos, BlockState state) {
		super(ModBlockEntities.BATTERY.get(), pos, state, 2); // 0 charge slot (charge item from block), 1 discharge slot (discharge item into block)
	}

	@Override
	public String getDefaultName() {
		return "container.battery";
	}

	public static void tick(Level level, BlockPos pos, BlockState state, TileEntityMachineBattery te) {
		if (level == null || level.isClientSide) return;

		// 1. Discharge item in slot 1 into block buffer
		ItemStack dischargeStack = te.inventory.getStackInSlot(1);
		if (!dischargeStack.isEmpty() && dischargeStack.getItem() instanceof IBatteryItem batteryItem) {
			long itemCharge = batteryItem.getCharge(dischargeStack);
			if (itemCharge > 0 && te.power < te.maxPower) {
				long canTransfer = Math.min(itemCharge, Math.min(batteryItem.getDischargeRate(dischargeStack), te.maxPower - te.power));
				if (canTransfer > 0) {
					batteryItem.dischargeBattery(dischargeStack, canTransfer);
					te.power += canTransfer;
					te.markChanged();
				}
			}
		}

		// 2. Charge item in slot 0 from block buffer
		ItemStack chargeStack = te.inventory.getStackInSlot(0);
		if (!chargeStack.isEmpty() && chargeStack.getItem() instanceof IBatteryItem batteryItem) {
			long itemCharge = batteryItem.getCharge(chargeStack);
			long itemMax = batteryItem.getMaxCharge(chargeStack);
			if (itemCharge < itemMax && te.power > 0) {
				long canTransfer = Math.min(te.power, Math.min(batteryItem.getChargeRate(chargeStack), itemMax - itemCharge));
				if (canTransfer > 0) {
					batteryItem.chargeBattery(chargeStack, canTransfer);
					te.power -= canTransfer;
					te.markChanged();
				}
			}
		}

		// 3. Distribute power to adjacent cables / machines
		if (te.power > 0) {
			for (Direction dir : Direction.values()) {
				BlockPos targetPos = pos.relative(dir);
				if (!level.hasChunkAt(targetPos)) continue;

				BlockEntity neighbor = level.getBlockEntity(targetPos);
				if (neighbor instanceof TileEntityCableBaseNT cable) {
					long space = cable.maxPower - cable.power;
					long transfer = Math.min(te.power, Math.min(cable.maxTransfer, space));
					if (transfer > 0) {
						te.power -= transfer;
						cable.power += transfer;
						cable.setChanged();
						te.markChanged();
					}
				}
			}
		}
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		tag.putLong("power", power);
		tag.putLong("maxPower", maxPower);
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		power = tag.getLong("power");
		if (tag.contains("maxPower")) {
			maxPower = tag.getLong("maxPower");
		}
	}
}
