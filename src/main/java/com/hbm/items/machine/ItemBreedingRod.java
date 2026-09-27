package com.hbm.items.machine;

import com.hbm.items.ItemBase;
import com.hbm.util.I18nUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ItemBreedingRod extends ItemBase {

    public final BreedingRodType rodType;

    public ItemBreedingRod(BreedingRodType rodType) {
        super(new Properties().stacksTo(1));
        this.rodType = rodType;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> list, TooltipFlag flagIn) {
        list.add(Component.literal(ChatFormatting.YELLOW + I18nUtil.resolveKey("trait.breedingRod", rodType.name())));
    }

    public enum BreedingRodType {
        TRITIUM,
        CO60,
        RA226,
        AC227,
        TH232,
        THF,
        U235,
        NP237,
        U238,
        PU238,
        PU239,
        RGP,
        WASTE,
        URANIUM
    }
}
