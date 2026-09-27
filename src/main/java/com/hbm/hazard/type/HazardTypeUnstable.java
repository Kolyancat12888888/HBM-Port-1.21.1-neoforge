package com.hbm.hazard.type;

import com.hbm.config.BombConfig;
import com.hbm.entity.logic.EntityNukeExplosionMK5;
import com.hbm.hazard.modifier.IHazardModifier;
import com.hbm.lib.ModDamageSource;
import com.hbm.util.I18nUtil;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.ObjDoubleConsumer;

public class HazardTypeUnstable implements IHazardType {
    public static final String NBT_TAG_TIMER = "timer";
    private BiConsumer<LivingEntity, ItemStack> onUpdateCustom;
    private ObjDoubleConsumer<ItemEntity> onDropCustom;
    private HazardInfoConsumer customInfo;
    private int timer = -1;

    public HazardTypeUnstable(int timer) {
        this(timer, null);
    }

    public HazardTypeUnstable(BiConsumer<LivingEntity, ItemStack> onUpdate, ObjDoubleConsumer<ItemEntity> onDrop, HazardInfoConsumer customInfo) {
        this.onUpdateCustom = onUpdate;
        this.onDropCustom = onDrop;
        this.customInfo = customInfo;
    }

    public HazardTypeUnstable(int timer, HazardInfoConsumer customInfo) {
        if (timer <= 0) throw new IllegalArgumentException("timer must be greater than 0");
        this.timer = timer;
        this.customInfo = customInfo;
    }

    @Override
    public void onUpdate(LivingEntity target, double level, ItemStack stack) {
        if (onUpdateCustom != null) {
            onUpdateCustom.accept(target, stack);
            return;
        }

        Level world = target.level();
        final int count = stack.getCount();
        final int radius = scaledRadius(level, count);
        setTimer(stack, getTimer(stack) + 1);

        if (getTimer(stack) >= this.timer && !world.isClientSide()) {
            stack.setCount(0);
            EntityNukeExplosionMK5 nuke = EntityNukeExplosionMK5.statFac(world, radius, target.getX(), target.getY(), target.getZ());
            nuke.setDetonator(target);
            world.addFreshEntity(nuke);

            world.playSound(null, target.getX(), target.getY(), target.getZ(), SoundEvents.GENERIC_EXPLODE.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
            target.hurt(ModDamageSource.of(world, ModDamageSource.NUCLEAR_BLAST), 10000.0F);
        }
    }

    @Override
    public void updateEntity(ItemEntity item, double level) {
        if (onDropCustom != null) {
            onDropCustom.accept(item, level);
            return;
        }

        Level world = item.level();
        int radius = (int) level;
        ItemStack stack = item.getItem();
        setTimer(stack, getTimer(stack) + 1);

        if (getTimer(stack) >= this.timer && !world.isClientSide()) {
            EntityNukeExplosionMK5 nuke = EntityNukeExplosionMK5.statFac(world, radius, item.getX(), item.getY(), item.getZ());
            world.addFreshEntity(nuke);

            world.playSound(null, item.getX(), item.getY(), item.getZ(), SoundEvents.GENERIC_EXPLODE.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
            item.discard();
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addHazardInformation(Player player, List<Component> tooltip, double level, ItemStack stack, List<IHazardModifier> modifiers) {
        if (customInfo != null) {
            customInfo.accept(player, tooltip, level, stack, modifiers);
        } else if (this.timer != -1) {
            final int scaled = scaledRadius(level, stack.getCount());
            tooltip.add(Component.literal("§4" + I18nUtil.resolveKey("trait.unstable") + "§r"));
            tooltip.add(Component.literal("§cDecay Time: " + (this.timer / 20) + "s - Explosion Radius: " + scaled + "m§r"));
            tooltip.add(Component.literal("§cDecay: " + (getTimer(stack) * 100 / this.timer) + "%§r"));
        }
    }

    private static int scaledRadius(double baseLevel, int count) {
        if (count <= 1) return (int) baseLevel;
        int r = (int) (baseLevel * Math.cbrt(count) + 0.5);
        return Math.max(1, r);
    }

    public static int getTimer(ItemStack stack) {
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        if (data != null && data.contains(NBT_TAG_TIMER)) {
            return data.copyTag().getInt(NBT_TAG_TIMER);
        }
        return 0;
    }

    public static void setTimer(ItemStack stack, int timer) {
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putInt(NBT_TAG_TIMER, timer));
    }
}
