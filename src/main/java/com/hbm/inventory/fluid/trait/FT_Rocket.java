package com.hbm.inventory.fluid.trait;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.util.BobMathUtil;
import com.hbm.util.I18nUtil;
import net.minecraft.ChatFormatting;

import java.io.IOException;
import java.util.List;

public class FT_Rocket extends FluidTrait {

    private int isp;
    private long thrust;

    public FT_Rocket(int isp, long thrust) {
        this.isp = isp;
        this.thrust = thrust;
    }

    public int getISP() {
        return this.isp;
    }

    public long getThrust() {
        return this.thrust;
    }

    @Override
    public void addInfo(List<String> info) {
        super.addInfo(info);

        info.add(ChatFormatting.LIGHT_PURPLE + "[" + I18nUtil.resolveKey("trait.rocketGrade") + "]");
        if (isp > 0)
            info.add(I18nUtil.resolveKey("trait.rocketGrade.desc") + ": " + BobMathUtil.getShortNumber(isp));

        info.add(ChatFormatting.RED + "[" + I18nUtil.resolveKey("trait.thrustPower") + "]");
        if (thrust > 0)
            info.add(I18nUtil.resolveKey("trait.thrustPower.desc") + ": " + BobMathUtil.getShortNumber(thrust));
    }

    @Override
    public void serializeJSON(JsonWriter writer) throws IOException {
        writer.name("isp").value(isp);
        writer.name("thrust").value(thrust);
    }

    @Override
    public void deserializeJSON(JsonObject obj) {
        this.isp = obj.get("isp").getAsInt();
        this.thrust = obj.get("thrust").getAsLong();
    }
}
