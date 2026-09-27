package com.hbm.tileentity.bomb;

import com.hbm.api.item.IDesignatorItem;
import com.hbm.entity.ModEntities;
import com.hbm.entity.missile.EntityMissileCustom;
import com.hbm.handler.MissileStruct;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemCustomMissile;
import com.hbm.items.weapon.ItemMissile;
import com.hbm.items.weapon.ItemMissile.FuelType;
import com.hbm.items.weapon.ItemMissile.PartSize;
import com.hbm.tileentity.ModBlockEntities;
import com.hbm.tileentity.TileEntityMachineBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class TileEntityCompactLauncher extends TileEntityMachineBase {

	public static final long maxPower = 100_000;
	public static final int maxSolid = 25_000;
	public static final int clearingDuration = 100;

	public long power = 0;
	public int solid = 0;
	public int tank0Amount = 0;
	public int tank1Amount = 0;
	public String tank0Type = "none";
	public String tank1Type = "none";
	public int clearingTimer = 0;

	public TileEntityCompactLauncher(BlockPos pos, BlockState state) {
		super(ModBlockEntities.COMPACT_LAUNCHER.get(), pos, state, 8);
	}

	@Override
	public String getDefaultName() {
		return "container.compactLauncher";
	}

	public static void tick(Level level, BlockPos pos, BlockState state, TileEntityCompactLauncher te) {
		if (level == null || level.isClientSide) return;

		if (te.clearingTimer > 0) te.clearingTimer--;

		te.updateTypes();

		ItemStack fuelStack = te.inventory.getStackInSlot(4);
		if (!fuelStack.isEmpty() && fuelStack.getItem() == ModItems.ROCKET_FUEL.get() && te.solid + 250 <= maxSolid) {
			fuelStack.shrink(1);
			te.solid += 250;
			te.markChanged();
		}

		if (te.canLaunch()) {
			for (int x = -1; x <= 1; x++) {
				for (int z = -1; z <= 1; z++) {
					if (level.hasNeighborSignal(pos.offset(x, 0, z))) {
						te.launch();
						return;
					}
				}
			}
		}
	}

	public boolean canLaunch() {
		return power >= maxPower * 0.75 && isMissileValid() && hasDesignator() && hasFuel() && clearingTimer == 0;
	}

	public void launch() {
		if (level == null || level.isClientSide) return;

		level.playSound(null, getBlockPos().getX() + 0.5, getBlockPos().getY(), getBlockPos().getZ() + 0.5, SoundEvents.GENERIC_EXPLODE.value(), SoundSource.BLOCKS, 10.0F, 1.0F);

		ItemStack designatorStack = inventory.getStackInSlot(1);
		double targetX = getBlockPos().getX();
		double targetZ = getBlockPos().getZ();

		if (designatorStack.getItem() instanceof IDesignatorItem designator) {
			Vec3 coords = designator.getCoords(level, designatorStack, getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ());
			targetX = Math.floor(coords.x);
			targetZ = Math.floor(coords.z);
		}

		MissileStruct struct = ItemCustomMissile.getStruct(inventory.getStackInSlot(0));
		EntityMissileCustom missile = new EntityMissileCustom(
				level,
				(float) (getBlockPos().getX() + 0.5),
				(float) (getBlockPos().getY() + 1.5),
				(float) (getBlockPos().getZ() + 0.5),
				(int) targetX,
				(int) targetZ,
				struct
		);

		level.addFreshEntity(missile);

		subtractFuel();
		clearingTimer = clearingDuration;
		inventory.setStackInSlot(0, ItemStack.EMPTY);
		markChanged();
	}

	private boolean hasFuel() {
		MissileStruct struct = ItemCustomMissile.getStruct(inventory.getStackInSlot(0));
		if (struct == null || struct.fuselage == null) return false;

		ItemMissile fuselage = struct.fuselage;
		float requiredFuel = (Float) fuselage.attributes[1];
		FuelType fuelType = (FuelType) fuselage.attributes[0];

		return switch (fuelType) {
			case SOLID -> this.solid >= requiredFuel;
			case KEROSENE, HYDROGEN, BALEFIRE -> this.tank0Amount >= requiredFuel && this.tank1Amount >= requiredFuel;
			case XENON -> this.tank0Amount >= requiredFuel;
			default -> false;
		};
	}

	private void subtractFuel() {
		MissileStruct struct = ItemCustomMissile.getStruct(inventory.getStackInSlot(0));
		if (struct == null || struct.fuselage == null) return;

		ItemMissile fuselage = struct.fuselage;
		int fuel = (int) (float) fuselage.attributes[1];

		switch ((FuelType) fuselage.attributes[0]) {
			case KEROSENE, HYDROGEN, BALEFIRE -> {
				tank0Amount = Math.max(0, tank0Amount - fuel);
				tank1Amount = Math.max(0, tank1Amount - fuel);
			}
			case XENON -> tank0Amount = Math.max(0, tank0Amount - fuel);
			case SOLID -> this.solid = Math.max(0, this.solid - fuel);
			default -> {}
		}
		this.power = Math.max(0, this.power - (long) (maxPower * 0.75));
	}

	public boolean isMissileValid() {
		MissileStruct struct = ItemCustomMissile.getStruct(inventory.getStackInSlot(0));
		if (struct == null || struct.fuselage == null) return false;
		return struct.fuselage.top == PartSize.SIZE_10;
	}

	public boolean hasDesignator() {
		ItemStack designator = inventory.getStackInSlot(1);
		if (!designator.isEmpty() && designator.getItem() instanceof IDesignatorItem des) {
			return des.isReady(level, designator, getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ());
		}
		return false;
	}

	public void updateTypes() {
		MissileStruct struct = ItemCustomMissile.getStruct(inventory.getStackInSlot(0));
		if (struct == null || struct.fuselage == null) {
			tank0Type = "none";
			tank1Type = "none";
			return;
		}

		ItemMissile fuselage = struct.fuselage;
		switch ((FuelType) fuselage.attributes[0]) {
			case KEROSENE -> {
				tank0Type = "kerosene";
				tank1Type = "peroxide";
			}
			case HYDROGEN -> {
				tank0Type = "hydrogen";
				tank1Type = "oxygen";
			}
			case XENON -> {
				tank0Type = "xenon";
				tank1Type = "none";
			}
			case BALEFIRE -> {
				tank0Type = "balefire";
				tank1Type = "peroxide";
			}
			default -> {
				tank0Type = "none";
				tank1Type = "none";
			}
		}
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		tag.putLong("power", power);
		tag.putInt("solid", solid);
		tag.putInt("tank0Amount", tank0Amount);
		tag.putInt("tank1Amount", tank1Amount);
		tag.putString("tank0Type", tank0Type);
		tag.putString("tank1Type", tank1Type);
		tag.putInt("clearingTimer", clearingTimer);
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		power = tag.getLong("power");
		solid = tag.getInt("solid");
		tank0Amount = tag.getInt("tank0Amount");
		tank1Amount = tag.getInt("tank1Amount");
		tank0Type = tag.getString("tank0Type");
		tank1Type = tag.getString("tank1Type");
		clearingTimer = tag.getInt("clearingTimer");
	}
}
