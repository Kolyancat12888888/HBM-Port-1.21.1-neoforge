package com.hbm.tileentity;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class TileEntityLoadedBase extends BlockEntity {

    public boolean isLoaded = true;
    public boolean muffled = false;
    public boolean tilted = false;
    public int tiltBlocksChecked = 0;
    public int tiltBlocksValid = 0;
    protected boolean hasDataChanged = true;

    public TileEntityLoadedBase(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        isLoaded = true;
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        isLoaded = false;
    }

    public void markChanged() {
        if (this.level != null) {
            this.setChanged();
        }
    }

    public float getVolume(float baseVolume) {
        return muffled ? baseVolume * 0.1F : baseVolume;
    }

    public void setMuffled(boolean muffled) {
        this.muffled = muffled;
        dataChanged();
    }

    public void dataChanged() {
        hasDataChanged = true;
        markChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putBoolean("muffled", muffled);
        tag.putBoolean("tilted", tilted);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        muffled = tag.getBoolean("muffled");
        tilted = tag.getBoolean("tilted");
        hasDataChanged = true;
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        tag.putBoolean("muffled", muffled);
        tag.putBoolean("tilted", tilted);
        return tag;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public enum TiltType {
        UNAVOIDABLE, CONFIG
    }

    public void checkTilt(TiltType cfg, boolean extraHeavy) {
        if (level == null) return;
        boolean doesTilt = false;
        if (cfg == TiltType.UNAVOIDABLE) doesTilt = true;
        if (cfg == TiltType.CONFIG && GeneralConfig.enableExpensiveMode) doesTilt = true;

        if (!doesTilt) {
            this.tilted = false;
            return;
        }
        if (this.getFloorCount() <= 0) {
            this.tilted = false;
            return;
        }
        if ((level.getGameTime() + (worldPosition.getY() + worldPosition.getZ() * 27644437L) * 27644437L + worldPosition.getX()) % 20 != 0)
            return;

        if (this.tiltBlocksChecked >= this.getFloorCount()) {
            if (this.tiltBlocksValid >= this.tiltBlocksChecked * 0.95) {
                this.tilted = false;
            } else {
                if (!this.tilted) {
                    level.playSound(null, worldPosition.getX() + 0.5, worldPosition.getY() + 0.5, worldPosition.getZ() + 0.5, SoundEvents.ANVIL_LAND, SoundSource.BLOCKS, 3.0F, 1.0F);
                }
                this.tilted = true;
            }

            this.markChanged();
            this.tiltBlocksChecked = 0;
            this.tiltBlocksValid = 0;
        }

        BlockPos floorPos = getFloorPosFromIndex(this.tiltBlocksChecked);
        if (floorPos == null) return;

        BlockState ground = level.getBlockState(floorPos);
        this.tiltBlocksChecked++;

        if (extraHeavy) {
            if (!ground.isSolid()) return;
            if (ground.is(Blocks.SAND) || ground.is(Blocks.GRAVEL) || ground.is(Blocks.DIRT)) return;
            if (ground.getBlock().getExplosionResistance() < Blocks.STONE.getExplosionResistance()) return;
            this.tiltBlocksValid++;
        } else {
            if (!ground.isFaceSturdy(level, floorPos, Direction.UP)) return;
            if (ground.is(Blocks.SAND)) return;
            this.tiltBlocksValid++;
        }
    }

    public int getFloorCount() {
        return 0;
    }

    public BlockPos getFloorPosFromIndex(int index) {
        return null;
    }

    public BlockPos standardFloor3x3(int index) {
        return new BlockPos(worldPosition.getX() - 1 + (index / 2) * 2, worldPosition.getY() - 1, worldPosition.getZ() - 1 + (index % 2) * 2);
    }

    public BlockPos standardFloor5x5(int index) {
        return new BlockPos(worldPosition.getX() - 2 + (index / 3) * 2, worldPosition.getY() - 1, worldPosition.getZ() - 2 + (index % 3) * 2);
    }

    public BlockPos standardFloor7x7(int index) {
        return new BlockPos(worldPosition.getX() - 3 + (index / 4) * 2, worldPosition.getY() - 1, worldPosition.getZ() - 3 + (index % 4) * 2);
    }
}
