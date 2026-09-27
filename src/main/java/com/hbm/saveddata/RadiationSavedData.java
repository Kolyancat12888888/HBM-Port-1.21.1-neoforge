package com.hbm.saveddata;

import com.hbm.config.GeneralConfig;
import com.hbm.config.RadiationConfig;
import com.hbm.handler.radiation.RadiationWorldHandler;
import it.unimi.dsi.fastutil.longs.Long2DoubleMap;
import it.unimi.dsi.fastutil.longs.Long2DoubleOpenHashMap;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;

public class RadiationSavedData extends SavedData {

    public static final String DATA_NAME = "hbm_radiation";

    public static final SavedData.Factory<RadiationSavedData> FACTORY = new SavedData.Factory<>(
            RadiationSavedData::new,
            RadiationSavedData::load,
            null
    );

    private final Long2DoubleOpenHashMap chunkRadMap = new Long2DoubleOpenHashMap();

    public RadiationSavedData() {
        chunkRadMap.defaultReturnValue(0.0);
    }

    public static RadiationSavedData get(Level level) {
        if (level instanceof ServerLevel serverLevel) {
            return serverLevel.getDataStorage().computeIfAbsent(FACTORY, DATA_NAME);
        }
        return null;
    }

    public static RadiationSavedData load(CompoundTag tag, HolderLookup.Provider provider) {
        RadiationSavedData data = new RadiationSavedData();
        if (tag.contains("chunks", Tag.TAG_LIST)) {
            ListTag list = tag.getList("chunks", Tag.TAG_COMPOUND);
            for (int i = 0; i < list.size(); i++) {
                CompoundTag cTag = list.getCompound(i);
                long chunkKey = cTag.getLong("pos");
                double rad = cTag.getDouble("rad");
                if (rad > 0.0001) {
                    data.chunkRadMap.put(chunkKey, rad);
                }
            }
        }
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider provider) {
        ListTag list = new ListTag();
        for (Long2DoubleMap.Entry entry : chunkRadMap.long2DoubleEntrySet()) {
            double rad = entry.getDoubleValue();
            if (rad > 0.0001) {
                CompoundTag cTag = new CompoundTag();
                cTag.putLong("pos", entry.getLongKey());
                cTag.putDouble("rad", rad);
                list.add(cTag);
            }
        }
        tag.put("chunks", list);
        return tag;
    }

    public double getRad(long chunkKey) {
        return chunkRadMap.get(chunkKey);
    }

    public void setRad(long chunkKey, double rad) {
        if (rad <= 0.0001) {
            if (chunkRadMap.remove(chunkKey) != 0.0) {
                setDirty();
            }
        } else {
            if (RadiationConfig.chunkRadCap > 0 && rad > RadiationConfig.chunkRadCap) {
                rad = RadiationConfig.chunkRadCap;
            }
            chunkRadMap.put(chunkKey, rad);
            setDirty();
        }
    }

    public void incrementRad(long chunkKey, double amount, double max) {
        if (amount == 0) return;
        double current = chunkRadMap.get(chunkKey);
        if (current >= max) return;
        double next = Math.min(current + amount, max);
        setRad(chunkKey, next);
    }

    public void decrementRad(long chunkKey, double amount) {
        if (amount == 0) return;
        double current = chunkRadMap.get(chunkKey);
        double next = Math.max(0.0, current - amount);
        setRad(chunkKey, next);
    }

    public void clear() {
        if (!chunkRadMap.isEmpty()) {
            chunkRadMap.clear();
            setDirty();
        }
    }

    public void tick(ServerLevel level) {
        if (!GeneralConfig.enableRads || chunkRadMap.isEmpty()) return;

        double dt = 1.0 / 20.0;
        double hl = RadiationConfig.radHalfLifeSeconds;
        double decayFactor = (hl > 0) ? Math.exp(Math.log(0.5) * (dt / hl)) : 0.999;
        double diffFactor = Math.min(0.01, (RadiationConfig.radDiffusivity * dt) / 100.0);

        Long2DoubleOpenHashMap nextMap = new Long2DoubleOpenHashMap(chunkRadMap.size());
        nextMap.defaultReturnValue(0.0);

        for (Long2DoubleMap.Entry entry : chunkRadMap.long2DoubleEntrySet()) {
            long chunkKey = entry.getLongKey();
            double rad = entry.getDoubleValue() * decayFactor;

            if (rad < 0.001) {
                continue;
            }

            int cx = ChunkPos.getX(chunkKey);
            int cz = ChunkPos.getZ(chunkKey);

            if (diffFactor > 0 && rad > 1.0) {
                double diffAmount = rad * diffFactor;
                rad -= diffAmount * 4.0;

                nextMap.addTo(ChunkPos.asLong(cx + 1, cz), diffAmount);
                nextMap.addTo(ChunkPos.asLong(cx - 1, cz), diffAmount);
                nextMap.addTo(ChunkPos.asLong(cx, cz + 1), diffAmount);
                nextMap.addTo(ChunkPos.asLong(cx, cz - 1), diffAmount);
            }

            nextMap.addTo(chunkKey, rad);
        }

        if (RadiationConfig.worldRadEffects && level.getGameTime() % 40 == 0) {
            for (Long2DoubleMap.Entry entry : nextMap.long2DoubleEntrySet()) {
                double rad = entry.getDoubleValue();
                if (rad > RadiationConfig.worldRadThreshold) {
                    long chunkKey = entry.getLongKey();
                    int cx = ChunkPos.getX(chunkKey);
                    int cz = ChunkPos.getZ(chunkKey);
                    if (level.hasChunk(cx, cz)) {
                        RadiationWorldHandler.decayChunkRandom(level, cx, cz, rad);
                    }
                }
            }
        }

        chunkRadMap.clear();
        for (Long2DoubleMap.Entry entry : nextMap.long2DoubleEntrySet()) {
            double rad = entry.getDoubleValue();
            if (rad >= 0.001) {
                chunkRadMap.put(entry.getLongKey(), rad);
            }
        }

        setDirty();
    }
}
