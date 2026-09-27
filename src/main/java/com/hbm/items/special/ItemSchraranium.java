package com.hbm.items.special;

import com.hbm.config.GeneralConfig;
import com.hbm.items.ItemBase;
import com.hbm.util.I18nUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ItemSchraranium extends ItemBase {

    public ItemSchraranium(Properties properties) {
        super(properties);
    }

    public ItemSchraranium() {
        super(new Properties());
    }

    @Override
    public Component getName(ItemStack stack) {
        if (GeneralConfig.enableLBSM && GeneralConfig.enableLBSMFullSchrab) {
            return Component.literal(I18nUtil.resolveKey("item.ingot_nikonium.name"));
        }
        return super.getName(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (GeneralConfig.enableLBSM && GeneralConfig.enableLBSMFullSchrab) {
            tooltipComponents.add(Component.literal("pankæk"));
        }
    }
}
