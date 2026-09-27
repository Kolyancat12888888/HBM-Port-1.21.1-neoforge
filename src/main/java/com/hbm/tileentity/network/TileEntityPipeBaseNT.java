package com.hbm.tileentity.network;

import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTankNTM;
import com.hbm.tileentity.ModBlockEntities;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.tileentity.machine.TileEntityMachineFluidTank;
import com.hbm.tileentity.machine.TileEntityMachineTurbine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityPipeBaseNT extends TileEntityLoadedBase {

    public final FluidTankNTM tank;
    public int maxTransfer = 1000;

    public TileEntityPipeBaseNT(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PIPE.get(), pos, state);
        this.tank = new FluidTankNTM(Fluids.NONE, 4000);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, TileEntityPipeBaseNT pipe) {
        if (level == null || level.isClientSide) return;

        if (pipe.tank.getFill() > 0) {
            for (Direction dir : Direction.values()) {
                BlockPos targetPos = pos.relative(dir);
                if (!level.hasChunkAt(targetPos)) continue;

                BlockEntity te = level.getBlockEntity(targetPos);
                if (te instanceof TileEntityPipeBaseNT otherPipe) {
                    if (otherPipe.tank.getTankType() == Fluids.NONE || otherPipe.tank.getTankType() == pipe.tank.getTankType()) {
                        if (pipe.tank.getFill() > otherPipe.tank.getFill()) {
                            int diff = (pipe.tank.getFill() - otherPipe.tank.getFill()) / 2;
                            int transfer = Math.min(diff, Math.min(pipe.maxTransfer, otherPipe.tank.getSpace()));
                            if (transfer > 0) {
                                otherPipe.tank.setTankType(pipe.tank.getTankType());
                                pipe.tank.setFill(pipe.tank.getFill() - transfer);
                                otherPipe.tank.setFill(otherPipe.tank.getFill() + transfer);
                                otherPipe.setChanged();
                                pipe.setChanged();
                            }
                        }
                    }
                } else if (te instanceof TileEntityMachineFluidTank fluidTank) {
                    if (fluidTank.tank.getTankType() == Fluids.NONE || fluidTank.tank.getTankType() == pipe.tank.getTankType()) {
                        int transfer = Math.min(pipe.tank.getFill(), Math.min(pipe.maxTransfer, fluidTank.tank.getSpace()));
                        if (transfer > 0) {
                            fluidTank.tank.setTankType(pipe.tank.getTankType());
                            pipe.tank.setFill(pipe.tank.getFill() - transfer);
                            fluidTank.tank.setFill(fluidTank.tank.getFill() + transfer);
                            fluidTank.setChanged();
                            pipe.setChanged();
                        }
                    }
                } else if (te instanceof TileEntityMachineTurbine turbine) {
                    if (pipe.tank.getTankType() == Fluids.STEAM || pipe.tank.getTankType() == Fluids.HOTSTEAM ||
                        pipe.tank.getTankType() == Fluids.SUPERHOTSTEAM || pipe.tank.getTankType() == Fluids.ULTRAHOTSTEAM) {
                        int transfer = Math.min(pipe.tank.getFill(), Math.min(pipe.maxTransfer, turbine.steamTank.getSpace()));
                        if (transfer > 0) {
                            turbine.steamTank.setTankType(pipe.tank.getTankType());
                            pipe.tank.setFill(pipe.tank.getFill() - transfer);
                            turbine.steamTank.setFill(turbine.steamTank.getFill() + transfer);
                            turbine.setChanged();
                            pipe.setChanged();
                        }
                    }
                }

                if (pipe.tank.getFill() <= 0) {
                    pipe.tank.setTankType(Fluids.NONE);
                    break;
                }
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tank.writeToNBT(tag, "tank");
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        tank.readFromNBT(tag, "tank");
    }
}
