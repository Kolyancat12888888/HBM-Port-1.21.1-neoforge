package com.hbm.hazard;

import com.hbm.hazard.type.IHazardType;

import java.util.ArrayList;
import java.util.List;

public class HazardData {
    public boolean doesOverride = false;
    public int mutexBits = 0;
    public List<HazardEntry> entries = new ArrayList<>();

    public HazardData addEntry(final IHazardType hazard) {
        return this.addEntry(hazard, 1D, false);
    }

    public HazardData addEntry(final IHazardType hazard, final double level) {
        return this.addEntry(hazard, level, false);
    }

    public HazardData addEntry(final IHazardType hazard, final double level, final boolean override) {
        this.entries.add(new HazardEntry(hazard, level));
        this.doesOverride = override;
        return this;
    }

    public HazardData addEntry(final HazardEntry entry) {
        this.entries.add(entry);
        return this;
    }

    public HazardData setMutex(final int mutex) {
        this.mutexBits = mutex;
        return this;
    }

    public HazardData setOverride(final boolean override) {
        this.doesOverride = override;
        return this;
    }

    public int getMutex() {
        return mutexBits;
    }
}
