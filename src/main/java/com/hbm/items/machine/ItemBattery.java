package com.hbm.items.machine;

import com.hbm.api.energymk2.IBatteryItem;
import com.hbm.items.ItemBase;
import com.hbm.lib.Library;
import com.hbm.util.I18nUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;

import java.util.List;

public class ItemBattery extends ItemBase implements IBatteryItem {

    protected long maxCharge;
    protected long chargeRate;
    protected long dischargeRate;

    public ItemBattery(Properties properties, long dura, long chargeRate, long dischargeRate) {
        super(properties.stacksTo(1));
        this.maxCharge = dura;
        this.chargeRate = chargeRate;
        this.dischargeRate = dischargeRate;
    }

    public ItemBattery(long dura, long chargeRate, long dischargeRate) {
        this(new Properties(), dura, chargeRate, dischargeRate);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        long charge = getCharge(stack);
        tooltipComponents.add(Component.literal(ChatFormatting.GOLD + I18nUtil.resolveKey("desc.energystore") + " " + Library.getShortNumber(charge) + "/" + Library.getShortNumber(maxCharge) + "HE" + ChatFormatting.RESET));
        tooltipComponents.add(Component.literal(ChatFormatting.GREEN + I18nUtil.resolveKey("desc.energychargerate") + " " + Library.getShortNumber(chargeRate * 20) + "HE/s" + ChatFormatting.RESET));
        tooltipComponents.add(Component.literal(ChatFormatting.RED + I18nUtil.resolveKey("desc.energydchargerate") + " " + Library.getShortNumber(dischargeRate * 20) + "HE/s" + ChatFormatting.RESET));
    }

    @Override
    public void chargeBattery(ItemStack stack, long i) {
        long current = getCharge(stack);
        setCharge(stack, Math.min(current + i, maxCharge));
    }

    @Override
    public void setCharge(ItemStack stack, long i) {
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> {
            tag.putLong(getChargeTagName(), Math.clamp(i, 0, maxCharge));
        });
    }

    @Override
    public void dischargeBattery(ItemStack stack, long i) {
        long current = getCharge(stack);
        setCharge(stack, Math.max(current - i, 0));
    }

    @Override
    public long getCharge(ItemStack stack) {
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        if (data != null && data.contains(getChargeTagName())) {
            return data.copyTag().getLong(getChargeTagName());
        }
        return maxCharge;
    }

    @Override
    public long getMaxCharge(ItemStack stack) {
        return maxCharge;
    }

    @Override
    public long getChargeRate(ItemStack stack) {
        return chargeRate;
    }

    @Override
    public long getDischargeRate(ItemStack stack) {
        return dischargeRate;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return Math.round((float) getCharge(stack) * 13.0F / (float) maxCharge);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return 0x00FF00;
    }
}
