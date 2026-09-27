package com.hbm.items.special;

import com.hbm.items.ItemBase;
import com.hbm.items.ModItems;
import com.hbm.util.I18nUtil;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;

import java.util.List;

public class ItemBookLore extends ItemBase {

    public ItemBookLore(Properties properties) {
        super(properties.stacksTo(1));
    }

    public ItemBookLore() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> list, TooltipFlag flag) {
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        if (data == null || !data.contains("k")) return;
        String key = data.copyTag().getString("k");
        if (key.isEmpty()) return;

        String fullKey = "book_lore." + key + ".author";
        String loc = I18nUtil.resolveKey(fullKey);
        if (!loc.equals(fullKey)) {
            list.add(Component.literal(I18nUtil.resolveKey("book_lore.author", loc)));
        }
    }

    public static ItemStack createBook(String key, int pages, int colorCov, int colorTit) {
        ItemStack book = new ItemStack(ModItems.book_lore);
        CustomData.update(DataComponents.CUSTOM_DATA, book, tag -> {
            tag.putString("k", key);
            tag.putShort("p", (short) pages);
            tag.putInt("cov_col", colorCov);
            tag.putInt("tit_col", colorTit);
        });
        return book;
    }
}
