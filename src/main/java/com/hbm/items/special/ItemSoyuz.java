package com.hbm.items.special;

import com.hbm.items.ItemBase;
import com.hbm.items.ItemEnums;
import com.hbm.util.I18nUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ItemSoyuz extends ItemBase {

    protected final ItemEnums.SoyuzSkinType skin;

    public ItemSoyuz(Properties properties, ItemEnums.SoyuzSkinType skin) {
        super(properties.stacksTo(1));
        this.skin = skin;
    }

    public ItemSoyuz(ItemEnums.SoyuzSkinType skin) {
        this(new Properties(), skin);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        String desc = I18nUtil.resolveKey("item.missile_soyuz.desc") + " ";
        switch (skin) {
            case NORMAL -> tooltipComponents.add(Component.literal(desc + ChatFormatting.GOLD + I18nUtil.resolveKey("item.missile_soyuz.orig.desc")));
            case LUNAR -> tooltipComponents.add(Component.literal(desc + ChatFormatting.BLUE + I18nUtil.resolveKey("item.missile_soyuz.luna.desc")));
            case POST_WAR -> tooltipComponents.add(Component.literal(desc + ChatFormatting.GREEN + I18nUtil.resolveKey("item.missile_soyuz.war.desc")));
        }
    }
}
