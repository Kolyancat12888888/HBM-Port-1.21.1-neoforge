package com.hbm.tileentity.machine;

import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemLens;
import com.hbm.tileentity.ModBlockEntities;
import com.hbm.tileentity.TileEntityMachineBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class TileEntityCoreStabilizer extends TileEntityMachineBase {

    public static final long maxPower = 2_500_000_000L;
    public static final int range = 15;
    public long power;
    public int watts = 1;
    public int beam;
    public boolean isOn = true;

    public TileEntityCoreStabilizer(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DFC_STABILIZER.get(), pos, state, 1);
    }

    @Override
    public String getDefaultName() {
        return "container.dfcStabilizer";
    }

    public static void tick(Level level, BlockPos pos, BlockState state, TileEntityCoreStabilizer stabilizer) {
        if (level.isClientSide()) return;

        stabilizer.watts = Mth.clamp(stabilizer.watts, 1, 100);
        long demand = (long) Math.pow(stabilizer.watts, 4);
        stabilizer.beam = 0;

        ItemStack lens = stabilizer.inventory.getStackInSlot(0);
        if (stabilizer.power >= demand && !lens.isEmpty() && lens.getItem() instanceof ItemLens lensItem && ItemLens.getLensDamage(lens) < lensItem.maxDamage) {
            Direction dir = state.getValue(BlockStateProperties.FACING);

            for (int i = 1; i <= range; i++) {
                BlockPos targetPos = pos.relative(dir, i);
                BlockEntity te = level.getBlockEntity(targetPos);

                if (te instanceof TileEntityCore core) {
                    core.field = Math.max(core.field, stabilizer.watts);
                    stabilizer.power -= demand;
                    stabilizer.beam = i;

                    long dmg = ItemLens.getLensDamage(lens);
                    dmg += stabilizer.watts;

                    if (dmg >= lensItem.maxDamage) {
                        stabilizer.inventory.setStackInSlot(0, ItemStack.EMPTY);
                    } else {
                        ItemLens.setLensDamage(lens, dmg);
                    }
                    break;
                }

                if (!level.getBlockState(targetPos).isAir()) break;
            }
        }

        stabilizer.markChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.saveAdditional(compound, registries);
        compound.putLong("power", power);
        compound.putInt("watts", watts);
        compound.putBoolean("isOn", isOn);
    }

    @Override
    protected void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.loadAdditional(compound, registries);
        power = compound.getLong("power");
        watts = compound.getInt("watts");
        isOn = compound.getBoolean("isOn");
    }
}
