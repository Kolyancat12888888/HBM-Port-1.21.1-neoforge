package com.hbm.tileentity.machine;

import com.hbm.tileentity.ModBlockEntities;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.util.ContaminationUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityZirnoxDestroyed extends TileEntityLoadedBase {

    public boolean onFire = true;

    public TileEntityZirnoxDestroyed(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ZIRNOX_DESTROYED.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, TileEntityZirnoxDestroyed destroyed) {
        if (level.isClientSide()) return;

        if (level.getGameTime() % 20 == 0) {
            float rads = destroyed.onFire ? 500_000F : 75_000F;
            ContaminationUtil.radiate(level, pos.getX(), pos.getY(), pos.getZ(), 60, rads);
        }

        if (level.random.nextInt(5000) == 0) {
            destroyed.onFire = false;
            destroyed.markChanged();
        }
    }

    @Override
    protected void saveAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.saveAdditional(nbt, registries);
        nbt.putBoolean("onFire", onFire);
    }

    @Override
    protected void loadAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.loadAdditional(nbt, registries);
        onFire = nbt.getBoolean("onFire");
    }
}
