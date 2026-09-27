package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.UpgradeManagerNT;
import com.hbm.inventory.recipes.CentrifugeRecipes;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.tileentity.IUpgradeInfoProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.List;

public class TileEntityMachineCentrifuge extends TileEntityMachineBase implements IUpgradeInfoProvider {

	public static int maxPower = 100_000;
	public static int processingSpeed = 200;
	public static int baseConsumption = 200;

	public final UpgradeManagerNT upgradeManager;
	public int progress;
	public long power;
	public boolean isProgressing;

	private static final HashMap<UpgradeType, Integer> validUpgrades = new HashMap<>();
	static {
		validUpgrades.put(UpgradeType.SPEED, 3);
		validUpgrades.put(UpgradeType.POWER, 3);
		validUpgrades.put(UpgradeType.OVERDRIVE, 3);
	}

	public TileEntityMachineCentrifuge(BlockPos pos, BlockState state) {
		super(com.hbm.tileentity.ModBlockEntities.CENTRIFUGE.get(), pos, state, 8);
		this.upgradeManager = new UpgradeManagerNT(this);
	}

	@Override
	public String getDefaultName() {
		return "container.centrifuge";
	}

	public static void tick(Level level, BlockPos pos, BlockState state, TileEntityMachineCentrifuge te) {
		if (level == null || level.isClientSide) return;

		te.upgradeManager.checkSlots(te.inventory, 6, 7);

		int speed = 1;
		int consumption = baseConsumption;

		speed += te.upgradeManager.getLevel(UpgradeType.SPEED);
		consumption += te.upgradeManager.getLevel(UpgradeType.SPEED) * baseConsumption;

		speed *= (1 + te.upgradeManager.getLevel(UpgradeType.OVERDRIVE) * 5);
		consumption += te.upgradeManager.getLevel(UpgradeType.OVERDRIVE) * baseConsumption * 50;

		consumption /= (1 + te.upgradeManager.getLevel(UpgradeType.POWER));

		if (te.hasPower() && te.isProcessing()) {
			te.power -= consumption;
			if (te.power < 0) te.power = 0;
		}

		te.isProgressing = te.hasPower() && te.canProcess();

		if (te.isProgressing) {
			te.progress += speed;

			if (te.progress >= processingSpeed) {
				te.progress = 0;
				te.processItem();
			}
			te.markChanged();
		} else {
			if (te.progress > 0) {
				te.progress = 0;
				te.markChanged();
			}
		}
	}

	public boolean hasPower() {
		return power > 0;
	}

	public boolean isProcessing() {
		return this.progress > 0;
	}

	public boolean canProcess() {
		ItemStack in = inventory.getStackInSlot(0);
		if (in.isEmpty()) return false;

		ItemStack[] out = CentrifugeRecipes.getOutput(in);
		if (out == null) return false;

		for (int i = 0; i < Math.min(4, out.length); i++) {
			if (out[i] == null || out[i].isEmpty()) continue;
			ItemStack existing = inventory.getStackInSlot(i + 2);
			if (existing.isEmpty()) continue;
			if (!ItemStack.isSameItemSameComponents(existing, out[i])) return false;
			if (existing.getCount() + out[i].getCount() > existing.getMaxStackSize()) return false;
		}
		return true;
	}

	private void processItem() {
		ItemStack in = inventory.getStackInSlot(0);
		ItemStack[] out = CentrifugeRecipes.getOutput(in);

		if (out != null) {
			for (int i = 0; i < Math.min(4, out.length); i++) {
				if (out[i] != null && !out[i].isEmpty()) {
					ItemStack existing = inventory.getStackInSlot(i + 2);
					if (existing.isEmpty()) {
						inventory.setStackInSlot(i + 2, out[i].copy());
					} else if (ItemStack.isSameItemSameComponents(existing, out[i])) {
						existing.grow(out[i].getCount());
					}
				}
			}
		}

		inventory.extractItem(0, 1, false);
		this.markChanged();
	}

	public int getCentrifugeProgressScaled(int i) {
		return (progress * i) / processingSpeed;
	}

	public long getPowerRemainingScaled(int i) {
		return (power * i) / maxPower;
	}

	@Override
	public boolean canProvideInfo(UpgradeType type, int level, boolean extendedInfo) {
		return validUpgrades.containsKey(type);
	}

	@Override
	public void provideInfo(UpgradeType type, int level, List<String> info, boolean extendedInfo) {
		info.add("Centrifuge Upgrade: " + type.name());
	}

	@Override
	public HashMap<UpgradeType, Integer> getValidUpgrades() {
		return validUpgrades;
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
