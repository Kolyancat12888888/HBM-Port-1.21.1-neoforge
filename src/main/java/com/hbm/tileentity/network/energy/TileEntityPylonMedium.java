package com.hbm.tileentity.network.energy;

import com.hbm.tileentity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityPylonMedium extends TileEntityPylon {

    public TileEntityPylonMedium(BlockPos pos, BlockState state) {
        super(pos, state);
        this.maxPower = 25_000_000;
        this.maxTransfer = 2_500_000;
        this.range = 64;
    }
}
