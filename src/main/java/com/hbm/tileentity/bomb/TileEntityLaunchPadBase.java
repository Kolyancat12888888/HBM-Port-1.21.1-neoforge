package com.hbm.tileentity.bomb;

import com.hbm.api.entity.IThrowable;
import com.hbm.api.item.IDesignatorItem;
import com.hbm.entity.ModEntities;
import com.hbm.entity.missile.*;
import com.hbm.entity.missile.EntityMissileTier0.*;
import com.hbm.entity.missile.EntityMissileTier1.*;
import com.hbm.entity.missile.EntityMissileTier2.*;
import com.hbm.entity.missile.EntityMissileTier3.*;
import com.hbm.entity.missile.EntityMissileTier4.*;
import com.hbm.interfaces.IBomb.BombReturnCode;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemMissileStandard;
import com.hbm.items.weapon.ItemMissileStandard.MissileFuel;
import com.hbm.tileentity.TileEntityMachineBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class TileEntityLaunchPadBase extends TileEntityMachineBase {

	public static final Map<Item, MissileFactory> missiles = new HashMap<>();

	@FunctionalInterface
	public interface MissileFactory {
		EntityMissileBaseNT create(Level level, double x, double y, double z, double targetX, double targetZ);
	}

	public static void registerLaunchables() {
		if (!missiles.isEmpty()) return;

		// Tier 0
		missiles.put(ModItems.MISSILE_MICRO.get(), (lvl, x, y, z, tx, tz) -> new EntityMissileMicro(lvl, (float)x, (float)y, (float)z, (int)tx, (int)tz));
		missiles.put(ModItems.MISSILE_SCHRABIDIUM.get(), (lvl, x, y, z, tx, tz) -> new EntityMissileSchrabidium(lvl, (float)x, (float)y, (float)z, (int)tx, (int)tz));
		missiles.put(ModItems.MISSILE_BHOLE.get(), (lvl, x, y, z, tx, tz) -> new EntityMissileBHole(lvl, (float)x, (float)y, (float)z, (int)tx, (int)tz));
		missiles.put(ModItems.MISSILE_TAINT.get(), (lvl, x, y, z, tx, tz) -> new EntityMissileTaint(lvl, (float)x, (float)y, (float)z, (int)tx, (int)tz));
		missiles.put(ModItems.MISSILE_EMP.get(), (lvl, x, y, z, tx, tz) -> new EntityMissileEMP(lvl, (float)x, (float)y, (float)z, (int)tx, (int)tz));

		// Tier 1
		missiles.put(ModItems.MISSILE_GENERIC.get(), (lvl, x, y, z, tx, tz) -> new EntityMissileGeneric(lvl, (float)x, (float)y, (float)z, (int)tx, (int)tz));
		missiles.put(ModItems.MISSILE_DECOY.get(), (lvl, x, y, z, tx, tz) -> new EntityMissileDecoy(lvl, (float)x, (float)y, (float)z, (int)tx, (int)tz));
		missiles.put(ModItems.MISSILE_INCENDIARY.get(), (lvl, x, y, z, tx, tz) -> new EntityMissileIncendiary(lvl, (float)x, (float)y, (float)z, (int)tx, (int)tz));
		missiles.put(ModItems.MISSILE_CLUSTER.get(), (lvl, x, y, z, tx, tz) -> new EntityMissileCluster(lvl, (float)x, (float)y, (float)z, (int)tx, (int)tz));
		missiles.put(ModItems.MISSILE_BUSTER.get(), (lvl, x, y, z, tx, tz) -> new EntityMissileBunkerBuster(lvl, (float)x, (float)y, (float)z, (int)tx, (int)tz));

		// Tier 2
		missiles.put(ModItems.MISSILE_STRONG.get(), (lvl, x, y, z, tx, tz) -> new EntityMissileStrong(lvl, (float)x, (float)y, (float)z, (int)tx, (int)tz));
		missiles.put(ModItems.MISSILE_INCENDIARY_STRONG.get(), (lvl, x, y, z, tx, tz) -> new EntityMissileIncendiaryStrong(lvl, (float)x, (float)y, (float)z, (int)tx, (int)tz));
		missiles.put(ModItems.MISSILE_CLUSTER_STRONG.get(), (lvl, x, y, z, tx, tz) -> new EntityMissileClusterStrong(lvl, (float)x, (float)y, (float)z, (int)tx, (int)tz));
		missiles.put(ModItems.MISSILE_BUSTER_STRONG.get(), (lvl, x, y, z, tx, tz) -> new EntityMissileBusterStrong(lvl, (float)x, (float)y, (float)z, (int)tx, (int)tz));
		missiles.put(ModItems.MISSILE_EMP_STRONG.get(), (lvl, x, y, z, tx, tz) -> new EntityMissileEMPStrong(lvl, (float)x, (float)y, (float)z, (int)tx, (int)tz));

		// Tier 3
		missiles.put(ModItems.MISSILE_BURST.get(), (lvl, x, y, z, tx, tz) -> new EntityMissileBurst(lvl, (float)x, (float)y, (float)z, (int)tx, (int)tz));
		missiles.put(ModItems.MISSILE_INFERNO.get(), (lvl, x, y, z, tx, tz) -> new EntityMissileInferno(lvl, (float)x, (float)y, (float)z, (int)tx, (int)tz));
		missiles.put(ModItems.MISSILE_RAIN.get(), (lvl, x, y, z, tx, tz) -> new EntityMissileRain(lvl, (float)x, (float)y, (float)z, (int)tx, (int)tz));
		missiles.put(ModItems.MISSILE_DRILL.get(), (lvl, x, y, z, tx, tz) -> new EntityMissileDrill(lvl, (float)x, (float)y, (float)z, (int)tx, (int)tz));
		missiles.put(ModItems.MISSILE_SHUTTLE.get(), (lvl, x, y, z, tx, tz) -> new EntityMissileShuttle(lvl, (float)x, (float)y, (float)z, (int)tx, (int)tz));

		// Tier 4
		missiles.put(ModItems.MISSILE_NUCLEAR.get(), (lvl, x, y, z, tx, tz) -> new EntityMissileNuclear(lvl, (float)x, (float)y, (float)z, (int)tx, (int)tz));
		missiles.put(ModItems.MISSILE_NUCLEAR_CLUSTER.get(), (lvl, x, y, z, tx, tz) -> new EntityMissileMirv(lvl, (float)x, (float)y, (float)z, (int)tx, (int)tz));
		missiles.put(ModItems.MISSILE_VOLCANO.get(), (lvl, x, y, z, tx, tz) -> new EntityMissileVolcano(lvl, (float)x, (float)y, (float)z, (int)tx, (int)tz));
		missiles.put(ModItems.MISSILE_DOOMSDAY.get(), (lvl, x, y, z, tx, tz) -> new EntityMissileDoomsday(lvl, (float)x, (float)y, (float)z, (int)tx, (int)tz));
		missiles.put(ModItems.MISSILE_N2.get(), (lvl, x, y, z, tx, tz) -> new EntityMissileN2(lvl, (float)x, (float)y, (float)z, (int)tx, (int)tz));
		missiles.put(ModItems.MISSILE_STEALTH.get(), (lvl, x, y, z, tx, tz) -> new EntityMissileStealth(lvl, (float)x, (float)y, (float)z, (int)tx, (int)tz));
	}

	public long power = 0;
	public final long maxPower = 100_000;

	public int prevRedstonePower;
	public int redstonePower;
	public Set<BlockPos> activatedBlocks = new HashSet<>();

	public int state = 0;
	public static final int STATE_MISSING = 0;
	public static final int STATE_LOADING = 1;
	public static final int STATE_READY = 2;

	public int tank0Amount = 0;
	public int tank1Amount = 0;
	public int maxTank = 24_000;
	public String tank0Type = "none";
	public String tank1Type = "none";

	public TileEntityLaunchPadBase(BlockEntityType<?> type, BlockPos pos, BlockState state, int slotCount) {
		super(type, pos, state, slotCount);
		registerLaunchables();
	}

	@Override
	public String getDefaultName() {
		return "container.launchPad";
	}

	public void updateRedstonePower(BlockPos fromPos) {
		if (level == null) return;
		boolean powered = level.hasNeighborSignal(fromPos);
		boolean contained = activatedBlocks.contains(fromPos);
		if (!contained && powered) {
			activatedBlocks.add(fromPos);
			if (redstonePower == -1) redstonePower = 0;
			redstonePower++;
		} else if (contained && !powered) {
			activatedBlocks.remove(fromPos);
			redstonePower--;
			if (redstonePower == 0) redstonePower = -1;
		}
	}

	public void updateBase() {
		if (level == null || level.isClientSide) return;

		registerLaunchables();

		if (this.redstonePower > 0 && this.prevRedstonePower <= 0) {
			this.launchFromDesignator();
		}

		this.prevRedstonePower = this.redstonePower;

		if (this.isMissileValid()) {
			if (inventory.getStackInSlot(0).getItem() instanceof ItemMissileStandard missile) {
				setFuel(missile);
			}
		}

		this.markChanged();
	}

	public void setFuel(ItemMissileStandard missile) {
		switch (missile.fuel) {
			case ETHANOL_PEROXIDE -> {
				tank0Type = "ethanol";
				tank1Type = "peroxide";
			}
			case KEROSENE_PEROXIDE -> {
				tank0Type = "kerosene";
				tank1Type = "peroxide";
			}
			case KEROSENE_LOXY -> {
				tank0Type = "kerosene";
				tank1Type = "oxygen";
			}
			case JETFUEL_LOXY -> {
				tank0Type = "kerosene_reform";
				tank1Type = "oxygen";
			}
			default -> {
				tank0Type = "none";
				tank1Type = "none";
			}
		}
	}

	public boolean isMissileValid() {
		return !inventory.getStackInSlot(0).isEmpty() && isMissileValid(inventory.getStackInSlot(0));
	}

	public boolean isMissileValid(ItemStack stack) {
		return stack.getItem() instanceof ItemMissileStandard missile && missile.launchable;
	}

	public boolean hasFuel() {
		if (this.power < 75_000) return false;

		if (!inventory.getStackInSlot(0).isEmpty() && inventory.getStackInSlot(0).getItem() instanceof ItemMissileStandard missile) {
			if (missile.fuel == MissileFuel.SOLID) return true;
			if (this.tank0Amount < missile.fuelCap) return false;
			if (this.tank1Amount < missile.fuelCap) return false;
			return true;
		}

		return false;
	}

	public Entity instantiateMissile(double targetX, double targetZ) {
		if (level == null) return null;
		ItemStack stack = inventory.getStackInSlot(0);
		if (stack.isEmpty()) return null;

		if (stack.getItem() == ModItems.MISSILE_ANTI_BALLISTIC.get()) {
			EntityMissileAntiBallistic missile = new EntityMissileAntiBallistic(ModEntities.MISSILE_ANTI_BALLISTIC.get(), level);
			missile.setPos(getBlockPos().getX() + 0.5D, getBlockPos().getY() + getLaunchOffset(), getBlockPos().getZ() + 0.5D);
			return missile;
		}

		MissileFactory factory = missiles.get(stack.getItem());
		if (factory == null) return null;

		return factory.create(level, getBlockPos().getX() + 0.5D, getBlockPos().getY() + getLaunchOffset(), getBlockPos().getZ() + 0.5D, targetX, targetZ);
	}

	public void finalizeLaunch(Entity missile) {
		if (level == null || level.isClientSide) return;

		level.addFreshEntity(missile);
		level.playSound(null, getBlockPos().getX() + 0.5, getBlockPos().getY(), getBlockPos().getZ() + 0.5, SoundEvents.GENERIC_EXPLODE.value(), SoundSource.BLOCKS, 2.0F, 1.0F);

		this.power -= 75_000;

		if (!inventory.getStackInSlot(0).isEmpty() && inventory.getStackInSlot(0).getItem() instanceof ItemMissileStandard item) {
			if (item.fuel != MissileFuel.SOLID) {
				tank0Amount = Math.max(0, tank0Amount - item.fuelCap);
				tank1Amount = Math.max(0, tank1Amount - item.fuelCap);
			}
		}

		this.inventory.getStackInSlot(0).shrink(1);
		this.markChanged();
	}

	public BombReturnCode launchFromDesignator() {
		if (!canLaunch()) return BombReturnCode.ERROR_MISSING_COMPONENT;

		boolean needsDesignator = needsDesignator(inventory.getStackInSlot(0).getItem());

		double targetX = 0;
		double targetZ = 0;

		if (!inventory.getStackInSlot(1).isEmpty() && inventory.getStackInSlot(1).getItem() instanceof IDesignatorItem designator) {
			if (!designator.isReady(level, inventory.getStackInSlot(1), getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ()) && needsDesignator) {
				return BombReturnCode.ERROR_MISSING_COMPONENT;
			}
			Vec3 coords = designator.getCoords(level, inventory.getStackInSlot(1), getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ());
			targetX = Math.floor(coords.x);
			targetZ = Math.floor(coords.z);
		} else {
			if (needsDesignator) return BombReturnCode.ERROR_MISSING_COMPONENT;
		}

		return this.launchToCoordinate(targetX, targetZ);
	}

	public BombReturnCode launchToEntity(Entity entity) {
		if (!canLaunch() || entity == null) return BombReturnCode.ERROR_MISSING_COMPONENT;

		Entity e = instantiateMissile(Math.floor(entity.getX()), Math.floor(entity.getZ()));
		if (e != null) {
			if (e instanceof EntityMissileAntiBallistic abm) {
				abm.tracking = entity;
			}
			finalizeLaunch(e);
			return BombReturnCode.LAUNCHED;
		}
		return BombReturnCode.ERROR_MISSING_COMPONENT;
	}

	public BombReturnCode launchToCoordinate(double targetX, double targetZ) {
		if (!canLaunch()) return BombReturnCode.ERROR_MISSING_COMPONENT;

		Entity e = instantiateMissile(targetX, targetZ);
		if (e != null) {
			finalizeLaunch(e);
			return BombReturnCode.LAUNCHED;
		}
		return BombReturnCode.ERROR_MISSING_COMPONENT;
	}

	public boolean needsDesignator(Item item) {
		return item != ModItems.MISSILE_ANTI_BALLISTIC.get();
	}

	public boolean canLaunch() {
		return this.isMissileValid() && this.hasFuel() && this.isReadyForLaunch();
	}

	public abstract boolean isReadyForLaunch();
	public abstract double getLaunchOffset();

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		tag.putLong("power", power);
		tag.putInt("state", state);
		tag.putInt("tank0Amount", tank0Amount);
		tag.putInt("tank1Amount", tank1Amount);
		tag.putString("tank0Type", tank0Type);
		tag.putString("tank1Type", tank1Type);
		tag.putInt("redstonePower", redstonePower);
		tag.putInt("prevRedstonePower", prevRedstonePower);
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		power = tag.getLong("power");
		state = tag.getInt("state");
		tank0Amount = tag.getInt("tank0Amount");
		tank1Amount = tag.getInt("tank1Amount");
		tank0Type = tag.getString("tank0Type");
		tank1Type = tag.getString("tank1Type");
		redstonePower = tag.getInt("redstonePower");
		prevRedstonePower = tag.getInt("prevRedstonePower");
	}
}
