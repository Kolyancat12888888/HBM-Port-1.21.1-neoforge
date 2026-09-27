package com.hbm.tileentity.bomb;

import com.hbm.items.weapon.ItemMissileStandard;
import com.hbm.items.weapon.ItemMissileStandard.MissileFormFactor;
import com.hbm.tileentity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityLaunchPadLarge extends TileEntityLaunchPadBase {

	public int formFactor = -1;
	public boolean erected = false;
	public boolean readyToLoad = false;
	public boolean scheduleErect = false;
	public float lift = 1.0F;
	public float erector = 90.0F;
	public int delay = 20;

	public TileEntityLaunchPadLarge(BlockPos pos, BlockState state) {
		super(ModBlockEntities.LAUNCH_PAD_LARGE.get(), pos, state, 7);
	}

	@Override
	public boolean isReadyForLaunch() {
		return this.erected && this.readyToLoad;
	}

	@Override
	public double getLaunchOffset() {
		return 2.0D;
	}

	public static void tick(Level level, BlockPos pos, BlockState state, TileEntityLaunchPadLarge te) {
		if (level == null || level.isClientSide) return;

		float erectorSpeed = 1.5F;
		float liftSpeed = 0.025F;

		if (te.isMissileValid()) {
			if (te.inventory.getStackInSlot(0).getItem() instanceof ItemMissileStandard missile) {
				te.formFactor = missile.formFactor.ordinal();
				if (missile.formFactor == MissileFormFactor.ATLAS || missile.formFactor == MissileFormFactor.HUGE) {
					erectorSpeed /= 2.0F;
					liftSpeed /= 2.0F;
				}
			}

			if (te.erector == 90.0F && te.lift == 1.0F) {
				te.readyToLoad = true;
			}
		} else {
			te.readyToLoad = false;
			te.erected = false;
			te.delay = 20;
		}

		if (te.power >= 75_000) {
			if (te.delay > 0) {
				te.delay--;

				if (te.delay < 10 && te.scheduleErect) {
					te.erected = true;
					te.scheduleErect = false;
				}

				if (te.inventory.getStackInSlot(0).isEmpty() || !te.readyToLoad) {
					if (te.erector < 90.0F) {
						te.erector = Math.min(te.erector + erectorSpeed, 90.0F);
						if (te.erector == 90.0F) te.delay = 20;
					} else if (te.lift < 1.0F) {
						te.lift = Math.min(te.lift + liftSpeed, 1.0F);
						if (te.lift == 1.0F) {
							te.readyToLoad = true;
							te.delay = 20;
						}
					}
				}
			} else {
				if (!te.erected && te.readyToLoad) {
					te.state = STATE_LOADING;

					if (te.erector != 0.0F) {
						te.erector = Math.max(te.erector - erectorSpeed, 0.0F);
						if (te.erector == 0.0F) te.delay = 20;
					} else if (te.lift > 0.0F) {
						te.lift = Math.max(te.lift - liftSpeed, 0.0F);
						if (te.lift == 0.0F) {
							te.scheduleErect = true;
							te.delay = 20;
						}
					}
				} else {
					if (te.erector < 90.0F) {
						te.erector = Math.min(te.erector + erectorSpeed, 90.0F);
						if (te.erector == 90.0F) te.delay = 20;
					} else if (te.lift < 1.0F) {
						te.lift = Math.min(te.lift + liftSpeed, 1.0F);
						if (te.lift == 1.0F) {
							te.readyToLoad = true;
							te.delay = 20;
						}
					}
				}
			}
		}

		if (!te.hasFuel() || !te.isMissileValid()) te.state = STATE_MISSING;
		if (te.erected && te.canLaunch()) te.state = STATE_READY;

		te.updateBase();
	}

	@Override
	public void finalizeLaunch(Entity missile) {
		super.finalizeLaunch(missile);
		this.erected = false;
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		tag.putBoolean("erected", erected);
		tag.putBoolean("readyToLoad", readyToLoad);
		tag.putFloat("lift", lift);
		tag.putFloat("erector", erector);
		tag.putInt("formFactor", formFactor);
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		erected = tag.getBoolean("erected");
		readyToLoad = tag.getBoolean("readyToLoad");
		lift = tag.getFloat("lift");
		erector = tag.getFloat("erector");
		formFactor = tag.getInt("formFactor");
	}
}
