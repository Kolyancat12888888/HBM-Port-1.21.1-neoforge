package com.hbm.hazard.type;

import com.hbm.hazard.modifier.IHazardModifier;
import com.hbm.util.I18nUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.List;
import java.util.function.ObjDoubleConsumer;

public class HazardTypeDangerousDrop implements IHazardType {
    private final ObjDoubleConsumer<ItemEntity> onDroppedItemUpdate;

    public HazardTypeDangerousDrop(ObjDoubleConsumer<ItemEntity> onDrop) {
        this.onDroppedItemUpdate = onDrop;
    }

    @Override
    public void onUpdate(LivingEntity target, double level, ItemStack stack) {
    }

    @Override
    public void updateEntity(ItemEntity item, double level) {
        if (onDroppedItemUpdate != null) {
            onDroppedItemUpdate.accept(item, level);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addHazardInformation(Player player, List<Component> list, double level, ItemStack stack, List<IHazardModifier> modifiers) {
        list.add(Component.literal("§c[" + I18nUtil.resolveKey("trait.drop") + "]"));
    }
}
