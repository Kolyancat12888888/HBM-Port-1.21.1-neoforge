package com.hbm.tileentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public abstract class TileEntityMachineBase extends TileEntityLoadedBase implements Nameable {

    public ItemStackHandler inventory;
    private Component customName;

    public TileEntityMachineBase(BlockEntityType<?> type, BlockPos pos, BlockState state, int slotCount) {
        this(type, pos, state, slotCount, 64);
    }

    public TileEntityMachineBase(BlockEntityType<?> type, BlockPos pos, BlockState state, int slotCount, int slotLimit) {
        super(type, pos, state);
        this.inventory = new ItemStackHandler(slotCount) {
            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                markChanged();
            }

            @Override
            public int getSlotLimit(int slot) {
                return slotLimit;
            }
        };
    }

    public abstract String getDefaultName();

    @Override
    public Component getName() {
        return this.customName != null ? this.customName : Component.translatable(getDefaultName());
    }

    @Nullable
    @Override
    public Component getCustomName() {
        return this.customName;
    }

    public void setCustomName(Component name) {
        this.customName = name;
    }

    public boolean isUseableByPlayer(Player player) {
        if (level == null || level.getBlockEntity(worldPosition) != this) {
            return false;
        }
        return player.distanceToSqr(worldPosition.getX() + 0.5D, worldPosition.getY() + 0.5D, worldPosition.getZ() + 0.5D) <= 64.0D;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (inventory != null) {
            tag.put("inventory", inventory.serializeNBT(registries));
        }
        if (customName != null) {
            tag.putString("CustomName", Component.Serializer.toJson(customName, registries));
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("inventory") && inventory != null) {
            inventory.deserializeNBT(registries, tag.getCompound("inventory"));
        }
        if (tag.contains("CustomName", 8)) {
            this.customName = Component.Serializer.fromJson(tag.getString("CustomName"), registries);
        }
    }
}
