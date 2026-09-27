package com.hbm.tileentity.machine.rbmk;

import com.hbm.blocks.ModBlocks;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.tileentity.machine.rbmk.RBMKColumn.ColumnType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

public abstract class TileEntityRBMKBase extends TileEntityLoadedBase {

	public double heat = 20.0D;
	public int reasimWater;
	public static final int maxWater = 16000;
	public int reasimSteam;
	public static final int maxSteam = 16000;
	public int craneIndicator;

	public static boolean explodeOnBroken = true;

	public TileEntityRBMKBase(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	public boolean hasLid() {
		return true;
	}

	public boolean isLidRemovable() {
		return true;
	}

	public double maxHeat() {
		return 1500D;
	}

	public double passiveCooling(int neighbors) {
		double min = RBMKDials.getPassiveCoolingInner(level);
		double max = RBMKDials.getPassiveCooling(level);
		return min + (max - min) * ((4 - Mth.clamp(neighbors, 0, 4)) / 4D);
	}

	public static void tick(Level level, BlockPos pos, BlockState state, TileEntityRBMKBase blockEntity) {
		if (level.isClientSide()) return;
		if (blockEntity.craneIndicator > 0) blockEntity.craneIndicator--;

		blockEntity.moveHeat();
		if (RBMKDials.getReasimBoilers(level)) {
			blockEntity.boilWater();
		}
		blockEntity.updateRBMK();
	}

	public void updateRBMK() {
	}

	private void boilWater() {
		if (heat < 100D) return;

		double heatConsumption = RBMKDials.getBoilerHeatConsumption(level);
		double availableHeat = (this.heat - 100) / heatConsumption;
		double availableWater = this.reasimWater;
		double availableSpace = maxSteam - this.reasimSteam;

		int processedWater = (int) Math.floor(Math.min(availableHeat, Math.min(availableWater, availableSpace)) * Mth.clamp((float) RBMKDials.getReaSimBoilerSpeed(level), 0.0F, 1.0F));
		if (processedWater <= 0) return;

		this.reasimWater -= processedWater;
		this.reasimSteam += processedWater;
		this.heat -= processedWater * heatConsumption;
	}

	public static final Direction[] heatDirs = new Direction[]{
			Direction.NORTH,
			Direction.EAST,
			Direction.SOUTH,
			Direction.WEST
	};

	protected TileEntityRBMKBase[] neighbourCache = new TileEntityRBMKBase[4];

	private void moveHeat() {
		if (level == null) return;
		boolean reasim = RBMKDials.getReasimBoilers(level);

		double heatTot = this.heat;
		int waterTot = this.reasimWater;
		int steamTot = this.reasimSteam;
		int members = 1;

		int index = 0;
		for (Direction dir : heatDirs) {
			if (neighbourCache[index] != null && !neighbourCache[index].isLoaded())
				neighbourCache[index] = null;

			if (neighbourCache[index] == null) {
				BlockEntity be = level.getBlockEntity(worldPosition.relative(dir));
				if (be instanceof TileEntityRBMKBase base) {
					neighbourCache[index] = base;
				}
			}

			TileEntityRBMKBase neighbor = neighbourCache[index];
			if (neighbor != null) {
				members++;
				heatTot += neighbor.heat;
				if (reasim) {
					waterTot += neighbor.reasimWater;
					steamTot += neighbor.reasimSteam;
				}
			}
			index++;
		}

		double stepSize = RBMKDials.getColumnHeatFlow(level);
		if (members > 1) {
			double targetHeat = heatTot / (double) members;

			int tWater = 0;
			int rWater = 0;
			int tSteam = 0;
			int rSteam = 0;

			if (reasim) {
				tWater = waterTot / members;
				rWater = waterTot % members;
				tSteam = steamTot / members;
				rSteam = steamTot % members;
			}

			for (TileEntityRBMKBase neighbor : neighbourCache) {
				if (neighbor != null) {
					double diff = targetHeat - neighbor.heat;
					neighbor.heat += diff * stepSize;

					if (reasim) {
						neighbor.reasimWater = tWater;
						neighbor.reasimSteam = tSteam;
						if (rWater > 0) { neighbor.reasimWater++; rWater--; }
						if (rSteam > 0) { neighbor.reasimSteam++; rSteam--; }
					}
					neighbor.markChanged();
				}
			}

			double diff = targetHeat - this.heat;
			this.heat += diff * stepSize;

			if (reasim) {
				this.reasimWater = tWater;
				this.reasimSteam = tSteam;
				if (rWater > 0) this.reasimWater += rWater;
				if (rSteam > 0) this.reasimSteam += rSteam;
			}

			this.markChanged();
		}

		this.heat -= passiveCooling(members - 1);
		if (this.heat < 20.0D) this.heat = 20.0D;
	}

	public void onMelt(int reduce) {
		standardMelt(reduce);
	}

	protected void standardMelt(int reduce) {
		if (level == null) return;
		int h = RBMKDials.getColumnHeight(level);
		reduce = Mth.clamp(reduce, 1, h);

		if (level.random.nextInt(3) == 0)
			reduce++;

		for (int i = h; i >= 0; i--) {
			BlockPos targetPos = worldPosition.above(i);
			if (i <= h + 1 - reduce) {
				level.setBlockAndUpdate(targetPos, ModBlocks.RBMK_DEBRIS.get().defaultBlockState());
			} else {
				level.setBlockAndUpdate(targetPos, Blocks.AIR.defaultBlockState());
			}
		}
	}

	public static Set<TileEntityRBMKBase> columns = new HashSet<>();

	public void meltdown() {
		if (level == null || level.isClientSide()) return;

		columns.clear();
		getFF(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ());

		int minX = worldPosition.getX();
		int maxX = worldPosition.getX();
		int minZ = worldPosition.getZ();
		int maxZ = worldPosition.getZ();

		for (TileEntityRBMKBase rbmk : columns) {
			if (rbmk.worldPosition.getX() < minX) minX = rbmk.worldPosition.getX();
			if (rbmk.worldPosition.getX() > maxX) maxX = rbmk.worldPosition.getX();
			if (rbmk.worldPosition.getZ() < minZ) minZ = rbmk.worldPosition.getZ();
			if (rbmk.worldPosition.getZ() > maxZ) maxZ = rbmk.worldPosition.getZ();
		}

		for (TileEntityRBMKBase rbmk : columns) {
			int distFromMinX = rbmk.worldPosition.getX() - minX;
			int distFromMaxX = maxX - rbmk.worldPosition.getX();
			int distFromMinZ = rbmk.worldPosition.getZ() - minZ;
			int distFromMaxZ = maxZ - rbmk.worldPosition.getZ();
			int minDist = Math.min(distFromMinX, Math.min(distFromMaxX, Math.min(distFromMinZ, distFromMaxZ)));
			rbmk.onMelt(minDist + 1);
		}

		int avgX = minX + (maxX - minX) / 2;
		int avgZ = minZ + (maxZ - minZ) / 2;

		level.explode(null, avgX + 0.5, worldPosition.getY() + 1, avgZ + 0.5, 25.0F, Level.ExplosionInteraction.BLOCK);
		com.hbm.util.ContaminationUtil.radiate(level, avgX + 0.5, worldPosition.getY() + 1, avgZ + 0.5, 60, 5000.0F);

		level.playSound(null, avgX + 0.5, worldPosition.getY() + 1, avgZ + 0.5, SoundEvents.GENERIC_EXPLODE.value(), SoundSource.BLOCKS, 50.0F, 0.7F);
		columns.clear();
	}

	private void getFF(int x, int y, int z) {
		Queue<BlockPos> queue = new ArrayDeque<>();
		queue.add(new BlockPos(x, y, z));
		int safetyLimit = 50000;

		while (!queue.isEmpty() && safetyLimit > 0) {
			safetyLimit--;
			BlockPos current = queue.poll();
			if (!level.isLoaded(current)) continue;

			BlockEntity te = level.getBlockEntity(current);
			if (te instanceof TileEntityRBMKBase rbmk) {
				if (!columns.contains(rbmk)) {
					columns.add(rbmk);
					queue.add(current.east());
					queue.add(current.west());
					queue.add(current.south());
					queue.add(current.north());
				}
			}
		}
	}

	public boolean isModerated() {
		return false;
	}

	public abstract ColumnType getConsoleType();

	public RBMKColumn getConsoleData() {
		RBMKColumn col = RBMKColumn.createForType(getConsoleType());
		col.heat = this.heat;
		col.maxHeat = this.maxHeat();
		col.moderated = this.isModerated();
		col.reasimWater = this.reasimWater;
		col.reasimSteam = this.reasimSteam;
		col.indicator = this.craneIndicator;
		return col;
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		tag.putDouble("heat", heat);
		tag.putInt("realSimWater", reasimWater);
		tag.putInt("realSimSteam", reasimSteam);
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		heat = tag.getDouble("heat");
		reasimWater = tag.getInt("realSimWater");
		reasimSteam = tag.getInt("realSimSteam");
	}
}
