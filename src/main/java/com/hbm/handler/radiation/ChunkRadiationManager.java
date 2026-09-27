package com.hbm.handler.radiation;

import com.hbm.saveddata.RadiationSavedData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;

public final class ChunkRadiationManager {
    public static final ProxyClass proxy = new ProxyClass();

    public static final class ProxyClass {
        public double getRadiation(Level world, BlockPos pos) {
            if (world.isClientSide() || pos == null) return 0.0;
            RadiationSavedData data = RadiationSavedData.get(world);
            if (data == null) return 0.0;
            long chunkKey = ChunkPos.asLong(pos.getX() >> 4, pos.getZ() >> 4);
            return data.getRad(chunkKey);
        }

        public void setRadiation(Level world, BlockPos pos, double rad) {
            if (world.isClientSide() || pos == null) return;
            RadiationSavedData data = RadiationSavedData.get(world);
            if (data == null) return;
            long chunkKey = ChunkPos.asLong(pos.getX() >> 4, pos.getZ() >> 4);
            data.setRad(chunkKey, rad);
        }

        public void incrementRad(Level world, BlockPos pos, double rad) {
            incrementRad(world, pos, rad, Double.MAX_VALUE);
        }

        public void incrementRad(Level world, BlockPos pos, double rad, double max) {
            if (world.isClientSide() || pos == null) return;
            RadiationSavedData data = RadiationSavedData.get(world);
            if (data == null) return;
            long chunkKey = ChunkPos.asLong(pos.getX() >> 4, pos.getZ() >> 4);
            data.incrementRad(chunkKey, rad, max);
        }

        public void decrementRad(Level world, BlockPos pos, double rad) {
            if (world.isClientSide() || pos == null) return;
            RadiationSavedData data = RadiationSavedData.get(world);
            if (data == null) return;
            long chunkKey = ChunkPos.asLong(pos.getX() >> 4, pos.getZ() >> 4);
            data.decrementRad(chunkKey, rad);
        }

        public void clearSystem(Level world) {
            if (world.isClientSide()) return;
            RadiationSavedData data = RadiationSavedData.get(world);
            if (data != null) {
                data.clear();
            }
        }
    }
}
