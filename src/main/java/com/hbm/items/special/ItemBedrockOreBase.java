package com.hbm.items.special;

import com.hbm.items.ItemBase;
import com.hbm.items.tool.ItemOreDensityScanner;
import com.hbm.util.I18nUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;

import java.util.List;
import java.util.Random;

public class ItemBedrockOreBase extends ItemBase {

    public ItemBedrockOreBase(Properties properties) {
        super(properties);
    }

    public ItemBedrockOreBase() {
        this(new Properties());
    }

    public static double getOreAmount(ItemStack stack, ItemBedrockOreNew.BedrockOreType type) {
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        if (data == null || !data.contains(type.suffix)) return 0;
        return data.copyTag().getDouble(type.suffix);
    }

    public static void setOreAmount(ItemStack stack, int x, int z) {
        setOreAmount(stack, x, z, 1D);
    }

    public static void setOreAmount(ItemStack stack, int x, int z, double mult) {
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> {
            for (ItemBedrockOreNew.BedrockOreType type : ItemBedrockOreNew.BedrockOreType.VALUES) {
                tag.putDouble(type.suffix, getOreLevel(x, z, type) * mult);
            }
        });
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        for (ItemBedrockOreNew.BedrockOreType type : ItemBedrockOreNew.BedrockOreType.VALUES) {
            double amount = getOreAmount(stack, type);
            String typeName = I18nUtil.resolveKey("item.bedrock_ore.type." + type.suffix + ".name");
            tooltipComponents.add(Component.literal(typeName + ": " + ((int) (amount * 100)) / 100D + " (" + ItemOreDensityScanner.getColor(amount) + I18nUtil.resolveKey(ItemOreDensityScanner.translateDensity(amount)) + ChatFormatting.RESET + ")"));
        }
    }

    public static double getOreLevel(int x, int z, ItemBedrockOreNew.BedrockOreType type) {
        double scale = 0.01D;
        double noise1 = Math.sin(x * scale * 0.7) * Math.cos(z * scale * 0.7);
        double noise2 = Math.sin((x + type.ordinal() * 100) * scale * 1.3) * Math.cos((z + type.ordinal() * 100) * scale * 1.3);
        return Math.clamp(Math.abs(noise1 * noise2) * 2.0, 0.0, 2.0);
    }
}
