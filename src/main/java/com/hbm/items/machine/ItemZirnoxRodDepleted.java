package com.hbm.items.machine;

import com.hbm.items.ItemBase;
import com.hbm.util.I18nUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ItemZirnoxRodDepleted extends ItemBase {

    public final EnumZirnoxTypeDepleted rodType;

    public ItemZirnoxRodDepleted(EnumZirnoxTypeDepleted rodType) {
        super(new Properties().stacksTo(1));
        this.rodType = rodType;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> list, TooltipFlag flag) {
        list.add(Component.literal(ChatFormatting.DARK_RED + "[Depleted Nuclear Fuel]"));
    }

    public enum EnumZirnoxTypeDepleted {
        NATURAL_URANIUM_FUEL,
        URANIUM_FUEL,
        THORIUM_FUEL,
        MOX_FUEL,
        PLUTONIUM_FUEL,
        U233_FUEL,
        U235_FUEL,
        LES_FUEL,
        ZFB_MOX_FUEL;

        public static final EnumZirnoxTypeDepleted[] VALUES = values();
    }
}
