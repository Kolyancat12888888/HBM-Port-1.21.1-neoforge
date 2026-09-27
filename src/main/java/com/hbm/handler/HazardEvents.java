package com.hbm.handler;

import com.hbm.config.RadiationConfig;
import com.hbm.hazard.HazardSystem;
import com.hbm.saveddata.RadiationSavedData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME)
public class HazardEvents {

    @SubscribeEvent
    public static void onLevelTick(LevelTickEvent.Post event) {
        if (event.getLevel() instanceof ServerLevel serverLevel) {
            RadiationSavedData data = RadiationSavedData.get(serverLevel);
            if (data != null) {
                data.tick(serverLevel);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide() || !player.isAlive()) return;

        EntityEffectHandler.onUpdate(player);

        if (player.tickCount % Math.max(1, RadiationConfig.hazardRate) == 0) {
            for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                ItemStack stack = player.getInventory().getItem(i);
                if (!stack.isEmpty()) {
                    HazardSystem.applyHazards(stack, player);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        if (event.getEntity() instanceof ItemEntity itemEntity) {
            if (itemEntity.level().isClientSide() || !itemEntity.isAlive()) return;
            if (itemEntity.tickCount % Math.max(1, RadiationConfig.hazardRate) == 0) {
                HazardSystem.updateDroppedItem(itemEntity);
            }
        } else if (event.getEntity() instanceof LivingEntity living && !(living instanceof Player)) {
            if (living.level().isClientSide() || !living.isAlive()) return;
            EntityEffectHandler.onUpdate(living);
        }
    }

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.isEmpty()) return;

        Player player = event.getEntity();
        var hazards = HazardSystem.getHazardsFromStack(stack);
        for (var hazard : hazards) {
            hazard.type.addHazardInformation(player, event.getToolTip(), hazard.baseLevel, stack, hazard.mods);
        }
    }
}
