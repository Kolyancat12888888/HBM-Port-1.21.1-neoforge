package com.hbm.items.machine;

import com.hbm.items.ItemBase;
import com.hbm.util.BobMathUtil;
import com.hbm.util.I18nUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;

import java.util.List;

public class ItemZirnoxRod extends ItemBase {

    public final EnumZirnoxType rodType;

    public ItemZirnoxRod(EnumZirnoxType rodType) {
        super(new Properties().stacksTo(1));
        this.rodType = rodType;
    }

    public static void incrementLifeTime(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return;
        CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        CompoundTag tag = customData.copyTag();
        int time = tag.getInt("life");
        tag.putInt("life", time + 1);
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
    }

    public static void setLifeTime(ItemStack stack, int time) {
        if (stack == null || stack.isEmpty()) return;
        CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        CompoundTag tag = customData.copyTag();
        tag.putInt("life", time);
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
    }

    public static int getLifeTime(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return 0;
        CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
        if (customData != null) {
            CompoundTag tag = customData.copyTag();
            if (tag.contains("life")) {
                return tag.getInt("life");
            }
        }
        return 0;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> list, TooltipFlag flag) {
        int life = getLifeTime(stack);
        double depletion = ((int) ((((double) life) / (double) rodType.maxLife) * 100000)) / 1000D;
        list.add(Component.literal(ChatFormatting.YELLOW + I18nUtil.resolveKey("trait.rbmk.depletion", depletion + "%")));

        if (rodType.breeding) {
            for (String s : I18nUtil.resolveKeyArray("desc.item.zirnoxBreedingRod", BobMathUtil.getShortNumber(rodType.maxLife))) {
                list.add(Component.literal(s));
            }
        } else {
            for (String s : I18nUtil.resolveKeyArray("desc.item.zirnoxRod", rodType.heat, BobMathUtil.getShortNumber(rodType.maxLife))) {
                list.add(Component.literal(s));
            }
        }
    }

    public enum EnumZirnoxType {
        NATURAL_URANIUM_FUEL(250_000, 30),
        URANIUM_FUEL(200_000, 50),
        TH232_FUEL(20_000, 0, true),
        THORIUM_FUEL(200_000, 40),
        MOX_FUEL(165_000, 75),
        PLUTONIUM_FUEL(175_000, 65),
        U233_FUEL(150_000, 100),
        U235_FUEL(165_000, 85),
        LES_FUEL(150_000, 150),
        LITHIUM_FUEL(20_000, 0, true),
        ZFB_MOX_FUEL(50_000, 35);

        public static final EnumZirnoxType[] VALUES = values();

        public final int maxLife;
        public final int heat;
        public final boolean breeding;

        EnumZirnoxType(int life, int heat, boolean breeding) {
            this.maxLife = life;
            this.heat = heat;
            this.breeding = breeding;
        }

        EnumZirnoxType(int life, int heat) {
            this(life, heat, false);
        }
    }
}
