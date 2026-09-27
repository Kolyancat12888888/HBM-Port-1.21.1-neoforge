package com.hbm.handler.pollution;

import com.hbm.config.RadiationConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;

public class PollutionHandler {

    public static final Map<Level, PollutionPerWorld> perWorld = new HashMap<>();

    public static final float SOOT_PER_SECOND = 1F / 25F;
    public static final float HEAVY_METAL_PER_SECOND = 1F / 50F;
    public static final float POISON_PER_SECOND = 1F / 50F;

    public static void incrementPollution(Level world, BlockPos pos, PollutionType type, float amount) {
        if (!RadiationConfig.enablePollution || pos == null || world == null) return;

        PollutionPerWorld ppw = perWorld.computeIfAbsent(world, k -> new PollutionPerWorld());
        ChunkPos chPos = new ChunkPos(pos.getX() >> 6, pos.getZ() >> 6);
        PollutionData data = ppw.pollution.computeIfAbsent(chPos, k -> new PollutionData());
        data.pollution[type.ordinal()] += amount;
    }

    public static float getPollution(Level world, BlockPos pos, PollutionType type) {
        if (!RadiationConfig.enablePollution || pos == null || world == null) return 0;

        PollutionPerWorld ppw = perWorld.get(world);
        if (ppw == null) return 0;
        ChunkPos chPos = new ChunkPos(pos.getX() >> 6, pos.getZ() >> 6);
        PollutionData data = ppw.pollution.get(chPos);
        if (data == null) return 0;
        return data.pollution[type.ordinal()];
    }

    public static class PollutionPerWorld {
        public Map<ChunkPos, PollutionData> pollution = new HashMap<>();
    }

    public static class PollutionData {
        public float[] pollution = new float[PollutionType.VALUES.length];
    }

    public enum PollutionType {
        SOOT("trait.ptype.soot"),
        POISON("trait.ptype.poison"),
        HEAVYMETAL("trait.ptype.heavymetal"),
        FALLOUT("trait.ptype.fallout");

        public static final PollutionType[] VALUES = values();
        public final String name;

        PollutionType(String name) {
            this.name = name;
        }
    }
}
