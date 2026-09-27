package com.hbm.handler.radiation;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public final class ChunkRadiationManager {
    public static final ProxyClass proxy = new ProxyClass();

    public static final class ProxyClass {
        public double getRadiation(Level world, BlockPos pos) {
            return 0.0;
        }

        public void setRadiation(Level world, BlockPos pos, double rad) {
        }

        public void incrementRad(Level world, BlockPos pos, double rad) {
        }

        public void incrementRad(Level world, BlockPos pos, double rad, double max) {
        }
    }
}
