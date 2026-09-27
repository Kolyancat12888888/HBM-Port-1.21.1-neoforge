package com.hbm.lib;

import net.minecraft.core.Direction;

/**
 * Compatibility wrapper for ForgeDirection mapping directly to Minecraft 1.21.1 Direction.
 */
public enum ForgeDirection {
    DOWN(Direction.DOWN),
    UP(Direction.UP),
    NORTH(Direction.NORTH),
    SOUTH(Direction.SOUTH),
    WEST(Direction.WEST),
    EAST(Direction.EAST),
    UNKNOWN(null);

    public final Direction mcDir;

    ForgeDirection(Direction mcDir) {
        this.mcDir = mcDir;
    }

    public static ForgeDirection getOrientation(int id) {
        if (id >= 0 && id < 6) {
            return values()[id];
        }
        return UNKNOWN;
    }

    public static ForgeDirection fromDirection(Direction dir) {
        if (dir == null) return UNKNOWN;
        return switch (dir) {
            case DOWN -> DOWN;
            case UP -> UP;
            case NORTH -> NORTH;
            case SOUTH -> SOUTH;
            case WEST -> WEST;
            case EAST -> EAST;
        };
    }

    public Direction toDirection() {
        return mcDir;
    }
}
