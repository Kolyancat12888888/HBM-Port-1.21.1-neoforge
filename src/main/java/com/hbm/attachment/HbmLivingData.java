package com.hbm.attachment;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;

public class HbmLivingData implements INBTSerializable<CompoundTag> {
    public static final int maxAsbestos = 60 * 60 * 20;
    public static final int maxBlacklung = 2 * 60 * 60 * 20;

    private double rads = 0;
    private double radEnv = 0;
    private double radBuf = 0;
    private double neutrons = 0;
    private double digamma = 0;
    private int asbestos = 0;
    private int blacklung = 0;
    private int bombTimer = 0;
    private int contagion = 0;
    private int oil = 0;

    public double getRads() { return rads; }
    public void setRads(double rads) { this.rads = rads; }

    public double getRadsEnv() { return radEnv; }
    public void setRadsEnv(double radEnv) { this.radEnv = radEnv; }

    public double getRadBuf() { return radBuf; }
    public void setRadBuf(double radBuf) { this.radBuf = radBuf; }

    public double getNeutrons() { return neutrons; }
    public void setNeutrons(double neutrons) { this.neutrons = neutrons; }

    public double getDigamma() { return digamma; }
    public void setDigamma(double digamma) { this.digamma = digamma; }

    public int getAsbestos() { return asbestos; }
    public void setAsbestos(int asbestos) { this.asbestos = asbestos; }

    public int getBlacklung() { return blacklung; }
    public void setBlacklung(int blacklung) { this.blacklung = blacklung; }

    public int getBombTimer() { return bombTimer; }
    public void setBombTimer(int bombTimer) { this.bombTimer = bombTimer; }

    public int getContagion() { return contagion; }
    public void setContagion(int contagion) { this.contagion = contagion; }

    public int getOil() { return oil; }
    public void setOil(int oil) { this.oil = oil; }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        tag.putDouble("rads", rads);
        tag.putDouble("radEnv", radEnv);
        tag.putDouble("radBuf", radBuf);
        tag.putDouble("neutrons", neutrons);
        tag.putDouble("digamma", digamma);
        tag.putInt("asbestos", asbestos);
        tag.putInt("blacklung", blacklung);
        tag.putInt("bombTimer", bombTimer);
        tag.putInt("contagion", contagion);
        tag.putInt("oil", oil);
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag tag) {
        if (tag.contains("rads")) rads = tag.getDouble("rads");
        if (tag.contains("radEnv")) radEnv = tag.getDouble("radEnv");
        if (tag.contains("radBuf")) radBuf = tag.getDouble("radBuf");
        if (tag.contains("neutrons")) neutrons = tag.getDouble("neutrons");
        if (tag.contains("digamma")) digamma = tag.getDouble("digamma");
        if (tag.contains("asbestos")) asbestos = tag.getInt("asbestos");
        if (tag.contains("blacklung")) blacklung = tag.getInt("blacklung");
        if (tag.contains("bombTimer")) bombTimer = tag.getInt("bombTimer");
        if (tag.contains("contagion")) contagion = tag.getInt("contagion");
        if (tag.contains("oil")) oil = tag.getInt("oil");
    }
}
