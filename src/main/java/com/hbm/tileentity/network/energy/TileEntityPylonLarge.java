package com.hbm.tileentity.network.energy;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityPylonLarge extends TileEntityPylon {

    public TileEntityPylonLarge(BlockPos pos, BlockState state) {
        super(pos, state);
        this.maxPower = 100_000_000;
        this.maxTransfer = 10_000_000;
        this.range = 128;
    }
}
