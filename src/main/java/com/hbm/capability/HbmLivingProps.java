package com.hbm.capability;

import com.hbm.attachment.HbmAttachments;
import com.hbm.attachment.HbmLivingData;
import com.hbm.config.RadiationConfig;
import net.minecraft.world.entity.LivingEntity;

public class HbmLivingProps {

    public static final int maxAsbestos = 6000;
    public static final int maxBlacklung = 6000;

    public static HbmLivingData getData(LivingEntity entity) {
        return entity.getData(HbmAttachments.LIVING_DATA);
    }

    public static double getRadiation(LivingEntity entity) {
        if (!RadiationConfig.enableContamination) return 0;
        return getData(entity).getRads();
    }

    public static void setRadiation(LivingEntity entity, double rad) {
        if (RadiationConfig.enableContamination) getData(entity).setRads(rad);
    }

    public static void incrementRadiation(LivingEntity entity, double rad) {
        if (!RadiationConfig.enableContamination) return;
        double radiation = getRadiation(entity) + rad;
        if (radiation > 25000000) radiation = 25000000;
        if (radiation < 0) radiation = 0;
        setRadiation(entity, radiation);
    }

    public static double getNeutron(LivingEntity entity) {
        return getData(entity).getNeutrons();
    }

    public static void setNeutron(LivingEntity entity, double rad) {
        getData(entity).setNeutrons(rad);
    }

    public static double getRadEnv(LivingEntity entity) {
        return getData(entity).getRadsEnv();
    }

    public static void setRadEnv(LivingEntity entity, double rad) {
        getData(entity).setRadsEnv(rad);
    }

    public static double getRadBuf(LivingEntity entity) {
        return getData(entity).getRadBuf();
    }

    public static void setRadBuf(LivingEntity entity, double rad) {
        getData(entity).setRadBuf(rad);
    }

    public static double getDigamma(LivingEntity entity) {
        return getData(entity).getDigamma();
    }

    public static void setDigamma(LivingEntity entity, double digamma) {
        getData(entity).setDigamma(digamma);
    }

    public static void incrementDigamma(LivingEntity entity, double digamma) {
        double dRad = getDigamma(entity) + digamma;
        if (dRad > 10) dRad = 10;
        if (dRad < 0) dRad = 0;
        setDigamma(entity, dRad);
    }

    public static int getAsbestos(LivingEntity entity) {
        return getData(entity).getAsbestos();
    }

    public static void setAsbestos(LivingEntity entity, int asbestos) {
        getData(entity).setAsbestos(asbestos);
    }

    public static void incrementAsbestos(LivingEntity entity, int asbestos) {
        setAsbestos(entity, getAsbestos(entity) + asbestos);
    }

    public static int getBlackLung(LivingEntity entity) {
        return getData(entity).getBlacklung();
    }

    public static void setBlackLung(LivingEntity entity, int blacklung) {
        getData(entity).setBlacklung(blacklung);
    }

    public static void incrementBlackLung(LivingEntity entity, int blacklung) {
        setBlackLung(entity, getBlackLung(entity) + blacklung);
    }

    public static int getTimer(LivingEntity entity) {
        return getData(entity).getBombTimer();
    }

    public static void setTimer(LivingEntity entity, int bombTimer) {
        getData(entity).setBombTimer(bombTimer);
    }

    public static int getContagion(LivingEntity entity) {
        return getData(entity).getContagion();
    }

    public static void setContagion(LivingEntity entity, int contagion) {
        getData(entity).setContagion(contagion);
    }

    public static int getOil(LivingEntity entity) {
        return getData(entity).getOil();
    }

    public static void setOil(LivingEntity entity, int oil) {
        getData(entity).setOil(oil);
    }
}
