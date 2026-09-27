package com.hbm.tileentity.machine;

import com.hbm.interfaces.ILaserable;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTankNTM;
import com.hbm.tileentity.ModBlockEntities;
import com.hbm.tileentity.TileEntityMachineBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class TileEntityCoreEmitter extends TileEntityMachineBase implements ILaserable {

    public long power;
    public static final long maxPower = 1_000_000_000L;
    public int watts = 1;
    public int beam;
    public long joules;
    public boolean isOn = true;
    public FluidTankNTM tank;
    public long prev;

    public static final int range = 50;

    public TileEntityCoreEmitter(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DFC_EMITTER.get(), pos, state, 0);
        tank = new FluidTankNTM(Fluids.CRYOGEL, 64_000);
    }

    @Override
    public String getDefaultName() {
        return "container.dfcEmitter";
    }

    public static void tick(Level level, BlockPos pos, BlockState state, TileEntityCoreEmitter emitter) {
        if (level.isClientSide()) return;

        emitter.watts = Mth.clamp(emitter.watts, 1, 100);
        long demand = maxPower * emitter.watts / 2000;
        emitter.beam = 0;

        if (emitter.joules > 0 || emitter.prev > 0) {
            if (emitter.tank.getFill() >= 20) {
                emitter.tank.setFill(emitter.tank.getFill() - 20);
            } else {
                level.setBlockAndUpdate(pos, Blocks.LAVA.defaultBlockState());
                return;
            }
        }

        if (emitter.isOn) {
            if (emitter.power >= demand) {
                emitter.power -= demand;
                long add = emitter.watts * 100L;
                emitter.joules += add;
            }
            emitter.prev = emitter.joules;

            if (emitter.joules > 0) {
                long out = emitter.joules * 95 / 100;
                Direction dir = Direction.from3DDataValue(state.getValue(net.minecraft.world.level.block.state.properties.BlockStateProperties.FACING).get3DDataValue());

                for (int i = 1; i <= range; i++) {
                    emitter.beam = i;
                    BlockPos targetPos = pos.relative(dir, i);
                    BlockState targetState = level.getBlockState(targetPos);
                    BlockEntity targetTe = level.getBlockEntity(targetPos);

                    if (targetTe instanceof ILaserable laserable) {
                        laserable.addEnergy(level, targetPos, out, dir);
                        break;
                    }

                    if (targetTe instanceof TileEntityCore core) {
                        out = core.burn(out);
                        continue;
                    }

                    if (!targetState.isAir()) {
                        if (targetState.liquid()) {
                            level.playSound(null, targetPos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
                            level.setBlockAndUpdate(targetPos, Blocks.AIR.defaultBlockState());
                            break;
                        }
                        if (targetState.getDestroySpeed(level, targetPos) >= 0 && level.random.nextInt(20) == 0) {
                            level.destroyBlock(targetPos, false);
                        }
                        break;
                    }
                }

                emitter.joules = 0;

                AABB laserBox = new AABB(pos).expandTowards(dir.getStepX() * emitter.beam, dir.getStepY() * emitter.beam, dir.getStepZ() * emitter.beam);
                List<Entity> list = level.getEntities((Entity) null, laserBox, e -> true);
                for (Entity e : list) {
                    e.igniteForSeconds(10);
                }
            }
        } else {
            emitter.joules = 0;
            emitter.prev = 0;
        }

        emitter.markChanged();
    }

    @Override
    public void addEnergy(Level level, BlockPos pos, long energy, Direction dir) {
        joules += energy;
    }

    @Override
    protected void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.saveAdditional(compound, registries);
        compound.putLong("power", power);
        compound.putInt("watts", watts);
        compound.putLong("joules", joules);
        compound.putLong("prev", prev);
        compound.putBoolean("isOn", isOn);
        tank.writeToNBT(compound, "tank");
    }

    @Override
    protected void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.loadAdditional(compound, registries);
        power = compound.getLong("power");
        watts = compound.getInt("watts");
        joules = compound.getLong("joules");
        prev = compound.getLong("prev");
        isOn = compound.getBoolean("isOn");
        tank.readFromNBT(compound, "tank");
    }
}
