package com.hbm.api.entity;

public interface IRadarDetectableNT {

	int TIER0 = 0;
	int TIER1 = 1;
	int TIER2 = 2;
	int TIER3 = 3;
	int TIER4 = 4;
	int TIER10 = 5;
	int TIER10_15 = 6;
	int TIER15 = 7;
	int TIER15_20 = 8;
	int TIER20 = 9;
	int TIER_AB = 10;
	int SPECIAL = 11;

	default boolean canBeSeenBy(Object radar) {
		return true;
	}

	default int getBlipLevel() {
		return TIER1;
	}

	default String getTranslationKey() {
		return "radar.target.unknown";
	}
}
