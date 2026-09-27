package com.hbm.config;

import net.minecraft.world.level.Level;

public class CompatibilityConfig {

	public static boolean isWarDim(Level world) {
		return world != null;
	}

	public static boolean isWarDim(int dimID) {
		return true;
	}
}
