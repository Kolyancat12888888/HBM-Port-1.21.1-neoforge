package com.hbm.handler.radiation;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.config.RadiationConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;

import java.util.Random;

public class RadiationWorldHandler {

    private static final Random RANDOM = new Random();

    public static void decayChunkRandom(ServerLevel level, int cx, int cz, double rad) {
        if (!RadiationConfig.worldRadEffects || !GeneralConfig.enableRads) return;
        LevelChunk chunk = level.getChunk(cx, cz);
        if (chunk == null) return;

        int iterations = Math.min(32, (int) (rad / 10.0));
        int baseX = cx << 4;
        int baseZ = cz << 4;

        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

        for (int i = 0; i < iterations; i++) {
            int lx = RANDOM.nextInt(16);
            int lz = RANDOM.nextInt(16);
            int topY = level.getHeight(net.minecraft.world.level.levelgen.Heightmap.Types.WORLD_SURFACE, baseX + lx, baseZ + lz);
            if (topY <= level.getMinBuildHeight()) continue;

            pos.set(baseX + lx, topY - 1, baseZ + lz);
            BlockState state = level.getBlockState(pos);
            decayBlock(level, pos, state);
        }
    }

    public static void decayBlock(Level world, BlockPos pos, BlockState state) {
        if (state.isAir()) return;

        if (state.is(Blocks.GRASS_BLOCK)) {
            world.setBlock(pos, ModBlocks.waste_earth.defaultBlockState(), 2);
            return;
        }
        if (state.is(Blocks.SHORT_GRASS) || state.is(Blocks.FERN)) {
            world.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
            return;
        }
        if (state.is(Blocks.TALL_GRASS) || state.is(Blocks.LARGE_FERN)) {
            world.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
            return;
        }
        if (state.is(Blocks.MYCELIUM)) {
            world.setBlock(pos, ModBlocks.waste_mycelium.defaultBlockState(), 2);
            return;
        }
        if (state.is(Blocks.SAND)) {
            world.setBlock(pos, ModBlocks.waste_trinitite.defaultBlockState(), 2);
            return;
        }
        if (state.is(Blocks.RED_SAND)) {
            world.setBlock(pos, ModBlocks.waste_trinitite_red.defaultBlockState(), 2);
            return;
        }
        if (state.is(BlockTags.LEAVES) && !state.is(ModBlocks.waste_leaves)) {
            if (RANDOM.nextInt(7) <= 5) {
                world.setBlock(pos, ModBlocks.waste_leaves.defaultBlockState(), 2);
            } else {
                world.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
            }
        }
    }
}
